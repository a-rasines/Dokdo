package databasePack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
/**
 * Constructor facil de tablas
 *
 */
public class TableBuilder {
	private String name;
	private List<String> columns = new LinkedList<>();
	private List<String> extra = new ArrayList<>();
	private List<String> columnNames = new ArrayList<>();
	public enum DataType{
		BIGINT(1),
		CHAR(1),
		DATE(0),
		DEC(2),
		ENUM(-1),
		INT(1), 
		VARCHAR(1); 
		private int params;
		DataType(int i) {
			this.params = i;
		}
		public int getParams() {
			return this.params;
		}
	}
	public TableBuilder(String name) {
		this.name = name;
	}
	/**
	 * Añade una columna a la tabla
	 * @param name nombre de la tabla
	 * @param type tipo de dato
	 * @param q cantidad de datos (digitos/caracteres). En string para facilitar el código, pero deben ser números
	 * @return El propio constructor para concatenar acciones
	 * @throws NullPointerException si el tamaño de q no coincide con lo necesario de type
	 * @throws NumberFormatException si alguno de los valores en q no es numérico
	 */
	public TableBuilder addColumn(String name, DataType type, String... q) {
		return addColumn(name, null, type, q);
	}
	/**
	 * Añade una columna a la tabla
	 * @param name nombre de la tabla
	 * @param def valor por defecto de la funcion. null si no tiene
	 * @param type tipo de dato
	 * @param q cantidad de datos (digitos/caracteres). En string para facilitar el código, pero deben ser números
	 * @return El propio constructor para concatenar acciones
	 */
	public TableBuilder addColumn(String name, String def, DataType type, String... q) {
		if(type.getParams()==-1 && q.length>0 || type.getParams() != q.length)throw new NullPointerException("No coincide el número de parametros introducidos con los del tipo");
		for(String s : q)Integer.parseInt(s);
		String params = String.join(",",q);
		if(!columnNames.contains(name)) {
			columns.add(name+" "+type.toString()+(params.length()==0?"":"("+params+")"+(def == null?"": " DEFAULT "+def)));
			columnNames.add(name);
			return this;
		}
		int pos = columnNames.indexOf(name);
		columns.set(pos, name+" "+type.toString()+(params.length()==0?"":"("+params+")"+(def == null?"": " DEFAULT "+def)));
		return this;
	}
	/**
	 * Establece la key principal de la tabla
	 * @param name nombre de la columna. Debe estar en la tabla
	 * @return El propio constructor para concatenar acciones
	 * @throws NullPointerException si la columna no existe
	 */
	public TableBuilder setPrimaryKey(String name) {
		if(!columnNames.contains(name))throw new NullPointerException("No existe ninguna columna llamada "+name);
		extra.add("PRIMARY KEY ("+name+")");
		return this;
	}
	public enum ForeignAction{NO_ACTION, CASCADE, SET_NULL, SET_DEFAULT};
	/**
	 * Añade una clave externa
	 * @param name nombre de la key en esta tabla
	 * @param otherTable nombre de la otra tabla
	 * @param reference nombre de la columna en la otra tabla
	 * @param onDelete accion al eliminar el objeto con la clave externa
	 * @param onUpdate accion al cambiar el objeto al que referencia la clave
	 * @return El propio constructor para concatenar acciones
	 * @throws NullPointerException si la columna no existe
	 */
	public TableBuilder addForeignKey(String name, String otherTable, String reference, ForeignAction onDelete, ForeignAction onUpdate) {
		if(!columnNames.contains(name))throw new NullPointerException("No existe ninguna columna llamada "+name);
		extra.add("FOREIGN KEY ("+name+") REFERENCES "+otherTable+"("+reference+") ON DELETE "+ onDelete.toString().replace("_", " ")+" ON UPDATE "+onUpdate.toString().replace("_", " "));
		return this;
	}
	/**
	 * Añade una clave externa
	 * @param name nombre de la key en esta tabla
	 * @param otherTable nombre de la otra tabla
	 * @param reference nombre de la columna en la otra tabla
	 * @param onDelete accion al eliminar el objeto con la clave externa
	 * @return El propio constructor para concatenar acciones
	 * @throws NullPointerException si la columna no existe
	 */
	public TableBuilder addForeignKey(String name, String otherTable, String reference, ForeignAction onDelete) {
		return addForeignKey(name, otherTable, reference, onDelete, ForeignAction.NO_ACTION);
	}
	/**
	 * Añade una clave externa
	 * @param name nombre de la key en esta tabla
	 * @param otherTable nombre de la otra tabla
	 * @param reference nombre de la columna en la otra tabla
	 * @return El propio constructor para concatenar acciones
	 * @throws NullPointerException si la columna no existe
	 */
	public TableBuilder addForeignKey(String name, String otherTable, String reference) {
		return addForeignKey(name, otherTable, reference, ForeignAction.NO_ACTION, ForeignAction.NO_ACTION);
	}
	/**
	 * Añade un check a la tabla
	 * @param column la columna a comprobar
	 * @param condition la condición que debe cumplir (Ej: ">= 50")
	 * @return El propio constructor para concatenar acciones
	 * @throws NullPointerException si la columna no existe
	 */
	public TableBuilder addCheck(String column, String condition) {
		if(!columnNames.contains(column))throw new NullPointerException("No existe ninguna columna llamada "+column);
		extra.add("CHECK("+column+" "+condition+")");
		return this;
	}
	/**
	 * Hace que una columna ya existente tenga solo valores únicos
	 * @param column columna a modificar
	 * @return El propio constructor para concatenar acciones
	 * @throws NullPointerException si la columna no existe
	 */
	public TableBuilder addUnique(String column) {
		if(!columnNames.contains(column))throw new NullPointerException("No existe ninguna columna llamada "+column);
		extra.add("UNIQUE("+column+")");
		return this;
	}
	
	public String build(){
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE IF NOT EXISTS "+name+"(\n");
		Consumer<String> c = new Consumer<String>() {
			@Override
			public void accept(String t) {
				sb.append(t+",\n");
				
			}};
		columns.forEach(c);
		extra.forEach(c);
		String end = sb.toString();
		char[] end0 = end.toCharArray();
		end0[end.length()-2]=" ".toCharArray()[0];
		end = String.valueOf(end0);
		end += ");";
		return end;
	}
	public String getName() {
		return name;
	}
}
