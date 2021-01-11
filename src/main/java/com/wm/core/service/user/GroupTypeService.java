package com.wm.core.service.user;

import com.wm.core.model.permission.GroupTypePermissionGroup;
import com.wm.core.model.permission.GroupTypeSpecialPermissionGroup;
import com.wm.core.model.permission.Permission;
import com.wm.core.model.permission.PermissionGroupPermission;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.Group;
import com.wm.core.model.user.GroupType;
import com.wm.core.model.user.SubGroup;
import com.wm.core.model.user.User;
import com.wm.core.repository.permission.*;
import com.wm.core.repository.user.GroupRepository;
import com.wm.core.repository.user.GroupTypeRepository;
import com.wm.core.repository.user.SubGroupRepository;
import com.wm.core.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GroupTypeService {
    Logger logger = LoggerFactory.getLogger(GroupTypeService.class.getName());

    @Autowired
    GroupTypeRepository groupTypeRepo;

    @Autowired
    GroupRepository groupRepo;

    @Autowired
    PermissionRepository permissionRepo;

    @Autowired
    SubGroupRepository subGroupRepo;

    @Autowired
    GroupTypePermissionGroupRepository groupTypePermissionGroupRepo;

    @Autowired
    PermissionGroupRepository permissionGroupRepo;

    @Autowired
    PermissionGroupPermissionsRepository permissionGroupPermissionsRepo;

    @Autowired
    GroupTypeSpecialPermissionGroupRepository groupTypeSpecialPermissionGroupRepo;

    @Autowired
    UserRepository userRepo;


    public BaseResponse createGroupType(GroupType groupType, long userId) {
        BaseResponse response = new BaseResponse();
        try {
            User user = userRepo.findById(userId).get();
            groupType.setUserId(user.getId());
            groupType.setDate_created(new Date());
            groupType.setLast_updated(new Date());
            GroupType newType = groupTypeRepo.save(groupType);
            response.setData(newType);
            response.setDescription("Group type created successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", newType.toString());
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            response.setDescription("Group type not created");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }


    public BaseResponse deleteGroupType(long grouptypeId) {
        BaseResponse response = new BaseResponse();
        if (groupTypeRepo.existsById(grouptypeId)) {
            List<Group> groups = groupRepo.findByGrouptypeId(grouptypeId);
            if (!groups.isEmpty()) {
                groupRepo.deleteAll(groups);
            }

            List<SubGroup> subGroups = subGroupRepo.findByGrouptypeId(grouptypeId);
            if (!subGroups.isEmpty()) {
                subGroupRepo.deleteAll(subGroups);
            }

            groupTypeRepo.deleteById(grouptypeId);
            response.setDescription("Group type deleted successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription());
        } else {
            response.setDescription("Group type not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }


    public BaseResponse editGroupType(GroupType groupType, long userId) {
        BaseResponse response = new BaseResponse();
        Optional<GroupType> type = groupTypeRepo.findById(groupType.getId());
        if (type.isPresent()) {
            groupType.setLast_updated(new Date());
            groupType.setUserId(userId);
            GroupType updatedGroup = groupTypeRepo.save(groupType);
            response.setData(updatedGroup);
            response.setDescription("Group type updated successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", updatedGroup.toString());
        } else {
            response.setDescription("Group type not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getAllGroupTypes() {
        BaseResponse response = new BaseResponse();
        List<GroupType> groupTypes = groupTypeRepo.findAll();
        if (!groupTypes.isEmpty()) {
            response.setData(groupTypes);
            response.setDescription("Group types found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.error(response.getDescription());
        } else {
            response.setDescription("No Group types found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getGroupTypeByID(Long id) {
        BaseResponse response = new BaseResponse();
        if (groupTypeRepo.existsById(id)) {
            Optional<GroupType> type = groupTypeRepo.findById(id);
            response.setData(type);
            response.setDescription("Group types found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", type.toString());
        } else {
            response.setDescription("No Group types found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getGroupTypeAllPermissions(Long groupTypeId) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> grouptypePermissions = new ArrayList<>();
        if (groupTypeRepo.existsById(groupTypeId)) {
            List<GroupTypePermissionGroup> groupTypePermissionGroups = groupTypePermissionGroupRepo.findByGrouptypeId(groupTypeId);
            if (!groupTypePermissionGroups.isEmpty()) {
                for (GroupTypePermissionGroup groupTypePermissionGroup : groupTypePermissionGroups) {
                    List<PermissionGroupPermission> permissionGroupPermissions = permissionGroupPermissionsRepo.findByPermissiongroupId(groupTypePermissionGroup.getPermissiongroupId());
                    for (PermissionGroupPermission permissionGroupPermission : permissionGroupPermissions) {
                        Optional<Permission> permission = permissionRepo.findById(permissionGroupPermission.getPermissionId());
                        grouptypePermissions.add(permission);
                    }
                }

                List<GroupTypeSpecialPermissionGroup> groupTypeSpecialPermissionGroups = groupTypeSpecialPermissionGroupRepo.findByGrouptypeId(groupTypeId);
                if (!groupTypeSpecialPermissionGroups.isEmpty()) {
                    for (GroupTypeSpecialPermissionGroup groupTypeSpecialPermissionGroup : groupTypeSpecialPermissionGroups) {
                        String actionvalue = groupTypeSpecialPermissionGroup.getAction();
                        List<PermissionGroupPermission> SpepermissionGroupPermissions = permissionGroupPermissionsRepo.findByPermissiongroupId(groupTypeSpecialPermissionGroup.getPermissiongroupId());

                        for (PermissionGroupPermission specialPermGroupPerms : SpepermissionGroupPermissions) {
                            Optional<Permission> permission = permissionRepo.findById(specialPermGroupPerms.getPermissionId());
                            if (actionvalue.equals("Add")) {
                                grouptypePermissions.add(permission);
                            } else if (actionvalue.equals("Restrict")) {
                                grouptypePermissions.remove(permission);
                            }
                        }
                    }
                }
                if (!grouptypePermissions.isEmpty()) {
                    response.setData(grouptypePermissions);
                    response.setDescription("Permissions found successfully.");
                    response.setStatusCode(HttpServletResponse.SC_OK);
                    logger.info(response.getDescription());
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

}
