package com.wm.core.service.permission;

import com.wm.core.model.permission.PermissionGroup;
import com.wm.core.model.permission.RoleSpecialPermissionGroup;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.repository.permission.PermissionGroupRepository;
import com.wm.core.repository.permission.RoleSpecialPermissionGroupRepository;
import com.wm.core.repository.user.RoleRepository;
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
public class RoleSpecialPermissionGroupService {
    Logger logger = LoggerFactory.getLogger(RoleSpecialPermissionGroupService.class.getName());

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    PermissionGroupRepository permissionGroupRepo;

    @Autowired
    RoleSpecialPermissionGroupRepository roleSpecialPermissionGroupRepo;

    public BaseResponse assignSpecialPermissionGroupsToRole(Long roleId, List<Long> permGroupIDs, String actionValue) {
        final BaseResponse response = new BaseResponse();
        ArrayList<Object> rolespecialperms = new ArrayList<>();
        if (roleRepo.existsById(roleId)) {
            for (long permgroupid : permGroupIDs) {
                if (permissionGroupRepo.existsById(permgroupid)) {
                    Optional<RoleSpecialPermissionGroup> roleSpecialPermissionGroup = roleSpecialPermissionGroupRepo.findByRoleIdAndPermissiongroupId(roleId, permgroupid);
                    if (!roleSpecialPermissionGroup.isPresent()) {
                        RoleSpecialPermissionGroup newroleSpecialPermissionGroup = new RoleSpecialPermissionGroup(roleId, permgroupid, actionValue);
                        RoleSpecialPermissionGroup saved_RoleSpecialPermGroup = roleSpecialPermissionGroupRepo.save(newroleSpecialPermissionGroup);
                        rolespecialperms.add(saved_RoleSpecialPermGroup);
                    }
                } else {
                    response.setDescription("Permission Group not found.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }
            }
            response.setData(rolespecialperms);
            response.setDescription("Assign Special permission group(s) to Role was successful.");
            response.setStatusCode(HttpServletResponse.SC_OK);

            if (!rolespecialperms.isEmpty()) {
                response.setData(rolespecialperms);
                response.setDescription("Assign special permission group(s) was successful.");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ": {}", rolespecialperms.toString());
            } else {
                response.setDescription("Assign permission group(s) not successful. Permission group(s) already been assigned or permission group does not exist.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("No results found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getRoleSpecialPermissionGroups(long roleId) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> roleSpecialPgs = new ArrayList<>();
        if (roleRepo.existsById(roleId)) {
            List<RoleSpecialPermissionGroup> rolePermissionGroups = roleSpecialPermissionGroupRepo.findByRoleId(roleId);
            if (!rolePermissionGroups.isEmpty()) {

                for (RoleSpecialPermissionGroup roleSpecialPermissionGroup : rolePermissionGroups) {
                    HashMap<String, Object> details = new HashMap<>();
                    Optional<PermissionGroup> permissionGroup = permissionGroupRepo.findById(roleSpecialPermissionGroup.getPermissiongroupId());
                    details.put("permissiongroup", permissionGroup);
                    roleSpecialPgs.add(details);
                }
                if (!roleSpecialPgs.isEmpty()) {
                    response.setData(roleSpecialPgs);
                    response.setDescription("Special Permissions Group found successfully.");
                    response.setStatusCode(HttpServletResponse.SC_OK);
                    logger.info(response.getDescription() + ": {}", roleSpecialPgs.toString());
                } else {
                    response.setDescription("No results found.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }
            } else {
                response.setDescription("No results found.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("No results found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;

    }

    public BaseResponse deleteRoleSpecialPermissionGroups(List<Long> rolespecialpermgroupIds) {
        BaseResponse response = new BaseResponse();
        if (!rolespecialpermgroupIds.isEmpty()) {
            for (long rolespepgid : rolespecialpermgroupIds) {
                Optional<RoleSpecialPermissionGroup> rolePermissionGroup = roleSpecialPermissionGroupRepo .findById(rolespepgid);
                if (rolePermissionGroup.isPresent()) {
                    roleSpecialPermissionGroupRepo.deleteById(rolespepgid);
                } else {
                    response.setDescription("No records found.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }
            }
            response.setDescription("Special permission Group has been removed.");
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
