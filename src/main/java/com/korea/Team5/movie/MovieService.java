package com.korea.Team5.movie;

import com.korea.Team5.DataNotFoundException;
import com.korea.Team5.USER.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public List<Movie> list(){
        return this.movieRepository.findAll();
    }


    public Page<Movie> mainList(int page){
        Pageable pageable = PageRequest.of(page, 4);
        return this.movieRepository.findAll(pageable);
    }

    public Movie getMovie(Integer id){
        Optional<Movie> movie = this.movieRepository.findById(id);
        if(movie.isPresent()){
            return movie.get();
        } else {
            throw new DataNotFoundException("movie not found");
        }
    }

    public void saveMovie(Movie movie){
        this.movieRepository.save(movie);
    }

    public void vote(Movie movie, Member member){
        movie.getVoter().add(member);
        this.movieRepository.save(movie);
    }
    public void voteCancle(Movie movie, Member member){
        movie.getVoter().remove(member);
        this.movieRepository.save(movie);
    }

}
