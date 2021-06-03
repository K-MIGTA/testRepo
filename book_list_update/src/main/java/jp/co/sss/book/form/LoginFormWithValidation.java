package jp.co.sss.book.form;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class LoginFormWithValidation {

	@NotNull
	@Digits(integer = 5, fraction = 1)
	@Pattern(regexp = "^[0-9]*$")
	private String bookUserId;

	@NotNull
	@NotBlank
	@Size(min = 1, max = 16)
	private String password;

	public String getBookUserId() {
		return bookUserId;
	}

	public void setBookUserId( String bookUserId) {
		this.bookUserId = bookUserId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
