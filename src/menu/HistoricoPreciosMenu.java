package menu;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import context.ApplicationContext;
import controller.HistoricoPreciosController;
import manager.SessionManager;

public class HistoricoPreciosMenu {
	private final ApplicationContext context;
	private final HistoricoPreciosController historicoPreciosController;
	
	public HistoricoPreciosMenu(ApplicationContext context) {
        this.context = context;
        this.historicoPreciosController = context.getBean(HistoricoPreciosController.class);
    }
	
	public void mostrar()
	{
		try (Scanner scanner = new Scanner(System.in)) {
			int opcion = 0;

			do {
				System.out.println("");
				System.out.println("----- Menú de Tarifas -----");
			    System.out.println("1. Registrar una Tarifa");
			    System.out.println("2. Eliminar una Tarifa");
			    System.out.println("3. Modificar una Tarifa");
			    System.out.println("4. Consultar una Tarifa");
			    System.out.println("5. Listar Tarifas");
			    System.out.println("6. Volver al Menu de Usuario");
			    System.out.println("7. Logout");
			    System.out.print("Seleccione una opción: ");
			    
			    if(scanner.hasNextInt()) {
			    	opcion = scanner.nextInt();
			    	
			    	switch (opcion) {
			    	case 1:
			    		registrarTarifa();
			    		break;
			    	case 2:
			    		eliminarTarifa();
			    		break;
			    	case 3:
			    		modificarTarifa();
			    		break;
			    	case 4:
			    		consultarTarifa();
			    		break;
			    	case 5:
			    		listarTarifas();
			    		break;
		    		case 6:
			    		mostrarUsuarioMenu();
			    		break;
			    	case 7:
			    		System.out.println("Saliendo...");
			    		SessionManager.getInstance().cerrarSesion();
			    		mostrarMainMenu();
			    		break;
			    	default:
			    		System.out.println("Opción no válida.");
			    		System.out.println("");
			    	}
			    } else {
			    	System.out.println("Entrada no válida. Por favor, ingrese un número.");
		            scanner.next(); // Consumir la entrada no válida
			    }
			} while (opcion != 7);
		}
	}
	
	private void registrarTarifa() {
		try (Scanner scanner = new Scanner(System.in)){
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Defino el formato de fecha
	        LocalDate fecha = null;
	        
	        while (fecha == null) {
	            System.out.print("Ingrese la Fecha de Vigencia en formato dd/MM/yyyy: ");
	            String fechaVigenciaInput = scanner.nextLine();

	            try {
	                fecha = LocalDate.parse(fechaVigenciaInput, formatter); // Intenta parsear la fecha
	                System.out.println("Fecha ingresada correctamente: " + fecha);
	            } catch (DateTimeParseException e) {
	                System.out.println("Formato de fecha inválido. Por favor, intente de nuevo.");
	            }
	        }
			
	        mostrarTiposDeVehiculos();
			System.out.print("Ingrese el ID del Tipo de Vehículo: ");
			int tipoVehiculoInput = scanner.nextInt();
			
			System.out.print("Ingrese el Precio por Hora: ");
			int precioPorHoraInput = scanner.nextInt();
			
			System.out.print("Ingrese el Precio por Día: ");
			int precioPorDiaInput = scanner.nextInt();
			
			System.out.print("Ingrese el Precio del Servicio de Lavado: ");
			int precioLavadoInput = scanner.nextInt();
			
			if(historicoPreciosController.validarExistenciaDeTarifas(tipoVehiculoInput, fecha)) {
				System.out.println("----------------------------------------------------------------------------------------");
				System.out.println("Ya existe un registro de Tarifas para el tipo de vehículo y fecha de vigencia ingresada.");
				System.out.println("----------------------------------------------------------------------------------------");
			} else {
				LocalDateTime fechaVigencia = fecha.atStartOfDay();
				LocalDateTime fechaHoraCarga = LocalDateTime.now();
				historicoPreciosController.crearHistoricoPrecios(fechaVigencia, fechaHoraCarga, SessionManager.getInstance().getUsuarioLogueado().getId(), 
						tipoVehiculoInput, precioPorHoraInput, precioPorDiaInput, precioLavadoInput);
				System.out.println("----------------------------");
				System.out.println("La Tarifa se creó con éxito.");
				System.out.println("----------------------------");
			}
			this.mostrar();
		}
	}
	
	private void mostrarTiposDeVehiculos() {
		ArrayList<String[]> tiposVehiculosArrayList = new ArrayList<>();
        tiposVehiculosArrayList.add(new String[]{"1", "Auto"});
        tiposVehiculosArrayList.add(new String[]{"2", "Camioneta"});
        tiposVehiculosArrayList.add(new String[]{"3", "Moto"});
        tiposVehiculosArrayList.add(new String[]{"4", "Camión ligero"});
        
        System.out.println("Lista de Tipos de Vehículos:");
        for (String[] tipo : tiposVehiculosArrayList) {
            System.out.println("ID: " + tipo[0] + " - Descripción: " + tipo[1]);
        }
	}
	
	private void eliminarTarifa() {
		System.out.println("En construcción");
	}
	
	private void modificarTarifa() {
		System.out.println("En construcción");
	}
	
	private void consultarTarifa() {
		System.out.println("En construcción");
	}
	
	private void listarTarifas() {
		System.out.println("En construcción");
	}
	
	private void mostrarMainMenu() {
		MainMenu mainMenu = new MainMenu(context);
		mainMenu.mostrar();
    }
	
	private void mostrarUsuarioMenu() {
		UsuarioMenu usuarioMenu = new UsuarioMenu(context);
		usuarioMenu.mostrar();
    }
}