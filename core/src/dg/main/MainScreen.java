package dg.main;


import java.util.logging.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import objetos.Barco;

//Pantalla en la que se va desarrollar el juego
public class MainScreen implements Screen{
	private static Logger logger= Logger.getLogger("MainScreen");
	public Barco barco = new Barco(10,0,0,0,null, new Texture("tileSetBarco.png"));
	Barco barco2 = new Barco(10,0,0,0,null, new Texture("tileSetBarco.png"));
	ShapeRenderer sr = new ShapeRenderer();
	
	
		
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
		logger.info("pressed:"+
				" W= "+Gdx.input.isKeyPressed(Input.Keys.W)+
				" A= "+Gdx.input.isKeyPressed(Input.Keys.A)+
				" S= "+Gdx.input.isKeyPressed(Input.Keys.S)+
				" D= "+Gdx.input.isKeyPressed(Input.Keys.D)
				);
		if(Gdx.input.isKeyPressed(Input.Keys.D))
			barco.right();
		if(Gdx.input.isKeyPressed(Input.Keys.A)) 
			barco.left();
		logger.info("barco: "+barco.getInfo());
		secondShipTest();
		logger.info("collision: "+String.valueOf(barco.collidesWith(barco2)));
		
		
		if(inBattle || Gdx.input.isKeyPressed(Input.Keys.P)) { //TODO Hacer que cambie cuando se entre en combate
			musicaOverworld.dispose();
			musicaBattle.dispose();
			
			musicaBattle.play();
	    	musicaBattle.setLooping(true);
	    	musicaBattle.setVolume(0.5f);
		}
		
				
		barco.dibujar(0, 0, 0, 0); 
		barco.drawCollisions(sr);
		barco2.dibujarRelativo(0, 0, barco2.getX() , barco2.getY(), barco.getX(), barco.getY());
		barco2.drawCollisions(sr);
		
		
		
	}
	public void secondShipTest() {
		if (Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.DOWN))
			barco2.decelerate();
		else if(Gdx.input.isKeyPressed(Input.Keys.UP))
			barco2.forward();
		else if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
			barco2.backwards();
		else 
			//barco2.decelerate();
			barco2.stop();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			barco2.right();
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) 
			barco2.left();
		
		
		
		
		
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
