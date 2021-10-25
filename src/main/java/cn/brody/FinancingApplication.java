package cn.brody;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author brody
 */
@SpringBootApplication(scanBasePackages = {"cn.brody.financing.*"})
public class FinancingApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinancingApplication.class, args);
    }

}
