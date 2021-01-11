package com.wm.core.service.business;

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
public class BusinessTypesService {
    Logger logger = LoggerFactory.getLogger(BusinessTypesService.class.getName());

    @Autowired
    BusinessSectorRepository businessSectorRepo;

    @Autowired
    BusinessTypeRepository businessTypeRepo;

    @Autowired
    BusinessesService businessesService;

    @Autowired
    UserRepository userRepo;

    public BaseResponse createBusinessType(BusinessType businessType, long userId) {
        BaseResponse response = new BaseResponse();
        User user = userRepo.findById(userId).get();
        businessType.setUserId(user.getId());
        businessType.setDate_created(new Date());
        businessType.setLast_updated(new Date());
        BusinessType created_businessType = businessTypeRepo.save(businessType);
        if (created_businessType != null) {
            response.setStatusCode(HttpServletResponse.SC_OK);
            response.setDescription("Business Type created");
            response.setData(created_businessType);
            logger.info(response.getDescription() + ": {}", created_businessType.toString());
        } else {
            response.setStatusCode(HttpServletResponse.SC_OK);
            response.setDescription("Business Type was not created");
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse deleteBusinessType(long id) {
        BaseResponse response = new BaseResponse();
        Optional<BusinessType> businessType = businessTypeRepo.findById(id);
        if (businessType.isPresent()) {
            businessTypeRepo.deleteById(id);
            response.setDescription("Business Type has been deleted successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", businessType.toString());
        } else {
            response.setDescription("No records found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse editBusinessType(BusinessType businessType, long userId) {
        BaseResponse response = new BaseResponse();
        if (businessTypeRepo.existsById(businessType.getId())) {
            businessType.setLast_updated(new Date());
            businessType.setUserId(userId);
            BusinessType updatedbusinessType = businessTypeRepo.save(businessType);
            response.setData(updatedbusinessType);
            response.setDescription("Business Type updated successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", businessType.toString());
        } else {
            response.setDescription("Business Type not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;
    }

    public BaseResponse getBusinessType(long Id) {
        BaseResponse response = new BaseResponse();
        if (businessTypeRepo.existsById(Id)) {
            Optional<BusinessType> businessType = businessTypeRepo.findById(Id);
            response.setData(businessType.get());
            response.setDescription("businessType found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", businessType.toString());
        } else {
            response.setDescription("businessType not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }


    public BaseResponse getBusinessTypes() {
        BaseResponse response = new BaseResponse();
        List<BusinessType> businessType = businessTypeRepo.findAll();
        if (!businessType.isEmpty()) {
            response.setData(businessType);
            response.setDescription("Business Type found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", businessType.toString());
        } else {
            response.setDescription("Business Type not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getBusinessTypesBySectorID(long sectorid) {
        BaseResponse response = new BaseResponse();
        if (businessSectorRepo.existsById(sectorid)) {
            List<BusinessType> businessType = businessTypeRepo.findByBusinesssectorId(sectorid);
            if (!businessType.isEmpty()) {
                response.setData(businessType);
                response.setDescription("Business Type found successfully");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ": {}", businessType.toString());
            } else {
                response.setDescription("Business Type not found");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("Business Type not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;
    }


}
