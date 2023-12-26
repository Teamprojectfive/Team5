package com.korea.Team5.MovieApi;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {
    @Value("${movie.api.url}")
    private String apiUrl ;

    @Value("${movie.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate;

    public ApiService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public String getData(String targetDt){

        String result = restTemplate.getForObject(apiUrl + "?key=" + apiKey + "&targetDt=" + targetDt, String.class);
        return result;

    }

}
