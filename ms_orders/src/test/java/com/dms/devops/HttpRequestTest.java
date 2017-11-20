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
    public void basicTestGetRequest() throws Exception {
        // Verify that it is possible to make a simple GET request
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/pedidorest/test", String.class)).contains("OK");
        
        System.out.println("");
        System.out.println(">>>>>>> MS_Orders: HttpRequestTest: OK. We were able to make a simple GET request.");
        System.out.println("");
    }

}
