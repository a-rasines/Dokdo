package objetos.barcos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import dg.main.MainScreen;
import objetos.Bala;
import objetos.Isla;
import objetos.Municion;
import objetos.Sprite;
/**
 * Representa un barco enemigo. Esta clase contiene un barco con herramientas para IA propia
 * @author algtc
 *
 */
public class BarcoEnemigo extends Barco{
	private Polygon lineaFrente;
	private Polygon lineaIzquierda;
	private Polygon lineaDerecha;
	private Polygon lineaAtras;
	private float rango = 150; //Rango de accion
	private boolean isProtecting;
	private Vector2 playerTracker;
	private boolean tracking = false;
	
	/**
	 * Crea un barco enemigo
	 * @param vida vida del barco
	 * @param nivel nivel del barco
	 * @param posX posición X inicial
	 * @param posY posición Y inicial
	 * @param island esta protegiendo una isla?
	 */
	public BarcoEnemigo(int vida, int nivel, float posX, float posY, boolean island, Municion municionEnUso) {
		super(vida, nivel, posX, posY, municionEnUso);
		isProtecting = island;
		refreshLineas();
	}
	/**
	 * Recalcula las lineas de IA de disparo
	 */
	protected void refreshLineas() {
		if(lineaFrente == null) {
			lineaFrente = new Polygon(new float[]{
					(float) this.getX() , (float) this.getY() ,
					(float) this.getX() +1, (float) this.getY() ,
					(float) this.getX() , (float) this.getY()  + rango
			});//Esquinas
			
			lineaAtras = new Polygon(new float[]{
					(float) this.getX() , (float) this.getY() ,
					(float) this.getX()  +1, (float) this.getY() ,
					(float) this.getX() , (float) this.getY() - rango
			});//Esquinas
			
			lineaDerecha = new Polygon(new float[]{
					(float) this.getX() , (float) this.getY() ,
					(float) this.getX()  +1, (float) this.getY() ,
					(float) this.getX()  + rango, (float) this.getY() 
			});//Esquinas
			
			lineaIzquierda = new Polygon(new float[]{
					(float) this.getX() , (float) this.getY() ,
					(float) this.getX()  +1, (float) this.getY(),
					(float) this.getX()  - rango, (float) this.getY() 
			});//Esquinas
			
			lineaFrente.setOrigin((float) this.getX(), (float) this.getY());//Pos barco
			lineaAtras.setOrigin((float) this.getX() , (float) this.getY() );//Pos barco
			lineaDerecha.setOrigin((float) this.getX() , (float) this.getY() );//Pos barco
			lineaIzquierda.setOrigin((float) this.getX() , (float) this.getY() );//Pos barco
		}else 
			lineaFrente.setPosition((float) this.getX() + this.getSizeX()/2, (float) this.getY() + this.getSizeY()/2); //getX/Y
		lineaFrente.setRotation(-getAngle()); //Get angulo
		
		lineaAtras.setPosition((float) this.getX() + this.getSizeX()/2, (float) this.getY() + this.getSizeY()/2); //getX/Y
		lineaAtras.setRotation(-getAngle()); //Get angulo
		
		lineaDerecha.setPosition((float) this.getX() + this.getSizeX()/2, (float) this.getY() + this.getSizeY()/2); //getX/Y
		lineaDerecha.setRotation(-getAngle()); //Get angulo
		
		lineaIzquierda.setPosition((float) this.getX() + this.getSizeX()/2, (float) this.getY() + this.getSizeY()/2); //getX/Y
		lineaIzquierda.setRotation(-getAngle()); //Get angulo
		
	}
	
	/** Compara las lineas del llamador con el bounding del objeto pasado
	 * @param o Sprite con el que se desea comparar (Objeto Pasado)
	 * @return True si toca, False si no
	 */
	//TODO Se añade return polygon y asi sabemos que linea toca, Meterselo al hijo ;)
	public PosicionCanyon tocaLinea(Sprite o) {
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
	    shapeRenderer.polygon(lineaDerecha.getTransformedVertices());
	    shapeRenderer.polygon(lineaIzquierda.getTransformedVertices());
	    shapeRenderer.polygon(lineaFrente.getTransformedVertices());
	    shapeRenderer.polygon(lineaAtras.getTransformedVertices());
	    shapeRenderer.end();
	    if(Intersector.overlapConvexPolygons(lineaDerecha, o.getBounds())) {
	    	return PosicionCanyon.DERECHA;
	    }
	    
	    if(Intersector.overlapConvexPolygons(lineaIzquierda, o.getBounds())) {
	    	return PosicionCanyon.IZQUIERDA;
	    }
	    
	    if(Intersector.overlapConvexPolygons(lineaFrente, o.getBounds())) {
	    	return PosicionCanyon.DELANTE;
	    }
	    
	    if(Intersector.overlapConvexPolygons(lineaAtras, o.getBounds())) {
	    	return PosicionCanyon.ATRAS;
	    }
	    
	    return null;
	}

	private boolean isTrackerIntersecting() {
		Boolean end = false;
		for(Isla i :MainScreen.islaList){
			if(linePoligonIntersection(new Vector2(getX(), getY()), new Vector2(MainScreen.barco.getX(), MainScreen.barco.getY()), i.getBounds())) {
				System.out.println(i.getX());
				System.out.println(i.getY());
				end = true;
				break;
			}
		}
		return end;
	}
	public void IAMove() {
		if(playerTracker == null) {
			playerTracker = new Vector2(getX()-MainScreen.barco.getX(), getY()-MainScreen.barco.getY());
		}else {
			playerTracker.set(getX()-MainScreen.barco.getX(), getY()-MainScreen.barco.getY());
			System.out.println(isTrackerIntersecting()?"PathfindCollision":"ClearPathfind");
		}
		if(isTrackerIntersecting()) { //Hay isla en el camino
			//TODO esquivar la isla de la forma mas eficiente
		}else { //Camino despejado
			float angFin;
			if(playerTracker.len()<rango) { //Atacar
				angFin = MainScreen.barco.getAngle();
				
			}else {
				System.out.println(playerTracker.angleDeg());
				angFin = (180+playerTracker.angleDeg())%360;
				//angFin = playerTracker.angleDeg();
				System.out.println(angFin);
				System.out.println(getAngle());
			}
			System.out.println((angFin-getAngle()));
			if(angFin-getAngle()<30) {
				forward();
			}else {
				decelerate();
			}
			rotate((angFin-getAngle()));
		}
	}
	@Override
	public <T> T move(float x, float y) {
		T a =super.move(x, y);
		refreshLineas();
		return a;
	}
	@Override
	public <T extends Sprite> T rotate(double q) {
		T a = super.rotate(q);
		refreshLineas();
		return a;
	}
	@Override
	public void recibeDanyo(Bala bullet) {
		super.recibeDanyo(bullet);
		if(vida<=0) {
			MainScreen.barEneBorrar.add(this);
		}
	}
	@Override
	public void recibeDanyoContinuo(Bala bullet) {
		super.recibeDanyoContinuo(bullet);
		
	}
	@Override
	public void onRangeOfPlayer() {
		super.onRangeOfPlayer();
		tracking = true;
	}
	@Override
	public void onExitFromRange() {
		super.onExitFromRange();
		tracking = false;
	}
}
