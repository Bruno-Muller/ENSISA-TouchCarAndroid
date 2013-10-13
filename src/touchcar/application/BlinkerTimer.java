package touchcar.application;

import java.util.Timer;
import car.Car;

public class BlinkerTimer {
	private Car model;
	private Controller.MyHandler handler;
	private Timer timer;
	
	
	BlinkerTimer(Car model, Controller.MyHandler handler) {
		this.model = model;
		this.handler = handler;
	}
	
	public void start() {
		if (this.timer != null)
			this.timer.cancel();
		this.timer = new Timer();
		this.timer.schedule(new BlinkerTask(this.model, this.handler), 500, 500);

	}
		
	public void stop() {
		if (this.timer != null)
			this.timer.cancel();
		this.timer = null;
	}
	
}
