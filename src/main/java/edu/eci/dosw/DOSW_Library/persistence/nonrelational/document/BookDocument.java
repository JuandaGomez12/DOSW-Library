package edu.eci.dosw.DOSW_Library.persistence.nonrelational.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "books")
public class BookDocument {

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("author")
    private String author;

    @Field("metadata")
    private MetaData metadata;

    @Field("availability")
    private Availability availability;
}
