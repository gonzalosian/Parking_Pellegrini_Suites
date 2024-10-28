package context;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import controller.DescuentosPorHoraController;
import controller.HistoricoPreciosController;
import controller.MovimientoPlayaController;
import controller.PuestoDePlayaController;
import controller.UsuarioController;
import controller.VehiculoController;
import database.DatabaseConnection;
import entity.DescuentosPorHora;
import entity.HistoricoPrecios;
import entity.MovimientosPlaya;
import entity.PuestosDePlaya;
import entity.Usuarios;
import entity.Vehiculos;
import manager.DescuentosPorHoraManager;
import manager.HistoricoPreciosManager;
import manager.MovimientoPlayaManager;
import manager.PuestoDePlayaManager;
import manager.UsuarioManager;
import manager.VehiculoManager;
import repository.GenericRepository;

public class ApplicationContext {
	private final Connection connection;
    private final Map<Class<?>, Object> beans = new HashMap<>();
    
    public ApplicationContext() {
        DatabaseConnection dbConnection = new DatabaseConnection();
        this.connection = dbConnection.getConnection();
        initializeBeans();
    }
    
    private void initializeBeans() {
        // Instancia de repositorios y managers
        GenericRepository<DescuentosPorHora, Integer> descuentosRepo = new GenericRepository<>(connection, DescuentosPorHora.class);
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
        
        GenericRepository<PuestosDePlaya, Integer> puestoDePlayaRepo = new GenericRepository<>(connection, PuestosDePlaya.class);
        PuestoDePlayaManager puestoDePlayaManager = new PuestoDePlayaManager(puestoDePlayaRepo, connection);
        PuestoDePlayaController puestoDePlayaController = new PuestoDePlayaController(puestoDePlayaManager);
        beans.put(PuestoDePlayaController.class, puestoDePlayaController);
        
        GenericRepository<MovimientosPlaya, Integer> movimientoPlayaRepo = new GenericRepository<>(connection, MovimientosPlaya.class);
        MovimientoPlayaManager movimientoPlayaManager = new MovimientoPlayaManager(movimientoPlayaRepo);
        MovimientoPlayaController movimientoPlayaController = new MovimientoPlayaController(movimientoPlayaManager);
        beans.put(MovimientoPlayaController.class, movimientoPlayaController);
        
        GenericRepository<HistoricoPrecios, Integer> historicoPreciosRepo = new GenericRepository<>(connection, HistoricoPrecios.class);
        HistoricoPreciosManager historicoPreciosManager = new HistoricoPreciosManager(historicoPreciosRepo, connection);
        HistoricoPreciosController historicoPreciosController = new HistoricoPreciosController(historicoPreciosManager);
        beans.put(HistoricoPreciosController.class, historicoPreciosController);

    }
    
    public <T> T getBean(Class<T> beanClass) {
        return beanClass.cast(beans.get(beanClass));
    }
    
    public void close() {
        // Cerrar la conexión al final del ciclo de vida de la aplicación
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
