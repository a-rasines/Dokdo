package objetos;
/**
 * Representa los tipos de {@link ojbetos.Bala Bala} que disparan los {@linkobjetos.Cañon Cañon}es.
 *
 */
public enum Municion {
	NORMAL(1, true,0,0),
	EXPLOSIVA(2, true,0,3),
	INCENDIARIA(2, false,3,1),
	VENENOSA(2, false,3,3),
	ENCADENADA(2, false,3,3);
	
	public int getCoolDown() {
		return coolDown;
	}
	public void setCoolDown(int coolDown) {
		this.coolDown = coolDown;
	}
	public int getVeces() {
		return veces;
	}
	public int getDanyo() {
		return danyo;
	}
	public void setDanyo(int danyo) {
		this.danyo = danyo;
	}
	public boolean getInstantaneo() {
		if(this.instantaneo) return false;
		else return true;
	}
	public final boolean instantaneo;
	private int danyo;
	private int veces;
	private int coolDown;
	Municion(int danyo, boolean instantaneo, int veces, int coolDown){
		this.instantaneo = instantaneo;
		this.danyo=danyo;
		this.veces=veces;
		this.coolDown=coolDown;
	}
	
}
