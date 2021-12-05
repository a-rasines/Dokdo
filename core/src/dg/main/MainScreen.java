package dg.main;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import DataBase.DatabaseHandler;
import objetos.Bala;
import objetos.Canyon;
import objetos.Isla;
import objetos.Municion;
import objetos.Sprite;
import objetos.barcos.Barco;
import objetos.barcos.BarcoEnemigo;
import objetos.barcos.BarcoJugador;
import objetos.barcos.Barco.PosicionCanyon;

//Pantalla en la que se va desarrollar el juego
public class MainScreen implements Screen{
	private static Logger logger= Logger.getLogger("MainScreen");
	public static BarcoJugador barco;
	public static List<Isla> islaList = new LinkedList<>();
	public static List<BarcoEnemigo> barcosEnemigos = new ArrayList<>();
	public static List<BarcoEnemigo> barEneBorrar = new ArrayList<>();
	public static List<Bala> balasDisparadas = new ArrayList<>();
	public static List<Bala> balasBorrar = new ArrayList<>();
	public static List<Sprite> onRange = new ArrayList<>();
	public static List<Sprite> offRange = new ArrayList<>();
	BarcoEnemigo barco2 = new BarcoEnemigo(10,0,0,0, false, Municion.NORMAL).setTexturePos(0,1);;
	
	public static HiloVolumen hv = new HiloVolumen();

	
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static Viewport vp = new FillViewport((float)screenSize.getWidth()-50, (float)screenSize.getHeight()-50);
	public static Stage stage = new Stage(vp);
	ShapeRenderer sr = new ShapeRenderer();
	
