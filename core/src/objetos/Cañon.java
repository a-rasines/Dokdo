package objetos;
/**
 * Esta clase representa un caÃ±on de un {@link objetos.Barco Barco}
 *
 */
public class Cañon extends Mejoras{
	private double x;
	private double y;
	public Cañon(int nivel, int precio, double x, double y) {
		super(nivel, precio);
		this.x = x;
		this.y = y;
	}
	public void disparar(Municion m) {
		
	}
}
