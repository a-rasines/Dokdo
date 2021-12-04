package dg.main;

import com.badlogic.gdx.Game;


public class Dokdo extends Game{
	
	@Override
	public void create() {
		//TODO Crear todas las ventnas que se van a usar para poder cambiar entre ellas
		MainScreen mapaC= new MainScreen();
		MenuP menu = new MenuP(this,mapaC);
		//setScreen(new MainScreen() ); //pruebas de juego
		setScreen(menu); //no borrar por favor--> pruebas del menu
	}
	

}
