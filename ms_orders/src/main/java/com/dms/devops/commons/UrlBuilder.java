package com.dms.devops.commons;

public class UrlBuilder {

    final String BASE_URL = "http://localhost:";

    int port = -1;


    public UrlBuilder(int port) {
        this.port = port;
    }

    public String getAddPedidoUrl() {
        return this.BASE_URL + this.port + "/pedidorest/item/adiciona/";
    }

    public String getRemovePedidoUrl() {
        return this.BASE_URL + this.port + "/pedidorest/item/remove/";
    }

    public String getUpdatePedidoUrl(long pedidoId) {
        return this.BASE_URL + this.port + "/pedidorest/pedido/" + String.valueOf(pedidoId);
    }

    public String getPedidosUrl() {
        return this.BASE_URL + this.port + "/pedidorest/";
    }

    public String getPedidoByCliente(long clientId) {
        return this.BASE_URL + this.port + "/pedidorest/pedido/" + String.valueOf(clientId);
    }

}
