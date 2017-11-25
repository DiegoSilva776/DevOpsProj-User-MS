package com.dms.devops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.dms.devops.rest.PedidoRestService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmokeTest {

    @Autowired
    private PedidoRestService pedidoService;

    @Test
    public void contexLoads() throws Exception {
        assertThat(pedidoService).isNotNull();
        System.out.println("\n\n>>> MS_Orders: SmokeTest:\n OK. The PedidoRestServe was AutoWired.\n");
    }

}
