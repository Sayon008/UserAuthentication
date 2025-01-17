package com.example.productctaelogservice_first_api.models;

import jakarta.persistence.Entity;

@Entity
public class TestModel extends BaseModel{
    private String textField;
    private Integer numField;
}
