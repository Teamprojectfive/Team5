package com.korea.Team5.kmapi;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korea.Team5.kmapi.dto.MovieInfoDto;
import com.korea.Team5.kmapi.entity.Plot;
import com.korea.Team5.kmapi.entity.Vod;
import com.korea.Team5.kmapi.repository.PlotRepository;
import com.korea.Team5.kmapi.repository.VodRepository;
import com.korea.Team5.movie.*;
import com.korea.Team5.movie.entity.MovieInfo;
import com.korea.Team5.movie.repository.MovieInfoRepository;
import com.korea.Team5.movie.repository.MovieRepository;
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
                        if("괴물".equals(title)){
                            movieInfo.setPosters("https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzEyMDlfMjA2%2FMDAxNzAyMTE5ODU3MzE0.vVswg-3_t0goE9Hn_3Ossm8WocPOkhNUsrLBUo7UBeIg.gC6ypnF7OwY79sMYDrEmCY7WLqZIjmRWv25xFkfOC1Yg.JPEG.ksw2069%2FIMG_2513.jpg&type=sc960_832");
                        }
                        if("스즈메의 문단속".equals(title)){
                            movieInfo.setPosters("https://img.cgv.co.kr/Movie/Thumbnail/StillCut/000087/87936/87936221923_727.jpg");
                        }
                        if(" 바다 탐험대 옥토넛 어보브 앤 비욘드 : 버드, 옥토경보를 울려라!".equals(title)){
                            movieInfo.setPosters("https://d3ihz389yobwks.cloudfront.net/1700538605929eHH53.jpg");
                        }
                        if("스노우 폭스 : 썰매개가 될 거야!".equals(title)){
                            movieInfo.setPosters("https://www.ydct.org/uploaded/file/editor/show/202311/17004650050440.png");
                        }
                        if("명탐정코난: 흑철의 어영".equals(title)){
                            movieInfo.setPosters("https://i.namu.wiki/i/W9-BvWLJ1wJOLLOUldPw1Px_TeVnr2_5v_ODtYZSooI0NYAvAY1hXoabVa8MscXl6C_fJq-oUoCoDiLwig54PQ.webp");
                        }
                        if("30일".equals(title)){
                            movieInfo.setPosters("https://i.namu.wiki/i/_bA-MObF5EXnGyBOJI1McaWX0xuyYLTqW6qBaKZVaa2u_K1VfLuWGmbaQhaAiS2oJkUjraEGXP5kyFjfx1bVBQ.webp");
                        }
                        if("소년들".equals(title)){
                            movieInfo.setPosters("https://i.namu.wiki/i/KAUR15tC2YIfKZ4WRg4iV1S7mCvMLk6ARQi2gVnGjdUWPa-k4NiBDtckddcoqz5glrtK-V-w1KeR0OvFm4_lmw.webp");
                        }


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

        return null;
    }
}

