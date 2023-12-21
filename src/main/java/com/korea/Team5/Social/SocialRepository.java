package com.korea.Team5.Social;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface SocialRepository extends JpaRepository<User,String> {
  Optional<User> findBysocialId(String socialId);



}
