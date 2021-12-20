package cm.intelso.dev.travelezi.dto;

public class StartIpt {

    private String path;
    private String planning;

    public StartIpt() {
    }

    public StartIpt(String path, String planning) {
        this.path = path;
        this.planning = planning;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPlanning() {
        return planning;
    }

    public void setPlanning(String planning) {
        this.planning = planning;
    }

    @Override
    public String toString() {
        return "StartIpt{" +
                "path='" + path + '\'' +
                ", planning='" + planning + '\'' +
                '}';
    }
}
