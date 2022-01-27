package com.java8.streams;

public class Student {

    private String subject;
    private String name;
    private String surname;
    private String city;
    private int age;

    public Student(String subject, String name, String surname, String city, int age) {
        super();
        this.subject = subject;
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.age = age;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student [subject=" + subject + ", name=" + name + ", surname=" + surname + ", city=" + city + ", age="
                + age + "]";
    }

}
