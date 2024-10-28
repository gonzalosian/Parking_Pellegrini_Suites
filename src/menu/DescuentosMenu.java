package menu;

import java.util.Scanner;

import context.ApplicationContext;
import controller.DescuentosPorHoraController;
import manager.SessionManager;

public class DescuentosMenu {
	private final ApplicationContext context;
	private final DescuentosPorHoraController descuentosController;
	
	public DescuentosMenu(ApplicationContext context) {
        this.context = context;
        this.descuentosController = context.getBean(DescuentosPorHoraController.class);
    }
	
	public void mostrar()
	{
		try (Scanner scanner = new Scanner(System.in)) {
			int opcion = 0;

			do {
				System.out.println("");
				System.out.println("----- Menú de Descuentos -----");
			    System.out.println("1. Registrar un Descuento");
			    System.out.println("2. Eliminar un Descuento");
			    System.out.println("3. Modificar un Descuento");
			    System.out.println("4. Consultar un Descuento");
			    System.out.println("5. Listar Descuentos");
			    System.out.println("6. Volver al Menu de Usuario");
			    System.out.println("7. Logout");
			    System.out.print("Seleccione una opción: ");
			    
			    if(scanner.hasNextInt()) {
			    	opcion = scanner.nextInt();
			    	
			    	switch (opcion) {
			    	case 1:
			    		registrarDescuento();
			    		break;
			    	case 2:
			    		eliminarDescuento();
			    		break;
			    	case 3:
			    		modificarDescuento();
			    		break;
			    	case 4:
			    		consultarDescuento();
			    		break;
			    	case 5:
			    		listarDescuentos();
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
	
	private void registrarDescuento() {
		try (Scanner scanner = new Scanner(System.in)){
			System.out.print("Ingrese la Cantidad de Horas: ");
			int cantidadHoras = scanner.nextInt();
			System.out.print("Ingrese el Porcentaje de Descuento: ");
			int porcentajeDescuento = scanner.nextInt();
			
			if(descuentosController.validarExistenciaDeDescuento(cantidadHoras)) {
				System.out.println("-----------------------------------------------------------------");
				System.out.println("Ya existe un registro de descuento con la misma cantidad de días.");
				System.out.println("-----------------------------------------------------------------");
			} else {
				descuentosController.crearDescuentoPorHora(cantidadHoras, porcentajeDescuento);
				System.out.println("-------------------------------");
				System.out.println("El Descuento se creó con éxito.");
				System.out.println("-------------------------------");
			}
			this.mostrar();
		}
	}
	
	private void eliminarDescuento() {
		System.out.println("En construcción");
	}
	
	private void modificarDescuento() {
		System.out.println("En construcción");
	}
	
	private void consultarDescuento() {
		System.out.println("En construcción");
	}
	
	private void listarDescuentos() {
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