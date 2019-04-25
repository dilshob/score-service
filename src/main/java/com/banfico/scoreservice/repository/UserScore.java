package com.banfico.scoreservice.repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserScore {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Long levelId;
	private String userId;
	private Long score;
	
	public Long getId() {
		return id;
	}
	public UserScore() {
	}
			
	public UserScore(Long levelId, String userId, Long score) {
		this.levelId = levelId;
		this.userId = userId;
		this.score = score;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLevelId() {
		return levelId;
	}
	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Long getScore() {
		return score;
	}
	public void setScore(Long score) {
		this.score = score;
	}
}
