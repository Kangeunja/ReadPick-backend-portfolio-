package com.portfolio.ReadPick.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.ReadPick.dao.BookMapper;
import com.portfolio.ReadPick.vo.BookVo;

@Service
public class NaverSearchIsbnService {
    @Value("${naver.client.id}")
    private String clientId;
    @Value("${naver.client.secret}")
    private String clientSecret;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    AladinCategorySearchService aladinBookSearchService;

    ObjectMapper objectMapper = new ObjectMapper();



    public void searchIsbnSave(String searchOneName) {
        HashSet<String> duplicate = new HashSet<>();
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://openapi.naver.com/v1/search/book.json?&query=" + searchOneName + "&display=" + 5;

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        String response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            Iterator<JsonNode> elements = jsonNode.get("items").elements();
            while (elements.hasNext()) {
                JsonNode item = elements.next();
                String isbn = item.get("isbn").asText();
                if (!duplicate.contains(isbn)) {
                    bookMapper.isbnInsert(isbn);
                }
            }
            List<BookVo>isbnList = bookMapper.selectIsbnList();
            for(int i=0; i<isbnList.size(); i++) {
                String isbn = isbnList.get(i).getIsbn();
                aladinBookSearchService.searchCategorySave(isbn);
                // System.out.println(isbn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
