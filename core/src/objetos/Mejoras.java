package objetos;

public class Mejoras {
	protected int nivelNecesario;
	protected int precioDeVenta;
	public Mejoras(int nivel, int precio) {
		this.nivelNecesario=nivel;
		this.precioDeVenta=precio;
	}
	public int getNivelNecesario() {
		return nivelNecesario;
	}
	public void setNivelNecesario(int nivelNecesario) {
		this.nivelNecesario = nivelNecesario;
	}
	public int getPrecioDeVenta() {
		return precioDeVenta;
	}
	public void setPrecioDeVenta(int precioDeVenta) {
		this.precioDeVenta = precioDeVenta;
	}
}
