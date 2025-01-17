package com.example.productctaelogservice_first_api.TableInheritanceExample.TablePerClass;

import jakarta.persistence.Entity;

@Entity(name = "tpc_instructor")
public class Instructor extends User{
    String company;
}