	public static boolean entraRango = false;
	/**
	 * Genera un array a partir de valores
	 * @param v valores en el array
	 * @return Array de los objetos metidos. Si es nativo sale como su versión que extiende de Object
	 */
	public <T> T[] arrayBuilder(@SuppressWarnings("unchecked") T... v){
		return v;
	}
	/** Genera islas de manera repartida por un mundo de 10000x10000 ( 5000 hacia cada lado ) 
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void generarIslas() {
		Random r = new Random();
		//Ahora mismo se generan 5 islas por cuadrante
		
		logger.info("Iniciada Generacion de islas");
		List<Vector2> isles = new LinkedList<>();
		Integer[][]cuadrants = arrayBuilder(arrayBuilder(1,1), arrayBuilder(-1, 1), arrayBuilder(-1, -1), arrayBuilder(1, -1));
		for(int i = 0; i< 4; i++) {			
			for(int j = 0; j < 5; j++) {
				boolean next = false;
				while (!next) {
					int x = cuadrants[i][0]*(r.nextInt(4500)+500);
					int y = cuadrants[i][1]*(r.nextInt(4500)+500);
					Vector2 v = new Vector2(x, y);
					boolean valid = true;
					for(Vector2 k: isles) {
						if(k.dst(v)<500) {
							valid = false;
							break;
						}
					}
					if (valid) {
						islaList.add(new Isla((float) x, (float) y, 0, 0, null));
						next = true;
					}
				}
			}
		}
		
		logger.info("Generacion completa");
		
		for(Isla i : islaList) {
			JSONObject isla = new JSONObject();
			isla.put("x", i.getX());
			isla.put("y", i.getY());
			isla.put("xText", 0);
			isla.put("yText", 0);
			DatabaseHandler.writeToJSON("IslaListas",isla, false);
		}
	}
	
	@Override
	public void show() {
		//Musica normal
		
		AudioPlayer.Reproducir("Sonidos//Overworld.mp3");
		
		JSONObject pos0 = DatabaseHandler.getObjectFromJSon("barcoPos");
		barco = new BarcoJugador(10,0,0,0, 150, Municion.INCENDIARIA).rotate(Float.parseFloat(DatabaseHandler.getStringFromJSon("barcoRot"))).tpTo(Float.parseFloat(pos0.get("x").toString()), Float.parseFloat(pos0.get("y").toString()));
		
    	barco.setCanyones(PosicionCanyon.DELANTE, new Canyon(0,0));
    	barco.setCanyones(PosicionCanyon.ATRAS, new Canyon(0,0));
    	barco.setCanyones(PosicionCanyon.DERECHA, new Canyon(0,0));
    	barco.setCanyones(PosicionCanyon.IZQUIERDA, new Canyon(0,0));
    	barco2.setCanyones(PosicionCanyon.DELANTE, new Canyon(0,0));
    	barco2.setCanyones(PosicionCanyon.ATRAS, new Canyon(0,0));
    	barco2.setCanyones(PosicionCanyon.DERECHA, new Canyon(0,0));
    	barco2.setCanyones(PosicionCanyon.IZQUIERDA, new Canyon(0,0));
    	barcosEnemigos.add(barco2);
    	
    	if(DatabaseHandler.getArrayFromJSon("IslaListas").size() == 0) {
    		generarIslas();
    	} else {
    		for(Object j : DatabaseHandler.getArrayFromJSon("IslaListas")) {
    			JSONObject i = (JSONObject) j;
    			//TODO linea 209 es la q da error.
    			islaList.add(new Isla((float) (double) i.get("x"),  (float)(double) i.get("y"), 0, 0, null));
    			logger.info("Islas Cargadas");
    		}
    	}
    	
	}

	Boolean cambioEstado = true;

	
	@Override
	public void render(float delta) {
		ScreenUtils.clear(0.0f, 0.5f, 1f,0); //Necesario para updatear correctamente la pantalla
		
		//Control de teclas
		
		if(Gdx.input.isKeyPressed(Input.Keys.R))
			barco.tpTo(0, 0);
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
		secondShipTest();//TODO eliminar al terminar tests
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
				if(barco.getMunicionEnUso().getInstantaneo())
					b.recibeDanyo(i);
				else{
					b.recibeDanyoContinuo(i);
				}
			} else if(i.collidesWith(barco) && !i.isJugador()) {
				barco.recibeDanyo(i);
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
		
		//Actualización de rango
		
		List<Sprite> move = new LinkedList<>();
		for(Sprite b : barco.getEnRango(offRange)) {
			move.add(b);
			b.onRangeOfPlayer();
		}
		onRange.addAll(move);
		offRange.removeAll(move);
		move.clear();
		for(Sprite s : onRange)
			if(!barco.enRango(s)) {
				move.add(s);
				s.onExitFromRange();
			}
		offRange.addAll(move);
		onRange.removeAll(move);
		move.clear();
		
		//Dibujado
		
		barco.dibujar();
		barco.drawCollisions(sr);
		barcosEnemigos.forEach(v->v.dibujar());
		islaList.forEach(v->v.dibujar());
		islaList.get(0).drawCollisions(sr);
		
		//Cambio de Audio
		
		//TODO prueba con nuestro unico barco
		//TODO lagea terrible
		//He probado ha acer que AudioPlayer tengas que instanciarlo
		//(qutarle el static) y asi tener dos audios a la vez, pero el solo reproduce uno y da un lag terrible
		//esto se supone que baja el volumen de la musica anterior, pero se muere (Genera hilos a saco)
		
		if(barco.enRango(barco2) && !entraRango) {
//			if(hv.isAlive()) {
//				hv.interrupt();
//			} 
//			
//			hv.setFichero("Sonidos//Battle.mp3");
//			hv.start();
			AudioPlayer.detener();
			AudioPlayer.Reproducir("Sonidos//Battle.mp3");
			
			entraRango = !entraRango;
		} 
		if(!barco.enRango(barco2) && entraRango) {
//			if(hv.isAlive()) {
//				hv.interrupt();
//			}
//			
//			hv.setFichero("Sonidos//Overworld.mp3");
//			hv.start();
			AudioPlayer.detener();
			AudioPlayer.Reproducir("Sonidos//Overworld.mp3");
			entraRango = !entraRango;
		} 
		//TODO Prueba de lineas
		
		if(barco2.tocaLinea(barco) != null) {
			barco2.dispararLado(barco2.tocaLinea(barco));
			
		}
		
	}
	public void secondShipTest() {
		if (Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.DOWN))
			barco2.decelerate();
		else if(Gdx.input.isKeyPressed(Input.Keys.UP))
			barco2.forward();
		else if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
			barco2.backwards();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			barco2.right();
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) 
			barco2.left();
		barco2.drawCollisions(sr);
		//HAY QUE CAMBIARLO
		//barco2.IAMove();
		sr.begin(ShapeRenderer.ShapeType.Line);
	    sr.line(new Vector2(barco.getX(), barco.getY()), new Vector2(barco2.getX(), barco2.getY()));
	    sr.end();
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