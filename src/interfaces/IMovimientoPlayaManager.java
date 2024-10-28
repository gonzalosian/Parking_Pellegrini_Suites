package interfaces;

import dto.MovimientoPlayaDTO;

public interface IMovimientoPlayaManager {
	Integer guardarMovimientoPlaya(MovimientoPlayaDTO movimientoPlayaDTO);
	void actualizarMovimientoPlaya(MovimientoPlayaDTO movimientoPlayaDTO);
}
