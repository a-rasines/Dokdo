package objetos.barcos;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import dg.main.MainScreen;
import objetos.Bala;
import objetos.Isla;
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
	public BarcoEnemigo(int vida, int nivel, float posX, float posY, boolean island) {
		super(vida, nivel, posX, posY);
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
	public boolean linePoligonIntersection(Vector2 v1, Vector2 v2, Polygon p) {
		/*
		 * f(x) = nx + m
		 * g(x) = ax + b
		 * nx + m = ax + b
		 * (n-a)x = b-m
		 * x = b-m/(n-a)
		 * n = y2-y1/(x2-x1)
		 * m = y1 - x1*n
		 * 
		 */
		float[] v = p.getTransformedVertices();
		float x1 = v1.x;
		float y1 = v1.y;
		float x2 = v2.x;
		float y2 = v2.y;
		for(int i = 0; i<p.getTransformedVertices().length/2-1;i++) {
			float x3 = v[i*2];
			float y3 = v[i*2+1];
			float x4 = v[i*2+2];
			float y4 = v[i*2+3];
			float n1 = (y2- y1)/(x2 - x1);
			float n2 = (y4 - y3)/(x4 - x3);
			float m1 = y1 - x1*n1;
			float m2 = y3 - x3*n2;
			
			if (n1 == n2 && m1 == m2)return true; //Coincidentes
			else if(n1 == n2)return false; //Paralelas no coincidentes
			if(x1 == x2) { //Linea 1 vertical
				if(x1*n2+m2 > Math.min(y1, y2) && x1*n2+m2 < Math.max(y1, y2)) {
					return true;
				}
			}else if (x3 == x4) { //Linea 2 verical
				if(x3*n1+m1 > Math.min(y3, y4) && x3*n1+m1 < Math.max(y3, y4)) {
					return true;
				}
			}else {
				float x = (m1 - m2)/(n2 - n1);
				if( x> Math.min(x1, x2) && x< Math.max(x1, x2) && x>Math.min(x3, x4) && x<Math.max(x3, x4))return true;
			}
		}
		return false;
	}
	private boolean isTrackerIntersecting() {
		Boolean end = false;
		for(Isla i :MainScreen.islaList){
			if(linePoligonIntersection(new Vector2(getX(), getY()), new Vector2(MainScreen.barco.getX(), MainScreen.barco.getY()), i.getBounds())) {
				end = true;
				break;
			}
		}
		return end;
	}
	public void IAMove() {
		if(playerTracker == null) {
			playerTracker = new Vector2(getX()+MainScreen.barco.getX(), getY()+MainScreen.barco.getY());
		}else {
			playerTracker.set(getX()+MainScreen.barco.getX(), getY()+MainScreen.barco.getY());
			System.out.println(isTrackerIntersecting()?"PathfindCollision":"ClearPathfind");
		}
		if(isTrackerIntersecting()) { //Hay isla en el camino
			//TODO esquivar la isla de la forma mas eficiente
		}else { //Camino despejado
			PosicionCanyon p = tocaLinea(MainScreen.barco);
			if(p != null) { //Posición de ataque
				rotate(Math.max(-this.vAng, Math.min(this.vAng, MainScreen.barco.getAngle()-getAngle())));
				forward();
			}else{ //Persecucion
				rotate(Math.max(-this.vAng, Math.min(this.vAng, playerTracker.angleDeg()-getAngle())));
				forward();
			}
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
