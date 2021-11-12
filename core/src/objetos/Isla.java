package objetos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
/**
 * Representa las islas en el mapa
 *
 */
public class Isla extends Sprite{
	protected int nivelRecomendado;
	protected List<Barco> barcos;
	protected int botin;
	private static Texture t = new Texture("tileSetIsla.png");
	/** Islas generales del juego.
	 * @param posX
	 * @param posY
	 * @param nivel
	 * @param botin
	 * @param barcosProtegiendo
	 */
	public Isla(float posX, float posY, int nivel, int botin,List<Barco> barcosProtegiendo) {
		super(posX, posY, 0, 0, 64, 64);
		this.nivelRecomendado=nivel;
		super.tMap = t;
		this.barcos=barcosProtegiendo;
		this.botin=botin;
	}
	/** Islas generales del juego.
	 * @param posX
	 * @param posY
	 * @param nivel
	 * @param botin
	 * @param barcosProtegiendo 
	 */
	public Isla(float posX, float posY, int nivel, int botin, Barco... barcosProtegiendo) {
		this(posX, posY, nivel, botin, Arrays.asList(barcosProtegiendo));
	}
	
	@Override
	public void onRangeOfPlayer() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onExitFromRange() {
		// TODO Auto-generated method stub
		
	}
	
	public int getNivelRecomendado() {
		return nivelRecomendado;
	}
	public void setNivelRecomendado(int nivelRecomendado) {
		this.nivelRecomendado = nivelRecomendado;
	}
	public List<Barco> getBarcos() {
		return barcos;
	}
	public void setBarcos(ArrayList<Barco> barcos) {
		this.barcos = barcos;
	}
	public int getBotin() {
		return botin;
	}
	public void setBotin(int botin) {
		this.botin = botin;
	}
}
