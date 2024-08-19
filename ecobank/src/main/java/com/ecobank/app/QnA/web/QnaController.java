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

    // QNA 단건 조회
    @GetMapping("/qna")
    public String getQnaInfo(@RequestParam("qnaNo") Integer qnaNo, Model model) {
        QnaVO qnaVO = qnaService.qnaSelectInfo(qnaNo);
        if (qnaVO == null) {
            return "error"; // QNA가 없을 경우 오류 페이지로 리디렉션
        }
        model.addAttribute("qna", qnaVO);
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
}
