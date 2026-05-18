package dao;

import connection.DatabaseConnection;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class GenericDAO<T> {

    public void save(T object, String tableName) {

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            Class<?> clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();

            StringBuilder columns = new StringBuilder();
            StringBuilder values = new StringBuilder();

            for (Field field : fields) {

                field.setAccessible(true);
                
                String fieldName = field.getName();
                if (fieldName.equals("id") || fieldName.equals("prix_total")) {
                    continue;
                }
                

                columns.append(fieldName).append(",");

                values.append("?,");
            }

            columns.deleteCharAt(columns.length() - 1);
            values.deleteCharAt(values.length() - 1);

            String sql = "INSERT INTO " + tableName +
                    " (" + columns + ") VALUES (" + values + ")";

            PreparedStatement ps =
                    connection.prepareStatement(sql);

            int parameterIndex = 1;
            for (int i = 0; i < fields.length; i++) {

                fields[i].setAccessible(true);
                
                String fieldName = fields[i].getName();
                if (fieldName.equals("id") || fieldName.equals("prix_total")) {
                    continue;
                }

                ps.setObject(parameterIndex++, fields[i].get(object));
            }

            ps.executeUpdate();

            System.out.println("Insertion réussie");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static List<T> findAll(Class<T> clazz) {

        List<T> result = new ArrayList<>();

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            String sql = "SELECT * FROM " + clazz.getSimpleName().toLowerCase();

            PreparedStatement ps =
                    connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                T obj = clazz.newInstance();

                for (Field field : clazz.getDeclaredFields()) {

                    field.setAccessible(true);
                    field.set(obj, rs.getObject(field.getName()));
                }

                result.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}