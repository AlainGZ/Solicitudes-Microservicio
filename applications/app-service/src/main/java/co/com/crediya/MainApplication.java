package co.com.crediya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication(scanBasePackages = "co.com.crediya")
@ConfigurationPropertiesScan
@EnableR2dbcRepositories(basePackages = "co.com.crediya.r2dbc")
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
