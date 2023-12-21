package com.korea.Team5.movie;
import com.korea.Team5.Review.Review;
import com.korea.Team5.USER.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import jakarta.persistence.ManyToMany;
@Entity
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name; // 이름

    private String content; // 내용

    private String genre; // 장르

    private String age; // 등급

    private String movieRelease; // 무비개봉일

    private String image; // 무비포스터

    private String cast; // 출연진

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE)
    private List<Review> reviewList;

    private LocalDateTime modifyDate;

    @ManyToMany
    Set<Member> voter;
}
