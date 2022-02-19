package hu.webuni.gnadigpeti.service;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import hu.webuni.gnadigpeti.dto.HolidayFilterDTO;
import hu.webuni.gnadigpeti.model.Employee;
import hu.webuni.gnadigpeti.model.Holiday;
import hu.webuni.gnadigpeti.repository.HolidayRepository;

@Service
public class HolidayService {

	@Autowired
	HolidayRepository holidayRepository;
	
	@Autowired
	EmployeeService employeeService;
	
	
	public List<Holiday> findAll(){
		return holidayRepository.findAll();
	}
	
	public Optional<Holiday> findById(long id) {
		return holidayRepository.findById(id);
	}
	
	
	
	@Transactional
	public Holiday addHoliday(Holiday holiday, long employeeId) {
		Employee employee = employeeService.findById(employeeId).get();
		employee.addHolidayRequest(holiday);
		holiday.setCreatedAt(LocalDateTime.now());
		employeeService.save(employee);
		return holidayRepository.save(holiday);
	}
	
	@Transactional
	public Holiday modifyHoliday(long id, Holiday newHoliday) {
		Holiday Holiday = holidayRepository.findById(id).get();
		if (Holiday.getApproved() != null)
			throw new IllegalStateException();
		Holiday.setEndDate(newHoliday.getEndDate());
		Holiday.setStartDate(newHoliday.getStartDate());
		Holiday.setCreatedAt(LocalDateTime.now());
		return Holiday;
	}
	
	@Transactional
	public void deleteHoliday(long id) {
		Holiday holiday = holidayRepository.findById(id).get();
		if (holiday.getApproved() != null)
			throw new InvalidParameterException();
		holiday.getEmployee().getHolidayRequests().remove(holiday);
		holidayRepository.deleteById(id);
	}
	
	@Transactional
	public Holiday approveHoliday(long id, long approverId, boolean status) {
		Holiday holiday = holidayRepository.findById(id).get();
		holiday.setApprover(employeeService.findById(approverId).get());
		holiday.setApproved(status);
		holiday.setApprovedAt(LocalDateTime.now());
		return holiday;
	}
	
	public Page<Holiday> findHolidayRequestsByExample(HolidayFilterDTO example, Pageable pageable) {
		LocalDateTime createDateTimeStart = example.getCreateDateTimeStart();
		LocalDateTime createDateTimeEnd = example.getCreateDateTimeEnd();
		String employeeName = example.getEmployeeName();
		String approvalName = example.getApproverName();
		Boolean approved = example.getApproved();
		LocalDate startOfHolidayRequest = example.getStartDate();
		LocalDate endOfHolidayRequest = example.getEndDate();

		Specification<Holiday> spec = Specification.where(null);
		
		if (approved != null)
			spec = spec.and(HolidaySpecifications.hasApproved(approved));
		if (createDateTimeStart != null && createDateTimeEnd != null)
			spec = spec.and(HolidaySpecifications.createDateIsBetween(createDateTimeStart, createDateTimeEnd));
		if (StringUtils.hasText(employeeName))
			spec = spec.and(HolidaySpecifications.hasEmployeeName(employeeName));
		if (StringUtils.hasText(approvalName))
			spec = spec.and(HolidaySpecifications.hasApprovalName(approvalName));
		if (startOfHolidayRequest != null)
			spec = spec.and(HolidaySpecifications.isEndDateGreaterThan(startOfHolidayRequest));
		if (endOfHolidayRequest != null)
			spec = spec.and(HolidaySpecifications.isStartDateLessThan(endOfHolidayRequest));

		return holidayRepository.findAll(spec, pageable);
	}
}
