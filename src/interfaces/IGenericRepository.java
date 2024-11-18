package interfaces;

import java.util.List;

public interface IGenericRepository<T, ID> {
	Integer crear(T entidad, boolean returnGeneratedId);

	T obtenerPorId(ID id);

	List<T> obtenerTodos();

	void actualizar(T entidad);

	void eliminar(ID id);
}