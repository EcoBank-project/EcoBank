package com.ecobank.app.admin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecobank.app.admin.service.AdminService;
import com.ecobank.app.admin.service.ChallDeclareVO;
import com.ecobank.app.admin.service.SnsDeclareVO;
import com.ecobank.app.admin.service.UserVO;
import com.ecobank.app.sns.service.SnsService;
import com.ecobank.app.sns.service.SnsVO;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    // SnsService에 @Autowired 애노테이션 추가
    @Autowired
    SnsService snsService;

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

    // SNS 신고 댓글 조회
    @GetMapping("SnsReplyDeclareList")
    public String SnsReplyDeclareList(Model model) {
        List<SnsDeclareVO> list = adminService.SnsReplyDeclareList();
        model.addAttribute("SnsReplyDeclareList", list);
        return "admins/SnsReplyDeclareList";
    }

    // SNS 신고 조회
    @GetMapping("SnsDeclareList")
    public String SnsDeclareList(Model model) {
        List<SnsDeclareVO> list = adminService.SnsDeclareList();
        model.addAttribute("SnsDeclareList", list);
        return "admins/SnsDeclareList";
    }

    // SNS 신고 삭제
    @PostMapping("snsDeclareDelete")
    public String snsDeclareDelete(@RequestParam int confirmNo) {
        adminService.snsDeclareDelete(confirmNo);
        return "redirect:/SnsDeclareList";
    }

    @PostMapping("snsReplyDeclareDelete")
    public String snsReplyDeclareDelete(@RequestParam int confirmNo) {
        adminService.snsReplyDeclareDelete(confirmNo);
        return "redirect:/SnsReplyDeclareList";
    }

    // SNS 전체 조회
    @GetMapping("adminSns")
    public String adminsnsList(Model model) {
        List<SnsVO> list = snsService.snsList();
        model.addAttribute("adminSns", list);
        return "admins/adminSns";
    }

}
