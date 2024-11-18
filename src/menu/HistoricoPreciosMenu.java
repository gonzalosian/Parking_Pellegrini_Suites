package menu;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import context.ApplicationContext;
import controller.HistoricoPreciosController;
import controller.TiposVehiculosController;
import dto.HistoricoPreciosDTO;
import dto.TiposVehiculosDTO;
import manager.SessionManager;

public class HistoricoPreciosMenu {
	private final ApplicationContext context;
	private final HistoricoPreciosController historicoPreciosController;
	private final TiposVehiculosController tiposVehiculosController;

	public HistoricoPreciosMenu(ApplicationContext context) {
		this.context = context;
		this.historicoPreciosController = context.getBean(HistoricoPreciosController.class);
		this.tiposVehiculosController = context.getBean(TiposVehiculosController.class);
	}

	public void mostrar() {
		try {
			int opcion = 0;

			do {
				System.out.println("");
				System.out.println("----- Menú de Tarifas -----");
				System.out.println("1. Registrar una Tarifa");
				System.out.println("2. Eliminar una Tarifa");
				System.out.println("3. Listar Tarifas");
				System.out.println("4. Volver...");
				System.out.print(">> Seleccione una opción: ");

				Scanner scanner = context.getScanner();

				if (scanner.hasNextInt()) {
					opcion = scanner.nextInt();

					switch (opcion) {
					case 1:
						registrarTarifa();
						break;
					case 2:
						eliminarTarifa();
						break;
					case 3:
						listarTarifas(false);
						break;
					case 4:
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
			} while (opcion != 4);
		} catch (Exception e) {
			System.out.println("Error inesperado: " + e.getMessage());
		}
	}

	private void registrarTarifa() {
		try {
			Scanner scanner = context.getScanner();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Defino el formato de fecha
			LocalDate fecha = null;

			while (fecha == null) {
				System.out.print(">> Ingrese la Fecha de Vigencia en formato dd/MM/yyyy: ");
				String fechaVigenciaInput = scanner.next();

				try {
					fecha = LocalDate.parse(fechaVigenciaInput, formatter); // Intenta parsear la fecha
					System.out.println("Fecha ingresada correctamente: " + fecha);
				} catch (DateTimeParseException e) {
					System.out.println("Formato de fecha inválido. Por favor, intente de nuevo.");
				}
			}

			List<Integer> idTiposVehiculosList = listarTiposVehiculos(true);

			int idTipoVehiculoSeleccionado = 0;
			do {
				System.out.print(">> Ingrese el ID del Tipo de Vehiculo: ");

				if (scanner.hasNextInt()) {
					idTipoVehiculoSeleccionado = scanner.nextInt();

					if (idTiposVehiculosList.contains(idTipoVehiculoSeleccionado)) {
//						System.out.println("El número " + idTipoVehiculoSeleccionado + " está en la lista.");
					} else {
						System.out.println("El número " + idTipoVehiculoSeleccionado + " no está en la lista.");
					}

				} else {
					System.out.println("Entrada no válida. Por favor, ingrese un número.");
					scanner.next(); // Consumir la entrada no válida
				}
			} while (!idTiposVehiculosList.contains(idTipoVehiculoSeleccionado));

			BigDecimal precioPorHoraInput = leerEntero(">> Ingrese el Precio por Hora: ", scanner);
			BigDecimal precioPorDiaInput = leerEntero(">> Ingrese el Precio por Día: ", scanner);
			BigDecimal precioLavadoInput = leerEntero(">> Ingrese el Precio del Servicio de Lavado: ", scanner);

			if (historicoPreciosController.validarExistenciaDeTarifas(idTipoVehiculoSeleccionado, fecha)) {
				System.out.println(
						"----------------------------------------------------------------------------------------");
				System.out.println(
						"Ya existe un registro de Tarifas para el tipo de vehículo y fecha de vigencia ingresada.");
				System.out.println(
						"----------------------------------------------------------------------------------------");
			} else {
				LocalDateTime fechaVigencia = fecha.atStartOfDay();
				LocalDateTime fechaHoraCarga = LocalDateTime.now();
				historicoPreciosController.crearHistoricoPrecios(fechaVigencia, fechaHoraCarga,
						SessionManager.getInstance().getUsuarioLogueado().getId(), idTipoVehiculoSeleccionado,
						precioPorHoraInput, precioPorDiaInput, precioLavadoInput);
				System.out.println("----------------------------");
				System.out.println("La Tarifa se creó con éxito.");
				System.out.println("----------------------------");
			}
		} catch (Exception e) {
			System.out.println("Error inesperado: " + e.getMessage());
		}
	}

	private List<Integer> listarTiposVehiculos(boolean devolverIds) {
		List<TiposVehiculosDTO> tiposVehiculosList = tiposVehiculosController.mostrarTiposVehiculos();
		List<Integer> idTiposVehiculosList = new ArrayList<Integer>();

		System.out.println("");
		System.out.println("Lista de Tipos de Vehículos:");
		System.out.println("ID - Tipo de Vehiculo");

		for (TiposVehiculosDTO tiposVehiculosDTO : tiposVehiculosList) {
			System.out.println(tiposVehiculosDTO.getId() + " - " + tiposVehiculosDTO.getDescripcion());
			if (devolverIds) {
				idTiposVehiculosList.add(tiposVehiculosDTO.getId());
			}
		}

		return idTiposVehiculosList;
	}

	private void eliminarTarifa() {
		List<Integer> idTarifasList = listarTarifas(true);
		System.out.print(">> Ingrese el ID de la Tarifa que desea eliminar: ");
		Scanner scanner = context.getScanner();

		if (scanner.hasNextInt()) {
			int idTarifaSeleccionado = scanner.nextInt();

			if (idTarifasList.contains(idTarifaSeleccionado)) {
//				System.out.println("El número " + idTarifaSeleccionado + " está en la lista.");
				historicoPreciosController.eliminarHistoricoPrecios(idTarifaSeleccionado);
				System.out.printf("El id %d ha sido eliminado \n", idTarifaSeleccionado);
			} else {
				System.out.println("El número " + idTarifaSeleccionado + " no está en la lista.");
			}

		} else {
			System.out.println("Entrada no válida. Por favor, ingrese un número.");
			scanner.next(); // Consumir la entrada no válida
		}
	}

	private List<Integer> listarTarifas(boolean devolverIds) {
		List<HistoricoPreciosDTO> historicoPreciosList = historicoPreciosController.mostrarHistoricoPrecios();
		List<Integer> idDctosList = new ArrayList<Integer>();

		System.out.println("");
		System.out.println("Lista de Tarifas:");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

		for (HistoricoPreciosDTO historicoPreciosDTO : historicoPreciosList) {
			System.out.println("ID: " + historicoPreciosDTO.getId() + " - Vigencia: "
					+ historicoPreciosDTO.getFechaVigencia().format(formatter) + " - Carga: "
					+ historicoPreciosDTO.getFechaHoraCarga().format(formatter) + " - Usuario Carga: "
					+ historicoPreciosDTO.getUsuario().getNombre()
							.concat(" " + historicoPreciosDTO.getUsuario().getApellido())
					+ " - Tipo Vehiculo: " + historicoPreciosDTO.getTipoVehiculo().getDescripcion()
					+ " - Precio por Hora: " + historicoPreciosDTO.getPrecioPorHora() + " - Precio por Dia: "
					+ historicoPreciosDTO.getPrecioPorDia() + " - Precio Lavado: "
					+ historicoPreciosDTO.getPrecioLavado());
			if (devolverIds)
				idDctosList.add(historicoPreciosDTO.getId());
		}

		return idDctosList;
	}

	private BigDecimal leerEntero(String mensaje, Scanner scanner) {
		BigDecimal numero;
		while (true) {
			System.out.print(mensaje);
			if (scanner.hasNextBigDecimal()) {
				numero = scanner.nextBigDecimal();
				break;
			} else {
				System.out.println("Entrada no válida. Por favor, ingrese un número entero.");
				scanner.next(); // Consumir la entrada no válida
			}
		}
		return numero;
	}
}