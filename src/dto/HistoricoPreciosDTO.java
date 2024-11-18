package dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HistoricoPreciosDTO {
	private Integer id;
	private LocalDateTime fechaVigencia;
	private LocalDateTime fechaHoraCarga;
	private int idUsuarioCarga;
	private int idTipoVehiculo;
	private BigDecimal precioPorHora;
	private BigDecimal precioPorDia;
	private BigDecimal precioLavado;
	private TiposVehiculosDTO tipoVehiculo;
	private UsuarioDTO usuario;
	
	public HistoricoPreciosDTO(Integer id, LocalDateTime fechaVigencia, LocalDateTime fechaHoraCarga, int idUsuarioCarga,
			int idTipoVehiculo, BigDecimal precioPorHora, BigDecimal precioPorDia, BigDecimal precioLavado) {
		this(id, fechaVigencia, fechaHoraCarga, idUsuarioCarga, idTipoVehiculo, precioPorHora, precioPorDia,
				precioLavado, null, null);
	}

	public HistoricoPreciosDTO(Integer id, LocalDateTime fechaVigencia, LocalDateTime fechaHoraCarga,
			int idUsuarioCarga, int idTipoVehiculo, BigDecimal precioPorHora, BigDecimal precioPorDia,
			BigDecimal precioLavado, TiposVehiculosDTO tipoVehiculo, UsuarioDTO usuario) {
		this.id = id;
		this.fechaVigencia = fechaVigencia;
		this.fechaHoraCarga = fechaHoraCarga;
		this.idUsuarioCarga = idUsuarioCarga;
		this.idTipoVehiculo = idTipoVehiculo;
		this.precioPorHora = precioPorHora;
		this.precioPorDia = precioPorDia;
		this.precioLavado = precioLavado;
		this.tipoVehiculo = tipoVehiculo;
		this.usuario = usuario;
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

	public BigDecimal getPrecioPorHora() {
		return precioPorHora;
	}

	public void setPrecioPorHora(BigDecimal precioPorHora) {
		this.precioPorHora = precioPorHora;
	}

	public BigDecimal getPrecioPorDia() {
		return precioPorDia;
	}

	public void setPrecioPorDia(BigDecimal precioPorDia) {
		this.precioPorDia = precioPorDia;
	}

	public BigDecimal getPrecioLavado() {
		return precioLavado;
	}

	public void setPrecioLavado(BigDecimal precioLavado) {
		this.precioLavado = precioLavado;
	}

	public TiposVehiculosDTO getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(TiposVehiculosDTO tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}
}
