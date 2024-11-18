package menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import context.ApplicationContext;
import controller.DescuentosPorHoraController;
import dto.DescuentoPorHoraDTO;

public class DescuentosMenu {
	private final ApplicationContext context;
	private final DescuentosPorHoraController descuentosController;

	public DescuentosMenu(ApplicationContext context) {
		this.context = context;
		this.descuentosController = context.getBean(DescuentosPorHoraController.class);
	}

	public void mostrar() {
		try {
			int opcion = 0;

			do {
				System.out.println("");
				System.out.println("----- Menú de Descuentos -----");
				System.out.println("1. Registrar un Descuento");
				System.out.println("2. Eliminar un Descuento");
				System.out.println("3. Modificar un Descuento");
				System.out.println("4. Listar Descuentos");
				System.out.println("5. Volver...");
				System.out.print(">> Seleccione una opción: ");

				Scanner scanner = context.getScanner();

				if (scanner.hasNextInt()) {
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
						listarDescuentos(false);
						break;
					case 5:
						System.out.println("Volviendo al menú anterior...");
						break;
					default:
						System.out.println("Opción no válida.");
						System.out.println("");
					}
				} else {
					System.out.println("Entrada no válida. Por favor, ingrese un número.");
					scanner.next(); // Consumir la entrada no válida
				}
			} while (opcion != 5);
		} catch (Exception e) {
			System.out.println("Error inesperado: " + e.getMessage());
//	        e.printStackTrace(); // Para desarrollo: Capturar cualquier excepción inesperada
		}
	}

	private void registrarDescuento() {
		try {
			Scanner scanner = context.getScanner();
			int cantidadHoras = leerEntero(">> Ingrese la Cantidad de Horas: ", scanner);
			int porcentajeDescuento = leerEntero(">> Ingrese el Porcentaje de Descuento: ", scanner);

			if (descuentosController.validarExistenciaDeDescuento(cantidadHoras, null)) {
				System.out.println("-----------------------------------------------------------------");
				System.out.println("Ya existe un registro de descuento con la misma cantidad de horas.");
				System.out.println("-----------------------------------------------------------------");
			} else {
				descuentosController.crearDescuentoPorHora(cantidadHoras, porcentajeDescuento);
				System.out.println("-------------------------------");
				System.out.println("El Descuento se creó con éxito.");
				System.out.println("-------------------------------");
			}
		} catch (Exception e) {
			System.out.println("Error inesperado: " + e.getMessage());
		}
	}

	private void eliminarDescuento() {
		List<Integer> idDctosList = listarDescuentos(true);
		System.out.print(">> Ingrese el ID de Descuento que desea eliminar: ");
		Scanner scanner = context.getScanner();

		if (scanner.hasNextInt()) {
			int idDctoSeleccionado = scanner.nextInt();

			if (idDctosList.contains(idDctoSeleccionado)) {
//				System.out.println("El número " + idDctoSeleccionado + " está en la lista.");
				descuentosController.eliminarDescuentoPorHora(idDctoSeleccionado);
				System.out.printf("El id %d ha sido eliminado \n", idDctoSeleccionado);
			} else {
				System.out.println("El número " + idDctoSeleccionado + " no está en la lista.");
			}

		} else {
			System.out.println("Entrada no válida. Por favor, ingrese un número.");
			scanner.next(); // Consumir la entrada no válida
		}
	}

	private List<Integer> listarDescuentos(boolean devolverIds) {
		List<DescuentoPorHoraDTO> descuentoPorHoraList = descuentosController.mostrarDescuentosPorHora();
		List<Integer> idDctosList = new ArrayList<Integer>();

		System.out.println("");
		System.out.println("Lista de Descuentos:");

		for (DescuentoPorHoraDTO descuentoPorHoraDTO : descuentoPorHoraList) {
			System.out.println("ID: " + descuentoPorHoraDTO.getId() + " - Cantidad de horas: "
					+ descuentoPorHoraDTO.getCantidadDias() + " - Porcentaje de Descuento: "
					+ descuentoPorHoraDTO.getPorcentajeDescuento());
			if (devolverIds)
				idDctosList.add(descuentoPorHoraDTO.getId());
		}

		return idDctosList;
	}

	private void modificarDescuento() {
		try {
			List<Integer> idDctosList = listarDescuentos(true);
			System.out.print(">> Ingrese el ID de Descuento que desea actualizar: ");
			Scanner scanner = context.getScanner();

			if (scanner.hasNextInt()) {
				int idDctoSeleccionado = scanner.nextInt();

				if (idDctosList.contains(idDctoSeleccionado)) {
//					System.out.println("El número " + idDctoSeleccionado + " está en la lista.");

					DescuentoPorHoraDTO descuentoPorHoraParaModificar = descuentosController
							.obtenerDescuentoPorId(idDctoSeleccionado);

					int cantidadHoras = leerEntero(">> Ingrese la Cantidad de Horas: ", scanner);
					int porcentajeDescuento = leerEntero(">> Ingrese el Porcentaje de Descuento: ", scanner);

					if (descuentosController.validarExistenciaDeDescuento(cantidadHoras,
							descuentoPorHoraParaModificar.getId())) {
						System.out.println("-----------------------------------------------------------------");
						System.out.println("Ya existe un registro de descuento con la misma cantidad de horas.");
						System.out.println("-----------------------------------------------------------------");
					} else {
						descuentoPorHoraParaModificar.setCantidadDias(cantidadHoras);
						descuentoPorHoraParaModificar.setPorcentajeDescuento(porcentajeDescuento);

						descuentosController.actualizarDescuentoPorHora(descuentoPorHoraParaModificar);
						System.out.println("-------------------------------");
						System.out.println("El Descuento se actualizó con éxito.");
						System.out.println("-------------------------------");
					}

					System.out.printf("El id %d ha sido actualizado \n", idDctoSeleccionado);
				} else {
					System.out.println("El número " + idDctoSeleccionado + " no está en la lista.");
				}

			} else {
				System.out.println("Entrada no válida. Por favor, ingrese un número.");
				scanner.next(); // Consumir la entrada no válida
			}
		} catch (Exception e) {
			System.out.println("Error inesperado: " + e.getMessage());
		}
	}

	private int leerEntero(String mensaje, Scanner scanner) {
		int numero;
		while (true) {
			System.out.print(mensaje);
			if (scanner.hasNextInt()) {
				numero = scanner.nextInt();
				break;
			} else {
				System.out.println("Entrada no válida. Por favor, ingrese un número entero.");
				scanner.next(); // Consumir la entrada no válida
			}
		}
		return numero;
	}
}