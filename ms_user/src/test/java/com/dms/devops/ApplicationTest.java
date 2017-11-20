package com.dms.devops;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    //private ClienteRestService clientService = new ClienteRestService();

    @Test
    public void contextLoads() throws Exception {
        System.out.println(">>>>>>>>> Initialized ClienteService test class.");
    }

}
