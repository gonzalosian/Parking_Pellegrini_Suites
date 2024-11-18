package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.PuestoDePlayaDTO;
import dto.TiposVehiculosDTO;
import entity.PuestosDePlaya;
import entity.TiposVehiculos;
import interfaces.IGenericRepository;
import interfaces.IPuestoDePlayaManager;
import repository.GenericRepository;

public class PuestoDePlayaManager implements IPuestoDePlayaManager {
	private IGenericRepository<PuestosDePlaya, Integer> repositorio;
	private final IGenericRepository<TiposVehiculos, Integer> tiposVehiculosRepository;
	private final Connection connection;

	public PuestoDePlayaManager(IGenericRepository<PuestosDePlaya, Integer> oRepositorio, Connection connection) {
		this.repositorio = oRepositorio;
		this.connection = connection;
		this.tiposVehiculosRepository = new GenericRepository<>(connection, TiposVehiculos.class);
	}

	public void actualizarPuestoDePlaya(PuestoDePlayaDTO puestoDePlayaDTO) {
		PuestosDePlaya puestoDePlaya = new PuestosDePlaya(puestoDePlayaDTO.getId(),
				puestoDePlayaDTO.getNumeroPuestoPlaya(), puestoDePlayaDTO.getIdPiso(),
				/* puestoDePlayaDTO.getIdTipoVehiculo(), */ puestoDePlayaDTO.getTiposVehiculos().getId(),
				puestoDePlayaDTO.getIdEstadoPuestoPlaya(), null);
		repositorio.actualizar(puestoDePlaya);
	}

	/**
	 * Busca el primer puesto libre para un Tipo de Vehículo específico, ordenado
	 * por piso y número de puesto.
	 * 
	 * @param idTipoVehiculo
	 * @return PuestoDePlayaDTO
	 */
	public PuestoDePlayaDTO buscarPuestoLibrePorTipoVehiculo(int idTipoVehiculo) {
		String sql = "SELECT pp.id, pp.numeroPuestoPlaya, pp.idPiso, pp.idTipoVehiculo, pp.idEstadoPuestoPlaya "
				+ "FROM PuestosDePlaya pp " + "LEFT JOIN EstadosPuestosPlaya ep ON pp.idEstadoPuestoPlaya = ep.id "
				+ "WHERE ep.descripcion = ? AND pp.idTipoVehiculo = ? " + "ORDER BY pp.idPiso, pp.numeroPuestoPlaya "
				+ "LIMIT 1 ";

		PuestoDePlayaDTO puestoLibre = null;

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, "libre"); // Asigna "libre" a la consulta
			stmt.setInt(2, idTipoVehiculo); // Asigna el idTipoVehiculo a la consulta

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				// Obtiene TiposVehiculos usando el repositorio genérico y el idTipoVehiculo
				TiposVehiculos tiposVehiculos = tiposVehiculosRepository.obtenerPorId(rs.getInt("idTipoVehiculo"));

				puestoLibre = new PuestoDePlayaDTO(rs.getInt("id"), rs.getInt("numeroPuestoPlaya"), rs.getInt("idPiso"),
						rs.getInt("idTipoVehiculo"), rs.getInt("idEstadoPuestoPlaya"),
						new TiposVehiculosDTO(tiposVehiculos.getId(), tiposVehiculos.getDescripcion()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return puestoLibre;
	}
}
