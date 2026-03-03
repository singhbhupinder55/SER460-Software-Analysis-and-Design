package app.model;

public class Appointment {
    private String patientId;
    private String examDate; // store as ISO string (yyyy-mm-dd)

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getExamDate() { return examDate; }
    public void setExamDate(String examDate) { this.examDate = examDate; }
}