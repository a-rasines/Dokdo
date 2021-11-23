package objetos;

import com.badlogic.gdx.graphics.Texture;

/**
 * Representa una bala disparada por un {@link objetos.Cañon}.
 *
 */
public class Bala extends Sprite{
	
	private static Texture t ;
	static {
		try {
		t = new Texture("tileSetBala.png");
		}catch(Exception e) {}
		}
	private int danyo;
	/**
	 * Bala disparada por un {@link objetos.Cañon Cañon}
	 * @param x0 x inicial
	 * @param y0 y inicial
	 * @param vel Velocidad Inicial de la bala
	 * @param angulo Angulo de disparo de la bala
	 * @param daño daño que ocasiona al colisionar
	 */
	public Bala(float x0, float y0, float vel, float angulo, int danyo) {
		super(x0, y0, vel, angulo, 8, 8, 0);
		super.tMap = t;
		this.danyo = danyo;
		//TODO hacer el bucle de movimiento
	}
	
	@Override
	public void onRangeOfPlayer() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onExitFromRange() {
		// TODO Auto-generated method stub
		
	}
	public void decelerate() {
		if(v>0) {
			v-=0.05;
			move();
		}else if(v<0) {
			v+=0.05;
			move();
		}
		if(v<0.05 && v>-0.05)v=0;
	}
	/*
	 * ▓▓▓▓▓▓▓▓▓▓ GETTERS/SETTERS ▓▓▓▓▓▓▓▓▓▓ 
	 */
	public int getDanyo() {
		return danyo;
	}
	public float getVelocidad() {
		return v;
	}
}