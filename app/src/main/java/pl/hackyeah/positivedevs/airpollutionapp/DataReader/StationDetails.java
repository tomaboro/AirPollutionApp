package pl.hackyeah.positivedevs.airpollutionapp.DataReader;


/**
 * Created by motek on 27.10.17.
 */

public class StationDetails extends Station {
    public StationDetails(String name, Integer id, String airSituation) {
        super(name, id);
        this.airSituation = airSituation;
    }

    public String airSituation;
    public int photoId;

}
