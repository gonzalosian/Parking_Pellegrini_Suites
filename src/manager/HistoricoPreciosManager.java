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
import entity.HistoricoPrecios;
import interfaces.IGenericRepository;
import interfaces.IHistoricoPreciosManager;

public class HistoricoPreciosManager implements IHistoricoPreciosManager {
	private IGenericRepository<HistoricoPrecios, Integer> repositorio;
	private final Connection connection;
	
	public HistoricoPreciosManager(IGenericRepository<HistoricoPrecios, Integer> oRepositorio, Connection connection) {
        this.repositorio = oRepositorio;
        this.connection = connection;
    }
	
	public void guardarHistoricoPrecios(HistoricoPreciosDTO historicoPreciosDTO) {
		HistoricoPrecios historicoPrecios = new HistoricoPrecios(null, historicoPreciosDTO.getFechaVigencia(), historicoPreciosDTO.getFechaHoraCarga(), 
				historicoPreciosDTO.getIdUsuarioCarga(), historicoPreciosDTO.getIdTipoVehiculo(), historicoPreciosDTO.getPrecioPorHora(), 
				historicoPreciosDTO.getPrecioPorDia(), historicoPreciosDTO.getPrecioLavado());
        repositorio.crear(historicoPrecios, false);
    }

    public List<HistoricoPreciosDTO> obtenerTodos() {
        List<HistoricoPrecios> historicosPrecios = repositorio.obtenerTodos();
        return historicosPrecios.stream()
                         .map(m -> new HistoricoPreciosDTO(m.getId(), m.getFechaVigencia(), m.getFechaHoraCarga(), m.getIdUsuarioCarga(), 
                        		 m.getIdTipoVehiculo(), m.getPrecioPorHora(), m.getPrecioPorDia(), m.getPrecioLavado()))
                         .collect(Collectors.toList());
    }
    
    public void eliminarHistoricoPrecios(Integer id) 
    {
    	repositorio.eliminar(id);
    }
    
    public HistoricoPreciosDTO obtenerHistoricoPreciosPorId(Integer id)
    {
    	HistoricoPrecios historicoPrecios = repositorio.obtenerPorId(id);
        if (historicoPrecios != null) {
            return new HistoricoPreciosDTO(historicoPrecios.getId(), historicoPrecios.getFechaVigencia(), historicoPrecios.getFechaHoraCarga(), 
            		historicoPrecios.getIdUsuarioCarga(), historicoPrecios.getIdTipoVehiculo(), historicoPrecios.getPrecioPorHora(), 
            		historicoPrecios.getPrecioPorDia(), historicoPrecios.getPrecioLavado());
        } else {
            return null;  // O maneja una excepción si prefieres
        }
    }
    
    public void actualizarHistoricoPrecios(HistoricoPreciosDTO historicoPreciosDTO) {
		HistoricoPrecios historicoPrecios = new HistoricoPrecios(historicoPreciosDTO.getId(), historicoPreciosDTO.getFechaVigencia(), 
				historicoPreciosDTO.getFechaHoraCarga(), historicoPreciosDTO.getIdUsuarioCarga(), historicoPreciosDTO.getIdTipoVehiculo(), 
				historicoPreciosDTO.getPrecioPorHora(), historicoPreciosDTO.getPrecioPorDia(), historicoPreciosDTO.getPrecioLavado());
        repositorio.actualizar(historicoPrecios);
    }
    
    /**
	 * Verifica si existe una tarifa con la misma fecha de vigencia y para el mismo tipo de vehículo
	 * @param idTipoVehiculo Tipo de vehículo
	 * @param fechaVigencia Fecha de vigencia
	 * @return Devuelve true si existe la cantidad ingresada.
	 * */
	public Boolean validarExistenciaDeTarifas(int idTipoVehiculo, LocalDate fechaVigencia) {
	    String sql = "SELECT id, fechaVigencia, fechaHoraCarga, idUsuarioCarga, idTipoVehiculo, precioPorHora, precioPorDia, precioLavado " +
	                 "FROM HistoricoPrecios " +
	                 "WHERE idTipoVehiculo = ? AND fechaVigencia = ? ";

	    Boolean existe = false;

	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setInt(1, idTipoVehiculo);
//	        stmt.setTimestamp(2, fechaVigencia);
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
