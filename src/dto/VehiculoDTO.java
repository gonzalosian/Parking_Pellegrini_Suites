package dto;

public class VehiculoDTO {
	private Integer id;
	private String patente;
	private Integer idCliente;
	private int idTipoVehiculo;
	private TiposVehiculosDTO tiposVehiculos;
	
	public VehiculoDTO(Integer id, String patente, Integer idCliente, int idTipoVehiculo) {
		this.id = id;
		this.patente = patente;
		this.idCliente = idCliente;
		this.idTipoVehiculo = idTipoVehiculo;
	}

	public VehiculoDTO(Integer id, String patente, Integer idCliente, int idTipoVehiculo,
			TiposVehiculosDTO tiposVehiculos) {
		this.id = id;
		this.patente = patente;
		this.idCliente = idCliente;
		this.idTipoVehiculo = idTipoVehiculo;
		this.tiposVehiculos = tiposVehiculos;
	}

	public Integer getId() {
		return id;
	}

	public String getPatente() {
		return patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public int getIdTipoVehiculo() {
		return idTipoVehiculo;
	}

	public void setIdTipoVehiculo(int idTipoVehiculo) {
		this.idTipoVehiculo = idTipoVehiculo;
	}

	public TiposVehiculosDTO getTiposVehiculos() {
		return tiposVehiculos;
	}

	public void setTiposVehiculos(TiposVehiculosDTO tiposVehiculos) {
		this.tiposVehiculos = tiposVehiculos;
	}
}
