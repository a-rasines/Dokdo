package dg.main;

import com.badlogic.gdx.Game;


public class JugoP extends Game{
	
	@Override
	public void create() {
		//setScreen(new MenuP());
		//TODO cambiado para probar el movimiento del barco
		setScreen(new MainScreen());
	}
	

}
