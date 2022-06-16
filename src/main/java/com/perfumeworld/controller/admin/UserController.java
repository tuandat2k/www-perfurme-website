package com.perfumeworld.controller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.perfumeworld.dto.RoleDto;
import com.perfumeworld.dto.UserDto;
import com.perfumeworld.interceptor.Auth;
import com.perfumeworld.interceptor.Auth.Role;
import com.perfumeworld.service.RoleService;
import com.perfumeworld.service.UserService;
import com.perfumeworld.utils.MessageRespone;



@Controller
@RequestMapping(path = "/admin/user")
public class UserController {
    @Autowired
    MessageRespone messageRespone;

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;
    
    @Auth(role = Role.ADMIN)
    @RequestMapping(method = RequestMethod.GET)
    public String viewUser(@RequestParam int page, @RequestParam int maxPageItem,
                           @RequestParam(required = false) String message, ModelMap map) {

        Object[] objects = userService.viewPageUserService((page - 1) * maxPageItem, maxPageItem);

        UserDto userDto = new UserDto();
        Long l = (Long) objects[0];
        int total = l.intValue();
        userDto.customPage(page, maxPageItem, total, (List<UserDto>) objects[1]);

        if (message != null) {
            Map<String, String> mesMap = messageRespone.getMessage(message);
            map.addAttribute("message", mesMap.get("message"));
            map.addAttribute("alert", mesMap.get("alert"));
        }

        map.addAttribute("user", userDto);
        return "admin/user/list";
    }

    @Auth(role = Role.ADMIN)
    @RequestMapping(path = "/edit", method = RequestMethod.GET)
    public String viewEditUser(@RequestParam(value = "message", required = false) String message, ModelMap map) {
        UserDto userDto = new UserDto();

        if (message != null) {
            Map<String, String> mesMap = messageRespone.getMessage(message);
            map.addAttribute("message", mesMap.get("message"));
            map.addAttribute("alert", mesMap.get("alert"));
        }
        map.addAttribute("user", userDto);
        return "admin/user/edit";
    }

    @RequestMapping(path = "/edit/{idUser}", method = RequestMethod.GET)
    public String viewEditUser(@PathVariable int idUser,
                               @RequestParam(value = "message", required = false) String message, ModelMap map) {
        UserDto userDto = new UserDto();
        userDto = userService.findByIdUserService(idUser);
        if (message != null) {
            Map<String, String> mesMap = messageRespone.getMessage(message);
            map.addAttribute("message", mesMap.get("message"));
            map.addAttribute("alert", mesMap.get("alert"));
        }
        map.addAttribute("user", userDto);
        return "admin/user/edit";
    }

    @RequestMapping(path = "/find", method = RequestMethod.POST)
    public String viewFindUser(@ModelAttribute UserDto user, ModelMap map) {
        String[] filter = {"fullName", user.getFullName()};
        Object[] objects = userService.findFilterUserService(filter);
        Long l = (Long) objects[0];
        int total = l.intValue();
        user.setListResult((List<UserDto>) objects[1]);
        map.addAttribute("user", user);
        return "admin/user/find";
    }

    @ModelAttribute("roles")
    public List<RoleDto> getRoles() {
        List<RoleDto> list = roleService.getListRoleService();
        return list;
    }

}
