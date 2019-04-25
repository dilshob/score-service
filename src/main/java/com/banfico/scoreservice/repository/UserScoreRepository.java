package com.banfico.scoreservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserScoreRepository extends JpaRepository<UserScore, Long> {
	@Query("SELECT new com.banfico.scoreservice.repository.HighScore(a.userId, MAX(a.score)) FROM UserScore a WHERE a.levelId =:levelId GROUP BY a.userId")
	public List<HighScore> getHighestScoresForlevel(@Param("levelId") Long levelId);
}
