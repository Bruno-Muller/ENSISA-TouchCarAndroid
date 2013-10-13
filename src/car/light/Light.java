package car.light;

public abstract class Light {
		
	public static final int MAINBEAM = 0;
	public static final int STOP_LIGHT = 0;
	public static final int LOWBEAM = 1;
	public static final int BACK_LIGHT = 1;
	public static int POSITION_LIGHT = 2;
	public static int BLINKER = 3;
	
	private boolean isOn;
	private int kind;
	
	Light(int kind) {
		this.isOn = false;
		this.kind = kind;
	}
	
	public void setOn() {
		this.isOn = true;
	}
	
	public void setOff() {
		this.isOn = false;
	}
	
	public boolean isOn() {
		return this.isOn;
	}
	
	public boolean isOff() {
		return !this.isOn;
	}
	
	public void toggle() {
		this.isOn = !this.isOn;
	}
	
	public int getKind() {
		return this.kind;
	}
	
}
