package com.dalewinston.ltrainfinder;

/**
 * Created by Dale Winston on 3/20/15.
        */
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.transit.realtime.GtfsRealtime.FeedEntity;
import com.google.transit.realtime.GtfsRealtime.FeedMessage;

public class GTFSReader {

    private String stop;

    public GTFSReader(String theURL, String stop) throws Exception {

        this.stop = stop;

    }

    public ArrayList<String> getMinutesToNextTrains()throws Exception{

        ArrayList<String> arrivalTimesString = new ArrayList<>();

        URL url2 = new URL("http://datamine.mta.info/mta_esi.php?key=1c90c74dbc055d891ad78889c285cec6&feed_id=2");

        FeedMessage feed = FeedMessage.parseFrom(url2.openStream());
        System.out.println(feed.getEntityCount());
        for(FeedEntity entity : feed.getEntityList()) {
            if(entity.hasTripUpdate()) {
                List list = entity.getTripUpdate().getStopTimeUpdateList();
                for(int i = 0; i < list.size(); i++) {
                    String stopID = entity.getTripUpdate().getStopTimeUpdate(i).getStopId();
                    if(stopID.contains(stop)) {
                        long timeStamp = entity.getTripUpdate().getStopTimeUpdate(i).getDeparture().getTime();
                        Date arrivalTime = new Date(timeStamp*1000);
                        Date now = new Date();
                        now.setTime(System.currentTimeMillis());
                        long duration = arrivalTime.getTime() - now.getTime();
                        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
                        String minutesTillNextTrain = "" + diffInMinutes;
                        arrivalTimesString.add(minutesTillNextTrain);
                    }
                }

            }
        }
        return arrivalTimesString;
    }
}
