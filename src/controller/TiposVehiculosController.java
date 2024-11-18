package controller;

import java.util.List;

import dto.TiposVehiculosDTO;
import interfaces.ITiposVehiculosManager;

public class TiposVehiculosController {
	private ITiposVehiculosManager objTiposVehiculosManager;

    public TiposVehiculosController(ITiposVehiculosManager oTiposVehiculosManager) {
        this.objTiposVehiculosManager = oTiposVehiculosManager;
    }

    public void crearTiposVehiculos(String descripcion) {
    	TiposVehiculosDTO tiposVehiculos = new TiposVehiculosDTO(null, descripcion);
    	objTiposVehiculosManager.guardarTiposVehiculos(tiposVehiculos);
    }

    public List<TiposVehiculosDTO> mostrarTiposVehiculos() {
        List<TiposVehiculosDTO> tiposVehiculos = objTiposVehiculosManager.obtenerTodos();
        return tiposVehiculos;
    }
    
    public void eliminarTiposVehiculos(Integer id)
    {
    	objTiposVehiculosManager.eliminarTiposVehiculos(id);
    }
    
    public TiposVehiculosDTO obtenerTiposVehiculosPorId(Integer id)
    {
    	TiposVehiculosDTO tiposVehiculosDTO = objTiposVehiculosManager.obtenerTiposVehiculosPorId(id);
    	return tiposVehiculosDTO;
    }
    
    public void actualizarTiposVehiculos(TiposVehiculosDTO tiposVehiculosDTO)
    {
    	objTiposVehiculosManager.actualizarTiposVehiculos(tiposVehiculosDTO);
    }
}
