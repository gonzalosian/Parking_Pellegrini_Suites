package interfaces;

import java.util.List;

import dto.UsuarioDTO;

public interface IUsuarioManager {
	List<UsuarioDTO> obtenerTodos();

	UsuarioDTO validarLogin(String user, String pass);
}