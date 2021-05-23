package DailyRoutineApp.app.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import DailyRoutineApp.app.dao.RoutineDetailDao;
import DailyRoutineApp.app.entity.Routine_Detail;

@Repository
public class RoutineDetailDaoImpl implements RoutineDetailDao{


	@PersistenceContext
	private EntityManager em;//----EntityManager使用

	@Autowired
	private JdbcTemplate jdbc;

	@Override
	public List<Routine_Detail> findAll() throws DataAccessException {
		List<Routine_Detail> list = em.createQuery("from Routine_Detail", Routine_Detail.class).getResultList();
		return list;
	}

	@Override
	public void insert(Routine_Detail content) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		String sql = "INSERT INTO ROUTINE_DETAIL(content,success_cnt,complate_flg,d_routine_routineid) Values(?,?,?,?)";

		jdbc.update(sql, content.getContent(),content.getSuccess_cnt(),content.isComplate_flg(),content.getD_routine().getRoutineid());
	}

	@Override
	public void update(Routine_Detail routine) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		String sql = "UPDATE ROUTINE_DETAIL SET content = ? ,complate_flg = ? ,SUCCESS_CNT = ? WHERE r_Detailid = ?";
		jdbc.update(sql, routine.getContent(),routine.isComplate_flg(),routine.getSuccess_cnt(),routine.getR_Detailid());
	}

	@Override
	public void delete(Routine_Detail routine) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		String sql = "DELETE FROM ROUTINE_DETAIL WHERE r_Detailid = ?";
		jdbc.update(sql, routine.getR_Detailid());
	}


	@Override
	public List<Routine_Detail> findListByRoutineId(Integer routineid) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		List<Routine_Detail> list = em.createQuery("from Routine_Detail where d_routine_routineid = :id",Routine_Detail.class)
				.setParameter("id", routineid).getResultList();
		return list;
	}

	@Override
	public Routine_Detail findById(Integer id) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		Routine_Detail obj = em.createQuery("from Routine_Detail where R_DETAILID = :id" , Routine_Detail.class)
				.setParameter("id", id).getSingleResult();
		return obj;
	}

	@Override
	public void deleteByRoutineId(Integer routineid) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		String sql = "DELETE FROM ROUTINE_DETAIL WHERE d_routine_routineid = ?";
		jdbc.update(sql, routineid);
	}

	@Override
	public void complate_flgInitial() throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		String sql = "UPDATE ROUTINE_DETAIL SET complate_flg = ?";
		jdbc.update(sql, false);
	}





}
