package cm.intelso.dev.travelezi.dto;

public class RideItem {

    private String code;
    private String departure;
    private String arrival;
    private String date;
    private String departureHour;
    private String arrivalHour;
    private String stops;

    public RideItem() {
    }

    public RideItem(String code, String departure, String arrival, String stops, String date, String departureHour, String arrivalHour) {
        this.code = code;
        this.date = date;
        this.departure = departure;
        this.arrival = arrival;
        this.stops = stops;
        this.departureHour = departureHour;
        this.arrivalHour = arrivalHour;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDepartureHour() {
        return departureHour;
    }

    public void setDepartureHour(String departureHour) {
        this.departureHour = departureHour;
    }

    public String getArrivalHour() {
        return arrivalHour;
    }

    public void setArrivalHour(String arrivalHour) {
        this.arrivalHour = arrivalHour;
    }

    /*@Override
    public String toString() {
        return "RideItem{" +
                "code='" + code + '\'' +
                ", departure='" + departure + '\'' +
                ", arrival='" + arrival + '\'' +
                ", date='" + date + '\'' +
                ", departureHour='" + departureHour + '\'' +
                ", arrivalHour='" + arrivalHour + '\'' +
                ", stops='" + stops + '\'' +
                '}';
    }*/

    @Override
    public String toString() {
        return code + " - " + departure;
    }
}
