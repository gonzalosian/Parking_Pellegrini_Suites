package dto;

public class VehiculoDTO {
	private Integer id;
	private String patente;
	private Integer idCliente;
    private int idTipoVehiculo;
    
    public VehiculoDTO(Integer id, String patente, Integer idCliente, int idTipoVehiculo)
    {
    	this.id = id;
    	this.patente = patente;
    	this.idCliente = idCliente;
    	this.idTipoVehiculo = idTipoVehiculo;
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
}
