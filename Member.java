package com.library.model;

public class Member {
    private int id;
    private String name;
    private String email;
    private String phone;
    private int credits;

    public Member() {}

    public Member(int id, String name, String email, String phone, int credits) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.credits = credits;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
}
