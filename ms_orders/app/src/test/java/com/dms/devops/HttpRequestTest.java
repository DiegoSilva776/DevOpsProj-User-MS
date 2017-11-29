package com.dms.devops;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;
import static org.junit.Assert.assertTrue;

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

import java.util.ArrayList;

import com.dms.devops.commons.Messages;
import com.dms.devops.commons.UrlBuilder;

import com.dms.devops.rest.PedidoRestService;
import com.dms.devops.dto.pedido.ItemPedidoDTO;
import com.dms.devops.domain.pedido.ItemPedido;
import com.dms.devops.domain.pedido.Pedido;
import com.dms.devops.domain.pedido.StatusPedido;


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
    public void test1AddPedido() throws Exception {
        logger.info(Messages.MSG_TEST_ADD_PEDIDO_OPENING);

        // Build a new Pedido
        long PRODUCT_ID = 0;
        long QTD = 10;
        long ORDER_ID = 0;
        long CLIENT_ID = 0;

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
        assertTrue(newPedido.getId() == ORDER_ID);
        assertTrue(newPedido.getIdCliente() == CLIENT_ID);
        
        // Pedido object contains the new item?
        boolean hasSameItem = false;

        for(int i = 0; i < newPedido.getItems().size(); i ++) {
            ItemPedido item = newPedido.getItems().get(i);

            if (item.getIdProduto() == PRODUCT_ID && item.getQuantidade() == QTD) {
                hasSameItem = true;
                break;
            }
        }

        assertTrue(hasSameItem);
        
        // Test succeeded
        logger.info(Messages.MSG_TEST_ADD_PEDIDO_CLOSURE);
    }

    @Test
    public void test2GetPedidos() throws Exception {
        logger.info(Messages.MSG_TEST_GET_ALL_PEDIDOS_OPENING);

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

        // Is there at least one Pedido?
        assertTrue(pedidos.size() > 0);

        // Test succeeded
        logger.info(Messages.MSG_TEST_GET_ALL_PEDIDOS_CLOSURE);
    }

    @Test
    public void test3GetPedidoByCliente() throws Exception {
        logger.info(Messages.MSG_TEST_GET_PEDIDO_BY_CLIENT_OPENING);

        // Build the search params
        long CLIENT_ID = 0;    

        // Create and run the GET request 
        UrlBuilder urlBuilder = new UrlBuilder(port);

        ResponseEntity<ArrayList<Pedido>> response = this.restTemplate.exchange(
            urlBuilder.getPedidoByClienteUrl(CLIENT_ID),
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<ArrayList<Pedido>>(){}
        );

        // Verify the response        
        ArrayList<Pedido> pedidos = response.getBody();

        // Is there at least one Pedido?
        assertTrue(pedidos.size() > 0);

        // The Pedidos really belong to the Cliente?
        boolean pedidosBelongToCliente = true;

        for (int i = 0; i < pedidos.size(); i++) {
            Pedido pedido = pedidos.get(i);

            if (pedido.getIdCliente() != CLIENT_ID) {
                pedidosBelongToCliente = false;
                break;
            }
        }

        assertTrue(pedidosBelongToCliente);

        // Test succeeded
        logger.info(Messages.MSG_TEST_GET_PEDIDO_BY_CLIENT_CLOSURE);
    }

    @Test
    public void test4PayPedido() throws Exception {
        logger.info(Messages.MSG_TEST_PAY_PEDIDO_OPENING);

        // Set the operation params
        long ORDER_ID = 0;
        
        // Create and run the PUT request 
        UrlBuilder urlBuilder = new UrlBuilder(port);

        ResponseEntity<Pedido> response = restTemplate.exchange(
            urlBuilder.getUpdatePedidoUrl(ORDER_ID), 
            HttpMethod.PUT, 
            null, 
            Pedido.class
        );

        // Verify the response
        Pedido updatedPedido = response.getBody();

        // Was the status of the order changed to paid?
        assertTrue(updatedPedido.getStatus().equals(StatusPedido.CONCLUIDO));
        
        // Test succeeded
        logger.info(Messages.MSG_TEST_PAY_PEDIDO_CLOSURE);
    }

    @Test
    public void test5CancelPedido() throws Exception {
        logger.info(Messages.MSG_TEST_CANCEL_PEDIDO_OPENING);

        // Set the operation params
        long ORDER_ID = 0;
        
        // Create and run the PUT request 
        UrlBuilder urlBuilder = new UrlBuilder(port);

        ResponseEntity<Pedido> response = restTemplate.exchange(
            urlBuilder.getUpdatePedidoUrl(ORDER_ID), 
            HttpMethod.DELETE, 
            null, 
            Pedido.class
        );

        // Verify the response
        Pedido updatedPedido = response.getBody();

        // Was the status of the order changed to paid?
        assertTrue(updatedPedido.getStatus().equals(StatusPedido.CANCELADO));

        // Test succeeded
        logger.info(Messages.MSG_TEST_CANCEL_PEDIDO_CLOSURE);
    }

    @Test
    public void test6RemoveItemPedido() throws Exception {
        logger.info(Messages.MSG_TEST_REMOVE_ITEM_PEDIDO_OPENING);

        // Build the object that carries the values necessary to remove an item from a Pedido
        long PRODUCT_ID = 0;
        long QTD = 10;
        long ORDER_ID = 0;
        
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setIdProduto(PRODUCT_ID);
        itemPedido.setQuantidade(QTD);

        ItemPedidoDTO requestRemovePedido = new ItemPedidoDTO();
        requestRemovePedido.setIdPedido(ORDER_ID);
        requestRemovePedido.setItem(itemPedido);
        
        // Create and run the POST request 
        UrlBuilder urlBuilder = new UrlBuilder(port);
        HttpEntity<ItemPedidoDTO> entity = new HttpEntity<ItemPedidoDTO>(requestRemovePedido, headers);
        
		ResponseEntity<Pedido> response = restTemplate.exchange(
            urlBuilder.getRemovePedidoUrl(), 
            HttpMethod.POST, 
            entity, 
            Pedido.class
        );

        // Verify the response
        Pedido updatedPedido = response.getBody();
        
        // Pedido object doesn't contain the removed item?
        if (updatedPedido.getItems() != null) {

            if (updatedPedido.getItems().size() > 0) {
                boolean doesntHaveRemovedItem = true;

                for (int i = 0; i < updatedPedido.getItems().size(); i ++) {
                    ItemPedido item = updatedPedido.getItems().get(i);

                    if (item.getIdProduto() == itemPedido.getIdProduto() && 
                        item.getQuantidade() == itemPedido.getQuantidade()) {
                        doesntHaveRemovedItem = false;
                        break;
                    }
                }

                assertTrue(doesntHaveRemovedItem);
            }
        }
        
        // Test succeeded
        logger.info(Messages.MSG_TEST_REMOVE_ITEM_PEDIDO_CLOSURE);
    }

}
