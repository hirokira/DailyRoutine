package DailyRoutineApp.app.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import DailyRoutineApp.app.dao.D_RoutineDao;
import DailyRoutineApp.app.entity.Account;
import DailyRoutineApp.app.entity.D_Routine;

@Repository
public class D_RoutineDaoImpl implements D_RoutineDao{

	@Autowired
	private JdbcTemplate jdbc;

	@PersistenceContext
	private EntityManager em;//----EntityManager使用

	@Override
	public void insert(D_Routine routine) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		String sql = "INSERT INTO D_routine(title,nicepnt,account_accountid,description,currenttime) Values(?,?,?,?,CURRENT_TIMESTAMP())";

		jdbc.update(sql, routine.getTitle(),routine.getNicepnt(),routine.getAccount().getAccountid(),routine.getDescription());
	}

	@Override
	public void update(D_Routine routine) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		String sql = "UPDATE D_routine SET title=?,nicepnt=?,account_accountid=? ,description=?,currenttime=? WHERE routineid = ?";
		jdbc.update(sql, routine.getTitle(),routine.getNicepnt(),routine.getAccount().getAccountid(),routine.getDescription(),routine.getCurrenttime(),routine.getRoutineid());
	}

	@Override
	public void delete(D_Routine routine) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		String sql = "DELETE FROM D_routine WHERE routineid = ?";
		jdbc.update(sql, routine.getRoutineid());
	}

	@Override
	public D_Routine findById(Integer routineid) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		D_Routine routine = em.createQuery("from D_Routine where routineid = :id", D_Routine.class)
				.setParameter("id", routineid)
				.getSingleResult();
		return routine;
	}

	@Override
	public List<D_Routine> findAll() throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		List<D_Routine> list = em.createQuery("from D_Routine order by currenttime DESC, routineid", D_Routine.class).getResultList();
		return list;
	}

	@Override
	public List<D_Routine> findAllByAccountId(Account account) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		List<D_Routine> list =em.createQuery("from D_Routine where account_accountid = :id order by currenttime DESC",D_Routine.class)
				.setParameter("id", account.getAccountid()).getResultList();
		return list;
	}

	@Override
	public Page<D_Routine> findAll(Pageable pageable) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		int routineListSize = findAll().size();

		List<D_Routine> list = em.createQuery("from D_Routine order by currenttime DESC", D_Routine.class)
								.setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
		Page<D_Routine> pageList = new PageImpl<D_Routine>(list,pageable,routineListSize);
		return pageList;
	}

	@Override
	public Page<D_Routine> findAllByAccountId(Pageable pageable, Account account) throws DataAccessException {
		int routineListSize = findAllByAccountId(account).size();

		List<D_Routine> list = em.createQuery("from D_Routine where account_accountid = :id order by currenttime DESC", D_Routine.class)
								.setParameter("id", account.getAccountid()).setFirstResult((int) pageable.getOffset())
								.setMaxResults(pageable.getPageSize()).getResultList();
		Page<D_Routine> pageList = new PageImpl<D_Routine>(list,pageable,routineListSize);
		return pageList;
	}


}
