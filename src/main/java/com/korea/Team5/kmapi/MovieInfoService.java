package com.korea.Team5.kmapi;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieInfoService {
    private final String apiUrl;
    private final String apiKey;
    private final RestTemplate restTemplate;
    private final MovieInfoRepository movieInfoRepository;


    @Autowired
    private MovieInfoService(RestTemplate restTemplate, @Value("${video.api.url}") String apiUrl, @Value("${video.api.key}") String apiKey, MovieInfoRepository movieInfoRepository) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.movieInfoRepository = movieInfoRepository;
//        this.videoRepository = videoRepository;
    }

    @Transactional
    public List<MovieInfo> videoListSaveDataBase() {
        String url = apiUrl + "&ServiceKey=" + apiKey + "&listCount=100" + "&releaseDts=20231001&releaseDte=20231231";

        ResponseEntity<String> responseEntity1 = restTemplate.getForEntity(url, String.class);
        String body = responseEntity1.getBody();
        ObjectMapper mapper = new ObjectMapper();

        try {
            DataWrap dataWrap = mapper.readValue(body, DataWrap.class);
            List<Data> datas = dataWrap.getData();
            Data data = datas.get(0);
            List<MovieInfoDto> movieInfoList = data.getResult();



            for (MovieInfoDto info : movieInfoList) {
                MovieInfo movieInfo = new MovieInfo();

                System.out.println(info.getTitle());
                System.out.println(info.getDirector());
                movieInfo.setTitle(info.getTitle());


                this.movieInfoRepository.save(movieInfo);


//                this.movieInfoRepository.save(info);
//                for (Director director : info.getDirectors()) {
//                    System.out.println(director.getDirectorNm());
//                }
            }
            return null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

//        List<Data> datas = responseEntity.getBody().getData();
//        List<MovieInfo> movieInfoList = data.getResult();
//        Data data = datas.get(0);
//
//        for (MovieInfo info : movieInfoList) {
//            System.out.println(info.getTitle());
//            for (Director director : info.getDirectors()) {
//                System.out.println(director.getDirectorNm());
//            }
//        }

//        return movieInfoList;


    }

}
