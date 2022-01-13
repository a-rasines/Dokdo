package objetos;
/**
 * Representa los tipos de {@link ojbetos.Bala Bala} que disparan los {@linkobjetos.Cañon Cañon}es.
 *
 */
public enum Municion {
	NORMAL(1, true,0,2,2),
	EXPLOSIVA(2, true,0,2,2),
	INCENDIARIA(2, false,3,1,2),
	VENENOSA(2, false,3,2,2),
	ENCADENADA(2, false,3,2,2);
	
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
	private int coolDownCont;
	Municion(int danyo, boolean instantaneo, int veces, int coolDown, int coolDDanoCont){
		this.instantaneo = instantaneo;
		this.danyo=danyo;
		this.veces=veces;
		this.coolDown=coolDown;
		this.coolDownCont=coolDDanoCont;
	}
	public int getCoolDownCont() {
		return coolDownCont;
	}
	public void setCoolDownCont(int coolDownCont) {
		this.coolDownCont = coolDownCont;
	}
	
}
