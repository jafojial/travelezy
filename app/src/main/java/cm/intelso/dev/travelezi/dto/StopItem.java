package cm.intelso.dev.travelezi.dto;

public class StopItem {

    private String code;
    private String name;
    private String lines;

    public StopItem() {
    }

    public StopItem(String code, String name, String lines) {
        this.code = code;
        this.name = name;
        this.lines = lines;
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
        return code + " - " + name;
    }
}
