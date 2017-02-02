package onlinejudge.user.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import onlinejudge.domain.User;
import onlinejudge.dto.MyResponse;
import onlinejudge.user.service.UserService;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	
	@RequestMapping({"/","/about"})
	public @ResponseBody String about(){
		return "Microservice User";
	}
	@RequestMapping({"/current"})
	public @ResponseBody Principal  about2( Principal principal){
		return principal;
	}
	
	/**
	 * #001
	 */
	@PreAuthorize("permitAll()")
	@RequestMapping(value="/users", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody MyResponse createUser(@RequestBody User user){
		MyResponse myResponse = null;
		try {
			user = userService.createUser(user);
			myResponse =  MyResponse.builder().success().setObj(user.getId()).build();
		} catch (Exception e) {
			e.printStackTrace();
			myResponse=  MyResponse.builder().fail().setObj(e.getMessage()).build();
		}
		return myResponse;
	}
}
