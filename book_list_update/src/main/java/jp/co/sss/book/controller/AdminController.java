package jp.co.sss.book.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.book.entity.AdminUser;
import jp.co.sss.book.entity.Book;
import jp.co.sss.book.entity.Genre;
import jp.co.sss.book.form.InsertForm;
import jp.co.sss.book.form.LoginFormAdminWithValidation;
import jp.co.sss.book.repository.AdminUserRepository;
import jp.co.sss.book.repository.BookRepository;
import jp.co.sss.book.repository.BookUserRepository;
import jp.co.sss.book.repository.GenreRepository;

@Controller
public class AdminController {
	@Autowired
	private BookUserRepository userRepository;
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private GenreRepository genreRepository;
	@Autowired
	private AdminUserRepository adminRepository;
	@Autowired
	HttpSession session;

	@RequestMapping(path = "admin/form", method = RequestMethod.GET)
	public String adminLogin(@ModelAttribute LoginFormAdminWithValidation form) {
		return "admin_form";
	}

	@RequestMapping(path = "admin/login", method = RequestMethod.POST)
	public String doLogin(@Valid @ModelAttribute LoginFormAdminWithValidation form,
			BindingResult result, Model model) {
		session.invalidate();
		if (result.hasErrors()) {
			model.addAttribute("errMessage", "管理者ID、またはパスワードが間違っています。");
			return adminLogin(form);
		}

		String userStr = form.getAdminUserId();
		int userId = Integer.parseInt(userStr);
		String password = form.getPassword();
		AdminUser admin = adminRepository.findByAdminUserIdAndPassword(userId, password);

		if (admin != null) {
			if (userStr.equals(String.valueOf(admin.getAdminUserId()))) {
				session.setAttribute("admin", admin.getAdminUserName() + "さん");
				session.setAttribute("adminUser", admin);
				return "redirect:/admin_list_disp";
			} else {
				model.addAttribute("errMessage", "管理者ID、またはパスワードが間違っています。");
				return "admin_form";
			}
		} else {
			model.addAttribute("errMessage", "管理者ID、またはパスワードが間違っています。");
			return "admin_form";

		}
	}

	@RequestMapping(path = "/admin_list_disp", method = RequestMethod.GET)
	public String adminListDisp(Model model) {
		session.setAttribute("genres", genreRepository.findAll());
		List<Book> list = bookRepository.findAllByOrderByBookIdAsc();
		if (list.size() == 0) {
			model.addAttribute("flag", 1);
		}
		session.setAttribute("lists", list);
		return "admin_list";
	}

	@RequestMapping(path = "/list_input", method = RequestMethod.GET)
	public String listInput(Model model) {
		model.addAttribute("flag", 1);
		return "admin_list";
	}

	@RequestMapping(path = "/insert", method = RequestMethod.POST)
	public String listInsert(@Valid @ModelAttribute InsertForm form,
			BindingResult result, Model model, int genreId) {
		if (result.hasErrors()) {
			return adminListDisp(model);
		}
		Genre genre = genreRepository.getOne(genreId);
		Book book = new Book();
		book.setBookId(form.getBookId());
		book.setBookName(form.getBookName());
		book.setAuthor(form.getAuthor());
		book.setPublicationDate(form.getPublicationDate());
		book.setStock(form.getStock());
		book.setGenre(genre);
		bookRepository.save(book);
		return "redirect:/admin_list_disp";
	}

	@RequestMapping(path = "/list_delete/{bookId}", method = RequestMethod.GET)
	public String listDelete(@PathVariable int bookId, Model model) {
		Book book = bookRepository.getOne(bookId);
		bookRepository.delete(book);
		return "redirect:/admin_list_disp";
	}

	@RequestMapping(path = "/list_update_select/{bookId}", method = RequestMethod.GET)
	public String listUpdateSelect(@ModelAttribute InsertForm form, @PathVariable int bookId, Model model) {
		model.addAttribute("flag", 2);
		Book book = bookRepository.getOne(bookId);
		session.setAttribute("bookSave", book);
		bookRepository.delete(book);
		session.setAttribute("lists", bookRepository.findAll());
		model.addAttribute("update", book);
		return "admin_list";
	}

	@RequestMapping(path = "/update", method = RequestMethod.POST)
	public String listUpdate(@Valid @ModelAttribute InsertForm form,
			BindingResult result, Model model, int genreId) {
		if (result.hasErrors()) {
			Book book = (Book) session.getAttribute("bookSave");
			bookRepository.save(book);
			return adminListDisp(model);
		}

		Genre genre = genreRepository.getOne(genreId);
		Book book = new Book();
		book.setBookId(form.getBookId());
		book.setBookName(form.getBookName());
		book.setAuthor(form.getAuthor());
		book.setPublicationDate(form.getPublicationDate());
		book.setStock(form.getStock());
		book.setGenre(genre);
		bookRepository.save(book);
		return "redirect:/admin_list_disp";
	}

}
