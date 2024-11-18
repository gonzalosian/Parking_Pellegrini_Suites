package menu;

import java.util.Scanner;

import context.ApplicationContext;
import manager.SessionManager;

public class MainMenu {
	private final ApplicationContext context;

	public MainMenu(ApplicationContext context) {
		this.context = context;
	}

	public void mostrar() {
		try {
			int opcion = 0;

			do {
				System.out.println("");
				System.out.println("Bienvenido a Parking Pellegrini Suites");
				System.out.println("");
				System.out.println("----- Menú Principal -----");
				System.out.println("1. Login (jperez - 123)");
				System.out.println("2. Salir");
				System.out.print("Seleccione una opción: ");

				Scanner scanner = context.getScanner();

				// Verificar si la entrada es un número entero
				if (scanner.hasNextInt()) {
					opcion = scanner.nextInt();

					switch (opcion) {
					case 1:
						mostrarLoginMenu();
						break;
					case 2:
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
			} while (opcion != 2);
		} catch (Exception e) {
			System.out.println("Error inesperado: " + e.getMessage());
//	        e.printStackTrace(); // Para desarrollo: Capturar cualquier excepción inesperada
		}
	}

	private void mostrarLoginMenu() {
		LoginMenu loginMenu = new LoginMenu(context);
		loginMenu.mostrar();
	}
}
