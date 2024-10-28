package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import dto.VehiculoDTO;
import entity.Vehiculos;
import interfaces.IGenericRepository;
import interfaces.IVehiculoManager;

public class VehiculoManager implements IVehiculoManager {
	private IGenericRepository<Vehiculos, Integer> repositorio;
	private final Connection connection;
	
	public VehiculoManager(IGenericRepository<Vehiculos, Integer> oRepositorio, Connection connection) {
		this.repositorio = oRepositorio;
		this.connection = connection;
	}
	
	/**
	 * Método para recuperar todos los vehículos
	 */
	public List<VehiculoDTO> obtenerTodos() {
        List<Vehiculos> listaVehiculos = repositorio.obtenerTodos();
        return listaVehiculos.stream()
                         .map(m -> new VehiculoDTO(m.getId(), m.getPatente(), m.getIdCliente(), m.getIdTipoVehiculo()))
                         .collect(Collectors.toList());
    }
	
	/**
	 * Método para guardar un vehículo
	 */
	public Integer guardarVehiculo(VehiculoDTO vehiculoDTO) {
		Vehiculos vehiculo = new Vehiculos(null, vehiculoDTO.getPatente(), vehiculoDTO.getIdCliente(), vehiculoDTO.getIdTipoVehiculo());
        Integer idVehiculoNuevo = repositorio.crear(vehiculo, true);
        return idVehiculoNuevo;
    }
	
	public Integer devolverIdVehiculoDeLaPatente(String patente) {
	    String sql = "SELECT v.id " +
	                 "FROM Vehiculos v " +
	                 "WHERE v.patente = ?";

	    Integer idPatente = null;

	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setString(1, patente);

	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	        	idPatente = rs.getInt("id");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return idPatente;
	}
}
