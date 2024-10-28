package entity;

public class Usuarios {
	private Integer id;
	private String apellido;
	private String nombre;
    private int dni;
    private int idCargo;
    private String userName;
    
    public Usuarios(Integer id, String apellido, String nombre, int dni, int idCargo, String userName)
    {
    	this.id = id;
    	this.apellido = apellido;
    	this.nombre = nombre;
    	this.dni = dni;
    	this.idCargo = idCargo;
    	this.userName = userName;
    }

	public Integer getId() {
		return id;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public int getIdCargo() {
		return idCargo;
	}

	public void setIdCargo(int idCargo) {
		this.idCargo = idCargo;
	}
	
	public String getUserName() { return userName; }
	public void setUserName(String userName) { this.userName = userName; }
}
