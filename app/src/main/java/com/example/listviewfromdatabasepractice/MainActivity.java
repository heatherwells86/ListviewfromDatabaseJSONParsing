package com.example.listviewfromdatabasepractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewResult;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonParse = findViewById(R.id.button_parse);

        mQueue = Volley.newRequestQueue(this);

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();

            }
        });
    }

    private void jsonParse() {

        String url = "https://api.myjson.com/bins/7npmw";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) { //called when object response is successful
                        try {
                            JSONArray jsonArray = response.getJSONArray("fruits");

                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject fruits =jsonArray.getJSONObject(i);

                                String fruitName = fruits.getString("Fruit Name");
                                String price = fruits.getString("Price");

                                mTextViewResult.append(fruitName + ", " + price + "\n");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //called when the object response contains an error
                error.printStackTrace(); //explains the reason that there is an error
            }
        });

        mQueue.add(request);
    }
}
