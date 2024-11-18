package interfaces;

import java.util.List;

import dto.VehiculoDTO;

public interface IVehiculoManager {
	List<VehiculoDTO> obtenerTodos();

	Integer guardarVehiculo(VehiculoDTO vehiculoDTO);

	Integer devolverIdVehiculoDeLaPatente(String patente);
}
