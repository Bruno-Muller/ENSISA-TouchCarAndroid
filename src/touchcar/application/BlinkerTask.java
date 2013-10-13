package touchcar.application;

import java.util.TimerTask;

import car.Car;
import car.can.CanMessage;

public class BlinkerTask extends TimerTask {
	
	private Car model;
	private Controller.MyHandler handler;
	
	BlinkerTask(Car model, Controller.MyHandler handler) {
		this.model = model;
		this.handler = handler;
	}

	public void run() {
		boolean repaint = false;
		for (int i=0; i<4; i++) 
			for (int j=0; j<4; j++)
				if (this.model.getLightState(i, j) == CanMessage.LIGHT_BLINKING) {
					this.model.getLight(i, j).toggle();
					repaint = true;
				}
		if (repaint)
			this.handler.sendMessage(this.handler.obtainMessage());
	}

}
