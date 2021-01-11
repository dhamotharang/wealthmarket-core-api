package com.wm.core.controller;

import com.wm.core.model.agency.Agencies;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.service.agency.AgenciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("${app.title}")
public class AgencyController {

    @Autowired
    AgenciesService agenciesService;

	@PutMapping("agency/update")
	ResponseEntity<?> editAgencyDetails(@Valid @RequestBody Agencies agencies) {
		BaseResponse response = agenciesService.editAgencyDetails(agencies);
		if (response.getStatusCode() == HttpServletResponse.SC_OK) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}


    @GetMapping("/agency/getall")
    ResponseEntity<?> getAllAgencies() {
        BaseResponse response = agenciesService.getAllAgencies();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/agency/license")
    ResponseEntity<?> licenseAgency(@RequestAttribute("userId") long userId, @RequestParam long agencyId, @RequestParam long statusId) {
        BaseResponse response = agenciesService.LicenseAgency(userId, agencyId, statusId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }




}
