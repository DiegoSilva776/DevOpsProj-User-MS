package com.dms.devops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Call;
import okhttp3.FormBody;

import java.util.ArrayList;

import com.dms.devops.commons.Messages;
import com.dms.devops.commons.UrlBuilder;

import com.dms.devops.rest.PedidoRestService;
import com.dms.devops.dto.pedido.ItemPedidoDTO;
import com.dms.devops.domain.pedido.ItemPedido;
import com.dms.devops.domain.pedido.Pedido;


@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    private static final Logger logger = LogManager.getLogger(PedidoRestService.class.getName());

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    private HttpHeaders headers = new HttpHeaders();

    @Test
    public void testAddPedido() throws Exception {
        logger.info("\n\n>>> MS_Orders: HttpRequestTest: testAddPedido:\n");

        // Build a new Pedido
        long ORDER_ID = 0;
        long CLIENT_ID = 0;
        long PRODUCT_ID = 0;
        long QTD = 10;
        
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setIdProduto(PRODUCT_ID);
        itemPedido.setQuantidade(QTD);

        ItemPedidoDTO requestAddPedido = new ItemPedidoDTO();
        requestAddPedido.setIdPedido(ORDER_ID);
        requestAddPedido.setIdCliente(CLIENT_ID);
        requestAddPedido.setItem(itemPedido);

        // Create and run the POST request 
        UrlBuilder urlBuilder = new UrlBuilder(port);
        HttpEntity<ItemPedidoDTO> entity = new HttpEntity<ItemPedidoDTO>(requestAddPedido, headers);
		ResponseEntity<Pedido> response = restTemplate.exchange(
            urlBuilder.getAddPedidoUrl(), 
            HttpMethod.POST, 
            entity, 
            Pedido.class
        );

        // Verify the response
        Pedido newPedido = response.getBody();
        
        // Pedido object contains the same client and order ids?
        assertThat(newPedido.getId() == ORDER_ID);
        assertThat(newPedido.getIdCliente() == CLIENT_ID);
        
        // Pedido object contains the new item?
        boolean hasSameItem = false;

        for(int i = 0; i < newPedido.getItems().size(); i ++) {
            ItemPedido item = newPedido.getItems().get(i);

            if (item.getIdProduto() == PRODUCT_ID && item.getQuantidade() == QTD) {
                hasSameItem = true;
                break;
            }
        }

        assertThat(hasSameItem);
        
        // Test succeeded
        logger.info("\n\n>>> MS_Orders: HttpRequestTest: ./testAddPedido:\n OK. We were able to create a Pedido.\n");
    }

    @Test
    public void testGetPedidos() throws Exception {
        logger.info("\n\n>>> MS_Orders: HttpRequestTest: testGetPedidos:\n");

        // Create and run the GET request 
        UrlBuilder urlBuilder = new UrlBuilder(port);
        ResponseEntity<ArrayList<Pedido>> response = this.restTemplate.exchange(
            urlBuilder.getPedidosUrl(),
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<ArrayList<Pedido>>(){}
        );

        // Verify the response        
        ArrayList<Pedido> pedidos = response.getBody();
        logger.info("Nós encontramos " + String.valueOf(pedidos.size()) + " Pedidos");
        
        // Is there at least one Pedido?
        assertThat(pedidos.size() > 0);

        // Test succeeded
        logger.info("\n\n>>> MS_Orders: HttpRequestTest: ./testGetPedidos:\n OK. We were able to make get the Pedidos.\n");
    }

}
