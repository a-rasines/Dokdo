package objetos.barcos;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import dg.main.MainScreen;
import objetos.Bala;
import objetos.Canyon;
import objetos.Isla;
import objetos.Municion;
import objetos.Sprite;
/**
 * Representa un barco enemigo. Esta clase contiene un barco con herramientas para IA propia
 * @author algtc
 *
 */
public class BarcoEnemigo extends Barco{
	private boolean enter=false;
	private Polygon lineaFrente;
	private Polygon lineaIzquierda;
	private Polygon lineaDerecha;
	private Polygon lineaAtras;
	private float rango = 150; //Rango de accion
	private boolean isProtecting;
	private Vector2 playerTracker;
	private boolean tracking = false;
	private float[] pos0; //Posicion a volver si estan defendiendo
	
	/*
	 * Plantillas
	 */
	public static BarcoEnemigo lvl1(float x, float y, boolean protecting) {
		BarcoEnemigo be = new BarcoEnemigo(3, 1, x, y, protecting, Municion.NORMAL);
		be.setCanyones(PosicionCanyon.DELANTE, new Canyon(0,0));
    	be.setCanyones(PosicionCanyon.ATRAS, new Canyon(0,0));
    	be.setCanyones(PosicionCanyon.DERECHA, new Canyon(0,0));
    	be.setCanyones(PosicionCanyon.IZQUIERDA, new Canyon(0,0));
    	return be;
	}
	
	/*
	 * Constructores
	 */
	
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
		if(island)pos0 = new float[]{posX, posY};
		v *= 0.9;
		isProtecting = island;
		lineaFrente = new Polygon(new float[]{
				0 , 0 ,
				1, 0,
				0 , 0  + rango
		});
		
		lineaAtras = new Polygon(new float[]{
				0 , 0 ,
				1, 0 ,
				0 , -rango
		});
		lineaDerecha = new Polygon(new float[]{
				0 , 0 ,
				1, 0,
				rango, 0 
		});
		
		lineaIzquierda = new Polygon(new float[]{
				0 , 0 ,
				1, 0,
				-rango, 0 
		});
		refreshLineas();
		/**
		cCombate.setCancion("Sonidos//Battle.mp3");
		hv.setSelCancion(cCombate);
		**/

		
	
	}
	/**
	 * Recalcula las lineas de IA de disparo
	 */
	protected void refreshLineas() {
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

	private Isla isTrackerIntersecting() {
		for(Isla i :MainScreen.islaList){
			if(linePoligonIntersection(new Vector2(getX(), getY()), new Vector2(MainScreen.barco.getX(), MainScreen.barco.getY()), i.getBounds())) {
				return i;
			}
		}
		return null;
	}
	public void IAMove() {
		if(playerTracker == null) {
			playerTracker = new Vector2(getX()-MainScreen.barco.getX(), getY()-MainScreen.barco.getY());
		}else {
			playerTracker.set(getX()-MainScreen.barco.getX(), getY()-MainScreen.barco.getY());
		}
		if(tracking) {
			float angFin; //Angulo al que rotar (absoluto)
			Isla inters = isTrackerIntersecting(); //Isla con la que intersecta el tracker
			if(playerTracker.len()<rango && inters == null) { //Atacar sin obstrucciones
				angFin = MainScreen.barco.getAngle();
				
			}else if (inters != null) { //Intersecta una isla
				Face f = inters.getFirstCollidingFace(new Vector2(getX(), getY()), new Vector2(MainScreen.barco.getX(), MainScreen.barco.getY()));
				if(f == Face.N || f == Face.S) 
					if(MainScreen.barco.getX() > getX())
						angFin = 90;
					else
						angFin = -90;
				else if (f == Face.E || f == Face.W)
					if(MainScreen.barco.getY() > getY())
						angFin = 0;
					else
						angFin = 180;
				angFin = 0;
			}else { //Perseguir sin obstrucciones
				angFin = -((playerTracker.angleDeg()+90)%360);
			}
			if(angFin>180) {
				angFin= angFin-360;	
			}else if(angFin<-180) {
				angFin = angFin+360;
			}
			float angRot = (angFin-getAngle())%360; //Angulo a rotar (relativo)
			if(angRot>180) {
				angRot-=360;	
			}else if(angRot<-180) {
				angRot+=360;
			}
			rotate(angRot);
			if(!collidesWith(MainScreen.islaList)) {
				if(Math.abs((angFin-getAngle())%360)<30) {
					forward();
				}else {
					decelerate();
				}
			}else {
				backwards();
			}
		}else if (isProtecting) {
			
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
		if(vida<=0) {
			MainScreen.barEneBorrar.add(this);
		}
	}
	@Override
	public void onRangeOfPlayer() {//TODO sonido	
		MainScreen.m1.IntercambioSonido(false);
		tracking = true;
	}
	@Override
	public void onExitFromRange() {//TODO sonido
		tracking = false;
		System.out.println("OnRange="+String.valueOf(MainScreen.onRange.size()));
		if(MainScreen.onRange.size() == 0) {
			enter=MainScreen.m1.IntercambioSonido(true);
		}
	}
}
