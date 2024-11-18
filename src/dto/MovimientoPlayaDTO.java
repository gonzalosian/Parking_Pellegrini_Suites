package dto;

import java.time.LocalDateTime;

public class MovimientoPlayaDTO {
	private Integer id;
	private int idPuestoDePlaya;
	private int idVehiculo;
    private LocalDateTime fechaHoraIngreso;
    private int idUsuarioIngreso;
    private LocalDateTime fechaHoraEgreso;
	private Integer idUsuarioEgreso;
    private int codigoBarraIn;
    private Integer codigoBarraOut;
    
    public MovimientoPlayaDTO(Integer id, int idPuestoDePlaya, int idVehiculo, LocalDateTime fechaHoraIngreso, int idUsuarioIngreso, 
    		LocalDateTime fechaHoraEgreso, Integer idUsuarioEgreso, int codigoBarraIn, Integer codigoBarraOut) {
    	this.id = id;
    	this.idPuestoDePlaya = idPuestoDePlaya;
    	this.idVehiculo = idVehiculo;
    	this.fechaHoraIngreso = fechaHoraIngreso;
    	this.idUsuarioIngreso = idUsuarioIngreso;
    	this.fechaHoraEgreso = fechaHoraEgreso;
    	this.idUsuarioEgreso = idUsuarioEgreso;
    	this.codigoBarraIn = codigoBarraIn;
    	this.codigoBarraOut = codigoBarraOut;
    }

	public Integer getId() {
		return id;
	}

	public int getIdPuestoDePlaya() {
		return idPuestoDePlaya;
	}

	public void setIdPuestoDePlaya(int idPuestoDePlaya) {
		this.idPuestoDePlaya = idPuestoDePlaya;
	}

	public int getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(int idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public LocalDateTime getFechaHoraIngreso() {
		return fechaHoraIngreso;
	}

	public void setFechaHoraIngreso(LocalDateTime fechaHoraIngreso) {
		this.fechaHoraIngreso = fechaHoraIngreso;
	}

	public int getIdUsuarioIngreso() {
		return idUsuarioIngreso;
	}

	public void setIdUsuarioIngreso(int idUsuarioIngreso) {
		this.idUsuarioIngreso = idUsuarioIngreso;
	}

	public LocalDateTime getFechaHoraEgreso() {
		return fechaHoraEgreso;
	}

	public void setFechaHoraEgreso(LocalDateTime fechaHoraEgreso) {
		this.fechaHoraEgreso = fechaHoraEgreso;
	}

	public Integer getIdUsuarioEgreso() {
		return idUsuarioEgreso;
	}

	public void setIdUsuarioEgreso(Integer idUsuarioEgreso) {
		this.idUsuarioEgreso = idUsuarioEgreso;
	}

	public int getCodigoBarraIn() {
		return codigoBarraIn;
	}

	public void setCodigoBarraIn(int codigoBarraIn) {
		this.codigoBarraIn = codigoBarraIn;
	}

	public Integer getCodigoBarraOut() {
		return codigoBarraOut;
	}

	public void setCodigoBarraOut(Integer codigoBarraOut) {
		this.codigoBarraOut = codigoBarraOut;
	}
}
