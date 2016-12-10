package com.ist_311.slidingpuzzle.utilities;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ist_311.slidingpuzzle.models.LeaderboardEntry;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.ist_311.slidingpuzzle.activities.MainActivity.sessionManager;

public class LeaderboardFunctions {

    /**
     * Updates the MySQL leaderboards.
     */
    public void updateLeaderboards(final LeaderboardEntry leaderboardEntry) {
        String requestString = "update_leaderboards";
        StringRequest strReq = new StringRequest(Request.Method.POST, Config.URL_UPDATE_LEADERBOARDS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    // Retrieve JSON error object
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");

                    // Check for error node in json
                    if (!error){
                        // Open next level, rather than making another HTTP request
                        if (sessionManager.getUnlocked() < 20) {
                            sessionManager.setUnlocked(sessionManager.getUnlocked() + 1);
                        }
                    }else{
                        // Error fetching data. Get the error message
                        String errorMsg = jsonObject.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                }
                // JSON error
                catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("email", leaderboardEntry.getEmail());
                params.put("level_num", String.valueOf(leaderboardEntry.getLevel_num()));
                params.put("score", String.valueOf(leaderboardEntry.getScore()));
                params.put("moves", String.valueOf(leaderboardEntry.getMoves()));
                params.put("time", leaderboardEntry.getTime());
                return params;
            }
        };

        // Adding request to request queue
        VolleyController.getVolleyController().addToRequestQueue(strReq, requestString);
    }
}