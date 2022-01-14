package databasePack;

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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.badlogic.gdx.Input;

import objetos.Municion;
import objetos.barcos.BarcoJugador;

/**
 * Clase encargada de gestionar la base de datos
 *
 */
public class DatabaseHandler {
	private static Logger logger= Logger.getLogger("DatabaseHandler");
	public static class SQL{
		private static Connection connection;
		/**
		 * Comienza la conexión a la base de datos
		 * @param name nombre de la base de datos. Corresponde al nombre del archivo
		 * @return True si ha funcionado
		 */
		public static boolean connect(String name) {
			try {
				logger.log(Level.INFO, "Trying connection to: "+ name+".db");
				connection = DriverManager.getConnection("jdbc:sqlite:"+name+".db");
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
				connection.createStatement().executeUpdate(tb.build());
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
		public static boolean addValue(String table, String... values) {
			String v = String.join(" , ", values);
			return rawUpdate("INSERT INTO "+table+" values("+v+")", true);
		}
		/**
		 * Elimina todos los valores que coinciden con la condición. Para hacer esta función de forma manual se puede usar SQLrawUpdate()
		 * @param table tabla a modificar
		 * @param condition condición de los valores a eliminar
		 * @return True si se ha completado
		 */
		public static boolean removeValues(String table, String condition) {
			return rawUpdate("DELETE FROM "+table+" WHERE "+condition, true);
		}
		public static boolean editValue(String table, String newValues, String condition) {
			return rawUpdate("UPDATE "+table+" SET "+newValues+" WHERE "+condition, false);
		}
		/**
		 * Devuelve un Set de la tabla elegida. Para hacer esta función de forma manual se puede usar SQLrawQuery().
		 * @param table tabla de la que coger los valores
		 * @param columns columnas a coger separadas por coma o * para coger todas
		 * @param conditions condiciones de la petición
		 * @return ResultSet de lo obtenido
		 */
		public static ResultSet get(String table, String columns, String conditions) {
			return rawQuery("select "+columns+" from "+ table + (conditions==""?"": " WHERE "+conditions), true);
		}
		/**
		 * Devuelve un Set de la tabla elegida. Para hacer esta función de forma manual se puede usar SQLrawQuery().
		 * @param table tabla de la que coger los valores
		 * @param columns columnas a coger separadas por coma o * para coger todas
		 * @return ResultSet de lo obtenido
		 */
		public static ResultSet get(String table, String columns) {
			return rawQuery("select "+columns+" from "+ table, true);
		}
		/**
		 * Devuelve si un valor existe en la base de datos
		 * @param table Tabla en la que buscar
		 * @param column Columna de la tabla
		 * @param value Valor a buscar
		 * @return true si existe. False si no existe o da error
		 */
		public static boolean existsValue(String table, String column, String value) {
			try {
				return rawQuery("SELECT COUNT("+column+ ") FROM "+table+" WHERE "+column+" = "+value, false).getInt(1)>0;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		/**
		 * Forma limpia de convertir a strings de SQL los strings
		 * @param val String a convertir
		 * @return
		 */
		public String toSQLString(String val) {
			return '"'+val+'"';
		}
		/**
		 * Manda una actualización a la base de datos (modificar/añadir datos)
		 * @param code solicitud
		 * @return True si se ha completado
		 */
		public static boolean rawUpdate(String code, boolean track) {
			try {
				if(track)logger.log(Level.INFO, "Trying to update: "+code);
				connection.createStatement().executeUpdate(code);
				return true;
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "Error updating database: "+e.getMessage());
				return false;
			}
		}
		/**
		 * Manda una petición a la base de datos
		 * @param query SELECT a introducir
		 * @return ResultSet de la petición
		 */
		public static ResultSet rawQuery(String query, boolean track) {
			try {
				if(track)logger.log(Level.INFO, "Trying to query: "+query);
				return connection.createStatement().executeQuery(query);
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "Error during query to database: "+e.getMessage());
				return null;
			}
		}
	}
	
	public static class JSON{
		private static JSONObject json;
		/**
		 * Escribe un objeto a JSON
		 * @param key llave del objeto
		 * @param v objeto a meter
		 * @param override ¿Cambia el valor de la llave o añadir a lista de valores?(Solo valido para JSONArray)
		 */
		@SuppressWarnings("unchecked")
		public static void write(String key, Object v, boolean override) {
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
		 * Añade o edita un valor en un diccionario en un JSON
		 * @param key llave del diccionario
		 * @param subKey llave dentro del diccionario
		 * @param object nuevo valor
		 */
		@SuppressWarnings("unchecked")
		public static void writeInObject(String key, String subKey, Object v) {
			JSONObject o = (JSONObject) json.get(key);
			o.put(subKey, v);
			try (FileWriter file = new FileWriter("src/data.json")) {
	            file.write(json.toJSONString()); 
	            file.flush();
	 
	        } catch (IOException e) {
	        	logger.log(Level.SEVERE, "Error writting to JSON: "+e.getMessage()+" Tried to insert in "+key+"("+subKey+") a(n) "+v.getClass().getName()+".("+v.toString()+")");
	        }
		}
		/**
		 * Elimina objeto de JSONArray
		 * @param key llave del objeto
		 * @param v objeto a borrar
		 */
		public static void removeFromArray(String key, Object v) {
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
		 */
		public static void load() {
			load(defaultObject());
		}
		/**
		 * Carga el JSON
		 */
		@SuppressWarnings("unchecked")
		private static void load(JSONObject def) {
			logger.log(Level.INFO, "Loading JSON");
			JSONParser jsonParser = new JSONParser();
	        try (FileReader reader = new FileReader("src/data.json")){
	            json = (JSONObject)jsonParser.parse(reader);
	            if (!json.keySet().equals(def.keySet())) {
	            	def.forEach((k, v)->{
	            		if(!json.containsKey(k.toString()))
	            			write(k.toString(), v, true);
	            	});
	            }
	        } catch (FileNotFoundException e) {
	        	logger.log(Level.WARNING, "JSON not found, generating new");
	            try {
					new File("src/data.json").createNewFile();
					try (FileWriter file = new FileWriter("src/data.json")) {
			            //We can write any JSONArray or JSONObject instance to the file
			            file.write(def.toJSONString()); 
			            file.flush();
			            load(def);
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
		public static JSONObject getObject(String key) {
			return (JSONObject) json.get(key);
		}
		/**
		 * Devuelve un JSONArray (List de JSON)
		 * @param key key de la list
		 * @return
		 */
		public static JSONArray getArray(String key) {
			logger.log(Level.INFO, "Trying to get JSONArray from "+key);
			return (JSONArray) json.get(key);
		}
		/**
		 * Devuelve un JSONString (String de JSON)
		 * @param key key del String
		 * @return
		 */
		public static String getString(String key) {
			if (json.containsKey(key))
				return json.get(key).toString();
			return null;
		}
		@SuppressWarnings("unchecked")
		private static JSONObject defaultObject() {
			JSONObject def = new JSONObject();
			def.put("users", new JSONArray());
			def.put("volumen", "0.5");
			//{Input.Keys.W,Input.Keys.S,Input.Keys.A,Input.Keys.D,Input.Keys.I,Input.Keys.K,Input.Keys.J,Input.Keys.L};
			JSONObject keys = new JSONObject();
			keys.put("moveForward", Input.Keys.W);
			keys.put("moveBackward", Input.Keys.S);
			keys.put("moveLeft", Input.Keys.A);
			keys.put("moveRight", Input.Keys.D);
			keys.put("shootForward", Input.Keys.I);
			keys.put("shootBackward", Input.Keys.K);
			keys.put("shootLeft", Input.Keys.J);
			keys.put("shootRight", Input.Keys.L);
			def.put("keys", keys);
			logger.info("Default JSON generated");
			return def;
		}
	}
}
