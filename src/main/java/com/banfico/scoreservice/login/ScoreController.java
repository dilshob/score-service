package com.banfico.scoreservice.login;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.banfico.scoreservice.repository.HighScore;
import com.banfico.scoreservice.repository.UserScore;
import com.banfico.scoreservice.repository.UserScoreRepository;

@RestController
public class ScoreController {

	@Autowired
	private User user;
	
	@Autowired
	private UserScoreRepository repository;
  
    @GetMapping(value = "/login/{userId}")
    @ResponseStatus(code = HttpStatus.OK)
	public String login(@PathVariable("userId") String userId, Model model, HttpServletRequest request) {
		if(!userId.equalsIgnoreCase(user.getUserId())) {
			request.getSession().invalidate();
			user.setUserId(userId);
			user.setSessionId(request.getSession().getId());
		}
		return user.getSessionId();
	}
    
    @PostMapping(value = "/score/{levelid}")
    @ResponseStatus(code = HttpStatus.CREATED)
	public void addScore(@PathVariable("levelid") Long levelId, @RequestParam("sessionkey")  String sessionkey, 
			@RequestBody Long score,Model model,HttpServletRequest request) {
    	if(!sessionkey.equalsIgnoreCase(user.getSessionId()) || isNotValidSession(request)) {
    		throw new InvalidSessionException();
		}
    	repository.save(new UserScore(levelId, user.getUserId(), score));
	}
	
	private boolean isNotValidSession(HttpServletRequest request) {
		Long timeLeft = new Date().getTime() - request.getSession().getCreationTime();
		return timeLeft > 600000;
	}

	@GetMapping(value = "/highscorelist/{levelid}")
	public List<HighScore> getHighScoreList(@PathVariable("levelid") Long levelId) {
		List<HighScore> userList = repository.getHighestScoresForlevel(levelId);
		if(CollectionUtils.isEmpty(userList)) {
			throw new NotFoundException();
		}
		return userList;
	}
}
