package com.example.sharedkernel.domain.demographics;

import com.example.sharedkernel.domain.base.ValueObject;
import lombok.Getter;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Getter
@Embeddable
public class FullName implements ValueObject {

    @Column(name = "first_name")
    private final String firstName;
    @Column(name = "last_name")
    private final String lastName;
    public FullName() {
        firstName = "default";
        lastName = "default";
    }
    public FullName(@NonNull  String firstName, @NonNull String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
