package controller;

import java.util.List;
import dto.VehiculoDTO;
import interfaces.IVehiculoManager;

public class VehiculoController {
	private IVehiculoManager objVehiculoManager;
	
	public VehiculoController(IVehiculoManager oVehiculoManager) {
		this.objVehiculoManager = oVehiculoManager;
	}
	
	public List<VehiculoDTO> obtenerVehiculos(){
		List<VehiculoDTO> listaVehiculos = objVehiculoManager.obtenerTodos();
		return listaVehiculos;
	}
	
	public Integer crearVehiculo(String patente, Integer idCliente, int idTipoVehiculo){
		VehiculoDTO vehiculo = new VehiculoDTO(null, patente, idCliente, idTipoVehiculo);
		Integer idVehiculoNuevo = objVehiculoManager.guardarVehiculo(vehiculo);
		return idVehiculoNuevo;
	}
	
	public Integer devolverIdVehiculoDeLaPatente(String patente) {
		Integer idVehiculo = objVehiculoManager.devolverIdVehiculoDeLaPatente(patente);
		return idVehiculo;
	}
}