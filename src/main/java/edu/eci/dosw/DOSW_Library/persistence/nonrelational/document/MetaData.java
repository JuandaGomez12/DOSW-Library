package edu.eci.dosw.DOSW_Library.persistence.nonrelational.document;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class MetaData {

    @Field("genre")
    private String genre;

    @Field("language")
    private String language;

    @Field("pages")
    private int pages;

    @Field("publisher")
    private String publisher;

    @Field("year")
    private int year;
}
