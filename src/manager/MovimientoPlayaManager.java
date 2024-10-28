package manager;

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
	 * @param movimientoPlayaDTO
	 * @return Id del movimiento si corresponde
	 */
	public Integer guardarMovimientoPlaya(MovimientoPlayaDTO movimientoPlayaDTO) {
		MovimientosPlaya movimientoPlaya = new MovimientosPlaya(null, movimientoPlayaDTO.getIdPuestoDePlaya(), movimientoPlayaDTO.getIdVehiculo(), 
				movimientoPlayaDTO.getFechaHoraIngreso(), movimientoPlayaDTO.getIdUsuarioIngreso(), movimientoPlayaDTO.getFechaHoraEgreso(), movimientoPlayaDTO.getIdUsuarioEgreso(), 
				movimientoPlayaDTO.getCodigoBarraIn(), movimientoPlayaDTO.getCodigoBarraOut());
        Integer idMovimientoPlaya = repositorio.crear(movimientoPlaya, true);
        return idMovimientoPlaya;
    }
	
	/**
	 * Actualiza un movimiento de playa
	 * @param movimientoPlayaDTO
	 */
	public void actualizarMovimientoPlaya(MovimientoPlayaDTO movimientoPlayaDTO) {
		MovimientosPlaya movimientoPlaya = new MovimientosPlaya(movimientoPlayaDTO.getId(), movimientoPlayaDTO.getIdPuestoDePlaya(), movimientoPlayaDTO.getIdVehiculo(), 
				movimientoPlayaDTO.getFechaHoraIngreso(), movimientoPlayaDTO.getIdUsuarioIngreso(), movimientoPlayaDTO.getFechaHoraEgreso(), movimientoPlayaDTO.getIdUsuarioEgreso(),
				movimientoPlayaDTO.getCodigoBarraIn(), movimientoPlayaDTO.getCodigoBarraOut());
        repositorio.actualizar(movimientoPlaya);
    }
}