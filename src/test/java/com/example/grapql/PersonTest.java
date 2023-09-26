package com.example.grapql;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PersonTest {

    @Test
    public void test() {
        Person p1 = new Person("1", "tu hu con", 30);
        Person p2 = p1.withId("2");
        assertThat(p1.age()).isEqualTo(p2.age());
        assertThat(p1.name()).isEqualTo(p2.name());
    }

}
