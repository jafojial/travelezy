package cm.intelso.dev.travelezi.data.model;

public class Planning {
    private String uuid;
    private String code;
    private String date;
    private String startAt;
    private String endAt;
    private String status;
    private User driver;
    private String createdAt;
    private String createdBy;
    private Line line;
    private Bus bus;

    public Planning() {
    }

    public Planning(String uuid, String code, String date, String startAt, String endAt, String status, User driver, String createdAt, String createdBy, Line line, Bus bus) {
        this.uuid = uuid;
        this.code = code;
        this.date = date;
        this.startAt = startAt;
        this.endAt = endAt;
        this.status = status;
        this.driver = driver;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.line = line;
        this.bus = bus;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    @Override
    public String toString() {
        return "Planning{" +
                "uuid='" + uuid + '\'' +
                ", code='" + code + '\'' +
                ", date='" + date + '\'' +
                ", startAt='" + startAt + '\'' +
                ", endAt='" + endAt + '\'' +
                ", status='" + status + '\'' +
                ", driver='" + driver + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", line=" + line +
                ", bus=" + bus +
                '}';
    }
}
