package entity;

public class TiposVehiculos {
	private Integer id;
	private String descripcion;

	public TiposVehiculos(Integer id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	public TiposVehiculos() {
	}

	public Integer getId() {
		return id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}