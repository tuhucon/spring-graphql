package com.example.grapql;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.server.WebGraphQlHandler;
import org.springframework.graphql.test.tester.WebGraphQlTester;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class PetWebGraphqlTest {

    @Autowired
    WebGraphQlHandler webGraphQlHandler;

    WebGraphQlTester webGraphQlTester;

    @BeforeEach
    public void setup() {
        webGraphQlTester = WebGraphQlTester.create(webGraphQlHandler);
    }

    @Test
    public void test1() {
        webGraphQlTester.document("""
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
    public void test2() {
        var response = webGraphQlTester.document("""
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
