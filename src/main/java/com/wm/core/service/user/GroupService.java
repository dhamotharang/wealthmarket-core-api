package com.wm.core.service.user;

import com.wm.core.model.permission.GroupPermissionGroup;
import com.wm.core.model.permission.GroupSpecialPermissionGroup;
import com.wm.core.model.permission.Permission;
import com.wm.core.model.permission.PermissionGroupPermission;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.Group;
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
public class GroupService {
    Logger logger = LoggerFactory.getLogger(GroupService.class.getName());

    @Autowired
    GroupRepository groupRepo;

    @Autowired
    GroupPermissionGroupRepository groupPermissionGroupRepo;

    @Autowired
    PermissionGroupPermissionsRepository permissionGroupPermissionsRepo;

    @Autowired
    PermissionGroupRepository permissionGroupRepo;

    @Autowired
    GroupSpecialPermissionGroupRepository groupSpecialPermissionGroupRepo;

    @Autowired
    GroupTypeRepository groupTypeRepo;

    @Autowired
    SubGroupRepository subGroupRepo;

    @Autowired
    PermissionRepository permissionRepo;

    @Autowired
    UserRepository userRepo;

    public BaseResponse createGroup(Group group, long userId) {
        BaseResponse response = new BaseResponse();
        try {
            User user = userRepo.findById(userId).get();
            group.setUserId(user.getId());
            group.setDate_created(new Date());
            group.setLast_updated(new Date());
            Group savedgroup = groupRepo.save(group);
            response.setData(savedgroup);
            response.setDescription("Group created successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", savedgroup.toString());
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            response.setDescription("Group not created");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }


    public BaseResponse deleteGroup(long groupId) {
        BaseResponse response = new BaseResponse();
        if (groupRepo.existsById(groupId)) {
            List<SubGroup> subGroups = subGroupRepo.findByGroupId(groupId);
            if (!subGroups.isEmpty()) {
                subGroupRepo.deleteAll(subGroups);
            }
            groupRepo.deleteById(groupId);
            response.setDescription("Group deleted successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription());
        } else {
            response.setDescription("Group not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }


    public BaseResponse editGroup(Group group, long userId) {
        BaseResponse response = new BaseResponse();
        if (groupRepo.existsById(group.getId())) {
            group.setLast_updated(new Date());
            group.setUserId(userId);
            Group updatedGroup = groupRepo.save(group);
            response.setData(updatedGroup);
            response.setDescription("Group updated successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", updatedGroup.toString());
        } else {
            response.setDescription("Group not found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;
    }

    public BaseResponse getAllGroups() {
        BaseResponse response = new BaseResponse();
        List<Group> groups = groupRepo.findAll();
        if (!groups.isEmpty()) {
            response.setData(groups);
            response.setDescription("Groups found successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription());
        } else {
            response.setDescription("No Groups found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getGroupsByGroupTypeID(long grouptypeId) {
        BaseResponse response = new BaseResponse();
        if (groupTypeRepo.existsById(grouptypeId)) {
            List<Group> groups = groupRepo.findByGrouptypeId(grouptypeId);
            if (!groups.isEmpty()) {
                response.setData(groups);
                response.setDescription("Groups found successfully.");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription());
            } else {
                response.setDescription("No Groups found.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("Group type not found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;
    }

    public BaseResponse getGroupByID(Long id) {
        BaseResponse response = new BaseResponse();
        if (groupRepo.existsById(id)) {
            Optional<Group> group = groupRepo.findById(id);
            response.setData(group);
            response.setDescription("Group found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", group.toString());
        } else {
            response.setDescription("No Groups found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getGroupAllPermissions(Long groupId) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> groupPermissions = new ArrayList<>();

        if (groupRepo.existsById(groupId)) {
            List<GroupPermissionGroup> groupPermissionGroups = groupPermissionGroupRepo.findByGroupId(groupId);
            if (!groupPermissionGroups.isEmpty()) {
                for (GroupPermissionGroup groupPermissionGroup : groupPermissionGroups) {
                    List<PermissionGroupPermission> permissionGroupPermissions = permissionGroupPermissionsRepo.findByPermissiongroupId(groupPermissionGroup.getPermissiongroupId());
                    for (PermissionGroupPermission permissionGroupPermission : permissionGroupPermissions) {
                        Optional<Permission> permission = permissionRepo.findById(permissionGroupPermission.getPermissionId());
                        groupPermissions.add(permission);
                    }
                }
//
                List<GroupSpecialPermissionGroup> groupSpecialPermissionGroups = groupSpecialPermissionGroupRepo.findByGroupId(groupId);
                if (!groupSpecialPermissionGroups.isEmpty()) {
                    for (GroupSpecialPermissionGroup groupSpecialPermissionGroup : groupSpecialPermissionGroups) {
                        String actionvalue = groupSpecialPermissionGroup.getAction();
                        List<PermissionGroupPermission> SpepermissionGroupPermissions = permissionGroupPermissionsRepo.findByPermissiongroupId(groupSpecialPermissionGroup.getPermissiongroupId());

                        for (PermissionGroupPermission specialPermGroupPerms : SpepermissionGroupPermissions) {
                            Optional<Permission> permission = permissionRepo.findById(specialPermGroupPerms.getPermissionId());
                            if (actionvalue.equals("Add")) {
                                groupPermissions.add(permission);
                            } else if (actionvalue.equals("Restrict")) {
                                groupPermissions.remove(permission);
                            }
                        }
                    }
                }
                if(!groupPermissions.isEmpty()){
                    response.setData(groupPermissions);
                    response.setDescription("Permissions found successfully.");
                    response.setStatusCode(HttpServletResponse.SC_OK);
                    logger.info(response.getDescription());
                }else{
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
