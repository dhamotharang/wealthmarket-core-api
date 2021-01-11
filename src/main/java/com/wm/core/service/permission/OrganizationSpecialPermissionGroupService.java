package com.wm.core.service.permission;

import com.wm.core.model.permission.OrganizationSpecialPermissionGroup;
import com.wm.core.model.permission.PermissionGroup;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.repository.permission.OrganizationSpecialPermissionGroupRepository;
import com.wm.core.repository.permission.PermissionGroupRepository;
import com.wm.core.repository.user.OrganizationRepository;
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
public class OrganizationSpecialPermissionGroupService {
    Logger logger = LoggerFactory.getLogger(OrganizationSpecialPermissionGroupService.class.getName());

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    PermissionGroupRepository permissionGroupRepo;


    @Autowired
    OrganizationSpecialPermissionGroupRepository organizationSpecialPermissionGroupRepo;


    public BaseResponse assignSpecialPermissionGroupsToOrganization(Long organizationId, List<Long> permGroupIDs, String ActionValue) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> orgspecialperm = new ArrayList<>();
        if (organizationRepository.existsById(organizationId)) {
            for (long permgroupId : permGroupIDs) {
                // check the permgroupid
                if (permissionGroupRepo.existsById(permgroupId)) {
                    Optional<OrganizationSpecialPermissionGroup> organizationSpecialPermissionGroup = organizationSpecialPermissionGroupRepo.findByOrganizationIdAndPermissiongroupId(organizationId, permgroupId);
                    if (!organizationSpecialPermissionGroup.isPresent()) {
                        if (ActionValue.equals("Add") || ActionValue.equals("Restrict")) {
                            OrganizationSpecialPermissionGroup neworganizationSpecialPermissionGroup = new OrganizationSpecialPermissionGroup(permgroupId,organizationId, ActionValue);
                            OrganizationSpecialPermissionGroup saved_organizationSpecialPermissionGroup = organizationSpecialPermissionGroupRepo.save(neworganizationSpecialPermissionGroup);
                            orgspecialperm.add(saved_organizationSpecialPermissionGroup);
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
            if (!orgspecialperm.isEmpty()) {
                response.setData(orgspecialperm);
                response.setDescription("Assign special permission group(s) was successful.");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ": {}", orgspecialperm.toString());
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

    public BaseResponse getOrganizationSpecialPermissionGroups(long organizationId) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> groupSpecialPgs = new ArrayList<>();
        if (organizationRepository.existsById(organizationId)) {
            List<OrganizationSpecialPermissionGroup> organizationSpecialPermissionGroups = organizationSpecialPermissionGroupRepo.findByOrganizationId(organizationId);

            if (!organizationSpecialPermissionGroups.isEmpty()) {
                for (OrganizationSpecialPermissionGroup orgspecialpermgroup : organizationSpecialPermissionGroups) {
                    HashMap<String, Object> details = new HashMap<>();
                    Optional<PermissionGroup> permissionGroup = permissionGroupRepo.findById(orgspecialpermgroup.getPermissiongroupId());
                    details.put("permissiongroup", permissionGroup);
                    groupSpecialPgs.add(details);
                }
                if (!groupSpecialPgs.isEmpty()) {
                    response.setData(groupSpecialPgs);
                    response.setDescription("Special permissions Group found successfully.");
                    response.setStatusCode(HttpServletResponse.SC_OK);
                    logger.info(response.getDescription() + ": {}", groupSpecialPgs.toString());
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

    public BaseResponse deleteOrganizationSpecialPermissionGroups(List<Long> orgspecialpermgroupIds) {
        BaseResponse response = new BaseResponse();
        if (!orgspecialpermgroupIds.isEmpty()) {
            for (long rolespepgid : orgspecialpermgroupIds) {
                Optional<OrganizationSpecialPermissionGroup> organizationSpecialPermissionGroup  = organizationSpecialPermissionGroupRepo.findById(rolespepgid);
                if (organizationSpecialPermissionGroup.isPresent()) {
                    response.setDescription("Special permission Group has been removed.");
                    response.setStatusCode(HttpServletResponse.SC_OK);
                    organizationSpecialPermissionGroupRepo.deleteById(rolespepgid);
                    logger.info(response.getDescription() + ": {}", organizationSpecialPermissionGroup.toString());
                } else {
                    response.setDescription("No records found.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }
            }
            response.setDescription("Special permission Group has been removed.");
            response.setStatusCode(HttpServletResponse.SC_OK);

        } else {
            response.setDescription("No records found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;
    }


}
