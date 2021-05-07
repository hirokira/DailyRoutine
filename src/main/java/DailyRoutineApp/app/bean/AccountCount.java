package DailyRoutineApp.app.bean;

import java.io.Serializable;

import lombok.Data;


@Data
public class AccountCount implements Serializable{


	  private static final long serialVersionUID = 1L;

	  private Integer count;

	  /*
	   * コンストラクタ
	   */
	  public AccountCount(Integer count) {
		  super();
		  this.count=count;
	  }

	  public AccountCount(Object[] object) {
		  this((Integer)object[0]);
	  }
}
