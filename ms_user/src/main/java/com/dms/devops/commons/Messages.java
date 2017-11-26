package com.dms.devops.commons;

public class Messages {
    
    // ClienteRestService method tags
    public static String MSG_ADD_CLIENTE = "\n\n>ClienteRestService: addCliente: \n";
    public static String MSG_MERGE_CLIENTE = "\n\n>ClienteRestService: mergeCliente: \n";
    public static String MSG_DELETE_CLIENTE = "\n\n>ClienteRestService: deleteCliente: \n";
    public static String MSG_GET_CLIENTES = "\n\n>ClienteRestService: getClientes: \n";
    public static String MSG_GET_CLIENTE = "\n\n>ClienteRestService: getCliente: \n";
    
    // ClienteRestService HttpRequestTest method tags
    public static String MSG_TEST_ADD_CLIENTE_OPENING = "\n\n>>> MS_users: HttpRequestTest: testAddCliente:\n";
    public static String MSG_TEST_ADD_CLIENTE_CLOSURE = "\n\n>>> MS_users: HttpRequestTest: ./testAddCliente:\n OK. We were able to create a Cliente.\n";
    public static String MSG_TEST_MERGE_CLIENTE_OPENING = "\n\n>>> MS_users: HttpRequestTest: testMergeCliente:\n";
    public static String MSG_TEST_MERGE_CLIENTE_CLOSURE = "\n\n>>> MS_users: HttpRequestTest: ./testMergeCliente:\n OK. We were able to update the selected Cliente.\n";
    public static String MSG_TEST_DELETE_CLIENTE_OPENING = "\n\n>>> MS_users: HttpRequestTest: testDeleteCliente:\n";
    public static String MSG_TEST_DELETE_CLIENTE_CLOSURE = "\n\n>>> MS_users: HttpRequestTest: ./testDeleteCliente:\n OK. We've deleted the Cliente.\n";
    public static String MSG_TEST_GET_CLIENTES_OPENING = "\n\n>>> MS_users: HttpRequestTest: testGetClientes\n";
    public static String MSG_TEST_GET_CLIENTES_CLOSURE = "\n\n>>> MS_users: HttpRequestTest: ./testGetClientes\n OK. We'ge got a list of Clientes.";
    public static String MSG_TEST_GET_CLIENTE_OPENING = "\n\n>>> MS_users: HttpRequestTest: testCancelPedido\n";
    public static String MSG_TEST_GET_CLIENTE_CLOSURE = "\n\n>>> MS_users: HttpRequestTest: ./testCancelPedido\n OK. We found the selectd Cliente.";

    // Success responses
    public static String SIMPLE_RESPONSE_CLIENTE_ADDED = "Cliente adicionado";

    // Errors
    public static String MSG_ERROR_CRAZY_ERROR_OCURRED = "\n\n>Ocorreu um erro lokos!\n";
    
}
