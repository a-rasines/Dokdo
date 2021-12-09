package DataBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Clase encargada de gestionar la base de datos
 *
 */
public class DatabaseHandler {
	private static Connection connection;
	private static Logger logger= Logger.getLogger("DatabaseHandler");
	private static Statement statement;
	private static JSONObject json;
	/**
	 * Comienza la conexión a la base de datos
	 * @param name nombre de la base de datos. Corresponde al nombre del archivo
	 * @return True si ha funcionado
	 */
	public static boolean connectToDatabase(String name) {
		try {
			logger.log(Level.INFO, "Trying connection to: "+ name+".db");
			connection = DriverManager.getConnection("jdbc:sqlite:"+name+".db");
			statement = connection.createStatement();
			statement.setQueryTimeout(5);
			return true;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error connecting to database: "+e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * Añade una tabla a la base de datos. Para hacerse de forma manual en SQL se puede hacer desde SQLrawUpdate()
	 * @param tb la tabla del constructor
	 * @return True si ha funcionado
	 */
	public static boolean addTable(TableBuilder tb) {
		if(connection == null)return false;
		 
		try {
			logger.log(Level.INFO, "Trying to update: "+tb.build());
	        statement.executeUpdate(tb.build());
	        return true;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error adding table to database: "+e.getMessage());
			return false;
		}
	}
	/**
	 * Añade una fila a una tabla. Para hacer esta función de forma manual se puede usar SQLrawUpdate()
	 * @param table tabla a modificar
	 * @param values valores a añadir (en orden)
	 * @return True si se ha completado
	 */
	public static boolean addValueToTable(String table, String... values) {
		String v = String.join(" , ", values);
		return SQLrawUpdate("INSERT INTO "+table+"values("+v+")");
	}
	/**
	 * Elimina todos los valores que coinciden con la condición. Para hacer esta función de forma manual se puede usar SQLrawUpdate()
	 * @param table tabla a modificar
	 * @param condition condición de los valores a eliminar
	 * @return True si se ha completado
	 */
	public static boolean removeValuesFromTable(String table, String condition) {
		return SQLrawUpdate("DELETE FROM "+table+" WHERE "+condition);
	}
	/**
	 * Devuelve un Set de la tabla elegida. Para hacer esta función de forma manual se puede usar SQLrawQuery().
	 * @param table tabla de la que coger los valores
	 * @param columns columnas a coger separadas por coma o * para coger todas
	 * @param conditions condiciones de la petición
	 * @return ResultSet de lo obtenido
	 */
	public static ResultSet getFromTable(String table, String columns, String conditions) {
		return SQLrawQuery("select "+columns+" from "+ table + (conditions==""?"": " WHERE "+conditions));
	}
	/**
	 * Devuelve un Set de la tabla elegida. Para hacer esta función de forma manual se puede usar SQLrawQuery().
	 * @param table tabla de la que coger los valores
	 * @param columns columnas a coger separadas por coma o * para coger todas
	 * @return ResultSet de lo obtenido
	 */
	public static ResultSet getFromTable(String table, String columns) {
		return SQLrawQuery("select "+columns+" from "+ table);
	}
	/**
	 * Manda una actualización a la base de datos (modificar/añadir datos)
	 * @param code solicitud
	 * @return True si se ha completado
	 */
	public static boolean SQLrawUpdate(String code) {
		try {
			logger.log(Level.INFO, "Trying to update: "+code);
			statement.executeUpdate(code);
			return true;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error updating database: "+e.getMessage());
			return false;
		}
	}
	public static ResultSet SQLrawQuery(String query) {
		try {
			logger.log(Level.INFO, "Trying to query: "+query);
			return statement.executeQuery(query);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error during query to database: "+e.getMessage());
			return null;
		}
	}
	/**
	 * Escribe un objeto a JSON
	 * @param key llave del objeto
	 * @param v objeto a meter
	 * @param override ¿Cambia el valor de la llave o añadir a lista de valores?(Solo valido para JSONArray)
	 */
	@SuppressWarnings("unchecked")
	public static void writeToJSON(String key, Object v, boolean override) {
		if(!json.containsKey(key)||override)json.put(key, v);
		else {
			JSONArray a = (JSONArray) json.get(key);
			a.add(v);
		}
		try (FileWriter file = new FileWriter("src/data.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(json.toJSONString()); 
            file.flush();
 
        } catch (IOException e) {
        	logger.log(Level.SEVERE, "Error writting to JSON: "+e.getMessage()+" Tried to insert in "+key+" a(n) "+v.getClass().getName()+".("+v.toString()+") override= "+override);
        }
	}
	/**
	 * Elimina objeto de JSONArray
	 * @param key llave del objeto
	 * @param v objeto a borrar
	 */
	public static void removeFromJSONArray(String key, Object v) {
		logger.log(Level.INFO, "Trying to remove a "+v.getClass().getName()+"("+v.toString()+") from "+key);
		JSONArray a = (JSONArray) json.get(key);
		a.remove(v);
		try (FileWriter file = new FileWriter("data.json")) {
            file.write(json.toJSONString()); 
            file.flush();
 
        } catch (IOException e) {
        	logger.log(Level.SEVERE, "Error removing from JSON: "+e.getMessage());
        }
	}
	/**
	 * Carga el JSON
	 * @param def default si falla el cargado
	 */
	@SuppressWarnings("unchecked")
	public static void loadJSon(String def) {
		logger.log(Level.INFO, "Loading JSON");
		JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("src/data.json")){
            json = (JSONObject)jsonParser.parse(reader);
            if (!json.keySet().equals(defaultJSON().keySet())) {
            	defaultJSON().forEach((k, v)->{
            		if(!json.containsKey(k.toString()))
            			writeToJSON(k.toString(), v, true);
            	});
            }
        } catch (FileNotFoundException e) {
        	logger.log(Level.WARNING, "JSON not found, generating new");
            try {
				new File("data.json").createNewFile();
				try (FileWriter file = new FileWriter("src/data.json")) {
		            //We can write any JSONArray or JSONObject instance to the file
		            file.write(def); 
		            file.flush();
		            loadJSon(def);
		        } catch (IOException e1) {
		        	logger.log(Level.SEVERE, "Error writting JSON archive: "+e.getMessage());
		        }
			} catch (IOException e1) {
				logger.log(Level.SEVERE, "Error creating JSON archive: "+e.getMessage());
			}
        } catch (IOException e) {
			logger.log(Level.SEVERE, "Error parsing JSON: "+e.getMessage());
		} catch (ParseException e) {
			logger.log(Level.SEVERE, "Error parsing JSON: "+e.getMessage());
		}
	}
	/**
	 * Devuelve un JSONObject (Map de JSON)
	 * @param key llave del mapa
	 * @return
	 */
	public static JSONObject getObjectFromJSon(String key) {
		logger.log(Level.INFO, "Trying to get JSONObject from "+key);
		return (JSONObject) json.get(key);
	}
	/**
	 * Devuelve un JSONArray (List de JSON)
	 * @param key key de la list
	 * @return
	 */
	public static JSONArray getArrayFromJSon(String key) {
		logger.log(Level.INFO, "Trying to get JSONArray from "+key);
		return (JSONArray) json.get(key);
	}
	/**
	 * Devuelve un JSONString (String de JSON)
	 * @param key key del String
	 * @return
	 */
	public static String getStringFromJSon(String key) {
		logger.log(Level.INFO, "Trying to get String from "+key);
		if (json.containsKey(key))
			return json.get(key).toString();
		return null;
	}
	@SuppressWarnings("unchecked")
	public static JSONObject defaultJSON() {
		JSONObject def = new JSONObject();
		
		//{
		JSONObject barcoPos = new JSONObject();
		barcoPos.put("x", 0.0f);
		barcoPos.put("y", 0.0f);
		//}
		def.put("barcoPos", barcoPos);
		def.put("barcoRot", 0.0f);
		def.put("IslaListas" , new JSONArray());
		return def;
	}
	public static String defaultJSONstr() {
		return defaultJSON().toJSONString();
	}
}
