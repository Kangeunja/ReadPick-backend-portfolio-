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

    @Transactional
    public void searchCategorySave(String searchIsbn) {
        BookCategoryVo bCVo = new BookCategoryVo();

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?ttbkey=" + ttbKey + "&itemIdType=ISBN&ItemId="
                + searchIsbn + "&output=js&Version=20131101";

        String response = restTemplate.exchange(url, HttpMethod.GET, null, String.class).getBody();

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            Iterator<JsonNode> elements = jsonNode.get("item").elements();
            while (elements.hasNext()) {
                JsonNode item = elements.next();
                String[] categories = item.get("categoryName").asText().split(">");
                List<String> categoryList = Arrays.asList(categories);

                String bmCategory = categoryList.get(0).trim().toLowerCase();
                bookCategoryMapper.insertBmCategories(bmCategory);
                

                int bmIdx = bookCategoryMapper.selectBmIdxByName(bmCategory);
                String bsCategory = categoryList.get(1);
                bCVo.setBmIdx(bmIdx);
                bCVo.setBsName(bsCategory);
                bookCategoryMapper.insertBsCategories(bCVo);

                int bsIdx = bookCategoryMapper.selectBsIdxByName(bsCategory);
                String bssCategory = categoryList.get(2);
                bCVo.setBsIdx(bsIdx);
                bCVo.setBssName(bssCategory);
                bookCategoryMapper.insertBssCategories(bCVo);

                if (categoryList.size() >= 4) {
                int bssIdx = bookCategoryMapper.selectBssIdxByName(bssCategory);
                String bsssCategory = categoryList.get(3);
                System.out.println("index 3 : " + bsssCategory);
                bCVo.setBssIdx(bssIdx);
                bCVo.setBsssName(bsssCategory);
                bookCategoryMapper.insertBsssCategories(bCVo);
                } else {
                System.out.println("세부카테고리없음");
                int bssIdx = bookCategoryMapper.selectBssIdxByName(bssCategory);
                bCVo.setBssIdx(bssIdx);
                bCVo.setBsssName("");
                bookCategoryMapper.insertBsssCategories(bCVo);
                }

                aladinBookSearchService.searchBookSave(searchIsbn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
