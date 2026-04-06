package edu.eci.dosw.DOSW_Library.persistence.nonrelational.document;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class EmbeddedUser {

    @Field("id")
    private String id;

    @Field("name")
    private String name;

    @Field("username")
    private String username;

    @Field("role")
    private String role;
}
