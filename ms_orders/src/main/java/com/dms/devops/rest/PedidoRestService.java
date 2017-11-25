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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dms.devops.commons.Messages;

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
		long idCliente = 0;
		boolean pedidoNovo = true;

		for (Pedido pedido : pedidosMock) {

			if (pedido.getId() == item.getIdPedido()) {
				pedido.getItems().add(item.getItem());

				idCliente = pedido.getIdCliente();
				pedidoNovo = false;
			}
		}

		// Cria um novo pedido com o item, se o pedido ainda não existir
		Pedido pedido = new Pedido();

		if (pedidoNovo) {
			idCliente = item.getIdCliente();
			pedido.setId(item.getIdPedido());
			pedido.setDataPedido(new Date());
			pedido.setIdCliente(item.getIdCliente());
			pedido.getItems().add(item.getItem());
			pedido.setStatus(StatusPedido.ABERTO);

			pedidosMock.add(pedido);
		}

		logger.info(" O cliente " + idCliente + 
					" adicionou o produto " + item.getItem().getIdProduto() +
					" ao pedido " + item.getIdPedido());

		return pedido;
	}

	@POST
	@Path("item/remove")
	@Consumes(MediaType.APPLICATION_JSON)
	public void removeItemPedido(ItemPedidoDTO item) {
		logger.info(Messages.MSG_CLIENT_REMOVED_ITEM_FROM_ORDER);

		long idCliente = 0;

		for (Pedido pedido : pedidosMock) {

			if (pedido.getId() == item.getIdPedido()) {
				pedido.getItems().remove(item.getItem());
				idCliente = pedido.getIdCliente();
			}
		}

		logger.info("pedido " + item.getIdPedido() + " do cliente " + idCliente + " removeu o produto "
				+ item.getItem().getIdProduto());
	}

	@PUT
	@Path("pedido/{idPedido}")
	public void pagaPedido(@PathParam("idPedido") long idPedido) {
		logger.info(Messages.MSG_CLIENT_HAS_PAID_ORDER);

		for (Pedido pedido : pedidosMock) {

			if (pedido.getId() == idPedido) {
				pedido.setStatus(StatusPedido.CONCLUIDO);
			}
		}

		logger.info("pedido " + idPedido + " efetivado");
	}

	@DELETE
	@Path("pedido/{idPedido}")
	public void cancelaPedido(@PathParam("idPedido") long idPedido) {
		logger.info(Messages.MSG_CLIENT_HAS_CANCELED_ORDER);

		for (Pedido pedido : pedidosMock) {

			if (pedido.getId() == idPedido) {
				pedido.setStatus(StatusPedido.CANCELADO); 
			}
		}

		logger.info("pedido " + idPedido + " cancelado");
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pedido> buscarPedidos() {
		logger.info(Messages.MSG_SEARCHED_ORDERS);
		
		return pedidosMock;
	}

	@GET
	@Path("pedido/{idCliente}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pedido> buscarPedidosPorCliente(@PathParam("idCliente") long idCliente) {
		logger.info(Messages.MSG_SEARCHED_ORDER_BY_CLIENT);

		List<Pedido> pedidos = new ArrayList<Pedido>();
		
		for (Pedido pedido : pedidosMock) {

			if (pedido.getIdCliente() == idCliente)
				pedidos.add(pedido);
		}

		logger.info("cliente " + idCliente + " possui " + pedidos.size() + " pedidos");
		
		return pedidos;
	}

}
