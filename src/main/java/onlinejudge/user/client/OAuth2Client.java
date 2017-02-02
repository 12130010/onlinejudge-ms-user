package onlinejudge.user.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "onlinejudge-ifa-oauth")
@RequestMapping("/onlinejudge-ifa-oauth")
public interface OAuth2Client {
	@RequestMapping("/about")
	public @ResponseBody String hello();
}
