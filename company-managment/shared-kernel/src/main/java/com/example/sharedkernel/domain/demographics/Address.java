package com.example.sharedkernel.domain.demographics;

import com.example.sharedkernel.domain.base.ValueObject;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Objects;

@Embeddable
@MappedSuperclass
public class Address implements ValueObject {

    @Column(name = "address")
    private String address;
    @Column(name = "city")
    @Embedded
    private CityName city;
    @Column(name = "country")
    @Enumerated(EnumType.STRING)
    private State state;

    protected Address() {
    }

    public Address(@NonNull String address, @NonNull CityName city,
                   @NonNull State state) {
        this.address = address;
        this.city = city;
        this.state = state;
    }

    @NonNull
    @JsonProperty("address")
    public String address() {
        return address;
    }

    @NonNull
    @JsonProperty("city")
    public CityName city() {
        return city;
    }

    @NonNull
    @JsonProperty("country")
    public State country() {
        return state;
    }


    public Address changeAddress(String address){
        if(address.isEmpty()){
            throw new IllegalArgumentException("Cannot set an empty address");
        }
        return new Address(address, city, state);
    }

    public Address changeCity(String cityName){
        if(address.isEmpty()){
            throw new IllegalArgumentException("City name must not be empty");
        }
        return new Address(address, city.changeCity(cityName), state);
    }

    public Address changeState(State state){
        return new Address(address, city, state);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(address, address.address) &&
                Objects.equals(city, address.city) &&
                state == address.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(address,  city, state);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(address);
        sb.append(", ");
        sb.append(city);
        sb.append(", ");
        sb.append(state);
        return sb.toString();
    }

}
