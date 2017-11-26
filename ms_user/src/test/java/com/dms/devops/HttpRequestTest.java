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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.dms.devops.commons.Messages;
import com.dms.devops.commons.UrlBuilder;

import com.dms.devops.rest.ClienteRestService;
import com.dms.devops.domain.cliente.Cliente;


@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    private static final Logger logger = LogManager.getLogger(ClienteRestService.class.getName());

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    private HttpHeaders headers = new HttpHeaders();

    
    @Test
    public void test1AddCliente() throws Exception {
        logger.info(Messages.MSG_TEST_ADD_CLIENTE_OPENING);

        // Build a new Cliente
        Cliente cliente = new Cliente();
        cliente.setId(7);
        cliente.setNome("Cliente 7");
        cliente.setEmail("curstomer7@email.com");

        // Create and run the POST request 
        UrlBuilder urlBuilder = new UrlBuilder(port);
        HttpEntity<Cliente> entity = new HttpEntity<Cliente>(cliente, headers);

		ResponseEntity<String> response = restTemplate.exchange(
            urlBuilder.getAddClienteUrl(), 
            HttpMethod.POST,
            entity, 
            String.class
        );

        // Verify the response
        String responseMsg = response.getBody();
        
        // Pedido object contains the same client and order ids?
        assertTrue(responseMsg.equals(Messages.SIMPLE_RESPONSE_CLIENTE_ADDED));
        
        // Test succeeded
        logger.info(Messages.MSG_TEST_ADD_CLIENTE_CLOSURE);
    }

    @Test
    public void test2GetClientes() throws Exception {
        logger.info(Messages.MSG_TEST_GET_CLIENTES_OPENING);

        // Create and run the PUT request 
        UrlBuilder urlBuilder = new UrlBuilder(port);

        ResponseEntity<ArrayList<Cliente>> response = restTemplate.exchange(
            urlBuilder.getClientesUrl(), 
            HttpMethod.GET, 
            null, 
            new ParameterizedTypeReference<ArrayList<Cliente>>(){}
        );

        // Verify the response
        ArrayList<Cliente> clientes = response.getBody();

        // Was the status of the order changed to paid?
        assertTrue(clientes.size() > 0);
        
        // Test succeeded
        logger.info(Messages.MSG_TEST_GET_CLIENTES_CLOSURE);
    }

    @Test
    public void test3MergeCliente() throws Exception {
        logger.info(Messages.MSG_TEST_MERGE_CLIENTE_OPENING);

        // Build a new Cliente
        long CLIENT_ID = 7;
        String UPDATED_CLIENTE_NAME = "Cliente Sete";
        String UPDATED_CLIENTE_EMAIL = "curstomerSete@email.com";

        Cliente cliente = new Cliente();
        cliente.setId(7);
        cliente.setNome(UPDATED_CLIENTE_NAME);
        cliente.setEmail(UPDATED_CLIENTE_EMAIL);

        // Create and run the GET request 
        UrlBuilder urlBuilder = new UrlBuilder(port);
        HttpEntity<Cliente> entity = new HttpEntity<Cliente>(cliente, headers);

        ResponseEntity<Cliente> response = this.restTemplate.exchange(
            urlBuilder.getMergeClienteUrl(),
            HttpMethod.PUT,
            entity,
            Cliente.class
        );

        // Verify the response        
        Cliente clienteResponse = response.getBody();

        // Is there at least one Pedido?
        assertTrue(clienteResponse.getId() == CLIENT_ID);
        assertTrue(clienteResponse.getNome().equals(UPDATED_CLIENTE_NAME));
        assertTrue(clienteResponse.getEmail().equals(UPDATED_CLIENTE_EMAIL));

        // Test succeeded
        logger.info(Messages.MSG_TEST_MERGE_CLIENTE_CLOSURE);
    }

    @Test
    public void test4GetCliente() throws Exception {
        logger.info(Messages.MSG_TEST_GET_CLIENTE_OPENING);

        // Set the operation params
        long CLIENT_ID = 7;
        
        // Create and run the PUT request 
        UrlBuilder urlBuilder = new UrlBuilder(port);

        ResponseEntity<Cliente> response = restTemplate.exchange(
            urlBuilder.getClienteUrl(CLIENT_ID), 
            HttpMethod.GET, 
            null, 
            Cliente.class
        );

        // Verify the response
        Cliente cliente = response.getBody();

        // Was the status of the order changed to paid?
        assertTrue(cliente.getId() == CLIENT_ID);
        
        // Test succeeded
        logger.info(Messages.MSG_TEST_GET_CLIENTE_CLOSURE);
    }

    @Test
    public void test5DeleteCliente() throws Exception {
        logger.info(Messages.MSG_TEST_DELETE_CLIENTE_OPENING);

        // Build the search params
        long CLIENT_ID = 7;

        // Create and run the GET request 
        UrlBuilder urlBuilder = new UrlBuilder(port);

        ResponseEntity<Map<Long, Cliente>> response = this.restTemplate.exchange(
            urlBuilder.getDeleteClienteByIdUrl(CLIENT_ID),
            HttpMethod.DELETE,
            null,
            new ParameterizedTypeReference<Map<Long, Cliente>>(){}
        );

        // Verify the response        
        Map<Long, Cliente> clientes = response.getBody();
        assertTrue(clientes.get(CLIENT_ID) == null);

        // Test succeeded
        logger.info(Messages.MSG_TEST_DELETE_CLIENTE_CLOSURE);
    }

}
