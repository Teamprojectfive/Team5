package com.korea.Team5.Dummy;

import com.korea.Team5.movie.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class dummyMovieService {

    private final dummyMovieRepository dummyMovieRepository;
    public List<dummyMovie> list(){
        return this.dummyMovieRepository.findAll();
    }
}
