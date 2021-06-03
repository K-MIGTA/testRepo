//package jp.co.sss.book.filter;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import jp.co.sss.book.entity.AdminUser;
//import jp.co.sss.book.entity.BookUser;
//import jp.co.sss.book.repository.AdminUserRepository;
//import jp.co.sss.book.repository.BookUserRepository;
//
//@Component
//public class LoginFilter implements Filter {
//	@Autowired
//	BookUserRepository userRepository;
//	@Autowired
//	AdminUserRepository adminRepository;
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response,
//			FilterChain chain) throws IOException, ServletException {
//		HttpServletRequest servlet = (HttpServletRequest) request;
//		HttpSession session = servlet.getSession();
//		String url = servlet.getRequestURI();
//		if (url.endsWith("/book_list/login") || url.endsWith("/book_list/logout") ||
//				url.endsWith("/book_list/") || url.endsWith("/book_list/admin/form") ||
//				url.endsWith("/book_list/admin/login") ||
//				url.indexOf("/html") != -1 || url.indexOf("/css") != -1 ||
//				url.indexOf("/img") != -1 || url.indexOf("/js") != -1) {
//			chain.doFilter(request, response);
//		} else {
//			BookUser user = (BookUser) session.getAttribute("bookUser");
//			AdminUser admin = (AdminUser) session.getAttribute("adminUser");
//
//			if (user != null) {
//				user = userRepository.findByBookUserIdAndPassword(user.getBookUserId(), user.getPassword());
//			} else if (admin != null) {
//				admin = adminRepository.findByAdminUserIdAndPassword(admin.getAdminUserId(), admin.getPassword());
//			}
//			if (user == null && admin == null) {
//				HttpServletResponse servletRespnse = (HttpServletResponse) response;
//				servletRespnse.sendRedirect("/book_list/");
//			}
//			chain.doFilter(request, response);
//		}
//
//	}
//}
