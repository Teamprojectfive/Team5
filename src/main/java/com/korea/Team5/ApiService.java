package com.korea.Team5;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

    private final RestTemplate restTemplate;
    private final String apiKey;

    public ApiService(RestTemplate restTemplate, @Value("${kobis.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    public String fetchDataFromApi(String today) {
        String apiUrl = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
        String url = apiUrl + "?key=" + apiKey + "&targetDt=" + today;

        try {
            // API 요청을 보내고 응답을 받음
            return restTemplate.getForObject(url, String.class);
        } catch (HttpClientErrorException e) {
            // 에러 발생 시 에러 메시지 출력
            return e.getResponseBodyAsString();
        }
    }
}


