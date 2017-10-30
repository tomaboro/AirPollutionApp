package pl.hackyeah.positivedevs.airpollutionapp;

import android.app.ProgressDialog;
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

public class LoadAllStationsDetails extends Thread {

    public LoadAllStationsDetails(MainActivity mContext, ArrayList<Station> stations) {
        progress = new ProgressDialog(mContext);
        this.mContext = mContext;
        this.stations = stations;
    }

    public void showDialog() {
        progress.setMessage("Downloading Data");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
    }

    ArrayList<StationDetails> detailStation;
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
        for (int i = 0; i < stations.size(); i++) {
            LoadStationDetails tmp = new LoadStationDetails(mContext, stations.get(i));
            detailStation.add(tmp.run());
        }
        progress.dismiss();
    }

}
