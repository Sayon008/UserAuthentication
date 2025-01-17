package com.example.productctaelogservice_first_api.TableInheritanceExample.TablePerClass;

import jakarta.persistence.Entity;

@Entity(name = "tpc_mentor")
public class Mentor extends User{
    int ratings;
}
