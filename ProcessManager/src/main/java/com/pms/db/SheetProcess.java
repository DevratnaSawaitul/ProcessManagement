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
    private Long sheetProcessId;
    private String fileName;
    private String processName;

    public Long getSheetProcessId() {
        return sheetProcessId;
    }

    public void setSheetProcessId(Long sheetProcessId) {
        this.sheetProcessId = sheetProcessId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    @Override
    public String toString() {
        return "SheetProcess{" +
                "sheetProcessId=" + sheetProcessId +
                ", fileName='" + fileName + '\'' +
                ", processName='" + processName + '\'' +
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