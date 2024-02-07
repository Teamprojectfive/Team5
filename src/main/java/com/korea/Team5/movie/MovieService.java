package com.korea.Team5.movie;

import com.korea.Team5.DataNotFoundException;
import com.korea.Team5.USER.Member;

import com.korea.Team5.USER.MemberRepository;

import com.korea.Team5.board.Board;
import com.korea.Team5.board.BoardRepository;
import com.korea.Team5.kmapi.repository.PlotRepository;
import com.korea.Team5.movie.entity.*;
import com.korea.Team5.movie.repository.*;
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
    private final GenreMovieInfoRepository genreMovieInfoRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;



    private final String apiUrl;
    private final String apiKey;
    private final String apiUrl2;
    private final RestTemplate restTemplate;


    @Autowired

    public MovieService(RestTemplate restTemplate, MovieRepository movieRepository, MovieInfoRepository movieInfoRepository, @Value("${movie.api.url2}") String apiUrl, @Value("${movie.api.key}") String apiKey, @Value("${movie.api.detail.url}") String apiUrl2, GenreRepository genreRepository, Actor1Repository actor1Repository, AuditRepository auditRepository, CompanyRepository companyRepository, NationRepository nationRepository, StaffRepository staffRepository, DirectorRepository directorRepository, PlotRepository plotRepository, GenreMovieInfoRepository genreMovieInfoRepository, MemberRepository memberRepository,BoardRepository boardRepository) {
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
        this.genreMovieInfoRepository = genreMovieInfoRepository;
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


    public Page<MovieInfo> mainList(int page) {
        Pageable pageable = PageRequest.of(page, 4);
        return this.movieInfoRepository.findAll(pageable);
    }


    public Movie getMovie(Integer id) {
        Optional<Movie> movie = this.movieRepository.findById(id);
        if (movie.isPresent()) {
            return movie.get();
        } else {
            throw new DataNotFoundException("movie not found");
        }
    }


    public List<GenreMovieInfo> genreMovieInfoList() {
        return this.genreMovieInfoRepository.findAll();
    }


    public MovieInfo getMovieInfo(Integer id) {
        Optional<MovieInfo> movieInfo = this.movieInfoRepository.findById(id);
        if (movieInfo.isPresent()) {
            return movieInfo.get();
        } else {
            throw new DataNotFoundException("movie not found");

        }
    }

    public List<MovieInfo> getMovieInfoNm(String movieNm){
        String movieclear =  movieNm.trim();
        List<MovieInfo> movieInfoList = this.movieInfoRepository.findByMovieNmContaining(movieclear);

        return movieInfoList;
    }

    public List<Genre> genreList() {
        List<Genre> genres = this.genreRepository.findAll();
        List<Genre> uniqueGenres = new ArrayList<>();

        Set<String> uniqueGenreNames = new HashSet<>();

        for (Genre genre : genres) {
            if (uniqueGenreNames.add(genre.getGenreNm())) {
                // 중복되지 않은 경우에만 리스트에 추가
                uniqueGenres.add(genre);
            }
        }

        return uniqueGenres;
    }


    public void saveMovie(Movie movie) {
        this.movieRepository.save(movie);
    }



    public List<GenreMovieInfo> getMoviesByGenreId (Integer genreId){
        // GenreMovieInfoRepository를 통해 해당 장르 ID에 속하는 MovieInfo 목록을 가져옴
        return genreMovieInfoRepository.findByGenreId(genreId);
    }

    public MovieInfo genreMovieInfoToMovieInfo (GenreMovieInfo genreMovieInfo){
        return genreMovieInfo.getMovieInfo();
    }

    public List<MovieInfo> genreMovieInfoToMovieInfo (List < GenreMovieInfo > genreMovieInfoList) {
        List<MovieInfo> movieInfoList = new ArrayList<>();
        for (GenreMovieInfo genreMovieInfo : genreMovieInfoList) {
            movieInfoList.add(genreMovieInfo.getMovieInfo());
        }
        return movieInfoList;
    }




    public void delete (Movie movie){
        this.movieRepository.delete(movie);
    }




    public void vote(MovieInfo movieInfo, Member member) {
        // 사용자가 이미 찜한 목록에 해당 영화가 있는지 확인
        if (!member.getVoter().contains(movieInfo)) {
            member.getVoter().add(movieInfo);
            memberRepository.save(member);
        }
    }








    @Transactional
    public List<Movie> fetchDataAndSaveToDatabase (String targetDt){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate targetDate = LocalDate.parse(targetDt, formatter);
        List<Movie> movieList = new ArrayList<>();
        Set<String> displayedMovieSet = new HashSet<>();


        for (int i = 0; i < 20; i++) {
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


    }

    @Transactional
    public MovieInfo getMovieDetail () {
        List<Movie> movieList = this.movieRepository.findAll();
        List<MovieInfo> movieInfoList = this.movieInfoRepository.findAll();

        try {

            int i = 0;
            for (Movie movie : movieList) {
                if (i == 30) {
                    break;
                }


                String movieCd = movie.getMovieCd();



                String url = apiUrl2 + "?key=" + apiKey + "&movieCd=" + movieCd;
                ResponseEntity<MovieInfoResult> responseEntity = restTemplate.getForEntity(url, MovieInfoResult.class);
                MovieInfoResult movieInfoResult = responseEntity.getBody();

                if (movieInfoResult != null) {
                    MovieInfoWrap movieInfoWrap = movieInfoResult.getMovieInfoResult();

                    if (movieInfoWrap != null) {
                        MovieInfo movieInfo = movieInfoWrap.getMovieInfo();
                        MovieInfo targetMovieInfo = movieInfoRepository.findByMovieCd(movieCd);


                        if (targetMovieInfo == null) {
                            targetMovieInfo = this.movieInfoRepository.save(movieInfo);
                        }


                        movie.setMovieInfo(targetMovieInfo);
                        this.movieRepository.save(movie);


                        List<GenreDto> genres = movieInfo.getGenres();
                        for (GenreDto genre : genres) {

                            Genre targetGenre = this.genreRepository.findByGenreNm(genre.getGenreNm());

                            if (targetGenre == null) {
                                targetGenre = new Genre();
                                targetGenre.setGenreNm(genre.getGenreNm());
                                targetGenre = this.genreRepository.save(targetGenre);
                            }

                            GenreMovieInfo targetGenreMovieInfo = this.genreMovieInfoRepository.findByGenreAndMovieInfo(targetGenre, targetMovieInfo);
                            if (targetGenreMovieInfo == null) {
                                GenreMovieInfo genreMovieInfo = new GenreMovieInfo();
                                genreMovieInfo.setGenre(targetGenre);
                                genreMovieInfo.setMovieInfo(targetMovieInfo);

                                this.genreMovieInfoRepository.save(genreMovieInfo);
                            }
                        }
                        List<Nation> nations = movieInfo.getNations();
                        for (Nation nation : nations) {
                            Nation existingNation = nationRepository.findByNationNmAndMovieInfo(nation.getNationNm(), targetMovieInfo);
                            if (existingNation == null) {
                                nation.setMovieInfo(targetMovieInfo);
                                this.nationRepository.save(nation);
                            }
                        }

                        List<Director> directors = movieInfo.getDirectors();
                        for (Director director : directors) {
                            director.setMovieInfo(targetMovieInfo);
                            this.directorRepository.save(director);
                        }
                        List<Audit> audits = movieInfo.getAudits();
                        for (Audit audit : audits) {
                            Audit existingAudit = this.auditRepository.findByWatchGradeNmAndMovieInfo(audit.getWatchGradeNm(), targetMovieInfo);
                            if (existingAudit == null) {
                                audit.setMovieInfo(targetMovieInfo);
                                this.auditRepository.save(audit);
                            }
                        }
                        List<Actor1> actor1s = movieInfo.getActors();
                        for (Actor1 actor1 : actor1s) {
                            Actor1 existingActor = this.actor1Repository.findByPeopleNmAndMovieInfo(actor1.getPeopleNm(), targetMovieInfo);
                            if (existingActor == null) {
                                actor1.setMovieInfo(targetMovieInfo);
                                this.actor1Repository.save(actor1);
                            }
                        }
                        List<Company> companies = movieInfo.getCompanys();
                        for (Company company : companies) {
                            company.setMovieInfo(targetMovieInfo);
                            this.companyRepository.save(company);
                        }
                    } else {
                        throw new RuntimeException("API 응답 중 MovieInfoWrap이 null입니다.");
                    }
                } else {
                    throw new RuntimeException("API 응답이 null입니다.");
                }

                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("API 요청 중 오류가 발생했습니다.");
        }
        return null;

    }
    public Page<Movie> getAllMovies ( int page){
        // 페이징 처리를 위해 Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, 10); // PAGE_SIZE는 페이지당 보여줄 항목 수

        // 모든 멤버를 가져오는 메서드 호출
        return movieRepository.findAll(pageable);
    }

    public Page<MovieInfo> getAllMoviesiNfo ( int page){
        // 페이징 처리를 위해 Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, 10); // PAGE_SIZE는 페이지당 보여줄 항목 수

        // 모든 멤버를 가져오는 메서드 호출
        return movieInfoRepository.findAll(pageable);
    }



}