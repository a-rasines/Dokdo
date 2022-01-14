package objetos.barcos;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import databasePack.DatabaseHandler;
import dg.main.Dokdo;
import dg.main.MainScreen;
import dg.main.PantallaMuerte;
import objetos.Bala;
import objetos.Municion;
import objetos.Sprite;
public class BarcoJugador extends Barco{
	
	private Circle range;
	//private static PantallaMuerte instance; <- Esto no debería de estar en PantallaMuerte?+
	private final int MAX = 6900;
	private final int MAXX = 6300;
	private final int MIN = -6900;
	private final int MINY = -6300;
	private int dineros;
	public BarcoJugador(float x, float y, int vida, int nivel, Municion municionEnUso, int dineros) {
		super(vida, nivel, x, y, municionEnUso);
		refreshRange();
		this.dineros = dineros;
	}
	
	/**Refresca la posicion del rango(Circulo)
	 * 
	 */
	protected void refreshRange() {
		if(range == null) {
			range = new Circle();
			range.setRadius(600);
			range.setPosition(new Vector2(getX(),getY()));
		} else {
			range.setPosition(new Vector2(getX(),getY()));
		}
	}
	
	public int getDineros() {
		return dineros;
	}
	public void setDineros(int dineros) {
		this.dineros = dineros;
	}
	
	//Colisiones con el Circulo
		public boolean enRango(Sprite o) {
			if(o==null)return false;
			return Sprite.overlapCirclePolygon(o.getBounds(), range);
		}
		public boolean enRango(Iterable<? extends Sprite> l) {
			for(Sprite s : l) {
				if (enRango(s))return true;
			}
			return false;
		}
		
		public <T extends Sprite> List<T> getEnRango(Iterable<T> l){
			LinkedList<T> end = new LinkedList<>();
			for(T s : l)if(enRango(s))end.add(s);
			return end;
		}
		
	private boolean limit = false;
	@Override
	public <T> T move(float x, float y) {
		T a = super.move(x, y);
		if(this.getX() > MAXX) {
			this.tpTo(MIN, this.getY());
		} else if (this.getX() < MIN) {
			this.tpTo(MAXX, this.getY());
		}
		
		if(this.getY() > MAX) {
			this.tpTo(this.getX(), MINY);
		} else if( this.getY() < MINY) {
			this.tpTo(this.getX(), MAX);
		}
		refreshRange();
		
		return a;
	}
	@Override
	public <T> T tpTo(float x, float y) {
		T a= super.tpTo(x, y);
		refreshRange();
		return a;
	}
	@Override
	public <T extends Sprite> T rotate(double q) {
		T a = super.rotate(q);
		return a;
	}
	@Override
	public void recibeDanyo(Bala bullet) {//TODO sonido
		vida-=bullet.getDanyo();
		MainScreen.balasBorrar.add(bullet);
		System.out.println(vida);
		if(vida<=0) {
			DatabaseHandler.SQL.editValue("Jugadores", "Vida= 10", "ID = " + MainScreen.ID_JUGADOR);
			Dokdo.getInstance().setScreen(PantallaMuerte.getInstance()); 
		}
	}

}
