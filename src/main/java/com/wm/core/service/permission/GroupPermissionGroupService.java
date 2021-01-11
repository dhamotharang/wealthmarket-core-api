package com.wm.core.service.permission;

import com.wm.core.model.permission.GroupPermissionGroup;
import com.wm.core.model.permission.PermissionGroup;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.Group;
import com.wm.core.repository.permission.GroupPermissionGroupRepository;
import com.wm.core.repository.permission.PermissionGroupRepository;
import com.wm.core.repository.user.GroupRepository;
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
public class GroupPermissionGroupService {
    Logger logger = LoggerFactory.getLogger(GroupPermissionGroupService.class.getName());

    @Autowired
    GroupRepository groupRepo;

    @Autowired
    PermissionGroupRepository permissionGroupRepo;


    @Autowired
    GroupPermissionGroupRepository groupPermissionGroupRepo;


    public BaseResponse assignPermissionGroupsToGroup(Long groupId, List<Long> permGroupIDs) {
        BaseResponse response = new BaseResponse();

        ArrayList<Object> groupPgs = new ArrayList<>();

        if (groupRepo.existsById(groupId)) {
            for (long permgroupid : permGroupIDs) {
                // check the permgroupid
                if (permissionGroupRepo.existsById(permgroupid)) {
                    Optional<GroupPermissionGroup> groupPermissionGroup = groupPermissionGroupRepo.findByGroupIdAndAndPermissiongroupId(groupId, permgroupid);
                    if (!groupPermissionGroup.isPresent()) {
                        GroupPermissionGroup newgroupPermissionGroup = new GroupPermissionGroup(permgroupid, groupId);
                        GroupPermissionGroup saved_groupPermissionGroup = groupPermissionGroupRepo.save(newgroupPermissionGroup);
                        groupPgs.add(saved_groupPermissionGroup);
                    }
                } else {
                    response.setDescription("Permission group does not exist.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
            if (!groupPgs.isEmpty()) {
                response.setData(groupPgs);
                response.setDescription("Assign permission group(s) to user type was successful.");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ": {}", groupPgs.toString());
            } else {
                response.setDescription("Assign permission group(s) to user type was not successful. It has already been assigned or permission group does not exist.");
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


    public BaseResponse getGroupPermissionGroups(long groupId) {
        BaseResponse response = new BaseResponse();

        ArrayList<Object> allGroupPgs = new ArrayList<>();
        List<GroupPermissionGroup> groupPermissionGroups = groupPermissionGroupRepo.findByGroupId(groupId);
        if (!groupPermissionGroups.isEmpty()) {
            for (GroupPermissionGroup grouppg : groupPermissionGroups) {
                HashMap<String, Object> details = new HashMap<>();
                Optional<PermissionGroup> permissionGroup = permissionGroupRepo.findById(grouppg.getPermissiongroupId());
                details.put("permissiongroup", permissionGroup);

                Optional<Group> group = groupRepo.findById(grouppg.getGroupId());
                details.put("group", group);
                allGroupPgs.add(details);
            }
            if (!allGroupPgs.isEmpty()) {
                response.setData(allGroupPgs);
                response.setDescription("Group type permission groups found successfully.");
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


    public BaseResponse deleteGroupPermissionGroups(List<Long> grouppermgroupIds) {
        BaseResponse response = new BaseResponse();
        if (!grouppermgroupIds.isEmpty()) {
            for (long grouppgid : grouppermgroupIds) {
                Optional<GroupPermissionGroup> groupPermnGroup = groupPermissionGroupRepo.findById(grouppgid);
                if (groupPermnGroup.isPresent()) {
                    groupPermissionGroupRepo.deleteById(grouppgid);
                    response.setDescription("Permission Group has been removed.");
                    response.setStatusCode(HttpServletResponse.SC_OK);
                    logger.info(response.getDescription() + ": {}", groupPermnGroup.toString());
                } else {
                    response.setDescription("No records found.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                    logger.error(response.getDescription());
                }
            }
            response.setDescription("Permission Group(s) has been removed.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.error(response.getDescription());
        } else {
            response.setDescription("No records found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }


}
