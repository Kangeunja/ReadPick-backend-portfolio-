package com.portfolio.ReadPick.service;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.ReadPick.dao.BookCategoryMapper;
import com.portfolio.ReadPick.dao.BookImageMapper;
import com.portfolio.ReadPick.dao.BookMapper;
import com.portfolio.ReadPick.vo.BookCategoryVo;
import com.portfolio.ReadPick.vo.BookVo;

@Service
public class AladinCategorySearchService {

    @Value("${ttb.api.key}")
    private String ttbKey;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    BookImageMapper bookImageMapper;

    @Autowired
    BookCategoryMapper bookCategoryMapper;

    @Autowired
    AladinBookSearchService aladinBookSearchService;

    ObjectMapper objectMapper = new ObjectMapper();

    BookVo bookVo = new BookVo();

    @Transactional
    public void searchCategorySave(String searchIsbn) {
        BookCategoryVo bCVo = new BookCategoryVo();

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?ttbkey=" + ttbKey + "&itemIdType=ISBN13&ItemId="
                + searchIsbn + "&output=js&Version=20131101";

        String response = restTemplate.exchange(url, HttpMethod.GET, null, String.class).getBody();
        // System.out.println("response : "+response);

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            if (jsonNode.get("errorCode") != null)
                return; // errorCode field가 있는 경우만 처리
            Iterator<JsonNode> elements = jsonNode.get("item").elements(); // item이라 써진 field 값을 가져온다
            while (elements.hasNext()) { // 다음 칸이 있는지 확인 있다면 true 없다면 그대로 종료
                JsonNode item = elements.next();
                if (item.get("categoryName") == null)
                    return; // categoryName field가 없을 때만 처리
                String[] categories = item.get("categoryName").asText().split(">");
                List<String> categoryList = Arrays.asList(categories);
                if (categoryList.size() >= 1 && !categoryList.get(0).equals("")) {
                    String bmName = categoryList.get(0); // 카테고리의 첫번째, 대분류를 가져온다
                    bookCategoryMapper.insertBmCategories(bmName); // 바로저장, 중복은 db에서 unique제약으로 걸러진다
                    if (categoryList.size() >= 2) {
                        int bmIdx = bookCategoryMapper.selectBmIdxByName(bmName); // 방금 저장한 대분류의
                        // 이름을 통해 대분류idx를 가져온다
                        String bsCategory = categoryList.get(1);
                        bCVo.setBmIdx(bmIdx);
                        bCVo.setBsName(bsCategory);
                        bookCategoryMapper.insertBsCategories(bCVo);
                        if (categoryList.size() >= 3) {
                            int bsIdx = bookCategoryMapper.selectBsIdxByName(bsCategory);
                            String bssName = categoryList.get(2);
                            bCVo.setBsIdx(bsIdx);
                            bCVo.setBssName(bssName);
                            bookCategoryMapper.insertBssCategories(bCVo);
                            if (categoryList.size() >= 4) {
                                int bssIdx = bookCategoryMapper.selectBssIdxByName(bssName);
                                String bsssName = categoryList.get(3);
                                bCVo.setBssIdx(bssIdx);
                                bCVo.setBsssName(bsssName);
                                bookCategoryMapper.insertBsssCategories(bCVo);
                                int bsssIdx = bookCategoryMapper.selectBsssIdxBybsssName(bsssName);
                                bookVo.setBsssIdx(bsssIdx);
                                bookVo.setIsbn(searchIsbn);
                                bookMapper.isbnInsert(bookVo);
                            } else {
                                int bssIdx = bookCategoryMapper.selectBssIdxByName(bssName);
                                bCVo.setBssIdx(bssIdx);
                                bCVo.setBsssName("");
                                bookCategoryMapper.insertBsssCategories(bCVo);
                                int bsssIdx = bookCategoryMapper.selectBsssIdxBybsssName("");
                                bookVo.setBsssIdx(bsssIdx);
                                bookVo.setIsbn(searchIsbn);
                                bookMapper.isbnInsert(bookVo);
                            }
                        }
                    }
                } else {
                    return;
                }

                // // aladinBookSearchService.searchBookSave(searchIsbn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
