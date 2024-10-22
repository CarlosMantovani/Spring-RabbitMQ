package mantovanidev.mscloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class MscloudgatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MscloudgatewayApplication.class, args);
	}
	@Bean
	public RouteLocator route(RouteLocatorBuilder builder){
		return builder.routes()
				.route(r -> r.path("/clietes/**")
						.uri("lb://msclientes"))
				.route(r -> r.path("/api/v1/funcionarios/**")
						.uri("http://localhost:8082/api/v1/funcionarios"))
				.route(r -> r.path("/api/v1/resultados/**")
						.uri("http://localhost:8083/api/v1/resultado"))
				.route(r -> r.path("/api/v1/sessaovotacao/**")
						.uri("http://localhost:8083/api/v1/sessaovotacao"))
				.route(r -> r.path("/api/v1/votos/**")
						.uri("http://localhost:8083/api/v1/votos"))
				.build();
	}
}
