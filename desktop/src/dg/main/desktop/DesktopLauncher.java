package dg.main.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import databasePack.DatabaseHandler;
import databasePack.TableBuilder;
import databasePack.TableBuilder.DataType;
import databasePack.TableBuilder.ForeignAction;
import dg.main.Dokdo;

import java.awt.Dimension;
import java.awt.Toolkit;

public class DesktopLauncher {
	public static void main(String[] arg) {
		DatabaseHandler.SQL.connect("dokdo");
		if(DatabaseHandler.SQL.addTable(
				new TableBuilder("Jugadores")
				.addColumn("ID", DataType.INT, "6")
				.addColumn("Nombre", "'Player1'", DataType.CHAR, "10")
				.addColumn("BarcoX", "0.0", DataType.DEC, "9", "4")
				.addColumn("BarcoY", "0.0", DataType.DEC, "9", "4")
				.addColumn("Vida", "10",DataType.INT, "2")
				.addColumn("Rotacion", "0.0", DataType.DEC, "5", "2")
				.addColumn("Nivel", "1", DataType.INT, "2")
				.addColumn("Dinero", "0", DataType.INT, "9")
				.addColumn("TimeS", DataType.BIGINT, String.valueOf(420 + 69 - 84*5))
				.setPrimaryKey("ID")))
			System.out.println("Users' table Generated");
		if(DatabaseHandler.SQL.addTable(
				new TableBuilder("Islas")
				.addColumn("ID", DataType.INT, "2")
				.addColumn("ID_Jugador", DataType.INT, "6")
				.addColumn("X", "0.0", DataType.DEC, "9", "4")
				.addColumn("Y", "0.0", DataType.DEC, "9", "4")
				.addColumn("Conquistada", "0", DataType.INT, "1")
				.addColumn("Nivel", "0", DataType.INT, "2")
				.addColumn("Botin", "0", DataType.INT, "5")
				.addColumn("Textura", DataType.INT, "2")
				.addForeignKey("ID_Jugador", "Jugador", "ID", ForeignAction.CASCADE)
				.addCheck("Conquistada", "BETWEEN 0 AND 1")))
			System.out.println("Isles' table Generated");
		DatabaseHandler.JSON.load();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		config.width = (int)screenSize.getWidth()-50;
		config.height = (int)screenSize.getHeight()-50;
		config.x = 0;
		config.y = 0;
		new LwjglApplication(Dokdo.getInstance(),config);
	}
}