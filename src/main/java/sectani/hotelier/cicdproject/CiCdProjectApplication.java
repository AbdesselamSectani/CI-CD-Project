package sectani.hotelier.cicdproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CiCdProjectApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(CiCdProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //System.out.println("Hello World!");
    }
}
