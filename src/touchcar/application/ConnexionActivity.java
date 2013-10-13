package touchcar.application;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ConnexionActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.connexion);
        
        // On cherche l'ip et le port dans les préférences
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String ip = prefs.getString("ip", "");
		int port = prefs.getInt("port", 50885);
		
		Log.d("TouchCar - ConnexionActivity", "Prefs - IP:" + ip  + " PORT:" + port );
		
		if (!ip.equals("")) {
			((EditText)this.findViewById(R.id.serverIpAddress)).setText(ip);
			((EditText)this.findViewById(R.id.serverPort)).setText(String.valueOf(port));
		}

    }
    
    public void onClick(View v) {
    	String ip = ((EditText)this.findViewById(R.id.serverIpAddress)).getText().toString();
		int port = Integer.parseInt(((EditText)this.findViewById(R.id.serverPort)).getText().toString());
		
		Log.d("TouchCar - ConnexionActivity", "IP:" + ip  + " PORT:" + port );
    	
    	// On enregistre l'ip et le port dans les préférences
    	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("ip", ip);
		editor.putInt("port", port);
		editor.commit();
		
		// On ouvre une nouvelle Activity 
		Intent monIntent = new Intent(this, CommandActivity.class);
		this.startActivity(monIntent);
		
    }
}
