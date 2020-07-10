package com.example.sharedkernel.domain.time;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Objects;


@Embeddable
public class Date {
    @Column(name = "year")
    private Long year;

    @Column(name = "month")
    private int month;

    @Column(name = "day")
    private int day;

    protected Date() {
    }

    public Date(@NonNull Long year, @NonNull int month,
                   @NonNull int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @NonNull
    @JsonProperty("year")
    public Long getYear() {
        return year;
    }

    @NonNull
    @JsonProperty("month")
    public int getMonth() {
        return month;
    }

    @NonNull
    @JsonProperty("day")
    public int getDay() {
        return day;
    }

    public int compare(Date other) {
        if(this.year > other.year){
            return 1;
        }
        else if (other.year > this.year){
            return -1;
        }
        else {
            if(this.month > other.month){
                return 1;
            }
            else if (other.month > this.month){
                return -1;
            }
            else {
                if(this.day > other.day){
                    return 1;
                }
                else if (other.day > this.day){
                    return -1;
                }
                else {
                    return 0;
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Date)) return false;
        Date date = (Date) o;
        return month == date.month &&
                day == date.day &&
                Objects.equals(year, date.year);
    }

    @Override
    public String toString() {
        return "Date{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }
}
