package jp.co.sss.book.form;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class LoginFormAdminWithValidation {

	@NotNull
	@Digits(integer = 5, fraction = 1)
	@Pattern(regexp = "^[0-9]*$")
	private String adminUserId;

	@NotNull
	@NotBlank
	@Size(min = 1, max = 16)
	private String password;


	public String getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
