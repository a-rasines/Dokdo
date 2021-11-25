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
	public int getDanyo() {
		return danyo;
	}
	public void setDanyo(int danyo) {
		this.danyo = danyo;
	}
	public final boolean instantaneo;
	private int danyo;
	Municion(int danyo, boolean instantaneo){
		this.instantaneo = instantaneo;
		this.danyo=danyo;
	}
	
}
