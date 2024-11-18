package manager;

import java.util.List;
import java.util.stream.Collectors;

import dto.TiposVehiculosDTO;
import entity.TiposVehiculos;
import interfaces.IGenericRepository;
import interfaces.ITiposVehiculosManager;

public class TiposVehiculosManager implements ITiposVehiculosManager {
	private IGenericRepository<TiposVehiculos, Integer> repositorio;

	public TiposVehiculosManager(IGenericRepository<TiposVehiculos, Integer> oRepositorio) {
		this.repositorio = oRepositorio;
	}

	public void guardarTiposVehiculos(TiposVehiculosDTO tiposVehiculosDTO) {
		TiposVehiculos tiposVehiculos = new TiposVehiculos(null, tiposVehiculosDTO.getDescripcion());
		repositorio.crear(tiposVehiculos, false);
	}

	public List<TiposVehiculosDTO> obtenerTodos() {
		List<TiposVehiculos> tiposVehiculos = repositorio.obtenerTodos();
		return tiposVehiculos.stream().map(m -> new TiposVehiculosDTO(m.getId(), m.getDescripcion()))
				.collect(Collectors.toList());
	}

	public void eliminarTiposVehiculos(Integer id) {
		repositorio.eliminar(id);
	}

	public TiposVehiculosDTO obtenerTiposVehiculosPorId(Integer id) {
		TiposVehiculos tiposVehiculos = repositorio.obtenerPorId(id);
		if (tiposVehiculos != null) {
			return new TiposVehiculosDTO(tiposVehiculos.getId(), tiposVehiculos.getDescripcion());
		} else {
			return null; // O maneja una excepción si prefieres
		}
	}

	public void actualizarTiposVehiculos(TiposVehiculosDTO tiposVehiculosDTO) {
		TiposVehiculos tiposVehiculos = new TiposVehiculos(tiposVehiculosDTO.getId(),
				tiposVehiculosDTO.getDescripcion());
		repositorio.actualizar(tiposVehiculos);
	}
}