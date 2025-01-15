package com.pms.db;

import javax.persistence.*;

import java.util.List;

import org.hibernate.*;
import com.pms.util.HibernateUtil;
import org.hibernate.query.Query;

@Entity
@Table(name = "steps")
public class Steps {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long stepId;

	@Column(name = "file_name", nullable = false)
	private String fileName;

	@Column(name = "process_name", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT ''")
	private String processName;

	@Column(name = "subprocess_name", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT ''")
	private String subprocessName;

	@Column(name = "step_number", nullable = false)
	private int stepNumber;

	@Column(name = "tool_name", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT ''")
	private String toolName;

	@Column(name = "tool_spec", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT ''")
	private String toolSpec;

	@Column(name = "special_instruction", nullable = false, columnDefinition = "TEXT DEFAULT ''")
	private String specialInstruction;

	@Column(name = "skill", nullable = false, columnDefinition = "VARCHAR(50) DEFAULT ''")
	private String skill;

	@Column(name = "time_minutes", nullable = false)
	private int timeMinutes;

	@Column(name = "image_url", columnDefinition = "TEXT DEFAULT ''")
	private String imageUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sheet_process_id")
	private SheetProcess sheetProcess;

	// Getters and Setters
	public Long getStepId() {
		return stepId;
	}

	public void setStepId(Long stepId) {
		this.stepId = stepId;
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

	public String getSubprocessName() {
		return subprocessName;
	}

	public void setSubprocessName(String subprocessName) {
		this.subprocessName = subprocessName;
	}

	public int getStepNumber() {
		return stepNumber;
	}

	public void setStepNumber(int stepNumber) {
		this.stepNumber = stepNumber;
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public String getToolSpec() {
		return toolSpec;
	}

	public void setToolSpec(String toolSpec) {
		this.toolSpec = toolSpec;
	}

	public String getSpecialInstruction() {
		return specialInstruction;
	}

	public void setSpecialInstruction(String specialInstruction) {
		this.specialInstruction = specialInstruction;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public int getTimeMinutes() {
		return timeMinutes;
	}

	public void setTimeMinutes(int timeMinutes) {
		this.timeMinutes = timeMinutes;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public SheetProcess getSheetProcess() {
		return sheetProcess;
	}

	public void setSheetProcess(SheetProcess sheetProcess) {
		this.sheetProcess = sheetProcess;
	}

	@Override
	public String toString() {
		return "Steps{" + "stepId=" + stepId + ", fileName='" + fileName + '\'' + ", processName='" + processName + '\''
				+ ", subprocessName='" + subprocessName + '\'' + ", stepNumber=" + stepNumber + ", toolName='"
				+ toolName + '\'' + ", toolSpec='" + toolSpec + '\'' + ", specialInstruction='" + specialInstruction
				+ '\'' + ", skill='" + skill + '\'' + ", timeMinutes=" + timeMinutes + ", imageUrl='" + imageUrl + '\''
				+ ", sheetProcess=" + sheetProcess + '}';
	}

	// Method to retrieve all steps based on a condition
	public Steps[] retrieveAllWhere(String condition) {
		Session session = HibernateUtil.pmsSessionFactory.openSession();
		try {
			List<Steps> list = session.createQuery("from Steps " + condition).getResultList();
			return (Steps[]) list.toArray(new Steps[list.size()]);
		} catch (Exception e) {
			// MessageLog.printError(e);
			return null;
		} finally {
			session.close();
		}
	}

	// Method to insert a new step
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

	// Method to update a step based on file_name and step_number
	public boolean update() {
		Session session = HibernateUtil.pmsSessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			Query query = session.createQuery(
					"update Steps set process_name = :process_name, subprocess_name = :subprocess_name, step_number = :step_number, "
							+ "tool_name = :tool_name, tool_spec = :tool_spec, special_instruction = :special_instruction, "
							+ "skill = :skill, time_minutes = :time_minutes, image_url = :image_url where file_name = :file_name and step_number = :step_number");
			query.setParameter("process_name", this.processName);
			query.setParameter("subprocess_name", this.subprocessName);
			query.setParameter("step_number", this.stepNumber);
			query.setParameter("tool_name", this.toolName);
			query.setParameter("tool_spec", this.toolSpec);
			query.setParameter("special_instruction", this.specialInstruction);
			query.setParameter("skill", this.skill);
			query.setParameter("time_minutes", this.timeMinutes);
			query.setParameter("image_url", this.imageUrl);
			query.setParameter("file_name", this.fileName);
			query.setParameter("step_number", this.stepNumber);

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
