package jp.co.sss.book.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.book.entity.Book;
import jp.co.sss.book.entity.BookUser;
import jp.co.sss.book.entity.Genre;
import jp.co.sss.book.form.InsertUserForm;
import jp.co.sss.book.form.LoginFormWithValidation;
import jp.co.sss.book.repository.BookRepository;
import jp.co.sss.book.repository.BookUserRepository;
import jp.co.sss.book.repository.GenreRepository;

@Controller
public class UserController {
	@Autowired
	private BookUserRepository userRepository;
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private GenreRepository genreRepository;
	@Autowired
	HttpSession session;

	@RequestMapping("/")
	public String index(@ModelAttribute LoginFormWithValidation form) {
		session.invalidate();
		return "index";
	}

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String doAdminLogin(@Valid @ModelAttribute LoginFormWithValidation form,
			BindingResult result, Model model) {
		session.invalidate();
		if (result.hasErrors()) {
			model.addAttribute("errMessage", "ユーザID、またはパスワードが間違っています。");
			return index(form);
		}

		String userStr = form.getBookUserId();
		int userId = Integer.parseInt(userStr);
		String password = form.getPassword();
		BookUser user = userRepository.findByBookUserIdAndPassword(userId, password);

		if (user != null) {
			if (userStr.equals(String.valueOf(user.getBookUserId()))) {
				session.setAttribute("user", user.getBookUserName() + "さん");
				session.setAttribute("bookUser", user);
				return "redirect:/list_disp";
			} else {
				model.addAttribute("errMessage", "ユーザID、またはパスワードが間違っています。");
				return "index";
			}
		} else {
			model.addAttribute("errMessage", "ユーザID、またはパスワードが間違っています。");
			return "index";

		}
	}


	@RequestMapping(path = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		//セッションの破棄
		session.invalidate();
		return "redirect:/";
	}

	@RequestMapping(path = "/list_disp", method = RequestMethod.GET)
	public String listDisp(Model model) {
		session.setAttribute("genres", genreRepository.findAll());
		List<Book> list = bookRepository.findAllByOrderByBookIdAsc();
		if (list.size() == 0) {
			model.addAttribute("flag", 1);
		}
		session.setAttribute("lists", list);
		return "list";
	}

	@RequestMapping(path = "/list_disp_name", method = RequestMethod.POST)
	public String listDispName(String bookName) {
		bookName = "%" + bookName + "%";
		List<Book> list = bookRepository.findByBookNameLike(bookName);
		if (list.size() == 0) {
			session.setAttribute("lists", null);
		} else {
			session.setAttribute("lists", list);
		}
		return "list";
	}

	@RequestMapping(path = "/list_disp_genre", method = RequestMethod.POST)
	public String listDispGenre(int genreId) {
		Genre genre = new Genre();
		genre.setGenreId(genreId);
		List<Book> list = bookRepository.findByGenre(genre);
		if (list.size() == 0) {
			session.setAttribute("lists", null);
		} else {
			session.setAttribute("lists", list);
		}
		return "list";
	}

	@RequestMapping("/user/form")
	public String userForm(@ModelAttribute InsertUserForm form) {
		return "user_form";
	}

	@RequestMapping(path = "/insert/user", method = RequestMethod.POST)
	public String doInsertUser(@Valid InsertUserForm form,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("insertUserForm" ,form);
			return userForm(form);
		}
		if(!(form.getPassword().equals(form.getPassword2()))) {
			model.addAttribute("errMessage", "パスワードが一致していません");
			return userForm(form);
		}
		String userId = form.getBookUserId();
		int bookUserId = Integer.parseInt(userId);
		BookUser user = userRepository.findByBookUserId(bookUserId);
		if(user == null) {
			BookUser insert = new BookUser();;
			insert.setBookUserId(bookUserId);
			insert.setBookUserName(form.getBookUserName());
			insert.setPassword(form.getPassword());
			userRepository.save(insert);
			return "redirect:/";
		}else {
			model.addAttribute("errMessage", "ユーザーIDが登録済みです。");
			return "user_form";
		}
	}

}
