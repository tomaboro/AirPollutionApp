package pl.hackyeah.positivedevs.airpollutionapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

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
import java.util.LinkedList;

import pl.hackyeah.positivedevs.airpollutionapp.DataReader.Station;
import pl.hackyeah.positivedevs.airpollutionapp.DataReader.StationDetails;
import pl.hackyeah.positivedevs.airpollutionapp.DataReader.giosInfo;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    ProgressDialog progress;
    private ArrayList<StationDetails> stations;

    public void floatingButtonOnClick(View v) {
        //LoadAllStations t = new LoadAllStations(this);
        //t.showDialog();
        //runOnUiThread(new LoadAllStations(this));
    }

    public void showList(LoadAllStations load) {
        ArrayList<String> ttmp = new ArrayList<>();
        for (int i = 0; i < load.stations.size(); i++) {
            ttmp.add(load.stations.get(i).name);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose station")
                .setItems(ttmp.toArray(new String[ttmp.size()]), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                    }
                });
        builder.create().show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_card);

        rv = (RecyclerView) findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();

    }

    private void initializeData() {
        stations = new ArrayList<>();
        stations.add(new StationDetails("Nowy Targ", 1, "Dobra"));
        stations.add(new StationDetails("Kraków", 112, "Świetna"));
        stations.add(new StationDetails("Zakopane", 3, "Słaba"));
    }

    private void initializeAdapter() {
        RVAdapter adapter = new RVAdapter(stations);
        rv.setAdapter(adapter);
    }

}
