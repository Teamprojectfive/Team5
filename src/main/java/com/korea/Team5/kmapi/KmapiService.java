package com.korea.Team5.kmapi;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korea.Team5.movie.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KmapiService {

    private final String apiUrl;
    private final String apiKey;
    private final RestTemplate restTemplate;
    private final MovieInfoRepository movieInfoRepository;
    private final MovieService movieService;
    private final MovieRepository movieRepository;
    private final PlotRepository plotRepository;
    private final VodRepository vodRepository;


    @Autowired
    private KmapiService(RestTemplate restTemplate, @Value("${video.api.url}") String apiUrl, @Value("${video.api.key}") String apiKey, MovieInfoRepository movieInfoRepository, MovieService movieService, MovieRepository movieRepository, PlotRepository plotRepository,VodRepository vodRepository) {
        this.vodRepository = vodRepository;
        this.plotRepository = plotRepository;
        this.movieRepository = movieRepository;
        this.movieInfoRepository = movieInfoRepository;
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.movieService = movieService;
    }

//    public List<Kmapi> getList(){
//        return this.kmapiRepository.findAll();
//    }

    @Transactional
    public List<MovieInfoDto> videoListSaveDataBase() {
        List<MovieInfo> movieInfoList = this.movieInfoRepository.findAll();

        for (MovieInfo movieInfo : movieInfoList) {
            String name = movieInfo.getMovieNm();
            String url = apiUrl + "&title=" + name + "&ServiceKey=" + apiKey;

            ResponseEntity<String> responseEntity1 = restTemplate.getForEntity(url, String.class);
            String body = responseEntity1.getBody();
            ObjectMapper mapper = new ObjectMapper();

            try {
                DataWrap dataWrap = mapper.readValue(body, DataWrap.class);
                List<Data> datas = dataWrap.getData();
                Data data = datas.get(0);
                List<MovieInfoDto> movieInfoDtoList = data.getResult();

                if (!movieInfoDtoList.isEmpty()) {
                    String target1 = " !HE ";
                    String target2 = " !HS ";

                    for (MovieInfoDto firstMovie : movieInfoDtoList) {
                        String title = firstMovie.getTitle().replace(target1, "").replace(target2, "").substring(1);
                        String year = firstMovie.getProdYear();
                        if(title.equals(movieInfo.getMovieNm()) && year.equals(movieInfo.getPrdtYear())){
                            System.out.println(firstMovie.getVods());

                            String posterUrl = firstMovie.getPosters().split("\\|")[0];
                            movieInfo.setPosters(posterUrl);
                            Plot plot = firstMovie.getPlots().getPlot().get(0);
                            Vod vod = firstMovie.getVods().getVod().get(0);
                            // Plot 정보와 MovieInfo 연결
                            plot.setMovieInfo(movieInfo);
                            vod.setMovieInfo(movieInfo);
                            // Plot 저장
                            this.vodRepository.save(vod);
                            this.plotRepository.save(plot);
                            System.out.println("영화 제목: " + movieInfo.getMovieNm());
                            System.out.println("영화 줄거리: " + plot.getPlotText());
                        } else {
                            System.out.println("줄거리 없음");
                        }
                    }

                }

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        // 여기서 반환하고 싶은 값이나 작업이 있는 경우 처리하도록 수정해주세요.
        return null;
    }
}







//            for (MovieInfoDto info : movieInfoDtoList) {
//                Optional<MovieInfo> optionalMovieInfo = movieInfoRepository.findByMovieNm();
//                if (optionalMovieInfo.isPresent()) {
//                    MovieInfo movieInfo = optionalMovieInfo.get();
//
//                    if (info.getPlots() != null) {
//                        Plot plot = info.getPlots().getPlot().get(0);
//
//                        // MovieInfo에 연결된 Plot 저장
//                        plot.setMovieInfo(movieInfo);
//                        movieInfo.getPlot().add(plot);
//
//                        // 저장
//
//                        movieInfoRepository.save(movieInfo);
//
//
//                        System.out.println("영화 제목: " + movieInfo.getMovieNm());
//                        System.out.println("영화 줄거리: " + plot.getPlotText());
//                    } else {
//                        System.out.println("줄거리 없음");
//                    }
//                }
//            }
//            return movieInfoDtoList;
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
