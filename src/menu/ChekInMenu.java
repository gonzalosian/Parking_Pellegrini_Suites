package menu;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import Enums.EnumEstadosPuestosPlaya;
import context.ApplicationContext;
import controller.MovimientoPlayaController;
import controller.PuestoDePlayaController;
import controller.VehiculoController;
import dto.PuestoDePlayaDTO;
import manager.SessionManager;

public class ChekInMenu {
	private final ApplicationContext context;
	private final VehiculoController vehiculoController;
	private final PuestoDePlayaController puestoDePlayaController;
	private final MovimientoPlayaController movimientoPlayaController;

    public ChekInMenu(ApplicationContext context) {
        this.context = context;
        this.vehiculoController = context.getBean(VehiculoController.class);
        this.puestoDePlayaController = context.getBean(PuestoDePlayaController.class);
        this.movimientoPlayaController = context.getBean(MovimientoPlayaController.class);
    }
	
	public void mostrar()
	{
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("");
			System.out.println("----- Check-In de Vehículo (sin lavado) -----");
			System.out.print("Ingrese la Patente: ");
			String patente = scanner.next();
			
			mostrarTiposDeVehiculos();
			System.out.print("Ingrese el ID del Tipo de Vehículo: ");
			int tipoVehiculo = scanner.nextInt();

			Integer idVehiculo = vehiculoController.devolverIdVehiculoDeLaPatente(patente);
			
			if (idVehiculo == null) {
				System.out.printf("La patente %s no existe. Se registrará.\n", patente);
				idVehiculo = vehiculoController.crearVehiculo(patente, null, tipoVehiculo);
				System.out.printf("La patente %s fue registrada con id %d.\n", patente, idVehiculo);
			} else System.out.printf("La patente existe y el id es %d.\n", idVehiculo);
			
			// buscar PuestoDePlaya donde numero de puesto en estado libre, sea del mismo tipo de vehiculo, ordenado por piso y nro puesto, con limit 1.
			PuestoDePlayaDTO puestoLibre = puestoDePlayaController.buscarPuestoLibrePorTipoVehiculo(tipoVehiculo);
			if(puestoLibre == null) {
				System.out.println("---------------------");
				System.out.println("No hay puestos libres");
				System.out.println("---------------------");
				mostrarUsuarioMenu();
			} else {
				// actualizar estado de PuestoDePlaya.
				puestoLibre.setIdEstadoPuestoPlaya(EnumEstadosPuestosPlaya.OCUPADO);
				puestoDePlayaController.actualizarPuestoDePlaya(puestoLibre);
				System.out.println("---------------------------------------------");
				System.out.println("El puesto número %d cambió a estado Ocupado."+puestoLibre.getNumeroPuestoPlaya());
				System.out.println("---------------------------------------------");
				
				/**
				 * Para generar y guardar un código de barras para el TP4 utilizaré la biblioteca ZXing (Zebra Crossing), 
				 * una de las más populares para trabajar con códigos de barras y códigos QR
				 * */
				int codigoDeBarra = (int) ThreadLocalRandom.current().nextLong(100000000000L, 1000000000000L);
				LocalDateTime fechaHoraCarga = LocalDateTime.now();
				// Generar movimiento en MovimientoPlaya
				Integer idMovimientoCheckIn = movimientoPlayaController.crearMovimientoPlaya(puestoLibre.getId(), idVehiculo, fechaHoraCarga, 
						SessionManager.getInstance().getUsuarioLogueado().getId(), null, null, codigoDeBarra, null);
				
				System.out.println("----- Ticket para automovilista -----");
//				System.out.printf("Patente: %s - Nro.Puesto: %d - Piso: %d - Ingreso: %s - Cód.Barra: %d \n", 
//						patente, puestoLibre.getNumeroPuestoPlaya(), puestoLibre.getIdPiso(), fechaHoraCarga.toString(), codigoDeBarra);
				System.out.printf("Patente: %s \n", patente);
				System.out.printf("Nro.Puesto: %d \n", puestoLibre.getNumeroPuestoPlaya());
				System.out.printf("Piso: %d \n", puestoLibre.getIdPiso());
				System.out.printf("Ingreso: %s \n", fechaHoraCarga.toString());
				System.out.printf("Cód.Barra: %d \n", codigoDeBarra);
				System.out.println("");
				System.out.println("Presione la tecla C para continuar.");
				String continuar;
				do {
				    continuar = scanner.next();
				    if (!continuar.equalsIgnoreCase("C")) {
				        System.out.println("Entrada inválida. Presione la tecla C para continuar.");
				    }
				} while (!continuar.equalsIgnoreCase("C"));
				
				mostrarUsuarioMenu();
			}
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
	
	private void mostrarUsuarioMenu() {
		UsuarioMenu usuarioMenu = new UsuarioMenu(context);
		usuarioMenu.mostrar();
    }
}
