package DailyRoutineApp.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import DailyRoutineApp.app.daoImpl.AccountDaoImpl;
import DailyRoutineApp.app.entity.Account;


@Service
public class AccountService {

	@Autowired
	private AccountDaoImpl impl;

    @Autowired
    private PasswordEncoder passwordEncoder;


	/*
	 * アカウント一覧取得
	 */
	public List<Account> acAll(){
		List<Account> acList = impl.acAll();
		return acList;
	}
	/*
	 * アカウント新規登録
	 */
	@Transactional
	public void insert(Account account) {
		account.setEnabled(true);	//---アカウントの有効化を行う。
		impl.insert(account);		//---アカウントを追加。
	}

	/*
	 * 引数(アカウントID）のアカウント情報取得
	 */
	public Account findById(String accountid) {
		Account account = impl.findById(accountid);
		return account;
	}

	/*
	 * 引数(アカウント名)のアカウント情報取得
	 */

	public Account findByAccountname(String accountname) {
		Account account = impl.findByAccountname(accountname);
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

	//---AccountをDBに登録。(adminかUserでそれぞれ分ける)
	public void accountSave(Account account) {
		account.setEnabled(true);	//---アカウントの有効化を行う。
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		if(account.isAdmin()) {
			svAdmin(account);
		}else {
			svUser(account);
		}
	}

	@Transactional
	public void svAdmin(Account account) {
		account.setAdmin(true);
		impl.insert(account);	//---jdbcTemplateでINSERT処理
	}

	@Transactional
	public void svUser(Account account) {
		account.setAdmin(false);
		impl.insert(account);
	}

	//---アカウント情報更新
	@Transactional
	public void updateAccount(Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		impl.update(account);
	}
}
