package com.example.grapql;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PetTest {

    @Test
    public void test() {
        Pet pet1 = new Pet("1", "tu hu", "red", "1");
        Pet pet2 = pet1.withId("2");
        assertThat(pet1.name()).isEqualTo(pet2.name());
        assertThat(pet1.color()).isEqualTo(pet2.color());
        assertThat(pet1.ownerID()).isEqualTo(pet2.ownerID());
    }

}
