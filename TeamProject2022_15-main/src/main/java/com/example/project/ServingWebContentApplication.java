package com.example.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The class in charge of displaying the webpages
 */
@SpringBootApplication
public class ServingWebContentApplication {

    /**
     * Main function for starting the web server
     * @param args unused
     */
    public static void main(String[] args) {
        SpringApplication.run(ServingWebContentApplication.class, args);
    }

}