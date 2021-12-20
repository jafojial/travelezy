package cm.intelso.dev.travelezi.dto;

import java.util.Date;

public class StartItem {

    private String code;
    private String path;
    private Date date;
    private Date beginHour;
    private Date endHour;
    private String status;
    private Date startedAt;
    private String controller;
    private String controlStatus;
    private String comment;
    private Date cancelledAt;
    private Date endedAt;
    private String endedBy;

    public StartItem() {
    }

    public StartItem(String code, String path, Date date, Date beginHour, Date endHour, String status, Date startedAt) {
        this.code = code;
        this.path = path;
        this.date = date;
        this.beginHour = beginHour;
        this.endHour = endHour;
        this.status = status;
        this.startedAt = startedAt;
    }

    public StartItem(String code, String path, Date date, Date beginHour, Date endHour, String status, Date startedAt, String controller, String controlStatus, String comment, Date cancelledAt, Date endedAt, String endedBy) {
        this.code = code;
        this.path = path;
        this.date = date;
        this.beginHour = beginHour;
        this.endHour = endHour;
        this.status = status;
        this.startedAt = startedAt;
        this.controller = controller;
        this.controlStatus = controlStatus;
        this.comment = comment;
        this.cancelledAt = cancelledAt;
        this.endedAt = endedAt;
        this.endedBy = endedBy;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getControlStatus() {
        return controlStatus;
    }

    public void setControlStatus(String controlStatus) {
        this.controlStatus = controlStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getBeginHour() {
        return beginHour;
    }

    public void setBeginHour(Date beginHour) {
        this.beginHour = beginHour;
    }

    public Date getEndHour() {
        return endHour;
    }

    public void setEndHour(Date endHour) {
        this.endHour = endHour;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Date getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(Date cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public Date getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(Date endedAt) {
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
        return "StartItem{" +
                "code='" + code + '\'' +
                ", path='" + path + '\'' +
                ", date=" + date +
                ", beginHour=" + beginHour +
                ", endHour=" + endHour +
                ", status='" + status + '\'' +
                ", startedAt=" + startedAt +
                ", controller='" + controller + '\'' +
                ", controlStatus='" + controlStatus + '\'' +
                ", comment='" + comment + '\'' +
                ", cancelledAt=" + cancelledAt +
                ", endedAt=" + endedAt +
                ", endedBy='" + endedBy + '\'' +
                '}';
    }
}
