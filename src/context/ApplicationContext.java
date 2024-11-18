package context;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import controller.DescuentosPorHoraController;
import controller.HistoricoPreciosController;
import controller.MovimientoPlayaController;
import controller.PuestoDePlayaController;
import controller.TiposVehiculosController;
import controller.UsuarioController;
import controller.VehiculoController;
import database.DatabaseConnection;
import entity.DescuentosPorHora;
import entity.HistoricoPrecios;
import entity.MovimientosPlaya;
import entity.PuestosDePlaya;
import entity.TiposVehiculos;
import entity.Usuarios;
import entity.Vehiculos;
import manager.DescuentosPorHoraManager;
import manager.HistoricoPreciosManager;
import manager.MovimientoPlayaManager;
import manager.PuestoDePlayaManager;
import manager.TiposVehiculosManager;
import manager.UsuarioManager;
import manager.VehiculoManager;
import repository.GenericRepository;

public class ApplicationContext {
	private static ApplicationContext applicationContext;
	private final Scanner scanner;
	private final DatabaseConnection dbConnection;
	private final Connection connection;
	private final Map<Class<?>, Object> beans = new HashMap<>();

	private ApplicationContext() {
		this.dbConnection = new DatabaseConnection();
		this.scanner = new Scanner(System.in);
		this.connection = dbConnection.getConnection();
		initializeBeans();
	}

	public static ApplicationContext getInstance() {
		if (applicationContext == null) {
			applicationContext = new ApplicationContext();
		}
		return applicationContext;
	}

	/** Obtiene la instancia del Scanner */
	public Scanner getScanner() {
		return scanner;
	}

	/** Cierra el Scanner */
	public void closeScanner() {
		scanner.close();
	}

	private void initializeBeans() {
		// Instancia de repositorios y managers
		GenericRepository<DescuentosPorHora, Integer> descuentosRepo = new GenericRepository<>(connection,
				DescuentosPorHora.class);
		DescuentosPorHoraManager descuentosManager = new DescuentosPorHoraManager(descuentosRepo, connection);
		DescuentosPorHoraController descuentosController = new DescuentosPorHoraController(descuentosManager);
		// Guardar en el map
		beans.put(DescuentosPorHoraController.class, descuentosController);

		GenericRepository<Usuarios, Integer> usuarioRepo = new GenericRepository<>(connection, Usuarios.class);
		UsuarioManager usuarioManager = new UsuarioManager(usuarioRepo, connection);
		UsuarioController usuarioController = new UsuarioController(usuarioManager);
		beans.put(UsuarioController.class, usuarioController);

		GenericRepository<Vehiculos, Integer> vehiculoRepo = new GenericRepository<>(connection, Vehiculos.class);
		VehiculoManager vehiculoManager = new VehiculoManager(vehiculoRepo, connection);
		VehiculoController vehiculoController = new VehiculoController(vehiculoManager);
		beans.put(VehiculoController.class, vehiculoController);

		GenericRepository<PuestosDePlaya, Integer> puestoDePlayaRepo = new GenericRepository<>(connection,
				PuestosDePlaya.class);
		PuestoDePlayaManager puestoDePlayaManager = new PuestoDePlayaManager(puestoDePlayaRepo, connection);
		PuestoDePlayaController puestoDePlayaController = new PuestoDePlayaController(puestoDePlayaManager);
		beans.put(PuestoDePlayaController.class, puestoDePlayaController);

		GenericRepository<MovimientosPlaya, Integer> movimientoPlayaRepo = new GenericRepository<>(connection,
				MovimientosPlaya.class);
		MovimientoPlayaManager movimientoPlayaManager = new MovimientoPlayaManager(movimientoPlayaRepo);
		MovimientoPlayaController movimientoPlayaController = new MovimientoPlayaController(movimientoPlayaManager);
		beans.put(MovimientoPlayaController.class, movimientoPlayaController);

		GenericRepository<HistoricoPrecios, Integer> historicoPreciosRepo = new GenericRepository<>(connection,
				HistoricoPrecios.class);
		HistoricoPreciosManager historicoPreciosManager = new HistoricoPreciosManager(historicoPreciosRepo, connection);
		HistoricoPreciosController historicoPreciosController = new HistoricoPreciosController(historicoPreciosManager);
		beans.put(HistoricoPreciosController.class, historicoPreciosController);
		
		GenericRepository<TiposVehiculos, Integer> tiposVehiculosRepo = new GenericRepository<>(connection,
				TiposVehiculos.class);
		TiposVehiculosManager tiposVehiculosManager = new TiposVehiculosManager(tiposVehiculosRepo);
		TiposVehiculosController tiposVehiculosController = new TiposVehiculosController(tiposVehiculosManager);
		beans.put(TiposVehiculosController.class, tiposVehiculosController);

	}

	public <T> T getBean(Class<T> beanClass) {
		return beanClass.cast(beans.get(beanClass));
	}

	/** Cierra la conexión a la DB al final del ciclo de vida de la aplicación */
	public void closeDataBase() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
