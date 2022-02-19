package hu.webuni.gnadigpeti.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import hu.webuni.gnadigpeti.model.Employee_;
import hu.webuni.gnadigpeti.model.Holiday;
import hu.webuni.gnadigpeti.model.Holiday_;

public  class HolidaySpecifications {
	
	public static Specification<Holiday> hasId(Long id){
		return (root, cq, cb) -> cb.equal(root.get(Holiday_.id), id);
	}
	
	public static Specification<Holiday> hasApproved(Boolean approved) {
		return (root, cq, cb) -> cb.equal(root.get(Holiday_.approved), approved);
	}

	public static Specification<Holiday> createDateIsBetween(LocalDateTime createDateTimeStart,
			LocalDateTime createDateTimeEnd) {
		return (root, cq, cb) -> cb.between(root.get(Holiday_.createdAt), createDateTimeStart, createDateTimeEnd);
	}

	public static Specification<Holiday> hasEmployeeName(String employeeName) {
		return (root, cq, cb) -> cb.like(cb.lower(root.get(Holiday_.employee).get(Employee_.name)),
				(employeeName + "%").toLowerCase());
	}

	public static Specification<Holiday> hasApprovalName(String approvalName) {
		return (root, cq, cb) -> cb.like(cb.lower(root.get(Holiday_.approver).get(Employee_.name)),
				(approvalName + "%").toLowerCase());
	}

	public static Specification<Holiday> isStartDateLessThan(LocalDate startOfHolidayRequest) {
		return (root, cq, cb) -> cb.lessThan(root.get(Holiday_.startDate), startOfHolidayRequest);
	}
	
	public static Specification<Holiday> isEndDateGreaterThan(LocalDate endOfHolidayRequest) {
		return (root, cq, cb) -> cb.greaterThan(root.get(Holiday_.endDate), endOfHolidayRequest);
	}
	
}
