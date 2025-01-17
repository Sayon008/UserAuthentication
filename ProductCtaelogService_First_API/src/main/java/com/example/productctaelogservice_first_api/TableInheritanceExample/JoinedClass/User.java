package com.example.productctaelogservice_first_api.TableInheritanceExample.TablePerClass;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

import java.util.UUID;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity(name="tpc_user")
public class User {
    @Id
    UUID id;
    String email;
}
