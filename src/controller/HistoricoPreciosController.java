package controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import dto.HistoricoPreciosDTO;
import interfaces.IHistoricoPreciosManager;

public class HistoricoPreciosController {
	private IHistoricoPreciosManager objHistoricoPreciosManager;

    public HistoricoPreciosController(IHistoricoPreciosManager oHistoricoPreciosManager) {
        this.objHistoricoPreciosManager = oHistoricoPreciosManager;
    }

    public void crearHistoricoPrecios(LocalDateTime fechaVigencia, LocalDateTime fechaHoraCarga, int idUsuarioCarga, int idTipoVehiculo,
    		BigDecimal precioPorHora, BigDecimal precioPorDia, BigDecimal precioLavado) {
    	HistoricoPreciosDTO historicoPrecios = new HistoricoPreciosDTO(null, fechaVigencia, fechaHoraCarga, idUsuarioCarga, idTipoVehiculo,
    			precioPorHora, precioPorDia, precioLavado);
    	objHistoricoPreciosManager.guardarHistoricoPrecios(historicoPrecios);
    }

    public List<HistoricoPreciosDTO> mostrarHistoricoPrecios() {
        try {
            List<HistoricoPreciosDTO> historicoPrecios = objHistoricoPreciosManager.obtenerTodos();
            return historicoPrecios;
        } catch (Exception e) {
            System.err.println("Error al mostrar el histórico de precios: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Ocurrió un error al mostrar el histórico de precios.", e);
        }
    }
    
    public void eliminarHistoricoPrecios(Integer id)
    {
    	objHistoricoPreciosManager.eliminarHistoricoPrecios(id);
    }
    
    public HistoricoPreciosDTO obtenerHistoricoPreciosPorId(Integer id)
    {
    	HistoricoPreciosDTO historicoPrecios = objHistoricoPreciosManager.obtenerHistoricoPreciosPorId(id);
    	return historicoPrecios;
    }
    
    public void actualizarHistoricoPrecios(HistoricoPreciosDTO historicoPreciosDTO)
    {
    	objHistoricoPreciosManager.actualizarHistoricoPrecios(historicoPreciosDTO);
    }
    
    public Boolean validarExistenciaDeTarifas(int idTipoVehiculo, LocalDate fechaVigencia)
    {
    	Boolean existe = objHistoricoPreciosManager.validarExistenciaDeTarifas(idTipoVehiculo, fechaVigencia);
    	return existe;
    }
}
