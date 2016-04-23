package com.estsoft.guestbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.estsoft.guestbook.dao.GuestBookDAO;
import com.estsoft.guestbook.vo.GuestBookVO;


@Controller
public class GuestBookController {

	@Autowired
	private GuestBookDAO dao;
	
	@RequestMapping("/")
	public ModelAndView index(){
		List<GuestBookVO> list = dao.getList();
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.setViewName("/WEB-INF/views/index.jsp");
		return mav;
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insert(@ModelAttribute GuestBookVO vo){
		dao.insert(vo);
		
		return "redirect:/";
	}
	
	@RequestMapping("/deleteform/no={no}")
	public ModelAndView deleteform(@PathVariable("no")Long no){
		ModelAndView mav = new ModelAndView();
		mav.addObject("no", no);
		mav.setViewName("/WEB-INF/views/deleteform.jsp");
		
		return mav;
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam("no")int no,@RequestParam("passwd")String passwd){
		System.out.println("delete no:"+no);
		System.out.println("delete passwd:"+passwd);
		
		String str = "";
		int chk = dao.delete(no, passwd);
		
		if (chk > 0){	// 성공
			str = "redirect:/";
		}
		else{			// 실패
			str = "/WEB-INF/views/deletefail.jsp?no="+no;
		}
		
		return str;
	}
}
