package com.dms.devops.commons;

public class UrlBuilder {

    final String BASE_URL = "http://localhost:";

    int port = -1;


    public UrlBuilder(int port) {
        this.port = port;
    }

    // Cliente Micro Service endpoints
    public String getAddClienteUrl() {
        return this.BASE_URL + this.port + "/clienterest/";
    }

    public String getClientesUrl() {
        return this.BASE_URL + this.port + "/clienterest/";
    }

    public String getMergeClienteUrl() {
        return this.BASE_URL + this.port + "/clienterest/";
    }

    public String getClienteUrl(long clientId) {
        return this.BASE_URL + this.port + "/clienterest/cliente/" + clientId;
    }

    public String getDeleteClienteByIdUrl(long clientId) {
        return this.BASE_URL + this.port + "/clienterest/cliente/" + clientId;
    }

}
