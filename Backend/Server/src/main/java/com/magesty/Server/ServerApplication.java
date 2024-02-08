package com.magesty.Server;

import com.magesty.Server.model.Server;
import com.magesty.Server.repository.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

import static com.magesty.Server.enumeration.Status.SERVER_DOWN;
import static com.magesty.Server.enumeration.Status.SERVER_UP;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	CommandLineRunner run(ServerRepository serverRepository) {
		return args -> {
			serverRepository.save(new Server(null, "45.141.123.141", "Ubuntu Linux", "16 GB",
					"Personal PC", "http://localhost:8080/api/server/image/server1.png", SERVER_UP));
			serverRepository.save(new Server(null, "192.168.1.58", "Fedora Linux", "16 GB",
					"Dell Towel", "http://localhost:8080/api/server/image/server2.png", SERVER_DOWN));
			serverRepository.save(new Server(null, "192.168.1.21", "MS 2008", "32 GB",
					"Web Server", "http://localhost:8080/api/server/image/server3.png", SERVER_UP));
			serverRepository.save(new Server(null, "192.168.1.14", "Red Hat enterprise Linux", "64 GB",
					"Mail Server", "http://localhost:8080/api/server/image/server4.png", SERVER_DOWN));
			serverRepository.save(new Server(null, "104.25.194.175", "AS16276 OVH SAS", "32 GB",
					"Personal PC", "http://localhost:8080/api/server/image/server5.png", SERVER_UP));
			serverRepository.save(new Server(null, "89.132.207.82", "Vodafone Hungary Ltd", "16 GB",
					"Personal PC", "http://localhost:8080/api/server/image/server6.png", SERVER_DOWN));
		};
	}

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration =  new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:3000"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin","Content-Type","Accept","Jwt-Token","Authorization","Origin, Accept",
				"X-Requested-With","Access-Control-Request-Method","Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin","Content-Type","Accept","Jwt-Token","Authorization", "Access-Control-Allow-Origin", "Access-Control-Allow-Origin","Access-Control-Allow-Credentials", "File-Name"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","DELETE","PUT","OPTIONS"));
		UrlBasedCorsConfigurationSource basedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		basedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter((CorsConfigurationSource) basedCorsConfigurationSource);
	}
}
