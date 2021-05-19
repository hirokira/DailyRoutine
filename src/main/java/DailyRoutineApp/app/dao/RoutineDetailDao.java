package DailyRoutineApp.app.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import DailyRoutineApp.app.entity.Routine_Detail;

public interface RoutineDetailDao {


	public List<Routine_Detail> findAll()throws DataAccessException;

	public void insert(Routine_Detail routine)throws DataAccessException;//---インサート処理

	public void update(Routine_Detail routine)throws DataAccessException;//---アップデート処理

	public void delete(Routine_Detail routine)throws DataAccessException;//---削除処理

	public List<Routine_Detail> findListByRoutineId(Integer routineid) throws DataAccessException;//---ルーティンタイトルIDが一致するルーティン詳細を取得

	//---ルーティン詳細のIDが一致するルーティン詳細を取得
	public Routine_Detail findById(Integer id)throws DataAccessException;


	//---ルーティンタイトルIDが一致するルーティン詳細削除処理
	public void deleteByRoutineId(Integer routineid)throws DataAccessException;


}
