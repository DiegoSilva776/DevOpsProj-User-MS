package com.dms.devops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void addOrder() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/clienterest/", String.class)).contains("customer1@gmail.com");

        System.out.println("");
        System.out.println(">>>>>>> MS_User: HttpRequestTest: OK. The list of clients is non-empty.");
        System.out.println("");
    }

}
