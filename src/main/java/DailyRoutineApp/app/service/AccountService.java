package DailyRoutineApp.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import DailyRoutineApp.app.daoImpl.AccountDaoImpl;
import DailyRoutineApp.app.entity.Account;


@Service
public class AccountService {

	@Autowired
	private AccountDaoImpl impl;


	/*
	 * アカウント新規登録
	 */
	@Transactional
	public void insert(Account account) {
		account.setEnabled(true);	//---アカウントの有効化を行う。
		impl.insert(account);		//---アカウントを追加。
	}

	/*
	 * 引数のアカウント情報取得
	 */
	public Account findById(String accountid) {
		Account account = impl.findById(accountid);
		return account;
	}

	/*
	 * アカウント更新
	 */
	@Transactional
	public void update(Account ac) {
		impl.update(ac);
	}

	/*
	 * アカウント削除
	 */
	@Transactional
	public void delete(String accountid) {
		impl.delete(accountid);
	}

	/*
	 * 指定したアカウントIDの数を取得
	 */
	public Integer acCount(Account ac) {
		Integer count =impl.acIdCheck(ac);
		return count;
	}
}
