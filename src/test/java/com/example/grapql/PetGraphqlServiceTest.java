package com.example.grapql;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import static org.assertj.core.api.Assertions.*;

@GraphQlTest(PetController.class)
public class PetGraphqlServiceTest {

    @Autowired
    GraphQlTester graphQlTester;

    @Test
    public void pet1() {
        graphQlTester.document("""
                query {
                    pets {
                        id
                        name
                        ownerWithException {
                            id
                        }
                    }
                }
                """).execute()
                .errors()
                .expect(err -> err.getErrorType().toString().equals("500357"))
                .verify();
    }

    @Test
    public void pet2() {
        var response = graphQlTester.document("""
                query {
                    pets {
                        id
                        name
                    }
                }
                """).execute();
        assertThat(response.path("pets[0].id").entity(String.class).get())
                .isEqualTo("1");
    }
}
