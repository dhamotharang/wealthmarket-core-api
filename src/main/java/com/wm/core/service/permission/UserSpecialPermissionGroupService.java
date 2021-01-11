package com.wm.core.service.permission;

import com.wm.core.model.permission.PermissionGroup;
import com.wm.core.model.permission.UserSpecialPermissionGroup;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.repository.permission.PermissionGroupRepository;
import com.wm.core.repository.permission.UserSpecialPermissionGroupRepository;
import com.wm.core.repository.user.UserRepository;
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
public class UserSpecialPermissionGroupService {
    Logger logger = LoggerFactory.getLogger(UserSpecialPermissionGroupService.class.getName());

    @Autowired
    UserRepository userRepo;

    @Autowired
    PermissionGroupRepository permissionGroupRepo;

    @Autowired
    UserSpecialPermissionGroupRepository userSpecialPermissionGroupRepo;


    public BaseResponse assignSpecialPermissionGroupsToUser(Long userId, List<Long> permGroupIDs, String ActionValue) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> userspecialperm = new ArrayList<>();
        if (userRepo.existsById(userId)) {
            for (long permgroupId : permGroupIDs) {
                // check the permgroupid
                if (permissionGroupRepo.existsById(permgroupId)) {
                    Optional<UserSpecialPermissionGroup> userSpecialPermissionGroup = userSpecialPermissionGroupRepo.findByUserIdAndPermissiongroupId(userId, permgroupId);
                    if (!userSpecialPermissionGroup.isPresent()) {

                        if (ActionValue.equals("Add") || ActionValue.equals("Restrict")) {
                            UserSpecialPermissionGroup newUserSpePermissionGroup = new UserSpecialPermissionGroup(userId, permgroupId, ActionValue);
                            UserSpecialPermissionGroup savedUserSpePermGroup = userSpecialPermissionGroupRepo.save(newUserSpePermissionGroup);
                            userspecialperm.add(savedUserSpePermGroup);
                        } else {
                            response.setDescription("Please, use Add or Restrict.");
                            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                            logger.error(response.getDescription());
                        }
                    } else {
                        response.setDescription("Permission group has already been assigned.");
                        response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                        logger.error(response.getDescription());
                    }
                } else {
                    response.setDescription("Permission Group not found.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }
            }
            if (!userspecialperm.isEmpty()) {
                response.setData(userspecialperm);
                response.setDescription("Assign special permission group(s) was successful.");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ": {}", userspecialperm.toString());
            } else {
                response.setDescription("Assign permission group(s) not successful. Permission group(s) already been assigned or permission group does not exist.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("User not found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;
    }

    public BaseResponse getUserSpecialPermissionGroups(long userId) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> userSpecialPgs = new ArrayList<>();
        if (userRepo.existsById(userId)) {
            List<UserSpecialPermissionGroup> userSpePermissionGroups = userSpecialPermissionGroupRepo.findByUserId(userId);
            if (!userSpePermissionGroups.isEmpty()) {
                for (UserSpecialPermissionGroup userspecial : userSpePermissionGroups) {
                    HashMap<String, Object> details = new HashMap<>();
                    Optional<PermissionGroup> permissionGroup = permissionGroupRepo.findById(userspecial.getPermissiongroupId());
                    details.put("permissiongroup", permissionGroup);
                    userSpecialPgs.add(details);
                }
                if (!userSpecialPgs.isEmpty()) {
                    response.setData(userSpecialPgs);
                    response.setDescription("User Special permissions Group found successfully.");
                    response.setStatusCode(HttpServletResponse.SC_OK);
                    logger.info(response.getDescription() + ": {}", userSpecialPgs.toString());
                } else {
                    response.setDescription("No User Specials found.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }
            } else {
                response.setDescription("No User Specials found.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("No User Specials found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }



        return response;

    }

    public BaseResponse deleteUserSpecialPermissionGroups(List<Long> userspecialpermissiongroupIds) {
        BaseResponse response = new BaseResponse();
        if (!userspecialpermissiongroupIds.isEmpty()) {
            for (long userspepgid : userspecialpermissiongroupIds) {
                Optional<UserSpecialPermissionGroup> userSpePermissionGroup = userSpecialPermissionGroupRepo.findById(userspepgid);
                if (userSpePermissionGroup.isPresent()) {
                    userSpecialPermissionGroupRepo.deleteById(userspepgid);
                } else {
                    response.setDescription("No record found for the specified Group Type");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
            response.setDescription("Special permission Group has been removed from the User.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription());

        } else {
            response.setDescription("No records found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;
    }


}
