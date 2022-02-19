package hu.webuni.gnadigpeti.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class HolidayDTO {
	private long id;
	private LocalDateTime createdAt;
	@NotNull
	private Long employeeId;
	private Long approverId;
	private Boolean approved;
	private LocalDateTime approvedAt;
	@NotNull
	private LocalDate startDate;
	@NotNull
	private LocalDate endDate;
	
	public HolidayDTO() {
	}

	
	
	public HolidayDTO(LocalDateTime createdAt, @NotNull Long employeeId, Long approverId, Boolean approved,
			LocalDateTime approvedAt, @NotNull LocalDate startDate, @NotNull LocalDate endDate) {
		super();
		this.createdAt = createdAt;
		this.employeeId = employeeId;
		this.approverId = approverId;
		this.approved = approved;
		this.approvedAt = approvedAt;
		this.startDate = startDate;
		this.endDate = endDate;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getApproverId() {
		return approverId;
	}

	public void setApproverId(Long approverId) {
		this.approverId = approverId;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public LocalDateTime getApprovedAt() {
		return approvedAt;
	}

	public void setApprovedAt(LocalDateTime approvedAt) {
		this.approvedAt = approvedAt;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "HolidayDTO [id=" + id + ", createdAt=" + createdAt + ", employeeId=" + employeeId + ", approverId="
				+ approverId + ", approved=" + approved + ", approvedAt=" + approvedAt + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}
	
	
}
