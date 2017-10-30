package pl.hackyeah.positivedevs.airpollutionapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pl.hackyeah.positivedevs.airpollutionapp.DataReader.Station;

/**
 * Created by motek on 27.10.17.
 */

public class LoadAllStations extends Thread {

    public LoadAllStations(MainActivity mContext) {
        progress = new ProgressDialog(mContext);
        this.mContext = mContext;
    }

    public void showDialog() {
        progress.setMessage("Downloading Data");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

    ProgressDialog progress;
    MainActivity mContext;
    Boolean done = false;
    Boolean error = false;
    Boolean requestSent = false;
    String result = "";

    ArrayList<Station> stations = new ArrayList<Station>();

    public void setPositive(String message) {
        done = true;
        result = message;
    }

    public void setNegative() {
        error = true;
    }

    @Override
    public void run() {

        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = "http://api.gios.gov.pl/pjp-api/rest/station/findAll";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("APA", response);
                        setPositive(response);
                        progress.setProgress(100);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("APA", "ERROR");
                setNegative();
            }
        });

        while (!error && !done) {
            if (!requestSent) {
                queue.add(stringRequest);
                requestSent = true;
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        Log.i("LIST", "STAT");
        try {
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String name = (String) obj.get("stationName");
                Integer id = (Integer) obj.get("id");

                Station station = new Station(name, id);
                Log.i("LIST", name);
                stations.add(station);
            }
        } catch (JSONException e) {
            Log.e("APA", "ERROR");
            e.printStackTrace();
        }

        progress.dismiss();
        mContext.showList(this);
    }
}

