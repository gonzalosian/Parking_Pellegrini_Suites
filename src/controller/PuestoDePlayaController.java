package controller;

import dto.PuestoDePlayaDTO;
import interfaces.IPuestoDePlayaManager;

public class PuestoDePlayaController {
	private IPuestoDePlayaManager objPuestoDePlayaManager;

    public PuestoDePlayaController(IPuestoDePlayaManager oPuestoDePlayaManager) {
        this.objPuestoDePlayaManager = oPuestoDePlayaManager;
    }
    
    public void actualizarPuestoDePlaya(PuestoDePlayaDTO puestoDePlayaDTO)
    {
    	objPuestoDePlayaManager.actualizarPuestoDePlaya(puestoDePlayaDTO);
    }
    
    public PuestoDePlayaDTO buscarPuestoLibrePorTipoVehiculo(int idTipoVehiculo) {
    	PuestoDePlayaDTO puestoLibre = objPuestoDePlayaManager.buscarPuestoLibrePorTipoVehiculo(idTipoVehiculo);
    	return puestoLibre;
    }
}
