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
		move((float)(v*Math.cos(angle)),(float)(v*Math.sin(angle)));
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
	
	
	
	/** Dibuja sprites especificos de un mapa de Sprites
	 * @param tileSet, Archivo en el que se encuentra el sprite deseado
	 * @param columna, Columna en la que se encuentra el sprite (Vertical)
	 * @param fila, Fila en la que se encuentra el sprite (Horizontal)
	 * @param posX, Posicion de X
	 * @param posY, posicion de Y
	 * @param angulo, Angulo en el que dibujar el Sprite(En grados)
	 * @param tamañoPx, Tamaño en pixeles, los sprites son cuadrados
	 */
	public void dibujar(Texture tileSet, int columna, int fila, float posX, float posY, float angulo, int tamañoPx) {
		SpriteBatch sb = new SpriteBatch();
		//TODO Añadir los casos de las balas, cañones y tal
		switch (tamañoPx) {
		case 32: //Barcos y tal
			//En filas y columnas, se indica la posicioin *32 (32x32 pixeles cada barco) +1 ya que en la textura habra un pixel entre estos
			//Empieza a contar en 0 las filas, aunque el primer sprite se encontrara en el pixel 1-1
			TextureRegion sprite32 = new TextureRegion(tileSet, fila*32 +1, columna*32 +1, 32, 32);
			
			sb.begin();
			//En orden, textura, x, y, CentroX, CentroY, Anchura, Altura, EscalaX, EscalaY, Angulo (En grados)
			sb.draw(sprite32, posX, posY, 16, 16, 32, 32, 1, 1, angulo);
			sb.end();
			break;
		case 64: //Islas y tal
			//En filas y columnas, se indica la posicioin *64 (64x64 pixeles cada barco) +1 ya que en la textura habra un pixel entre estos
			//Empieza a contar en 0 las filas, aunque el primer sprite se encontrara en el pixel 1-1
			TextureRegion sprite64 = new TextureRegion(tileSet, fila*64 +1, columna*64 +1, 64, 64);
			
			sb.begin();
			//En orden, textura, x, y, CentroX, CentroY, Anchura, Altura, EscalaX, EscalaY, Angulo (En grados)
			sb.draw(sprite64, posX, posY, 32, 32, 64, 64, 1, 1, angulo);
			sb.end();
			break;
		default:
			System.out.println("Ningun valor valido");
			break;
		}
		
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
