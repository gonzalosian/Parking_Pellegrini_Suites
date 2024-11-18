package menu;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import Enums.EnumEstadosPuestosPlaya;
import context.ApplicationContext;
import controller.MovimientoPlayaController;
import controller.PuestoDePlayaController;
import controller.TiposVehiculosController;
import controller.VehiculoController;
import dto.MovimientoPlayaDTO;
import dto.PuestoDePlayaDTO;
import dto.TiposVehiculosDTO;
import manager.SessionManager;

public class ChekInMenu {
	private final ApplicationContext context;
	private final VehiculoController vehiculoController;
	private final PuestoDePlayaController puestoDePlayaController;
	private final MovimientoPlayaController movimientoPlayaController;
	private final TiposVehiculosController tiposVehiculosController;

	public ChekInMenu(ApplicationContext context) {
		this.context = context;
		this.vehiculoController = context.getBean(VehiculoController.class);
		this.puestoDePlayaController = context.getBean(PuestoDePlayaController.class);
		this.movimientoPlayaController = context.getBean(MovimientoPlayaController.class);
		this.tiposVehiculosController = context.getBean(TiposVehiculosController.class);
	}

	public void mostrar() {
		try {
			int opcion = 0;

			do {
				System.out.println("");
				System.out.println("----- Menú de Movimientos Parking -----");
				System.out.println("1. Check-In");
				System.out.println("2. Listar Movimientos");
				System.out.println("3. Volver...");
				System.out.print(">> Seleccione una opción: ");

				Scanner scanner = context.getScanner();

				if (scanner.hasNextInt()) {
					opcion = scanner.nextInt();

					switch (opcion) {
					case 1:
						checkIn();
						break;
					case 2:
						listarMovimientos(false);
						break;
					case 3:
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
			} while (opcion != 3);
		} catch (Exception e) {
			System.out.println("Error inesperado: " + e.getMessage());
//	        e.printStackTrace(); // Para desarrollo: Capturar cualquier excepción inesperada
		}
	}

	public void checkIn() {
		try {
			Scanner scanner = context.getScanner();
			System.out.println("");
			System.out.println("----- Check-In de Vehículo (sin lavado) -----");
			System.out.print(">> Ingrese la Patente: ");
			String patente = scanner.next();

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

			Integer idVehiculo = vehiculoController.devolverIdVehiculoDeLaPatente(patente);

			if (idVehiculo == null) {
				System.out.printf("La patente %s no existe. Se registrará.\n", patente);
				idVehiculo = vehiculoController.crearVehiculo(patente, null, idTipoVehiculoSeleccionado);
				System.out.printf("La patente %s fue registrada con id %d.\n", patente, idVehiculo);
			} else
				System.out.printf("La patente existe y el id es %d.\n", idVehiculo);

			// buscar PuestoDePlaya donde numero de puesto en estado libre, sea del mismo
			// tipo de vehiculo, ordenado por piso y nro puesto, con limit 1.
			PuestoDePlayaDTO puestoLibre = puestoDePlayaController
					.buscarPuestoLibrePorTipoVehiculo(idTipoVehiculoSeleccionado);
			if (puestoLibre == null) {
				System.out.println("---------------------");
				System.out.println("No hay puestos libres");
				System.out.println("---------------------");
			} else {
				// actualizar estado de PuestoDePlaya.
				puestoLibre.setIdEstadoPuestoPlaya(EnumEstadosPuestosPlaya.OCUPADO);
				puestoDePlayaController.actualizarPuestoDePlaya(puestoLibre);
				System.out.println("---------------------------------------------");
				System.out.printf("El puesto número %d cambió a estado Ocupado.\n", puestoLibre.getNumeroPuestoPlaya());
				System.out.println("---------------------------------------------");

				/**
				 * Para generar y guardar un código de barras a futuro utilizaré la biblioteca
				 * ZXing (Zebra Crossing), una de las más populares para trabajar con códigos de
				 * barras y códigos QR
				 */
				int codigoDeBarra = ThreadLocalRandom.current().nextInt(100000000, 1000000000);
				LocalDateTime fechaHoraCarga = LocalDateTime.now();
				// Generar movimiento en MovimientoPlaya
				Integer idMovimientoCheckIn = movimientoPlayaController.crearMovimientoPlaya(puestoLibre.getId(),
						idVehiculo, fechaHoraCarga, SessionManager.getInstance().getUsuarioLogueado().getId(), null,
						null, codigoDeBarra, null);

				System.out.println("-----------------------------------");
				System.out.printf("Se generó el Movimiento nro. %d \n", idMovimientoCheckIn);
				System.out.println("-----------------------------------");

				System.out.println("----- Ticket para automovilista -----");
				System.out.printf("Patente: %s \n", patente);
				System.out.printf("Nro.Puesto: %d \n", puestoLibre.getNumeroPuestoPlaya());
				System.out.printf("Tipo Vehículo Puesto: %s \n", puestoLibre.getTiposVehiculos().getDescripcion());
				System.out.printf("Piso: %d \n", puestoLibre.getIdPiso());
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
				System.out.printf("Ingreso: %s \n", fechaHoraCarga.format(formatter));
				System.out.printf("Cód.Barra: %d \n", codigoDeBarra);
				System.out.println("");
				System.out.println(">> Presione la tecla C para continuar.");
				String continuar;
				do {
					continuar = scanner.next();
					if (!continuar.equalsIgnoreCase("C")) {
						System.out.println("Entrada inválida. Presione la tecla C para continuar.");
					}
				} while (!continuar.equalsIgnoreCase("C"));
			}
		} catch (Exception e) {
			System.out.println("Error inesperado: " + e.getMessage());
//	        e.printStackTrace(); // Para desarrollo: Capturar cualquier excepción inesperada
		}
	}

	private List<Integer> listarMovimientos(boolean devolverIds) {
		List<MovimientoPlayaDTO> movimientoPlayaList = movimientoPlayaController.mostrarMovimientos();
		List<Integer> idMovimientosList = new ArrayList<Integer>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

		System.out.println("");
		System.out.println("Lista de Movimientos:");

		for (MovimientoPlayaDTO movimientoPlayaDTO : movimientoPlayaList) {
			System.out.println("ID: " + movimientoPlayaDTO.getId() + " - Nro.Puesto: "
					+ movimientoPlayaDTO.getIdPuestoDePlaya() + " - ID Vehículo: " + movimientoPlayaDTO.getIdVehiculo()
					+ " - Ingreso: " + movimientoPlayaDTO.getFechaHoraIngreso().format(formatter)
					+ " - Usuario Ingreso: " + movimientoPlayaDTO.getIdUsuarioIngreso() + " - Egreso: "
					+ movimientoPlayaDTO.getFechaHoraEgreso() + " - Usuario Egreso: "
					+ movimientoPlayaDTO.getIdUsuarioEgreso() + " - Cód.Barra Ingreso: "
					+ movimientoPlayaDTO.getCodigoBarraIn() + " - Cód.Barra Egreso: "
					+ movimientoPlayaDTO.getCodigoBarraOut());
			if (devolverIds)
				idMovimientosList.add(movimientoPlayaDTO.getId());
		}

		return idMovimientosList;
	}

	private List<Integer> listarTiposVehiculos(boolean devolverIds) {
		List<TiposVehiculosDTO> tiposVehiculosList = tiposVehiculosController.mostrarTiposVehiculos();
		List<Integer> idTiposVehiculosList = new ArrayList<Integer>();

		System.out.println("");
		System.out.println("Lista de Tipos de Vehículos:");

		for (TiposVehiculosDTO tiposVehiculosDTO : tiposVehiculosList) {
			System.out.println(
					"ID: " + tiposVehiculosDTO.getId() + " - Tipo de Vehiculo: " + tiposVehiculosDTO.getDescripcion());
			if (devolverIds) {
				idTiposVehiculosList.add(tiposVehiculosDTO.getId());
			}
		}

		return idTiposVehiculosList;
	}
}
