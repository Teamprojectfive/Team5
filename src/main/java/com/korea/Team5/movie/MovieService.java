package com.korea.Team5.movie;

import com.korea.Team5.DataNotFoundException;
import lombok.RequiredArgsConstructor;
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

    public Movie getMovie(Long id){
        Optional<Movie> movie = this.movieRepository.findById(id);
        if(movie.isPresent()){
            return movie.get();
        } else {
            throw new DataNotFoundException("movie not found");
        }
    }

}
