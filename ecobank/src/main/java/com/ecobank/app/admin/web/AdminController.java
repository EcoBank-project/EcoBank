package com.ecobank.app.admin.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecobank.app.admin.service.AdminService;
import com.ecobank.app.admin.service.ChallDeclareVO;
import com.ecobank.app.admin.service.SnsDeclareVO;
import com.ecobank.app.admin.service.UserVO;
import com.ecobank.app.admin.service.adminSnsVO;
import com.ecobank.app.sns.service.SnsService;
import com.ecobank.app.sns.service.SnsVO;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private SnsService snsService;

    // 전체조회
    @GetMapping("admin")
    public String intro(Model model) {
    	//오늘 가입한 회원 리스트
        List<UserVO> userList = adminService.UserList();
        model.addAttribute("userList", userList);
       
        // 오늘 회원가입한 회원 수
        int todaySignUpCount = adminService.getcreaTeat();
        model.addAttribute("todaySignUpCount", todaySignUpCount);
        
        //총 회원수
        int users = adminService.getusers();
        model.addAttribute("users", users);
        
        return "admins/admin";
    }

    // 회원목록
    @GetMapping("adminUser")
    public String getusers(Model model) {
        List<UserVO> userList = adminService.UserList();
        model.addAttribute("userList", userList);
        return "admins/adminUser";
    }

    // 회원 상태 업데이트
    @PostMapping("adminUserUpdate")
    public String updateUserState(@RequestParam String useId, @RequestParam String userState ,RedirectAttributes rttr) {
        int updatedCount = adminService.updateUserState(useId, userState);     
        rttr.addFlashAttribute("updateStatus", updatedCount > 0 ? "성공적으로 업데이트되었습니다." : "업데이트 실패.");
        return "redirect:/adminUser";
    }

    //챌린지 신청목록
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
    
    @GetMapping("/selectChallDeclare/{confirmNo}")
    @ResponseBody
    public Map<String,Object> selectChallDeclare(@PathVariable int confirmNo) {
        return adminService.selectChallDeclare(confirmNo);
    }
    
//    @GetMapping("/selectChallDeclare/{userNo}")
//    @ResponseBody
//    public Map<String,Object> selectChallCount() {
//        return adminService.selectChallCount();
//    }
    
    
    
    

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
    
    // SNS 전체 조회
    @GetMapping("adminSns") // 'adminSns' URL로 GET 요청이 들어오면 이 메서드가 호출됩니다.
    public String adminsnsList(Model model ,adminSnsVO adminSnsVO) {
        List<adminSnsVO> list =  adminService.selectSns(adminSnsVO);//snsService.snsList(snsVO); // 전체 SNS 목록을 조회합니다.
        model.addAttribute("adminSns", list); // 조회한 SNS 목록을 뷰에 전달합니다.
        return "admins/adminSns"; // 'admins/adminSns' 뷰를 반환합니다.
    }
    
    //SNS 조회
    @GetMapping("/adminSns/{feedNo}")
    @ResponseBody
    public Map<String, Object> SnsDeclareList(@PathVariable int feedNo) {
        return adminService.SnsDeclareList(feedNo);
    }
    
    @GetMapping("/adminSns/replies/{feedNo}")
    public String getRepliesByFeedNo(@PathVariable int feedNo, Model model) {
        try {
            List<adminSnsVO> replies = adminService.getRepliesByFeedNo(feedNo);
            model.addAttribute("replies", replies);
            model.addAttribute("feedNo", feedNo);
            return "admins/SnsDeclareList";
        } catch (Exception e) {
            e.printStackTrace(); // 로그에 예외를 기록
            model.addAttribute("errorMessage", "문제가 발생했습니다. 나중에 다시 시도해 주세요.");
            return "error"; // error.html로 리다이렉트
        }
    }
    
    // SNS 업데이트
    @PostMapping("updatefeedState")
    public String updatefeedState(@RequestParam int feedNo, @RequestParam String feedState, Model model) {
        int updatedCount = adminService.updatefeedState(feedNo, feedState);
        model.addAttribute("updatefeedState", updatedCount > 0 ? "성공적으로 업데이트되었습니다." : "업데이트 실패.");
        return "redirect:/adminSns";
    }
    
     
}
