package cm.intelso.dev.travelezi.dto;

import java.util.Date;

public class PathItem {

    private String code;
    private Date date;
    private Date startHour;
    private Date endHour;
    private String status;
    private String driver;
    private String bus;
    private String busImmat;
    private String lineCode;
    private String lineNumber;
    private String lineName;

    public PathItem() {
    }

    public PathItem(String code, String status, String driver, String bus, Date date, Date startHour, Date endHour) {
        this.code = code;
        this.date = date;
        this.status = status;
        this.driver = driver;
        this.bus = bus;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public PathItem(String code, Date date, Date startHour, Date endHour, String status, String driver, String bus, String busImmat, String lineCode, String lineNumber, String lineName) {
        this.code = code;
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
        this.status = status;
        this.driver = driver;
        this.bus = bus;
        this.busImmat = busImmat;
        this.lineCode = lineCode;
        this.lineNumber = lineNumber;
        this.lineName = lineName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public String getBusImmat() {
        return busImmat;
    }

    public void setBusImmat(String busImmat) {
        this.busImmat = busImmat;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartHour() {
        return startHour;
    }

    public void setStartHour(Date startHour) {
        this.startHour = startHour;
    }

    public Date getEndHour() {
        return endHour;
    }

    public void setEndHour(Date endHour) {
        this.endHour = endHour;
    }

    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
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

    public String toString2() {
        return "PlanningItem{" +
                "code='" + code + '\'' +
                ", date=" + date +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                ", status='" + status + '\'' +
                ", driver='" + driver + '\'' +
                ", bus='" + bus + '\'' +
                ", busImmat='" + busImmat + '\'' +
                ", lineCode='" + lineCode + '\'' +
                ", lineNumber='" + lineNumber + '\'' +
                ", lineName='" + lineName + '\'' +
                '}';
    }

    @Override
    public String toString() {
        return code + " - " + status;
    }
}
