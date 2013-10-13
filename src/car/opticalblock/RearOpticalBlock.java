package car.opticalblock;

import car.light.BackLight;
import car.light.Light;
import car.light.StopLight;

public abstract class RearOpticalBlock extends OpticalBlock {

	RearOpticalBlock(int position) {
		super(position);
		super.lights[Light.STOP_LIGHT] = new StopLight();
		super.lights[Light.BACK_LIGHT] = new BackLight();
	}

}
