package app.model;

public class Patient {
    private String patientId;       // 5-digit string
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String healthHistory;
    private String insuranceId;

    public Patient() {}

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getHealthHistory() { return healthHistory; }
    public void setHealthHistory(String healthHistory) { this.healthHistory = healthHistory; }

    public String getInsuranceId() { return insuranceId; }
    public void setInsuranceId(String insuranceId) { this.insuranceId = insuranceId; }

    public String getFullName() {
        return (firstName == null ? "" : firstName) + " " + (lastName == null ? "" : lastName);
    }

    @Override
    public String toString() {
        return patientId + " - " + getFullName().trim();
    }
}