package entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HistoricoPrecios {
	private Integer id;
	private LocalDateTime fechaVigencia;
	private LocalDateTime fechaHoraCarga;
	private int idUsuarioCarga;
	private int idTipoVehiculo;
	private BigDecimal precioPorHora;
	private BigDecimal precioPorDia;
	private BigDecimal precioLavado;
	private TiposVehiculos tiposVehiculos;
	private Usuarios usuarios;

	public HistoricoPrecios(Integer id, LocalDateTime fechaVigencia, LocalDateTime fechaHoraCarga, int idUsuarioCarga,
			int idTipoVehiculo, BigDecimal precioPorHora, BigDecimal precioPorDia, BigDecimal precioLavado) {
		// encadenamiento de constructores para reutilizar la lógica de inicialización
		this(id, fechaVigencia, fechaHoraCarga, idUsuarioCarga, idTipoVehiculo, precioPorHora, precioPorDia,
				precioLavado, null, null);
		/**
		 * Ventajas del Encadenamiento de Constructores
		 * - Reducción de duplicación: La lógica común se centraliza en un único constructor. 
		 * - Mantenimiento más sencillo: Si necesitas agregar o cambiar un atributo, solo modificas un lugar.
		 * - Legibilidad: Es más claro que un constructor llama al otro con valores predeterminados.
		 */
	}

	public HistoricoPrecios(Integer id, LocalDateTime fechaVigencia, LocalDateTime fechaHoraCarga, int idUsuarioCarga,
			int idTipoVehiculo, BigDecimal precioPorHora, BigDecimal precioPorDia, BigDecimal precioLavado,
			TiposVehiculos tiposVehiculos, Usuarios usuarios) {
		this.id = id;
		this.fechaVigencia = fechaVigencia;
		this.fechaHoraCarga = fechaHoraCarga;
		this.idUsuarioCarga = idUsuarioCarga;
		this.idTipoVehiculo = idTipoVehiculo;
		this.precioPorHora = precioPorHora;
		this.precioPorDia = precioPorDia;
		this.precioLavado = precioLavado;
		this.tiposVehiculos = tiposVehiculos;
		this.usuarios = usuarios;
	}

	public HistoricoPrecios() {
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

	public TiposVehiculos getTiposVehiculos() {
		return tiposVehiculos;
	}

	public void setTiposVehiculos(TiposVehiculos tiposVehiculos) {
		this.tiposVehiculos = tiposVehiculos;
	}

	public Usuarios getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Usuarios usuarios) {
		this.usuarios = usuarios;
	}
}