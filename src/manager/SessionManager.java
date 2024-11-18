package manager;

import dto.UsuarioDTO;

public class SessionManager {
	private static SessionManager instance;
	private UsuarioDTO usuarioLogueado;

	private SessionManager() {
	}

	// Para obtener la única instancia de la clase (Singleton)
	public static SessionManager getInstance() {
		if (instance == null) {
			instance = new SessionManager();
		}
		return instance;
	}

	// Para establecer el usuario logueado
	public void setUsuarioLogueado(UsuarioDTO usuario) {
		this.usuarioLogueado = usuario;
	}

	// Para obtener el usuario logueado
	public UsuarioDTO getUsuarioLogueado() {
		return usuarioLogueado;
	}

	// Para cerrar sesión
	public void cerrarSesion() {
		this.usuarioLogueado = null;
	}
}