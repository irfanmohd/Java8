package com.datastructure;

public class Employee {

    public Employee(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + "]";
    }

    @Override
    public boolean equals(Object obj) {

        Employee other = (Employee) obj;
        if (id == other.id && (name.equals(other.name)))
            return true;
        return false;

        //return true;
    }


}
