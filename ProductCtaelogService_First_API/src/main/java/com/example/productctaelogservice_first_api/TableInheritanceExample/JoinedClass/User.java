package com.example.productctaelogservice_first_api.TableInheritanceExample.JoinedClass;

import jakarta.persistence.*;

import java.util.UUID;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity(name="jc_user")
public class User {
    @Id
    UUID id;
    String email;
}
