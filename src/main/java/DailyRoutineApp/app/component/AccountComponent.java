package DailyRoutineApp.app.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import DailyRoutineApp.app.daoImpl.AccountDaoImpl;
import DailyRoutineApp.app.entity.Account;

@Component
public class AccountComponent {


	@Autowired
	private AccountDaoImpl impl;

	/*
	 * 入力したアカウントIdの重複チェックロジック
	 *   false：既に登録されている
	 *   true：既に登録されていない
	 */
	public boolean acIdCheck(Account ac) {
		if(impl.acIdCheck(ac)!=0) {
			return false;
		}else {
			return true;
		}
	}

}
