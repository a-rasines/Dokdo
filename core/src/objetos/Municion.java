package objetos;
/**
 * Representa los tipos de {@link ojbetos.Bala Bala} que disparan los {@linkobjetos.Cañon Cañon}es.
 *
 */
public enum Municion {
	NORMAL(1, true),
	EXPLOSIVA(2, true),
	INCENDIARIA(2, false),
	VENENOSA(1, false),
	ENCADENADA(1, false);
	public int getDa�o() {
		return da�o;
	}
	public void setDa�o(int da�o) {
		this.da�o = da�o;
	}
	public final boolean instantaneo;
	private int da�o;
	Municion(int da�o, boolean instantaneo){
		this.instantaneo = instantaneo;
	}
	
}
