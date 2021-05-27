package DailyRoutineApp.app.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import DailyRoutineApp.app.entity.Account;

public interface AccountDao {

	public List<Account> acAll();

	public void insert(Account ac) throws DataAccessException;

	public Integer acIdCheck(Account ac) throws DataAccessException;

	public Integer acNameCheck(Account ac) throws DataAccessException;

	public Account findById(String accountid) throws DataAccessException;

	public Account findByAccountname(String accountname) throws DataAccessException;

	public void update(Account ac) throws DataAccessException;

	public void delete(String accountid) throws DataAccessException;

	public Page<Account> acAll(Pageable pageable) throws DataAccessException;
}
