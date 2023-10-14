package com.example.countrycodewithflag;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends Activity {
    TextView Cname, Ccode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Cname = findViewById(R.id.Cname);
        Ccode = findViewById(R.id.Ccode);
        try {
            String simData = ((TelephonyManager) MainActivity.this.getSystemService(Context.TELEPHONY_SERVICE)).getSimCountryIso().toUpperCase();
            JSONObject jsonObject = new JSONObject(loadJSOnFromAssets());
            JSONArray userArray = jsonObject.getJSONArray("mydata");
            for (int i = 0; i < userArray.length(); i++) {
                JSONObject countryDetails = userArray.getJSONObject(i);
                if (simData.equals(countryDetails.getString("code"))) {
                    Cname.setText(countryDetails.getString("name"));
                    Ccode.setText((countryDetails.getString("emoji")).concat(" ").concat(countryDetails.getString("dial_code")));
                }
            }
        } catch (Exception e) {
            Log.i("Found Error :", e.toString());
        }
    }

    private String loadJSOnFromAssets() {
        String json;
        try {
            InputStream is = getAssets().open("countryList.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (Exception e) {
            Log.i("Found Error :", e.toString());
            return null;
        }
        return json;
    }
}
