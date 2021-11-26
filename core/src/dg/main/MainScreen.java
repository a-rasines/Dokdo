package dg.main;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import objectos.barcos.Barco;
import objectos.barcos.Barco.PosicionCanyon;
import objectos.barcos.BarcoEnemigo;
import objectos.barcos.BarcoJugador;
import objetos.Bala;
import objetos.Canyon;
import objetos.Isla;
import objetos.Sprite;

//Pantalla en la que se va desarrollar el juego
public class MainScreen implements Screen{
	private static Logger logger= Logger.getLogger("MainScreen");
	public static BarcoJugador barco = new BarcoJugador(10,0,0,0, 150);
	LinkedList<Isla> islaList = new LinkedList<>();
	public static ArrayList<BarcoEnemigo> barcosEnemigos = new ArrayList<>();
	public static ArrayList<BarcoEnemigo> barEneBorrar = new ArrayList<>();
	public static List<Bala> balasDisparadas = new ArrayList<>();
	public static List<Bala> balasBorrar = new ArrayList<>();
	public static List<Sprite> onRange = new ArrayList<>();
	BarcoEnemigo barco2 = new BarcoEnemigo(10,0,0,0).setTexturePos(0,1);;
	
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
    	barcosEnemigos.add(barco2);
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
		/*logger.info("pressed:"+
				" W= "+Gdx.input.isKeyPressed(Input.Keys.W)+
				" A= "+Gdx.input.isKeyPressed(Input.Keys.A)+
				" S= "+Gdx.input.isKeyPressed(Input.Keys.S)+
				" D= "+Gdx.input.isKeyPressed(Input.Keys.D)
				);*/
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
		//logger.info("barco: "+barco.getInfo());
		secondShipTest();
		//logger.info("collision:"+String.valueOf(barco.collidesWith(barco2)));
		if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
			barco.dispararLado(PosicionCanyon.DELANTE);
		}else if(Gdx.input.isKeyJustPressed(Input.Keys.J)) {
			barco.dispararLado(PosicionCanyon.IZQUIERDA);
		}else if(Gdx.input.isKeyJustPressed(Input.Keys.K)) {
			barco.dispararLado(PosicionCanyon.ATRAS);
		}else if(Gdx.input.isKeyJustPressed(Input.Keys.L)) {
			barco.dispararLado(PosicionCanyon.DERECHA);
		}
		//DAÑO A LOS BARCOS ENEMIGOS.
		for (Bala i: balasDisparadas){
			BarcoEnemigo b = i.getCollidesWith(barcosEnemigos);
			if(b != null && i.isJugador()) {
				b.recibeDaño(i);
			}
			i.decelerate();
			i.dibujar();
		}
		for(Bala i: balasBorrar) {
			balasDisparadas.remove(i);
		}
		for(Barco j: barEneBorrar) {
			barcosEnemigos.remove(j);
		}
		
		barEneBorrar.clear();
		balasBorrar.clear();
		
		//Si funciona con la funcion OnExitRange, esto se podria borrar seguramente
		
		if(cambioEstado && barco.enRango(barco2)){ //TODO Hacer que cambie cuando se entre en combate
			barco.onRangeOfPlayer();
			cambioEstado = false;
		}
		barcosEnemigos.forEach((v)->{
			if( barco.enRango(v)) {
				v.onRangeOfPlayer();
				onRange.add(v);
			}
		});
		List<Sprite> toRemove = new LinkedList<>();
		onRange.forEach((v)->{
			if( !barco.enRango(v)) {
				v.onExitFromRange();
				toRemove.add(v);
			}
		});
		toRemove.forEach((v)->{
			onRange.remove(v);
		});
				
		barco.dibujar(); 
		barco.drawCollisions(sr);
		for(Barco j: barcosEnemigos) {
			j.dibujar();
		}
		islaList.get(0).dibujar();
		islaList.get(0).drawCollisions(sr);
		
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