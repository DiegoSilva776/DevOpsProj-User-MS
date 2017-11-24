package com.dms.devops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

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

import com.dms.devops.rest.PedidoRestService;
import com.dms.devops.dto.pedido.ItemPedidoDTO;
import com.dms.devops.domain.pedido.ItemPedido;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    private HttpHeaders headers = new HttpHeaders();

    private static final Logger logger = LogManager.getLogger(PedidoRestService.class.getName());

    /**
     * Verify if is possible to make a simple GET request
     */
    @Test
    public void testMakeSimpleGetRequest() throws Exception {
        String TEST_ENDPOINT_URL = "http://localhost:" + port + "/pedidorest/test/";

        // Run the request
        String response = this.restTemplate.getForObject(TEST_ENDPOINT_URL, String.class);
        assertThat(response).contains("OK - PedidoRestService: GET request to 'test/' endpoint");
        
        // Test succeeded
        logger.info("\n>>> MS_Orders: basicTestGetRequest: OK. We were able to make a simple GET request.\n");
    }

    /**
     * Try to add a Pedido to the list of pedidos
     */
    @Test
    public void testAddPedido() throws Exception {
        // Build the request Add request
        String ADD_PEDIDO_ENDPOINT_URL = "http://localhost:" + port + "/pedidorest/item/adiciona";

        // Run the POST request
        ItemPedidoDTO requestAddPedido = new ItemPedidoDTO();
        ItemPedido itemPedido = new ItemPedido();

        itemPedido.setIdProduto(0);
        itemPedido.setQuantidade(10);

        requestAddPedido.setIdPedido(0);
        requestAddPedido.setIdCliente(0);
        requestAddPedido.setItem(itemPedido);

		HttpEntity<ItemPedidoDTO> entity = new HttpEntity<ItemPedidoDTO>(requestAddPedido, headers);
		ResponseEntity<String> response = restTemplate.exchange(ADD_PEDIDO_ENDPOINT_URL, HttpMethod.POST, entity, String.class);
        assertThat(response.toString()).contains("adicionou o produto");

        // Test succeeded
        logger.info("\n>>> MS_Orders: testAddPedido: OK. We were able to create a Pedido and GET it.\n");
    }

    /**
     * Try to get an empty list or a list with N Pedido objects
     */
    @Test
    public void testGetPedidos() throws Exception {
        // Build the GET request
        String GET_PEDIDOS_ENDPOINT_URL = "http://localhost:" + port + "/pedidorest/";

        // Run the GET request
        String response = this.restTemplate.getForObject(GET_PEDIDOS_ENDPOINT_URL, String.class);
        assertThat("[");

        // Test succeeded
        logger.info("\n>>> MS_Orders: testGetPedidos: OK. We were able to make a simple GET request.\n");
    }

}
