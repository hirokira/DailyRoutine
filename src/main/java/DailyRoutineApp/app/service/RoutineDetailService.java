package DailyRoutineApp.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import DailyRoutineApp.app.daoImpl.RoutineDetailDaoImpl;
import DailyRoutineApp.app.entity.Routine_Detail;

@Service
public class RoutineDetailService {


	@Autowired
	private RoutineDetailDaoImpl impl;

	/*
	 * 指定したルーティン詳細IDと一致するルーティン詳細を取得
	 */
	public Routine_Detail findById(Integer id) {
		return impl.findById(id);
	}

	/*
	 * 指定したルーティンIDと一致するルーティン詳細リストを取得
	 */
	public List<Routine_Detail> findListByRoutineId(Integer routineid){
		return impl.findListByRoutineId(routineid);
	}

	/*
	 * ルーティン詳細のインサート処理
	 */
	@Transactional
	public void insert(Routine_Detail routine) {
		impl.insert(routine);
	}

	/*
	 * ルーティン詳細のアップデート処理
	 */
	@Transactional
	public void update(Routine_Detail routine) {
		impl.update(routine);
	}

	/*
	 * ルーティン詳細の削除処理
	 */
	@Transactional
	public void delete(Routine_Detail routine) {
		impl.delete(routine);
	}

	/*
	 * 指定したルーティンタイトルIDと一致するルーティン詳細を削除
	 */
	@Transactional
	public void deleteByRoutineId(Integer routineId) {
		impl.deleteByRoutineId(routineId);
	}

	/*
	 * 指定したルーティンタイトルIDと一致するルーティン詳細を取得
	 */
}
