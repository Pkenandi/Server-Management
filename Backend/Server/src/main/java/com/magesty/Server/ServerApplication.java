package com.magesty.Server;

import com.magesty.Server.model.Server;
import com.magesty.Server.repository.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
					"Personal PC", "http://localhost:8080/server/image/server1.png", SERVER_UP));
			serverRepository.save(new Server(null, "192.168.1.58", "Fedora Linux", "16 GB",
					"Dell Towel", "http://localhost:8080/server/image/server2.png", SERVER_DOWN));
			serverRepository.save(new Server(null, "192.168.1.21", "MS 2008", "32 GB",
					"Web Server", "http://localhost:8080/server/image/server3.png", SERVER_UP));
			serverRepository.save(new Server(null, "192.168.1.14", "Red Hat enterprise Linux", "64 GB",
					"Mail Server", "http://localhost:8080/server/image/server4.png", SERVER_DOWN));
			serverRepository.save(new Server(null, "104.25.194.175", "AS16276 OVH SAS", "32 GB",
					"Personal PC", "http://localhost:8080/server/image/server5.png", SERVER_UP));
			serverRepository.save(new Server(null, "89.132.207.82", "Vodafone Hungary Ltd", "16 GB",
					"Personal PC", "http://localhost:8080/server/image/server6.png", SERVER_DOWN));
		};
	}

}
