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

@Controller // 이 클래스가 Spring MVC 컨트롤러임을 나타냅니다.
public class AdminController {

    @Autowired // Spring이 자동으로 AdminService의 인스턴스를 주입합니다.
    private AdminService adminService;

    @Autowired // Spring이 자동으로 SnsService의 인스턴스를 주입합니다.
    private SnsService snsService;

    // 전체 조회
    @GetMapping("admin") // 'admin' URL로 GET 요청이 들어오면 이 메서드가 호출됩니다.
    public String intro(Model model) {
        List<UserVO> userList = adminService.UserList(); // 전체 사용자 목록을 조회합니다.
        model.addAttribute("userList", userList); // 조회한 사용자 목록을 뷰에 전달합니다.
       
        // 오늘 회원가입한 회원 수를 조회하여 뷰에 전달합니다.
        int todaySignUpCount = adminService.getcreaTeat();
        model.addAttribute("todaySignUpCount", todaySignUpCount);
        
        // 총 회원 수를 조회하여 뷰에 전달합니다.
        int users = adminService.getusers();
        model.addAttribute("users", users);
        
        return "admins/admin"; // 'admins/admin' 뷰를 반환합니다.
    }

    // 총 회원 수 조회
    @GetMapping("adminUser") // 'adminUser' URL로 GET 요청이 들어오면 이 메서드가 호출됩니다.
    public String getusers(Model model) {
        List<UserVO> userList = adminService.UserList(); // 전체 사용자 목록을 조회합니다.
        model.addAttribute("userList", userList); // 조회한 사용자 목록을 뷰에 전달합니다.
        return "admins/adminUser"; // 'admins/adminUser' 뷰를 반환합니다.
    }

    // 회원 상태 업데이트
    @PostMapping("adminUserUpdate") // 'adminUserUpdate' URL로 POST 요청이 들어오면 이 메서드가 호출됩니다.
    public String updateUserState(@RequestParam String useId, @RequestParam String userState, Model model) {
        // 사용자의 상태를 업데이트합니다.
        int updatedCount = adminService.updateUserState(useId, userState);
        
        // 상태 업데이트 결과 메시지를 뷰에 전달합니다.
        model.addAttribute("updateStatus", updatedCount > 0 ? "성공적으로 업데이트되었습니다." : "업데이트 실패.");
        return "redirect:/adminUser"; // 'adminUser' URL로 리다이렉트합니다.
    }
    
    // 신고된 챌린지 목록 조회
    @GetMapping("ChallDeclareList") // 'ChallDeclareList' URL로 GET 요청이 들어오면 이 메서드가 호출됩니다.
    public String ChallDeclareList(Model model) {
        List<ChallDeclareVO> list = adminService.ChallDeclareList(); // 신고된 챌린지 목록을 조회합니다.

        // 중복된 신고를 제거합니다. (confirmNo 기준)
        List<ChallDeclareVO> uniqueList = list.stream()
            .collect(Collectors.toMap(
                ChallDeclareVO::getConfirmNo, // 키: 인증번호
                challDeclare -> challDeclare,  // 값: ChallDeclareVO 객체
                (existing, replacement) -> existing)) // 중복 시 기존 객체 유지
            .values()
            .stream()
            .collect(Collectors.toList());
        
        // 각 신고된 챌린지의 신고 건수를 맵으로 저장합니다.
        Map<Integer, Integer> countMap = uniqueList.stream()
                .collect(Collectors.toMap(
                    ChallDeclareVO::getConfirmNo,
                    challDeclare -> adminService.getCountBychallNos(challDeclare.getConfirmNo()),
                    (existing, replacement) -> existing));

        // 신고 건수 맵과 신고된 챌린지 목록을 뷰에 전달합니다.
        model.addAttribute("countMap", countMap);     
        model.addAttribute("ChallDeclareList", uniqueList);
        return "admins/ChallDeclareList"; // 'admins/ChallDeclareList' 뷰를 반환합니다.
    }

