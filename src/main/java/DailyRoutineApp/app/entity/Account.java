package DailyRoutineApp.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.sun.istack.NotNull;

import lombok.Data;

@Entity
@Data
public class Account {

	public Account() {}

	public Account(String accountId,String password,String accountName,boolean isAdmin) {
		setAccountid(accountId);
		setPassword(password);
		setAccountname(accountName);
		setEnabled(true);
		setAdmin(isAdmin);
	}


	@Id
	@NotEmpty(message="アカウントIDは必須項目です")
	@Length(min=4,max=10,message="アカウントIDは4文字以上10文字未満です。")
	@Column(nullable=false,unique=true)
	private String accountid;

	@NotNull
	@NotEmpty(message="パスワードは必須項目です")
	@Column(nullable=false)
	private String password;

	@NotNull
	@NotEmpty(message="アカウント名は必須項目です")
	@Length(min=1,max=20,message="アカウント名は1文字以上20文字未満です。")
	@Column(nullable=false)
	private String accountname;

	@Column(nullable=false)
	private boolean enabled;

	@Column(nullable=false)
	private boolean admin;

}
