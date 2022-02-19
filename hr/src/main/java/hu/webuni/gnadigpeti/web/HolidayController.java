package hu.webuni.gnadigpeti.web;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.gnadigpeti.dto.HolidayDTO;
import hu.webuni.gnadigpeti.dto.HolidayFilterDTO;
import hu.webuni.gnadigpeti.mapper.HolidayMapper;
import hu.webuni.gnadigpeti.model.Holiday;
import hu.webuni.gnadigpeti.service.HolidayService;

@RestController
@RequestMapping("/api/holidays")
public class HolidayController {

	@Autowired
	HolidayService holidayService;
	
	@Autowired
	HolidayMapper holidayMapper;
	
	@GetMapping
	public List<HolidayDTO> getAll() {
		return holidayMapper.holidayRequestsToDtos(holidayService.findAll());
	}
	
	@GetMapping("/{id}")
	public HolidayDTO getById(@PathVariable long id) {
		Holiday holidayRequest = holidayService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return holidayMapper.holidayRequestToDto(holidayRequest);
	}
	
	//@PreAuthorize("#newHolidayRequest.employeeId == authentication.principal.employee.employeeId")
	@PostMapping
	public HolidayDTO addHolidayRequest(@RequestBody @Valid HolidayDTO newHoliday) {
		Holiday holiday;
		try {
			holiday = holidayService.addHoliday(holidayMapper.dtoToHolidayRequest(newHoliday), newHoliday.getEmployeeId());
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return holidayMapper.holidayRequestToDto(holiday);
	}
	
	
	@PutMapping("/{id}")
	public HolidayDTO modifyHoliday(@PathVariable long id, @RequestBody @Valid HolidayDTO modifiedHoliday) {
		modifiedHoliday.setEmployeeId(id);
		Holiday holiday;
		try {
			holiday = holidayService.modifyHoliday(id, holidayMapper.dtoToHolidayRequest(modifiedHoliday));
		} catch (InvalidParameterException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		return holidayMapper.holidayRequestToDto(holiday);
	}
	
	@DeleteMapping("/{id}")
	public void deleteHolidayRequest(@PathVariable long id) {
		try {
			holidayService.deleteHoliday(id);
		} catch (InvalidParameterException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(value = "/{id}/approval", params = { "status", "approverId" })
	public HolidayDTO approveHolidayRequest(@PathVariable long id, @RequestParam long approverId, @RequestParam boolean status) {
		Holiday holidayRequest;
		try {
			holidayRequest = holidayService.approveHoliday(id, approverId, status);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return holidayMapper.holidayRequestToDto(holidayRequest);
	}
	
	@GetMapping(value = "/search")
	public List<HolidayDTO> findByExample(@RequestBody HolidayFilterDTO example, 
			Pageable pageable) {
		Page<Holiday> page = holidayService.findHolidayRequestsByExample(example, pageable);
		return holidayMapper.holidayRequestsToDtos(page.getContent());
	}
	
	
	
	
}
