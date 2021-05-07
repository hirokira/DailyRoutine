package DailyRoutineApp.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import DailyRoutineApp.app.daoImpl.AccountDaoImpl;
import DailyRoutineApp.app.entity.Account;



@Service

public class TestService {


	@Autowired
	private AccountDaoImpl impl;


//	@Autowired
//	private PasswordEncoder passwordEncoder;

	@Transactional
	public void svAdmin(String accountId,String password,String accountName) {
		Account user = new Account(accountId,password,accountName,true);
		impl.insert(user);
	}

	@Transactional
	public void svUser(String accountId,String password,String accountName) {
		Account user = new Account(accountId,password,accountName,false);
		impl.insert(user);
	}


}
