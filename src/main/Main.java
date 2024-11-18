package main;

import context.ApplicationContext;
import menu.MainMenu;

public class Main {

	public static void main(String[] args) {
		ApplicationContext context = ApplicationContext.getInstance();

		try {
			MainMenu mainMenu = new MainMenu(context);
			mainMenu.mostrar();
		} finally {
			context.closeDataBase();
			context.closeScanner();
		}
	}
}