package entity;

public class DescuentosPorHora {
	private Integer id;
    private int cantidadDias;
    private int porcentajeDescuento;
    
    public DescuentosPorHora(Integer id, int cantidadDias, int porcentajeDescuento) {
        this.id = id;
        this.cantidadDias = cantidadDias;
        this.porcentajeDescuento = porcentajeDescuento;
    }
    
    public DescuentosPorHora() {}

	public int getId() {
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