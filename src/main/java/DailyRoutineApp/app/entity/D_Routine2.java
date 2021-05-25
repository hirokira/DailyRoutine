package DailyRoutineApp.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import lombok.Data;

@Entity
@Data
public class D_Routine2 {

	public D_Routine2() {}

	public D_Routine2(Account account,String title,Integer NicePnt) {
		setRoutineid(0);
		setTitle(title);
		setNicepnt(NicePnt);
		setAccount(account);
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable=false)
	private Integer routineid;

	@NotNull
	@NotEmpty(message="Content Titleは必須項目です")
	@Column(nullable=false)
	private String title;

	@Column(nullable=false)
	private Integer nicepnt;

	@ManyToOne
	private Account account;


}
