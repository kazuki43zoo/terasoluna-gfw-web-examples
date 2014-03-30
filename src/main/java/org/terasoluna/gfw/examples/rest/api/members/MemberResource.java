package org.terasoluna.gfw.examples.rest.api.members;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import org.terasoluna.gfw.common.codelist.ExistInCodeList;
import org.terasoluna.gfw.examples.rest.api.common.resource.ApiValidationGroups.PostResources;
import org.terasoluna.gfw.examples.rest.api.common.resource.ApiValidationGroups.PutResource;
import org.terasoluna.gfw.examples.rest.api.common.resource.hateoas.AbstractLinksSupportedResource;

public class MemberResource extends AbstractLinksSupportedResource  {

    private static final long serialVersionUID = 1L;

    @Null(groups = PostResources.class)
    @NotNull(groups = PutResource.class)
    @Size(min = 36, max = 36, groups = PutResource.class)
    private String memberId;

    @NotNull
    @Size(min = 1, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 50)
    private String lastName;

    @NotNull
    @Size(min = 1)
    @ExistInCodeList(codeListId = "CL_GENDER")
    private String gender;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
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
