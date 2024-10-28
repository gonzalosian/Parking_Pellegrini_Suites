package dto;

public class PuestoDePlayaDTO {
	private Integer id;
	private int numeroPuestoPlaya;
	private int idPiso;
	private int idTipoVehiculo;
    private int idEstadoPuestoPlaya;
    
    public PuestoDePlayaDTO(Integer id, int numeroPuestoPlaya, int idPiso, int idTipoVehiculo, int idEstadoPuestoPlaya) {
    	this.id = id;
    	this.numeroPuestoPlaya = numeroPuestoPlaya;
    	this.idPiso = idPiso;
    	this.idTipoVehiculo = idTipoVehiculo;
    	this.idEstadoPuestoPlaya = idEstadoPuestoPlaya;
    }

	public Integer getId() {
		return id;
	}

	public int getNumeroPuestoPlaya() {
		return numeroPuestoPlaya;
	}

	public void setNumeroPuestoPlaya(int numeroPuestoPlaya) {
		this.numeroPuestoPlaya = numeroPuestoPlaya;
	}

	public int getIdPiso() {
		return idPiso;
	}

	public void setIdPiso(int idPiso) {
		this.idPiso = idPiso;
	}

	public int getIdTipoVehiculo() {
		return idTipoVehiculo;
	}

	public void setIdTipoVehiculo(int idTipoVehiculo) {
		this.idTipoVehiculo = idTipoVehiculo;
	}

	public int getIdEstadoPuestoPlaya() {
		return idEstadoPuestoPlaya;
	}

	public void setIdEstadoPuestoPlaya(int idEstadoPuestoPlaya) {
		this.idEstadoPuestoPlaya = idEstadoPuestoPlaya;
	}
}
