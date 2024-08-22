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

    // 관리자 페이지에 대한 전체 조회
    @GetMapping("/admin")
    public String intro(Model model) {
        List<UserVO> userList = adminService.UserList(); // 사용자 목록 조회
        model.addAttribute("userList", userList);

        int todaySignUpCount = adminService.getcreaTeat(); // 오늘의 회원 가입 수 조회
        model.addAttribute("todaySignUpCount", todaySignUpCount);

        int users = adminService.getusers(); // 전체 사용자 수 조회
        model.addAttribute("users", users);

        return "admins/admin"; // 관리자 대시보드 페이지로 이동
    }

    // 회원 목록 페이지
    @GetMapping("/adminUser")
    public String getUsers(Model model) {
        List<UserVO> userList = adminService.UserList(); // 사용자 목록 조회
        model.addAttribute("userList", userList);
        return "admins/adminUser"; // 회원 목록 페이지로 이동
    }

    // 회원 상태 업데이트 처리
    @PostMapping("/adminUserUpdate")
    public String updateUserState(@RequestParam String useId, @RequestParam String userState, RedirectAttributes rttr) {
        int updatedCount = adminService.updateUserState(useId, userState); // 사용자 상태 업데이트
        rttr.addFlashAttribute("updateStatus", updatedCount > 0 ? "성공적으로 업데이트되었습니다." : "업데이트 실패.");
        return "redirect:/adminUser"; // 업데이트 후 회원 목록 페이지로 리디렉션
    }

    // 챌린지 신청 목록 조회
    @GetMapping("/ChallDeclareList")
    public String getChallDeclareList(Model model) {
        List<ChallDeclareVO> list = adminService.ChallDeclareList(); // 챌린지 신청 목록 조회

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
        return "admins/ChallDeclareList"; // 챌린지 신청 목록 페이지로 이동
    }

    // 챌린지 신청 상세 조회 (AJAX)
    @GetMapping("/selectChallDeclare/{confirmNo}")
    @ResponseBody
    public Map<String, Object> selectChallDeclare(@PathVariable int confirmNo) {
        return adminService.selectChallDeclare(confirmNo); // 챌린지 신청 상세 정보 반환
    }

    // SNS 전체 조회 페이지
    @GetMapping("/adminSns")
    public String getAdminSnsList(Model model, adminSnsVO adminSnsVO) {
        List<adminSnsVO> list = adminService.selectSns(adminSnsVO); // SNS 목록 조회
        model.addAttribute("adminSns", list);
        return "admins/adminSns"; // SNS 목록 페이지로 이동
    }

    // SNS 신고 목록 조회 (AJAX)
    @GetMapping("/getSnsDeclareList/{feedNo}")
    @ResponseBody
    public List<Map<String, Object>> getSnsDeclareList(@PathVariable int feedNo) {
        System.out.println("Received feedNo: " + feedNo); // 디버깅용 출력
        return adminService.SnsDeclareList(feedNo); // SNS 신고 목록 반환
    }

    // SNS 댓글 조회 페이지
    @GetMapping("/SnsReplyDeclareList")
    public String getRepliesByFeedNo(@RequestParam("feed_no") int feedNo, Model model) {
        try {
            List<adminSnsVO> replies = adminService.selectRepliesByFeedNo(feedNo); // SNS 댓글 목록 조회
            model.addAttribute("replies", replies);
            model.addAttribute("feedNo", feedNo);
            return "admins/SnsReplyDeclareList"; // SNS 댓글 목록 페이지로 이동
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "문제가 발생했습니다. 나중에 다시 시도해 주세요.");
            return "error"; // 오류 페이지로 이동
        }
    }

    // SNS 피드 상태 업데이트
    @PostMapping("/updatefeedState")
    public String updateFeedState(@RequestParam int feedNo, @RequestParam String feedState, Model model) {
        int updatedCount = adminService.updatefeedState(feedNo, feedState); // SNS 피드 상태 업데이트
        model.addAttribute("updatefeedState", updatedCount > 0 ? "성공적으로 업데이트되었습니다." : "업데이트 실패.");
        return "redirect:/adminSns"; // 업데이트 후 SNS 목록 페이지로 리디렉션
    }

    // QNA 목록 페이지
    @GetMapping("/QnaUser")
    public String getQnaUser(Model model) {
        List<QnaVO> qnaUser = adminService.qnaUser(); // QNA 목록 조회
        model.addAttribute("qnaUser", qnaUser);
        return "admins/QnaUser"; // QNA 목록 페이지로 이동
    }

    // QNA 단건 조회 리디렉션 핸들러
    @GetMapping("/adminQnaDetail/{qnaNo}")
    public String redirectToQnaDetail(@PathVariable Integer qnaNo) {
        return "redirect:/adminQnaDetail?qnaNo=" + qnaNo; // QNA 상세 페이지로 리디렉션
    }

    // QNA 상세 페이지 조회
    @GetMapping("/adminQnaDetail")
    public String getQnaDetail(@RequestParam("qnaNo") Integer qnaNo, Model model) {
        if (qnaNo == null) {
            model.addAttribute("errorMessage", "QNA 번호가 필요합니다.");
            return "error"; // QNA 번호가 없으면 오류 페이지로 이동
        }

        QnaVO qnaVO = adminService.qnaSelectInfo(qnaNo); // QNA 정보 조회
        if (qnaVO == null) {
            model.addAttribute("errorMessage", "QNA 정보가 없습니다.");
            return "error"; // QNA 정보가 없으면 오류 페이지로 이동
        }

        // QNA와 관련된 답글 정보도 조회
        QnaVO replyVO = adminService.qnaReplySelect(qnaNo);
        model.addAttribute("qna", qnaVO);
        model.addAttribute("reply", replyVO);

        return "admins/adminQnaDetail"; // QNA 상세 페이지로 이동
    }

    // QNA 답글 상세 조회
    @GetMapping("/qnaReplyDetail")
    public String getQnaReplyDetail(@RequestParam("qnaNo") Integer qnaNo, Model model) {
        QnaVO qnaVO = adminService.qnaReplySelect(qnaNo); // 답글 정보 조회
        if (qnaVO == null) {
            model.addAttribute("errorMessage", "답글 정보가 없습니다.");
            return "error"; // 답글 정보가 없으면 오류 페이지로 이동
        }
        model.addAttribute("reply", qnaVO);
        return "admins/adminQnaDetail"; // QNA 상세 페이지로 이동
    }

    // QNA 답글 등록
    @SuppressWarnings("unused")
    @PostMapping("/insertqnareplyInfo")
    public String insertqnareplyInfo(@ModelAttribute QnaVO qnaVO, Model model) {
        // qnaVO에서 qnaNo를 추출
        Integer qnaNo = qnaVO.getQnaNo();
        
        // qnaNo가 null인지 확인
        if (qnaNo == null) {
            model.addAttribute("errorMessage", "QNA 번호가 필요합니다.");
            return "error"; // QNA 번호가 없으면 오류 페이지로 이동
        }
        // QNA 답글 등록 처리
        adminService.insertqnareplyInfo(qnaVO);

        // 등록 후 상세 페이지로 리다이렉트
        return "redirect:/adminQnaDetail?qnaNo=" + qnaNo; // 답글 등록 후 QNA 상세 페이지로 리디렉션
    }

    // QNA 답글 삭제
    @PostMapping("/deleteqnadeclare")
    public String deleteqnadeclare(@RequestParam("qnaReplyNo") Integer qnaReplyNo, @RequestParam("qnaNo") Integer qnaNo, Model model) {
        try {
            adminService.deleteqnadeclare(qnaReplyNo); // QNA 답글 삭제
            return "redirect:/adminQnaDetail?qnaNo=" + qnaNo; // 삭제 후 QNA 상세 페이지로 리디렉션
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "답글 삭제 중 오류가 발생했습니다.");
            return "error"; // 오류 페이지로 이동
        }
    }

}
