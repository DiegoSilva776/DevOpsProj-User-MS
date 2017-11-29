package com.dms.devops.rest;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.net.URI;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dms.devops.commons.Messages;
import com.dms.devops.commons.UrlBuilder;

import com.dms.devops.domain.cliente.Cliente;
import com.dms.devops.domain.pedido.Pedido;
import com.dms.devops.domain.pedido.StatusPedido;
import com.dms.devops.dto.pedido.ItemPedidoDTO;

@Controller
@Named
@Path("/pedidorest/")
public class PedidoRestService {
 
	private static List<Pedido> pedidosMock = new ArrayList<Pedido>();
	private static final Logger logger = LogManager.getLogger(PedidoRestService.class.getName());
	private static long contadorErroCaotico;


	@POST
	@Path("item/adiciona")
	@Consumes(MediaType.APPLICATION_JSON)
	public Pedido adicionaItemPedido(ItemPedidoDTO item) {
		logger.info(Messages.MSG_CLIENT_ADDED_ITEM_TO_ORDER);

		// Simula um erro de tempos em tempos
		contadorErroCaotico++;

		if ((contadorErroCaotico) % 7 == 0) {
			throw new RuntimeException(Messages.MSG_ERROR_CRAZY_ERROR_OCURRED);
		}

		// Se o pedido já existe, adiciona o novo item
		boolean pedidoNovo = true;
			
		for (Pedido pedido : PedidoRestService.pedidosMock) {

			if (pedido.getId() == item.getIdPedido()) {
				pedido.getItems().add(item.getItem());
				pedidoNovo = false;
			}
		}

		// Cria um novo pedido com o item, se o pedido ainda não existir
		Pedido pedido = new Pedido();

		if (pedidoNovo) {
			pedido.setId(item.getIdPedido());
			pedido.setDataPedido(new Date());
			pedido.setIdCliente(item.getIdCliente());
			pedido.getItems().add(item.getItem());
			pedido.setStatus(StatusPedido.ABERTO);

			PedidoRestService.pedidosMock.add(pedido);
		}

		logger.info("\n\n>O cliente " + item.getIdCliente() +
					" adicionou o produto " + item.getItem().getIdProduto() +
					" ao pedido " + item.getIdPedido() + "\n");

		return pedido;
	}

	/**
	 * Verify the existence of the client by making a request to an endpoint of the ms_users micro service
	 */
	private Boolean verifyClient(long clientId) {
		boolean clientVerified = true;

		try {
			RestTemplate restTemplate = new RestTemplate();
			UrlBuilder urlBuilder = new UrlBuilder();

			ResponseEntity<Cliente> response = restTemplate.exchange(
				urlBuilder.getUserByIdUrl(clientId),
				HttpMethod.GET,
				null,
				Cliente.class
			);

			// Verify the response
			Cliente cliente = response.getBody();

			// Verify if the given clientId is ok in the ms_user micro service
			if (cliente != null) {

				if (cliente.getId() != clientId) {
					clientVerified = false;
				}
			} else {
				clientVerified = false;
			}
		} catch(Exception e) {
			clientVerified = false;

			logger.info(e.getMessage());
		}

		return false;
	}

	@POST
	@Path("item/remove")
	@Consumes(MediaType.APPLICATION_JSON)
	public Pedido removeItemPedido(ItemPedidoDTO item) {
		logger.info(Messages.MSG_CLIENT_REMOVED_ITEM_FROM_ORDER + item.getIdPedido());

		// Se existe um Pedido com o id do Pedido passado na request, 
		// remove o Item que também foi passado na request
		long idCliente = 0;
		Pedido updatedPedido = null;

		for (Pedido pedido : PedidoRestService.pedidosMock) {

			if (pedido.getId() == item.getIdPedido()) {
				pedido.getItems().remove(item.getItem());

				idCliente = pedido.getIdCliente();
				updatedPedido = pedido;
			}
		}

		logger.info("\n\n>O cliente " + idCliente +
		            " removeu o produto " + item.getItem().getIdProduto() +
					" do pedido " + item.getIdPedido() + "\n");

		return updatedPedido;
	}

	@PUT
	@Path("pedido/{idPedido}")
	public Pedido pagaPedido(@PathParam("idPedido") long idPedido) {
		logger.info(Messages.MSG_CLIENT_HAS_PAID_ORDER);

		// Se existe um Pedido com o id do Pedido passado na request, 
		// altera o seus status para CONCLUIDO
		Pedido upatedPedido = null;

		for (Pedido pedido : PedidoRestService.pedidosMock) {

			if (pedido.getId() == idPedido) {
				pedido.setStatus(StatusPedido.CONCLUIDO);

				upatedPedido = pedido;
			}
		}

		logger.info("\n\n>Pedido " + idPedido + " efetivado\n");

		return upatedPedido;
	}

	@DELETE
	@Path("pedido/{idPedido}")
	public Pedido cancelaPedido(@PathParam("idPedido") long idPedido) {
		logger.info(Messages.MSG_CLIENT_HAS_CANCELED_ORDER);

		// Se existe um Pedido com o id do Pedido passado na request, 
		// altera o seus status para CANCELADO
		Pedido upatedPedido = null;

		for (Pedido pedido : PedidoRestService.pedidosMock) {

			if (pedido.getId() == idPedido) {
				pedido.setStatus(StatusPedido.CANCELADO);

				upatedPedido = pedido;
			}
		}

		logger.info("\n\n>Pedido " + idPedido + " cancelado\n");

		return upatedPedido;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pedido> buscarPedidos() {
		logger.info(Messages.MSG_SEARCHED_ORDERS);
		
		return PedidoRestService.pedidosMock;
	}

	@GET
	@Path("pedido/{idCliente}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pedido> buscarPedidosPorCliente(@PathParam("idCliente") long idCliente) {
		logger.info(Messages.MSG_SEARCHED_ORDER_BY_CLIENT + idCliente);

		List<Pedido> pedidos = new ArrayList<Pedido>();
		
		for (Pedido pedido : PedidoRestService.pedidosMock) {
			
			if (pedido.getIdCliente() == idCliente) {
				pedidos.add(pedido);
			}
		}

		logger.info("\n\n>Cliente " + idCliente + " possui " + pedidos.size() + " pedidos\n");
		
		return pedidos;
	}

}
