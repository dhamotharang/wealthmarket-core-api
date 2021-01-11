package com.wm.core.service.permission;

import com.wm.core.model.permission.GroupSpecialPermissionGroup;
import com.wm.core.model.permission.PermissionGroup;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.repository.permission.GroupSpecialPermissionGroupRepository;
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
public class GroupSpecialPermissionGroupService {
    Logger logger = LoggerFactory.getLogger(GroupSpecialPermissionGroupService.class.getName());

    @Autowired
    GroupRepository groupRepo;

    @Autowired
    PermissionGroupRepository permissionGroupRepo;


    @Autowired
    GroupSpecialPermissionGroupRepository groupSpecialPermissionGroupRepo;


    public BaseResponse assignSpecialPermissionGroupsToGroup(Long groupId, List<Long> permGroupIDs, String ActionValue) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> groupspecialperm = new ArrayList<>();
        if (groupRepo.existsById(groupId)) {
            for (long permgroupId : permGroupIDs) {
                // check the permgroupid
                if (permissionGroupRepo.existsById(permgroupId)) {
                    Optional<GroupSpecialPermissionGroup> groupSpecialPermissionGroup = groupSpecialPermissionGroupRepo.findByGroupIdAndPermissiongroupId(groupId, permgroupId);
                    if (!groupSpecialPermissionGroup.isPresent()) {

                        if (ActionValue.equals("Add") || ActionValue.equals("Restrict")) {
                            GroupSpecialPermissionGroup groupSpePermGroup = new GroupSpecialPermissionGroup(permgroupId, groupId, ActionValue);
                            GroupSpecialPermissionGroup saved_groupSpecialPermissionGroup = groupSpecialPermissionGroupRepo.save(groupSpePermGroup);
                            groupspecialperm.add(saved_groupSpecialPermissionGroup);
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
            if (!groupspecialperm.isEmpty()) {
                response.setData(groupspecialperm);
                response.setDescription("Assign special permission group(s) was successful.");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ": {}", groupspecialperm.toString());
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

    public BaseResponse getGroupSpecialPermissionGroups(long groupId) {
        BaseResponse response = new BaseResponse();
        ArrayList<Object> groupSpecialPgs = new ArrayList<>();
        if (groupRepo.existsById(groupId)) {
            List<GroupSpecialPermissionGroup> groupSpecialPermissionGroups = groupSpecialPermissionGroupRepo.findByGroupId(groupId);
            if (!groupSpecialPermissionGroups.isEmpty()) {

                for (GroupSpecialPermissionGroup groupspecialpermgroup : groupSpecialPermissionGroups) {
                    HashMap<String, Object> details = new HashMap<>();
                    Optional<PermissionGroup> permissionGroup = permissionGroupRepo.findById(groupspecialpermgroup.getPermissiongroupId());
                    details.put("permissiongroup", permissionGroup);
                    groupSpecialPgs.add(details);
                }
                if (!groupSpecialPgs.isEmpty()) {
                    response.setData(groupSpecialPgs);
                    response.setDescription("Special permissions Group found successfully.");
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

    public BaseResponse deleteGroupSpecialPermissionGroups(List<Long> groupspecialpermgroupIds) {
        BaseResponse response = new BaseResponse();
        if (!groupspecialpermgroupIds.isEmpty()) {
            for (long groupspepgid : groupspecialpermgroupIds) {
                Optional<GroupSpecialPermissionGroup> groupSpecialPermissionGroup = groupSpecialPermissionGroupRepo.findById(groupspepgid);
                if (groupSpecialPermissionGroup.isPresent()) {
                    groupSpecialPermissionGroupRepo.deleteById(groupspepgid);
                    response.setDescription("Special permission Group has been removed.");
                    response.setStatusCode(HttpServletResponse.SC_OK);
                    logger.info(response.getDescription() + ": {}", groupSpecialPermissionGroup.toString());
                } else {
                    response.setDescription("No records found.");
                    response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
        } else {
            response.setDescription("No records found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }


}
