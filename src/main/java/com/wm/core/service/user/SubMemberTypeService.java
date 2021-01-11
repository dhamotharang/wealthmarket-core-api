package com.wm.core.service.user;

import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.SubMemberType;
import com.wm.core.model.user.User;
import com.wm.core.repository.user.MemberTypeRepository;
import com.wm.core.repository.user.SubMemberTypeRepository;
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
public class SubMemberTypeService {
    Logger logger = LoggerFactory.getLogger(SubMemberTypeService.class.getName());

    @Autowired
    MemberTypeRepository memberTypeRepo;

    @Autowired
    SubMemberTypeRepository subMemberTypeRepo;


    @Autowired
    UserRepository userRepo;


    public BaseResponse createSubMemberType(SubMemberType subMemberType, long userId) {
        BaseResponse response = new BaseResponse();
        try {
            User user = userRepo.findById(userId).get();
            subMemberType.setUserId(user.getId());
            subMemberType.setDate_created(new Date());
            subMemberType.setLast_updated(new Date());
            SubMemberType subMemberType1 = subMemberTypeRepo.save(subMemberType);
            response.setData(subMemberType1);
            response.setDescription("Sub Member type created successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", subMemberType1.toString());
        } catch (IllegalArgumentException ex) {
            response.setDescription("Sub Member Type not created");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription() + ": {}", ex.getMessage());
        }
        return response;
    }


    public BaseResponse deleteSubMemberType(long id) {
        BaseResponse response = new BaseResponse();
        if (subMemberTypeRepo.existsById(id)) {
            subMemberTypeRepo.deleteById(id);
            response.setDescription("Sub Member type deleted successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription());
        } else {
            response.setDescription("Sub Member type not found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse editSubMemberType(SubMemberType subMemberType, long userId) {
        BaseResponse response = new BaseResponse();
        if (subMemberTypeRepo.existsById(subMemberType.getId())) {
            subMemberType.setLast_updated(new Date());
            subMemberType.setUserId(userId);
            SubMemberType updatedSubMemebertype = subMemberTypeRepo.save(subMemberType);
            response.setData(updatedSubMemebertype);
            response.setDescription("Sub Member type updated successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", updatedSubMemebertype.toString());
        } else {
            response.setDescription("Sub Member type not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription() );
        }

        return response;
    }

    public BaseResponse getAllSubMemberType() {
        BaseResponse response = new BaseResponse();
        List<SubMemberType> subMemberTypes = subMemberTypeRepo.findAll();
        if (!subMemberTypes.isEmpty()) {
            response.setData(subMemberTypes);
            response.setDescription("Sub Member types found successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription());
        } else {
            response.setDescription("No Sub Member types found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription() );
        }
        return response;
    }


    public BaseResponse getSubMemberTypeByID(Long id) {
        BaseResponse response = new BaseResponse();
        if (subMemberTypeRepo.existsById(id)) {
            Optional<SubMemberType> type = subMemberTypeRepo.findById(id);
            response.setData(type);
            response.setDescription("Sub Member type found successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", type.toString());
        } else {
            response.setDescription("No Sub Member types found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription() );
        }
        return response;
    }

    public BaseResponse getSubMemberTypeByMemberTypeID(Long membertypeId) {
        BaseResponse response = new BaseResponse();
        List<SubMemberType> subMemberTypes = subMemberTypeRepo.findByMembertypeId(membertypeId);
        if (!subMemberTypes.isEmpty()) {
            response.setData(subMemberTypes);
            response.setDescription("Sub Member Type found successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", subMemberTypes.toString());
        } else {
            response.setDescription("No Sub Member types found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription() );
        }
        return response;
    }


}
