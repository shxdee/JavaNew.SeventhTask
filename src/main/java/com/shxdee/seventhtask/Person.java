package com.shxdee.seventhtask;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


@JsonSerialize(using = PersonSerializer.class)
@JsonDeserialize(using = PersonDeserializer.class)
public class Person implements Serializable {
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthDay;

    public Person(String firstName, String middleName, String lastName, LocalDate birthDay) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDay = birthDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) && Objects.equals(middleName, person.middleName) && Objects.equals(lastName, person.lastName) && Objects.equals(birthDay, person.birthDay);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + firstName + '\'' +
                ", lastName='" + middleName + '\'' +
                ", patronymic='" + lastName + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, middleName, lastName, birthDay);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }


}
