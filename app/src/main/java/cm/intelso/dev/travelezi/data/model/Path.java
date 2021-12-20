package cm.intelso.dev.travelezi.data.model;

import java.util.List;

public class Path {

    String uuid;
    String departure;
    String destination;
    int distance;
    String distanceText;
    int duration;
    String durationText;
    List<Start> starts;

    public Path() {
    }

    public Path(String uuid, String departure, String destination, int distance, String distanceText, int duration, String durationText, List<Start> starts) {
        this.uuid = uuid;
        this.departure = departure;
        this.destination = destination;
        this.distance = distance;
        this.distanceText = distanceText;
        this.duration = duration;
        this.durationText = durationText;
        this.starts = starts;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getDistanceText() {
        return distanceText;
    }

    public void setDistanceText(String distanceText) {
        this.distanceText = distanceText;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDurationText() {
        return durationText;
    }

    public void setDurationText(String durationText) {
        this.durationText = durationText;
    }

    public List<Start> getStarts() {
        return starts;
    }

    public void setStarts(List<Start> starts) {
        this.starts = starts;
    }

    public String toString2() {
        return "Path{" +
                "uuid='" + uuid + '\'' +
                ", departure='" + departure + '\'' +
                ", destination='" + destination + '\'' +
                ", distance=" + distance +
                ", distanceText='" + distanceText + '\'' +
                ", duration=" + duration +
                ", durationText='" + durationText + '\'' +
                ", starts=" + starts +
                '}';
    }

    @Override
    public String toString() {
        return departure + " --> " + destination;
    }
}
