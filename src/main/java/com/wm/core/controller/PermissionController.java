package com.wm.core.controller;

import com.wm.core.model.permission.Permission;
import com.wm.core.model.permission.PermissionGroup;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.service.permission.*;
import com.wm.core.service.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("${app.title}")
public class PermissionController {
    @Autowired
    PermissionGroupService permissionGroupService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    PermissionGroupPermissionsService permissionGroupPermissionsService;

    @Autowired
    UserTypePermissionGroupService userTypePermissionGroupService;

    @Autowired
    GroupTypePermissionGroupService groupTypePermissionGroupService;

    @Autowired
    GroupTypeSpecialPermissionGroupService groupTypeSpecialPermissionGroupService;

    @Autowired
    RolePermissionGroupService rolePermissionGroupService;

    @Autowired
    RoleSpecialPermissionGroupService roleSpecialPermissionGroupService;

    @Autowired
    GroupSpecialPermissionGroupService groupSpecialPermissionGroupService;

    @Autowired
    UserSpecialPermissionGroupService userSpecialPermissionGroupService;

    @Autowired
    GroupPermissionGroupService groupPermissionGroupService;

    @Autowired
    SubGroupPermissionGroupService subGroupPermissionGroupService;

    @Autowired
    SubGroupSpecialPermissionGroupService subGroupSpecialPermissionGroupService;

    @Autowired
    OrganizationSpecialPermissionGroupService organizationSpecialPermissionGroupService;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    GroupTypeService groupTypeService;

    @Autowired
    GroupService groupService;

    @Autowired
    SubGroupService subGroupService;

    //-----------------------Start-----Permission------------------


