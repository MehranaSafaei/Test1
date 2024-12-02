package com.mehrana.test.entity;

import java.util.Objects;

public class Personnel {
    private Long id;
    private String userName;
    private String mobile;
    private Long PersonnelCode ;

    public Personnel() {
    }

    public Personnel( String userName, String mobile, Long personnelCode) {
        this.userName = userName;
        this.mobile = mobile;
        PersonnelCode = personnelCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getPersonnelCode() {
        return PersonnelCode;
    }

    public void setPersonnelCode(Long personnelCode) {
        PersonnelCode = personnelCode;
    }

    @Override
    public String toString() {
        return "Personnel{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", PersonnelCode=" + PersonnelCode +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Personnel personnel = (Personnel) o;
        return Objects.equals(id, personnel.id) && Objects.equals(userName, personnel.userName) && Objects.equals(mobile, personnel.mobile) && Objects.equals(PersonnelCode, personnel.PersonnelCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, mobile, PersonnelCode);
    }
}
