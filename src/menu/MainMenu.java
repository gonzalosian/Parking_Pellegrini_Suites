package menu;

import java.util.Scanner;

import context.ApplicationContext;
import manager.SessionManager;

public class MainMenu {
	private final ApplicationContext context;
	// Definir constantes para colores ANSI
//    public static final String ANSI_RESET = "\u001B[0m";
//    public static final String ANSI_RED = "\u001B[31m";
//    public static final String ANSI_GREEN = "\u001B[32m";
//    public static final String ANSI_YELLOW = "\u001B[33m";
//    public static final String ANSI_BLUE = "\u001B[34m";

    public MainMenu(ApplicationContext context) {
        this.context = context;
    }

    public void mostrar() {
        try (Scanner scanner = new Scanner(System.in)) {
			int opcion = 0;

			do {
				System.out.println("");
				System.out.println("Bienvenido a Parking Pellegrini Suites");
				System.out.println("");
			    System.out.println("----- Menú Principal -----");
			    System.out.println("1. Login (jperez - 123)");
			    System.out.println("2. Salir");
//				System.out.println(ANSI_BLUE + "Bienvenido a Parking Pellegrini Suites" + ANSI_RESET);
//				System.out.println("");
//			    System.out.println(ANSI_GREEN + "----- Menú Principal -----" + ANSI_RESET);
//			    System.out.println(ANSI_YELLOW + "1. Login (jperez - 123)" + ANSI_RESET);
//			    System.out.println(ANSI_YELLOW + "2. Salir" + ANSI_RESET);
			    System.out.print("Seleccione una opción: ");
			    
			    if(scanner.hasNextInt()) {
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
//			    		System.out.println(ANSI_RED + "Opción no válida." + ANSI_RESET);
			    		System.out.println("");
			    	}
			    } else {
			    	System.out.println("Entrada no válida. Por favor, ingrese un número.");
//			    	System.out.println(ANSI_RED + "Entrada no válida. Por favor, ingrese un número." + ANSI_RESET);
		            scanner.next(); // Consumir la entrada no válida
			    }
			} while (opcion != 2);
		}
        context.close(); // Cerrar la conexión al salir de la aplicación
    }

    private void mostrarLoginMenu() {
        LoginMenu loginMenu = new LoginMenu(context);
        loginMenu.mostrar();
    }
}
