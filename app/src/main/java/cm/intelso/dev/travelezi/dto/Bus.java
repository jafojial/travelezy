package cm.intelso.dev.travelezi.dto;

public class Bus {

    private String code;
    private String immat;
    private double rtLat;
    private double rtLng;

    public Bus() {
    }

    public Bus(String code, double rtLat, double rtLng, String immat) {
        this.code = code;
        this.rtLat = rtLat;
        this.rtLng = rtLng;
        this.immat = immat;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getImmat() {
        return immat;
    }

    public void setImmat(String immat) {
        this.immat = immat;
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
        return code;
    }
}
