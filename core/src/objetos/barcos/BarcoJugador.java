package objetos.barcos;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONObject;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import DataBase.DatabaseHandler;
import dg.main.MainScreen;
import dg.main.PantallaMuerte;
import objetos.Bala;
import objetos.Sprite;
public class BarcoJugador extends Barco{
	
	private Circle range = new Circle();

	public BarcoJugador(int vida, int nivel, float posX, float posY, int rango) {
		super(vida, nivel, posX, posY);
		range.setRadius(rango);
		range.setPosition(new Vector2(getX(),getY()));
		// TODO Auto-generated constructor stub
	}
	/**Refresca la posicion del rango(Circulo)
	 * 
	 */
	protected void refreshRange() {
		if(range == null) {
			range.setRadius(150);
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
	@SuppressWarnings("unchecked")
	@Override
	public <T> T move(float x, float y) {
		T a = super.move(x, y);
		JSONObject barcoPos = new JSONObject();
		barcoPos.put("x", getX());
		barcoPos.put("y", getY());
		DatabaseHandler.writeToJSON("barcoPos", barcoPos, true);
		refreshRange();
		return a;
	}
	@Override
	public <T extends Sprite> T rotate(double q) {
		T a = super.rotate(q);
		DatabaseHandler.writeToJSON("barcoRot", getAngle(), true);
		return a;
	}
	@Override
	public void recibeDanyo(Bala bullet) {
		vida-=bullet.getDanyo();
		MainScreen.balasBorrar.add(bullet);
		if(vida<=0) {
			 //((Game)Gdx.app.getApplicationListener()).setScreen(new PantallaMuerte());
		}
	}

}
