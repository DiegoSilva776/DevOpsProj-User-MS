package com.dms.devops.commons;

public class Messages {
    
    // PedidoRestService method tags
    public static String MSG_CLIENT_ADDED_ITEM_TO_ORDER = "\n\n>PedidoRestService: adicionaItemPedido: \n";
    public static String MSG_CLIENT_REMOVED_ITEM_FROM_ORDER = "\n\n>PedidoRestService: removeItemPedido: \n";
    public static String MSG_CLIENT_HAS_PAID_ORDER = "\n\n>PedidoRestService: pagaPedido: \n";
    public static String MSG_CLIENT_HAS_CANCELED_ORDER = "\n\n>PedidoRestService: cancelaPedido: \n";
    public static String MSG_SEARCHED_ORDERS = "\n\n>PedidoRestService: buscarPedidos: \n";
    public static String MSG_SEARCHED_ORDER_BY_CLIENT = "\n\n>PedidoRestService: buscarPedidosPorCliente: \n";

    // PedidoRestService HttpRequestTest method tags
    public static String MSG_TEST_ADD_PEDIDO_OPENING = "\n\n>>> MS_Orders: HttpRequestTest: testAddPedido:\n";
    public static String MSG_TEST_ADD_PEDIDO_CLOSURE = "\n\n>>> MS_Orders: HttpRequestTest: ./testAddPedido:\n OK. We were able to create a Pedido.\n";
    public static String MSG_TEST_GET_ALL_PEDIDOS_OPENING = "\n\n>>> MS_Orders: HttpRequestTest: testGetPedidos:\n";
    public static String MSG_TEST_GET_ALL_PEDIDOS_CLOSURE = "\n\n>>> MS_Orders: HttpRequestTest: ./testGetPedidos:\n OK. We were able to get a list of Pedidos.\n";
    public static String MSG_TEST_GET_PEDIDO_BY_CLIENT_OPENING = "\n\n>>> MS_Orders: HttpRequestTest: testGetPedidoByCliente:\n";
    public static String MSG_TEST_GET_PEDIDO_BY_CLIENT_CLOSURE = "\n\n>>> MS_Orders: HttpRequestTest: ./testGetPedidoByCliente:\n OK. We got the Cliente's Pedidos.\n";
    public static String MSG_TEST_PAY_PEDIDO_OPENING = "\n\n>>> MS_Orders: HttpRequestTest: testPayPedido\n";
    public static String MSG_TEST_PAY_PEDIDO_CLOSURE = "\n\n>>> MS_Orders: HttpRequestTest: ./testPayPedido\n OK. The status of the Pedido was updated to CONCLUIDO.";
    public static String MSG_TEST_CANCEL_PEDIDO_OPENING = "\n\n>>> MS_Orders: HttpRequestTest: testCancelPedido\n";
    public static String MSG_TEST_CANCEL_PEDIDO_CLOSURE = "\n\n>>> MS_Orders: HttpRequestTest: ./testCancelPedido\n OK. The status of the Pedido was updated to CANCELADO.";
    public static String MSG_TEST_REMOVE_ITEM_PEDIDO_OPENING = "\n\n>>> MS_Orders: HttpRequestTest: testRemoveItemPedido:\n";
    public static String MSG_TEST_REMOVE_ITEM_PEDIDO_CLOSURE = "\n\n>>> MS_Orders: HttpRequestTest: ./testRemoveItemPedido:\n OK. We were able to remove an item from the Pedido.\n";
    
    // Errors
    public static String MSG_ERROR_CRAZY_ERROR_OCURRED = "\n\n>Ocorreu um erro lokos!\n";

}
