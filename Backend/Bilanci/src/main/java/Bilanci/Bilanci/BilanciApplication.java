package Bilanci.Bilanci;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@EnableCaching
@SpringBootApplication
public class BilanciApplication {

	public static void main(String[] args) {
		SpringApplication.run(BilanciApplication.class, args);
	}

}
