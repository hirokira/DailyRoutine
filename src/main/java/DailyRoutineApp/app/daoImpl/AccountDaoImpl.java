package DailyRoutineApp.app.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
		List<Account> list = em.createQuery("from Account order by admin DESC",Account.class).getResultList();
		return list;
	}

	@Override
	public void insert(Account ac) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		String sql = "INSERT INTO account(accountId,password,accountName,enabled,admin) Values(?,?,?,?,?)";

		jdbc.update(sql, ac.getAccountid(),ac.getPassword(),ac.getAccountname(),ac.isEnabled(),ac.isAdmin());
	}

	/*
	 * 引数のアカウントIdが同じ数を集計する。
	 */
	@Override
	public Integer acIdCheck(Account ac) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		int count = jdbc.queryForObject("SELECT COUNT(accountid) FROM account WHERE accountid = ?", Integer.class,ac.getAccountid());
		return count;
	}

	/*
	 * 引数のアカウント名が同じ数を集計する
	 */
	@Override
	public Integer acNameCheck(Account ac) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		int count = jdbc.queryForObject("SELECT COUNT(accountname) FROM account WHERE accountname = ?", Integer.class, ac.getAccountname());
		return count;
	}

	/*
	 * 引数のアカウントIdのオブジェクトを取得する。
	 */
	@Override
	public Account findById(String accountid) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
//		String sql = "SELECT * FROM account where accountid = ?";
//		RowMapper<Account> rowmapper = new BeanPropertyRowMapper<Account>(Account.class);
//		//SQL実行　jdbcTemplateのqueryメソッドを使ってDBから情報を取得。
//		Account account = jdbc.queryForObject(sql, rowmapper, accountid);

		String qstr = "from Account where accountid = :fstr";
		Query query = em.createQuery(qstr).setParameter("fstr", accountid);
		Account account = (Account) query.getSingleResult();
		return account;
	}

	/*
	 * 引数のアカウントの更新処理
	 */
	@Override
	public void update(Account ac) throws DataAccessException {
		String sql = "UPDATE ACCOUNT SET  accountid = ?, accountname = ? ,password = ?,enabled = ?,admin=? WHERE accountid = ?";
		jdbc.update(sql, ac.getAccountid(),ac.getAccountname(),ac.getPassword(),ac.isEnabled(),ac.isAdmin(),ac.getAccountid());
	}

	/*
	 * 引数のアカウントIDのアカウントを削除
	 */
	@Override
	public void delete(String accountid) throws DataAccessException {
		String sql = "DELETE FROM ACCOUNT WHERE accountid = ?";
		jdbc.update(sql, accountid);
	}

}
