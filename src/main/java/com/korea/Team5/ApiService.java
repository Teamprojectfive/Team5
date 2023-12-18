package com.korea.Team5;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService{
    private final RestTemplate restTemplate;

    public ApiService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public String fetchDataFromApi(String apiUrl){
        String apiKey = "ed496e700881942361ed983f38957c9a";
        return restTemplate.getForObject(apiUrl, String.class, apiKey);
    }
}



