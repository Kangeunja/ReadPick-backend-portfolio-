package com.portfolio.ReadPick.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.portfolio.ReadPick.dao.BookCategoryMapper;
import com.portfolio.ReadPick.dao.BookmarkMapper;
import com.portfolio.ReadPick.dao.UserMapper;
import com.portfolio.ReadPick.vo.BookCategoryVo;
import com.portfolio.ReadPick.vo.BookmarkVo;
import com.portfolio.ReadPick.vo.UserVo;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;


@RestController
public class UserController {

    @Autowired
    HttpSession session;

    @Autowired
    UserMapper userMapper;

    @Autowired
    BookCategoryMapper bookCategoryMapper;

    @Autowired
    BookmarkMapper bookmarkMapper;

    @PostMapping("list")
    public ResponseEntity<List<UserVo>> list() {

        List<UserVo> list = userMapper.selectList();

        return ResponseEntity.ok(list);
    }

    @PostMapping("login")
    @Operation(summary = "로그인", description = "로그인하기")
    public RedirectView login(String id, String pw, String url, RedirectAttributes ra) {

        UserVo user = userMapper.selectOneFromId(id);

        // 아이디가 없는(틀린)경우
        if (user == null) {
            ra.addAttribute("reason", "fail_id");
            return new RedirectView(url);
        }

        // 비밀번호가 틀린경우
        if (user.getPw().equals(pw) == false) {
            ra.addAttribute("reason", "fail_password");
            return new RedirectView(url);
        }

        // 로그인처리: 현재 로그인된 객체(user)정보를 session저장
        session.setAttribute("user", user);

        if (url != null)
            return new RedirectView(url);

        if(user.getFirstAt().equals("Y")){
            return new RedirectView("/userPick.do");
        }    

        return new RedirectView("/mainPage.do");
    }

    // 로그아웃
    @PostMapping("logout")
    @Operation(summary = "로그아웃", description = "로그아웃하기")
    public RedirectView logout() {

        session.removeAttribute("user");

        return new RedirectView("/mainPage.do");
    }// end:logout()

    // 아이디 중복체크
    @PostMapping(value = "checkId")
    @Operation (summary = "아이디 중복체크", description = "회원가입 시 아이디 중복체크")
    public String check_id(String id) {

        UserVo vo = userMapper.selectOneFromId(id);

        boolean bResult = false;
        if (vo == null)
            bResult = true;
        else
            bResult = false;

        String json = String.format("{\"result\": %b }", bResult);

        return json;
    }// end:check_id()

    // 회원가입
    @PostMapping("userInsert")
    @Operation (summary = "회원가입", description = "회원가입하기")
    public RedirectView userInsert(UserVo user) {

        user.setAdminAt("N");
        int res = userMapper.userInsert(user);

        return new RedirectView("/mainPage.do");
    }

    // 로그인체크
    @PostMapping(value = "checkLogin")
    @Operation (summary = "로그인체크", description = "로그인체크")
    public ResponseEntity<String> checkLogin(String id, String pw) {
        UserVo user = (UserVo) session.getAttribute("user");
        if(user==null){
            return ResponseEntity.ok("fail");
        }
        return ResponseEntity.ok("success");
    }

    @GetMapping("userPick")
    @Operation(summary = "첫 로그인 시 띄울 장르 페이지", description = "여기서 선택 후 확인을 누르면 userPickResult로 이동 <br> 데이터 사용할 때는 Map형식으로 보냈으니 알아서 잘 <br> 참고로 Map이름은 categories 키 밸류는 이름이 같음 각각 bsList/bssList")
    public ResponseEntity<Map<String, Object>> userPick() {
        List<BookCategoryVo> bsList = bookCategoryMapper.selectBsList();
        List<BookCategoryVo> bssList = new ArrayList<BookCategoryVo>();
        for (int i = 0; i < bsList.size(); i++) {
            bssList.addAll(bookCategoryMapper.selectBssList(bsList.get(i).getBsIdx()));
        }
        Map<String, Object> categories = new HashMap<String, Object>();
        categories.put("bsList", bsList);
        categories.put("bssList", bssList);
        return ResponseEntity.ok(categories);
    }

    @PostMapping("userPickResult")
    @Operation(summary = "테스트불가", description = "처음 로그인 했을 때 고른 장르들을 저장하는 코드지만 데이터를 정확하게 보내야 하는 이슈로 스웨거에서 테스트불가")
    public ResponseEntity<String> userPickResult(List<List<Integer>> userPick) {

        UserVo user = (UserVo) session.getAttribute("user");

        if(user != null) {
            for (int i = 0; i < userPick.size(); i++) {
                int bsIdx = userPick.get(i).get(0);
                int bssIdx = userPick.get(i).get(1);
                int bmIdx = bookCategoryMapper.selectBmIdxOneByBsIdx(bsIdx);
    
                int res = bookCategoryMapper.insertUserPick(user.getUserIdx(), bmIdx, bsIdx, bssIdx);
            }
        }
        user.setFirstAt("N");
        int res = userMapper.userFirstAtUpdate(user);
        
        return ResponseEntity.ok("success");
    }
    
}
