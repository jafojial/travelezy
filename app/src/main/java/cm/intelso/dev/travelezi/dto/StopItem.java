package cm.intelso.dev.travelezi.dto;

public class StopItem {

    private String code;
    private String name;
    private String lines;
    private double rtLat;
    private double rtLng;

    public StopItem() {
    }

    public StopItem(String code, String name, String lines, double rtLat, double rtLng) {
        this.code = code;
        this.name = name;
        this.lines = lines;
        this.rtLat = rtLat;
        this.rtLng = rtLng;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLines() {
        return lines;
    }

    public void setLines(String lines) {
        this.lines = lines;
    }

    public double getRtLat() {
        return rtLat;
    }

    public void setRtLat(double rtLat) {
        this.rtLat = rtLat;
    }

    public double getRtLng() {
        return rtLng;
    }

    public void setRtLng(double rtLng) {
        this.rtLng = rtLng;
    }

    /*@Override
    public String toString() {
        return "LineItem{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", lines='" + lines + '\'' +
                '}';
    }*/

    @Override
    public String toString() {
        return name;
    }
}
