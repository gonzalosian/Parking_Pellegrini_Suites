package entity;

public class Vehiculos {
	private Integer id;
	private String patente;
	private Integer idCliente;
	private int idTipoVehiculo;
	private TiposVehiculos tiposVehiculos;
	
	public Vehiculos(Integer id, String patente, Integer idCliente, int idTipoVehiculo) {
		this.id = id;
		this.patente = patente;
		this.idCliente = idCliente;
		this.idTipoVehiculo = idTipoVehiculo;
	}

	public Vehiculos(Integer id, String patente, Integer idCliente, int idTipoVehiculo, TiposVehiculos tiposVehiculos) {
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

	public TiposVehiculos getTiposVehiculos() {
		return tiposVehiculos;
	}

	public void setTiposVehiculos(TiposVehiculos tiposVehiculos) {
		this.tiposVehiculos = tiposVehiculos;
	}
}
