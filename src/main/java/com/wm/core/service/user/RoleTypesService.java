package com.wm.core.service.user;

import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.RoleTypes;
import com.wm.core.model.user.User;
import com.wm.core.repository.user.RoleTypesRepository;
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
public class RoleTypesService {
    Logger logger = LoggerFactory.getLogger(RoleTypesService.class.getName());

    @Autowired
    RoleTypesRepository roleTypesRepo;

    @Autowired
    UserRepository userRepo;


    public BaseResponse createRoleType(RoleTypes roleTypes, long userId) {//System Defined (Default) / User Defined
        BaseResponse response = new BaseResponse();
        try {
            User user = userRepo.findById(userId).get();
            roleTypes.setUserId(user.getId());
            roleTypes.setDate_created(new Date());
            roleTypes.setLast_updated(new Date());
            RoleTypes newType = roleTypesRepo.save(roleTypes);
            response.setData(newType);
            response.setDescription("Role Type created successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", newType.toString());
        } catch (IllegalArgumentException ex) {
            response.setDescription("Role Type not created");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription() + ": {}", ex.getMessage());
        }
        return response;
    }

    public BaseResponse deleteRoleType(long id) {
        BaseResponse response = new BaseResponse();
        Optional<RoleTypes> type = roleTypesRepo.findById(id);
        if (type.isPresent()) {
            roleTypesRepo.deleteById(id);
            response.setDescription("Role Type deleted successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", type.get().toString());
        } else {
            response.setDescription("No roletypes found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse editRoleType(RoleTypes roleTypes, long userId) {
        BaseResponse response = new BaseResponse();
        if (roleTypesRepo.existsById(roleTypes.getId())) {
            roleTypes.setLast_updated(new Date());
            roleTypes.setUserId(userId);
            RoleTypes updated = roleTypesRepo.save(roleTypes);
            response.setData(updated);
            response.setDescription("Role Type updated successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", updated.toString());
        } else {
            response.setDescription("No results found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;
    }

    public BaseResponse getAllRoleTypes() {
        BaseResponse response = new BaseResponse();
        List<RoleTypes> types = roleTypesRepo.findAll();
        if (!types.isEmpty()) {
            response.setData(types);
            response.setDescription("Role Type found successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription());
        } else {
            response.setDescription("No results found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getRoleByID(Long id) {
        BaseResponse response = new BaseResponse();
        Optional<RoleTypes> types = roleTypesRepo.findById(id);
        if (types.isPresent()) {
            response.setData(types);
            response.setDescription("Role Types found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", types.toString());
        } else {
            response.setDescription("No results found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }


}

