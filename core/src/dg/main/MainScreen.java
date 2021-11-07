package dg.main;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

import objetos.Barco;

//Pantalla en la que se va desarrollar el juego
public class MainScreen implements Screen{
	Barco barco = new Barco(10,0,0,0,null);
	
	Music musicaOverworld;
	Music musicaBattle =  Gdx.audio.newMusic(Gdx.files.internal("Sonidos\\Battle.mp3"));
	@Override
	public void show() {
		//Musica normal
		musicaOverworld = Gdx.audio.newMusic(Gdx.files.internal("Sonidos\\Overworld.mp3"));
		musicaOverworld.play();
    	musicaOverworld.setLooping(true);
    	musicaOverworld.setVolume(0.5f);
    	
	}

	Boolean inBattle = false;
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		ScreenUtils.clear(1, 0, 0, 1); //Necesario para updatear correctamente la pantalla
		if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.S))
			barco.decelerate();
		else if(Gdx.input.isKeyPressed(Input.Keys.W))
			barco.forward();
		else if(Gdx.input.isKeyPressed(Input.Keys.S))
			barco.backwards();
		else 
			barco.decelerate();
		
		if(Gdx.input.isKeyPressed(Input.Keys.D))
			barco.right();
		if(Gdx.input.isKeyPressed(Input.Keys.A)) 
			barco.left();
		
		barco.printPos();
		
		
		barco.dibujar(new Texture("tileSetBarco.png"), 0, 0, barco.getX(), barco.getY(), 32);
		if(inBattle || Gdx.input.isKeyPressed(Input.Keys.P)) { //TODO Hacer que cambie cuando se entre en combate
			musicaOverworld.dispose();
			musicaBattle.dispose();
			
			musicaBattle.play();
	    	musicaBattle.setLooping(true);
	    	musicaBattle.setVolume(0.5f);
		}
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	

}
