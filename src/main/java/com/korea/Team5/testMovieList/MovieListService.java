package com.korea.Team5.testMovieList;

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
public class MovieListService {

    private final String apiUrl;
    private final String apiKey;
    private final RestTemplate restTemplate;
    private final MovieListRepository movieListRepository;

    @Autowired
    public MovieListService(RestTemplate restTemplate, @Value("${movie.api.url}") String apiUrl, @Value("${movie.api.key}") String apiKey, MovieListRepository movieListRepository) {
        this.restTemplate = restTemplate;
        this.movieListRepository = movieListRepository;
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }

    @Transactional
    public List<TestMovieList> movieListSaveDataBase() {
        String url = apiUrl + "?key=" + apiKey + "&itemPerPage=100";
        ResponseEntity<MovieList> responseEntity = restTemplate.getForEntity(url, MovieList.class);
        List<TestMovieList> movies = responseEntity.getBody().getMovieListResult().getMovieList();
        for (TestMovieList movie : movies) {
            TestMovieList testMovieList = this.movieListRepository.findByMovieNm(movie.getMovieNm());
            if (testMovieList == null) {
                this.movieListRepository.save(movie);
            }
        }
        return movies;
    }
}
