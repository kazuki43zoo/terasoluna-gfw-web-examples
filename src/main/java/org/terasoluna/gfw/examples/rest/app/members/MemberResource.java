package org.terasoluna.gfw.examples.rest.app.members;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import org.terasoluna.gfw.examples.common.domain.model.Gender;

public class MemberResource implements Serializable {

    private static final long serialVersionUID = 1L;

    public static interface MemberCreating {
    }

    public static interface MemberUpdating {
    }

    @Null(groups = MemberCreating.class)
    @NotNull(groups = MemberUpdating.class)
    @Size(min = 36, max = 36, groups = MemberUpdating.class)
    private String memberId;

    @NotNull
    @Size(min = 1, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 50)
    private String lastName;

    @NotNull
    private Gender gender;

    @NotNull
    @Size(min = 1, max = 256)
    private String emailAddress;

    @Size(max = 20)
    private String phoneNumber;

    @Size(max = 256)
    private String address;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
