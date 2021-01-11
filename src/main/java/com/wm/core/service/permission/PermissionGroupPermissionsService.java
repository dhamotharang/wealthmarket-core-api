package com.wm.core.service.permission;


import com.wm.core.model.permission.Permission;
import com.wm.core.model.permission.PermissionGroup;
import com.wm.core.model.permission.PermissionGroupPermission;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.repository.permission.PermissionGroupPermissionsRepository;
import com.wm.core.repository.permission.PermissionGroupRepository;
import com.wm.core.repository.permission.PermissionRepository;
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
public class PermissionGroupPermissionsService {
    Logger logger = LoggerFactory.getLogger(PermissionGroupPermissionsService.class.getName());

    @Autowired
    PermissionGroupPermissionsRepository permissionGroupPermissionsRepo;

    @Autowired
    PermissionRepository permissionRepo;

    @Autowired
    PermissionGroupRepository permissionGroupRepo;

    public BaseResponse assignPermissionsToPermissionGroup(Long permGroupID, List<Long> permIDs) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> pgs = new ArrayList<>();
        if (permissionGroupRepo.existsById(permGroupID)) {
            // check the permgroupid;
            for (long permid : permIDs) {
                // check the permid
                if (permissionRepo.existsById(permid)) {

                    Optional<PermissionGroupPermission> existPermGroupPerm = permissionGroupPermissionsRepo.findByPermissiongroupIdAndPermissionId(permGroupID, permid);
                    if (!existPermGroupPerm.isPresent()) {
                        PermissionGroupPermission permissionGroupPermissions = new PermissionGroupPermission();
                        permissionGroupPermissions.setPermissiongroupId(permGroupID);
                        permissionGroupPermissions.setPermissionId(permid);

                        PermissionGroupPermission newpermissionGroupPermission = permissionGroupPermissionsRepo.save(permissionGroupPermissions);
                        pgs.add(newpermissionGroupPermission);

                    } else {
                        response.setDescription("Permission has already been assigned.");
                        response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                        logger.error(response.getDescription());
                    }
                } else {
                    response.setDescription("Permission does not exit.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }
            }
            if (!pgs.isEmpty()) {
                response.setData(pgs);
                response.setDescription("Permission(s) assigned to permission group successfully.");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ": {}", pgs.toString());
            } else {
                response.setDescription("Permission(s) assigned to permission group was not successfully.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }

        } else {
            response.setDescription("Permission group not found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;
    }

    public BaseResponse getPermissionGroupPermissions(long permissionGroupId) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> allPermGroupPerms = new ArrayList<>();
        List<PermissionGroupPermission> permissionGroupPermissions = permissionGroupPermissionsRepo.findByPermissiongroupId(permissionGroupId);

        if (!permissionGroupPermissions.isEmpty()) {

            for (PermissionGroupPermission pgp : permissionGroupPermissions) {
                HashMap<String, Object> pgpdetails = new HashMap<>();

                pgpdetails.put("permissiongrouppermission", pgp);
                Optional<Permission> permission = permissionRepo.findById(pgp.getPermissionId());

                pgpdetails.put("permission", permission);

                Optional<PermissionGroup> permissionGroup = permissionGroupRepo.findById(pgp.getPermissiongroupId());
                pgpdetails.put("permissiongroup", permissionGroup);

                allPermGroupPerms.add(pgpdetails);
            }
            if (!allPermGroupPerms.isEmpty()) {
                response.setData(allPermGroupPerms);
                response.setDescription("Permission group permissions found successfully.");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ": {}", allPermGroupPerms.toString());
            } else {
                response.setDescription("No result found.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }

        } else {
            response.setDescription("No result found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }


        return response;

    }

    public BaseResponse deletePermissionGroupPermissions(List<Long> permissionGroupPermissionIds) {
        BaseResponse response = new BaseResponse();

        if (!permissionGroupPermissionIds.isEmpty()) {
            // check the permgroupid;
            for (long permgppermid : permissionGroupPermissionIds) {
                // check the permid
                Optional<PermissionGroupPermission> permissionGroupPermission = permissionGroupPermissionsRepo.findById(permgppermid);
                if (permissionGroupPermission.isPresent()) {
                    permissionGroupPermissionsRepo.deleteById(permgppermid);
                    response.setDescription("Permission(s) has been removed from the Permission Group.");
                    response.setStatusCode(HttpServletResponse.SC_OK);
                    logger.info(response.getDescription() + ": {}", permissionGroupPermission.toString());
                } else {
                    response.setDescription("Could not find permission group permission.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }
            }
            response.setDescription("Permission(s) has been removed from the Permission Group.");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("Permission group not found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }


}
