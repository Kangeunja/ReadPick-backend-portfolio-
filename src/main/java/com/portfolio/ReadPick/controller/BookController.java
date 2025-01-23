package com.portfolio.ReadPick.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.portfolio.ReadPick.dao.BookMapper;

@Controller
public class BookController {

    @Autowired
    BookMapper bookMapper;

    @RequestMapping("DBData.do")
    public String DBData() {
        ShopVo shop = new ShopVo();
        // 1=게임 2=스포츠
        for (int c = 1; c <= 2; c++) {
            // 대분류가 게임인 경우
            if (c == 1) {
                // String categoryName = shopMapper.selectCategoryName(c);
                List<ShopVo> gameMcategory = shopMapper.DBMcategoryName(c);
                for (int gm = 0; gm < gameMcategory.size(); gm++) {
                    String gameMcategoryName = gameMcategory.get(gm).getMcategoryName();
                    shop.setMcategoryName(gameMcategoryName);
                    shop.setCategoryNo(c);
                    int gameMcategoryNo = shopMapper.selectMCategoryNo(shop);
                    List<ShopVo> gameDcategory = shopMapper.selectdCategoryNameList(gameMcategoryNo);
                    for (int gd = 0; gd < gameDcategory.size(); gd++) {
                        String gameDcategoryName = gameDcategory.get(gd).getDcategoryName();
                        searchService.searchAndSave(1, gameMcategoryName, gameDcategoryName);
                    }
                }
                System.out.println("================게임추가 끝===============");
                // 대분류가 스포츠인 경우
            } else if (c == 2) {
                List<ShopVo> sportsMcategory = shopMapper.DBMcategoryName(c);
                for (int sm = 0; sm < sportsMcategory.size(); sm++) {
                    String sportsMcategoryName = sportsMcategory.get(sm).getMcategoryName();
                    shop.setMcategoryName(sportsMcategoryName);
                    shop.setCategoryNo(c);
                    int sportsMcategoryNo = shopMapper.selectMCategoryNo(shop);
                    List<ShopVo> sportsDcategory = shopMapper.selectdCategoryNameList(sportsMcategoryNo);
                    for (int sd = 0; sd < sportsDcategory.size(); sd++) {
                        String sportsDcategoryName = sportsDcategory.get(sd).getDcategoryName();
                        searchService.searchAndSave(2, sportsMcategoryName, sportsDcategoryName);
                    }
                }
                System.out.println("================스포츠추가 끝===============");
            }
        }
        return "home";
    }

}
