package onlinejudge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import onlinejudge.repository.UserRepository;

@SpringBootApplication
@EnableDiscoveryClient
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableResourceServer
@EnableOAuth2Client
@EnableFeignClients
public class OnlinejudgeMSUserApplication
{
	 private final Logger logger = LoggerFactory.getLogger(OnlinejudgeMSUserApplication.class);
	@Autowired
	UserRepository userRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(OnlinejudgeMSUserApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		userRepository.deleteAll();
//		User user = new User();
//		user.setDisplayName("Hoàng Nhược Quỳ");
//		
//		user = userRepository.save(user);
//		System.out.println(user.getDisplayName());
//		
//		logger.debug("This is a debug message");
//        logger.info("This is an info message");
//        logger.warn("This is a warn message");
//        logger.error("This is an error message");
//	}
}
