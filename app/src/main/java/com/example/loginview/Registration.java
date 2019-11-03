package com.example.loginview;

public class Registration {
    private String firstName;
    private String lastname;
    private String password;
    private String email;
    private String phoneNo;
    private String category;
    private int regId;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Registration(String firstName1, String lastName1, String password1, String email1, String phoneNo1, String category1) {
        firstName = firstName1;
        lastname = lastName1;
        password = password1;
        email = email1;
        phoneNo = phoneNo1;
        category = category1;

    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getCategory() {
        return category;
    }

    public int getRegId() {return regId;}
}
