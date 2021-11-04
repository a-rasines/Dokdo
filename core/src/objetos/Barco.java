package objetos;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
/**
 * Representa un barco, tanto el del jugador como el de los enemigos
 *
 */
public class Barco extends Sprite{
	
	/*
	 * ▓▓▓▓▓▓▓▓▓▓ CLASES INTERNAS ▓▓▓▓▓▓▓▓▓▓
	 */
	
	/**
	 * Clase que contiene y controla los {@link objetos.Cañon Cañon}es en un lado del {@link objetos.Barco Barco}
	 *
	 */
	private class CannonSide{
		private ArrayList<Cañon>c;//cañones en el lado
		private double cd = 0; //cooldown
		private long t0 = System.currentTimeMillis(); //Momento de reinicio del cooldown
		public CannonSide(ArrayList<Cañon>c) {
			this.c = c;
		}
		public ArrayList<Cañon> getCannons() {
			return c;
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
			return System.currentTimeMillis()-t0 >=cd?reset():false;
			
		}
	}
	
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
	
	//TODO Mover esto al barco del jugador
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
	//TODO posiblemente el dibujado no vaya aqui, pero para probar lo pongo
	private Texture tileSet;
	private TextureRegion barco; 	
	
	
	
	public void dibujarBarco() {
		SpriteBatch sb = new SpriteBatch();
		tileSet = new Texture("TexturasMal.png");
		// el TextureRegion selecciona la parte de la imagen deseada (en 0,0 contamaño36*36)
		barco = new TextureRegion(tileSet, 0, 0, 36, 36 );
		
		sb.draw(barco, x, y, 18, 18, 36, 36, 1, 1, super.angle);
		
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
