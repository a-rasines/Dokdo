package dg.main.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import DataBase.DatabaseHandler;
import dg.main.Dokdo;

import java.awt.Dimension;
import java.awt.Toolkit;

public class DesktopLauncher {
	public static void main(String[] arg) {
		DatabaseHandler.connectToDatabase("dokdo");
		DatabaseHandler.loadJSon(DatabaseHandler.defaultJSON());
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		config.width = (int)screenSize.getWidth()-50;
		config.height = (int)screenSize.getHeight()-50;
		config.x = 0;
		config.y = 0;
		//new LwjglApplication(new DokdoCore(), config);
		new LwjglApplication(new Dokdo(),config); // juego auxiliar de prueba
	}
}
