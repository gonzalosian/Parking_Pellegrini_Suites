package controller;

import java.util.List;

import dto.UsuarioDTO;
import interfaces.IUsuarioManager;

public class UsuarioController {
	private IUsuarioManager objUsuarioManager;
	
	public UsuarioController(IUsuarioManager oUsuarioManager)
	{
		this.objUsuarioManager = oUsuarioManager;
	}
	
	public List<UsuarioDTO> mostrarUsuarios() {
        List<UsuarioDTO> listaUsuarios = objUsuarioManager.obtenerTodos();
        return listaUsuarios;
    }
	
	public UsuarioDTO validarLogin(String user, String pass)
	{
		return objUsuarioManager.validarLogin(user, pass);
	}
}