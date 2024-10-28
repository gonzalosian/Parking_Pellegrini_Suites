package main;

import context.ApplicationContext;
import menu.MainMenu;

public class Main {

	public static void main(String[] args) {
		ApplicationContext context = new ApplicationContext();
		
		MainMenu mainMenu = new MainMenu(context);
        mainMenu.mostrar();
		
		// TODO Esto fue implementado y funcionaba bien, pero se utilizará ApplicationContext
		/*
		DatabaseConnection dbConnection = new DatabaseConnection();
		Connection connection = dbConnection.getConnection();
		
		GenericRepository<DescuentosPorHora, Integer> repo = new GenericRepository<>(connection, DescuentosPorHora.class);
		
		DescuentosPorHoraManager descuentosPorHoraManager = new DescuentosPorHoraManager(repo);
		DescuentosPorHoraController descuentosPorHoraController = new DescuentosPorHoraController(descuentosPorHoraManager);
		
		// CREAR
		descuentosPorHoraController.crearDescuentoPorHora(2, 1);
		
		// CONSULTAR TODOS
		List<DescuentoPorHoraDTO> descuentosPorHora = descuentosPorHoraController.mostrarDescuentosPorHora();
		for (DescuentoPorHoraDTO descuentoPorHoraDTO : descuentosPorHora) {
			System.out.println(descuentoPorHoraDTO.getId()+" - "+descuentoPorHoraDTO.getCantidadDias()+" - "+descuentoPorHoraDTO.getPorcentajeDescuento());
		};
		
		// ELIMINAR
		descuentosPorHoraController.eliminarDescuentoPorHora(49);
		
		// CONSULTAR ID
		DescuentoPorHoraDTO descuentoPorHora = descuentosPorHoraController.obtenerDescuentoPorId(1);
		System.out.println(descuentoPorHora.getId()+" - "+descuentoPorHora.getCantidadDias()+" - "+descuentoPorHora.getPorcentajeDescuento());
		
		// EDITAR
		descuentosPorHoraController.actualizarDescuentoPorHora(new DescuentoPorHoraDTO(1, 33, 11));
		*/

	}

}
