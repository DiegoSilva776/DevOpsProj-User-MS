package com.dms.devops.commons;

public class UrlBuilder {

    final String BASE_URL = "http://localhost:";

    int port = -1;


    public UrlBuilder(int port) {
        this.port = port;
    }

    public String getPedidosUrl() {
        return this.BASE_URL + this.port + "/pedidorest/";
    }

    public String getAddPedidoUrl() {
        return this.BASE_URL + this.port + "/pedidorest/item/adiciona/";
    }

    public String getPedidoByCliente(long clientId) {
        return this.BASE_URL + this.port + "/pedidorest/pedido/" + String.valueOf(clientId);
    }

}

