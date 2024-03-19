package com.maru.chaekmaru.book;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.maru.chaekmaru.config.Config;
import com.maru.chaekmaru.member.MemberDto;
import com.maru.chaekmaru.mypage.MypageService;
import com.maru.chaekmaru.review.ReviewDto;
import com.maru.chaekmaru.review.ReviewService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/book")
public class BookController {

	@Autowired
	BookService bookService;

	@Autowired
	ReviewService reviewService;

	@Autowired
	MypageService mypageService;

	@GetMapping("/list")
	public String list(Model model, @RequestParam(required = false, value = "search", defaultValue = "") String search,
			@RequestParam(required = false, value = "filter", defaultValue = "") String filter,
			@RequestParam(required = false, value = "sort", defaultValue = "new") String sort,
			@RequestParam(required = false, value = "page", defaultValue = "1") String page) {

		int pageItemPerPage = 20;
		int nowPageCount = Integer.parseInt(page);
		int allBookCount = bookService.countBook(search, filter);
		int allPageCount = bookService.countAllPage(allBookCount, pageItemPerPage);

		if (nowPageCount > allPageCount) {
			nowPageCount = allPageCount;
		}

		ArrayList<BookDto> items = new ArrayList<>();
		ArrayList<ListPageDto> listPageDtos = new ArrayList<>();

		items = bookService.setList(search, filter, sort, pageItemPerPage, nowPageCount);
		listPageDtos = bookService.setPaging(pageItemPerPage, nowPageCount, allPageCount);

		model.addAttribute("search", search);
		model.addAttribute("filter", filter);
		model.addAttribute("sort", sort);

		model.addAttribute("nowPage", nowPageCount);
		model.addAttribute("allPage", allPageCount);

		model.addAttribute("listPageDtos", listPageDtos);
		model.addAttribute("items", items);

		return "/book/list";
	}

	@GetMapping("/view/{book_no}")
	public String view(HttpSession session, Model model, @PathVariable("book_no") String book_no) {
		int b_no = Integer.parseInt(book_no);
		BookDto item = bookService.setView(b_no);

		ArrayList<ReviewDto> reviewDtos = new ArrayList<>();
		reviewDtos = reviewService.setReviews(b_no);

		boolean isReviewWrite = false;
		boolean isMemberPick = false;

		MemberDto loginedMemberDto = (MemberDto) session.getAttribute(Config.LOGINED_MEMBER_INFO);

		if (loginedMemberDto != null) {
			isReviewWrite = reviewService.isReviewWrite(loginedMemberDto.getM_id(), b_no);

			if (mypageService.isMemberPick(loginedMemberDto.getM_id(), b_no) != null) {
				isMemberPick = true;
			}

		}

		model.addAttribute("item", item);
		model.addAttribute("reviews", reviewDtos);
		model.addAttribute("isReviewWrite", isReviewWrite);
		model.addAttribute("cate", Integer.parseInt(item.getB_kdc()) / 100);
		model.addAttribute("pick", isMemberPick);
		
		return "/book/view";
	}

}
