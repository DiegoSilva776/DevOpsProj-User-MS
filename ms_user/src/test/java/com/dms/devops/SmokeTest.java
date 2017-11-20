package com.dms.devops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dms.devops.rest.ClienteRestService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmokeTest {

    @Autowired
    private ClienteRestService clientService;

    @Test
    public void contexLoads() throws Exception {
        assertThat(clientService).isNotNull();

        System.out.println("");
        System.out.println(">>>>>>> SmokeTest: OK. The ClienteRestServe was AutoWired.");
        System.out.println("");
    }

}
