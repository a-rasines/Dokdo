package dg.main.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import dg.main.Dokdo;
import dg.main.DokdoCore;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//new LwjglApplication(new DokdoCore(), config);
		new LwjglApplication(new Dokdo(),config); // juego auxiliar de prueba
	}
}
