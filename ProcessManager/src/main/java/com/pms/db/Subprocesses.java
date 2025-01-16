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
	private Long subprocess_id;
	private String process_name;
	private String subprocess_name;
	private Boolean active = true;

	public Long getSubprocessId() {
		return subprocess_id;
	}

	public void setSubprocessId(Long subprocessId) {
		this.subprocess_id = subprocessId;
	}

	public String getProcessName() {
		return process_name;
	}

	public void setProcessName(String processName) {
		this.process_name = processName;
	}

	public String getSubprocessName() {
		return subprocess_name;
	}

	public void setSubprocessName(String subprocessName) {
		this.subprocess_name = subprocessName;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Subprocesses{" + "subprocessId=" + subprocess_id + ", processName='" + process_name + '\''
				+ ", subprocessName='" + subprocess_name + '\'' + ", active=" + active + '}';
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
			query.setParameter("subprocess_name", this.subprocess_name);
			query.setParameter("active", this.active);
			query.setParameter("process_name", this.process_name);

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