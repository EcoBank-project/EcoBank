package com.ecobank.app.admin.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecobank.app.QnA.service.QnaVO;
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
    @GetMapping("/admin")
    public String intro(Model model) {
        List<UserVO> userList = adminService.UserList();
        model.addAttribute("userList", userList);

        int todaySignUpCount = adminService.getcreaTeat();
        model.addAttribute("todaySignUpCount", todaySignUpCount);

        int users = adminService.getusers();
        model.addAttribute("users", users);

        return "admins/admin";
    }

    // 회원목록
    @GetMapping("/adminUser")
    public String getUsers(Model model) {
        List<UserVO> userList = adminService.UserList();
        model.addAttribute("userList", userList);
        return "admins/adminUser";
    }

    // 회원 상태 업데이트
    @PostMapping("/adminUserUpdate")
    public String updateUserState(@RequestParam String useId, @RequestParam String userState, RedirectAttributes rttr) {
        int updatedCount = adminService.updateUserState(useId, userState);
        rttr.addFlashAttribute("updateStatus", updatedCount > 0 ? "성공적으로 업데이트되었습니다." : "업데이트 실패.");
        return "redirect:/adminUser";
    }

    // 챌린지 신청 목록
    @GetMapping("/ChallDeclareList")
    public String getChallDeclareList(Model model) {
        List<ChallDeclareVO> list = adminService.ChallDeclareList();

        // 중복 제거 (confirmNo를 기준으로)
        List<ChallDeclareVO> uniqueList = list.stream()
            .collect(Collectors.toMap(
                ChallDeclareVO::getConfirmNo,
                challDeclare -> challDeclare,
                (existing, replacement) -> existing))
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
    public Map<String, Object> selectChallDeclare(@PathVariable int confirmNo) {
        return adminService.selectChallDeclare(confirmNo);
    }

    // SNS 전체 조회
    @GetMapping("/adminSns")
    public String getAdminSnsList(Model model, adminSnsVO adminSnsVO) {
        List<adminSnsVO> list = adminService.selectSns(adminSnsVO);
        model.addAttribute("adminSns", list);
        return "admins/adminSns";
    }

    @GetMapping("/getSnsDeclareList/{feedNo}")
    @ResponseBody
    public List<Map<String, Object>> getSnsDeclareList(@PathVariable int feedNo) {
        System.out.println("Received feedNo: " + feedNo);
        return adminService.SnsDeclareList(feedNo);
    }

    // SNS 댓글 조회
    @GetMapping("/SnsReplyDeclareList")
    public String getRepliesByFeedNo(@RequestParam("feed_no") int feedNo, Model model) {
        try {
            List<adminSnsVO> replies = adminService.selectRepliesByFeedNo(feedNo);
            model.addAttribute("replies", replies);
            model.addAttribute("feedNo", feedNo);
            return "admins/SnsReplyDeclareList";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "문제가 발생했습니다. 나중에 다시 시도해 주세요.");
            return "error";
        }
    }

    // SNS 업데이트
    @PostMapping("/updatefeedState")
    public String updateFeedState(@RequestParam int feedNo, @RequestParam String feedState, Model model) {
        int updatedCount = adminService.updatefeedState(feedNo, feedState);
        model.addAttribute("updatefeedState", updatedCount > 0 ? "성공적으로 업데이트되었습니다." : "업데이트 실패.");
        return "redirect:/adminSns";
    }

    // QNA 목록
    @GetMapping("/QnaUser")
    public String getQnaUser(Model model) {
        List<QnaVO> qnaUser = adminService.qnaUser();
        model.addAttribute("qnaUser", qnaUser);
        return "admins/QnaUser";
    }

    // QNA 단건 조회 리디렉션 핸들러
    @GetMapping("/adminQnaDetail/{qnaNo}")
    public String redirectToQnaDetail(@PathVariable Integer qnaNo) {
        return "redirect:/adminQnaDetail?qnaNo=" + qnaNo;
    }


    @GetMapping("/adminQnaDetail")
    public String getQnaDetail(@RequestParam("qnaNo") Integer qnaNo, Model model) {
        if (qnaNo == null) {
            model.addAttribute("errorMessage", "QNA 번호가 필요합니다.");
            return "error";
        }

        QnaVO qnaVO = adminService.qnaSelectInfo(qnaNo);
        if (qnaVO == null) {
            model.addAttribute("errorMessage", "QNA 정보가 없습니다.");
            return "error";
        }

        // QNA와 관련된 답글 정보도 조회
        QnaVO replyVO = adminService.qnaReplySelect(qnaNo);
        model.addAttribute("qna", qnaVO);
        model.addAttribute("reply", replyVO);

        return "admins/adminQnaDetail";
    }


    // QNA 답글 상세조회
    @GetMapping("/qnaReplyDetail")
    public String getQnaReplyDetail(@RequestParam("qnaNo") Integer qnaNo, Model model) {
        QnaVO qnaVO = adminService.qnaReplySelect(qnaNo);
        if (qnaVO == null) {
            model.addAttribute("errorMessage", "답글 정보가 없습니다.");
            return "error";
        }
        model.addAttribute("reply", qnaVO);
        return "admins/adminQnaDetail";
    }
    //QNA등록
    @SuppressWarnings("unused")
	@PostMapping("/insertqnareplyInfo")
    public String insertqnareplyInfo(@ModelAttribute QnaVO qnaVO, Model model) {
        // qnaVO에서 qnaNo를 추출
        Integer qnaNo = qnaVO.getQnaNo();
        
        // qnaNo가 null인지 확인
        if (qnaNo == null) {
            model.addAttribute("errorMessage", "QNA 번호가 필요합니다.");
            return "error";
        }
        // QNA 답글 등록 처리
        adminService.insertqnareplyInfo(qnaVO);

        // 등록 후 상세 페이지로 리다이렉트
        return "redirect:/adminQnaDetail?qnaNo=" + qnaNo;
    }
 // QNA 답글 삭제
    @PostMapping("/deleteqnadeclare")
    public String deleteqnadeclare(@RequestParam("qnaReplyNo") Integer qnaReplyNo, @RequestParam("qnaNo") Integer qnaNo, Model model) {
        try {
            adminService.deleteqnadeclare(qnaReplyNo);
            return "redirect:/adminQnaDetail?qnaNo=" + qnaNo; // 삭제 후 QNA 상세 페이지로 리디렉션
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "답글 삭제 중 오류가 발생했습니다.");
            return "error"; // 에러 페이지로 리디렉션
        }
    }
}
