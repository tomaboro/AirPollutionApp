package pl.hackyeah.positivedevs.airpollutionapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pl.hackyeah.positivedevs.airpollutionapp.DataReader.Station;
import pl.hackyeah.positivedevs.airpollutionapp.DataReader.StationDetails;

/**
 * Created by motek on 27.10.17.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.StationViewHolder> {

    public static class StationViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView stationName;
        TextView stationAir;
        ImageView stationPhoto;

        public StationViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            stationName = (TextView) itemView.findViewById(R.id.station_name);
            stationAir = (TextView) itemView.findViewById(R.id.station_air);
            stationPhoto = (ImageView) itemView.findViewById(R.id.station_photo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: START EDIT ACTIVITY
                    Toast toast = Toast.makeText(v.getContext(), stationName.getText().toString(), Toast.LENGTH_LONG);
                    toast.show();
                }
            });
        }
    }

    List<StationDetails> stations;

    RVAdapter(List<StationDetails> stations) {
        this.stations = stations;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public StationViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_items, viewGroup, false);
        StationViewHolder pvh = new StationViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(StationViewHolder stationViewHolder, int i) {
        stationViewHolder.stationName.setText(stations.get(i).name);
        stationViewHolder.stationAir.setText("Air situation: " + stations.get(i).airSituation.toString());
        //stationViewHolder.stationPhoto.setImageResource(stations.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return stations.size();
    }
}
