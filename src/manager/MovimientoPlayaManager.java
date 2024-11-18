package manager;

import java.util.List;
import java.util.stream.Collectors;

import dto.MovimientoPlayaDTO;
import entity.MovimientosPlaya;
import interfaces.IGenericRepository;
import interfaces.IMovimientoPlayaManager;

public class MovimientoPlayaManager implements IMovimientoPlayaManager {
	private IGenericRepository<MovimientosPlaya, Integer> repositorio;

	public MovimientoPlayaManager(IGenericRepository<MovimientosPlaya, Integer> oRepositorio) {
		this.repositorio = oRepositorio;
	}

	/**
	 * Guardar un movimiento de playa
	 * 
	 * @param movimientoPlayaDTO
	 * @return Id del movimiento si corresponde
	 */
	public Integer guardarMovimientoPlaya(MovimientoPlayaDTO movimientoPlayaDTO) {
		try {
			MovimientosPlaya movimientoPlaya = new MovimientosPlaya(null, movimientoPlayaDTO.getIdPuestoDePlaya(),
					movimientoPlayaDTO.getIdVehiculo(), movimientoPlayaDTO.getFechaHoraIngreso(),
					movimientoPlayaDTO.getIdUsuarioIngreso(), movimientoPlayaDTO.getFechaHoraEgreso(),
					movimientoPlayaDTO.getIdUsuarioEgreso(), movimientoPlayaDTO.getCodigoBarraIn(),
					movimientoPlayaDTO.getCodigoBarraOut());
			Integer idMovimientoPlaya = repositorio.crear(movimientoPlaya, true);
			return idMovimientoPlaya;
		} catch (Exception e) {
			// Manejar la excepción y registrar el error
			System.err.println("Error al guardar el movimiento de playa: " + e.getMessage());
			e.printStackTrace();

			// Opcional: Lanzar una excepción personalizada
			throw new RuntimeException("No se pudo guardar el movimiento de playa. Por favor, intente nuevamente.", e);
		}
	}

	public List<MovimientoPlayaDTO> obtenerTodos() {
		List<MovimientosPlaya> movimientosPlaya = repositorio.obtenerTodos();
		return movimientosPlaya.stream()
				.map(m -> new MovimientoPlayaDTO(m.getId(), m.getIdPuestoDePlaya(), m.getIdVehiculo(),
						m.getFechaHoraIngreso(), m.getIdUsuarioIngreso(), m.getFechaHoraEgreso(),
						m.getIdUsuarioEgreso(), m.getCodigoBarraIn(), m.getCodigoBarraOut()))
				.collect(Collectors.toList());
	}

	/**
	 * Actualiza un movimiento de playa
	 * 
	 * @param movimientoPlayaDTO
	 */
	public void actualizarMovimientoPlaya(MovimientoPlayaDTO movimientoPlayaDTO) {
		MovimientosPlaya movimientoPlaya = new MovimientosPlaya(movimientoPlayaDTO.getId(),
				movimientoPlayaDTO.getIdPuestoDePlaya(), movimientoPlayaDTO.getIdVehiculo(),
				movimientoPlayaDTO.getFechaHoraIngreso(), movimientoPlayaDTO.getIdUsuarioIngreso(),
				movimientoPlayaDTO.getFechaHoraEgreso(), movimientoPlayaDTO.getIdUsuarioEgreso(),
				movimientoPlayaDTO.getCodigoBarraIn(), movimientoPlayaDTO.getCodigoBarraOut());
		repositorio.actualizar(movimientoPlaya);
	}
}