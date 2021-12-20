package cm.intelso.dev.travelezi.data.model;

public class POI {

    String uuid;
    String code;
    String name;
    String address;
    String completeName;
    String latitude;
    String longitude;
    String placeId;
    City city;
    String type;

    public POI() {
    }

    public POI(String uuid, String code, String name, String address, String completeName, String latitude, String longitude, String placeId, City city, String type) {
        this.uuid = uuid;
        this.code = code;
        this.name = name;
        this.address = address;
        this.completeName = completeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placeId = placeId;
        this.city = city;
        this.type = type;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompleteName() {
        return completeName;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
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

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "POI{" +
                "uuid='" + uuid + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", completeName='" + completeName + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", placeId='" + placeId + '\'' +
                ", city=" + city +
                ", type='" + type + '\'' +
                '}';
    }
}
