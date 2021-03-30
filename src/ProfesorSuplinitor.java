import java.util.ArrayList;

public class ProfesorSuplinitor extends Profesor{
    private String data_inceput;
    private String data_final;
    public ProfesorSuplinitor(String nume, String prenume,String data_inceput, String data_final) {
        super(nume, prenume);
        this.data_inceput = data_inceput;
        this.data_final = data_final;
    }

    public String getData_inceput() {
        return data_inceput;
    }

    public String getData_final() {
        return data_final;
    }

    public void setData_inceput(String data_inceput) {
        this.data_inceput = data_inceput;
    }

    public void setData_final(String data_final) {
        this.data_final = data_final;
    }
}
