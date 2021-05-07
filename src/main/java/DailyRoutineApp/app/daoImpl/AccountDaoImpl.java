package DailyRoutineApp.app.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import DailyRoutineApp.app.dao.AccountDao;
import DailyRoutineApp.app.entity.Account;

@Repository
public class AccountDaoImpl implements AccountDao{

	@PersistenceContext
	private EntityManager em;//----EntityManager使用

	@Autowired
	private JdbcTemplate jdbc;//----JdbcTemplateをIJ

	@Override
	public List<Account> acAll() {

		// TODO 自動生成されたメソッド・スタブ
		List<Account> list = em
				.createQuery("from Account",Account.class)
				.getResultList();
		return list;
	}

	@Override
	public void insert(Account ac) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		String sql = "INSERT INTO account(accountId,password,accountName,enabled,admin) Values(?,?,?,?,?)";

		jdbc.update(sql, ac.getAccountid(),ac.getPassword(),ac.getAccountname(),ac.isEnabled(),ac.isAdmin());
	}



}
