package controller;

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
			int precioPorHora, int precioPorDia, int precioLavado) {
    	HistoricoPreciosDTO historicoPrecios = new HistoricoPreciosDTO(null, fechaVigencia, fechaHoraCarga, idUsuarioCarga, idTipoVehiculo,
    			precioPorHora, precioPorDia, precioLavado);
    	objHistoricoPreciosManager.guardarHistoricoPrecios(historicoPrecios);
    }

    public List<HistoricoPreciosDTO> mostrarDescuentosPorHora() {
        List<HistoricoPreciosDTO> historicoPrecios = objHistoricoPreciosManager.obtenerTodos();
        return historicoPrecios;
    }
    
    public void eliminarDescuentoPorHora(Integer id)
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
