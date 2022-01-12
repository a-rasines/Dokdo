package objetos.barcos;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import database.DatabaseHandler;
import dg.main.Dokdo;
import dg.main.MainScreen;
import dg.main.PantallaMuerte;
import objetos.Bala;
import objetos.Municion;
import objetos.Sprite;
public class BarcoJugador extends Barco{
	
	private Circle range;
	//private static PantallaMuerte instance; <- Esto no debería de estar en PantallaMuerte?+

	public BarcoJugador(float x, float y, int vida, int nivel, Municion municionEnUso) {
		super(vida, nivel, x, y, municionEnUso);
		refreshRange();
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
	@Override
	public <T> T move(float x, float y) {
		T a = super.move(x, y);
		DatabaseHandler.SQL.editValue("Jugadores", "BarcoX = "+String.valueOf(getX())+", BarcoY = "+String.valueOf(getY()), "ID = "+DatabaseHandler.JSON.getString("actualUser"));
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
		DatabaseHandler.SQL.editValue("Jugadores", "Rotacion = "+String.valueOf(getAngle()), "ID = "+DatabaseHandler.JSON.getString("actualUser"));
		return a;
	}
	@Override
	public void recibeDanyo(Bala bullet) {//TODO sonido
		vida-=bullet.getDanyo();
		System.out.println(vida);
		MainScreen.balasBorrar.add(bullet);
		if(vida<=0) {
			System.out.println("MURIO");
			Dokdo.getInstance().setScreen(PantallaMuerte.getInstance()); 
		}
	}

}
