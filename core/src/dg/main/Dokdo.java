package dg.main;

import com.badlogic.gdx.Game;

import databasePack.DatabaseHandler;
import hilos.HiloVolumen;


public class Dokdo extends Game{
	private static Dokdo instance;
	
	
	public static Dokdo getInstance() {
		if(instance == null)instance = new Dokdo();
		return instance;
	}
	@Override
	public void create() {
		FormatoMenus.volumenes = new float[]{Float.parseFloat(DatabaseHandler.JSON.getString("volumen")),0.0f};
		HiloVolumen.getInstance().start();
		//menuOp= new MenuOp(Dokdo.getInstance(), s1, cMenu, Menu8Bits, volumenes, false, MenuP);
		//TODO Crear todas las ventnas que se van a usar para poder cambiar entre ellas
		//setScreen(new MenuP(Dokdo.getInstance(), s1, cMenu, Menu8Bits, volumenes, true)); 
		setScreen(MenuP.getInstance());
		//setScreen(new MainScreen(new MenuP()));
	}
	@Override
	public void dispose () {
		if (screen != null) screen.hide();
		System.exit(0);
	}
	
	

}
