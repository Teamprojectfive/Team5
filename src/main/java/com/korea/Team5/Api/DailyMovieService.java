package com.korea.Team5.Api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyMovieService {

    private final String apiUrl;
    private final String apiKey;
    private final RestTemplate restTemplate;
    private final DailyMovieRepository dailyMovieRepository;

    @Autowired
    public DailyMovieService(RestTemplate restTemplate, DailyMovieRepository dailyMovieRepository, @Value("${movie.api.url}") String apiUrl, @Value("${movie.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.dailyMovieRepository = dailyMovieRepository;
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }

    @Transactional
    public List<DailyMovie> fetchDataAndSaveToDatabase(String targetDt) {
        // API 호출 및 데이터 가져오는 로직
        String url = apiUrl + "?key=" + apiKey + "&targetDt=" + targetDt;
        ResponseEntity<DailyBoxOfficeList> responseEntity = restTemplate.getForEntity(url, DailyBoxOfficeList.class);
        List<DailyMovie> movies = responseEntity.getBody().getBoxOfficeResult().getDailyBoxOfficeList();
        for (DailyMovie movie : movies) {
            movie.setModificationDateTime(LocalDateTime.now());
            this.dailyMovieRepository.save(movie);
        }
        return movies;
    }

}

