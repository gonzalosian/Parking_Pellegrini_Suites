package interfaces;

import java.util.List;

public interface IGenericRepository<T, ID> {
//    void crear(T entidad);
	Integer crear(T entidad, boolean returnGeneratedId);
    T obtenerPorId(ID id);
    List<T> obtenerTodos();
    void actualizar(T entidad);
    void eliminar(ID id);
}