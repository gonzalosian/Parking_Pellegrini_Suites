package repository;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import interfaces.IGenericRepository;

public class GenericRepository<T, ID> implements IGenericRepository<T, ID> {
	private final Connection connection;
	private final Class<T> entityClass;

	public GenericRepository(Connection connection, Class<T> entityClass) {
		this.connection = connection;
		this.entityClass = entityClass;
	}

	public Integer crear(T entidad, boolean returnGeneratedId) {
		Field[] fields = entityClass.getDeclaredFields();
		StringBuilder columnNames = new StringBuilder();
		StringBuilder valuesPlaceholders = new StringBuilder();
		int cantidadCamposIncluidos = 0;

		try {
			for (Field field : fields) {
				// Filtramos solo los tipos básicos y los wrappers comunes, excluyendo objetos
				// complejos
				if (field.getType().isPrimitive() || field.getType() == Integer.class || field.getType() == String.class
						|| field.getType() == Double.class || field.getType() == Float.class
						|| field.getType() == Long.class || field.getType() == Boolean.class
						|| field.getType() == Short.class || field.getType() == Byte.class
						|| field.getType() == BigDecimal.class || field.getType() == LocalDateTime.class) {

					columnNames.append(field.getName()).append(",");
					valuesPlaceholders.append("?,");

					cantidadCamposIncluidos += 1;
				} else {
					System.out.println("Se excluye una clase compleja: " + field.getName());
				}
			}

			// Quitar la última coma
			if (cantidadCamposIncluidos > 0) {
				columnNames.setLength(columnNames.length() - 1);
				valuesPlaceholders.setLength(valuesPlaceholders.length() - 1);
			} else {
				throw new IllegalStateException("No se incluyeron campos válidos para la inserción.");
			}
		} catch (Exception e) {
			System.err.println("Error procesando los campos de la entidad: " + e.getMessage());
			e.printStackTrace();
			return null; // Si hay un error al procesar los campos, retornar nulo
		}

		String sql = "INSERT INTO " + entityClass.getSimpleName() + " (" + columnNames + ") VALUES ("
				+ valuesPlaceholders + ")";
		Integer generatedId = null;

		try (PreparedStatement stmt = connection.prepareStatement(sql,
				returnGeneratedId ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS)) {
			try {
				for (int i = 0, j = 0; i < fields.length; i++) {
					if (fields[i].getType().isPrimitive() || fields[i].getType() == Integer.class
							|| fields[i].getType() == String.class || fields[i].getType() == Double.class
							|| fields[i].getType() == Float.class || fields[i].getType() == Long.class
							|| fields[i].getType() == Boolean.class || fields[i].getType() == Short.class
							|| fields[i].getType() == Byte.class || fields[i].getType() == BigDecimal.class
							|| fields[i].getType() == LocalDateTime.class) {

						fields[i].setAccessible(true);
						stmt.setObject(++j, fields[i].get(entidad));
					}
				}
			} catch (IllegalAccessException e) {
				System.err.println("Error al acceder a los campos de la entidad: " + e.getMessage());
				e.printStackTrace();
				return null; // Si hay un error al acceder a los campos, retornar nulo
			}

			stmt.executeUpdate();

			// Si se requiere el ID generado, recuperarlo
			if (returnGeneratedId) {
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					generatedId = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			System.err.println("Error al ejecutar la consulta SQL: " + e.getMessage());
			e.printStackTrace();
		}

		return generatedId;
	}

	@Override
	public T obtenerPorId(ID id) {
		String sql = "SELECT * FROM " + entityClass.getSimpleName() + " WHERE id = ?";
		T entidad = null;

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setObject(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				entidad = entityClass.getDeclaredConstructor().newInstance();
				Field[] fields = entityClass.getDeclaredFields();

				for (Field field : fields) {
					field.setAccessible(true);
					field.set(entidad, rs.getObject(field.getName()));
				}
			}
		} catch (SQLException | IllegalAccessException | InstantiationException | NoSuchMethodException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		return entidad;
	}

	@Override
	public List<T> obtenerTodos() {
		List<T> entities = new ArrayList<>();
		String sql = "SELECT * FROM " + entityClass.getSimpleName();

		try (Statement stmt = connection.createStatement()) {
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				try {
					T entidad = entityClass.getDeclaredConstructor().newInstance();
					Field[] fields = entityClass.getDeclaredFields();

					for (Field field : fields) {

						if (field.getType().isPrimitive() || field.getType() == Integer.class
								|| field.getType() == String.class || field.getType() == Double.class
								|| field.getType() == Float.class || field.getType() == Long.class
								|| field.getType() == Boolean.class || field.getType() == Short.class
								|| field.getType() == Byte.class || field.getType() == BigDecimal.class
								|| field.getType() == LocalDateTime.class) {
							field.setAccessible(true);
							field.set(entidad, rs.getObject(field.getName()));
						}
					}
					entities.add(entidad);
				} catch (Exception e) {
					System.err.println("Error al procesar una entidad: " + e.getMessage());
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			System.err.println("Error al ejecutar la consulta SQL: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Error al ejecutar la consulta SQL.", e);
		} catch (Exception e) {
			System.err.println("Error al instanciar la entidad: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Error al instanciar la entidad.", e);
		}

		return entities;
	}

	@Override
	public void actualizar(T entidad) {
		Field[] fields = entityClass.getDeclaredFields();
		StringBuilder setClause = new StringBuilder();

		for (Field field : fields) {
			if (!field.getName().equals("id") && (field.getType().isPrimitive() || field.getType() == Integer.class
					|| field.getType() == String.class || field.getType() == Double.class
					|| field.getType() == Float.class || field.getType() == Long.class
					|| field.getType() == Boolean.class || field.getType() == Short.class
					|| field.getType() == Byte.class || field.getType() == BigDecimal.class
					|| field.getType() == LocalDateTime.class)) { // Asumiendo que 'id' es la clave primaria y no se
																	// actualiza
				setClause.append(field.getName()).append(" = ?,");
			}
		}

		setClause.setLength(setClause.length() - 1); // Eliminar la última coma
		String sql = "UPDATE " + entityClass.getSimpleName() + " SET " + setClause + " WHERE id = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			int index = 1;

			for (Field field : fields) {
				field.setAccessible(true);
				if (!field.getName().equals("id") && (field.getType().isPrimitive() || field.getType() == Integer.class
						|| field.getType() == String.class || field.getType() == Double.class
						|| field.getType() == Float.class || field.getType() == Long.class
						|| field.getType() == Boolean.class || field.getType() == Short.class
						|| field.getType() == Byte.class || field.getType() == BigDecimal.class
						|| field.getType() == LocalDateTime.class)) {
					stmt.setObject(index++, field.get(entidad));
				}
			}

			// Setear el id al final
			Field idField = entityClass.getDeclaredField("id");
			idField.setAccessible(true);
			stmt.setObject(index, idField.get(entidad));

			stmt.executeUpdate();
		} catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void eliminar(ID id) {
		String sql = "DELETE FROM " + entityClass.getSimpleName() + " WHERE id = ?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setObject(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}