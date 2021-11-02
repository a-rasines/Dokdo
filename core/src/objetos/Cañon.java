package objetos;

public class Cañon extends Mejoras{
	protected float cooldownDelCañon;
	public Cañon(int nivel, int precio, float cooldown) {
		super(nivel, precio);
		this.cooldownDelCañon=cooldown;
	}
	public float getCooldownDelCañon() {
		return cooldownDelCañon;
	}
	public void setCooldownDelCañon(float cooldownDelCañon) {
		this.cooldownDelCañon = cooldownDelCañon;
	}

}
