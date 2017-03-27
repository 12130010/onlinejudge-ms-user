package onlinejudge.user.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import onlinejudge.domain.User;
import onlinejudge.dto.MyResponse;
import onlinejudge.message.util.MessageSourceUtil;
import onlinejudge.user.service.UserService;

@Controller
public class UserController implements MessageSourceAware{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MessageSource messageSource; 
	 
	@Autowired
	UserService userService;
	
	@RequestMapping({"/","/about"})
	public @ResponseBody String about(){
		return "Microservice User";
	}
	@RequestMapping({"/current"})
	public @ResponseBody Principal  currentUser( Principal principal){
		logger.debug("/current - User: " + principal.getName());
		return principal;
	}
	
	/**
	 * #user-001
	 */
	@PreAuthorize("permitAll()")
	@RequestMapping(value="/users", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody User createUser(@RequestBody User user){
		logger.debug("/users : Create user - User: " + user.getEmail());
		try {
			user = userService.createUser(user);
			//TODO validate user information
			logger.info("Create user success! - User: " + user.getEmail());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Create user fail! - User: " + user.getEmail() + " - exception: " +e.getMessage());
		}
		return user;
	}
	/**
	 * #user-002
	 * Get user id by email
	 * @param email
	 * @return
	 */
	@RequestMapping("/get-user-id-by-email")
	public @ResponseBody ResponseEntity<String> getUserIDByEmail(@RequestParam String email){
		ResponseEntity<String> response  = null;
		User user = userService.getUserByEmail(email);
		if(user == null){
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else{
			response = new ResponseEntity<String>(user.getId(), HttpStatus.OK);
		}
		return response;
	}
	
	/**
	 * #user-003
	 * Get user info by email
	 * @param email
	 * @return
	 */
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getUserByEmail(@RequestParam(defaultValue = "") String email){
		ResponseEntity<?> response = null;
		User user = userService.getUserByEmail(email);
		if(user == null){
			MyResponse myResponse = MyResponse.builder().fail().
					setObj(MessageSourceUtil.getMessage(messageSource, "user.email.not.exist", email)).build();
			response = new ResponseEntity<MyResponse>(myResponse, HttpStatus.BAD_REQUEST);
		}else{
			user.setPassword("******");
			response = new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return response;
	}
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
