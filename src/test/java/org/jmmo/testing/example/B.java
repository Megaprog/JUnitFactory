package org.jmmo.testing.example;

public class B extends A {
    private String secondName;

    public B(String firstName, String secondName) {
        super(firstName);
        this.secondName = secondName;
    }

    public String getSecondName() {
        return secondName;
    }
}
