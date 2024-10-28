package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import dto.UsuarioDTO;
import entity.Usuarios;
import interfaces.IGenericRepository;
import interfaces.IUsuarioManager;

public class UsuarioManager implements IUsuarioManager {
	private IGenericRepository<Usuarios, Integer> repositorio;
	private final Connection connection;
	
	public UsuarioManager(IGenericRepository<Usuarios, Integer> oRepositorio, Connection connection) {
        this.repositorio = oRepositorio;
        this.connection = connection;
    }

	public List<UsuarioDTO> obtenerTodos() {
        List<Usuarios> listaUsuarios = repositorio.obtenerTodos();
        return listaUsuarios.stream()
                         .map(m -> new UsuarioDTO(m.getId(), m.getDni(), m.getApellido(), m.getNombre(), m.getIdCargo(), m.getUserName()))
                         .collect(Collectors.toList());
    }
	
	public boolean validarLogin2(String user, String pass)
	{
		boolean encontrado = false;
		
		String userGenerico = "jperez";
		String passGenerico = "123";
		
		if (user.equals(userGenerico) && pass.equals(passGenerico)) {
			encontrado = true;
		}
		
		return encontrado;
	}
	
	/**
	 * Devuelve un usuario para el user y pass ingresado.
	 * @param user userName ingresado
	 * @param pass userPass ingresado
	 * @return UsuarioDTO
	 * */
	public UsuarioDTO validarLogin(String user, String pass) {
	    String sql = "SELECT id, apellido, nombre, dni, idCargo, userName, userPass " +
	                 "FROM Usuarios " +
	                 "WHERE userName = ? AND userPass = ?";
	    
	    UsuarioDTO usuario = null;

	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setString(1, user);
	        stmt.setString(2, pass);

	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	        	usuario = new UsuarioDTO(
	        			rs.getInt("id"), 
	        			rs.getInt("dni"), 
	        			rs.getString("apellido"), 
	        			rs.getString("nombre"), 
	        			rs.getInt("idCargo"), 
	        			rs.getString("userName"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return usuario;
	}
}
