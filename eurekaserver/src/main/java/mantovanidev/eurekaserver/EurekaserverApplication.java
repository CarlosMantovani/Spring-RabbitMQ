package mantovanidev.eurekaserver;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaserverApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		System.setProperty("EUREKA_USER", dotenv.get("EUREKA_USER"));
		System.setProperty("EUREKA_PASSWORD", dotenv.get("EUREKA_PASSWORD"));
		SpringApplication.run(EurekaserverApplication.class, args);
	}

}
