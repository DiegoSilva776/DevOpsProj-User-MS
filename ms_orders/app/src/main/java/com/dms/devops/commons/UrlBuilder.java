package com.dms.devops.commons;

public class UrlBuilder {

    final String BASE_URL = "http://localhost:";
    final String BASE_URL_USER_MS = "http://localhost:";

    final int PORT_USER_MS = 8082;

    int port = -1;


    public UrlBuilder() {}

    public UrlBuilder(int port) {
        this.port = port;
    }

    // Pedido Micro Service endpoints
    public String getAddPedidoUrl() {
        return this.BASE_URL + this.port + "/pedidorest/item/adiciona/";
    }

    public String getRemovePedidoUrl() {
        return this.BASE_URL + this.port + "/pedidorest/item/remove/";
    }

    public String getUpdatePedidoUrl(long pedidoId) {
        return this.BASE_URL + this.port + "/pedidorest/pedido/" + pedidoId;
    }

    public String getPedidosUrl() {
        return this.BASE_URL + this.port + "/pedidorest/";
    }

    public String getPedidoByClienteUrl(long clientId) {
        return this.BASE_URL + this.port + "/pedidorest/pedido/" + clientId;
    }

    // User Micro Service endpoints
    public String getUserByIdUrl(long clientId) {
        return this.BASE_URL_USER_MS + this.PORT_USER_MS + "/clienterest/cliente/" + clientId;
    }

}
