package app.storage;

import app.model.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class DataStore {

    private static final String DATA_DIR = "data";
    private static final String USERS_FILE = "users.txt";
    private static final String EMAIL_DIR = "emails";

    private final Random random = new Random();

    public DataStore() {
        ensureDataFolder();
        ensureUsersFileWithDefaults();
        ensureEmailFolder();
    }

    // ===================== PATIENT INTAKE =====================

    public String generateUniquePatientId() {
        while (true) {
            int num = 10000 + random.nextInt(90000);
            String id = String.valueOf(num);

            if (!Files.exists(patientInfoPath(id))) return id;
        }
    }

    public boolean savePatientInfo(Patient p) {
        if (p == null || p.getPatientId() == null || p.getPatientId().isBlank()) return false;

        Path file = patientInfoPath(p.getPatientId());

        try (BufferedWriter bw = Files.newBufferedWriter(file)) {
            bw.write("PatientID=" + safe(p.getPatientId())); bw.newLine();
            bw.write("FirstName=" + safe(p.getFirstName())); bw.newLine();
            bw.write("LastName=" + safe(p.getLastName())); bw.newLine();
            bw.write("Email=" + safe(p.getEmail())); bw.newLine();
            bw.write("PhoneNumber=" + safe(p.getPhoneNumber())); bw.newLine();
            bw.write("HealthHistory=" + safe(p.getHealthHistory())); bw.newLine();
            bw.write("InsuranceID=" + safe(p.getInsuranceId())); bw.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("Failed to save patient info: " + e.getMessage());
            return false;
        }
    }

    public Patient loadPatientInfo(String patientId) {
        if (patientId == null || patientId.isBlank()) return null;

        Path file = patientInfoPath(patientId.trim());
        if (!Files.exists(file)) return null;

        Patient p = new Patient();
        p.setPatientId(patientId.trim());

        try (BufferedReader br = Files.newBufferedReader(file)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length != 2) continue;
                String key = parts[0].trim();
                String val = parts[1].trim();

                switch (key) {
                    case "FirstName" -> p.setFirstName(val);
                    case "LastName" -> p.setLastName(val);
                    case "Email" -> p.setEmail(val);
                    case "PhoneNumber" -> p.setPhoneNumber(val);
                    case "HealthHistory" -> p.setHealthHistory(val);
                    case "InsuranceID" -> p.setInsuranceId(val);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load patient info: " + e.getMessage());
            return null;
        }

        return p;
    }

    // ===================== CT RESULTS =====================

    public boolean saveCTResults(CTScan scan) {
        if (scan == null || scan.getPatientId() == null || scan.getPatientId().isBlank()) return false;

        if (loadPatientInfo(scan.getPatientId()) == null) return false;

        Path file = ctResultsPath(scan.getPatientId());

        try (BufferedWriter bw = Files.newBufferedWriter(file)) {
            bw.write("PatientID=" + safe(scan.getPatientId())); bw.newLine();
            bw.write("TotalScore=" + scan.getTotalScore()); bw.newLine();
            bw.write("LM=" + scan.getLm()); bw.newLine();
            bw.write("LAD=" + scan.getLad()); bw.newLine();
            bw.write("LCX=" + scan.getLcx()); bw.newLine();
            bw.write("RCA=" + scan.getRca()); bw.newLine();
            bw.write("PDA=" + scan.getPda()); bw.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("Failed to save CT Results: " + e.getMessage());
            return false;
        }
    }

    public CTScan loadCTResults(String patientId) {
        if (patientId == null || patientId.isBlank()) return null;

        Path file = ctResultsPath(patientId.trim());
        if (!Files.exists(file)) return null;

        CTScan scan = new CTScan();
        scan.setPatientId(patientId.trim());

        try (BufferedReader br = Files.newBufferedReader(file)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length != 2) continue;

                String key = parts[0].trim();
                String val = parts[1].trim();

                switch (key) {
                    case "TotalScore" -> scan.setTotalScore(parseIntSafe(val));
                    case "LM" -> scan.setLm(parseIntSafe(val));
                    case "LAD" -> scan.setLad(parseIntSafe(val));
                    case "LCX" -> scan.setLcx(parseIntSafe(val));
                    case "RCA" -> scan.setRca(parseIntSafe(val));
                    case "PDA" -> scan.setPda(parseIntSafe(val));
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load CT Results: " + e.getMessage());
            return null;
        }

        return scan;
    }

    // ===================== APPOINTMENTS =====================

    public Path appointmentPath(String patientId) {
        return Paths.get(DATA_DIR, patientId + "_Appointment.txt");
    }

    public boolean saveAppointment(Appointment appt) {
        if (appt == null || appt.getPatientId() == null || appt.getPatientId().isBlank()) return false;

        // Patient should exist
        if (loadPatientInfo(appt.getPatientId()) == null) return false;

        Path p = appointmentPath(appt.getPatientId());
        String content =
                "PatientID=" + safe(appt.getPatientId()) + "\n" +
                "ExamDate=" + safe(appt.getExamDate()) + "\n";

        try {
            Files.writeString(p, content);
            return true;
        } catch (Exception e) {
            System.err.println("Failed to save appointment: " + e.getMessage());
            return false;
        }
    }

    // ===================== USERS / LOGIN =====================

    private void ensureUsersFileWithDefaults() {
        try {
            Path p = Paths.get(DATA_DIR, USERS_FILE);
            if (Files.exists(p)) return;

            // username|password|role
            String content =
                    "receptionist1|pass123|RECEPTIONIST\n" +
                    "tech1|pass123|CT_TECHNICIAN\n" +
                    "doctor1|pass123|DOCTOR\n";

            Files.writeString(p, content);
        } catch (Exception e) {
            System.err.println("Failed to create users file: " + e.getMessage());
        }
    }

    public User authenticateStaff(String username, String password) {
        Path p = Paths.get(DATA_DIR, USERS_FILE);
        if (!Files.exists(p)) return null;

        try (BufferedReader br = Files.newBufferedReader(p)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 3) continue;

                String u = parts[0].trim();
                String pw = parts[1].trim();
                String roleStr = parts[2].trim();

                if (u.equals(username) && pw.equals(password)) {
                    Role role = Role.valueOf(roleStr);
                    return new User(u, role);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to read users file: " + e.getMessage());
        }

        return null;
    }

    public boolean authenticatePatient(String patientId, String lastName) {
        Patient p = loadPatientInfo(patientId);
        if (p == null) return false;

        String storedLastName = p.getLastName();
        if (storedLastName == null) return false;

        return storedLastName.trim().equalsIgnoreCase(lastName.trim());
    }

    // ===================== EMAIL PROOF =====================

    private void ensureEmailFolder() {
        try {
            Files.createDirectories(Paths.get(DATA_DIR, EMAIL_DIR));
        } catch (Exception e) {
            System.err.println("Failed to create email folder: " + e.getMessage());
        }
    }

    public boolean saveDoctorEmailProof(Patient patient, CTScan scan, String riskText) {
        try {
            String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = patient.getPatientId() + "_Email_" + ts + ".txt";
            Path out = Paths.get(DATA_DIR, EMAIL_DIR, fileName);

            String body =
                    "To: " + safe(patient.getEmail()) + "\n" +
                    "Subject: CT Results and Risk Assessment\n\n" +
                    "Hello " + safe(patient.getFirstName()) + " " + safe(patient.getLastName()) + ",\n\n" +
                    "Your CT scan results are available.\n\n" +
                    "Total Agatston CAC Score: " + scan.getTotalScore() + "\n" +
                    "LM: " + scan.getLm() + "\n" +
                    "LAD: " + scan.getLad() + "\n" +
                    "LCX: " + scan.getLcx() + "\n" +
                    "RCA: " + scan.getRca() + "\n" +
                    "PDA: " + scan.getPda() + "\n\n" +
                    "Risk Assessment:\n" + riskText + "\n\n" +
                    "Regards,\nHeart Specialist\n";

            Files.writeString(out, body);
            return true;
        } catch (Exception e) {
            System.err.println("Failed to save email proof: " + e.getMessage());
            return false;
        }
    }

    // ===================== PATH HELPERS =====================

    public Path patientInfoPath(String patientId) {
        return Paths.get(DATA_DIR, patientId + "_PatientInfo.txt");
    }

    public Path ctResultsPath(String patientId) {
        return Paths.get(DATA_DIR, patientId + "_CTResults.txt");
    }

    private void ensureDataFolder() {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
        } catch (IOException e) {
            System.err.println("Could not create data folder: " + e.getMessage());
        }
    }

    private int parseIntSafe(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private String safe(String s) {
        return s == null ? "" : s.replace("\n", " ").trim();
    }
}