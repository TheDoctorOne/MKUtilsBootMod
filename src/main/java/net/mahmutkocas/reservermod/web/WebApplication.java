package net.mahmutkocas.reservermod.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebApplication {
    public static void WebApp() {
        new Thread(() ->
                SpringApplication.run(WebApplication.class)
        ).start();
    }
}
