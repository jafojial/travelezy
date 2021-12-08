package cm.intelso.dev.travelezi.dto;

public class WaitingDetail {

    /**
     * Distance en metre (m)
     */
    private Long distance;
    /**
     * Temps en seconde (s)
     */
    private Long time;
    private String distance_text;
    private String time_text;

    public WaitingDetail() {
    }

    public WaitingDetail(Long distance, Long time, String distance_text, String time_text) {
        this.distance = distance;
        this.time = time;
        this.distance_text = distance_text;
        this.time_text = time_text;
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

    public String getDistance_text() {
        return distance_text;
    }

    public void setDistance_text(String distance_text) {
        this.distance_text = distance_text;
    }

    public String getTime_text() {
        return time_text;
    }

    public void setTime_text(String time_text) {
        this.time_text = time_text;
    }

    @Override
    public String toString() {
        return "WaitingDetail{" +
                "distance=" + distance +
                ", time=" + time +
                ", distance_text='" + distance_text + '\'' +
                ", time_text='" + time_text + '\'' +
                '}';
    }
}
