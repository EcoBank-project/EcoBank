package com.ecobank.app.admin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecobank.app.admin.service.AdminService;
import com.ecobank.app.admin.service.ChallDeclareVO;
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
        
        int users = adminService.getusers();
        model.addAttribute("users", users);
        
        return "admins/admin";
    }
    
    
    
    // 총 회원 수 
    @GetMapping("adminUser")
    public String getusers(Model model) {
    	List<UserVO> list = adminService.UserList();
        model.addAttribute("userList", list);
        return "admins/adminUser";
    }
    
    // 회원 상태 업데이트
    @PostMapping("adminUserUpdate")
    public String updateUserState(@RequestParam String useId, @RequestParam String userState, Model model) {
        int updatedCount = adminService.updateUserState(useId, userState);
        model.addAttribute("updateStatus", updatedCount > 0 ? "성공적으로 업데이트되었습니다." : "업데이트 실패.");
        return "redirect:/adminUser";
    }

    // 챌린지 신고 목록 조회
    @GetMapping("ChallDeclareList")
    public String ChallDeclareList(Model model) {
        List<ChallDeclareVO> list = adminService.ChallDeclareList();
        model.addAttribute("ChallDeclareList", list);
        return "admins/ChallDeclareList";
    }
    
    // 챌린지 신고 삭제
    @PostMapping("deleteChallDeclare")
    public String deleteChallDeclare(@RequestParam int confirmNo) {
        adminService.deleteChallDeclare(confirmNo);
        return "redirect:/ChallDeclareList";
    }
}
