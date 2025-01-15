package com.pms.db;

import java.util.List;

import org.hibernate.*;
import org.hibernate.query.Query;

import javax.persistence.*;
import com.pms.util.HibernateUtil;

@Entity
@Table(name = "sheets")
public class Sheets {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sheetId;
	private String fileName;
	private String version;
	private String date;
	private String department;
	private String designNo;
	private String floor;
	private String dateOfLastUpdate;
	private String lastUpdatedBy;

	public Long getSheetId() {
		return sheetId;
	}

	public void setSheetId(Long sheetId) {
		this.sheetId = sheetId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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
		return designNo;
	}

	public void setDesignNo(String designNo) {
		this.designNo = designNo;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getDateOfLastUpdate() {
		return dateOfLastUpdate;
	}

	public void setDateOfLastUpdate(String dateOfLastUpdate) {
		this.dateOfLastUpdate = dateOfLastUpdate;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Override
	public String toString() {
		return "Sheets{" + "sheetId=" + sheetId + ", fileName='" + fileName + '\'' + ", version='" + version + '\''
				+ ", date='" + date + '\'' + ", department='" + department + '\'' + ", designNo='" + designNo + '\''
				+ ", floor='" + floor + '\'' + ", dateOfLastUpdate='" + dateOfLastUpdate + '\'' + ", lastUpdatedBy='"
				+ lastUpdatedBy + '\'' + '}';
	}

	public Sheets[] retrieveAllWhere(String condition) {
		Session session = HibernateUtil.pmsSessionFactory.openSession();
		try {
			List<Sheets> list = session.createQuery("from Sheets " + condition).getResultList();
			return list.toArray(new Sheets[0]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}

	public boolean update() {
		// MessageLog.info("In Sheets update() file_name=" + fileName);
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
			query.setParameter("design_no", this.designNo);
			query.setParameter("floor", this.floor);
			query.setParameter("date_of_last_update", this.dateOfLastUpdate);
			query.setParameter("last_updated_by", this.lastUpdatedBy);
			query.setParameter("file_name", this.fileName);

			int status = query.executeUpdate();
			transaction.commit();
			return status != 0;
		} catch (Exception e) {
			// MessageLog.printError(e);
			transaction.rollback();
			return false;
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