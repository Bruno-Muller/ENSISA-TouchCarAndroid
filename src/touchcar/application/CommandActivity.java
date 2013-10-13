package touchcar.application;

import car.Car;
import car.light.Light;
import car.opticalblock.OpticalBlock;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class CommandActivity extends Activity implements OnTouchListener {

	private Controller controller;
	private Car car;
	private boolean isRunning;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.command);

		this.findViewById(R.id.mainBeam).setOnTouchListener(this); 
	}

	@Override
	public void onStart() {
		super.onStart();

		// On cherche l'ip et le port dans les préférences
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String ip = prefs.getString("ip", "");
		int port = prefs.getInt("port", 50885);

		this.car = new Car();
		try {
			this.controller = new Controller(ip, port, car, this);
			this.isRunning = true;
		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
			Log.e("TouchCar - CommandActivity", e.getMessage());
			this.isRunning = false;
			this.finish();
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.blinkerLeft:
			this.controller.toggleLeftBlinker();
			break;
		case R.id.blinkerRight:
			this.controller.toggleRightBlinker();
			break;
		case R.id.positionLowBeam:
			this.controller.toggleLowBeamPositionLight();
			break;
		case R.id.warning:
			this.controller.toggleWarning();
			break;
		case R.id.stopLight:
			this.controller.toggleStopLight();
			break;
		case R.id.backLight:
			this.controller.toggleBackLight();
			break;
		}
	}

	public void repaint() {
		Log.d("TouchCar - CommandActivity", "-- repaint --");
		if (this.car.getLight(OpticalBlock.FRONT_LEFT, Light.BLINKER).isOn() || this.car.getLight(OpticalBlock.REAR_LEFT, Light.BLINKER).isOn())
			((ImageButton)this.findViewById(R.id.blinkerLeft)).setImageResource(R.drawable.blinker_left_on);
		else
			((ImageButton)this.findViewById(R.id.blinkerLeft)).setImageResource(R.drawable.blinker_left_off);

		if (this.car.getLight(OpticalBlock.FRONT_RIGHT, Light.BLINKER).isOn() || this.car.getLight(OpticalBlock.REAR_RIGHT, Light.BLINKER).isOn())
			((ImageButton)this.findViewById(R.id.blinkerRight)).setImageResource(R.drawable.blinker_right_on);
		else
			((ImageButton)this.findViewById(R.id.blinkerRight)).setImageResource(R.drawable.blinker_right_off);	

		if (this.car.getLight(OpticalBlock.FRONT_LEFT, Light.MAINBEAM).isOn() || this.car.getLight(OpticalBlock.FRONT_RIGHT, Light.MAINBEAM).isOn())
			((ImageButton)this.findViewById(R.id.mainBeam)).setImageResource(R.drawable.mainbeam_on);	
		else
			((ImageButton)this.findViewById(R.id.mainBeam)).setImageResource(R.drawable.mainbeam_off);

		if ((this.car.getLight(OpticalBlock.FRONT_LEFT, Light.POSITION_LIGHT).isOn() ||
				this.car.getLight(OpticalBlock.FRONT_RIGHT, Light.POSITION_LIGHT).isOn() ||
				this.car.getLight(OpticalBlock.REAR_LEFT, Light.POSITION_LIGHT).isOn() ||
				this.car.getLight(OpticalBlock.REAR_RIGHT, Light.POSITION_LIGHT).isOn()) &&
				(this.car.getLight(OpticalBlock.FRONT_LEFT, Light.LOWBEAM).isOn() ||
						this.car.getLight(OpticalBlock.FRONT_RIGHT, Light.LOWBEAM).isOn()))
			((ImageButton)this.findViewById(R.id.positionLowBeam)).setImageResource(R.drawable.position_on_lowbeam_on);
		else if ((this.car.getLight(OpticalBlock.FRONT_LEFT, Light.POSITION_LIGHT).isOn() ||
				this.car.getLight(OpticalBlock.FRONT_RIGHT, Light.POSITION_LIGHT).isOn() ||
				this.car.getLight(OpticalBlock.REAR_LEFT, Light.POSITION_LIGHT).isOn() ||
				this.car.getLight(OpticalBlock.REAR_RIGHT, Light.POSITION_LIGHT).isOn()) &&
				this.car.getLight(OpticalBlock.FRONT_LEFT, Light.LOWBEAM).isOff() &&
				this.car.getLight(OpticalBlock.FRONT_RIGHT, Light.LOWBEAM).isOff())
			((ImageButton)this.findViewById(R.id.positionLowBeam)).setImageResource(R.drawable.position_on_lowbeam_off);
		else
			((ImageButton)this.findViewById(R.id.positionLowBeam)).setImageResource(R.drawable.position_off_lowbeam_off);

		if ((this.car.getLight(OpticalBlock.FRONT_LEFT, Light.BLINKER).isOn() || this.car.getLight(OpticalBlock.REAR_LEFT, Light.BLINKER).isOn()) && 
				(this.car.getLight(OpticalBlock.FRONT_RIGHT, Light.BLINKER).isOn() || this.car.getLight(OpticalBlock.REAR_RIGHT, Light.BLINKER).isOn()))
			((ImageButton)this.findViewById(R.id.warning)).setImageResource(R.drawable.warning_on);
		else
			((ImageButton)this.findViewById(R.id.warning)).setImageResource(R.drawable.warning_off);

		if (this.car.getLight(OpticalBlock.REAR_LEFT, Light.STOP_LIGHT).isOn() || this.car.getLight(OpticalBlock.REAR_RIGHT, Light.STOP_LIGHT).isOn())
			((ImageButton)this.findViewById(R.id.stopLight)).setImageResource(R.drawable.stoplight_on);
		else
			((ImageButton)this.findViewById(R.id.stopLight)).setImageResource(R.drawable.stoplight_off);

		if (this.car.getLight(OpticalBlock.REAR_LEFT, Light.BACK_LIGHT).isOn() || this.car.getLight(OpticalBlock.REAR_RIGHT, Light.BACK_LIGHT).isOn())
			((ImageButton)this.findViewById(R.id.backLight)).setImageResource(R.drawable.backlight_on);
		else
			((ImageButton)this.findViewById(R.id.backLight)).setImageResource(R.drawable.backlight_off);
	}


	@Override
	public void onStop() {
		super.onStop();

		if (this.isRunning) {
			Log.d("TouchCar - CommandActivity", "-- onStop --");
			try {
				this.controller.closeClient();
			} catch (Exception e) {
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
				Log.e("TouchCar - CommandActivity", e.getMessage());
			}	finally {
				this.finish();
			}
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			this.controller.toggleMainBeam();
			return true;
		case MotionEvent.ACTION_UP:
			if (this.car.getLight(OpticalBlock.FRONT_LEFT, Light.LOWBEAM).isOff() && this.car.getLight(OpticalBlock.FRONT_RIGHT, Light.LOWBEAM).isOff())
				this.controller.toggleMainBeam();
			return true;
		}

		return false;
	}
}
