package com.example.grapql;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PetHttpGraphqlTest {

    @Autowired
    HttpGraphQlTester httpGraphQlTester;

    @Test
    public void test1() {
        httpGraphQlTester.document("""
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
                .expect(err -> err.getExtensions().get("classification").equals("500357"))
                .verify();
    }

    @Test
    public void test2() {
        var response = httpGraphQlTester.document("""
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
