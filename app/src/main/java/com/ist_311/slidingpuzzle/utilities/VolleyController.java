package com.ist_311.slidingpuzzle.utilities;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyController extends Application {

    // Tag
    private static final String TAG = VolleyController.class.getSimpleName();

    // The RequestQueue and self volleyController
    private RequestQueue requestQueue;
    private static VolleyController volleyController;

    @Override
    public void onCreate() {
        super.onCreate();
        volleyController = this;
    }

    /**
     * Returns the volleyController of the VolleyController.
     * @return the volleyController.
     */
    public static synchronized VolleyController getVolleyController() {
        return volleyController;
    }

    /**
     * Gets the RequestQueue.
     * @return the RequestQueue.
     */
    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * Adds an HTTP request to the RequestQueue.
     * @param req the String queue.
     * @param tag the http request tag.
     * @param <T> the template.
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
}
