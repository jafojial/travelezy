package cm.intelso.dev.travelezi.dto;

import java.sql.Date;

public class User {

    private String uuid;
    private String email;
    private String roles;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private Date dob;
    private String gender;
    private String phone;
    private Boolean isVerified;
    private String status;
    private String experience;
    private String minibio;
    private String pobox;
    private String city;
    private String countryCode;
    private Boolean phoneVerified;
    private Boolean emailVerified;
    private String driverLicense;
    // private List<Planning> plannings;
    private Date phoneVerifiedAt;
    private Date emailVerifiedAt;
    private Date created_at;
    private Date editedAt;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String uuid, String email, String roles, String username, String password, String firstname, String lastname, Date dob, String gender, String phone, Boolean isVerified, String status, String experience, String minibio, String pobox, String city, String countryCode, Boolean phoneVerified, Boolean emailVerified, String driverLicense, Date phoneVerifiedAt, Date emailVerifiedAt, Date created_at, Date editedAt) {
        this.uuid = uuid;
        this.email = email;
        this.roles = roles;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.isVerified = isVerified;
        this.status = status;
        this.experience = experience;
        this.minibio = minibio;
        this.pobox = pobox;
        this.city = city;
        this.countryCode = countryCode;
        this.phoneVerified = phoneVerified;
        this.emailVerified = emailVerified;
        this.driverLicense = driverLicense;
        this.phoneVerifiedAt = phoneVerifiedAt;
        this.emailVerifiedAt = emailVerifiedAt;
        this.created_at = created_at;
        this.editedAt = editedAt;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getMinibio() {
        return minibio;
    }

    public void setMinibio(String minibio) {
        this.minibio = minibio;
    }

    public String getPobox() {
        return pobox;
    }

    public void setPobox(String pobox) {
        this.pobox = pobox;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Boolean getPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(Boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    public Date getPhoneVerifiedAt() {
        return phoneVerifiedAt;
    }

    public void setPhoneVerifiedAt(Date phoneVerifiedAt) {
        this.phoneVerifiedAt = phoneVerifiedAt;
    }

    public Date getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(Date emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(Date editedAt) {
        this.editedAt = editedAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid='" + uuid + '\'' +
                ", email='" + email + '\'' +
                ", roles='" + roles + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dob=" + dob +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", isVerified=" + isVerified +
                ", status='" + status + '\'' +
                ", experience='" + experience + '\'' +
                ", minibio='" + minibio + '\'' +
                ", pobox='" + pobox + '\'' +
                ", city='" + city + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", phoneVerified=" + phoneVerified +
                ", emailVerified=" + emailVerified +
                ", driverLicense='" + driverLicense + '\'' +
                ", phoneVerifiedAt=" + phoneVerifiedAt +
                ", emailVerifiedAt=" + emailVerifiedAt +
                ", created_at=" + created_at +
                ", editedAt=" + editedAt +
                '}';
    }
}
