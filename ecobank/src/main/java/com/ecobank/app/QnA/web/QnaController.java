package com.ecobank.app.QnA.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecobank.app.QnA.service.QnaService;
import com.ecobank.app.QnA.service.QnaVO;

@Controller
public class QnaController {

    @Autowired
    private QnaService qnaService;

    // QNA 목록 페이지
    @GetMapping("QnaUserList")
    public String getQnaUserList(Model model) {
        List<QnaVO> QnaUserList = qnaService.qnaUserList();
        model.addAttribute("QnaUserList", QnaUserList);
        return "QnA/QnaUserList";
    }

    // QNA 등록 페이지
    @GetMapping("/QnaForm")
    public String showQnaForm(Model model, HttpSession session) {
        QnaVO qnaVO = new QnaVO();
        Integer userNo = (Integer) session.getAttribute("userNo");
        if (userNo != null) {
            qnaVO.setUserNo(userNo);
        }
        model.addAttribute("qnaVO", qnaVO);
        return "QnA/QnaForm";
    }

    // QNA 등록 기능
    @PostMapping("/QnaForm")
    public String submitQna(@ModelAttribute QnaVO qnaVO, HttpSession session) {
        Integer userNo = (Integer) session.getAttribute("userNo");
        if (userNo != null) {
            qnaVO.setUserNo(userNo);
        }
        qnaService.insertqnaInfo(qnaVO);
        return "redirect:/QnaUserList";
    }

    // QNA 단건 조회 및 답글 리스트 조회
    @GetMapping("/qna")
    public String getQnaInfo(@RequestParam("qnaNo") Integer qnaNo, Model model, HttpSession session) {
        Integer loggedInUserNo = (Integer) session.getAttribute("userNo");

        // 단일 QNA 정보 조회
        QnaVO qnaVO = qnaService.qnaSelectInfo(qnaNo); 
              
        // 작성자 번호와 로그인한 사용자 번호 비교 (int 타입 비교)
        if (qnaVO.getUserNo() != loggedInUserNo) {
            return "QnA/error"; // 권한 없음 페이지로 리디렉션
        }

        // 답글 리스트 조회
        List<QnaVO> qnaReplyList = qnaService.qnaReplyList(qnaNo);
        
        // 모델에 QNA 정보와 답글 목록 추가
        model.addAttribute("qna", qnaVO);
        model.addAttribute("qnaReplyList", qnaReplyList);

        return "QnA/QnaDetail"; // QNA 상세 페이지로 이동
    }

    // QNA 삭제
    @PostMapping("/qnaDelete")
    public String qnaDelete(@RequestParam("qnaNo") Integer qnaNo) {
        try {
            qnaService.qnaDelete(qnaNo);
            return "redirect:/QnaUserList"; // 삭제 후 QNA 목록 페이지로 리디렉션
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // 에러 페이지로 리디렉션
        }
       
    }
    
    
 // QNA 수정 페이지 조회
    @GetMapping("/qnaEdit/{qnaNo}")
    public String editQna(@PathVariable Integer qnaNo, Model model) {
        if (qnaNo == null) {
            model.addAttribute("errorMessage", "QNA 번호가 필요합니다.");
            return "error"; // QNA 번호가 없으면 오류 페이지로 이동
        }

        QnaVO qnaVO = qnaService.qnaSelectInfo(qnaNo); // QNA 정보 조회
        if (qnaVO == null) {
            model.addAttribute("errorMessage", "QNA 정보가 없습니다.");
            return "error"; // QNA 정보가 없으면 오류 페이지로 이동
        }

        model.addAttribute("qna", qnaVO); // QNA 정보를 모델에 추가
        return "qna/qnaEdit"; // QNA 수정 페이지로 이동
    }

    // QNA 수정 처리
    @PostMapping("/updateQna")
    public String updateQna(@ModelAttribute QnaVO qnaVO, Model model) {
        int result = qnaService.updateQnaInfo(qnaVO); // QNA 정보 수정
        if (result > 0) {
            return "redirect:/qnaDetail?qnaNo=" + qnaVO.getQnaNo(); // 수정 후 QNA 상세 페이지로 리디렉션
        } else {
            model.addAttribute("errorMessage", "QNA 수정에 실패했습니다.");
            return "error"; // 수정 실패 시 오류 페이지로 이동
        }
    }
    
}
