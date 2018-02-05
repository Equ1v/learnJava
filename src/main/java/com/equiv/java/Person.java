package com.equiv.java;

import java.security.SecureRandom;

public class Person {
    private int age;
    private String name;

    public Person() {
        this.age = new SecureRandom().nextInt(100);

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnoprstuvwxyz";
        StringBuilder stringBuilder = new StringBuilder();
        int nameSize = new SecureRandom().nextInt(15);
        while (nameSize>0) {
            nameSize--;
            char c = characters.toCharArray()[new SecureRandom().nextInt(characters.length())];
            stringBuilder.append(c);
        }
        this.name = stringBuilder.toString();
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
