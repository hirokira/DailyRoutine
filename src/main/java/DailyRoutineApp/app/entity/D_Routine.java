package DailyRoutineApp.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class D_Routine {

	public D_Routine() {}

	public D_Routine(Account account,String title,Integer NicePnt) {
		setRoutineid(0);
		setTitle(title);
		setNicepnt(NicePnt);
		setAccount(account);
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable=false)
	private Integer routineid;

	@Column(nullable=false)
	private String title;

	@Column(nullable=false)
	private Integer nicepnt;

	@ManyToOne
	private Account account;


}
