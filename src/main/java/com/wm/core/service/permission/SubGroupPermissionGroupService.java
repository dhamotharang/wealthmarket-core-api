package com.wm.core.service.permission;

import com.wm.core.model.permission.PermissionGroup;
import com.wm.core.model.permission.SubGroupPermissionGroup;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.SubGroup;
import com.wm.core.repository.permission.PermissionGroupRepository;
import com.wm.core.repository.permission.SubGroupPermissionGroupRepository;
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
public class SubGroupPermissionGroupService {
    Logger logger = LoggerFactory.getLogger(SubGroupPermissionGroupService.class.getName());

    @Autowired
    SubGroupRepository subGroupRepo;

    @Autowired
    PermissionGroupRepository permissionGroupRepo;


    @Autowired
    SubGroupPermissionGroupRepository subGroupPermissionGroupRepo;


    public BaseResponse assignPermissionGroupsToSubGroup(Long subgroupId, List<Long> permGroupIDs) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> subgroupPgs = new ArrayList<>();
        if (subGroupRepo.existsById(subgroupId)) {
            for (long permgroupid : permGroupIDs) {
                // check the permgroupid
                if (permissionGroupRepo.existsById(permgroupid)) {
                    Optional<SubGroupPermissionGroup> subGroupPermissionGroup = subGroupPermissionGroupRepo.findBySubgroupIdAndPermissiongroupId(subgroupId, permgroupid);
                    if (!subGroupPermissionGroup.isPresent()) {
                        SubGroupPermissionGroup newsubGroupPermissionGroup = new SubGroupPermissionGroup(permgroupid, subgroupId);
                        SubGroupPermissionGroup saved_groupTypePermissionGroup = subGroupPermissionGroupRepo.save(newsubGroupPermissionGroup);
                        subgroupPgs.add(saved_groupTypePermissionGroup);
                    }
                } else {
                    response.setDescription("Permission group does not exist.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }
            }
            if (!subgroupPgs.isEmpty()) {
                response.setData(subgroupPgs);
                response.setDescription("Assign permission group(s) was successful.");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ": {}", subgroupPgs.toString());
            } else {
                response.setDescription("Assign permission group(s) was not successful. It has already been assigned or permission group does not exist.");
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

    public BaseResponse getSubGroupPermissionGroups(long subgroupId) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> allSubGroupPgs = new ArrayList<>();
        List<SubGroupPermissionGroup> subGroupPermissionGroups = subGroupPermissionGroupRepo.findBySubgroupId(subgroupId);
        if (!subGroupPermissionGroups.isEmpty()) {
            for (SubGroupPermissionGroup subgpg : subGroupPermissionGroups) {
                HashMap<String, Object> details = new HashMap<>();
                Optional<PermissionGroup> permissionGroup = permissionGroupRepo.findById(subgpg.getPermissiongroupId());
                details.put("permissiongroup", permissionGroup);

                Optional<SubGroup> subGroup = subGroupRepo.findById(subgpg.getSubgroupId());
                details.put("subgroup", subGroup);
                allSubGroupPgs.add(details);
            }
            if (!allSubGroupPgs.isEmpty()) {
                response.setData(allSubGroupPgs);
                response.setDescription("Group type permission groups found successfully.");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ": {}", allSubGroupPgs.toString());
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

    public BaseResponse deleteSubGroupPermissionGroups(List<Long> subgrouppermgroupIds) {
        BaseResponse response = new BaseResponse();
        if (!subgrouppermgroupIds.isEmpty()) {
            for (long subgrouppgid : subgrouppermgroupIds) {
                Optional<SubGroupPermissionGroup> subGroupPermissionGroup = subGroupPermissionGroupRepo.findById(subgrouppgid);
                if (subGroupPermissionGroup.isPresent()) {
                    subGroupPermissionGroupRepo.deleteById(subgrouppgid);
                    response.setDescription("Permission Group has been removed.");
                    response.setStatusCode(HttpServletResponse.SC_OK);
                    logger.info(response.getDescription() + ": {}", subGroupPermissionGroup.toString());
                } else {
                    response.setDescription("No records found.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }
            }
            response.setDescription("Permission Group(s) has been removed.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription());
        } else {
            response.setDescription("No records found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }


}
