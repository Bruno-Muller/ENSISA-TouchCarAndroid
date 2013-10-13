package car;

import car.can.CanMessage;
import car.can.CanModul;
import car.light.Light;
import car.opticalblock.*;

public class Car {
	
	private OpticalBlock opticalBlock[];
	private CanModul canModul[];
	
	public Car() {
		
		this.opticalBlock = new OpticalBlock[4];
		this.canModul = new CanModul[4];
		
		this.opticalBlock[OpticalBlock.FRONT_LEFT] = new FrontLeftOpticalBlock();
		this.opticalBlock[OpticalBlock.FRONT_RIGHT] = new FrontRightOpticalBlock();
		this.opticalBlock[OpticalBlock.REAR_LEFT] = new RearLeftOpticalBlock();
		this.opticalBlock[OpticalBlock.REAR_RIGHT] = new RearRightOpticalBlock();

		for (int i=0; i<4; i++) 
			this.canModul[i]= new CanModul(this.opticalBlock[i]);
		
	}

	public int getLightState(int position, int light) {
		return this.canModul[position].getLightState(light);
	}
	
	public Light getLight(int position, int light) {
		return this.opticalBlock[position].getLight(light);
	}
	
	public boolean getError(int position, int mask) {
		return this.canModul[position].getError(mask);
	}
	
	public void acceptCanMessage(CanMessage canMessage) {
		for (int i=0; i<4; i++) {
			this.canModul[i].acceptCanMessage(canMessage);
		}
	}
	
}
