package application.models;

public class PemohonVisaModel {
    private int id;
    private String namaPemohon;
    private String noPaspor;
    private String jenisVisa;

    public PemohonVisaModel() {
    }

    public PemohonVisaModel(int id, String namaPemohon, String noPaspor, String jenisVisa) {
        this.id = id;
        this.namaPemohon = namaPemohon;
        this.noPaspor = noPaspor;
        this.jenisVisa = jenisVisa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaPemohon() {
        return namaPemohon;
    }

    public void setNamaPemohon(String namaPemohon) {
        this.namaPemohon = namaPemohon;
    }

    public String getNoPaspor() {
        return noPaspor;
    }

    public void setNoPaspor(String noPaspor) {
        this.noPaspor = noPaspor;
    }

    public String getJenisVisa() {
        return jenisVisa;
    }

    public void setJenisVisa(String jenisVisa) {
        this.jenisVisa = jenisVisa;
    }
}
