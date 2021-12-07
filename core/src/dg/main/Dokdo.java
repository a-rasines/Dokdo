package dg.main;

import com.badlogic.gdx.Game;

import hilos.HiloVolumen;


public class Dokdo extends Game{
	private static MainScreen mapaC;
	private static MenuP menu ;
	private HiloVolumen s1= new HiloVolumen();
	
	@Override
	public void create() {
		//TODO Crear todas las ventnas que se van a usar para poder cambiar entre ellas
		MainScreen mapaC= new MainScreen(s1);
		MenuP menu = new MenuP(this,mapaC,s1);
		//setScreen(new MainScreen() ); //pruebas de juego
		setScreen(menu); //no borrar por favor--> pruebas del menu
	}
	
	

}