    // SNS 댓글 신고 목록 조회
    @GetMapping("SnsReplyDeclareList") // 'SnsReplyDeclareList' URL로 GET 요청이 들어오면 이 메서드가 호출됩니다.
    public String SnsReplyDeclareList(Model model) {
        List<SnsDeclareVO> list = adminService.SnsReplyDeclareList(); // SNS 댓글 신고 목록을 조회합니다.
        
        // 중복된 댓글 신고를 제거합니다. (replyNo 기준)
        List<SnsDeclareVO> uniqueList = list.stream()
            .collect(Collectors.toMap(
                SnsDeclareVO::getReplyNo, // 키: replyNo
                snsDeclare -> snsDeclare,  // 값: SnsDeclareVO 객체
                (existing, replacement) -> existing)) // 중복 시 기존 객체 유지
            .values()
            .stream()
            .collect(Collectors.toList());
        
        // 각 댓글에 대한 신고 건수를 맵으로 저장합니다.
        Map<Integer, Integer> countMap = uniqueList.stream()
                .collect(Collectors.toMap(
                    SnsDeclareVO::getReplyNo,
                    snsDeclare -> adminService.getCountByReplyNo(snsDeclare.getReplyNo()),
                    (existing, replacement) -> existing));
        
        // 신고 건수 맵과 신고된 댓글 목록을 뷰에 전달합니다.
        model.addAttribute("countMap", countMap);
        model.addAttribute("SnsReplyDeclareList", uniqueList); // 중복 제거된 리스트를 전달합니다.
        return "admins/SnsReplyDeclareList"; // 'admins/SnsReplyDeclareList' 뷰를 반환합니다.
    }

    // SNS 전체 조회
    @GetMapping("adminSns") // 'adminSns' URL로 GET 요청이 들어오면 이 메서드가 호출됩니다.
    public String adminsnsList(Model model) {
        List<SnsVO> list = snsService.snsList(); // 전체 SNS 목록을 조회합니다.
        model.addAttribute("adminSns", list); // 조회한 SNS 목록을 뷰에 전달합니다.
        return "admins/adminSns"; // 'admins/adminSns' 뷰를 반환합니다.
    }
    
    // SNS 전체 조회 (업데이트용)
    @GetMapping("updateadminSns") // 'updateadminSns' URL로 GET 요청이 들어오면 이 메서드가 호출됩니다.
    public String adminsnsList1(Model model) {
        List<SnsVO> list = snsService.snsList(); // 전체 SNS 목록을 조회합니다.

        model.addAttribute("adminSns", list); // 조회한 SNS 목록을 뷰에 전달합니다.
        return "admins/updateadminSns"; // 'admins/updateadminSns' 뷰를 반환합니다.
    }
    
    // SNS 상태 업데이트
    @GetMapping("updateSnsState") // 'updateSnsState' URL로 GET 요청이 들어오면 이 메서드가 호출됩니다.
    public String SnsDeclareList(@RequestParam(name = "feedNo", required = false, defaultValue = "0") int feedNo, Model model) {
        List<SnsDeclareVO> list = adminService.SnsDeclareList(); // SNS 신고 목록을 조회합니다.

        // 중복된 신고를 제거합니다. (feedNo 기준)
        List<SnsDeclareVO> uniqueList = list.stream()
            .collect(Collectors.toMap(
                SnsDeclareVO::getFeedNo,
                snsDeclare -> snsDeclare,
                (existing, replacement) -> existing))
            .values()
            .stream()
            .collect(Collectors.toList());

        // 각 feedNo에 대한 신고 건수를 맵으로 저장합니다.
        Map<Integer, Integer> countMap = uniqueList.stream()
            .collect(Collectors.toMap(
                SnsDeclareVO::getFeedNo,
                snsDeclare -> adminService.getCountByFeedNo(snsDeclare.getFeedNo()),
                (existing, replacement) -> existing));

        // 신고 목록과 신고 건수를 뷰에 전달합니다.
        model.addAttribute("SnsDeclareList", uniqueList);
        model.addAttribute("countMap", countMap);
        return "redirect:/updateadminSns"; // 'updateadminSns' URL로 리다이렉트합니다.
    }
    
    // SNS 피드 상태 업데이트
    @PostMapping("updatefeedState") // 'updatefeedState' URL로 POST 요청이 들어오면 이 메서드가 호출됩니다.
    public String updatefeedState(@RequestParam int feedNo, @RequestParam String feedState, Model model) {
        // SNS 피드 상태를 업데이트합니다.
        int updatedCount = adminService.updatefeedState(feedNo, feedState);
        
        // 상태 업데이트 결과 메시지를 뷰에 전달합니다.
        model.addAttribute("updatefeedState", updatedCount > 0 ? "성공적으로 업데이트되었습니다." : "업데이트 실패.");
        return "redirect:/adminSns"; // 'adminSns' URL로 리다이렉트합니다.
    }
}
