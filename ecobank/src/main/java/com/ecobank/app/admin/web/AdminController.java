package com.ecobank.app.admin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecobank.app.admin.service.AdminService;
import com.ecobank.app.admin.service.UserVO;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    // 전체조회
    @GetMapping("admin")
    public String intro(Model model) {
        List<UserVO> list = adminService.UserList();
        model.addAttribute("userList", list);

        int todaySignUpCount = adminService.getcreaTeat();
        model.addAttribute("todaySignUpCount", todaySignUpCount);
        return "admins/admin";
    }

    @GetMapping("adminUser")
    public String userList(Model model) {
        List<UserVO> list = adminService.UserList();
        model.addAttribute("userList", list);
        return "admins/adminUser";
    }

    @PostMapping("adminUserUpdate")
    public String updateUserState(@RequestParam String useId, @RequestParam String userState, Model model) {
        int updatedCount = adminService.updateUserState(useId, userState);
        model.addAttribute("updateStatus", updatedCount > 0 ? "성공적으로 업데이트되었습니다." : "업데이트 실패.");
        return "redirect:/adminUser";
    }
}
