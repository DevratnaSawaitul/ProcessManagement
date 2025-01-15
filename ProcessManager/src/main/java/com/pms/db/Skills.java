package com.pms.db;

import javax.persistence.*;

import java.util.List;

import org.hibernate.*;
import org.hibernate.query.Query;
import com.pms.util.HibernateUtil;

@Entity
@Table(name = "skills")
public class Skills {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long skillId;
	private String skillName;
	private Boolean active;

	public Long getSkillId() {
		return skillId;
	}

	public void setSkillId(Long skillId) {
		this.skillId = skillId;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Skills{" + "skillId=" + skillId + ", skillName='" + skillName + '\'' + ", active=" + active + '}';
	}

	public Skills[] retrieveAllWhere(String condition) {
		Session session = HibernateUtil.pmsSessionFactory.openSession();
		try {
			List<Skills> list = session.createQuery("from Skills " + condition).getResultList();
			return (Skills[]) list.toArray(new Skills[list.size()]);
		} catch (Exception e) {
			// MessageLog.printError(e);
			return null;
		} finally {
			session.close();
		}
	}

	// Method to insert a new skill
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
					"update Skills set active = :active where skill_name = :skill_name");
			query.setParameter("skill_name", this.skillName);
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
