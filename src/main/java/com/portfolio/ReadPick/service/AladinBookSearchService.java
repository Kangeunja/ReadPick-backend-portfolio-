package com.portfolio.ReadPick.service;

import java.util.Iterator;

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
import com.portfolio.ReadPick.vo.BookImageVo;
import com.portfolio.ReadPick.vo.BookVo;

@Service
public class AladinBookSearchService {
    @Value("${ttb.api.key}")
    private String ttbKey;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    BookImageMapper bookImageMapper;

    @Autowired
    BookCategoryMapper bookCategoryMapper;

    ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public void searchBookSave(String searchIsbn) {

        BookVo bookVo = new BookVo();
        BookImageVo bookImageVo = new BookImageVo();

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?ttbkey=" + ttbKey + "&itemIdType=ISBN&ItemId="
                + searchIsbn + "&output=js&Version=20131101";

        String response = restTemplate.exchange(url, HttpMethod.GET, null, String.class).getBody();

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            Iterator<JsonNode> elements = jsonNode.get("item").elements();
            while (elements.hasNext()) {
                JsonNode item = elements.next();
                bookVo.setBmIdx(bookCategoryMapper.selectMaxBmIdx());
                bookVo.setBsIdx(bookCategoryMapper.selectMaxBsIdx());
                bookVo.setBssIdx(bookCategoryMapper.selectMaxBssIdx());
                bookVo.setBsssIdx(bookCategoryMapper.selectMaxBsssIdx());
                String title = item.get("title").asText();
                // title = title.replaceAll("</b>", "");
                bookVo.setBName(title);
                bookVo.setAuthor(item.get("author").asText());
                bookVo.setBContent(item.get("description").asText());
                bookVo.setLink(item.get("link").asText());
                bookVo.setPublisher(item.get("publisher").asText());
                bookVo.setPubDate(item.get("pubDate").asInt());
                bookVo.setIsbn(item.get("isbn13").asText());

                bookMapper.insertBook(bookVo);

                // 사진넣는곳
                bookImageVo.setBIdx(bookMapper.selectMaxBIdx());
                bookImageVo.setFileType("Y");
                bookImageVo.setFileName(item.get("cover").asText());
                bookImageMapper.insertFile(bookImageVo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
