package car.opticalblock;

import car.light.Light;
import car.light.LowBeam;
import car.light.MainBeam;

public abstract class FrontOpticalBlock extends OpticalBlock {
		
	FrontOpticalBlock(int position) {		
		super(position);
		super.lights[Light.MAINBEAM] = new MainBeam();
		super.lights[Light.LOWBEAM] = new LowBeam();
	}

}
