package objetos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.badlogic.gdx.graphics.Texture;

import databasePack.DatabaseHandler;
import dg.main.MainScreen;
import objetos.barcos.BarcoEnemigo;
import objetos.barcos.BarcoJugador;
/**
 * Representa las islas en el mapa
 *
 */
public class Isla extends Sprite{
	private static Logger logger= Logger.getLogger("Isla");
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
		if(conquistada)this.barcos = new ArrayList<>();
		else this.barcos=new ArrayList<BarcoEnemigo> (Arrays.asList(
				BarcoEnemigo.lvl1(posX + 25,posY + 80, true).setTexturePos(0, 2),
				BarcoEnemigo.lvl1(posX + 50, posY - 50, true).setTexturePos(0, 2),
				BarcoEnemigo.lvl1(posX - 50,posY - 50, true).setTexturePos(0, 2)
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
		barco.setDineros(barco.getDineros()+this.botin);
		DatabaseHandler.SQL.editValue("Islas", "Conquistada = " + "1" , "ID = " + String.valueOf(this.id)+" AND ID_Jugador = "+MainScreen.ID_JUGADOR);
		DatabaseHandler.SQL.editValue("Jugadores", "Dinero= "+String.valueOf(barco.getDineros()) + ", BarcoX = "+String.valueOf(getX()+70)+", BarcoY = "+String.valueOf(getY())+", TimeS = " + String.valueOf(System.currentTimeMillis())+", Vida=" + String.valueOf(barco.getNivelDelBarco()*8) + ", Nivel= "+String.valueOf(barco.getNivelDelBarco()), "ID = " + MainScreen.ID_JUGADOR);
		logger.info("Isla conquistada");
		logger.info("Autoguardado Completado");
	}
	public boolean isConquistada() {
		return conquistada;
	}
}
