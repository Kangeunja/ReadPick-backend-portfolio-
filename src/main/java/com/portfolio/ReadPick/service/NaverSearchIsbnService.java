package com.portfolio.ReadPick.service;

import java.util.HashSet;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.ReadPick.dao.BookMapper;

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

    int start = 1; // 검색 시작 위치

    ObjectMapper objectMapper = new ObjectMapper();

    HashSet<String> duplicate = new HashSet<>();
    @Transactional
    public void searchIsbnSave(String searchOneName) {
        System.out.println("searchOneName : " + searchOneName);
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://openapi.naver.com/v1/search/book.json?&query=" + searchOneName + "&display=" + 1 + "&start=" + start;

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
                aladinBookSearchService.searchCategorySave(isbn);
                Thread.sleep(100);
                // System.out.println(isbn);
            }
            start += 1; // 다음 검색 시작 위치로 이동
        } catch (Exception e) {
            System.out.println("NaverSearchIsbnServiceError : " + e);
        }
    }
}
