package menu;

import java.util.Scanner;

import context.ApplicationContext;
import manager.SessionManager;

public class UsuarioMenu {
	private final ApplicationContext context;

	public UsuarioMenu(ApplicationContext context) {
		this.context = context;
	}

	public void mostrar() {
		try {
			int opcion = 0;

			do {
				System.out.println("");
				System.out.printf("Bienvenido/a %s %s \n",
						SessionManager.getInstance().getUsuarioLogueado().getNombre(),
						SessionManager.getInstance().getUsuarioLogueado().getApellido());
				System.out.println("");
				System.out.println("----- Menú de Usuario -----");
				System.out.println("1. Movimientos Parking");
				System.out.println("2. Descuentos");
				System.out.println("3. Tarifas");
				System.out.println("4. Salir");
				System.out.print(">> Seleccione una opción: ");

				Scanner scanner = context.getScanner();

				if (scanner.hasNextInt()) {
					opcion = scanner.nextInt();

					switch (opcion) {
					case 1:
						mostrarCheckInMenu();
						break;
					case 2:
						mostrarDescuentosMenu();
						break;
					case 3:
						mostrarTarifasMenu();
						break;
					case 4:
						System.out.println("Saliendo...");
						SessionManager.getInstance().cerrarSesion();
						break;
					default:
						System.out.println("Opción no válida.");
						System.out.println("");
					}
				} else {
					System.out.println("Entrada no válida. Por favor, ingrese un número.");
					scanner.next(); // Consumir la entrada no válida
				}
			} while (opcion != 4);
		} catch (Exception e) {
			System.out.println("Error inesperado: " + e.getMessage());
//	        e.printStackTrace(); // Para desarrollo: Capturar cualquier excepción inesperada
		}
	}

	private void mostrarCheckInMenu() {
		ChekInMenu chekInMenu = new ChekInMenu(context);
		chekInMenu.mostrar();
	}

	private void mostrarDescuentosMenu() {
		DescuentosMenu descuentosMenu = new DescuentosMenu(context);
		descuentosMenu.mostrar();
	}

	private void mostrarTarifasMenu() {
		HistoricoPreciosMenu tarifasMenu = new HistoricoPreciosMenu(context);
		tarifasMenu.mostrar();
	}
}
