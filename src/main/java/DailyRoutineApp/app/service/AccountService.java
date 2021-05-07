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

}
