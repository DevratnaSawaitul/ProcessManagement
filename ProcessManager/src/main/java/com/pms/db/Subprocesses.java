package com.pms.db;

import javax.persistence.*;

import java.util.List;

import org.hibernate.*;
import com.pms.util.HibernateUtil;
import org.hibernate.query.Query;

@Entity
@Table(name = "subprocesses")
public class Subprocesses {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subprocessId;
	private String processName;
	private String subprocessName;
	private Boolean active = true;

	public Long getSubprocessId() {
		return subprocessId;
	}

	public void setSubprocessId(Long subprocessId) {
		this.subprocessId = subprocessId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getSubprocessName() {
		return subprocessName;
	}

	public void setSubprocessName(String subprocessName) {
		this.subprocessName = subprocessName;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Subprocesses{" + "subprocessId=" + subprocessId + ", processName='" + processName + '\''
				+ ", subprocessName='" + subprocessName + '\'' + ", active=" + active + '}';
	}

	public Subprocesses[] retrieveAllWhere(String condition) {
		Session session = HibernateUtil.pmsSessionFactory.openSession();
		try {
			List<Subprocesses> list = session.createQuery("from Subprocesses " + condition).getResultList();
			return (Subprocesses[]) list.toArray(new Subprocesses[list.size()]);
		} catch (Exception e) {
			// MessageLog.printError(e);
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
			System.out.println(e);
			transaction.rollback();
			return false;
		} finally {
			session.close();
		}
	}

	public boolean update() {
		Session session = HibernateUtil.pmsSessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			Query query = session.createQuery(
					"update Subprocesses set process_name = :process_name, active = :active where subprocess_name = :subprocess_name");
			query.setParameter("subprocess_name", this.subprocessName);
			query.setParameter("active", this.active);
			query.setParameter("process_name", this.processName);

			int status = query.executeUpdate();
			transaction.commit();
			return status != 0;
		} catch (Exception e) {
			System.out.println(e);
			transaction.rollback();
			return false;
		} finally {
			session.close();
		}
	}
}