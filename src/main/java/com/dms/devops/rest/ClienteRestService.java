package com.dms.devops.rest;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import com.dms.devops.domain.cliente.Cliente;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.dms.devops.commons.Messages;


@Controller
@Named
@Path("/clienterest/")
public class ClienteRestService {

	private static final Logger logger = LogManager.getLogger(ClienteRestService.class.getName());
	private static Map<Long, Cliente> clientes = new HashMap<Long, Cliente>();
	private static long contadorErroCaotico;

	static {
		Cliente cliente0 = new Cliente();
		cliente0.setId(0);
		cliente0.setNome("Cliente 0");
		cliente0.setEmail("customer0@gmail.com");

		Cliente cliente1 = new Cliente();
		cliente1.setId(1);
		cliente1.setNome("Cliente 1");
		cliente1.setEmail("customer1@gmail.com");

		Cliente cliente2 = new Cliente();
		cliente2.setId(2);
		cliente2.setNome("Cliente 2");
		cliente2.setEmail("customer2@gmail.com");

		Cliente cliente3 = new Cliente();
		cliente3.setId(3);
		cliente3.setNome("Cliente 3");
		cliente3.setEmail("customer3@gmail.com");

		Cliente cliente4 = new Cliente();
		cliente4.setId(4);
		cliente4.setNome("Cliente 4");
		cliente4.setEmail("customer4@gmail.com");

		Cliente cliente5 = new Cliente();
		cliente5.setId(5);
		cliente5.setNome("Cliente 5");
		cliente5.setEmail("customer5@gmail.com");

		ClienteRestService.clientes.put(cliente0.getId(), cliente0);
		ClienteRestService.clientes.put(cliente1.getId(), cliente1);
		ClienteRestService.clientes.put(cliente2.getId(), cliente2);
		ClienteRestService.clientes.put(cliente3.getId(), cliente3);
		ClienteRestService.clientes.put(cliente4.getId(), cliente4);
		ClienteRestService.clientes.put(cliente5.getId(), cliente5);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String addCliente(Cliente cliente) {
		logger.info(Messages.MSG_ADD_CLIENTE);

		ClienteRestService.clientes.put(cliente.getId(), cliente);

		logger.info("\n\n> O cliente " + cliente.getId() + " foi inserido!\n");

		return Messages.SIMPLE_RESPONSE_CLIENTE_ADDED;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Cliente mergeCliente(Cliente cliente) {
		logger.info(Messages.MSG_MERGE_CLIENTE);

		// Lança um erro de tempos em tesmpos
		contadorErroCaotico++;

		if ((contadorErroCaotico) % 7 == 0) {
			throw new RuntimeException(Messages.MSG_ERROR_CRAZY_ERROR_OCURRED);
		}

		// Atualiza Cliente
		Cliente temp = ClienteRestService.clientes.get(cliente.getId());
		temp.setNome(cliente.getNome());
		temp.setEmail(cliente.getEmail());
		ClienteRestService.clientes.put(cliente.getId(), temp);

		logger.info("\n\n> O cliente " + cliente.getId() + " foi alterado!\n");

		return temp;
	}

	@DELETE
	@Path("cliente/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<Long, Cliente> deleteCliente(@PathParam("id") long id) {
		logger.info(Messages.MSG_DELETE_CLIENTE);

		ClienteRestService.clientes.remove(id);

		logger.info("\n\n> O cliente " + id + " foi excluido!\n");

		return ClienteRestService.clientes;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Cliente> getClientes() {
		logger.info(Messages.MSG_GET_CLIENTES);

		logger.info("\n\n> Foram buscados " + ClienteRestService.clientes.values().size() + " clientes\n");

		return ClienteRestService.clientes.values();
	}

	@GET
	@Path("cliente/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Cliente getCliente(@PathParam("id") long id) {
		logger.info(Messages.MSG_GET_CLIENTE);

		// Encontra um Cliente pelo seu id e o retorna
		Cliente cli = null;

		for (Cliente c : ClienteRestService.clientes.values()) {

			if (c.getId() == id) {
				cli = c;
			}
		}

		logger.info("\n\n> O cliente " + cli.getNome() + " foi buscado\n");

		return cli;
	}

}
