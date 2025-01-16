package com.pms.db;

import javax.persistence.*;
import org.hibernate.*;

import com.pms.util.HibernateUtil;

import java.util.List;

@Entity
@Table(name = "sheet_process")
public class SheetProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sheet_process_id;
    private String file_name;
    private String process_name;

    public Long getSheetProcessId() {
        return sheet_process_id;
    }

    public void setSheetProcessId(Long sheetProcessId) {
        this.sheet_process_id = sheetProcessId;
    }

    public String getFileName() {
        return file_name;
    }

    public void setFileName(String fileName) {
        this.file_name = fileName;
    }

    public String getProcessName() {
        return process_name;
    }

    public void setProcessName(String processName) {
        this.process_name = processName;
    }

    @Override
    public String toString() {
        return "SheetProcess{" +
                "sheetProcessId=" + sheet_process_id +
                ", fileName='" + file_name + '\'' +
                ", processName='" + process_name + '\'' +
                '}';
    }

    public SheetProcess[] retrieveAllWhere(String condition) {
        Session session = HibernateUtil.pmsSessionFactory.openSession();
        try {
            List<SheetProcess> list = session.createQuery("from SheetProcess " + condition).getResultList();
            return list.toArray(new SheetProcess[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public boolean insert() {
        Session session = HibernateUtil.pmsSessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(this);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
    }
}