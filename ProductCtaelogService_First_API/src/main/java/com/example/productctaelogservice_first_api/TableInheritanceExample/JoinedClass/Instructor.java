package com.example.productctaelogservice_first_api.TableInheritanceExample.JoinedClass;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity(name = "jc_instructor")
//If we want to give a different name of the primary key
@PrimaryKeyJoinColumn(name = "user_id")
public class Instructor extends User {
    String company;
}
