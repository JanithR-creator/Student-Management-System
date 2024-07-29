package model;

import java.util.Date;

public class Intake {
    private String intake;
    private Date startDate;
    private String intakeName;
    private boolean intakeCompleteness;//object eka hadaddi false wenna ooni
    private String programId;

    public Intake() {
    }

    public Intake(String intake, Date startDate, String intakeName, boolean intakeCompleteness, String programId) {
        this.intake = intake;
        this.startDate = startDate;
        this.intakeName = intakeName;
        this.intakeCompleteness = intakeCompleteness;
        this.programId = programId;
    }

    public String getIntake() {
        return intake;
    }

    public void setIntake(String intake) {
        this.intake = intake;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getIntakeName() {
        return intakeName;
    }

    public void setIntakeName(String intakeName) {
        this.intakeName = intakeName;
    }

    public boolean isIntakeCompleteness() {
        return intakeCompleteness;
    }

    public void setIntakeCompleteness(boolean intakeCompleteness) {
        this.intakeCompleteness = intakeCompleteness;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    @Override
    public String toString() {
        return "Intake{" +
                "intake='" + intake + '\'' +
                ", startDate=" + startDate +
                ", intakeName='" + intakeName + '\'' +
                ", intakeCompleteness=" + intakeCompleteness +
                ", programId='" + programId + '\'' +
                '}';
    }
}
