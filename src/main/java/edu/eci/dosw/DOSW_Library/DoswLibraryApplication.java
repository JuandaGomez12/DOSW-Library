package edu.eci.dosw.DOSW_Library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "edu.eci.dosw.DOSW_Library.persistence.relational.repository")
@EnableMongoRepositories(basePackages = "edu.eci.dosw.DOSW_Library.persistence.nonrelational.repository")
=======

@SpringBootApplication
>>>>>>> 99ce3ddad76a1c4b3c9c90ad262cc377de97fb4c
public class DoswLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoswLibraryApplication.class, args);
    }
}
