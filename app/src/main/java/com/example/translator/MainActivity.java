package com.example.translator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String YODA_URL = "https://api.funtranslations.com/translate/yoda.json?text=";
    public static final String PIRATE_URL = "https://api.funtranslations.com/translate/pirate.json?text=";
    public static final String VALSPEAK_URL = "https://api.funtranslations.com/translate/valspeak.json?text=";
    public static final String MINION_URL = "https://api.funtranslations.com/translate/minion.json?text=";
    public static final String PIG_URL = "https://api.funtranslations.com/translate/pig-latin.json?text=";
    public static final String SHAKESPEARE_URL = "https://api.funtranslations.com/translate/shakespeare.json?text=";

    public static final String TAG = "MainActivity";

    TextView tvTitle;
    EditText etCompose;
    Button btSubmit;
    TextView tvTranslation;
    TextView tvLanguage;
    AsyncHttpClient client;
    Spinner dropdown;
    String currURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = findViewById(R.id.tvTitle);
        etCompose = findViewById(R.id.etCompose);
        btSubmit = findViewById(R.id.btSubmit);
        tvLanguage = findViewById(R.id.tvLanguage);
        tvTranslation = findViewById(R.id.tvTranslation);
        dropdown = findViewById(R.id.dropdown);

        //create a list of items for the spinner.
        String[] items = new String[]{"Yoda", "Pirate", "Valspeak", "Minion", "Pig Latin", "Shakespeare"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        currURL = YODA_URL;

        client = new AsyncHttpClient();

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch (position) {
                    case 0:
                        // Whatever you want to happen when the first item gets selected
                        currURL = YODA_URL;
                        //tvTranslation.setText("Yoda");
                        break;
                    case 1:
                        // Whatever you want to happen when the second item gets selected
                        currURL = PIRATE_URL;
                        //tvTranslation.setText("Pirate");
                        break;
                    case 2:
                        // Whatever you want to happen when the second item gets selected
                        currURL = VALSPEAK_URL;
                        //tvTranslation.setText("Valspeak");
                        break;
                    case 3:
                        // Whatever you want to happen when the second item gets selected
                        currURL = MINION_URL;
                        //tvTranslation.setText("Minion");
                        break;
                    case 4:
                        // Whatever you want to happen when the second item gets selected
                        currURL = PIG_URL;
                        //tvTranslation.setText("Pig Latin");
                        break;
                    case 5:
                        // Whatever you want to happen when the second item gets selected
                        currURL = SHAKESPEARE_URL;
                        //tvTranslation.setText("Shakespeare");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                currURL = YODA_URL;
            }
        });

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = currURL + etCompose.getText().toString();
                if (content.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Sorry, your input cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }


                client.get(content, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        JSONObject jsonObject = json.jsonObject;

                        try {
                            JSONObject obj = jsonObject.getJSONObject("contents");
                            String translated = obj.getString("translated");
                            //Toast.makeText(MainActivity.this, translated, Toast.LENGTH_LONG).show();
                            tvTranslation.setText(translated);
                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, "Obtaining JSON Failed", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Toast.makeText(MainActivity.this, "API Request Error. Status Code: " + statusCode, Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }


}
