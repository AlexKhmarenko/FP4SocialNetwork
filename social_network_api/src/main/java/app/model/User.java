package app.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity (name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Integer user_id;

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;

    @Column(name = "email")
    String email;

    @Column (name = "created_date")
    Date createdDate;

    @Column (name = "name")
    String name;

    @Column (name = "profile_background_image_url")
    String profileBackgroundImageURL;

    @Column(name = "profile_image_url")
    String profileImageURL;

}
