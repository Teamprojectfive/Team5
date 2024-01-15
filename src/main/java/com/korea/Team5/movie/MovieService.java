package com.korea.Team5.movie;

import com.korea.Team5.DataNotFoundException;
import com.korea.Team5.USER.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieInfoRepository movieInfoRepository;

    private final String apiUrl;
    private final String apiKey;
    private final String apiUrl2;
    private final RestTemplate restTemplate;


    @Autowired

    public MovieService(RestTemplate restTemplate, MovieRepository movieRepository, MovieInfoRepository movieInfoRepository, @Value("${movie.api.url2}") String apiUrl, @Value("${movie.api.key}") String apiKey, @Value("${movie.api.detail.url}") String apiUrl2) {
        this.restTemplate = restTemplate;
        this.movieRepository = movieRepository;
        this.movieInfoRepository = movieInfoRepository;
        this.apiUrl = apiUrl;
        this.apiUrl2 = apiUrl2;
        this.apiKey = apiKey;
    }



    public List<Movie> list() {
        return this.movieRepository.findAll();
    }


    public Page<Movie> mainList(int page) {
        Pageable pageable = PageRequest.of(page, 4);
        return this.movieRepository.findAll(pageable);
    }

    public Movie getMovie(Integer id) {
        Optional<Movie> movie = this.movieRepository.findById(id);
        if (movie.isPresent()) {
            return movie.get();
        } else {
            throw new DataNotFoundException("movie not found");
        }
    }

    public void saveMovie(Movie movie) {
        this.movieRepository.save(movie);
    }

    public void vote(Movie movie, Member member) {
        movie.getVoter().add(member);
        this.movieRepository.save(movie);
    }

    public void voteCancle(Movie movie, Member member) {
        movie.getVoter().remove(member);
        this.movieRepository.save(movie);
    }

    public void delete(Movie movie) {
        this.movieRepository.delete(movie);
    }




    @Transactional
    public List<Movie> fetchDataAndSaveToDatabase(String targetDt) {
        String url = apiUrl + "?key=" + apiKey + "&targetDt=" + targetDt;
        ResponseEntity<WeeklyBoxOfficeList> responseEntity = restTemplate.getForEntity(url, WeeklyBoxOfficeList.class);
        List<Movie> movies = responseEntity.getBody().getBoxOfficeResult().getWeeklyBoxOfficeList();

        for (Movie movie : movies) {



            this.movieRepository.save(movie);
        }
        return movies;
    }

    @Transactional
    public MovieInfo getMovieDetail(String movieCd) {
        String url = apiUrl2 + "?key=" + apiKey + "&movieCd=" + movieCd;

        try {
            ResponseEntity<MovieInfoResult> responseEntity = restTemplate.getForEntity(url, MovieInfoResult.class);
            MovieInfoResult movieInfoResult = responseEntity.getBody();

            if (movieInfoResult != null) {
                MovieInfoWrap movieInfoWrap = movieInfoResult.getMovieInfoResult();

                if (movieInfoWrap != null) {
                    MovieInfo movieInfo = movieInfoWrap.getMovieInfo();

                    this.movieInfoRepository.save(movieInfo);

                    System.out.println(movieInfo);

                    return movieInfo;
                } else {
                    throw new RuntimeException("API 응답 중 MovieInfoWrap이 null입니다.");
                }
            } else {
                throw new RuntimeException("API 응답이 null입니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("API 요청 중 오류가 발생했습니다.");
        }
    }
    public Page<Movie> getAllMovies(int page) {
        // 페이징 처리를 위해 Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, 10); // PAGE_SIZE는 페이지당 보여줄 항목 수

        // 모든 멤버를 가져오는 메서드 호출
        return movieRepository.findAll(pageable);
    }
}
