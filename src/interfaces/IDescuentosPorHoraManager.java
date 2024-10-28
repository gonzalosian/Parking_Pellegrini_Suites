package interfaces;

import java.util.List;
import dto.DescuentoPorHoraDTO;

public interface IDescuentosPorHoraManager {
	void guardarDescuentosPorHora(DescuentoPorHoraDTO descuentoPorHoraDTO);
	List<DescuentoPorHoraDTO> obtenerTodos();
	void eliminarDescuentoPorHora(Integer id);
	DescuentoPorHoraDTO obtenerDescuentoPorId(Integer id);
	void actualizarDescuentoPorHora(DescuentoPorHoraDTO descuentoPorHoraDTO);
	Boolean validarExistenciaDeDescuento(int cantidadHoras);
}