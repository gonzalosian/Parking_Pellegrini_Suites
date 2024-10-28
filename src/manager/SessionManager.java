package manager;

import dto.UsuarioDTO;

public class SessionManager {
    private static SessionManager instance;
    private UsuarioDTO usuarioLogueado;

    // Constructor privado para evitar instancias externas
    private SessionManager() {}

    // Método para obtener la única instancia de la clase (Singleton)
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // Método para establecer el usuario logueado
    public void setUsuarioLogueado(UsuarioDTO usuario) {
        this.usuarioLogueado = usuario;
    }

    // Método para obtener el usuario logueado
    public UsuarioDTO getUsuarioLogueado() {
        return usuarioLogueado;
    }

    // Método para cerrar sesión
    public void cerrarSesion() {
        this.usuarioLogueado = null;
    }
}