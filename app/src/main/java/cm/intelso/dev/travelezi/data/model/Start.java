package cm.intelso.dev.travelezi.data.model;

public class Start {
    private String uuid;
    private String departure_at;
    private String arrival_at;
    private String status;
    private String startedAt;
    private User controller;
    private String controlStatus;
    private String comment;
    private String cancelledAt;
    private String endedAt;
    private String endedBy;
    private Path path;
    private Planning planning;

    public Start() {
    }

    public Start(String uuid, String controlStatus, String departure_at, String arrival_at, String status, User controller, String comment, Path path, Planning planning) {
        this.uuid = uuid;
        this.controlStatus = controlStatus;
        this.departure_at = departure_at;
        this.arrival_at = arrival_at;
        this.status = status;
        this.controller = controller;
        this.comment = comment;
        this.path = path;
        this.planning = planning;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getControlStatus() {
        return controlStatus;
    }

    public void setControlStatus(String controlStatus) {
        this.controlStatus = controlStatus;
    }

    public String getDeparture_at() {
        return departure_at;
    }

    public void setDeparture_at(String departure_at) {
        this.departure_at = departure_at;
    }

    public String getArrival_at() {
        return arrival_at;
    }

    public void setArrival_at(String arrival_at) {
        this.arrival_at = arrival_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getController() {
        return controller;
    }

    public void setController(User controller) {
        this.controller = controller;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Planning getPlanning() {
        return planning;
    }

    public void setPlanning(Planning planning) {
        this.planning = planning;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(String cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public String getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(String endedAt) {
        this.endedAt = endedAt;
    }

    public String getEndedBy() {
        return endedBy;
    }

    public void setEndedBy(String endedBy) {
        this.endedBy = endedBy;
    }

    @Override
    public String toString() {
        return "Start{" +
                "uuid='" + uuid + '\'' +
                ", departure_at='" + departure_at + '\'' +
                ", arrival_at='" + arrival_at + '\'' +
                ", status='" + status + '\'' +
                ", startedAt=" + startedAt +
                ", controller=" + controller +
                ", controlStatus='" + controlStatus + '\'' +
                ", comment='" + comment + '\'' +
                ", cancelledAt=" + cancelledAt +
                ", endedAt=" + endedAt +
                ", endedBy='" + endedBy + '\'' +
                ", path=" + path +
                ", planning=" + planning +
                '}';
    }
}
