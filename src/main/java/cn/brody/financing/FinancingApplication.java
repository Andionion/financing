package cn.brody.financing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author brody
 */
@EnableScheduling
@SpringBootApplication
public class FinancingApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinancingApplication.class, args);
    }

}
