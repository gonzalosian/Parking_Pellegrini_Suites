package menu;

import java.util.Scanner;

import context.ApplicationContext;
import controller.UsuarioController;
import dto.UsuarioDTO;
import manager.SessionManager;

public class LoginMenu {
	private final ApplicationContext context;
	private final UsuarioController usuarioController;

	public LoginMenu(ApplicationContext context) {
		this.context = context;
		this.usuarioController = context.getBean(UsuarioController.class);
	}

	public void mostrar() {
		Scanner scanner = context.getScanner();
		System.out.println("---------------------------------");
		System.out.print("Ingrese su usuario: ");
		String user = scanner.next();
		System.out.print("Ingrese su contraseña: ");
		String pass = scanner.next();

		UsuarioDTO usuarioLogueado = authUsuario(user, pass);

		if (usuarioLogueado != null) {
			mostrarUsuarioMenu();
		} else {
			this.mostrarMainMenu();
		}
	}

	public UsuarioDTO authUsuario(String user, String pass) {
		UsuarioDTO usuario = usuarioController.validarLogin(user, pass);

		if (usuario != null) {
			System.out.println("-----------------------");
			System.out.println("Se logueo correctamente");
			System.out.println("-----------------------");
			SessionManager.getInstance().setUsuarioLogueado(usuario);
		} else {
			System.out.println("---------------------------------");
			System.out.println("Usuario y/o contraseña incorrecto");
			System.out.println("---------------------------------");
		}

		return usuario;
	}

	private void mostrarUsuarioMenu() {
		UsuarioMenu usuarioMenu = new UsuarioMenu(context);
		usuarioMenu.mostrar();
	}

	private void mostrarMainMenu() {
		MainMenu mainMenu = new MainMenu(context);
		mainMenu.mostrar();
	}
}
