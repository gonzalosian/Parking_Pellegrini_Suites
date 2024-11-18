package dto;

public class TiposVehiculosDTO {
	private Integer id;
	private String descripcion;
	
	public TiposVehiculosDTO(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
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