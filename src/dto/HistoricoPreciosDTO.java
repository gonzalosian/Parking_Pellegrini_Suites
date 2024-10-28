package dto;

import java.time.LocalDateTime;

public class HistoricoPreciosDTO {
	private Integer id;
	private LocalDateTime fechaVigencia;
	private LocalDateTime fechaHoraCarga;
	private int idUsuarioCarga;
	private int idTipoVehiculo;
	private int precioPorHora;
	private int precioPorDia;
	private int precioLavado;
	
	public HistoricoPreciosDTO(Integer id, LocalDateTime fechaVigencia, LocalDateTime fechaHoraCarga, int idUsuarioCarga, int idTipoVehiculo,
			int precioPorHora, int precioPorDia, int precioLavado) {
    	this.id = id;
    	this.fechaVigencia = fechaVigencia;
    	this.fechaHoraCarga = fechaHoraCarga;
    	this.idUsuarioCarga = idUsuarioCarga;
    	this.idTipoVehiculo = idTipoVehiculo;
    	this.precioPorHora = precioPorHora;
    	this.precioPorDia = precioPorDia;
    	this.precioLavado = precioLavado;
    }

	public Integer getId() {
		return id;
	}

	public LocalDateTime getFechaVigencia() {
		return fechaVigencia;
	}

	public void setFechaVigencia(LocalDateTime fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}

	public LocalDateTime getFechaHoraCarga() {
		return fechaHoraCarga;
	}

	public void setFechaHoraCarga(LocalDateTime fechaHoraCarga) {
		this.fechaHoraCarga = fechaHoraCarga;
	}

	public int getIdUsuarioCarga() {
		return idUsuarioCarga;
	}

	public void setIdUsuarioCarga(int idUsuarioCarga) {
		this.idUsuarioCarga = idUsuarioCarga;
	}

	public int getIdTipoVehiculo() {
		return idTipoVehiculo;
	}

	public void setIdTipoVehiculo(int idTipoVehiculo) {
		this.idTipoVehiculo = idTipoVehiculo;
	}

	public int getPrecioPorHora() {
		return precioPorHora;
	}

	public void setPrecioPorHora(int precioPorHora) {
		this.precioPorHora = precioPorHora;
	}

	public int getPrecioPorDia() {
		return precioPorDia;
	}

	public void setPrecioPorDia(int precioPorDia) {
		this.precioPorDia = precioPorDia;
	}

	public int getPrecioLavado() {
		return precioLavado;
	}

	public void setPrecioLavado(int precioLavado) {
		this.precioLavado = precioLavado;
	}
}
