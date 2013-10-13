package car.opticalblock;

import car.light.Light;
import car.light.PositionLight;
import car.light.TurnSignal;

public abstract class OpticalBlock {
	
	public static final int FRONT_LEFT = 0;
	public static final int FRONT_RIGHT = 1;
	public static final int REAR_LEFT = 2;
	public static final int REAR_RIGHT = 3;
			
	private int position;
	protected Light[] lights;
	
	OpticalBlock(int position) {
		this.position = position;
		this.lights = new Light[4];
		this.lights[Light.POSITION_LIGHT] = new PositionLight();
		this.lights[Light.BLINKER] = new TurnSignal();
	}
	
	public int getPosition() {
		return this.position;
	}
	
	public Light getLight(int index) {
		return lights[index];
	}
}
