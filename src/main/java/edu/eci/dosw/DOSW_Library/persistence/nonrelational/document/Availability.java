package edu.eci.dosw.DOSW_Library.persistence.nonrelational.document;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Availability {

    @Field("total_copies")
    private int totalCopies;

    @Field("available_copies")
    private int availableCopies;
}
