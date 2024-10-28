package repository;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    @Override
//    public void crear(T entidad) {
//        // Obtener los nombres de las columnas a partir de los campos de la clase T
//        Field[] fields = entityClass.getDeclaredFields();
//        StringBuilder columnNames = new StringBuilder();
//        StringBuilder valuesPlaceholders = new StringBuilder();
//
//        for (Field field : fields) {
//            columnNames.append(field.getName()).append(",");
//            valuesPlaceholders.append("?,");
//        }
//
//        // Quitar la última coma
//        columnNames.setLength(columnNames.length() - 1);
//        valuesPlaceholders.setLength(valuesPlaceholders.length() - 1);
//
//        String sql = "INSERT INTO " + entityClass.getSimpleName() + " (" + columnNames + ") VALUES (" + valuesPlaceholders + ")";
//
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            for (int i = 0; i < fields.length; i++) {
//                fields[i].setAccessible(true);
//                stmt.setObject(i + 1, fields[i].get(entidad)); // Setear el valor en el PreparedStatement
//            }
//            stmt.executeUpdate();
//        } catch (SQLException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
    public Integer crear(T entidad, boolean returnGeneratedId) {
        Field[] fields = entityClass.getDeclaredFields();
        StringBuilder columnNames = new StringBuilder();
        StringBuilder valuesPlaceholders = new StringBuilder();

        for (Field field : fields) {
            columnNames.append(field.getName()).append(",");
            valuesPlaceholders.append("?,");
        }

        // Quitar la última coma
        columnNames.setLength(columnNames.length() - 1);
        valuesPlaceholders.setLength(valuesPlaceholders.length() - 1);

        String sql = "INSERT INTO " + entityClass.getSimpleName() + " (" + columnNames + ") VALUES (" + valuesPlaceholders + ")";
        Integer generatedId = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql, returnGeneratedId ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS)) {
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                stmt.setObject(i + 1, fields[i].get(entidad));
            }

            stmt.executeUpdate();

            // Si se requiere el ID generado, recuperarlo
            if (returnGeneratedId) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
            }
        } catch (SQLException | IllegalAccessException e) {
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
        } catch (SQLException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
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
                T entidad = entityClass.getDeclaredConstructor().newInstance();
                Field[] fields = entityClass.getDeclaredFields();

                for (Field field : fields) {
                    field.setAccessible(true);
                    field.set(entidad, rs.getObject(field.getName()));
                }
                entities.add(entidad);
            }
        } catch (SQLException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return entities;
    }

    @Override
    public void actualizar(T entidad) {
    	Field[] fields = entityClass.getDeclaredFields();
        StringBuilder setClause = new StringBuilder();

        for (Field field : fields) {
            if (!field.getName().equals("id")) { // Asumiendo que 'id' es la clave primaria y no se actualiza
                setClause.append(field.getName()).append(" = ?,");
            }
        }

        setClause.setLength(setClause.length() - 1); // Eliminar la última coma
        String sql = "UPDATE " + entityClass.getSimpleName() + " SET " + setClause + " WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;

            for (Field field : fields) {
                field.setAccessible(true);
                if (!field.getName().equals("id")) {
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