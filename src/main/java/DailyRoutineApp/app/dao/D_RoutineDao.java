package DailyRoutineApp.app.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import DailyRoutineApp.app.entity.D_Routine;

public interface D_RoutineDao {

	public void insert(D_Routine routine)throws DataAccessException;//---インサート処理

	public void update(D_Routine routine)throws DataAccessException;//---アップデート処理

	public void delete(D_Routine routine)throws DataAccessException;//---削除処理

	public D_Routine findById(Integer routineid) throws DataAccessException;//---ルーティンをroutineIdから取得

	public List<D_Routine> findAll()throws DataAccessException;

}
