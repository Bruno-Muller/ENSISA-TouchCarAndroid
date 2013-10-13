package touchcar.application;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SplashScreenActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.splash_screen);
        
    	Log.d("TouchCar - SplashScreenActivity" , " -- Timer --");
		new Timer().schedule(new Starter(this), 3000); //Un timer qui s'executera en one shot après 3000 ms
		
    }
    
	private class Starter extends TimerTask {

		private Context context;

		Starter(Context context) {
			this.context = context;
		}

		@Override
		public void run() {
			Intent intent = new Intent(this.context, ConnexionActivity.class);
			this.context.startActivity(intent);
			((Activity) this.context).finish();
		}

	}
}