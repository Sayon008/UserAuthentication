package com.example.productctaelogservice_first_api.models;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseModel {
    @Id
    private Long id;
    private Date created_at;
    private Date lastUpdated_at;
    private State state;   // state is used for soft delete purpose, we do not want to get rid of any data, once a data is getting deleted from the database it cannot be reverted
}


// We will not be creating object for this model class so make the class abstract
