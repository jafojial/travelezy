package cm.intelso.dev.travelezi.dto;

public class LineItem {

    private String code;
    private String departure;
    private String arrival;
    private String stops;

    public LineItem() {
    }

    public LineItem(String code, String departure, String arrival, String stops) {
        this.code = code;
        this.departure = departure;
        this.arrival = arrival;
        this.stops = stops;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getStops() {
        return stops;
    }

    public void setStops(String stops) {
        this.stops = stops;
    }

    /*@Override
    public String toString() {
        return "LineItem{" +
                "code='" + code + '\'' +
                ", departure='" + departure + '\'' +
                ", arrival='" + arrival + '\'' +
                ", stops='" + stops + '\'' +
                '}';
    }*/

    @Override
    public String toString() {
        return code + " - " + departure;
    }
}
