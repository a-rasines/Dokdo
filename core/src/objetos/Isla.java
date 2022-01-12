package objetos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;

import databasePack.DatabaseHandler;
import objetos.barcos.BarcoEnemigo;
import objetos.barcos.BarcoJugador;
/**
 * Representa las islas en el mapa
 *
 */
public class Isla extends Sprite{
	private int nivelRecomendado;
	private int id;
	private List<BarcoEnemigo> barcos;
	private int botin;
	private boolean conquistada = false;
	private static Texture t;
	static {
		try {
			t = new Texture("tileSetIsla.png");
		}catch(Exception e) {}
	}
	
	/** Islas generales del juego.
	 * @param posX
	 * @param posY
	 * @param nivel
	 * @param botin
	 * @param barcosProtegiendo
	 */
	public Isla(int id, float posX, float posY, int nivel, int botin, boolean conquistada) {
		super(posX, posY, 0, 64, 64);
		this.id = id;
		this.nivelRecomendado=nivel;
		this.conquistada = conquistada;
		super.tMap = t;
		this.barcos=new ArrayList<BarcoEnemigo> (Arrays.asList(
				BarcoEnemigo.lvl1(0,0, true).setTexturePos(0, 2).tpTo( posX + 25,  posY + 80),
				BarcoEnemigo.lvl1(0,0, true).setTexturePos(0, 2).tpTo(posX + 50, posY - 50),
				BarcoEnemigo.lvl1(0,0, true).setTexturePos(0, 2).tpTo(posX - 50,posY - 50)
			));
		this.botin=botin;
	}
	/** Islas generales del juego.
	 * @param posX
	 * @param posY
	 * @param nivel
	 * @param botin
	 * @param barcosProtegiendo 
	 */
//	public Isla(float posX, float posY, int nivel, int botin, Barco... barcosProtegiendo) {
//		this(posX, posY, nivel, botin, Arrays.asList(barcosProtegiendo));
//	}
	
	@Override
	public void onRangeOfPlayer() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onExitFromRange() {
		// TODO Auto-generated method stub
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNivelRecomendado() {
		return nivelRecomendado;
	}
	public void setNivelRecomendado(int nivelRecomendado) {
		this.nivelRecomendado = nivelRecomendado;
	}
	public List<BarcoEnemigo> getBarcos() {
		return barcos;
	}
	public void setBarcos(List<BarcoEnemigo> barcos) {
		this.barcos = barcos;
	}
	public void setBarcos(BarcoEnemigo... barcos) {
		this.barcos = Arrays.asList(barcos);
	}
	public int getBotin() {
		return botin;
	}
	public void setBotin(int botin) {
		this.botin = botin;
	}
	
	public void conquistar(BarcoJugador barco) {
		conquistada = true;
		DatabaseHandler.SQL.editValue("Islas", "Conquistada = " + "1" , String.valueOf(this.id));
		DatabaseHandler.SQL.editValue("Jugadores", "BarcoX = "+String.valueOf(getX()+45)+", BarcoY = "+String.valueOf(barco.getY())+", TimeS = " + String.valueOf(System.currentTimeMillis()), "ID = " + DatabaseHandler.JSON.getString("actualUser"));
	}
	public boolean isConquistada() {
		return conquistada;
	}
}
