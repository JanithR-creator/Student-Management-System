package view.tm;

import javafx.scene.control.Button;

public class IntakeTm {
    private String code;
    private String date;
    private String intakeName;
    private String pName;
    private Button btn;

    public IntakeTm() {
    }

    public IntakeTm(String code, String date, String intakeName, String pName, Button btn) {
        this.code = code;
        this.date = date;
        this.intakeName = intakeName;
        this.pName = pName;
        this.btn = btn;
    }

    public Button getBtn() {
        return btn;
    }

    public void setCode(Button btn) {
        this.btn = btn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIntakeName() {
        return intakeName;
    }

    public void setIntakeName(String intakeName) {
        this.intakeName = intakeName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "IntakeTm{" +
                "code='" + code + '\'' +
                ", date='" + date + '\'' +
                ", intakeName='" + intakeName + '\'' +
                ", pName='" + pName + '\'' +
                '}';
    }
}
