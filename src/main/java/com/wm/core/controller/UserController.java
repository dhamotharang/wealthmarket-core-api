package com.wm.core.controller;

import com.google.i18n.phonenumbers.NumberParseException;
import com.wm.core.model.response.BaseResponse;
import com.wm.core.model.user.*;
import com.wm.core.service.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * @author JOSWAL
 */
@RestController
@RequestMapping("${app.title}")
public class UserController {

    
    Logger logger = LoggerFactory.getLogger(UserController.class.getName());

    @Autowired
    UserService userService;

    @Autowired
    UserTypeService userTypeService;

    @Autowired
    GuestVisitorService guestVisitorService;

    @Autowired
    UserRequestService userRequestService;

    @Autowired
    MemberTypeService memberTypeService;

    @Autowired
    SubMemberTypeService subMemberTypeService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    GroupTypeService groupTypeService;

    @Autowired
    GroupService groupService;

    @Autowired
    RoleTypesService roleTypesService;

    @Autowired
    SubGroupService subGroupService;

    @Autowired
    RoleService roleService;

    @Autowired
    DependantService dependantService;

    @Autowired
    StatusesService statusesService;

    //------------------------------Start--------------UserType----------------------//
    @DeleteMapping("/users/usertypes/delete/{id}")
    ResponseEntity<?> deleteUserType(@PathVariable long id) {
        BaseResponse response = userTypeService.deleteUserType(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/usertypes/edit")
    ResponseEntity<?> editUserType(@RequestAttribute("userId") long userid, @RequestBody UserType userType) {
        BaseResponse response = userTypeService.editUserType(userType, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users/usertypes/create")
    ResponseEntity<?> createUserType(@RequestBody UserType userType) {
        BaseResponse response = userTypeService.createUserType(userType);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/usertypes/getall")
    ResponseEntity<?> getAllUserTypes() {
        BaseResponse response = userTypeService.getUserTypes();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/usertypes/get/{id}")
    ResponseEntity<?> getUserTypes(@PathVariable Long id) {
        BaseResponse response = userTypeService.getUserTypes(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    //------------------------------End--------------UserType----------------------//

    //------------------------------Start--------------GuestVisitor----------------------//
    @PostMapping("/users/guests_visitors/create")
    ResponseEntity<?> createGuestVisitors(@RequestParam String IpAddress, @RequestParam String Location) {
        BaseResponse response = guestVisitorService.createGuest(IpAddress, Location);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/guests_visitors/update")
    ResponseEntity<?> updateGuestVisitor(@RequestParam String IpAddress, @RequestParam String email, @RequestParam String phone) {
        BaseResponse response = guestVisitorService.updateGuestVisitor(IpAddress, email, phone);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //------------------------------End--------------GuestVisitor----------------------//


    //------------------------------Start--------------Dependant----------------------//

    @PostMapping("/users/dependant/create")
    ResponseEntity<?> createDependant(@RequestAttribute("userId") long userId, @RequestParam(name = "parentId", defaultValue = "-1") long parentId, @Valid @RequestBody NewUser newUser) throws NumberParseException {
        BaseResponse response = dependantService.registerDependatnUser(userId, newUser, parentId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/dependants/upgrade/{id}")
    ResponseEntity<?> upgradeDependant(@PathVariable long id) {
        BaseResponse response = dependantService.upgradeDependant(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/dependants/get")
    ResponseEntity<?> getUserDependants(@RequestAttribute("userId") long userId, @RequestParam(name = "parentId", defaultValue = "-1")long parentId) {
        BaseResponse response = dependantService.getUserDependants(userId, parentId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // @DeleteMapping("/users/dependants/delete/{id}")
    // ResponseEntity<?> deleteDependants(@PathVariable long id) {
    //     BaseResponse response = dependantService.deleteDependant(id);
    //     if (response.getStatusCode() == HttpServletResponse.SC_OK) {
    //         return new ResponseEntity<>(response, HttpStatus.OK);
    //     } else {
    //         return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    //     }
    // }
    //------------------------------End--------------Dependant----------------------//


    //------------------------------Start--------------Users----------------------//
    @PostMapping("/users/user/register")
    ResponseEntity<?> register(@Valid @RequestBody NewUser newUser) throws NumberParseException {
        BaseResponse response = userService.registerUser(newUser);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/user/get")
    ResponseEntity<?> getUserDetails(@RequestAttribute("userId") long userId) {
        BaseResponse response = userService.getUserDetails(userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/user/usertype/get/{id}")
    ResponseEntity<?> getUsersByType(@PathVariable long id) {
        BaseResponse response = userService.getUsersByType(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/user/edit_name")
    ResponseEntity<?> UpdateUserName(@RequestAttribute("userId") long id, @RequestParam String firstname, @RequestParam String lastname) {
        BaseResponse response = userService.updateUserName(id, firstname, lastname);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/user/updatepassword")
    ResponseEntity<?> UpdatePassword(@RequestAttribute("userId") long id, @RequestParam String npassword, @RequestParam String opassword) {
        BaseResponse response = userService.setNewPassword(id, npassword, opassword);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/user/block")
    ResponseEntity<?> blockUser(@RequestParam long userId) {
        BaseResponse response = userService.blockUser(userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/user/unblock")
    ResponseEntity<?> unblockUser(@RequestParam long userId) {
        BaseResponse response = userService.unblockUser(userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //to verify user using verification code
    @PostMapping("/users/user/verify")
        //whitelist this endpoint
    ResponseEntity<?> verifyUser(@RequestParam String verificationcode) {
        BaseResponse response = userService.confirmEmailAndActivateUser(verificationcode);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //for the admin to activate a user
    @PutMapping("/users/user/activate")
        //whitelist this endpoint
    ResponseEntity<?> activateUser(@RequestParam long userId) {
        BaseResponse response = userService.activateUser(userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/user/search")
    ResponseEntity<?> FindUser(@RequestParam String searchText) {
        BaseResponse response = userService.searchUsers(searchText);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users/user/password/request")
    ResponseEntity<?> requestPassswordReset(@RequestParam String email) {
        BaseResponse response = userService.sendUserPasswordResetEmail(email);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("users/user/password/reset")
    ResponseEntity<?> setResetPassword(@RequestParam String verificationCode, @RequestParam String newPassword) {
        BaseResponse response = userService.setResetPassword(verificationCode, newPassword);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("users/user/password/change")
    ResponseEntity<?> setNewPassword(@RequestAttribute("userId") long userId, @RequestParam String oldPassword, @RequestParam String newPassword) {
        BaseResponse response = userService.setNewPassword(userId, oldPassword, newPassword);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/user/request_changes/get")
    ResponseEntity<?> getUserRequestedChanges(@RequestAttribute("userId") long userId) {
        BaseResponse response = userRequestService.getUserRequestedChanges(userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/users/user/request_changes/approve_reject/{id}")
    ResponseEntity<?> approveRequestedChange(@PathVariable long id) {
        BaseResponse response = userRequestService.approveOrRejectUserRequest(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/user/request_changes/getall")
    ResponseEntity<?> getRequestedChanges() {
        BaseResponse response = userRequestService.getAllRequestedChanges();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users/user/request_changes/create")
    ResponseEntity<?> RequestToChangeDetails(@RequestAttribute("userId") long id, @RequestBody ChangeRequest details) {
        BaseResponse response = userRequestService.requestDetailsChange(id, details);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/user/organizations/get")
    ResponseEntity<?> GetUserOrganisations(@RequestAttribute("userId") long userId, @RequestParam long grouptypeId) {
        BaseResponse response = organizationService.getUserOrganizations(userId, grouptypeId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/users/user/organizations/getall")
    ResponseEntity<?> GetAllOrganisations() {
        BaseResponse response = organizationService.getAllOrganizations();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/user/organizations/email/activate")
    ResponseEntity<?> validateOrganizationEmail(@RequestParam String verificationcode) {
        BaseResponse response = organizationService.confirmOrganizationEmail(verificationcode);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/organizations/user/get")
    ResponseEntity<?> GetUser_Organisations(@RequestAttribute("userId") long userId) {
        BaseResponse response = organizationService.getUser_Organizations(userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

//------------------------------End--------------Users----------------------//


//------------------------------Start--------------Group Type----------------------//

    @PostMapping("/users/grouptypes/create")
    ResponseEntity<?> createGroupType(@RequestAttribute("userId") long userid, @Valid @RequestBody GroupType groupType) {
        BaseResponse response = groupTypeService.createGroupType(groupType, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/users/grouptypes/delete/{id}")
    ResponseEntity<?> deleteGroupType(@PathVariable long id) {
        BaseResponse response = groupTypeService.deleteGroupType(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/grouptypes/get/{id}")
    ResponseEntity<?> getGroupTypeByID(@PathVariable long id) {
        BaseResponse response = groupTypeService.getGroupTypeByID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/grouptypes/getall")
    ResponseEntity<?> getAllGroupTypes() {
        BaseResponse response = groupTypeService.getAllGroupTypes();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/grouptypes/update")
    ResponseEntity<?> updateGroupType(@RequestAttribute("userId") long userid, @RequestBody GroupType groupType) {
        BaseResponse response = groupTypeService.editGroupType(groupType, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    //------------------------------End--------------GroupType----------------------//


    //------------------------------Start--------------Group----------------------//

    @PostMapping("/users/groups/create")
    ResponseEntity<?> createGroup(@RequestAttribute("userId") long userid, @Valid @RequestBody Group group) {
        BaseResponse response = groupService.createGroup(group, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/users/groups/delete/{id}")
    ResponseEntity<?> deleteGroup(@PathVariable long id) {
        BaseResponse response = groupService.deleteGroup(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/groups/get/{id}")
    ResponseEntity<?> getGroupByID(@PathVariable long id) {
        BaseResponse response = groupService.getGroupByID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/groups/getall")
    ResponseEntity<?> getAllGroups() {
        BaseResponse response = groupService.getAllGroups();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/groups/update")
    ResponseEntity<?> updateGroup(@RequestAttribute("userId") long userid, @RequestBody Group group) {
        BaseResponse response = groupService.editGroup(group, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/groups/grouptype/get/{id}")
    ResponseEntity<?> getGroupsByGroupTypeID(@PathVariable long id) {
        BaseResponse response = groupService.getGroupsByGroupTypeID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //------------------------------End--------------Group----------------------//


    //------------------------------Start--------------SubGroup----------------------//

    @PostMapping("/users/subgroups/create")
    ResponseEntity<?> createSubGroup(@RequestAttribute("userId") long userid, @Valid @RequestBody SubGroup subGroup) {
        BaseResponse response = subGroupService.createSubGroup(subGroup, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/users/subgroups/delete/{id}")
    ResponseEntity<?> deleteSubGroup(@PathVariable long id) {
        BaseResponse response = subGroupService.deleteSubGroup(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/subgroups/get/{id}")
    ResponseEntity<?> getSubGroupByID(@PathVariable long id) {
        BaseResponse response = subGroupService.getSubGroupByID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/subgroups/getall")
    ResponseEntity<?> getAllSubGroups() {
        BaseResponse response = subGroupService.getAllSubGroups();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/subgroups/update")
    ResponseEntity<?> updateSubGroup(@RequestAttribute("userId") long userid, @RequestBody SubGroup subGroup) {
        BaseResponse response = subGroupService.editSubGroup(subGroup, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/subgroups/grouptype/get/{id}")
    ResponseEntity<?> getSubGroupByGrouptypeID(@PathVariable long id) {
        BaseResponse response = subGroupService.getSubGroupByGrouptypeID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/subgroups/group/get/{id}")
    ResponseEntity<?> getSubGroupByGroupID(@PathVariable long id) {
        BaseResponse response = subGroupService.getSubGroupByGroupID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    //------------------------------End--------------SubGroup----------------------//

    //------------------------------Start--------------MemberType----------------------//
    @PostMapping("/users/membertypes/create")
    ResponseEntity<?> createMemberType(@RequestAttribute("userId") long userid, @RequestBody MemberType memberType) {
        BaseResponse response = memberTypeService.createMemberType(memberType, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/users/membertypes/delete/{id}")
    ResponseEntity<?> deleteMemberType(@PathVariable long id) {
        BaseResponse response = memberTypeService.deleteMemberType(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/membertypes/get/{id}")
    ResponseEntity<?> getMemberTypeByID(@PathVariable long id) {
        BaseResponse response = memberTypeService.getMemberTypeByID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/membertypes/getall")
    ResponseEntity<?> getAllMemberTypes() {
        BaseResponse response = memberTypeService.getAllMemberTypes();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/membertypes/update")
    ResponseEntity<?> updateMemberType(@RequestAttribute("userId") long userid, @RequestBody MemberType memberType) {
        BaseResponse response = memberTypeService.editMemberType(memberType, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    //------------------------------End--------------MemberType----------------------//


    //------------------------------Start--------------SubMember Type----------------------//

    @PostMapping("/users/submember_type/create")
    ResponseEntity<?> createSubMemperType(@RequestAttribute("userId") long userid, @Valid @RequestBody SubMemberType subMemberType) {
        BaseResponse response = subMemberTypeService.createSubMemberType(subMemberType, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/users/submember_type/delete/{id}")
    ResponseEntity<?> deleteSubMemberType(@PathVariable long id) {
        BaseResponse response = subMemberTypeService.deleteSubMemberType(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/submember_type/get/{id}")
    ResponseEntity<?> getSubMemberTypeByID(@PathVariable long id) {
        BaseResponse response = subMemberTypeService.getSubMemberTypeByID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/submember_type/getall")
    ResponseEntity<?> getAllSubMemberTypes() {
        BaseResponse response = subMemberTypeService.getAllSubMemberType();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/submember_type/update")
    ResponseEntity<?> updateSubMemberType(@RequestAttribute("userId") long userid, @RequestBody SubMemberType subMemberType) {
        BaseResponse response = subMemberTypeService.editSubMemberType(subMemberType, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/submember_type/membertype/get/{id}")
    ResponseEntity<?> getSubMemberTypeByMemberTypeID(@PathVariable long id) {
        BaseResponse response = subMemberTypeService.getSubMemberTypeByMemberTypeID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //------------------------------End--------------SubGroup----------------------//


    //------------------------------Start--------------Role Type----------------------//
    @PostMapping("/users/roletypes/create")
    ResponseEntity<?> createRoleType(@RequestAttribute("userId") long userid, @Valid @RequestBody RoleTypes roleTypes) {
        BaseResponse response = roleTypesService.createRoleType(roleTypes, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/users/roletypes/delete/{id}")
    ResponseEntity<?> deleteRoleType(@PathVariable long id) {
        BaseResponse response = roleTypesService.deleteRoleType(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/roletypes/get/{id}")
    ResponseEntity<?> getRoleTypeByID(@PathVariable long id) {
        BaseResponse response = roleTypesService.getRoleByID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/roletypes/getall")
    ResponseEntity<?> getAllRoleTypes() {
        BaseResponse response = roleTypesService.getAllRoleTypes();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/roletypes/update")
    ResponseEntity<?> updateRoleType(@RequestAttribute("userId") long userid, @RequestBody RoleTypes roleTypes) {
        BaseResponse response = roleTypesService.editRoleType(roleTypes, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    //------------------------------End--------------Role Type----------------------//

    //------------------------------Start--------------Role ------------------------//

    @PostMapping("/users/roles/create")
    ResponseEntity<?> createRole(@RequestAttribute("userId") long userid, @RequestBody Role role) {
        BaseResponse response = roleService.createRole(role, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/users/roles/delete/{id}")
//for admin
    ResponseEntity<?> deleteRole(@PathVariable long id) {
        BaseResponse response = roleService.deleteRole(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/users/roles/user/delete/{id}")
//for users
    ResponseEntity<?> deleteUserRole(@PathVariable long id) {
        BaseResponse response = roleService.deleteUserRole(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/roles/getall")
    ResponseEntity<?> getAllRoles() {
        BaseResponse response = roleService.getAllRoles();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/roles/get/{id}")
    ResponseEntity<?> getRoleByID(@PathVariable long id) {
        BaseResponse response = roleService.getRoleByID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/roles/update")
    ResponseEntity<?> updateRole(@RequestAttribute("userId") long userid, @RequestBody Role role) {
        BaseResponse response = roleService.editRole(role, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/roles/roletype/get/{id}")
    ResponseEntity<?> getRolesByRoleTypeID(@PathVariable long id) {
        BaseResponse response = roleService.getRolesByRoleTypeID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    //------------------------------End--------------Role ------------------------//


    //------------------------------Start--------------Organisation/Entity----------------------//
    @PostMapping("/users/organizations/create")
    ResponseEntity<?> createOrganisation(@RequestAttribute("userId") long userId, @RequestBody NewOrganization newOrganization) throws NumberParseException {
        BaseResponse response = organizationService.createNewOrganization(userId, newOrganization);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/organizations/getall")
    ResponseEntity<?> getAllOrganizations() {
        BaseResponse response = organizationService.getAllOrganizations();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/organizations/get/{id}")
    ResponseEntity<?> getOrganizationByID(@PathVariable long id) {
        BaseResponse response = organizationService.getOrganizationByID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/organizations/edit")
    ResponseEntity<?> EditOrganization(@RequestParam long organizationId, @RequestParam String Name, @RequestParam String Email, @RequestParam String Phone) {
        BaseResponse response = organizationService.updateOrganization(organizationId, Name, Email, Phone);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/users/organizations/grouptype/get/{id}")
    ResponseEntity<?> getOrganizationsByGroupTypeID(@PathVariable long id) {
        BaseResponse response = organizationService.getOrganizationsByGroupTypeID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/users/organizations/group/get/{id}")
    ResponseEntity<?> getOrganizationsByGroupID(@PathVariable long id) {
        BaseResponse response = organizationService.getOrganizationsByGroupID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/organizations/subgroup/get/{id}")
    ResponseEntity<?> getOrganizationsBySubGroupID(@PathVariable long id) {
        BaseResponse response = organizationService.getOrganizationsBySubGroupID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    //------------Organisation Staff ---Start-----
    @PostMapping("/users/organizations/staff/add")
    ResponseEntity<?> addOrganizationStaff(@RequestParam long organizationId, @RequestParam long userId, @RequestParam long roleId) {
        BaseResponse response = organizationService.addOrganizationStaff(organizationId, userId, roleId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/organizations/staff/delete")
    ResponseEntity<?> removeOrganizationStaff(@RequestParam long organizationId, @RequestParam long userId) {
        BaseResponse response = organizationService.removeOrganizationStaff(organizationId, userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/organizations/staff/status/change")
    ResponseEntity<?> changeOrganizationStaffStatus(@RequestParam long organizationId, @RequestParam long userId, @RequestParam long statusId) {
        BaseResponse response = organizationService.changeOrganizationStaffStatus(organizationId, userId, statusId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/organizations/staff/roles/change")
    ResponseEntity<?> changeOrganizationStaffRole(@RequestParam long organizationId, @RequestParam long userId, @RequestParam long roleId) {
        BaseResponse response = organizationService.changeOrganizationStaffRole(organizationId, userId, roleId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/organizations/staff/get/{id}")
    ResponseEntity<?> getOrganizationStaff(@PathVariable long id) {
        BaseResponse response = organizationService.getOrganizationStaff(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/organizations/staff/user/get/{id}")
    ResponseEntity<?> getUserOrganizationStaffDetails(@RequestAttribute("userId") long userId, @PathVariable long id) {
        BaseResponse response = organizationService.getUserOrganizationStaffDetails(id, userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    //------------Organisation Staff ---End-----

    //------------Organisation Member ---Start-----

    @PostMapping("/users/organizations/member/add")
    ResponseEntity<?> addOrganizationMember(@RequestParam long organizationId, @RequestParam long userId) {
        BaseResponse response = organizationService.addOrganizationMember(organizationId, userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/users/organizations/member/delete")
    ResponseEntity<?> removeOrganizationMember(@RequestParam long organizationId, @RequestParam long userId) {
        BaseResponse response = organizationService.removeOrganizationMember(organizationId, userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/organizations/member/status/change")
    ResponseEntity<?> changeOrganizationMemberStatus(@RequestParam long organizationId, @RequestParam long userId, @RequestParam long statusId) {
        BaseResponse response = organizationService.changeOrganizationMemberStatus(organizationId, userId, statusId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/organizations/member/get/{id}")
    ResponseEntity<?> getOrganizationMembers(@PathVariable long id) {
        BaseResponse response = organizationService.getOrganizationMembers(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/organizations/member/user/get/{id}")
    ResponseEntity<?> getUserOrganizationMemberDetails(@RequestAttribute("userId") long userId, @PathVariable long id) {
        BaseResponse response = organizationService.getUserOrganizationMemberDetails(id, userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    //------------Organisation Member ---End-----

    @PostMapping("/users/organization/roles/create")
    ResponseEntity<?> createOrganizationRoleWithPermissions(@RequestAttribute("userId") long userId, @RequestParam List<Long> permissionIDs, @Valid @RequestBody Role role) {
        BaseResponse response = roleService.createOrganizationRoleWithPermissions(role, userId, permissionIDs);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/organizations/roles/get/{id}")
    ResponseEntity<?> getOrganizationRoles(@PathVariable long id) {
        BaseResponse response = organizationService.getOrganizationRoles(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    //------------------------------End--------------Organisation/Entity----------------------//


    //------------------------------Start--------------Status----------------------//
    @PostMapping("/users/statuses/create")
    ResponseEntity<?> createStatus(@RequestAttribute("userId") long userid, @Valid @RequestBody Status status) {
        BaseResponse response = statusesService.createStatus(status, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/users/statuses/delete/{id}")
    ResponseEntity<?> deleteStatus(@PathVariable long id) {
        BaseResponse response = statusesService.deleteStatus(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/statuses/get/{id}")
    ResponseEntity<?> getStatusByID(@PathVariable long id) {
        BaseResponse response = statusesService.getStatusByID(id);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/statuses/getall")
    ResponseEntity<?> getAllStatuses() {
        BaseResponse response = statusesService.getStatuses();
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/users/statuses/update")
    ResponseEntity<?> updateStatus(@RequestAttribute("userId") long userid, @RequestBody Status status) {
        BaseResponse response = statusesService.editStatus(status, userid);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    //------------------------------End--------------Status----------------------//

    //------------Make SubAdmin ---Start-----
    @PostMapping("/users/subadmin/add")
    ResponseEntity<?> makeSubAdmin(@RequestParam long userId, @RequestParam long roleId) {
        BaseResponse response = organizationService.MakeSubAdmin(userId, roleId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/users/subadmin/delete/{userId}")
    ResponseEntity<?> removeSubAdmin(@PathVariable long userId) {
        BaseResponse response = organizationService.RemoveSubAdmin(userId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/subadmin/get/{roleId}")
    ResponseEntity<?> getAllSubAdmins(@PathVariable long roleId) {
        BaseResponse response = organizationService.GetAllSubAdmins(roleId);
        if (response.getStatusCode() == HttpServletResponse.SC_OK) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


}
