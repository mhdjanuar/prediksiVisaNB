package application.dao;

import application.models.PemohonVisaModel;
import java.util.List;

public interface PemohonVisaDao {
    public List<PemohonVisaModel> findAll();
    public int create(PemohonVisaModel pemohon);
    public int update(PemohonVisaModel pemohon);
    public int deletePemohon(int id);
}
