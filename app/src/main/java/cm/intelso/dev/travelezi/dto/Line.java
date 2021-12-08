package cm.intelso.dev.travelezi.dto;

import java.util.List;

public class Line {
    private String uuid;
    private String code;
    private String number;
    private String name;
    private String type;
    private List<Planning> plannings;
    private POI startPoint;
    private POI endPoint;

    public Line() {
    }

    public Line(String uuid, String code, String number, String name, String type, List<Planning> plannings, POI startPoint, POI endPoint) {
        this.uuid = uuid;
        this.code = code;
        this.number = number;
        this.name = name;
        this.type = type;
        this.plannings = plannings;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Planning> getPlannings() {
        return plannings;
    }

    public void setPlannings(List<Planning> plannings) {
        this.plannings = plannings;
    }

    public POI getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(POI startPoint) {
        this.startPoint = startPoint;
    }

    public POI getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(POI endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public String toString() {
        return "Line{" +
                "uuid='" + uuid + '\'' +
                ", code='" + code + '\'' +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", plannings=" + plannings +
                ", startPoint=" + startPoint +
                ", endPoint=" + endPoint +
                '}';
    }
}
