package com.pms.db;

import org.hibernate.query.Query;
import javax.persistence.*;
import org.hibernate.*;

import com.pms.util.HibernateUtil;

import java.util.List;

@Entity
@Table(name = "tools")
public class Tools {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long toolId;
	private String toolName;
	private String subProcess;
	private Boolean active;

	public Long getToolId() {
		return toolId;
	}

	public void setToolId(Long toolId) {
		this.toolId = toolId;
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public String getSubProcess() {
		return subProcess;
	}

	public void setSubProcess(String subProcess) {
		this.subProcess = subProcess;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Tools{" + "toolId=" + toolId + ", toolName='" + toolName + '\'' + ", subProcess='" + subProcess + '\''
				+ ", active=" + active + '}';
	}

	public Tools[] retrieveAllWhere(String condition) {
		Session session = HibernateUtil.pmsSessionFactory.openSession();
		try {
			List<Tools> list = session.createQuery(" from Tools " + condition).getResultList();
			return (Tools[]) list.toArray(new Tools[list.size()]);
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
					"update Tools set active = :active where toolName = :toolName");
			query.setParameter("toolName", this.toolName);
			query.setParameter("active", this.active);

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