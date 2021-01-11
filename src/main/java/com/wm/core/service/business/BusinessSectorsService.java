package com.wm.core.service.business;

import com.wm.core.model.business.BusinessSector;
import com.wm.core.model.business.BusinessType;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.User;
import com.wm.core.repository.business.BusinessSectorRepository;
import com.wm.core.repository.business.BusinessTypeRepository;
import com.wm.core.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BusinessSectorsService {
    Logger logger = LoggerFactory.getLogger(BusinessSectorsService.class.getName());

    @Autowired
    BusinessSectorRepository businessSectorRepo;

    @Autowired
    BusinessTypeRepository businessTypeRepo;

    @Autowired
    UserRepository userRepo;


    public BaseResponse createBusinessSector(BusinessSector businessSector, long userId) {
        BaseResponse response = new BaseResponse();
        User user = userRepo.findById(userId).get();
        businessSector.setUserId(user.getId());
        businessSector.setDate_created(new Date());
        businessSector.setLast_updated(new Date());
        BusinessSector created_businessSector = businessSectorRepo.save(businessSector);
        if (created_businessSector != null) {
            response.setStatusCode(HttpServletResponse.SC_OK);
            response.setDescription("Business Sector created");
            response.setData(created_businessSector);
            logger.info(response.getDescription() + ": {}", created_businessSector.toString());
        } else {
            response.setDescription("Business Sector not created");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }


    public BaseResponse deleteBusinessSector(Long secionId) {
        BaseResponse response = new BaseResponse();
        if (businessSectorRepo.existsById(secionId)) {
            List<BusinessType> businessTypes = businessTypeRepo.findByBusinesssectorId(secionId);
            if(!businessTypes.isEmpty()){
                businessTypeRepo.deleteAll(businessTypes);
            }
            businessSectorRepo.deleteById(secionId);
            response.setDescription("Business Sector has been deleted successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", businessTypes.toString());
        } else {
            response.setDescription("No record found for the specified id");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }


    public BaseResponse editBusinessSector(BusinessSector businessSector, long userId) {
        BaseResponse response = new BaseResponse();
        if (businessSectorRepo.existsById(businessSector.getId())) {
            businessSector.setLast_updated(new Date());
            businessSector.setUserId(userId);
            BusinessSector updatedBusinessSector = businessSectorRepo.save(businessSector);
            response.setData(updatedBusinessSector);
            response.setDescription("Business Sector updated successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", updatedBusinessSector.toString());
        } else {
            response.setDescription("Business Sector not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }


    public BaseResponse getAllBusinessSectors() {
        BaseResponse response = new BaseResponse();
        List<BusinessSector> businessSectors = businessSectorRepo.findAll();
        if (!businessSectors.isEmpty()) {
            response.setData(businessSectors);
            response.setDescription("business sectors found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription());
        } else {
            response.setDescription("no business sectors found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getBusinessSector(long id) {
        BaseResponse response = new BaseResponse();
        if (businessSectorRepo.existsById(id)) {
            Optional<BusinessSector> businessSectors = businessSectorRepo.findById(id);
            response.setData(businessSectors);
            response.setDescription("business sectors found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", businessSectors.toString());
        } else {
            response.setDescription("no business sectors found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }


}
