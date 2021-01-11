package com.wm.core.service.permission;

import com.wm.core.model.permission.PermissionGroup;
import com.wm.core.model.permission.SubGroupSpecialPermissionGroup;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.repository.permission.PermissionGroupRepository;
import com.wm.core.repository.permission.SubGroupSpecialPermissionGroupRepository;
import com.wm.core.repository.user.SubGroupRepository;
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
public class SubGroupSpecialPermissionGroupService {
    Logger logger = LoggerFactory.getLogger(SubGroupSpecialPermissionGroupService.class.getName());

    @Autowired
    SubGroupSpecialPermissionGroupRepository subGroupSpecialPermissionGroupRepo;

    @Autowired
    PermissionGroupRepository permissionGroupRepo;

    @Autowired
    SubGroupRepository subGroupRepo;


    public BaseResponse assignSpecialPermissionGroupsToSubGroup(Long subgroupId, List<Long> permGroupIDs, String ActionValue) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> subgroupspecialperm = new ArrayList<>();
        if (subGroupRepo.existsById(subgroupId)) {
            for (long permgroupId : permGroupIDs) {
                // check the permgroupid
                if (permissionGroupRepo.existsById(permgroupId)) {
                    Optional<SubGroupSpecialPermissionGroup> subGroupSpecialPermissionGroup = subGroupSpecialPermissionGroupRepo.findBySubgroupIdAndPermissiongroupId(subgroupId, permgroupId);
                    if (!subGroupSpecialPermissionGroup.isPresent()) {

                        if (ActionValue.equals("Add") || ActionValue.equals("Restrict")) {
                            SubGroupSpecialPermissionGroup newsubGroupSpecialPermissionGroup = new SubGroupSpecialPermissionGroup(permgroupId,subgroupId,ActionValue);
                            SubGroupSpecialPermissionGroup saved_subGroupSpecialPermissionGroup = subGroupSpecialPermissionGroupRepo.save(newsubGroupSpecialPermissionGroup);
                            subgroupspecialperm.add(saved_subGroupSpecialPermissionGroup);
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
            if (!subgroupspecialperm.isEmpty()) {
                response.setData(subgroupspecialperm);
                response.setDescription("Assign special permission group(s) was successful.");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ": {}", subgroupspecialperm.toString());
            } else {
                response.setDescription("Assign permission group(s) not successful. Permission group(s) already been assigned or permission group does not exist.");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("No records found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getSubGroupSpecialPermissionGroups(long subGroupID) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> subGroupSpecialPgs = new ArrayList<>();
        if (subGroupRepo.existsById(subGroupID)) {
            List<SubGroupSpecialPermissionGroup> subGroupSpecialPermissionGroups = subGroupSpecialPermissionGroupRepo.findBySubgroupId(subGroupID);
            if (!subGroupSpecialPermissionGroups.isEmpty()) {
                for (SubGroupSpecialPermissionGroup subgroupspecial : subGroupSpecialPermissionGroups) {
                    HashMap<String, Object> details = new HashMap<>();
                    Optional<PermissionGroup> permissionGroup = permissionGroupRepo.findById(subgroupspecial.getPermissiongroupId());
                    details.put("permissiongroup", permissionGroup);
                    subGroupSpecialPgs.add(details);
                }
                if (!subGroupSpecialPgs.isEmpty()) {
                    response.setData(subGroupSpecialPgs);
                    response.setDescription("User Special permissions Group found successfully.");
                    response.setStatusCode(HttpServletResponse.SC_OK);
                    logger.info(response.getDescription() + ": {}", subGroupSpecialPgs.toString());
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

    public BaseResponse deleteSubGroupSpecialPermissionGroups(List<Long> subgroupspecialpermgroupIds) {
        BaseResponse response = new BaseResponse();
        if (!subgroupspecialpermgroupIds.isEmpty()) {
            for (long subgroupspecialId : subgroupspecialpermgroupIds) {
                Optional<SubGroupSpecialPermissionGroup> subGroupSpecialPermissionGroup = subGroupSpecialPermissionGroupRepo.findById(subgroupspecialId);
                if (subGroupSpecialPermissionGroup.isPresent()) {
                    subGroupSpecialPermissionGroupRepo.deleteById(subgroupspecialId);
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
