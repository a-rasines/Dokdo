package objetos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Representa todo objeto movil
 *
 */
public abstract class Sprite {
	public static Texture tMap;
	protected float x = 0;
	protected float y = 0;
	protected float v = 0; //velocidad
	protected float angle = 0;
	/**
	 * Mueve el objeto por velocidad en el ángulo especificado
	 */
	public void move() {
		move((float)(v*Math.sin(Math.toRadians(angle))),(float)(v*Math.cos(Math.toRadians(angle))));
	}
	/**
	 * Mueve el objeto coordenadas personalizadas
	 * @param x movimiento en x
	 * @param y movimiento en y
	 */
	public void move(float x, float y) {
		this.x+=x;
		this.y+=y;
	}
	/**
	 * Rota el objeto q grados
	 * @param q grados a girar
	 */
	public void rotate(double q) {
		angle = (float) ((angle + q)%360);
	}
	/**
	 * Hace un print de la posición y la orientación del objeto
	 */
	public void printPos() {
		System.out.println("( "+x+" , "+y+" ) "+angle+"º, v="+v);
	}
	
	
	
	/** Dibuja sprites especificos de un mapa de Sprites. 64 px = Isla, 32 px = Barco, 16 px = cañon, 8 px = bala
	 * @param tileSet, Archivo en el que se encuentra el sprite deseado
	 * @param columna, Columna en la que se encuentra el sprite (Vertical)
	 * @param fila, Fila en la que se encuentra el sprite (Horizontal)
	 * @param posX, Posicion de X
	 * @param posY, posicion de Y
	 * @param tamañoPx, Tamaño en pixeles, los sprites son cuadrados
	 */
	private SpriteBatch sb = new SpriteBatch();
	public void dibujar(Texture tileSet, int columna, int fila, float posX, float posY, int tamanyoPx) {
		
		
	
			//En filas y columnas, se indica la posicioin *32 (32x32 pixeles cada barco) +1 ya que en la textura habra un pixel entre estos
			//Empieza a contar en 0 las filas, aunque el primer sprite se encontrara en el pixel 1-1
			TextureRegion sprite = new TextureRegion(tileSet, fila*32 +1, columna*32 +1, 32, 32);
			
			sb.begin();
			//En orden, textura, x, y, CentroX, CentroY, Anchura, Altura, EscalaX, EscalaY, Angulo (En grados)
			sb.draw(sprite, posX, posY, tamanyoPx/2, tamanyoPx/2, tamanyoPx, tamanyoPx, 1, 1, -angle);
			sb.end();
		
	}
		
	
	
	//Getters 
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public float getAngle() {
		return angle;
	}
	
}
