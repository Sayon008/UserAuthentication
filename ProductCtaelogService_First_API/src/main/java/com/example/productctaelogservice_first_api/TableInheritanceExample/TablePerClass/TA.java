package com.example.productctaelogservice_first_api.TableInheritanceExample.TablePerClass;

import jakarta.persistence.Entity;

@Entity(name = "tpc_ta")
public class TA extends User{
    int helpRequest;
}
