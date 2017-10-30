package pl.hackyeah.positivedevs.airpollutionapp;

import android.content.Context;
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
import pl.hackyeah.positivedevs.airpollutionapp.DataReader.StationDetails;

/**
 * Created by motek on 27.10.17.
 */

public class LoadStationDetails {
    public LoadStationDetails(MainActivity mContext, Station station) {
        this.mContext = mContext;
        this.station = station;
    }

    Context mContext;
    Boolean done = false;
    Boolean error = false;
    Boolean requestSent = false;
    String result = "";
    Station station;

    ArrayList<Station> stations = new ArrayList<Station>();

    public void setPositive(String message) {
        done = true;
        result = message;
    }

    public void setNegative() {
        error = true;
    }

    public StationDetails run() {
        StationDetails stationReturn = new StationDetails("ERROR", -10, "COULDNT LOAD");

        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = "http://api.gios.gov.pl/pjp-api/rest/aqindex/getIndex/" + station.id.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        setPositive(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setNegative();
            }
        });

        while (!error && !done) {
            if (!requestSent) {
                queue.add(stringRequest);
                requestSent = true;
            }
        }


        try {
            JSONObject obj = new JSONObject(result);
            result = (String) obj.getJSONObject("stIndexLevel").get("indexLevelName");
            stationReturn = new StationDetails(station.name, station.id, result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stationReturn;
    }
}
