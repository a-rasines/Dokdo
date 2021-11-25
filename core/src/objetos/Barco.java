package objetos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import dg.main.AudioPlayer;
import dg.main.MainScreen;
import objetos.Barco.PosicionCanyon;
/**
 * Representa un barco, tanto el del jugador como el de los enemigos
 *
 */
public class Barco extends Sprite{
	
	/*
	 * ▓▓▓▓▓▓▓▓▓▓ ENUMS ▓▓▓▓▓▓▓▓▓▓ 
	 */
	
	/**
	 * Tipo de {@link objetos.Barco Barco}
	 *
	 */
	public static enum Tipo{
	};
	/**
	 * Lado del {@link objetos.Barco Barco} en el que está el {@link objetos.Cañon Cañon}
	 *
	 */
	public enum PosicionCanyon{
		IZQUIERDA(0, 2, 270),
		DERECHA(0, 0, 90),
		DELANTE(1, 0, 0),
		ATRAS(2, 0, 180);
		private int x;
		private int y;
		private int angle;
		PosicionCanyon(int i, int j, int k) {
			x = i;
			y = j;
			angle = k;
		}
		int getX() {
			return x;
		}
		int getY() {
			return y;
		}
		int getAngle() {
			return angle;
		}
	};
	
	/*
	 * ▓▓▓▓▓▓▓▓▓▓ ATRIBUTOS ▓▓▓▓▓▓▓▓▓▓
	 */
	private Polygon lineaFrente;
	private Polygon lineaIzquierda;
	private Polygon lineaDerecha;
	private Polygon lineaAtras;
	
	
	private static Texture t;
	static {
		try {
			t = new Texture("tileSetBarco.png");
		}catch(Exception e) {}
	}
	protected int vida;
	protected int nivel;
	protected Municion municionEnUso;
	protected HashMap<PosicionCanyon,CannonSide> canyones;
	protected float vMax = 5; //velocidad maxima
	protected float a = 1; //aceleración
	protected float vAng = 100; //velocidad angular en grados
	private float rango = 150; //Rango de accion
	
	/*
	 * ▓▓▓▓▓▓▓▓▓▓ CONSTRUCTORES ▓▓▓▓▓▓▓▓▓▓
	 */
	
	/** Barcos del juego
	 * @param vida Vida actual del barco
	 * @param nivel Nivel actual del barco
	 * @param posX Posicion en X del barco
	 * @param posY Posicion en Y del barco
	 * @param municionActual Municion que esta usando el barco
	 */
	public Barco(int vida, int nivel, float posX, float posY, Municion municionActual) {
		super(posX, posY, 0, 0, 32, 32, 150); //TODO Ajustar rango hasta una distancia interesante
		super.tMap = t;
		canyones = new HashMap<>();
		this.vida=vida;
		this.nivel=nivel;
		this.municionEnUso=municionActual;
		this.rango = 150;
		refreshLineas();
	}
	/** Barcos del juego
	 * @param vida Vida actual del barco
	 * @param nivel Nivel actual del barco
	 * @param posX Posicion en X del barco
	 * @param posY Posicion en Y del barco
	 */
	public Barco(int vida, int nivel, float posX, float posY) {
		this(vida, nivel, posX, posY, Municion.NORMAL);
	}
	
	
	
	/*
	 * ▓▓▓▓▓▓▓▓▓▓ FUNCIONES ▓▓▓▓▓▓▓▓▓▓
	 */
	
