package interfaces;

import java.util.List;

import dto.MovimientoPlayaDTO;

public interface IMovimientoPlayaManager {
	Integer guardarMovimientoPlaya(MovimientoPlayaDTO movimientoPlayaDTO);

	List<MovimientoPlayaDTO> obtenerTodos();

	void actualizarMovimientoPlaya(MovimientoPlayaDTO movimientoPlayaDTO);
}
