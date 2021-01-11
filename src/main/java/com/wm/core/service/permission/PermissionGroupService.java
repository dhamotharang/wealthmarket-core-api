package com.wm.core.service.permission;


import com.wm.core.model.permission.PermissionGroup;
import com.wm.core.model.permission.PermissionGroupPermission;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.User;
import com.wm.core.repository.permission.PermissionGroupPermissionsRepository;
import com.wm.core.repository.permission.PermissionGroupRepository;
import com.wm.core.repository.user.OrganizationRepository;
import com.wm.core.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class PermissionGroupService {
    Logger logger = LoggerFactory.getLogger(PermissionGroupService.class.getName());

    @Autowired
    PermissionGroupRepository permissionGroupRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    PermissionGroupPermissionsRepository permissionGroupPermissionsRepo;
    @Autowired
    OrganizationRepository organizationRepo;

    public BaseResponse createPermissionGroup(PermissionGroup permissionGroup, long userid) {
        BaseResponse response = new BaseResponse();
        User user = userRepo.findById(userid).get();
        permissionGroup.setUserId(user.getId());
        permissionGroup.setDate_created(new Date());
        permissionGroup.setLast_updated(new Date());
        permissionGroup = permissionGroupRepo.save(permissionGroup);
        if (permissionGroup != null) {
            response.setData(permissionGroup);
            response.setDescription("New Permission group has been created successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", permissionGroup.toString());
        } else {
            response.setDescription("New permission was not  created.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getAllPermissionGroups() {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> pglist = new ArrayList<>();
        List<PermissionGroup> permissionGroup = permissionGroupRepo.findAll();
        if (!permissionGroup.isEmpty()) {
            for (PermissionGroup permgrp : permissionGroup) {
                HashMap<String, Object> permgroup = new HashMap<>();
                Optional<User> user = userRepo.findById(permgrp.getUserId());
                permgroup.put("user", user);
                permgroup.put("permissiongroup", permgrp);
                pglist.add(permgroup);
            }
            if (!pglist.isEmpty()) {
                response.setData(pglist);
                response.setDescription("permission groups found successfully.");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ": {}", pglist.toString());
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

    public BaseResponse getPermissionGroup(Long id) {
        BaseResponse response = new BaseResponse();
        if (permissionGroupRepo.existsById(id)) {
            HashMap<String, Object> perm = new HashMap<>();
            Optional<PermissionGroup> permissionGroup = permissionGroupRepo.findById(id);
            Optional<User> user = userRepo.findById(permissionGroup.get().getUserId());

            perm.put("permissiongroup", permissionGroup);
            perm.put("user", user);
            response.setData(perm);
            response.setDescription("Permission group found successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", perm.toString());
        } else {
            response.setDescription("No result found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;

    }

    public BaseResponse deletePermissionGroup(Long id) {
        BaseResponse response = new BaseResponse();
        if (permissionGroupRepo.existsById(id)) {
            List<PermissionGroupPermission> permissionGroupPermissions = permissionGroupPermissionsRepo.findByPermissiongroupId(id);
            for (PermissionGroupPermission pgid : permissionGroupPermissions) {
                permissionGroupPermissionsRepo.deleteById(pgid.getId());
            }
            permissionGroupRepo.deleteById(id);
            response.setDescription("Permission group has been deleted successfully");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription());
        } else {
            response.setDescription("No records found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse updatePermissionGroup(PermissionGroup permissionGroup, long userId) {
        BaseResponse response = new BaseResponse();
        if (permissionGroupRepo.existsById(permissionGroup.getId())) {
            permissionGroup.setLast_updated(new Date());
            permissionGroup.setUserId(userId);
            PermissionGroup updatedPermissionGroup = permissionGroupRepo.save(permissionGroup);
            response.setData(updatedPermissionGroup);
            response.setDescription("Permission Group has been updated successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", updatedPermissionGroup.toString());
        } else {
            response.setDescription("Permission group was not updated.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }


    public BaseResponse getOrganizationPermissionGroup(Long organizationId) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> pglist = new ArrayList<>();
        List<PermissionGroup> permissionGroup = permissionGroupRepo.findByOrganizationId(organizationId);
        if (!permissionGroup.isEmpty()) {
            for (PermissionGroup permgrp : permissionGroup) {
                HashMap<String, Object> permgroup = new HashMap<>();
                Optional<User> user = userRepo.findById(permgrp.getUserId());
                permgroup.put("user", user);
                permgroup.put("permissiongroup", permgrp);
                pglist.add(permgroup);
            }
            if (!pglist.isEmpty()) {
                response.setData(pglist);
                response.setDescription("permission groups found successfully.");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ": {}", pglist.toString());
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
