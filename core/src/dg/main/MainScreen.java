package dg.main;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import objetos.Barco;
import objetos.Isla;

//Pantalla en la que se va desarrollar el juego
public class MainScreen implements Screen{
	private static Logger logger= Logger.getLogger("MainScreen");
	public static Barco barco = new Barco(10,0,0,0,null);
	LinkedList<Isla> islaList = new LinkedList<>();
	ArrayList<Barco> barcosEnemigos = new ArrayList<>();
	ArrayList<Barco> balasDisparadas = new ArrayList<>();
	Barco barco2 = new Barco(10,0,0,0,null);
	ShapeRenderer sr = new ShapeRenderer();
	
	@Override
	public void show() {
		//Musica normal
		AudioPlayer.Reproducir("Sonidos//Overworld.mp3");
    	islaList.add(new Isla(100, 100, 1, 1));
    	
   	}

	Boolean cambioEstado = true;

	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		ScreenUtils.clear(0.0f, 0.5f, 1f,0); //Necesario para updatear correctamente la pantalla
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
		if (barco.collidesWith(islaList)) {
			barco.undoMove();
			barco.stop();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) 
			barco.right();
		if(Gdx.input.isKeyPressed(Input.Keys.A) || barco.collidesWith(islaList)) 
			barco.left();
		if(barco.collidesWith(islaList))
			barco.right();
		logger.info("barco: "+barco.getInfo());
		secondShipTest();
		logger.info("collision:"+String.valueOf(barco.collidesWith(barco2)));
		
		
		
		//Si funciona con la funcion OnExitRange, esto se podria borrar seguramente
		
		if(cambioEstado && barco.enRango(barco2)){ //TODO Hacer que cambie cuando se entre en combate
			barco.onRangeOfPlayer();
			cambioEstado = false;
		}
		if(!cambioEstado && !barco.enRango(barco2)) {
			barco.onExitFromRange();
			cambioEstado = true;
		}
		
				
		barco.dibujar(0, 0); 
		barco.drawCollisions(sr);
		barco2.dibujar(0, 0);
		islaList.get(0).dibujar(0, 0);
		islaList.get(0).drawCollisions(sr);
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
