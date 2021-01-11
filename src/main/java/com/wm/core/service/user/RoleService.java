package com.wm.core.service.user;

import com.wm.core.model.permission.*;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.MemberOrganizations;
import com.wm.core.model.user.Role;
import com.wm.core.model.user.RoleTypes;
import com.wm.core.model.user.User;
import com.wm.core.repository.permission.*;
import com.wm.core.repository.user.MemberOrganizationsRepository;
import com.wm.core.repository.user.RoleRepository;
import com.wm.core.repository.user.RoleTypesRepository;
import com.wm.core.repository.user.UserRepository;
import com.wm.core.service.permission.PermissionGroupPermissionsService;
import com.wm.core.service.permission.RolePermissionGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class RoleService {
    Logger logger = LoggerFactory.getLogger(RoleService.class.getName());

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    RoleTypesRepository roleTypesRepo;

    @Autowired
    PermissionRepository permissionRepo;

    @Autowired
    RolePermissionGroupRepository rolePermissionGroupRepo;

    @Autowired
    PermissionGroupPermissionsRepository permissionGroupPermissionsRepo;

    @Autowired
    PermissionGroupPermissionsService permissionGroupPermissionsService;

    @Autowired
    RoleSpecialPermissionGroupRepository roleSpecialPermissionGroupRepo;

    @Autowired
    RolePermissionGroupService rolePermissionGroupService;

    @Autowired
    PermissionGroupRepository permissionGroupRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    MemberOrganizationsRepository memberOrganizationRepo;


    public BaseResponse createRole(Role role, long userId) {
        //the user would have to specify the organization and the roletype
        //the admin would have to specify both the org and the roletype or only roletype in the case of system defined(default)
        BaseResponse response = new BaseResponse();
        try {
            User user = userRepo.findById(userId).get();
            role.setUserId(user.getId());
            role.setDate_created(new Date());
            role.setLast_updated(new Date());
            Role newRole = roleRepo.save(role);
            response.setData(newRole);
            response.setDescription("Role created successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", newRole.toString());
        } catch (IllegalArgumentException ex) {
            response.setDescription("Role not created");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    // for creating organization roles defined by a user (organization owner/admin)
    public BaseResponse createOrganizationRoleWithPermissions(Role role, long userId, List<Long> permissionIDs) {

        BaseResponse response = new BaseResponse();
        response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);

        //check if the role has been created by the organization before
        Optional<Role> existingRole = roleRepo.findByOrganizationIdAndName(role.getOrganizationId(), role.getName());
        if (existingRole.isPresent()) {
            response.setDescription("Role already existing for organization.");
            logger.error(response.getDescription());
            return response;
        }

        //create new  role
        role.setUserId(userId);
        role.setDate_created(new Date());
        role.setLast_updated(new Date());
        Role newRole = roleRepo.save(role);

        //create permission group name from role name and organization id
        String name = role.getName() + "-" + role.getOrganizationId();

        //create permission group for role
        PermissionGroup rolePermissionGroup = new PermissionGroup(name, userId, new Date(), new Date(), role.getOrganizationId());
        PermissionGroup createdPermissionGroup = permissionGroupRepo.save(rolePermissionGroup);

        //assign permissions to created permission group
        permissionGroupPermissionsService.assignPermissionsToPermissionGroup(createdPermissionGroup.getId(), permissionIDs);

        //assign permission group to role
        List<Long> permissionGroupIds = new ArrayList<>();
        permissionGroupIds.add(createdPermissionGroup.getId());
        response = rolePermissionGroupService.assignPermissionGroupsToRole(newRole.getId(), permissionGroupIds);

        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            response.setDescription("Role created and permissions assigned successfully.");
            logger.info(response.getDescription() + ": {}", response.getData().toString());
        } else {
            response.setDescription("Error while creating Role and assigning permissions");
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse deleteRole(long id) {
        BaseResponse response = new BaseResponse();
        response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        Optional<Role> role = roleRepo.findById(id);
        if (role.isPresent()) {
            //check if any staff is assigned the role
            List<MemberOrganizations> staffAssignedCurrentRole = memberOrganizationRepo.findByRoleId(id);
            if (staffAssignedCurrentRole.size() > 0) {
                response.setDescription("you cannot delete a role already assigned.");
                logger.error(response.getDescription());
                return response;
            }
            //delete the role if its not assigned to anyone
            roleRepo.deleteById(id);
            response.setDescription("Role deleted successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", role.toString());

        } else {
            response.setDescription("No results found");
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse deleteUserRole(long id) {
        BaseResponse response = new BaseResponse();
        if (roleRepo.existsById(id)) {
            Optional<Role> role = roleRepo.findById(id);
            RoleTypes roleTypes = roleTypesRepo.findById(role.get().getRoletypeId()).get();
            if (roleTypes.getName().equals("User Defined")) {
                roleRepo.deleteById(id);
                response.setDescription("Role deleted successfully.");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription() + ": {}", role.toString());
            } else {
                response.setDescription("Role cannot be deleted");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("No results found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse editRole(Role editedRole, long userId) {
        BaseResponse response = new BaseResponse();
        Optional<Role> role = roleRepo.findById(editedRole.getId());
        if (role.isPresent()) {
            Role realRole = role.get();
            realRole.setName(editedRole.getName());
            realRole.setLast_updated(new Date());
            realRole.setUserId(userId);
            Role updated = roleRepo.save(realRole);
            response.setData(updated);
            response.setDescription("Role updated successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", role.toString());
        } else {
            response.setDescription("role not found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;
    }

    public BaseResponse getAllRoles() {
        BaseResponse response = new BaseResponse();
        List<Role> roles = roleRepo.findAll();
        ArrayList<Object> rolesList = new ArrayList<>();
        for (Role role : roles) {
            HashMap<String, String> details = new HashMap<>();
            Long userID = role.getUserId();

            // if role was created with old model, update it with admin userid
            if (userID == null || userID == 0) {
                userID = 1L;
                role.setDate_created(new Date());
                role.setLast_updated(new Date());
                role.setUserId(userID);
                role = roleRepo.save(role);
            }

            Optional<User> user = userRepo.findById(userID);
            Optional<RoleTypes> roletype = roleTypesRepo.findById(role.getRoletypeId());
            String username = user.get().getFirst_name() + " " + user.get().getLast_name();
            details.put("id", "" + role.getId());
            details.put("name", role.getName());
            details.put("role_type", roletype.get().getName());
            details.put("created_by", username);
            details.put("date_created", "" + role.getDate_created());
            details.put("last_updated", "" + role.getLast_updated());
            rolesList.add(details);
        }
        if (!rolesList.isEmpty()) {
            response.setData(rolesList);
            response.setDescription("Roles found successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription());
        } else {
            response.setDescription("No results found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getRoleByID(Long id) {
        BaseResponse response = new BaseResponse();
        if (roleRepo.existsById(id)) {
            Optional<Role> role = roleRepo.findById(id);
            response.setData(role);
            response.setDescription("Role found successfully.");
            response.setStatusCode(HttpServletResponse.SC_OK);
            logger.info(response.getDescription() + ": {}", role.toString());
        } else {
            response.setDescription("No results found.");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }
        return response;
    }

    public BaseResponse getRolesByRoleTypeID(Long id) {
        BaseResponse response = new BaseResponse();
        if (roleTypesRepo.existsById(id)) {
            List<Role> role = roleRepo.findByRoletypeId(id);
            if (!role.isEmpty()) {
                response.setData(role);
                response.setDescription("Role found successfully");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription());
            } else {
                response.setDescription("No results found");
                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
                logger.error(response.getDescription());
            }
        } else {
            response.setDescription("No results found");
            response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
            logger.error(response.getDescription());
        }

        return response;
    }


    public BaseResponse getRoleAllPermissions(Long roleId) {
        BaseResponse response = new BaseResponse();
        response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);

        Set<Permission> rolePermissions;
        if (roleRepo.existsById(roleId)) {
            rolePermissions = getAllRolePermissions(roleId);
            if (!rolePermissions.isEmpty()) {
                response.setData(rolePermissions);
                response.setDescription("Role Permissions found successfully.");
                response.setStatusCode(HttpServletResponse.SC_OK);
                logger.info(response.getDescription());
            } else {
                response.setDescription("No results found.");
                logger.error(response.getDescription());
            }
        }else{
            response.setDescription("role not found.");
            logger.error(response.getDescription());
        }
        return response;
    }

    public Set<Permission> getAllRolePermissions(Long roleId){
        Set<Permission> permissions = new HashSet<>();
        List<RolePermissionGroup> rolePermissionGroups = rolePermissionGroupRepo.findByRoleId(roleId);
        if (!rolePermissionGroups.isEmpty()) {
            for (RolePermissionGroup rolePermissionGroup : rolePermissionGroups) {
                List<PermissionGroupPermission> permissionGroupPermissions = permissionGroupPermissionsRepo.findByPermissiongroupId(rolePermissionGroup.getPermissiongroupId());
                for (PermissionGroupPermission permissionGroupPermission : permissionGroupPermissions) {
                    Optional<Permission> permission = permissionRepo.findById(permissionGroupPermission.getPermissionId());
                    permissions.add(permission.get());
                }
            }

            List<RoleSpecialPermissionGroup> RoleSpecialPermissionGroup = roleSpecialPermissionGroupRepo.findByRoleId(roleId);
            if (!RoleSpecialPermissionGroup.isEmpty()) {
                for (RoleSpecialPermissionGroup rolespcialpermissiongroup : RoleSpecialPermissionGroup) {
                    String actionvalue = rolespcialpermissiongroup.getAction();
                    List<PermissionGroupPermission> SpepermissionGroupPermissions = permissionGroupPermissionsRepo.findByPermissiongroupId(rolespcialpermissiongroup.getPermissiongroupId());

                    for (PermissionGroupPermission specialPermGroupPerms : SpepermissionGroupPermissions) {
                        Optional<Permission> permission = permissionRepo.findById(specialPermGroupPerms.getPermissionId());
                        if (actionvalue.equals("Add")) {
                            permissions.add(permission.get());
                        } else if (actionvalue.equals("Restrict")) {
                            permissions.remove(permission.get());
                        }
                    }
                }
            }
        }
        return  permissions;
    }

}

