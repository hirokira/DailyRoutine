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
	 * 　　false：既に登録済み
	 * 　　true：未登録
	 */
	public boolean acIdCheck(Account ac) {
		if(impl.acIdCheck(ac)!=0) {
			return false;
		}else {
			return true;
		}
	}

	/*
	 * 入力したアカウント名の重複チェックロジック
	 * 　　false：既に登録済み
	 * 　　true：未登録
	 */
	public boolean acNameCheck(Account ac) {
		if(impl.acNameCheck(ac)!=0) {
			return false;
		}else {
			return true;
		}
	}

	/*
	 * 入力チェック時に重複あった場合のメッセージを返却するメソッド
	 */
	public String acCheckMsg(Account ac) {
		if(acIdCheck(ac)==false && acNameCheck(ac)==false) {
			return "アカウントIDとアカウント名が既に使用済みです。";
		}else if(acIdCheck(ac)==false) {
			return "アカウントIDが既に使用済みです。";
		}else if(acNameCheck(ac)==false) {
			return "アカウント名が既に使用済みです。";
		}else {
			return null;
		}

	}

}
