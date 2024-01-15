package com.korea.Team5.movie;

import com.korea.Team5.DataNotFoundException;
import com.korea.Team5.USER.Member;
import com.korea.Team5.kmapi.Plot;
import com.korea.Team5.kmapi.PlotRepository;
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



import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieInfoRepository movieInfoRepository;

    private final GenreRepository genreRepository;
    private final Actor1Repository actor1Repository;
    private final AuditRepository auditRepository;
    private final CompanyRepository companyRepository;
    private final NationRepository nationRepository;
    private final StaffRepository staffRepository;
    private final DirectorRepository directorRepository;
    private final PlotRepository plotRepository;




    private final String apiUrl;
    private final String apiKey;
    private final String apiUrl2;
    private final RestTemplate restTemplate;


    @Autowired

    public MovieService(RestTemplate restTemplate, MovieRepository movieRepository, MovieInfoRepository movieInfoRepository, @Value("${movie.api.url2}") String apiUrl, @Value("${movie.api.key}") String apiKey, @Value("${movie.api.detail.url}") String apiUrl2, GenreRepository genreRepository, Actor1Repository actor1Repository, AuditRepository auditRepository, CompanyRepository companyRepository, NationRepository nationRepository, StaffRepository staffRepository, DirectorRepository directorRepository, PlotRepository plotRepository) {
        this.plotRepository = plotRepository;
        this.directorRepository = directorRepository;
        this.actor1Repository = actor1Repository;
        this.auditRepository = auditRepository;
        this.companyRepository = companyRepository;
        this.nationRepository = nationRepository;
        this.staffRepository = staffRepository;

        this.restTemplate = restTemplate;
        this.movieRepository = movieRepository;
        this.movieInfoRepository = movieInfoRepository;
        this.genreRepository = genreRepository;



        this.apiUrl = apiUrl;
        this.apiUrl2 = apiUrl2;
        this.apiKey = apiKey;
    }



    public List<Movie> list() {
        return this.movieRepository.findAll();
    }

    public List<MovieInfo> infoList() {
        return this.movieInfoRepository.findAll();
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
    public MovieInfo getMovieInfo(Integer id) {
        Optional<MovieInfo> movieInfo = this.movieInfoRepository.findById(id);
        if (movieInfo.isPresent()) {
            return movieInfo.get();
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate targetDate = LocalDate.parse(targetDt, formatter);
        List<Movie> movieList = new ArrayList<>();


        for (int i = 0; i < 14; i++) {
            String url = apiUrl + "?key=" + apiKey + "&targetDt=" + targetDate.format(formatter);
            System.out.println(url);
            ResponseEntity<WeeklyBoxOfficeList> responseEntity = restTemplate.getForEntity(url, WeeklyBoxOfficeList.class);
            List<Movie> movies = responseEntity.getBody().getBoxOfficeResult().getWeeklyBoxOfficeList();

            for (Movie movie : movies) {

                int audiAcc = Integer.parseInt(movie.getAudiAcc());
                movie.setAudiAcc(String.valueOf(audiAcc));
                this.movieRepository.save(movie);


            }
            movieList.addAll(movies);

            // currentDate를 1주씩 감소
            targetDate = targetDate.minusWeeks(1);



        }
        return movieList;

//        List<Movie> sortedMovies = this.movieRepository.findAllByOrderByAudiAccDesc();

    }

    @Transactional
    public MovieInfo getMovieDetail() {
        List<Movie> movieList = this.movieRepository.findAll();

        // 1. 박스오피스 영화 다 가져오기(가져와서 제거하든)
        // 2. 중복 제거된 영화리스트.
        // 3. 하나씩 뽑아서 movieCd 추출해서 api url 생성
        // 4. 요청해서 받아온 데이터를 저장 -> OneToMany 연결해서 저장(이미 존재하는 경우 저장 X)
        Set<String> movieCds = new HashSet<>();
        try {

            int i = 0;
            for (Movie movie : movieList) {
                if (i == 30) {
                    break;
                }

                String movieCd = movie.getMovieCd();
                if (movieCds.add(movieCd)) {
                    String url = apiUrl2 + "?key=" + apiKey + "&movieCd=" + movieCd;
                    ResponseEntity<MovieInfoResult> responseEntity = restTemplate.getForEntity(url, MovieInfoResult.class);
                    MovieInfoResult movieInfoResult = responseEntity.getBody();
                    if (movieInfoResult != null) {
                        MovieInfoWrap movieInfoWrap = movieInfoResult.getMovieInfoResult();

                        if (movieInfoWrap != null) {
                            MovieInfo movieInfo = movieInfoWrap.getMovieInfo();
                            this.movieInfoRepository.save(movieInfo);

                            List<Genre> genres = movieInfo.getGenres();
                            for(Genre genre : genres){
                                genre.setMovieInfo(movieInfo);
                                this.genreRepository.save(genre);
                            }
                            List<Nation> nations = movieInfo.getNations();
                            for(Nation nation : nations){
                                nation.setMovieInfo(movieInfo);
                                this.nationRepository.save(nation);
                            }
                            List<Director> directors = movieInfo.getDirectors();
                            for(Director director : directors){
                                director.setMovieInfo(movieInfo);
                                this.directorRepository.save(director);
                            }
                            List<Audit> audits = movieInfo.getAudits();
                            for(Audit audit : audits){
                                audit.setMovieInfo(movieInfo);
                                this.auditRepository.save(audit);
                            }
                            List<Actor1> actor1s = movieInfo.getActors();
                            for(Actor1 actor1 : actor1s) {
                                actor1.setMovieInfo(movieInfo);
                                this.actor1Repository.save(actor1);
                            }
                            List<Company> companies = movieInfo.getCompanys();
                            for(Company company : companies) {
                                company.setMovieInfo(movieInfo);
                                this.companyRepository.save(company);
                            }
//                            List<Plot> plot = movieInfo.getPlot();
//                            for(Plot plots : plot){
//                                plots.setMovieInfo(movieInfo);
//                                this.plotRepository.save(plots);
//                            }


                            movie.setMovieInfo(movieInfo);
                            this.movieRepository.save(movie);

                        } else {
                            throw new RuntimeException("API 응답 중 MovieInfoWrap이 null입니다.");
                        }
                    } else {
                        throw new RuntimeException("API 응답이 null입니다.");
                    }
                    i++;


                }
            }
            } catch(Exception e){
                e.printStackTrace();
                throw new RuntimeException("API 요청 중 오류가 발생했습니다.");
            }
            return null;

    }

    public Page<Movie> getAllMovies(int page) {
        // 페이징 처리를 위해 Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, 10); // PAGE_SIZE는 페이지당 보여줄 항목 수

        // 모든 멤버를 가져오는 메서드 호출
        return movieRepository.findAll(pageable);
    }

}

