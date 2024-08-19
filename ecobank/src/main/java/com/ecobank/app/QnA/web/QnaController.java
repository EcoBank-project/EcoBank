package com.ecobank.app.QnA.web;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ecobank.app.QnA.service.QnaService;
import com.ecobank.app.QnA.service.QnaVO;

@Controller
public class QnaController {

    @Autowired
    private QnaService qnaService;

    // QNA 목록
    @GetMapping("QnaUserList")
    public String getusers(Model model) {
        List<QnaVO> QnaUserList = qnaService.qnaUserList();
        model.addAttribute("QnaUserList", QnaUserList);
        return "QnA/QnaUserList";
    }
    //QNA 단건조회 
    @GetMapping("qnaSelectInfo")
    public String qnaSelectInfo(Model model) {
        List<QnaVO> QnaUserList = qnaService.qnaUserList();
        model.addAttribute("QnaUserList", QnaUserList);
        return "QnA/QnaUserList";
    }
    //QNA등록 페이지
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
    //QnA등록 기능
    @PostMapping("/QnaForm")
    public String submitQna(@ModelAttribute QnaVO qnaVO, HttpSession session) {
        Integer userNo = (Integer) session.getAttribute("userNo");
        if (userNo != null) {
            qnaVO.setUserNo(userNo);
        }
        // 서비스 호출하여 데이터베이스에 QNA 정보 저장
        qnaService.insertqnaInfo(qnaVO);
        return "redirect:/QnaUserList";
    }

  
    //QnA 단건조회
    @GetMapping("/qna/{qnaNo}")
    public String getQnaInfo(@PathVariable Integer qnaNo, Model model) {
        QnaVO qnaVO = qnaService.qnaSelectInfo(qnaNo);
        if (qnaVO == null) {
            return "error"; // QNA가 없을 경우 오류 페이지로 리디렉션
        }
        model.addAttribute("qna", qnaVO);
        return "QnA/QnaDetail"; // QNA 상세 페이지로 이동
    }
    
}
