package objetos;
/**
 * Representa una mejora. Tanto de un {@link objetos.Barco Barco} como de una {@link objetos.Isla Isla} o un {@link objetos.Cañon Cañon}.
 *
 */
public class Mejoras {
	protected int nivelNecesario;
	protected int precio;
	/**Mejoras generales del juego
	 * @param nivel Nivel necesario para adquirir la mejora
	 * @param precio Precio necesario para comprar la mejora
	 */
	public Mejoras(int nivel, int precio) {
		this.nivelNecesario=nivel;
		this.precio=precio;
	}
	public int getNivelNecesario() {
		return nivelNecesario;
	}
	public void setNivelNecesario(int nivelNecesario) {
		this.nivelNecesario = nivelNecesario;
	}
	public int getPrecioDeVenta() {
		return precio;
	}
	public void setPrecioDeVenta(int precioDeVenta) {
		this.precio = precioDeVenta;
	}
}
