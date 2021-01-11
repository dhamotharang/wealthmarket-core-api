package com.wm.core.service.permission;

import com.wm.core.model.permission.PermissionGroup;
import com.wm.core.model.permission.RolePermissionGroup;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.Role;
import com.wm.core.repository.permission.PermissionGroupRepository;
import com.wm.core.repository.permission.RolePermissionGroupRepository;
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
public class RolePermissionGroupService {
    Logger logger = LoggerFactory.getLogger(RolePermissionGroupService.class.getName());

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    PermissionGroupRepository permissionGroupRepo;

    @Autowired
    RolePermissionGroupRepository rolePermissionGroupRepo;

    public BaseResponse assignPermissionGroupsToRole(Long roleId, List<Long> permGroupIDs) {
        final BaseResponse response = new BaseResponse();
        ArrayList<Object> rolePermGroups = new ArrayList<>();
        if (roleRepo.existsById(roleId)) {
            for (long permgroupid : permGroupIDs) {
                // check the userType;
                if (permissionGroupRepo.existsById(permgroupid)) {
                    Optional<RolePermissionGroup> rolePermissionGroup = rolePermissionGroupRepo.findByRoleIdAndPermissiongroupId(roleId, permgroupid);
                    if (!rolePermissionGroup.isPresent()) {
                        RolePermissionGroup newrolePermissionGroup = new RolePermissionGroup(permgroupid, roleId);
                        RolePermissionGroup saved_RolePermGroup = rolePermissionGroupRepo.save(newrolePermissionGroup);
                        rolePermGroups.add(saved_RolePermGroup);
                    }
                } else {
                    response.setDescription("Permission group does not exist.");
                    response.setStatusCode(HttpServletResponse.SC_OK);
                    logger.error(response.getDescription());
                }
            }
            if (!rolePermGroups.isEmpty()) {
                response.setData(rolePermGroups);
                response.setDescription("Assign permission group(s) was successful.");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ": {}", rolePermGroups.toString());
            } else {
                response.setDescription("Assign permission group(s) was not successful. It has already been assigned or permission group does not exist.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("Selected Role not found.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.error(response.getDescription());
        }

        return response;
    }

    public BaseResponse getRolePermissionGroups(long roleId) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> allRolePGs = new ArrayList<>();
        List<RolePermissionGroup> rolePermissionGroup = rolePermissionGroupRepo.findByRoleId(roleId);
        if (!rolePermissionGroup.isEmpty()) {
            for (RolePermissionGroup rolepg : rolePermissionGroup) {
                HashMap<String, Object> details = new HashMap<>();
                Optional<PermissionGroup> permissionGroup = permissionGroupRepo.findById(rolepg.getPermissiongroupId());
                details.put("permissiongroup", permissionGroup);

                Optional<Role> role = roleRepo.findById(rolepg.getRoleId());
                details.put("role", role);
                allRolePGs.add(details);
            }
            if (!allRolePGs.isEmpty()) {
                response.setData(allRolePGs);
                response.setDescription("Group type permission groups found successfully.");
                response.setStatusCode(HttpServletResponse.SC_OK);
            } else {
                response.setDescription("No results found.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            }

        } else {
            response.setDescription("No results found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }

        return response;

    }

    public BaseResponse deleteRolePermissionGroups(List<Long> rolepermgroupIds) {
        BaseResponse response = new BaseResponse();
        if (!rolepermgroupIds.isEmpty()) {
            for (long rolepgId : rolepermgroupIds) {
                Optional<RolePermissionGroup> rolePermissionGroup = rolePermissionGroupRepo.findById(rolepgId);
                if (rolePermissionGroup.isPresent()) {
                    rolePermissionGroupRepo.deleteById(rolepgId);
                    response.setDescription("Permission Group has been removed.");
                    response.setStatusCode(HttpServletResponse.SC_OK);
                } else {
                    response.setDescription("No records found.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
            response.setDescription("Permission Group(s) has been removed.");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("No records found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }


}
