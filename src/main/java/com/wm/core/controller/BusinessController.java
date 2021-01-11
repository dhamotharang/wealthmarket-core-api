package com.wm.core.controller;

import com.wm.core.model.business.BusinessSector;
import com.wm.core.model.business.BusinessType;
import com.wm.core.model.business.Businesses;
import com.wm.core.model.business.Producers;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.service.business.BusinessSectorsService;
import com.wm.core.service.business.BusinessTypesService;
import com.wm.core.service.business.BusinessesService;
import com.wm.core.service.business.ProducersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("${app.title}")
public class BusinessController {

    @Autowired
    BusinessSectorsService businessSectorsService;

    @Autowired
    BusinessTypesService businessTypesService;

    @Autowired
    BusinessesService businessesService;

    @Autowired
    ProducersService producersService;


    //--------------------Start--------Business Sector -------------------------
    @PostMapping("/businesses/sectors/create")
    ResponseEntity<?> createBusinessSector(@RequestAttribute("userId") long userid, @Valid @RequestBody BusinessSector businessSector) {
        BaseResponse response = businessSectorsService.createBusinessSector(businessSector, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/businesses/sectors/delete/{id}")
    ResponseEntity<?> deleteBusinessSector(@PathVariable Long id) {
        BaseResponse response = businessSectorsService.deleteBusinessSector(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/businesses/sectors/update")
    ResponseEntity<?> editBusinessSector(@RequestAttribute("userId") long userid, @Valid @RequestBody BusinessSector businessSector) {
        BaseResponse response = businessSectorsService.editBusinessSector(businessSector, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/businesses/sectors/getall")
    ResponseEntity<?> getBusinessSectors() {
        BaseResponse response = businessSectorsService.getAllBusinessSectors();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/businesses/sectors/get/{id}")
    ResponseEntity<?> getBusinessSector(@PathVariable long id) {
        BaseResponse response = businessSectorsService.getBusinessSector(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    //--------------------End--------Business Sector -------------------------


    //--------------------Start--------Business Type -------------------------
    @PostMapping("/businesses/business_types/create")
    ResponseEntity<?> createBusinessType(@RequestAttribute("userId") long userid, @RequestBody BusinessType businessType) {
        BaseResponse response = businessTypesService.createBusinessType(businessType, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/businesses/business_types/delete/{id}")
    ResponseEntity<?> deleteBusinessType(@PathVariable Long id) {
        BaseResponse response = businessTypesService.deleteBusinessType(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/businesses/business_types/update")
    ResponseEntity<?> editBusinessType(@RequestAttribute("userId") long userid, @RequestBody BusinessType businessType) {
        BaseResponse response = businessTypesService.editBusinessType(businessType, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/businesses/business_types/get/{id}")
    ResponseEntity<?> getBusinessType(@PathVariable long id) {
        BaseResponse response = businessTypesService.getBusinessType(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/businesses/business_types/getall")
    ResponseEntity<?> getAllBusinessTypes() {
        BaseResponse response = businessTypesService.getBusinessTypes();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/businesses/business_types/sector/get/{id}")
    ResponseEntity<?> getBusinessTypesBySectorID(@PathVariable long id) {
        BaseResponse response = businessTypesService.getBusinessTypesBySectorID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

//--------------------End--------Business Type -------------------------

    //--------------------Start--------Businesses -------------------------
    @GetMapping("/businesses/business/business_type/get/{id}")
    ResponseEntity<?> getBusinessesByType(@PathVariable long id) {
        BaseResponse response = businessesService.getBusinessesByType(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/businesses/business/business_sector/get/{id}")
    ResponseEntity<?> getBusinessesBySector(@PathVariable long id) {
        BaseResponse response = businessesService.getBusinessesBySector(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/businesses/business/update")
    ResponseEntity<?> editBusinessDetails(@RequestBody Businesses businesses) {
        BaseResponse response = businessesService.editBusinessDetails(businesses);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/businesses/business/getall")
    ResponseEntity<?> getAllBusinesses() {
        BaseResponse response = businessesService.getAllBusinesses();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
//--------------------End--------Businesses -------------------------


    //--------------------Start--------Producers -------------------------

    @PutMapping("/businesses/producer/update")
    ResponseEntity<?> editProducerDetails(@RequestBody Producers producers) {
        BaseResponse response = producersService.editProducersDetails(producers);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/businesses/producer/getall")
    ResponseEntity<?> getAllProducers() {
        BaseResponse response = producersService.getAllProducers();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/businesses/business/organization/get/{id}")
    ResponseEntity<?> getBusinessDetailsByOrganizationId(@PathVariable long id) {
        BaseResponse response = businessesService.getBusinessDetailsByOrganizationId(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
//--------------------End--------Businesses -------------------------


}
