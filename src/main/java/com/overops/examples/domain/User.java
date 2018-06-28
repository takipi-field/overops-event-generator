package com.overops.examples.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String emailAddress;
    private LocalDate birthDate;
    private String placeOfBirth;
    private String ssn;
    private String password;
    private String note;

    public User() {
    }

    public User(String firstName, String middleName, String lastName, String emailAddress, LocalDate birthDate, String placeOfBirth, String ssn, String password, String note) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.birthDate = birthDate;
        this.placeOfBirth = placeOfBirth;
        this.ssn = ssn;
        this.password = password;
        this.note = note;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public String getSsn() {
        return ssn;
    }

    public String getPassword() {
        return password;
    }

    public String getNote() {
        return note;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", birthDate=" + birthDate +
                ", placeOfBirth='" + placeOfBirth + '\'' +
                ", ssn='" + ssn + '\'' +
                ", password='" + password + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
