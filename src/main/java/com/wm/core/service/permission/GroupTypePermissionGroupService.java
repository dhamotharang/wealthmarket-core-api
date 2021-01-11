package com.wm.core.service.permission;

import com.wm.core.model.permission.GroupTypePermissionGroup;
import com.wm.core.model.permission.PermissionGroup;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.GroupType;
import com.wm.core.repository.permission.GroupTypePermissionGroupRepository;
import com.wm.core.repository.permission.PermissionGroupRepository;
import com.wm.core.repository.permission.UserTypePermissionGroupRepository;
import com.wm.core.repository.user.GroupTypeRepository;
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
public class GroupTypePermissionGroupService {
    Logger logger = LoggerFactory.getLogger(GroupTypePermissionGroupService.class.getName());

    @Autowired
    GroupTypeRepository groupTypeRepo;

    @Autowired
    PermissionGroupRepository permissionGroupRepo;

    @Autowired
    UserTypePermissionGroupRepository userTypePermissionGroupRepo;

    @Autowired
    GroupTypePermissionGroupRepository groupTypePermissionGroupRepo;

    public BaseResponse assignPermissionGroupsToGroupType(Long grouptypeId, List<Long> permGroupIDs) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> groupTypePgs = new ArrayList<>();
        if (groupTypeRepo.existsById(grouptypeId)) {
            for (long permgroupid : permGroupIDs) {
                // check the permgroupid
                if (permissionGroupRepo.existsById(permgroupid)) {
                    Optional<GroupTypePermissionGroup> groupTypePermissionGroup = groupTypePermissionGroupRepo.findByGrouptypeIdAndPermissiongroupId(grouptypeId, permgroupid);
                    if (!groupTypePermissionGroup.isPresent()) {
                        GroupTypePermissionGroup newgroupTypePermissionGroup = new GroupTypePermissionGroup(permgroupid, grouptypeId);
                        GroupTypePermissionGroup saved_groupTypePermissionGroup = groupTypePermissionGroupRepo.save(newgroupTypePermissionGroup);
                        groupTypePgs.add(saved_groupTypePermissionGroup);
                    }
                } else {
                    response.setDescription("Permission group does not exist.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }
            }
            if (!groupTypePgs.isEmpty()) {
                response.setData(groupTypePgs);
                response.setDescription("Assign permission group(s) was successful.");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ": {}", groupTypePgs.toString());
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

    public BaseResponse getGroupTypePermissionGroups(long groupTypeID) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> allGTPgs = new ArrayList<>();
        List<GroupTypePermissionGroup> groupTypePermissionGroup = groupTypePermissionGroupRepo.findByGrouptypeId(groupTypeID);
        if (!groupTypePermissionGroup.isEmpty()) {
            for (GroupTypePermissionGroup gtpg : groupTypePermissionGroup) {
                HashMap<String, Object> details = new HashMap<>();
                Optional<PermissionGroup> permissionGroup = permissionGroupRepo.findById(gtpg.getPermissiongroupId());
                details.put("permissiongroup", permissionGroup);

                Optional<GroupType> groupType = groupTypeRepo.findById(gtpg.getGrouptypeId());
                details.put("grouptype", groupType);
                allGTPgs.add(details);
            }
            if (!allGTPgs.isEmpty()) {
                response.setData(allGTPgs);
                response.setDescription("Group type permission groups found successfully.");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ": {}", allGTPgs.toString());
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

    public BaseResponse deleteGroupTypePermissionGroups(List<Long> grouptypepermgroupIds) {
        BaseResponse response = new BaseResponse();
        if (!grouptypepermgroupIds.isEmpty()) {
            for (long grouptypepgid : grouptypepermgroupIds) {
                Optional<GroupTypePermissionGroup> groupTypePermissionGroup = groupTypePermissionGroupRepo.findById(grouptypepgid);
                if (groupTypePermissionGroup.isPresent()) {
                    groupTypePermissionGroupRepo.deleteById(grouptypepgid);
                    response.setDescription("Permission Group has been removed.");
                    response.setStatusCode(HttpServletResponse.SC_OK);
                    logger.info(response.getDescription() + ": {}", groupTypePermissionGroup.toString());
                } else {
                    response.setDescription("No records found.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }
            }
            response.setDescription("Permission Group(s) has been removed.");
            response.setStatusCode(HttpServletResponse.SC_OK);
        } else {
            response.setDescription("No records found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }


}
