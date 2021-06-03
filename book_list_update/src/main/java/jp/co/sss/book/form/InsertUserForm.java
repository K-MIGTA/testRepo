package jp.co.sss.book.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class InsertUserForm {

	@NotNull(message = "ユーザーIDが未入力です")
	@Pattern(regexp = "^[0-9]*$" ,message="整数値を入力してください")
	private String bookUserId;

	@NotNull(message = "ユーザー名が未入力です")
	private String bookUserName;

	@NotNull(message = "パスワードが未入力です")
	@NotBlank(message = "パスワードが未入力です")
	@Size(min = 1, max = 16, message= "16文字以内でお願いします")
	private String password;

	@NotNull
	@NotBlank
	@Size(min = 1, max = 16)
	private String password2;

	public String getBookUserId() {
		return bookUserId;
	}

	public void setBookUserId(String bookUserId) {
		this.bookUserId = bookUserId;
	}

	public String getBookUserName() {
		return bookUserName;
	}

	public void setBookUserName(String bookUserName) {
		this.bookUserName = bookUserName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}



}
