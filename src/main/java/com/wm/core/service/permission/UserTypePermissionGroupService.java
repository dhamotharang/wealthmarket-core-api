package com.wm.core.service.permission;

import com.wm.core.model.permission.PermissionGroup;
import com.wm.core.model.permission.UserTypePermissionGroup;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.UserType;
import com.wm.core.repository.permission.PermissionGroupRepository;
import com.wm.core.repository.permission.PermissionRepository;
import com.wm.core.repository.permission.UserTypePermissionGroupRepository;
import com.wm.core.repository.user.UserTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Service
public class UserTypePermissionGroupService {
    Logger logger = LoggerFactory.getLogger(UserTypePermissionGroupService.class.getName());

    @Autowired
    UserTypeRepository userTypeRepo;

    @Autowired
    PermissionGroupRepository permissionGroupRepo;

    @Autowired
    PermissionRepository permissionRepo;

    @Autowired
    UserTypePermissionGroupRepository userTypePermissionGroupRepo;

    public BaseResponse assignPermissionGroupsToUserType(Long usertypeId, List<Long> permGroupIDs) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> userTypePgs = new ArrayList<>();
        if (userTypeRepo.existsById(usertypeId)) {

            for (long permgroupid : permGroupIDs) {

                if (permissionGroupRepo.existsById(permgroupid)) {
                    Optional<UserTypePermissionGroup> userTypePermissionGroup = userTypePermissionGroupRepo.findByUsertypeIdAndAndPermissiongroupId(usertypeId, permgroupid);
                    if (!userTypePermissionGroup.isPresent()) {
                        UserTypePermissionGroup newuserTypePermissionGroup = new UserTypePermissionGroup(permgroupid, usertypeId);
                        UserTypePermissionGroup savedUsertypePermGroup = userTypePermissionGroupRepo.save(newuserTypePermissionGroup);
                        userTypePgs.add(savedUsertypePermGroup);
                    } else {
                        response.setDescription("Permission group has already been assigned.");
                        response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                        logger.error(response.getDescription());
                    }
                } else {
                    response.setDescription("Permission group does not exsit.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }
            }
            if (!userTypePgs.isEmpty()) {
                response.setData(userTypePgs);
                response.setDescription("Assign permission group(s) to user type was successful.");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ": {}", userTypePgs.toString());
            } else {
                response.setDescription("Assign permission group(s) to user type was not successful. It has already been assigned or permission group does not exist.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("User Type not found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getUserTypePermissionGroups(long usertypeId) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> allUsertypePermGroups = new ArrayList<>();
        List<UserTypePermissionGroup> userTypePermissionGroups = userTypePermissionGroupRepo.findByUsertypeId(usertypeId);
        if (!userTypePermissionGroups.isEmpty()) {
            for (UserTypePermissionGroup utpg : userTypePermissionGroups) {
                HashMap<String, Object> details = new HashMap<>();
                Optional<PermissionGroup> permissionGroup = permissionGroupRepo.findById(utpg.getPermissiongroupId());
                details.put("permissiongroup", permissionGroup);

                Optional<UserType> userType = userTypeRepo.findById(usertypeId);
                details.put("usertype", userType);
                allUsertypePermGroups.add(details);

            }
            if (!allUsertypePermGroups.isEmpty()) {
                response.setData(allUsertypePermGroups);
                response.setDescription("Usertype Permission group(s) found successfully.");
                response.setStatusCode(HttpServletResponse.SC_OK);
            } else {
                response.setDescription("No result found.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            response.setDescription("No result found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;

    }

    public BaseResponse deleteUsertypePermissionGroups(List<Long> userTypePermGroupIds) {
        BaseResponse response = new BaseResponse();
        if (!userTypePermGroupIds.isEmpty()) {
            for (long usertypepgid : userTypePermGroupIds) {

                Optional<UserTypePermissionGroup> userTypePermissionGroup = userTypePermissionGroupRepo.findById(usertypepgid);
                if (userTypePermissionGroup.isPresent()) {
                    userTypePermissionGroupRepo.deleteById(usertypepgid);
                } else {
                    response.setDescription("Could not find Usertype Permission Group.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }
            }
            response.setDescription("Permission Group(s) has been removed from the User type.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription());
        } else {
            response.setDescription("User Type not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }


}
