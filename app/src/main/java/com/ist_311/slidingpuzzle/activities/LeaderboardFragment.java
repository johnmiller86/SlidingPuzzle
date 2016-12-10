package com.ist_311.slidingpuzzle.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ist_311.slidingpuzzle.R;
import com.ist_311.slidingpuzzle.models.ListViewAdapter;
import com.ist_311.slidingpuzzle.utilities.Config;
import com.ist_311.slidingpuzzle.utilities.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.ist_311.slidingpuzzle.models.ListViewAdapter.EMAIL;
import static com.ist_311.slidingpuzzle.models.ListViewAdapter.LEVEL;
import static com.ist_311.slidingpuzzle.models.ListViewAdapter.MOVES;
import static com.ist_311.slidingpuzzle.models.ListViewAdapter.SCORE;
import static com.ist_311.slidingpuzzle.models.ListViewAdapter.TIME;


@SuppressWarnings("EmptyMethod")
public class LeaderboardFragment extends Fragment {

    // Leaderboard list and view.
    private ArrayList<HashMap<String, String>> list;
    private ListView listView;

    public LeaderboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        listView = (ListView) view.findViewById(R.id.listView1);
        list = new ArrayList<>();
        return getLeaderboards(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Gets the user's settings from MySQL.
     */
    private View getLeaderboards(View view, final Bundle savedInstanceState) {
        String requestString = "get_leaderboards";
        StringRequest strReq = new StringRequest(Request.Method.POST, Config.URL_GET_LEADERBOARDS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    // Retrieve JSON response object
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");

                    // Retrieve JSON response array
                    JSONArray jsonArray = jsonObject.getJSONArray("response");

                    // Check for error node in json
                    if (!error) {
                        for (int i = 0; i < jsonArray.length(); i++) {

                            // Retrieve inner JSON objects
                            JSONObject row = jsonArray.getJSONObject(i);

                            // Configure entry and add to list
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put(LEVEL, String.valueOf(row.getInt("level_num")));
                            hashMap.put(EMAIL, row.getString("email"));
                            hashMap.put(SCORE, String.valueOf(row.getInt("score")));
                            hashMap.put(MOVES, String.valueOf(row.getInt("moves")));
                            hashMap.put(TIME, row.getString("time"));
                            list.add(hashMap);
                        }
                        ListViewAdapter adapter = new ListViewAdapter(getLayoutInflater(savedInstanceState), list);
                        listView.setAdapter(adapter);
                    } else {
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
                // No params for this method, return empty HashMap
                return new HashMap<>();
            }
        };

        // Adding request to request queue
        VolleyController.getVolleyController().addToRequestQueue(strReq, requestString);
        return view;
    }
}
