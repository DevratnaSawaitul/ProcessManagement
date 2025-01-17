package com.pms.db;

import java.util.List;

import org.hibernate.*;
import org.hibernate.query.Query;

import javax.mail.Message;
import javax.persistence.*;
import com.pms.util.HibernateUtil;
import com.pms.util.MessageLog;

@Entity
@Table(name = "sheets")
public class Sheets {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sheet_id;
	private String file_name;
	private String version;
	private String date;
	private String department;
	private String design_no;
	private String floor;
	private String date_of_last_update;
	private String last_updated_by;

	public Long getSheetId() {
		return sheet_id;
	}

	public void setSheetId(Long sheetId) {
		this.sheet_id = sheetId;
	}

	public String getFileName() {
		return file_name;
	}

	public void setFileName(String fileName) {
		this.file_name = fileName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignNo() {
		return design_no;
	}

	public void setDesignNo(String designNo) {
		this.design_no = designNo;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getDateOfLastUpdate() {
		return date_of_last_update;
	}

	public void setDateOfLastUpdate(String dateOfLastUpdate) {
		this.date_of_last_update = dateOfLastUpdate;
	}

	public String getLastUpdatedBy() {
		return last_updated_by;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.last_updated_by = lastUpdatedBy;
	}

	public Sheets[] retrieveAllWhere(String condition) {
		MessageLog.info("In Sheets retrieveAllWhere condition= " + condition);
		Session session = HibernateUtil.pmsSessionFactory.openSession();
		try {
			List<Sheets> list = session.createQuery("from Sheets " + condition).getResultList();
			return list.toArray(new Sheets[0]);
		} catch (Exception e) {
			MessageLog.printError(e);
			return null;
		} finally {
			session.close();
		}
	}

	public boolean update() {
		MessageLog.info("In Sheets update() file_name=" + file_name);
		Session session = HibernateUtil.pmsSessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			Query query = session
					.createQuery("update Sheets set version = :version, date = :date, department = :department, "
							+ "design_no = :design_no, floor = :floor, date_of_last_update = :date_of_last_update, "
							+ "last_updated_by = :last_updated_by where file_name = :file_name");
			query.setParameter("version", this.version);
			query.setParameter("date", this.date);
			query.setParameter("department", this.department);
			query.setParameter("design_no", this.design_no);
			query.setParameter("floor", this.floor);
			query.setParameter("date_of_last_update", this.date_of_last_update);
			query.setParameter("last_updated_by", this.last_updated_by);
			query.setParameter("file_name", this.file_name);

			int status = query.executeUpdate();
			transaction.commit();
			return status != 0;
		} catch (Exception e) {
			MessageLog.printError(e);
			transaction.rollback();
			return false;
		} finally {
			session.close();
		}
	}

	public boolean insert() {
		MessageLog.info("In Sheets insert filename= " + file_name);
		Session session = HibernateUtil.pmsSessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.save(this);
			transaction.commit();
			return true;
		} catch (Exception e) {
			MessageLog.printError(e);
			transaction.rollback();
			return false;
		} finally {
			session.close();
		}
	}
}