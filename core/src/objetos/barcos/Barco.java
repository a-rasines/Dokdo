package objetos.barcos;

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
import objetos.Bala;
import objetos.Canyon;
import objetos.Municion;
import objetos.Sprite;
import objetos.barcos.Barco.PosicionCanyon;
/**
 * Representa un barco, tanto el del jugador como el de los enemigos
 *
 */
public class Barco extends Sprite{
	
	/*
	 * ▓▓▓▓▓▓▓▓▓▓ ENUMS ▓▓▓▓▓▓▓▓▓▓ 
	 */
	
	/**
	 * Tipo de {@link objetos.barcos.Barco Barco}
	 *
	 */
	public static enum Tipo{
	};
	/**
	 * Lado del {@link objetos.barcos.Barco Barco} en el que está el {@link objetos.Cañon Cañon}
	 *
	 */
	public enum PosicionCanyon{
		IZQUIERDA(3, 0, 270),
		DERECHA(1, 2, 90),
		DELANTE(3, 2, 0),
		ATRAS(0, 1, 180);
		private int x;
		private int y;
		private int angle;
		/*
		 * 3---2
		 * |   |
		 * 0---1
		 */
		PosicionCanyon(int i, int j, int k) {
			x = i;
			y = j;
			angle = k;
		}
		int getFirst() {
			return x;
		}
		int getSecond() {
			return y;
		}
		int getAngle() {
			return angle;
		}
	};
	
	/*
	 * ▓▓▓▓▓▓▓▓▓▓ ATRIBUTOS ▓▓▓▓▓▓▓▓▓▓
	 */
	
	
	
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
		super(posX, posY, 0, 0, 32, 32); //TODO Ajustar rango hasta una distancia interesante
		super.tMap = t;
		canyones = new HashMap<>();
		this.vida=vida;
		this.nivel=nivel;
		this.municionEnUso=municionActual;
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
	
	//MOVIMIENTO
	
	public void right() {
		rotate(vAng*Gdx.graphics.getDeltaTime());
		
	}
	public void left() {
		rotate(-vAng*Gdx.graphics.getDeltaTime());
	}
	public void forward() {
		if(v < vMax)v+=a;
		move();
	}
	public void undoMove() {
		v= -v;
		move();
		v=-v;
	}
	public void backwards() {
		if(v>-vMax)v-=a;
		move();
	}
	public void stop() {
		v=0;
	}
	public void decelerate() {
		if(v>0) {
			v-=0.1;
			move();
		}else if(v<0) {
			v+=0.1;
			move();
		}
		if(v<0.1 && v>-0.1)v=0;
	}
	//REVISAR
	public void recibeDaño( Bala bullet) {
		vida -= bullet.getDanyo();
		MainScreen.balasBorrar.add(bullet);
		System.out.println(vida);
	}
	//DETECCIÓN
	
	@Override
	public void onRangeOfPlayer() {
		AudioPlayer.detener();
		AudioPlayer.Reproducir("Sonidos//Battle.mp3");
		
	}
	
	@Override
	public void onExitFromRange() {
		if(MainScreen.onRange.size() == 0) {
			AudioPlayer.detener();
			AudioPlayer.Reproducir("Sonidos//Overworld.mp3");
		}
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
 * Representa los {@link objetos.Cañon Cañon}es de un lado del {@link objetos.barcos.Barco Barco} Es una clase que une los conjuntos de {@link objetos.Cañon Cañon}es con su cooldown
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
		/*
		 * 4-----3  6,7----4,5     0,32---32,32
		 * |     |   |      |        |      |
		 * |     |   |      |        |      |
		 * 1-----2  0,1----2,3      0,0----32,0
		 */
		
		float x0 = v[2*pc.getFirst()];
		float y0 = v[2*pc.getFirst()+1];
		float x1 = v[2*pc.getSecond()];
		float y1 = v[2*pc.getSecond()+1];
		if(canShoot()) {
			for(Canyon c: c) {
				
				c.disparar(m,n*(x0+x1)/(s+1), n*(y0+y1)/(s+1),b.getAngle()+ pc.getAngle(), b instanceof BarcoJugador);
				n++;
			}
			//setCooldown(cooldown);
			return true;
		}
		return false;
	}
	
	
}
