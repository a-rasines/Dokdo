package objetos;

import com.badlogic.gdx.graphics.Texture;

/**
 * Representa una bala disparada por un {@link objetos.Cañon}.
 *
 */
public class Bala extends Sprite{
	
	private static Texture t ;
	private float vInicial=10;
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
	 * @param saltoX salto en X cada vuelta
	 * @param saltoY salto en Y cada vuelta
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
	
	/*
	 * ▓▓▓▓▓▓▓▓▓▓ GETTERS/SETTERS ▓▓▓▓▓▓▓▓▓▓ 
	 */
	public int getDanyo() {
		return danyo;
	}
}
