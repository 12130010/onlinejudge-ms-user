package onlinejudge.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import onlinejudge.domain.User;
import onlinejudge.repository.UserRepository;
import onlinejudge.user.client.OAuth2Client;

@Service
public class UserService {
	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public static final String USER_IS_EXIST = "User exist with email %s";
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	OAuth2Client oAuth2Client;
	
	public User createUser(User user) throws Exception{
		User userInRepository = userRepository.findUserByEmail(user.getEmail());
		if(userInRepository != null){
			throw new Exception(String.format(USER_IS_EXIST, user.getEmail()));
		}else{
			user.setPassword(encoder.encode(user.getPassword()));
			user = userRepository.save(user);
		}
		return user;
	}
}
