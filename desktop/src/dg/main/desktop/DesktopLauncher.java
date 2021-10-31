package dg.main.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import dg.main.DokdoCore;
import dg.main.JugoP;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//new LwjglApplication(new DokdoCore(), config);
		new LwjglApplication(new JugoP(),config); // juego auxiliar de prueba
	}
}
