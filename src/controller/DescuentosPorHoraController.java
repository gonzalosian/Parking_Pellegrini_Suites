package controller;

import java.util.List;

import dto.DescuentoPorHoraDTO;
import interfaces.IDescuentosPorHoraManager;

public class DescuentosPorHoraController {
	private IDescuentosPorHoraManager objDescuentosPorHoraManager;

    public DescuentosPorHoraController(IDescuentosPorHoraManager oDescuentosPorHoraManager) {
        this.objDescuentosPorHoraManager = oDescuentosPorHoraManager;
    }

    public void crearDescuentoPorHora(int cantidadDias, int porcentajeDescuento) {
    	DescuentoPorHoraDTO descuentoPorHora = new DescuentoPorHoraDTO(null, cantidadDias, porcentajeDescuento);
    	objDescuentosPorHoraManager.guardarDescuentosPorHora(descuentoPorHora);
    }

    public List<DescuentoPorHoraDTO> mostrarDescuentosPorHora() {
        List<DescuentoPorHoraDTO> descuentosPorHora = objDescuentosPorHoraManager.obtenerTodos();
        return descuentosPorHora;
    }
    
    public void eliminarDescuentoPorHora(Integer id)
    {
    	objDescuentosPorHoraManager.eliminarDescuentoPorHora(id);
    }
    
    public DescuentoPorHoraDTO obtenerDescuentoPorId(Integer id)
    {
    	DescuentoPorHoraDTO descuentoPorHoraDTO = objDescuentosPorHoraManager.obtenerDescuentoPorId(id);
    	return descuentoPorHoraDTO;
    }
    
    public void actualizarDescuentoPorHora(DescuentoPorHoraDTO descuentoPorHoraDTO)
    {
    	objDescuentosPorHoraManager.actualizarDescuentoPorHora(descuentoPorHoraDTO);
    }
    
    public Boolean validarExistenciaDeDescuento(int cantidadHoras, Integer idDescuentoParaModificar)
    {
    	Boolean existe = objDescuentosPorHoraManager.validarExistenciaDeDescuento(cantidadHoras, idDescuentoParaModificar);
    	return existe;
    }
}
