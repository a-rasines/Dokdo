package objetos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	public enum PosicionCañon{
		IZQUIERDA,
		DERECHA,
		DELANTE,
		ATRAS
	};
	
	/*
	 * ▓▓▓▓▓▓▓▓▓▓ ATRIBUTOS ▓▓▓▓▓▓▓▓▓▓
	 */
	
	protected int vida;
	protected int nivel;
	protected Municion municionEnUso;
	protected HashMap<PosicionCañon,CannonSide> cañones;
	protected float vMax = 10; //velocidad maxima
	protected float a = 1; //aceleración
	protected float vAng = 50; //velocidad angular en grados
	
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
		this.vida=vida;
		this.nivel=nivel;
		super.x=posX;
		super.y=posY;
		this.municionEnUso=municionActual;
	}
	
	/*
	 * ▓▓▓▓▓▓▓▓▓▓ FUNCIONES ▓▓▓▓▓▓▓▓▓▓
	 */
	
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
	public void backwards() {
		if(v>0)v-=a;
		move();
	}
	public void stop() {
		v=0;
	}
	public void decelerate() {
		if(v>0) {
			v-=0.2;
			move();
		}else if(v<0)v=0;
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
	private ArrayList<Cañon> c;//cañones en el lado
	private double cd = 0; //cooldown en segundos
	private long t0 = System.currentTimeMillis(); //Momento de reinicio del cooldown
	public CannonSide(List<Cañon>c) {
		setCannons(c);
	}
	public CannonSide(Cañon... c) {
		setCannons(c);
	}
	public ArrayList<Cañon> getCannons() {
		return c;
	}
	public void setCannons(List<Cañon> list) {
		c = new ArrayList<>(list);
	}
	public void setCannons(Cañon... cannons) {
		setCannons(Arrays.asList(cannons));
	}
	public void addCannons(Cañon... cannons) {
		for(Cañon ca : cannons)
			c.add(ca);
	}
	public Cañon getCannon(int pos) {
		return c.get(pos);
		}
	public void setCooldown(double cooldown) {
		cd = cooldown;
		t0 = System.currentTimeMillis();
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
}
