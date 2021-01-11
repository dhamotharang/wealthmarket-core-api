package com.wm.core.service.permission;

import com.wm.core.model.permission.GroupTypeSpecialPermissionGroup;
import com.wm.core.model.permission.PermissionGroup;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.repository.permission.GroupSpecialPermissionGroupRepository;
import com.wm.core.repository.permission.GroupTypeSpecialPermissionGroupRepository;
import com.wm.core.repository.permission.PermissionGroupRepository;
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
public class GroupTypeSpecialPermissionGroupService {
	Logger logger = LoggerFactory.getLogger(GroupTypeSpecialPermissionGroupService.class.getName());

	@Autowired
	GroupTypeSpecialPermissionGroupRepository groupTypeSpecialPermissionGroupRepo;

	@Autowired
	PermissionGroupRepository permissionGroupRepo;

	@Autowired
	GroupTypeRepository groupTypeRepo;

	@Autowired
	GroupSpecialPermissionGroupRepository groupSpecialPermissionGroupRepo;


	public BaseResponse assignSpecialPermissionGroupsToGroupType(Long grouptypeId, List<Long> permGroupIDs, String ActionValue) {
		BaseResponse response = new BaseResponse();
		ArrayList<Object> grouptypepecialperm = new ArrayList<>();
		if (groupTypeRepo.existsById(grouptypeId)) {
			for (long permgroupId : permGroupIDs) {
				// check the permgroupid
				if (permissionGroupRepo.existsById(permgroupId)) {
					Optional<GroupTypeSpecialPermissionGroup> groupTypeSpecialPermissionGroup = groupTypeSpecialPermissionGroupRepo.findByGrouptypeIdAndPermissiongroupId(grouptypeId, permgroupId);
					if (!groupTypeSpecialPermissionGroup.isPresent()) {

						if (ActionValue.equals("Add") || ActionValue.equals("Restrict")) {
							GroupTypeSpecialPermissionGroup newgroupTypeSpecialPermissionGroup = new GroupTypeSpecialPermissionGroup(grouptypeId, permgroupId, ActionValue);
							GroupTypeSpecialPermissionGroup savedgroupTypeSpecialPermissionGroup = groupTypeSpecialPermissionGroupRepo.save(newgroupTypeSpecialPermissionGroup);
							grouptypepecialperm.add(savedgroupTypeSpecialPermissionGroup);
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
			if (!grouptypepecialperm.isEmpty()) {
				response.setData(grouptypepecialperm);
				response.setDescription("Assign special permission group(s) to user was successful.");
				response.setStatusCode(HttpServletResponse.SC_OK);
				logger.info(response.getDescription() + ": {}", grouptypepecialperm.toString());
			} else {
				response.setDescription("Assign permission group(s) not successful. Permission group(s) already been assigned or permission group does not exist.");
				response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
				logger.error(response.getDescription());
			}
		} else {
			response.setDescription("User not found.");
			response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
			logger.error(response.getDescription());
		}
		return response;
	}

	public BaseResponse getGroupTypeSpecialPermissionGroups(long grouptypeId) {
		BaseResponse response = new BaseResponse();
		ArrayList<Object> userSpecialPgs = new ArrayList<>();
		if (groupTypeRepo.existsById(grouptypeId)) {
			List<GroupTypeSpecialPermissionGroup> groupTypePermissionGroup = groupTypeSpecialPermissionGroupRepo.findByGrouptypeId(grouptypeId);
			if (!groupTypePermissionGroup.isEmpty()) {
				for (GroupTypeSpecialPermissionGroup grouptypespecial : groupTypePermissionGroup) {
					HashMap<String, Object> details = new HashMap<>();
					Optional<PermissionGroup> permissionGroup = permissionGroupRepo.findById(grouptypespecial.getPermissiongroupId());
					details.put("permissiongroup", permissionGroup);
					userSpecialPgs.add(details);
				}
				if (!userSpecialPgs.isEmpty()) {
					response.setData(userSpecialPgs);
					response.setDescription("Special permissions Group found successfully.");
					response.setStatusCode(HttpServletResponse.SC_OK);
				} else {
					response.setDescription("No results found.");
					response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
				}
			} else {
				response.setDescription("No results found.");
				response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
			}
		} else {
			response.setDescription("No results found.");
			response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
		}


		return response;

	}

	public BaseResponse deleteGroupTypeSpecialPermissionGroups(List<Long> grouptypespecialpermgroupIds) {
		BaseResponse response = new BaseResponse();
		if (!grouptypespecialpermgroupIds.isEmpty()) {
			for (long grouptypespecialpermgpid : grouptypespecialpermgroupIds) {
				Optional<GroupTypeSpecialPermissionGroup> groupTypeSpecialPermissionGroup = groupTypeSpecialPermissionGroupRepo.findById(grouptypespecialpermgpid);
				if (groupTypeSpecialPermissionGroup.isPresent()) {
					groupTypeSpecialPermissionGroupRepo.deleteById(grouptypespecialpermgpid);
				} else {
					response.setDescription("No record found for the specified Group Type");
					response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
				}
			}
			response.setDescription("Special permission Group has been removed.");
			response.setStatusCode(HttpServletResponse.SC_OK);
		} else {
			response.setDescription("No records found");
			response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
		}
		return response;
	}


}
