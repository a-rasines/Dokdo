package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import DataBase.TableBuilder.DataType;

/**
 * Clase encargada de gestionar la base de datos
 *
 */
public class DatabaseHandler {
	private static Connection connection;
	private static Statement statement;
	/**
	 * Comienza la conexión a la base de datos
	 * @param name nombre de la base de datos. Corresponde al nombre del archivo
	 * @return True si ha funcionado
	 */
	public static boolean connectToDatabase(String name) {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:"+name+".db");
			statement = connection.createStatement();
			statement.setQueryTimeout(5);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * Añade una tabla a la base de datos. Para hacerse de forma manual en SQL se puede hacer desde rawUpdate()
	 * @param tb la tabla del constructor
	 * @return True si ha funcionado
	 */
	public static boolean addTable(TableBuilder tb) {
		if(connection == null)return false;
		 
		try {
	        statement.executeUpdate("drop table if exists "+tb.getName());
	        statement.executeUpdate(tb.build());
	        return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * Añade una fila a una tabla. Para hacer esta función de forma manual se puede usar rawUpdate()
	 * @param table tabla a modificar
	 * @param values valores a añadir (en orden)
	 * @return True si se ha completado
	 */
	public static boolean addValueToTable(String table, String... values) {
		String v = String.join(" , ", values);
		return rawUpdate("INSERT INTO "+table+"values("+v+")");
	}
	/**
	 * Elimina todos los valores que coinciden con la condición. Para hacer esta función de forma manual se puede usar rawUpdate()
	 * @param table tabla a modificar
	 * @param condition condición de los valores a eliminar
	 * @return True si se ha completado
	 */
	public static boolean removeValuesFromTable(String table, String condition) {
		return rawUpdate("DELETE FROM "+table+" WHERE "+condition);
	}
	/**
	 * Devuelve un Set de la tabla elegida. Para hacer esta función de forma manual se puede usar rawQuery().
	 * @param table tabla de la que coger los valores
	 * @param columns columnas a coger separadas por coma o * para coger todas
	 * @param conditions condiciones de la petición
	 * @return ResultSet de lo obtenido
	 */
	public static ResultSet getFromTable(String table, String columns, String conditions) {
		return rawQuery("select "+columns+" from "+ table + (conditions==""?"": " WHERE "+conditions));
	}
	/**
	 * Devuelve un Set de la tabla elegida. Para hacer esta función de forma manual se puede usar rawQuery().
	 * @param table tabla de la que coger los valores
	 * @param columns columnas a coger separadas por coma o * para coger todas
	 * @return ResultSet de lo obtenido
	 */
	public static ResultSet getFromTable(String table, String columns) {
		return rawQuery("select "+columns+" from "+ table);
	}
	/**
	 * Manda una actualización a la base de datos (modificar/añadir datos)
	 * @param code solicitud
	 * @return True si se ha completado
	 */
	public static boolean rawUpdate(String code) {
		try {
			statement.executeUpdate(code);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static ResultSet rawQuery(String query) {
		try {
			return statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
