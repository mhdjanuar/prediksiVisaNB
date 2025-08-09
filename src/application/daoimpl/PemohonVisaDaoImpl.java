package application.daoimpl;

import application.dao.PemohonVisaDao;
import application.models.PemohonVisaModel;
import application.utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PemohonVisaDaoImpl implements PemohonVisaDao {

    private Connection dbConnection;
    private PreparedStatement pstmt;
    private ResultSet resultSet;
    private String query;

    public PemohonVisaDaoImpl() {
        dbConnection = DatabaseUtil.getInstance().getConnection();
    }

    @Override
    public List<PemohonVisaModel> findAll() {
        List<PemohonVisaModel> listData = new ArrayList<>();
        try {
            query = "SELECT * FROM pemohon";
            pstmt = dbConnection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                PemohonVisaModel pemohon = new PemohonVisaModel();
                pemohon.setId(resultSet.getInt("id"));
                pemohon.setNamaPemohon(resultSet.getString("nama_pemohon"));
                pemohon.setNoPaspor(resultSet.getString("no_paspor"));
                pemohon.setJenisVisa(resultSet.getString("jenis_visa"));
                listData.add(pemohon);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeResources();
        }
        return listData;
    }

    @Override
    public int create(PemohonVisaModel pemohon) {
        try {
            query = "INSERT INTO pemohon (nama_pemohon, no_paspor, jenis_visa) VALUES (?, ?, ?)";
            pstmt = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, pemohon.getNamaPemohon());
            pstmt.setString(2, pemohon.getNoPaspor());
            pstmt.setString(3, pemohon.getJenisVisa());

            int result = pstmt.executeUpdate();
            resultSet = pstmt.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeResources();
        }
    }

    @Override
    public int update(PemohonVisaModel pemohon) {
        try {
            query = "UPDATE pemohon SET nama_pemohon = ?, no_paspor = ?, jenis_visa = ? WHERE id = ?";
            pstmt = dbConnection.prepareStatement(query);
            pstmt.setString(1, pemohon.getNamaPemohon());
            pstmt.setString(2, pemohon.getNoPaspor());
            pstmt.setString(3, pemohon.getJenisVisa());
            pstmt.setInt(4, pemohon.getId());

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeResources();
        }
    }

    @Override
    public int deletePemohon(int id) {
        try {
            query = "DELETE FROM pemohon WHERE id = ?";
            pstmt = dbConnection.prepareStatement(query);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeResources();
        }
    }

    private void closeResources() {
        try {
            if (resultSet != null) resultSet.close();
            if (pstmt != null) pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
