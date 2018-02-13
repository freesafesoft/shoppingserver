package com.fss.shopping.web.dto;

import com.fss.shopping.constraint.FieldMatch;
import com.fss.shopping.validation.ValidEmail;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.AssertTrue;

@FieldMatch.List({@FieldMatch(first = "password", second = "passwordConfirm", message = "The password fields must match"),
        @FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")})
public class UserRegistrationDto {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @ValidEmail
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String passwordConfirm;

    @AssertTrue
    private Boolean terms;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getTerms() {
        return terms;
    }

    public void setTerms(Boolean terms) {
        this.terms = terms;
    }

    public String toString() {
        return firstName + " " + lastName;
    }
}