	//Actualizar valores
	/**Actualiza los valores de fin de linea (El inicio de esta es la posicion propia del barco)
	 * 
	 */
	
	
	protected void refreshLineas() {
		if(lineaFrente == null) {
			lineaFrente = new Polygon(new float[]{
					(float) this.getX() , (float) this.getY() ,
					(float) this.getX() +1, (float) this.getY() ,
					(float) this.getX() , (float) this.getY()  + rango
			});//Esquinas
			
			lineaAtras = new Polygon(new float[]{
					(float) this.getX() , (float) this.getY() ,
					(float) this.getX()  +1, (float) this.getY() ,
					(float) this.getX() , (float) this.getY() - rango
			});//Esquinas
			
			lineaDerecha = new Polygon(new float[]{
					(float) this.getX() , (float) this.getY() ,
					(float) this.getX()  +1, (float) this.getY() ,
					(float) this.getX()  + rango, (float) this.getY() 
			});//Esquinas
			
			lineaIzquierda = new Polygon(new float[]{
					(float) this.getX() , (float) this.getY() ,
					(float) this.getX()  +1, (float) this.getY(),
					(float) this.getX()  - rango, (float) this.getY() 
			});//Esquinas
			
			lineaFrente.setOrigin((float) this.getX(), (float) this.getY());//Pos barco
			lineaAtras.setOrigin((float) this.getX() , (float) this.getY() );//Pos barco
			lineaDerecha.setOrigin((float) this.getX() , (float) this.getY() );//Pos barco
			lineaIzquierda.setOrigin((float) this.getX() , (float) this.getY() );//Pos barco
		}else 
			lineaFrente.setPosition((float) this.getX() + this.getSizeX()/2, (float) this.getY() + this.getSizeY()/2); //getX/Y
		lineaFrente.setRotation(-getAngle()); //Get angulo
		
		lineaAtras.setPosition((float) this.getX() + this.getSizeX()/2, (float) this.getY() + this.getSizeY()/2); //getX/Y
		lineaAtras.setRotation(-getAngle()); //Get angulo
		
		lineaDerecha.setPosition((float) this.getX() + this.getSizeX()/2, (float) this.getY() + this.getSizeY()/2); //getX/Y
		lineaDerecha.setRotation(-getAngle()); //Get angulo
		
		lineaIzquierda.setPosition((float) this.getX() + this.getSizeX()/2, (float) this.getY() + this.getSizeY()/2); //getX/Y
		lineaIzquierda.setRotation(-getAngle()); //Get angulo
		
	}
	//MOVIMIENTO
	
	public void right() {
		rotate(vAng*Gdx.graphics.getDeltaTime());
		refreshLineas();
		
	}
	public void left() {
		rotate(-vAng*Gdx.graphics.getDeltaTime());
		refreshLineas();
	}
	public void forward() {
		if(v < vMax)v+=a;
		move();
		refreshLineas();
	}
	public void undoMove() {
		v= -v;
		move();
		refreshLineas();
		v=-v;
	}
	public void backwards() {
		if(v>-vMax)v-=a;
		move();
		refreshLineas();
	}
	public void stop() {
		v=0;
	}
	public void decelerate() {
		if(v>0) {
			v-=0.1;
			move();
			refreshLineas();
		}else if(v<0) {
			v+=0.1;
			move();
			refreshLineas();
		}
		if(v<0.1 && v>-0.1)v=0;
	}
	//REVISAR
	public static void recibeDaño(Sprite a, Bala bullet) {
		Barco b = (Barco) a;
		b.setVidaDelBarco(b.getVidaDelBarco()-bullet.getDanyo());
		MainScreen.balasBorrar.add(bullet);
		if(b.getVidaDelBarco()<=0) {
			MainScreen.barEneBorrar.add(b);
		}
	}
	//DETECCIÓN
	
	/** Compara las lineas del llamador con el bounding del objeto pasado
	 * @param o Sprite con el que se desea comparar (Objeto Pasado)
	 * @return True si toca, False si no
	 */
	//TODO Se añade return polygon y asi sabemos que linea toca, Meterselo al hijo ;)
	public Polygon tocaLinea(Sprite o) {
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
	    shapeRenderer.polygon(lineaDerecha.getTransformedVertices());
	    shapeRenderer.polygon(lineaIzquierda.getTransformedVertices());
	    shapeRenderer.polygon(lineaFrente.getTransformedVertices());
	    shapeRenderer.polygon(lineaAtras.getTransformedVertices());
	    shapeRenderer.end();
	    if(Intersector.overlapConvexPolygons(lineaDerecha, o.getBounds())) {
	    	return lineaDerecha;
	    }
	    
	    if(Intersector.overlapConvexPolygons(lineaIzquierda, o.getBounds())) {
	    	return lineaIzquierda;
	    }
	    
	    if(Intersector.overlapConvexPolygons(lineaFrente, o.getBounds())) {
	    	return lineaFrente;
	    }
	    
	    if(Intersector.overlapConvexPolygons(lineaAtras, o.getBounds())) {
	    	return lineaAtras;
	    }
	    
	    return null;
		
	}
	
	@Override
	public void onRangeOfPlayer() {
		// TODO Auto-generated method stub
		AudioPlayer.detener();
		AudioPlayer.Reproducir("Sonidos//Battle.mp3");
		
	}
	
