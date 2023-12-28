package com.korea.Team5.weeklyMovie;

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
public class WeeklyMovieService {

    private final String apiUrl;
    private final String apiKey;
    private final RestTemplate restTemplate;
    private final WeeklyMovieRepository weeklyMovieRepository;



    @Autowired
    public WeeklyMovieService(RestTemplate restTemplate, WeeklyMovieRepository weeklyMovieRepository, @Value("${movie.api.url2}") String apiUrl, @Value("${movie.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.weeklyMovieRepository = weeklyMovieRepository;
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }

    @Transactional
    public List<WeeklyMovie> fetchDataAndSaveToDatabase(String targetDt) {
        // API 호출 및 데이터 가져오는 로직
        String url = apiUrl + "?key=" + apiKey + "&targetDt=" + targetDt;
        ResponseEntity<WeeklyBoxOfficeList> responseEntity = restTemplate.getForEntity(url, WeeklyBoxOfficeList.class);
        List<WeeklyMovie> movies = responseEntity.getBody().getBoxOfficeResult().getWeeklyBoxOfficeList();
        for (WeeklyMovie movie : movies) {
            movie.setModificationDateTime(LocalDateTime.now());
            this.weeklyMovieRepository.save(movie);
        }
        return movies;
    }

}

