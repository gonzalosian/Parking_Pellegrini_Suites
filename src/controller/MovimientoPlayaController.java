package controller;

import java.time.LocalDateTime;

import dto.MovimientoPlayaDTO;
import interfaces.IMovimientoPlayaManager;

public class MovimientoPlayaController {
	private IMovimientoPlayaManager objMovimientoPlayaManager;

    public MovimientoPlayaController(IMovimientoPlayaManager oMovimientoPlayaManager) {
        this.objMovimientoPlayaManager = oMovimientoPlayaManager;
    }
    
    public Integer crearMovimientoPlaya(int idPuestoDePlaya, int idVehiculo, LocalDateTime fechaHoraIngreso, int idUsuarioIngreso, LocalDateTime fechaHoraEgreso, 
    		Integer idUsuarioEgreso, int codigoBarraIn, Integer codigoBarraOut)
    {
    	MovimientoPlayaDTO movimientoPlaya = new MovimientoPlayaDTO(null, idPuestoDePlaya, idVehiculo, fechaHoraIngreso, idUsuarioIngreso, fechaHoraEgreso, 
        		idUsuarioEgreso, codigoBarraIn, codigoBarraOut);
    	Integer idMovimientoCheckIn = objMovimientoPlayaManager.guardarMovimientoPlaya(movimientoPlaya);
    	return idMovimientoCheckIn;
    }
    
    public void actualizarMovimientoPlaya(MovimientoPlayaDTO movimientoPlayaDTO)
    {
    	objMovimientoPlayaManager.actualizarMovimientoPlaya(movimientoPlayaDTO);
    }
}
