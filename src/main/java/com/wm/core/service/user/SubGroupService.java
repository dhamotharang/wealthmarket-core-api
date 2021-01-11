package com.wm.core.service.user;

import com.wm.core.model.permission.Permission;
import com.wm.core.model.permission.PermissionGroupPermission;
import com.wm.core.model.permission.SubGroupPermissionGroup;
import com.wm.core.model.permission.SubGroupSpecialPermissionGroup;
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
import java.util.*;

@Service
public class SubGroupService {
    Logger logger = LoggerFactory.getLogger(SubGroupService.class.getName());

    @Autowired
    GroupTypeRepository groupTypeRepo;

    @Autowired
    GroupRepository groupRepo;

    @Autowired
    SubGroupRepository subGroupRepo;

    @Autowired
    PermissionRepository permissionRepo;

    @Autowired
    SubGroupPermissionGroupRepository subGroupPermissionGroupRepo;

    @Autowired
    PermissionGroupRepository permissionGroupRepo;

    @Autowired
    PermissionGroupPermissionsRepository permissionGroupPermissionsRepo;

    @Autowired
    SubGroupSpecialPermissionGroupRepository subGroupSpecialPermissionGroupRepo;


    @Autowired
    UserRepository userRepo;


    public BaseResponse createSubGroup(SubGroup subGroup, long userId) {
        BaseResponse response = new BaseResponse();
        try {
            User user = userRepo.findById(userId).get();
            subGroup.setUserId(user.getId());
            subGroup.setDate_created(new Date());
            subGroup.setLast_updated(new Date());
            SubGroup savedSubGroup = subGroupRepo.save(subGroup);
            response.setData(savedSubGroup);
            response.setDescription("Sub Group type created successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", savedSubGroup.toString());
        } catch (IllegalArgumentException ex) {
            response.setDescription("Sub Group not created");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.info(response.getDescription() + ": {}", ex.getMessage());

        }
        return response;
    }


    public BaseResponse deleteSubGroup(long id) {
        BaseResponse response = new BaseResponse();
        Optional<SubGroup> type = subGroupRepo.findById(id);
        if (type.isPresent()) {
            subGroupRepo.deleteById(id);
            response.setDescription("Sub Group deleted successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", type.get().toString());
        } else {
            response.setDescription("Sub Group not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse editSubGroup(SubGroup subGroup, long userId) {
        BaseResponse response = new BaseResponse();
        if (subGroupRepo.existsById(subGroup.getId())) {
            subGroup.setLast_updated(new Date());
            subGroup.setUserId(userId);
            SubGroup updatedSubGroup = subGroupRepo.save(subGroup);
            response.setData(updatedSubGroup);
            response.setDescription("Sub Group updated successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", updatedSubGroup.toString());
        } else {
            response.setDescription("Sub Group not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;
    }

    public BaseResponse getAllSubGroups() {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> subgroups = new ArrayList<>();
        List<SubGroup> subGroups = subGroupRepo.findAll();
        if (!subGroups.isEmpty()) {
            for (SubGroup sub : subGroups) {
                HashMap<String, Object> subs = new HashMap<>();
                subs.put("subgroup", sub);
                Optional<Group> group = groupRepo.findById(sub.getGroupId());
                subs.put("group", group);
                Optional<GroupType> groupType = groupTypeRepo.findById(sub.getGrouptypeId());
                subs.put("grouptype", groupType);
                subgroups.add(subs);
            }
            response.setData(subgroups);
            response.setDescription("Sub Groups found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription());
        } else {
            response.setDescription("No Sub Groups found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }


    public BaseResponse getSubGroupByID(Long id) {
        BaseResponse response = new BaseResponse();
        if (subGroupRepo.existsById(id)) {
            Optional<SubGroup> type = subGroupRepo.findById(id);
            response.setData(type);
            response.setDescription("Sub Group found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", type.toString());
        } else {
            response.setDescription("No Sub Groups found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getSubGroupByGroupID(Long groupId) {
        BaseResponse response = new BaseResponse();
        List<SubGroup> subgroups = subGroupRepo.findByGroupId(groupId);
        if (!subgroups.isEmpty()) {
            response.setData(subgroups);
            response.setDescription("Sub Groups found successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", subgroups.toString());
        } else {
            response.setDescription("No Sub Groups found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getSubGroupByGrouptypeID(Long grouptypeId) {
        BaseResponse response = new BaseResponse();
        List<SubGroup> subgroups = subGroupRepo.findByGrouptypeId(grouptypeId);
        if (!subgroups.isEmpty()) {
            response.setData(subgroups);
            response.setDescription("Sub Groups founds successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription());
        } else {
            response.setDescription("No Sub Groups found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getSubGroupAllPermissions(Long subGroupId) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> subGroupPermissions = new ArrayList<>();
        if (subGroupRepo.existsById(subGroupId)) {
            List<SubGroupPermissionGroup> subGroupPermissionGroups = subGroupPermissionGroupRepo.findBySubgroupId(subGroupId);
            if (!subGroupPermissionGroups.isEmpty()) {
                for (SubGroupPermissionGroup subGroupPermissionGroup : subGroupPermissionGroups) {
                    List<PermissionGroupPermission> permissionGroupPermissions = permissionGroupPermissionsRepo.findByPermissiongroupId(subGroupPermissionGroup.getPermissiongroupId());
                    for (PermissionGroupPermission permissionGroupPermission : permissionGroupPermissions) {
                        Optional<Permission> permission = permissionRepo.findById(permissionGroupPermission.getPermissionId());
                        subGroupPermissions.add(permission);
                    }
                }

                List<SubGroupSpecialPermissionGroup> subGroupSpecialPermissionGroups = subGroupSpecialPermissionGroupRepo.findBySubgroupId(subGroupId);
                if (!subGroupSpecialPermissionGroups.isEmpty()) {
                    for (SubGroupSpecialPermissionGroup subGroupSpecialPermissionGroup : subGroupSpecialPermissionGroups) {
                        String actionvalue = subGroupSpecialPermissionGroup.getAction();
                        List<PermissionGroupPermission> SpepermissionGroupPermissions = permissionGroupPermissionsRepo.findByPermissiongroupId(subGroupSpecialPermissionGroup.getPermissiongroupId());

                        for (PermissionGroupPermission specialPermGroupPerms : SpepermissionGroupPermissions) {
                            Optional<Permission> permission = permissionRepo.findById(specialPermGroupPerms.getPermissionId());

                            if (actionvalue.equals("Add")) {
                                subGroupPermissions.add(permission);
                            } else if (actionvalue.equals("Restrict")) {
                                subGroupPermissions.remove(permission);
                            }
                        }
                    }
                }
            }
            if (!subGroupPermissions.isEmpty()) {
                response.setData(subGroupPermissions);
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

        return response;
    }

}
