package dg.main;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import objetos.Bala;
import objetos.Barco;
import objetos.Canyon;
import objetos.Isla;
import objetos.Municion;
import objetos.Barco.PosicionCanyon;

//Pantalla en la que se va desarrollar el juego
public class MainScreen implements Screen{
	private static Logger logger= Logger.getLogger("MainScreen");
	public static Barco barco = new Barco(10,0,0,0,Municion.NORMAL);
	LinkedList<Isla> islaList = new LinkedList<>();
	ArrayList<Barco> barcosEnemigos = new ArrayList<>();
	public static ArrayList<Bala> balasDisparadas = new ArrayList<>();
	public static ArrayList<Bala> balasBorrar = new ArrayList<>();
	Barco barco2 = new Barco(10,0,0,0,null);
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Viewport vp = new FillViewport((float)screenSize.getWidth()-50, (float)screenSize.getHeight()-50);
	Stage stage = new Stage(vp);
	ShapeRenderer sr = new ShapeRenderer();
	
	@Override
	public void show() {
		//Musica normal
		AudioPlayer.Reproducir("Sonidos//Overworld.mp3");
    	islaList.add(new Isla(100, 100, 1, 1));
    	barco.setCanyones(PosicionCanyon.DELANTE, new Canyon(0,0));
    	barco.setCanyones(PosicionCanyon.ATRAS, new Canyon(0,0));
    	barco.setCanyones(PosicionCanyon.DERECHA, new Canyon(0,0));
    	barco.setCanyones(PosicionCanyon.IZQUIERDA, new Canyon(0,0));
    	barco2.setTexturePos(0,1); //Para seleccionar el sprite dentro del archivo TODO hacerlo bonito
   	}

	Boolean cambioEstado = true;

	
	@Override
	public void render(float delta) {
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
		if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
			barco.dispararLado(PosicionCanyon.DELANTE);
		}else if(Gdx.input.isKeyJustPressed(Input.Keys.J)) {
			barco.dispararLado(PosicionCanyon.IZQUIERDA);
		}else if(Gdx.input.isKeyJustPressed(Input.Keys.K)) {
			barco.dispararLado(PosicionCanyon.ATRAS);
		}else if(Gdx.input.isKeyJustPressed(Input.Keys.L)) {
			barco.dispararLado(PosicionCanyon.DERECHA);
		}
		for (Bala i: balasDisparadas){
			i.decelerate();
			i.dibujar();
		}for(Bala i: balasBorrar) {
			balasDisparadas.remove(i);
		}
		balasBorrar.clear();
		
		//Si funciona con la funcion OnExitRange, esto se podria borrar seguramente
		
		if(cambioEstado && barco.enRango(barco2)){ //TODO Hacer que cambie cuando se entre en combate
			barco.onRangeOfPlayer();
			cambioEstado = false;
		}
		if(!cambioEstado && !barco.enRango(barco2)) {
			barco.onExitFromRange();
			cambioEstado = true;
		}
		
				
		barco.dibujar(); 
		barco.drawCollisions(sr);
		barco2.dibujar();
		islaList.get(0).dibujar();
		islaList.get(0).drawCollisions(sr);
		barco2.drawCollisions(sr);
		
		//TODO Prueba de lineas
		if(barco2.tocaLinea(barco) != null) {
			
			System.out.println("toca");
		}
		
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
		vp.update(width, height);
		
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