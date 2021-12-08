package cm.intelso.dev.travelezi.dto;

import java.util.List;

public class Bus {

    private String uuid;
    private String numChassis;
    private String immatriculation;
    private String mark;
    private String model;
    private List<Planning> plannings;
    private String latitude;
    private String longitude;
    /**
     * Distance en metre (m)
     */
    private Long distance;
    /**
     * Temps en seconde (s)
     */
    private Long time;
    private String distanceText;
    private String timeText;

    public Bus() {
    }

    public Bus(String uuid, String numChassis, String immatriculation, String latitude, String longitude) {
        this.uuid = uuid;
        this.numChassis = numChassis;
        this.immatriculation = immatriculation;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Bus(String uuid, String numChassis, String immatriculation, String mark, String model, List<Planning> plannings, String latitude, String longitude) {
        this.uuid = uuid;
        this.numChassis = numChassis;
        this.immatriculation = immatriculation;
        this.mark = mark;
        this.model = model;
        this.plannings = plannings;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNumChassis() {
        return numChassis;
    }

    public void setNumChassis(String numChassis) {
        this.numChassis = numChassis;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Planning> getPlannings() {
        return plannings;
    }

    public void setPlannings(List<Planning> plannings) {
        this.plannings = plannings;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getDistanceText() {
        return distanceText;
    }

    public void setDistanceText(String distanceText) {
        this.distanceText = distanceText;
    }

    public String getTimeText() {
        return timeText;
    }

    public void setTimeText(String timeText) {
        this.timeText = timeText;
    }

    @Override
    public String toString() {
        return immatriculation;
    }
}
