package objetos;
/**
 * Esta clase representa un cañon de un {@link objetos.Barco Barco}
 *
 */
public class Ca�on extends Mejoras{
	private double x;
	private double y;
	public Ca�on(int nivel, int precio, double x, double y) {
		super(nivel, precio);
		this.x = x;
		this.y = y;
	}
	public void disparar(Municion m) {
		
	}
}
