package dto;

public class DescuentoPorHoraDTO {
	private Integer id;
    private int cantidadDias;
    private int porcentajeDescuento;
    
    public DescuentoPorHoraDTO(Integer id, int cantidadDias, int porcentajeDescuento) {
        this.id = id;
        this.cantidadDias = cantidadDias;
        this.porcentajeDescuento = porcentajeDescuento;
    }
    
    public Integer getId() {
		return id;
	}

	public int getCantidadDias() {
		return cantidadDias;
	}

	public void setCantidadDias(int cantidadDias) {
		this.cantidadDias = cantidadDias;
	}

	public int getPorcentajeDescuento() {
		return porcentajeDescuento;
	}

	public void setPorcentajeDescuento(int porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}
}
