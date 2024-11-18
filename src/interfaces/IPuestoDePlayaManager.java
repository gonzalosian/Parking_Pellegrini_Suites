package interfaces;

import dto.PuestoDePlayaDTO;

public interface IPuestoDePlayaManager {
	void actualizarPuestoDePlaya(PuestoDePlayaDTO puestoDePlayaDTO);

	PuestoDePlayaDTO buscarPuestoLibrePorTipoVehiculo(int idTipoVehiculo);
}
