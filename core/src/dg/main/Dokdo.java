package dg.main;

import com.badlogic.gdx.Game;

import hilos.HiloVolumen;


public class Dokdo extends Game{
	private static Dokdo instance;
	public static Dokdo getInstance() {
		if(instance == null)instance = new Dokdo();
		return instance;
	}
	@Override
	public void create() {
		//TODO Crear todas las ventnas que se van a usar para poder cambiar entre ellas
		setScreen(new MenuP()); //no borrar por favor--> pruebas del menu
	}
	
	

}
