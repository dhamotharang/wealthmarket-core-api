package com.wm.core.service.permission;

import com.wm.core.model.permission.Permission;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.User;
import com.wm.core.repository.permission.PermissionRepository;
import com.wm.core.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;


@Service
public class PermissionService {
	Logger logger = LoggerFactory.getLogger(PermissionService.class.getName());

	@Autowired
	PermissionRepository permissionRepo;

	@Autowired
	UserRepository userRepo;

	public BaseResponse getAllPermissions() {
		BaseResponse response = new BaseResponse();
		List<Permission> permissions = permissionRepo.findAll();
		ArrayList<Object> permissionList = new ArrayList<>();
		for (Permission perm : permissions) {
			HashMap<String, String> details = new HashMap<>();
			Optional<User> user = userRepo.findById(perm.getUserId());
			String username = user.get().getFirst_name() + " " + user.get().getLast_name();
			details.put("id", "" + perm.getId());
			details.put("name", perm.getName());
			details.put("created_by", username);
			details.put("date_created", "" + perm.getDate_created());
			details.put("last_updated", "" + perm.getLast_updated());
			permissionList.add(details);
		}

		if (!permissionList.isEmpty()) {
			response.setData(permissionList);
			response.setDescription("permissions found successfully.");
			response.setStatusCode(HttpServletResponse.SC_OK);
			logger.info(response.getDescription() + ": {}", permissionList.toString());
		} else {
			response.setDescription("No permissions found.");
			response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
			logger.error(response.getDescription());
		}
		return response;

	}

	public BaseResponse updatePermission(Permission permission) {
		BaseResponse response = new BaseResponse();
		if (permissionRepo.existsById(permission.getId())) {
			permission.setLast_updated(new Date());
			Permission updatedPermission = permissionRepo.save(permission);
			response.setData(updatedPermission);
			response.setDescription("Permission has been updated successfully.");
			response.setStatusCode(HttpServletResponse.SC_OK);
			logger.info(response.getDescription() + ": {}", updatedPermission.toString());
		} else {
			response.setDescription("New permission was not updated.");
			response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
			logger.error(response.getDescription());
		}
		return response;
	}

	public BaseResponse createPermission(Permission permission, long userid) {
		BaseResponse response = new BaseResponse();
		User user = userRepo.findById(userid).get();
		permission.setUserId(user.getId());
		permission.setDate_created(new Date());
		permission.setLast_updated(new Date());
		permission = permissionRepo.save(permission);
		if (permission != null) {
			response.setData(permission);
			response.setDescription("New Permission has been created successfully.");
			response.setStatusCode(HttpServletResponse.SC_OK);
			logger.info(response.getDescription() + ": {}", permission.toString());
		} else {
			response.setDescription("New permission was not created.");
			response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
			logger.error(response.getDescription());
		}
		return response;
	}

	public BaseResponse getPermissions(Long id) {
		BaseResponse response = new BaseResponse();
		Optional<Permission> permission = permissionRepo.findById(id);
		if (permission.isPresent()) {
			response.setData(permission);
			response.setDescription("permission found successfully.");
			response.setStatusCode(HttpServletResponse.SC_OK);
			logger.info(response.getDescription() + ": {}", permission.toString());
		} else {
			response.setDescription("No result found.");
			response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
			logger.error(response.getDescription());
		}
		return response;

	}

	public BaseResponse deletePermission(Long id) {
		BaseResponse response = new BaseResponse();
		if (permissionRepo.existsById(id)) {
			Permission permission = permissionRepo.getOne(id);
			permissionRepo.deleteById(id);
			response.setDescription("Permission has been deleted successfully");
			response.setStatusCode(HttpServletResponse.SC_OK);
			logger.info(response.getDescription() + ": {}", permission.toString());
		} else {
			response.setDescription("No records found.");
			response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
			logger.error(response.getDescription());
		}
		return response;
	}

}
