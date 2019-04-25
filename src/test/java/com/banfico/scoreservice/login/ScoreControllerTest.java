package com.banfico.scoreservice.login;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.banfico.scoreservice.repository.UserScoreRepository;

public class ScoreControllerTest {

	@InjectMocks
	private ScoreController controller;

	@Mock
	private UserScoreRepository repository;

	@Mock
	private User user;

	@Mock
	MockHttpServletRequest request;

	private MockMvc mockMvc;
	private Locale locale;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		locale = LocaleContextHolder.getLocale();
	}

	@Test
	public void login_WithUserId_thenReturnOk() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/login/userId").locale(locale))
				.andExpect(status().isOk()).andReturn();
		assertThat(result.getResponse().getContentAsString(), Matchers.notNullValue());
		
		MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders.get("/login/userId").locale(locale))
				.andExpect(status().isOk()).andReturn();
		assertThat(result.getResponse().getContentAsString(), Matchers.equalTo(result1.getResponse().getContentAsString()));
	}
	
	
	@Test
	public void addScore_WithUserIdWithValidSession_thenReturnOk() throws Exception {
		Mockito.when(user.getSessionId()).thenReturn("test");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/score/5")
				.param("sessionkey", "test")
				.locale(locale)
				.contentType(MediaType.APPLICATION_JSON).content("87"))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void addScore_WithUserIdWithInValidSession_ThrowException() throws Exception {
		Mockito.when(user.getSessionId()).thenReturn("error");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/score/5")
				.param("sessionkey", "test")
				.locale(locale)
				.contentType(MediaType.APPLICATION_JSON).content("87"))
				.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void highscorelist_WithUserIdWithOutSessionNotEqualStoersUser_thenReturnOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/highscorelist/4").locale(locale))
				.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void highscorelist_WithUserIdWithSessionNotEqualStoersUser_thenReturnOk() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/login/userId").locale(locale))
				.andExpect(status().isOk()).andReturn();
		
		Mockito.when(user.getSessionId()).thenReturn(result.getResponse().getContentAsString());
		mockMvc.perform(MockMvcRequestBuilders.post("/score/5")
				.param("sessionkey", result.getResponse().getContentAsString())
				.locale(locale)
				.contentType(MediaType.APPLICATION_JSON).content("87")
				.sessionAttr("user", new User("test", "test")))
				.andExpect(status().isCreated());
		
		mockMvc.perform(MockMvcRequestBuilders.get("/highscorelist/5").locale(locale))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
		        .andExpect(jsonPath("$[0].userId").exists())
		        .andExpect(jsonPath("$[0].test").value("test"));
		
	}
	
}
