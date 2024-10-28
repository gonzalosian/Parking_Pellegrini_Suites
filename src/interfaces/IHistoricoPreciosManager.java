package interfaces;

import java.time.LocalDate;
import java.util.List;

import dto.HistoricoPreciosDTO;

public interface IHistoricoPreciosManager {
	void guardarHistoricoPrecios(HistoricoPreciosDTO historicoPreciosDTO);
	List<HistoricoPreciosDTO> obtenerTodos();
	void eliminarHistoricoPrecios(Integer id);
	HistoricoPreciosDTO obtenerHistoricoPreciosPorId(Integer id);
	void actualizarHistoricoPrecios(HistoricoPreciosDTO historicoPreciosDTO);
	Boolean validarExistenciaDeTarifas(int idTipoVehiculo, LocalDate fechaVigencia);
}
