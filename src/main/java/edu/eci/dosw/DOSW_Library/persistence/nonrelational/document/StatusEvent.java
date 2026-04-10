package edu.eci.dosw.DOSW_Library.persistence.nonrelational.document;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Data
public class StatusEvent {

    @Field("status")
    private String status;

    @Field("date")
    private LocalDate date;

    @Field("notes")
    private String notes;
}