    @PostMapping(value = "/permissions/permission/create")
    ResponseEntity<?> createPermission(@RequestAttribute("userId") long userid, @Valid @RequestBody Permission permission) {
        BaseResponse response = permissionService.createPermission(permission, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/permissions/permission/delete/{id}")
    ResponseEntity<?> deletePermission(@PathVariable Long id) {
        BaseResponse response = permissionService.deletePermission(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/permissions/permission/getall")
    ResponseEntity<?> getAllPermissions() {
        BaseResponse response = permissionService.getAllPermissions();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/permissions/permission/get/{id}")
    ResponseEntity<?> getPermission(@PathVariable Long id) {
        BaseResponse response = permissionService.getPermissions(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/permissions/permission/update")
    ResponseEntity<?> updatePermission(@Valid @RequestBody Permission permission) {
        BaseResponse response = permissionService.updatePermission(permission);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

//----------------------End------Permission---------------------------

//----------------------Start------Permission-Group------------------------

    @DeleteMapping(value = "/permissions/permissongroup/delete/{id}")
    ResponseEntity<?> deletePermissionGroup(@PathVariable Long id) {
        BaseResponse response = permissionGroupService.deletePermissionGroup(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/permissions/permissiongroup/create")
    ResponseEntity<?> createPermissionGroup(@RequestAttribute("userId") long userid, @Valid @RequestBody PermissionGroup permissionGroup) {
        BaseResponse response = permissionGroupService.createPermissionGroup(permissionGroup, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/permissions/permissiongroup/getall")
    ResponseEntity<?> getAllPermissionGroups() {
        BaseResponse response = permissionGroupService.getAllPermissionGroups();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/permissions/permissiongroup/get/{id}")
    ResponseEntity<?> getPermissionGroup(@PathVariable Long id) {
        BaseResponse response = permissionGroupService.getPermissionGroup(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/permissions/permissiongroup/update")
    ResponseEntity<?> updatePermissionGroup(@RequestAttribute("userId") long userid, @Valid @RequestBody PermissionGroup permissionGroup) {
        BaseResponse response = permissionGroupService.updatePermissionGroup(permissionGroup, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //----------------------End------Permission-Group------------------------

    //----------------------Start------Permission-Group-Permissions-----------------------
    @PostMapping(value = "/permissions/permissiongroup/permission/assign")
    ResponseEntity<?> assignPermissionsToPermissionGroup(@RequestParam long permissionGroupID, @RequestParam List<Long> permissionIDs) {
        BaseResponse response = permissionGroupPermissionsService.assignPermissionsToPermissionGroup(permissionGroupID,
                permissionIDs);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/permissions/permissiongroup/permission/delete")
    ResponseEntity<?> deletePermissionGroupPermission(@RequestParam List<Long> permissionGroupPermissionIds) {
        BaseResponse response = permissionGroupPermissionsService.deletePermissionGroupPermissions(permissionGroupPermissionIds);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/permissions/permissiongroup/permission/get/{id}")
    ResponseEntity<?> getPermissionGroupPermissions(@PathVariable Long id) {
        BaseResponse response = permissionGroupPermissionsService.getPermissionGroupPermissions(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    //----------------------End------Permission-Group-Permissions-----------------------

    //----------------------Start------UserType--Permission-Group-----------------------

    @PostMapping(value = "/permissions/usertype/permissiongroup/assign")
    ResponseEntity<?> assignPermissionGroupsToUserType(@RequestParam long userTypeId, @RequestParam List<Long> permissionGroupIDs) {
        BaseResponse response = userTypePermissionGroupService.assignPermissionGroupsToUserType(userTypeId, permissionGroupIDs);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/permissions/usertype/permissiongroup/get/{id}")
    ResponseEntity<?> getUserTypePermissionGroups(@PathVariable Long id) {
        BaseResponse response = userTypePermissionGroupService.getUserTypePermissionGroups(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/permissions/usertype/permissiongroup/delete")
    ResponseEntity<?> deleteUserTypePermissionGroups(@RequestParam List<Long> usertypePermissionGroupIds) {
        BaseResponse response = userTypePermissionGroupService.deleteUsertypePermissionGroups(usertypePermissionGroupIds);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //----------------------End------UserType--Permission-Group-----------------------

    //----------------------Start------User-Special--Permission-Group-----------------------

    @PostMapping(value = "/permissions/user/special_permissiongroup/assign")
    ResponseEntity<?> assignSpecialPermissionGroupsToUser(@RequestParam long userId, @RequestParam List<Long> permissionGroupIDs, @RequestParam String ActionValue) {
        BaseResponse response = userSpecialPermissionGroupService.assignSpecialPermissionGroupsToUser(userId,
                permissionGroupIDs, ActionValue);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/permissions/user/special_permissiongroup/get/{userId}")
    ResponseEntity<?> getUserSpecialPermissionGroups(@PathVariable Long userId) {
        BaseResponse response = userSpecialPermissionGroupService.getUserSpecialPermissionGroups(userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/permissions/user/special_permissiongroup/delete")
    ResponseEntity<?> deleteUserSpecialPermissionGroups(@RequestParam List<Long> userspecialpermissiongroupIds) {
        BaseResponse response = userSpecialPermissionGroupService.deleteUserSpecialPermissionGroups(userspecialpermissiongroupIds);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/permissions/user/allpermissions/get")
    ResponseEntity<?> getUserAllPermissions(@RequestAttribute("userId") long userId) {
        BaseResponse response = userService.getUserAllPermissions(userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/permissions/user/rolespermissions/get")
    ResponseEntity<?> getAllUserRolesPermissions(@RequestAttribute("userId") long userId) {
        BaseResponse response = userService.getAllUserRolePermissions(userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //----------------------End------User-Special--Permission-Group-----------------------


    //----------------------Start------GroupType--Permission-Group-----------------------

    @PostMapping(value = "/permissions/grouptype/permissiongroup/assign")
    ResponseEntity<?> assignPermissionGroupsToGroupType(@RequestParam long groupTypeId, @RequestParam List<Long> permissionGroupIDs) {
        BaseResponse response = groupTypePermissionGroupService.assignPermissionGroupsToGroupType(groupTypeId, permissionGroupIDs);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/permissions/grouptype/permissiongroup/get/{id}")
    ResponseEntity<?> getGroupTypePermissionGroups(@PathVariable long id) {
        BaseResponse response = groupTypePermissionGroupService.getGroupTypePermissionGroups(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/permissions/grouptype/permissiongroup/delete")
    ResponseEntity<?> deleteGroupTypePermissionGroups(@RequestParam List<Long> grouptypepermgroupIds) {
        BaseResponse response = groupTypePermissionGroupService.deleteGroupTypePermissionGroups(grouptypepermgroupIds);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/permissions/grouptype/allpermissions/get/{id}")
    ResponseEntity<?> getGroupTypeAllPermissions(@PathVariable Long id) {
        BaseResponse response = groupTypeService.getGroupTypeAllPermissions(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //----------------------Start------GroupType--Permission-Group-----------------------


    //----------------------Start------GroupType--Special--Permission-Group-----------------------

    @PostMapping(value = "/permissions/grouptype/special/permissiongroup/assign")
    ResponseEntity<?> assignSpecialPermissionGroupsToGroupType(@RequestParam long groupTypeId, @RequestParam List<Long> permissionGroupIDs, String actionValue) {
        BaseResponse response = groupTypeSpecialPermissionGroupService.assignSpecialPermissionGroupsToGroupType(groupTypeId, permissionGroupIDs, actionValue);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/permissions/grouptype/special/permissiongroup/get/{id}")
    ResponseEntity<?> getGroupTypeSpecialPermissionGroups(@PathVariable long id) {
        BaseResponse response = groupTypeSpecialPermissionGroupService.getGroupTypeSpecialPermissionGroups(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/permissions/grouptype/special/permissiongroups/delete")
    ResponseEntity<?> deleteGroupTypeSpecialPermissionGroups(@RequestParam List<Long> grouptypespecialpermgroupIds) {
        BaseResponse response = groupTypeSpecialPermissionGroupService.deleteGroupTypeSpecialPermissionGroups(grouptypespecialpermgroupIds);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    //----------------------End------GroupType--Special--Permission-Group-----------------------

    //----------------------Start------Group--Permission-Group-----------------------

    @PostMapping(value = "/permissions/group/permissiongroups/assign")
    ResponseEntity<?> assignPermissionGroupsToGroup(@RequestParam long groupId, @RequestParam List<Long> permissionGroupIDs) {
        BaseResponse response = groupPermissionGroupService.assignPermissionGroupsToGroup(groupId, permissionGroupIDs);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/permissions/group/permissiongroups/get/{id}")
    ResponseEntity<?> getGroupPermissionGroups(@PathVariable long id) {
        BaseResponse response = groupPermissionGroupService.getGroupPermissionGroups(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/permissions/group/permissiongroups/delete")
    ResponseEntity<?> deleteGroupPermissionGroups(@RequestParam List<Long> grouppermgroupIds) {
        BaseResponse response = groupPermissionGroupService.deleteGroupPermissionGroups(grouppermgroupIds);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/permissions/group/allpermissions/get/{id}")
    ResponseEntity<?> getGroupAllPermissions(@PathVariable Long id) {
        BaseResponse response = groupService.getGroupAllPermissions(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    //----------------------Start------Group--Permission-Group-----------------------



    //----------------------Start------Group--Special--Permission-Group-----------------------
    @PostMapping(value = "/permissions/group/special/permissiongroup/assign")
    ResponseEntity<?> assignSpecialPermissionGroupsToGroup(@RequestParam long groupId, @RequestParam List<Long> permissionGroupIDs, @RequestParam String ActionValue) {
        BaseResponse response = groupSpecialPermissionGroupService.assignSpecialPermissionGroupsToGroup(groupId,
                permissionGroupIDs, ActionValue);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/permissions/group/special/permissiongroup/get/{id}")
    ResponseEntity<?> getGroupSpecialPermissionGroups(@PathVariable Long id) {
        BaseResponse response = groupSpecialPermissionGroupService.getGroupSpecialPermissionGroups(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/permissions/group/special/permissiongroup/delete")
    ResponseEntity<?> deleteGroupSpecialPermissionGroups(@RequestParam List<Long> groupspecialpermgroupIds) {
        BaseResponse response = groupSpecialPermissionGroupService.deleteGroupSpecialPermissionGroups(groupspecialpermgroupIds);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //----------------------End------Group--Special---Permission-Group-----------------------


    //----------------------Start------SubGroup--Permission-Group-----------------------

    @PostMapping(value = "/permissions/subgroup/permissiongroup/assign")
    ResponseEntity<?> assignPermissionGroupsToSubGroup(@RequestParam long subGroupId, @RequestParam List<Long> permissionGroupIDs) {
        BaseResponse response = subGroupPermissionGroupService.assignPermissionGroupsToSubGroup(subGroupId, permissionGroupIDs);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/permissions/subgroup/permissiongroup/get/{id}")
    ResponseEntity<?> getSubGroupPermissionGroups(@PathVariable long id) {
        BaseResponse response = subGroupPermissionGroupService.getSubGroupPermissionGroups(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/permissions/subgroup/permissiongroup/delete")
    ResponseEntity<?> deleteSubGroupPermissionGroups(@RequestParam List<Long> subgrouppermgroupIds) {
        BaseResponse response = subGroupPermissionGroupService.deleteSubGroupPermissionGroups(subgrouppermgroupIds);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/permissions/subgroup/allpermissions/get/{id}")
    ResponseEntity<?> getSubGroupAllPermissions(@PathVariable Long id) {
        BaseResponse response = subGroupService.getSubGroupAllPermissions(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    //----------------------Start------SubGroup--Permission-Group-----------------------



    //----------------------Start------SubGroup--Special--Permission-Group-----------------------
    @PostMapping(value = "/permissions/subgroup/special/permissiongroup/assign")
    ResponseEntity<?> assignSpecialPermissionGroupsToSubGroup(@RequestParam long subGroupId, @RequestParam List<Long> permissionGroupIDs, @RequestParam String actionValue) {
        BaseResponse response = subGroupSpecialPermissionGroupService.assignSpecialPermissionGroupsToSubGroup(subGroupId, permissionGroupIDs, actionValue);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/permissions/subgroup/special/permissiongroup/get/{id}")
    ResponseEntity<?> getSubGroupSpecialPermissionGroups(@PathVariable Long id) {
        BaseResponse response = subGroupSpecialPermissionGroupService.getSubGroupSpecialPermissionGroups(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/permissions/subgroup/special/permissiongroup/delete")
    ResponseEntity<?> deleteSubGroupSpecialPermissionGroups(@RequestParam List<Long> subgroupspecialpermgroupIds) {
        BaseResponse response = subGroupSpecialPermissionGroupService.deleteSubGroupSpecialPermissionGroups(subgroupspecialpermgroupIds);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //----------------------End------SubGroup--Special---Permission-Group-----------------------

    //----------------------Start------Role---Permission-Group-----------------------
    @PostMapping(value = "/permissions/role/permissiongroup/assign")
    ResponseEntity<?> assignPermissionGroupsToRole(@RequestParam long roleId, @RequestParam List<Long> permissionGroupIDs) {
        BaseResponse response = rolePermissionGroupService.assignPermissionGroupsToRole(roleId, permissionGroupIDs);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/permissions/role/permissiongroup/get/{id}")
    ResponseEntity<?> getRolePermissionGroups(@PathVariable Long id) {
        BaseResponse response = rolePermissionGroupService.getRolePermissionGroups(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/permissions/role/permissiongroup/delete")
    ResponseEntity<?> deleteRolePermissionGroups(@RequestParam List<Long> rolepermgroupIds) {
        BaseResponse response = rolePermissionGroupService.deleteRolePermissionGroups(rolepermgroupIds);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/permissions/role/allpermissions/get/{id}")
    ResponseEntity<?> getRoleAllPermissions(@PathVariable Long id) {
        BaseResponse response = roleService.getRoleAllPermissions(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    //----------------------End------Role---Permission-Group-----------------------

    //----------------------Start------Role--Special---Permission-Group-----------------------

    @PostMapping(value = "/permissions/role/special/permissiongroup/assign")
    ResponseEntity<?> assignSpecialPermissionGroupsToRole(@RequestParam long roleId, @RequestParam List<Long> permissionGroupIDs, @RequestParam String ActionValue) {
        BaseResponse response = roleSpecialPermissionGroupService.assignSpecialPermissionGroupsToRole(roleId,
                permissionGroupIDs, ActionValue);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/permissions/role/special/permissiongroup/get/{id}")
    ResponseEntity<?> getRoleSpecialPermissionGroups(@PathVariable Long id) {
        BaseResponse response = roleSpecialPermissionGroupService.getRoleSpecialPermissionGroups(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/permissions/role/special/permissiongroup/delete")
    ResponseEntity<?> deleteRoleSpecialPermissionGroups(@RequestParam List<Long> rolespecialpermgroupIds) {
        BaseResponse response = roleSpecialPermissionGroupService.deleteRoleSpecialPermissionGroups(rolespecialpermgroupIds);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //----------------------End------Role--Special---Permission-Group-----------------------


    //----------------------Start------Organization---Permission-Group-----------------------

    @GetMapping(value = "/permissions/organization/allpermissions/get/{id}")
    ResponseEntity<?> getOrganizationAllPermissions(@PathVariable Long id) {
        BaseResponse response = organizationService.getOrganizationAllPermissions(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/permissions/organization/permissiongroup/get/{organizationId}")
    ResponseEntity<?> getOrganizationPermissionGroup(@PathVariable Long organizationId) {
        BaseResponse response = permissionGroupService.getOrganizationPermissionGroup(organizationId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    //----------------------End------Organization---Permission-Group-----------------------

    //----------------------Start------Organization--Special--Permission-Group-----------------------

    @PostMapping(value = "/permissions/organization/special/permissiongroup/assign")
    ResponseEntity<?> assignSpecialPermissionGroupsToOrganization(@RequestParam long organizationId, @RequestParam List<Long> permissionGroupIDs, @RequestParam String ActionValue) {
        BaseResponse response = organizationSpecialPermissionGroupService.assignSpecialPermissionGroupsToOrganization(organizationId,
                permissionGroupIDs, ActionValue);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/permissions/organization/special/permissiongroup/get/{id}")
    ResponseEntity<?> getOrganizationSpecialPermissionGroups(@PathVariable Long id) {
        BaseResponse response = organizationSpecialPermissionGroupService.getOrganizationSpecialPermissionGroups(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/permissions/organization/special/permissiongroup/delete")
    ResponseEntity<?> deleteOrganizationSpecialPermissionGroups(@RequestParam List<Long> orgspecialpermgroupIds) {
        BaseResponse response = organizationSpecialPermissionGroupService.deleteOrganizationSpecialPermissionGroups(orgspecialpermgroupIds);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //----------------------End------Organization Special---Permission-Group-----------------------


}
