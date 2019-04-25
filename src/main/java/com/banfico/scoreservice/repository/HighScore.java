package com.banfico.scoreservice.repository;

public class HighScore {

	private String userId;
	private Long score;

	public HighScore() {
	}

	public HighScore(String userId, Long score) {
		this.userId = userId;
		this.score = score;
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
