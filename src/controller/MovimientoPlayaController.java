package controller;

import java.time.LocalDateTime;
import java.util.List;

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
    	try {
    		MovimientoPlayaDTO movimientoPlaya = new MovimientoPlayaDTO(null, idPuestoDePlaya, idVehiculo, fechaHoraIngreso, idUsuarioIngreso, fechaHoraEgreso, 
    				idUsuarioEgreso, codigoBarraIn, codigoBarraOut);
    		Integer idMovimientoCheckIn = objMovimientoPlayaManager.guardarMovimientoPlaya(movimientoPlaya);
    		return idMovimientoCheckIn;			
		} catch (Exception e) {
			// Manejar la excepción y registrar el error
	        System.err.println("Error al crear el movimiento de playa: " + e.getMessage());
	        e.printStackTrace();

	        // Opcionalmente, podrías lanzar una excepción personalizada para el contexto de la aplicación
	        throw new RuntimeException("No se pudo crear el movimiento de playa. Por favor, revise los datos ingresados.");
		}
    }
    
    public List<MovimientoPlayaDTO> mostrarMovimientos() {
        List<MovimientoPlayaDTO> movimientosPlaya = objMovimientoPlayaManager.obtenerTodos();
        return movimientosPlaya;
    }
    
    public void actualizarMovimientoPlaya(MovimientoPlayaDTO movimientoPlayaDTO)
    {
    	objMovimientoPlayaManager.actualizarMovimientoPlaya(movimientoPlayaDTO);
    }
}
