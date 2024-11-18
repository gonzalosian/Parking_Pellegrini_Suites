package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import dto.DescuentoPorHoraDTO;
import entity.DescuentosPorHora;
import interfaces.IDescuentosPorHoraManager;
import interfaces.IGenericRepository;

public class DescuentosPorHoraManager implements IDescuentosPorHoraManager {
	private IGenericRepository<DescuentosPorHora, Integer> repositorio;
	private final Connection connection;

	public DescuentosPorHoraManager(IGenericRepository<DescuentosPorHora, Integer> oRepositorio,
			Connection connection) {
		this.repositorio = oRepositorio;
		this.connection = connection;
	}

	public void guardarDescuentosPorHora(DescuentoPorHoraDTO descuentoPorHoraDTO) {
		DescuentosPorHora descuentosPorHora = new DescuentosPorHora(null, descuentoPorHoraDTO.getCantidadDias(),
				descuentoPorHoraDTO.getPorcentajeDescuento());
		repositorio.crear(descuentosPorHora, false);
	}

	public List<DescuentoPorHoraDTO> obtenerTodos() {
		List<DescuentosPorHora> descuentosPorHora = repositorio.obtenerTodos();
		return descuentosPorHora.stream()
				.map(m -> new DescuentoPorHoraDTO(m.getId(), m.getCantidadDias(), m.getPorcentajeDescuento()))
				.collect(Collectors.toList());
	}

	public void eliminarDescuentoPorHora(Integer id) {
		repositorio.eliminar(id);
	}

	public DescuentoPorHoraDTO obtenerDescuentoPorId(Integer id) {
		DescuentosPorHora descuentosPorHora = repositorio.obtenerPorId(id);
		if (descuentosPorHora != null) {
			return new DescuentoPorHoraDTO(descuentosPorHora.getId(), descuentosPorHora.getCantidadDias(),
					descuentosPorHora.getPorcentajeDescuento());
		} else {
			return null; // O maneja una excepción si prefieres
		}
	}

	public void actualizarDescuentoPorHora(DescuentoPorHoraDTO descuentoPorHoraDTO) {
		DescuentosPorHora descuentosPorHora = new DescuentosPorHora(descuentoPorHoraDTO.getId(),
				descuentoPorHoraDTO.getCantidadDias(), descuentoPorHoraDTO.getPorcentajeDescuento());
		repositorio.actualizar(descuentosPorHora);
	}

	/**
	 * Verifica si existe un descuento con la misma cantidad de días ingresados
	 * 
	 * @param cantidadHoras Cantidad de horas desde la cual aplicará el descuento
	 * @return Devuelve true si existe la cantidad ingresada.
	 */
	public Boolean validarExistenciaDeDescuento(int cantidadHoras, Integer idDescuentoParaModificar) {
		String sql;
		if(idDescuentoParaModificar == null) {
			sql = "SELECT id, cantidadDias, porcentajeDescuento " + "FROM DescuentosPorHora "
					+ "WHERE cantidadDias = ? ";
		} else {
			sql = "SELECT id, cantidadDias, porcentajeDescuento " + "FROM DescuentosPorHora "
					+ "WHERE cantidadDias = ? AND id != ? ";
		}

		Boolean existe = false;

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, cantidadHoras);
			
			if(idDescuentoParaModificar != null)
				stmt.setInt(2, idDescuentoParaModificar);

			ResultSet rs = stmt.executeQuery();

			if (rs.next())
				existe = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return existe;
	}
}
