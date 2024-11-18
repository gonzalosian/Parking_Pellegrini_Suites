package interfaces;

import java.util.List;

import dto.TiposVehiculosDTO;

public interface ITiposVehiculosManager {
	void guardarTiposVehiculos(TiposVehiculosDTO tiposVehiculosDTO);

	List<TiposVehiculosDTO> obtenerTodos();

	void eliminarTiposVehiculos(Integer id);

	TiposVehiculosDTO obtenerTiposVehiculosPorId(Integer id);

	void actualizarTiposVehiculos(TiposVehiculosDTO tiposVehiculosDTO);
}
