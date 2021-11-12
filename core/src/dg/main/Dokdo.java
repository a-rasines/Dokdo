package dg.main;

import com.badlogic.gdx.Game;


public class Dokdo extends Game{
	
	@Override
	public void create() {
	
		setScreen(new MainScreen() ); //pruebas de juego
		//setScreen(new MenuP()); //no borrar por favor--> pruebas del menu
	}
	

}