	@Override
	public void onExitFromRange() {
		// TODO Auto-generated method stub
		AudioPlayer.detener();
		AudioPlayer.Reproducir("Sonidos//Overworld.mp3");
		
	}
	//CANYONES
	public boolean dispararLado(PosicionCanyon lc) {
		return canyones.get(lc).shootIfPosible(municionEnUso, 10);
	}
	/**
	 * Establece los cañones de un lado
	 * @param key lado a establecer
	 * @param canyones los cañones a establecer, puede ser un Array o los cañones uno detras de otro
	 */
	public void setCanyones(PosicionCanyon key, Canyon... canyones) {
		this.canyones.put(key, new CannonSide(this, key, canyones));
	}
	
	
	/*
	 * ▓▓▓▓▓▓▓▓▓▓ GETTERS/SETTERS ▓▓▓▓▓▓▓▓▓▓
	 */
	
	public int getVidaDelBarco() {
		return vida;
	}
	public void setVidaDelBarco(int vidaDelBarco) {
		this.vida = vidaDelBarco;
	}
	public int getNivelDelBarco() {
		return nivel;
	}
	public void setNivelDelBarco(int nivelDelBarco) {
		this.nivel = nivelDelBarco;
	}
	public Municion getMunicionEnUso() {
		return municionEnUso;
	}
	public void setMunicionEnUso(Municion municionEnUso) {
		this.municionEnUso = municionEnUso;
	}
}
/**
 * Representa los {@link objetos.Cañon Cañon}es de un lado del {@link objetos.Barco Barco} Es una clase que une los conjuntos de {@link objetos.Cañon Cañon}es con su cooldown
 *
 */
class CannonSide{
	private ArrayList<Canyon> c;//cañones en el lado
	private double cd = 0; //cooldown en segundos
	private long t0 = System.currentTimeMillis(); //Momento de reinicio del cooldown
	private Barco b;
	private PosicionCanyon pc;
	public CannonSide(List<Canyon>c, Barco b, PosicionCanyon pc) {
		setCannons(c);
		this.b = b;
		this.pc = pc;
	}
	public CannonSide(Barco b, PosicionCanyon pc, Canyon... c) {
		this(Arrays.asList(c), b, pc);
	}
	public ArrayList<Canyon> getCannons() {
		return c;
	}
	public void setCannons(List<Canyon> list) {
		c = new ArrayList<>(list);
	}
	public void setCannons(Canyon... cannons) {
		setCannons(Arrays.asList(cannons));
	}
	public void addCannons(Canyon... cannons) {
		for(Canyon ca : cannons)
			c.add(ca);
	}
	public Canyon getCannon(int pos) {
		return c.get(pos);
		}
	public void setCooldown(double cooldown) {
		cd = cooldown;
		t0 = System.currentTimeMillis();
	}
	public double getCoolDown() {
		return cd;
	}
	private boolean reset() {
		cd = 0;
		return true;
	}
	/**
	 * Determina si los cañones pueden disparar o no.
	 */
	public boolean canShoot() {
		return System.currentTimeMillis()-t0 >=cd*1000?reset():false;
		
	}
	/**
	 * Combrueba si los cañones pueden disparar y si eso dispara
	 * @param cooldown El cooldown despues de terminar de disparar
	 * @return Devuelve si ha disparado
	 */
	public boolean shootIfPosible(double cooldown) {
		return shootIfPosible(Municion.NORMAL, cooldown);
	}
	/**
	 * Combrueba si los cañones pueden disparar y si eso dispara
	 * @param m La munición que van a disparar
	 * @param cooldown El cooldown despues de terminar de disparar
	 * @return Devuelve si ha disparado
	 */
	public boolean shootIfPosible(Municion m, double cooldown) {
		float n=1;
		float s = c.size();
		float[] v = b.getBounds().getTransformedVertices();
		//copiado de git
		float x0 = b.getX();
		float y0 = b.getY();
		float vx = b.getSizeX();
		float vy = b.getSizeY();
		
		/*
		 * 4-----3  6,7----4,5     0,32---32,32
		 * |     |   |      |        |      |
		 * |     |   |      |        |      |
		 * 1-----2  0,1----2,3      0,0----32,0
		 */
		
		//Lo que tenia antes Canal
		//float x0 = v[0];//0
		//float y0 = v[1];//0
		float vX0 = v[2] - x0; //32, 0, 32
		float vY0 = v[3] - y0; // 0, 0, 0
		float vX1 = v[4] - v[2]; //32, 32, 0
		float vY1 = v[5] - v[3]; //32, 0, 32
		if(canShoot()) {
			for(Canyon c: c) {
				float[] x = {0, n/(s+1), 1};
				float[] y = {0, n/(s+1), 1};
				c.disparar(m, (float)(x0+x[pc.getX()]*vx), (float)(y0+y[pc.getY()]*vy), b.getAngle()+ pc.getAngle());
				n++;
			}
			//setCooldown(cooldown);
		}
		return canShoot();
	}
	
	
}
