package cm.intelso.dev.travelezi.dto;

public class GenericResponse {

    private int status;
    private String succes;
    private String uuid;
    private String errors;

    public GenericResponse() {
    }

    public GenericResponse(int status, String succes, String uuid, String errors) {
        this.status = status;
        this.succes = succes;
        this.uuid = uuid;
        this.errors = errors;
    }

    public String getSucces() {
        return succes;
    }

    public void setSucces(String succes) {
        this.succes = succes;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "GenericResponse{" +
                "status=" + status +
                ", succes='" + succes + '\'' +
                ", uuid='" + uuid + '\'' +
                ", errors='" + errors + '\'' +
                '}';
    }
}
