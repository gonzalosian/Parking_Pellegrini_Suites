package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import dto.HistoricoPreciosDTO;
import dto.TiposVehiculosDTO;
import dto.UsuarioDTO;
import entity.HistoricoPrecios;
import entity.TiposVehiculos;
import entity.Usuarios;
import interfaces.IGenericRepository;
import interfaces.IHistoricoPreciosManager;
import repository.GenericRepository;

public class HistoricoPreciosManager implements IHistoricoPreciosManager {
	private IGenericRepository<HistoricoPrecios, Integer> repositorio;
	private final IGenericRepository<TiposVehiculos, Integer> tiposVehiculosRepository;
	private final IGenericRepository<Usuarios, Integer> usuariosRepository;
	private final Connection connection;

	public HistoricoPreciosManager(IGenericRepository<HistoricoPrecios, Integer> oRepositorio, Connection connection) {
		this.repositorio = oRepositorio;
		this.connection = connection;
		this.tiposVehiculosRepository = new GenericRepository<>(connection, TiposVehiculos.class); // Repositorio
																									// genérico para
																									// TiposVehiculos
		this.usuariosRepository = new GenericRepository<>(connection, Usuarios.class); // Repositorio genérico para
																						// Usuarios
	}

	public void guardarHistoricoPrecios(HistoricoPreciosDTO historicoPreciosDTO) {
		HistoricoPrecios historicoPrecios = new HistoricoPrecios(null, historicoPreciosDTO.getFechaVigencia(),
				historicoPreciosDTO.getFechaHoraCarga(), historicoPreciosDTO.getIdUsuarioCarga(),
				historicoPreciosDTO.getIdTipoVehiculo(), historicoPreciosDTO.getPrecioPorHora(),
				historicoPreciosDTO.getPrecioPorDia(), historicoPreciosDTO.getPrecioLavado(), null, null);
		repositorio.crear(historicoPrecios, false);
	}

	public List<HistoricoPreciosDTO> obtenerTodos() {
		try {
			List<HistoricoPrecios> historicosPrecios = repositorio.obtenerTodos();
			return historicosPrecios.stream().map(m -> {
				try {
					// Obtiene Usuarios usando el repositorio genérico y el IdUsuarioCarga
					Usuarios usuarios = usuariosRepository.obtenerPorId(m.getIdUsuarioCarga());

					// Obtiene TiposVehiculos usando el repositorio genérico y el idTipoVehiculo
					TiposVehiculos tiposVehiculos = tiposVehiculosRepository.obtenerPorId(m.getIdTipoVehiculo());

					return new HistoricoPreciosDTO(m.getId(), m.getFechaVigencia(), m.getFechaHoraCarga(),
							m.getIdUsuarioCarga(), m.getIdTipoVehiculo(), m.getPrecioPorHora(), m.getPrecioPorDia(),
							m.getPrecioLavado(), new TiposVehiculosDTO(tiposVehiculos.getId(), tiposVehiculos.getDescripcion()),
							new UsuarioDTO(usuarios.getId(), usuarios.getDni(), usuarios.getApellido(), usuarios.getNombre(),
									usuarios.getIdCargo(), usuarios.getUserName()));
				} catch (Exception e) {
	                System.err.println("Error al mapear un registro de HistoricoPrecios: " + e.getMessage());
	                e.printStackTrace();
	                throw new RuntimeException("Error al mapear un registro de HistoricoPrecios.", e);
	            }
			}).collect(Collectors.toList());
		} catch (Exception e) {
	        System.err.println("Error al obtener todos los históricos de precios: " + e.getMessage());
	        e.printStackTrace();
	        throw new RuntimeException("Ocurrió un error al obtener todos los históricos de precios.", e);
	    }
	}

	public void eliminarHistoricoPrecios(Integer id) {
		repositorio.eliminar(id);
	}

	public HistoricoPreciosDTO obtenerHistoricoPreciosPorId(Integer id) {
		HistoricoPrecios historicoPrecios = repositorio.obtenerPorId(id);
		if (historicoPrecios != null) {
			// Obtiene Usuarios usando el repositorio genérico y el IdUsuarioCarga
			Usuarios usuarios = usuariosRepository.obtenerPorId(historicoPrecios.getIdUsuarioCarga());
			// Obtiene TiposVehiculos usando el repositorio genérico y el idTipoVehiculo
			TiposVehiculos tiposVehiculos = tiposVehiculosRepository.obtenerPorId(historicoPrecios.getIdTipoVehiculo());
			
			return new HistoricoPreciosDTO(historicoPrecios.getId(), historicoPrecios.getFechaVigencia(),
					historicoPrecios.getFechaHoraCarga(), historicoPrecios.getIdUsuarioCarga(),
					historicoPrecios.getIdTipoVehiculo(), historicoPrecios.getPrecioPorHora(),
					historicoPrecios.getPrecioPorDia(), historicoPrecios.getPrecioLavado(),
					new TiposVehiculosDTO(tiposVehiculos.getId(), tiposVehiculos.getDescripcion()),
					new UsuarioDTO(usuarios.getId(), usuarios.getDni(), usuarios.getApellido(), usuarios.getNombre(),
							usuarios.getIdCargo(), usuarios.getUserName()));
		} else {
			return null; // O maneja una excepción si prefieres
		}
	}

	public void actualizarHistoricoPrecios(HistoricoPreciosDTO historicoPreciosDTO) {
		HistoricoPrecios historicoPrecios = new HistoricoPrecios(historicoPreciosDTO.getId(),
				historicoPreciosDTO.getFechaVigencia(), historicoPreciosDTO.getFechaHoraCarga(),
				historicoPreciosDTO.getIdUsuarioCarga(), historicoPreciosDTO.getIdTipoVehiculo(),
				historicoPreciosDTO.getPrecioPorHora(), historicoPreciosDTO.getPrecioPorDia(),
				historicoPreciosDTO.getPrecioLavado(), null, null);
		repositorio.actualizar(historicoPrecios);
	}

	/**
	 * Verifica si existe una tarifa con la misma fecha de vigencia y para el mismo
	 * tipo de vehículo
	 * 
	 * @param idTipoVehiculo Tipo de vehículo
	 * @param fechaVigencia  Fecha de vigencia
	 * @return Devuelve true si existe la cantidad ingresada.
	 */
	public Boolean validarExistenciaDeTarifas(int idTipoVehiculo, LocalDate fechaVigencia) {
		String sql = "SELECT id, fechaVigencia, fechaHoraCarga, idUsuarioCarga, idTipoVehiculo, precioPorHora, precioPorDia, precioLavado "
				+ "FROM HistoricoPrecios " + "WHERE idTipoVehiculo = ? AND fechaVigencia = ? ";

		Boolean existe = false;

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, idTipoVehiculo);
//	        Convertir LocalDate a Timestamp usando LocalDateTime
			LocalDateTime fechaInicioDelDia = fechaVigencia.atStartOfDay();
			Timestamp timestamp = Timestamp.valueOf(fechaInicioDelDia);

			stmt.setTimestamp(2, timestamp);

			ResultSet rs = stmt.executeQuery();

			if (rs.next())
				existe = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return existe;
	}
}
