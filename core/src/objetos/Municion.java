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
	public int getDaño() {
		return daño;
	}
	public void setDaño(int daño) {
		this.daño = daño;
	}
	public final boolean instantaneo;
	private int daño;
	/**Caracteristicas generales de la municion
	 * @param daño Daño que hace la bala al impactar
	 * @param instantaneo Este parametro define si hace daño instantaneo o durante un cierto tiempo
	 */
	Municion(int daño, boolean instantaneo){
		this.instantaneo = instantaneo;
	}
	
}
