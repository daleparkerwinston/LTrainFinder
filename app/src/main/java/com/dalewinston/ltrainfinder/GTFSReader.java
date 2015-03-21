package com.dalewinston.ltrainfinder;

/**
 * Created by dale on 3/20/15.
 */
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.transit.realtime.GtfsRealtime.FeedEntity;
import com.google.transit.realtime.GtfsRealtime.FeedMessage;

public class GTFSReader {

    private URL url;
    private String stop;

    public GTFSReader(String theURL, String stop) throws Exception {

        URL url = new URL(theURL);
        this.stop = stop;
        System.out.println("!!!!Hello )))23jijriw40");

    }

    public ArrayList<String> getMinutesToNextTrains()throws Exception{

        ArrayList<String> arrivalTimesString = new ArrayList<String>();
        URL url2 = new URL("http://datamine.mta.info/mta_esi.php?key=1c90c74dbc055d891ad78889c285cec6&feed_id=2");
        FeedMessage feed = FeedMessage.parseFrom(url2.openStream());
        System.out.println(feed.getEntityCount());
        System.out.println("!!!!Hello )))23jijriw40");
        for(FeedEntity entity : feed.getEntityList()) {
            if(entity.hasTripUpdate()) {
                System.out.println("!!!!Hello )))23jijriw40");
                List list = entity.getTripUpdate().getStopTimeUpdateList();
                for(int i = 0; i < list.size(); i++) {
                    System.out.println("!!!!Hello )))23jijriw40");
                    String stopID = entity.getTripUpdate().getStopTimeUpdate(i).getStopId();
                    if(stopID.contains(stop)) {
                        long timeStamp = entity.getTripUpdate().getStopTimeUpdate(i).getDeparture().getTime();
                        Date arrivalTime = new Date(timeStamp*1000);
                        System.out.println("!!!!Hello )))23jijriw40");
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
