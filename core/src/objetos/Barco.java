package objetos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import dg.main.AudioPlayer;
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
		IZQUIERDA(0, 2),
		DERECHA(0, 1),
		DELANTE(1, 0),
		ATRAS(2, 0);
		private int x;
		private int y;
		PosicionCanyon(int i, int j) {
			x = i;
			y = j;
		}
		int getX() {
			return x;
		}
		int getY() {
			return y;
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
		super(posX, posY, 0, 0, 32, 32, 150); //TODO Ajustar rango hasta una distancia interesante
		super.tMap = t;
		this.vida=vida;
		this.nivel=nivel;
		this.municionEnUso=municionActual;
	}
	
	
	
	/*
	 * ▓▓▓▓▓▓▓▓▓▓ FUNCIONES ▓▓▓▓▓▓▓▓▓▓
	 */
	
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
	
	//DETECCIÓN
	
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
	public boolean disaprarLado(PosicionCanyon lc) {
		return canyones.get(lc).shootIfPosible(municionEnUso, canyones.get(lc).getCoolDown());
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
	public CannonSide(Canyon... c) {
		setCannons(c);
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
	 * @param m La munición que van a disparar
	 * @param cooldown El cooldown despues de terminar de disparar
	 * @return Devuelve si ha disparado
	 */
	public boolean shootIfPosible(Municion m, double cooldown) {
		int n=1;
		int s = c.size();
		float x0 = b.getX();
		float y0 = b.getY();
		int vx = b.getSizeX();
		int vy = b.getSizeY();
		if(canShoot()) {
			for(Canyon c: c) {
				int[] x = {0, n/s, 1};
				int[] y = {0, n/s, vy};
				c.disparar(m, x0+(x[pc.getX()]*vx), y0+(y[pc.getY()]*vy), b.getAngle());
				n++;
			}
			setCooldown(cooldown);
		}
		return canShoot();
	}
	
	
}
