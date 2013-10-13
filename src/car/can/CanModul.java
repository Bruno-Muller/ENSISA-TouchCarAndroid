package car.can;

import car.opticalblock.OpticalBlock;

public class CanModul {
			
	private static final int MASK[] = {CanMessage.FRONT_LEFT, CanMessage.FRONT_RIGHT, CanMessage.REAR_LEFT, CanMessage.REAR_RIGHT};
	
	private CanMessage state;
	private OpticalBlock opticalBLock;

	public CanModul(OpticalBlock opticalBLock) {
		this.opticalBLock = opticalBLock;
		this.state = new CanMessage(MASK[opticalBLock.getPosition()], CanMessage.ALL_OFF, 0);
	}

	public OpticalBlock getOpticalBlock() {
		return this.opticalBLock;
	}

	public void acceptCanMessage(CanMessage canMessage) {
		if (isTarget(canMessage)) {
			for (int i=0; i<4; i++) {
				switch (canMessage.getLightState(i)) {
					case CanMessage.LIGHT_OFF:
						this.state.setLightState(i, CanMessage.LIGHT_OFF);
						this.opticalBLock.getLight(i).setOff();
						break;
					case CanMessage.LIGHT_ON:
						this.state.setLightState(i, CanMessage.LIGHT_ON);
						this.opticalBLock.getLight(i).setOn();
						break;
					case CanMessage.LIGHT_BLINKING:
						this.state.setLightState(i, CanMessage.LIGHT_BLINKING);
						this.opticalBLock.getLight(i).setOn();
						break;
				}
			}
			if (this.state.getDataHigh() != (canMessage.getDataHigh() & 0xFC))
				this.state.setDataHigh(canMessage.getDataHigh() & 0xFC);
		}
	}
	
	public int getLightState(int light) {
		return this.state.getLightState(light);
	}
	
	private boolean isTarget(CanMessage canMessage) {
		if ((canMessage.getId() & this.state.getId()) == this.state.getId())
			return true;
		return false;
	}

	public boolean getError(int mask) {
		return this.state.getError(mask);
	}
}
