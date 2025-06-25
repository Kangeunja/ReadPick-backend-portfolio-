package com.portfolio.ReadPick.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.ReadPick.dao.UserMapper;
import com.portfolio.ReadPick.vo.UserImageVo;
import com.portfolio.ReadPick.vo.UserSessionDTO;

import jakarta.servlet.http.HttpSession;

@RestController
public class UserImageController {

    @Autowired
    HttpSession session;

    @Autowired
    UserMapper userMapper;

    String fileUploadPath = "C:/Users/호앵/Desktop/READPICKImages/";
    

     // user 프로필 이미지 추가
    @PostMapping(value = "userImageInsert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> userImageInsert(@RequestPart("file") MultipartFile file) throws Exception {

        UserSessionDTO user = (UserSessionDTO) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.ok("login:fail");
        }
        int userIdx = user.getUserIdx();

        try {
            if (!file.isEmpty()) {
                String filename = file.getOriginalFilename();
                UserImageVo userImage = new UserImageVo();
                userImage.setUserIdx(userIdx);
                userImage.setFileName(filename);
                File f = new File(fileUploadPath, filename);
                if (f.exists()) {// 동일한 파일이 존재하냐?
                    // 시간_파일명 이름변경
                    long tm = System.currentTimeMillis();
                    filename = String.format("%d_%s", tm, filename);
                    f = new File(fileUploadPath + filename);
                }
                file.transferTo(f);
                userMapper.insertUserImage(userImage);
            } else {
                return ResponseEntity.ok("fail");
            }
        } catch (Exception e) {
            System.out.println("userImageInsert: " + e.getMessage());
            return ResponseEntity.ok("fail");
        }

        UserSessionDTO sessionUserInfo = (UserSessionDTO) session.getAttribute("user");
        sessionUserInfo.setFileName("http://localhost:8080/READPICKImages/" + file.getOriginalFilename());
        session.setAttribute("user", sessionUserInfo);
        return ResponseEntity.ok("http://localhost:8080/READPICKImages/" + file.getOriginalFilename());
    
    }

    // 프로필 이미지 삭제
    @PostMapping("userImageDelete")
    public ResponseEntity<String> userImageDelete() {
        UserSessionDTO user = (UserSessionDTO) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.ok("login:fail");
        }
        int userIdx = user.getUserIdx();
        String deleteFileName = userMapper.selectUserImageFromUserIdx(userIdx);
        File deleteFile = new File(fileUploadPath, deleteFileName);
        if (deleteFile.exists()) {
            boolean deleted = deleteFile.delete();
            if (!deleted) {
                System.out.println("파일 삭제 실패: " + deleted);
            } else {
                System.out.println("파일 삭제 성공: " + deleted);
            }
        }
        int res = userMapper.deleteUserImage(userIdx);
        UserSessionDTO sessionUserInfo = (UserSessionDTO) session.getAttribute("user");
        sessionUserInfo.setFileName("default");
        session.setAttribute("user", sessionUserInfo);
        return ResponseEntity.ok("success");
    }

    // 프로필 이미지 변경
    @PostMapping(value = "userImageUpdate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> userImageUpdate(@RequestPart("file") MultipartFile file) throws Exception {
        UserSessionDTO user = (UserSessionDTO) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.ok("login:fail");
        }
        int userIdx = user.getUserIdx();

        // 기존 이미지 삭제
        String deleteFileName = userMapper.selectUserImageFromUserIdx(userIdx);
        System.out.println("deleteFileName: " + deleteFileName);
        File deleteFile = new File(fileUploadPath, deleteFileName);
        try {
            if (deleteFile.exists()) {
                boolean deleted = deleteFile.delete();
                if (deleted) {
                    System.out.println("파일 삭제 성공: " + deleted);
                }
            } else {
                return ResponseEntity.ok("fileDelete:fail");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                File f = new File(fileUploadPath, fileName);
                file.transferTo(f);
                UserImageVo userImage = new UserImageVo();
                userImage.setUserIdx(userIdx);
                userImage.setFileName(fileName);
                userMapper.updateUserImage(userImage);
            } else {
                return ResponseEntity.ok("fail");
            }
        } catch (Exception e) {
            System.out.println("userImageUpdate: " + e.getMessage());
            return ResponseEntity.ok("fail");
        }

        UserSessionDTO sessionUserInfo = (UserSessionDTO) session.getAttribute("user");
        sessionUserInfo.setFileName("http://localhost:8080/READPICKImages/" + file.getOriginalFilename());
        session.setAttribute("user", sessionUserInfo);
        return ResponseEntity.ok("http://localhost:8080/READPICKImages/" + file.getOriginalFilename());
    }

    // 프로필 이미지 가져오기
    @GetMapping("/READPICKImages/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        // 요청된 이미지 파일 경로 설정
        Path imagePath = Paths.get(fileUploadPath).resolve(imageName);
        
        // 이미지가 존재하지 않으면 404 반환
        if (!imagePath.toFile().exists()) {
            return ResponseEntity.notFound().build();
        }

        // 이미지 파일 반환
        Resource resource = new FileSystemResource(imagePath);
        
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)  // 이미지의 MIME 타입 설정
                .body(resource);  // 이미지 리소스를 바디로 반환
    }

}
