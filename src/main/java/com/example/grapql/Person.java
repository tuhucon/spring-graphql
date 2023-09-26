package com.example.grapql;

public record Person(String id, String name, Integer age) {
    public Person withId(String id ) {
        return new Person(id, this.name, this.age);
    }
}
