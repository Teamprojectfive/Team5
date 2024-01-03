package com.korea.Team5.api;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final String apiUrl;
    private final String apiKey;
    private final RestTemplate restTemplate;
    private final VideoRepository videoRepository;

    @Autowired
    private VideoService(RestTemplate restTemplate, @Value("${video.api.url}") String apiUrl, @Value("${video.api.key}") String apiKey, VideoRepository videoRepository) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.videoRepository = videoRepository;
    }

    @Transactional
    public List<Video> videoListSaveDataBase(){
        String url = apiUrl + "?key=" + apiKey;
        return null;
    }

}
