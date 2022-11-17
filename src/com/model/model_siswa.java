/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;
import com.controller.controller_siswa;
import com.koneksi.koneksi;
import com.view.form_siswa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
/**
 *
 * @author Administrator
 */
public class model_siswa implements controller_siswa {
    String jk;

    @Override
    public void Simpan(form_siswa siswa) throws SQLException {
        if (siswa.rbLaki.isSelected()){
            jk = "Laki-laki";
        } else {
            jk = "Perempuan";
        }
        try {
            Connection con = koneksi.getcon();
            String sql = "Insert Into siswa Values(?,?,?,?,?,?,?)";
            PreparedStatement prepare = con.prepareStatement(sql);
            prepare.setString(1, siswa.txtNIS.getText());
            prepare.setString(2, siswa.txtNama.getText());
            prepare.setString(3, jk);
            prepare.setString(4, (String) siswa.cbKelas.getSelectedItem());
            prepare.setString(5, (String) siswa.cbJurusan.getSelectedItem());
            prepare.setString(6, siswa.txtEskul.getText());
            prepare.setString(7, siswa.txtHobi.getText());
            prepare.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil diSimpan");
            prepare.close();
            Baru(siswa);
        } catch (Exception e){
            System.out.println(e);
        } finally {
            Tampil(siswa);
            siswa.setLebarKolom();
        }
    }

    @Override
    public void Ubah(form_siswa siswa) throws SQLException {
        if (siswa.rbLaki.isSelected()){
            jk = "Laki-laki";
        } else {
            jk = "Perempuan";
        }
        try {
            Connection con = koneksi.getcon();
            String sql = "UPDATE Siswa SET nama=?, jenis_kelamin=?, kelas=?," + "jurusan=?, eskul=?, hobi=? WHERE NIS=?";
            PreparedStatement prepare = con.prepareStatement(sql);
            prepare.setString(7, siswa.txtNIS.getText());
            prepare.setString(1, siswa.txtNama.getText());
            prepare.setString(2, jk);
            prepare.setString(4, (String) siswa.cbJurusan.getSelectedItem());
            prepare.setString(3, (String) siswa.cbKelas.getSelectedItem());
            prepare.setString(5, siswa.txtEskul.getText());
            prepare.setString(6, siswa.txtHobi.getText());
            prepare.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil diUbah");
            prepare.close();
        } catch (Exception e){
            System.out.println(e);
        } finally {
            Tampil(siswa);
            siswa.setLebarKolom();
            Baru(siswa);
        }
    }

    @Override
    public void Hapus(form_siswa siswa) throws SQLException {
        try {
            Connection con = koneksi.getcon();
            String sql = "DELETE FROM Siswa WHERE NIS=?";
            PreparedStatement prepare = con.prepareStatement(sql);
            prepare.setString(1, siswa.txtNIS.getText());
            prepare.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil diHapus");
            prepare.close();
        } catch (Exception e){
            System.out.println(e);
        } finally {
            Tampil(siswa);
            siswa.setLebarKolom();
            Baru(siswa);
        }
    }

    @Override
    public void Tampil(form_siswa siswa) throws SQLException {
        siswa.tblmodel.getDataVector().removeAllElements();
       siswa.tblmodel.fireTableDataChanged();
        try {
            Connection con = koneksi.getcon();
            Statement stt = con.createStatement();
           // Query Menampilkan Semua Data Pada Table Siswa
           // Dengan Urutan NIS Dari Kecil Ke Besar
           String sql = "SELECT * FROM siswa ORDER BY nis ASC";
           ResultSet res = stt.executeQuery(sql);
            while (res.next()) {                
                Object[] ob = new Object[8];
                ob[0] = res.getString(1);
                ob[1] = res.getString(2);
                ob[2] = res.getString(3);
                ob[3] = res.getString(4);
                ob[4] = res.getString(5);
                ob[5] = res.getString(6);
                ob[6] = res.getString(7);
                siswa.tblmodel.addRow(ob);
            }
           
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void Baru(form_siswa siswa) throws SQLException {
        siswa.txtNIS.setText("");
        siswa.txtNama.setText("");
        siswa.rbLaki.setSelected(true);
        siswa.cbKelas.setSelectedIndex(0);
        siswa.cbJurusan.setSelectedIndex(0);
        siswa.txtEskul.setText("");
        siswa.txtHobi.setText("");
    }

    @Override
    public void KlikTabel(form_siswa siswa) throws SQLException {
        try {
        int pilih = siswa.tabel.getSelectedRow();
        if (pilih == -1) {
            return;
        }
        siswa.txtNIS.setText(siswa.tblmodel.getValueAt (pilih, 0).toString());
        siswa.txtNama.setText(siswa.tblmodel.getValueAt (pilih, 1).toString());
        siswa.cbJurusan.setSelectedItem(siswa.tblmodel.getValueAt (pilih, 4).toString());
        siswa.cbKelas.setSelectedItem(siswa.tblmodel.getValueAt (pilih, 3).toString());
        jk = String.valueOf(siswa.tblmodel.getValueAt (pilih, 2));
        siswa.txtHobi.setText(siswa.tblmodel.getValueAt (pilih, 6).toString());
        siswa.txtEskul.setText(siswa.tblmodel.getValueAt (pilih, 5).toString());
    } catch (Exception e) {
    }
        //memberi nilai jk pada radio button
        if (siswa.rbLaki.getText().equals(jk)) {
            siswa.rbLaki.setSelected(true);
        } else {
            siswa.rbPerempuan.setSelected(true);
        }
    }
}

