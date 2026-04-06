package edu.eci.dosw.DOSW_Library.persistence.nonrelational.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;

@Data
@Document(collection = "loans")
public class LoanDocument {

    @Id
    private String id;

    @Field("loan_date")
    private LocalDate loanDate;

    @Field("return_date")
    private LocalDate returnDate;

    @DBRef
    private BookDocument book;

    @Field("user")
    private EmbeddedUser user;

    @Field("status_events")
    private List<StatusEvent> statusEvents;
}
