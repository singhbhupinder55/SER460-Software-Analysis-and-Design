package app.model;

public class CTScan {
    private String patientId;

    private int totalScore;

    private int lm;
    private int lad;
    private int lcx;
    private int rca;
    private int pda;

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public int getTotalScore() { return totalScore; }
    public void setTotalScore(int totalScore) { this.totalScore = totalScore; }

    public int getLm() { return lm; }
    public void setLm(int lm) { this.lm = lm; }

    public int getLad() { return lad; }
    public void setLad(int lad) { this.lad = lad; }

    public int getLcx() { return lcx; }
    public void setLcx(int lcx) { this.lcx = lcx; }

    public int getRca() { return rca; }
    public void setRca(int rca) { this.rca = rca; }

    public int getPda() { return pda; }
    public void setPda(int pda) { this.pda = pda; }

    public static String determineRisk(double totalScore) {
    if (totalScore == 0) {
        return "Zero: No plaque. Your risk of heart attack is low.";
    } else if (totalScore >= 1 && totalScore <= 10) {
        return "1 - 10: Small amount of plaque. You have less than a 10 percent chance of having heart disease, and your risk of heart attack is low.";
    } else if (totalScore >= 11 && totalScore <= 100) {
        return "11 - 100: Some plaque. You have mild heart disease and a moderate chance of heart attack. Your doctor may recommend other treatment in addition to lifestyle changes.";
    } else if (totalScore >= 101 && totalScore <= 400) {
        return "101 - 400: Moderate amount of plaque. You have heart disease and plaque may be blocking an artery. Your chance of having a heart attack is moderate to high. Your health professional may want more tests and may start treatment.";
    } else {
        return "Over 400: Large amount of plaque. You have more than a 90 percent chance that plaque is blocking one of your arteries. Your chance of heart attack is high. Your health professional will want more tests and will start treatment.";
    }
}
}