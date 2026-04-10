package edu.eci.dosw.DOSW_Library.persistence.nonrelational;

import edu.eci.dosw.DOSW_Library.persistence.nonrelational.document.UserDocument;
import edu.eci.dosw.DOSW_Library.persistence.nonrelational.repository.UserDocumentRepository;
import edu.eci.dosw.DOSW_Library.persistence.relational.repository.JpaUserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("mongo")
public class DataInitializer implements ApplicationRunner {

    private final UserDocumentRepository userDocumentRepository;
    private final JpaUserRepository jpaUserRepository;

    public DataInitializer(UserDocumentRepository userDocumentRepository,
                           JpaUserRepository jpaUserRepository) {
        this.userDocumentRepository = userDocumentRepository;
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        jpaUserRepository.findAll().forEach(entity -> {
            if (userDocumentRepository.findByUsername(entity.getUsername()).isEmpty()) {
                UserDocument doc = new UserDocument();
                doc.setId(entity.getId());
                doc.setName(entity.getName());
                doc.setUsername(entity.getUsername());
                doc.setPassword(entity.getPassword());
                doc.setRole(entity.getRole().name());
                userDocumentRepository.save(doc);
                System.out.println("Usuario sincronizado en MongoDB: " + entity.getUsername());
            }
        });
    }
}
