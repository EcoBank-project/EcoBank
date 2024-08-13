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
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private SnsService snsService;

    // 전체조회
    @GetMapping("admin")
    public String intro(Model model) {
        List<UserVO> userList = adminService.UserList();
        model.addAttribute("userList", userList);
       
        // 오늘 회원가입한 회원 수
        int todaySignUpCount = adminService.getcreaTeat();
        model.addAttribute("todaySignUpCount", todaySignUpCount);
        
        int users = adminService.getusers();
        model.addAttribute("users", users);
        
        return "admins/admin";
    }

    // 총 회원 수
    @GetMapping("adminUser")
    public String getusers(Model model) {
        List<UserVO> userList = adminService.UserList();
        model.addAttribute("userList", userList);
        return "admins/adminUser";
    }

    // 회원 상태 업데이트
    @PostMapping("adminUserUpdate")
    public String updateUserState(@RequestParam String useId, @RequestParam String userState, Model model) {
        int updatedCount = adminService.updateUserState(useId, userState);
        model.addAttribute("updateStatus", updatedCount > 0 ? "성공적으로 업데이트되었습니다." : "업데이트 실패.");
        return "redirect:/adminUser";
    }

    @GetMapping("/ChallDeclareList")
    public String ChallDeclareList(Model model) {
        List<ChallDeclareVO> list = adminService.ChallDeclareList();

        // 중복 제거 (confirmNo를 기준으로)
        List<ChallDeclareVO> uniqueList = list.stream()
            .collect(Collectors.toMap(
                ChallDeclareVO::getConfirmNo, // 키: 인증번호
                challDeclare -> challDeclare,  // 값: ChallDeclareVO 객체
                (existing, replacement) -> existing)) // 중복 시 기존 객체 유지
            .values()
            .stream()
            .collect(Collectors.toList());

        // 신고 횟수 계산
        Map<Integer, Integer> countMap = uniqueList.stream()
                .collect(Collectors.toMap(
                    ChallDeclareVO::getConfirmNo,
                    challDeclare -> adminService.getCountBychallNos(challDeclare.getConfirmNo()),
                    (existing, replacement) -> existing));

        model.addAttribute("countMap", countMap);     
        model.addAttribute("ChallDeclareList", uniqueList);
        return "admins/ChallDeclareList";
    }

    // SNS 댓글 신고 조회
    @GetMapping("SnsReplyDeclareList")
    public String SnsReplyDeclareList(Model model) {
        List<SnsDeclareVO> list = adminService.SnsReplyDeclareList();
        
        // 중복 제거 (replyNo를 기준으로)
        List<SnsDeclareVO> uniqueList = list.stream()
            .collect(Collectors.toMap(
                SnsDeclareVO::getReplyNo, // 키: replyNo (SnsDeclareVO에서 가져옴)
                snsDeclare -> snsDeclare,  // 값: SnsDeclareVO 객체
                (existing, replacement) -> existing)) // 중복 시 기존 객체 유지
            .values()
            .stream()
            .collect(Collectors.toList());
        
        Map<Integer, Integer> countMap = uniqueList.stream()
                .collect(Collectors.toMap(
                    SnsDeclareVO::getReplyNo,
                    snsDeclare -> adminService.getCountByReplyNo(snsDeclare.getReplyNo()),
                    (existing, replacement) -> existing));
        
    
        model.addAttribute("countMap", countMap);
        model.addAttribute("SnsReplyDeclareList", uniqueList); // 중복 제거된 리스트 전달
        return "admins/SnsReplyDeclareList";
    }

    // SNS 신고 조회
    @GetMapping("SnsDeclareList")
    public String SnsDeclareList(@RequestParam(name = "feedNo", required = false, defaultValue = "0") int feedNo, Model model) {
        List<SnsDeclareVO> list = adminService.SnsDeclareList();

        // 중복 제거 (feedNo를 기준으로)
        List<SnsDeclareVO> uniqueList = list.stream()
            .collect(Collectors.toMap(
                SnsDeclareVO::getFeedNo,
                snsDeclare -> snsDeclare,
                (existing, replacement) -> existing))
            .values()
            .stream()
            .collect(Collectors.toList());

     

        model.addAttribute("SnsDeclareList", uniqueList);
        return "redirect:/updateadminSns";
    }
 
    // SNS 업데이트
    @PostMapping("updatefeedState")
    public String updatefeedState(@RequestParam int feedNo, @RequestParam String feedState, Model model) {
        int updatedCount = adminService.updatefeedState(feedNo, feedState);
        model.addAttribute("updatefeedState", updatedCount > 0 ? "성공적으로 업데이트되었습니다." : "업데이트 실패.");
        return "redirect:/adminSns";
    }
}
