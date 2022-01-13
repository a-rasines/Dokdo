package objetos;

import com.badlogic.gdx.graphics.Texture;

import dg.main.MainScreen;
import objetos.barcos.Barco;

/**
 * Representa una bala disparada por un {@link objetos.Cañon}.
 *
 */
public class Bala extends Sprite{
	private float decelerate = 0.1f; 
	private static Texture t ;
	
	private Barco barcoDisparo;
	private int veces;
	private int cd;
	private long t0 = 0;
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
	public Bala(float x0, float y0, float vel, float angulo, int danyo, Barco barco) {
		super(x0, y0, vel, 8, 8);
		rotate(angulo);
		super.tMap = t;
		this.danyo = danyo;
		this.barcoDisparo = barco;
		this.veces = barco.getMunicionEnUso().getVeces();
		this.cd = barco.getMunicionEnUso().getCoolDown();
		//TODO hacer el bucle de movimiento
	}
	
	public int getVeces() {
		return veces;
	}

	public void setVeces(int veces) {
		this.veces = veces;
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
			v-=decelerate;
			move();
		}else if(v<0) {
			v+=decelerate;
			move();
		}
		if(v<decelerate && v>-decelerate)MainScreen.balasBorrar.add(this);
	}
	
	public float getDanyoVel(Barco residente) {
		return residente.getNivelDelBarco() * this.getVelocidad();
	}
	/*
	 * ▓▓▓▓▓▓▓▓▓▓ GETTERS/SETTERS ▓▓▓▓▓▓▓▓▓▓ 
	 */
	public int getDanyo() {
		t0 = System.currentTimeMillis();
		return danyo;
	}
	public boolean canDamage() {
		return System.currentTimeMillis()-t0 >=cd*1000;
	}
	public float getVelocidad() {
		return v;
	}
	public Barco getBarco() {
		return this.barcoDisparo;
	}
	public boolean barcoDisparo(Barco barcoComparado) {
		return this.barcoDisparo.equals(barcoComparado);
	}
	public void recudirVeces() {
		this.veces--;
	}
}