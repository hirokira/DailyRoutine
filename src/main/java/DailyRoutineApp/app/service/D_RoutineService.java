package DailyRoutineApp.app.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import DailyRoutineApp.app.daoImpl.D_RoutineDaoImpl;
import DailyRoutineApp.app.entity.D_Routine;
import DailyRoutineApp.app.entity.Routine_Detail;

@Service
public class D_RoutineService {


	@Autowired
	private D_RoutineDaoImpl impl;

	@Autowired
	private RoutineDetailService detailService;

	/*
	 * ルーティンタイトルのインサート処理
	 */
	@Transactional
	public void insert(D_Routine routine) {
		impl.insert(routine);
	}

	/*
	 * ルーティンタイトルのアップデート処理
	 * 		アップデートした際、同時にcurrenttimeを現在日時に更新する
	 */
	@Transactional
	public void update(D_Routine routine) {
		routine.setCurrenttime(new Timestamp(System.currentTimeMillis()));     // 現在の日時を取得
		impl.update(routine);
	}

	/*
	 * ルーティンタイトル一覧を取得
	 */
	public List<D_Routine> findAll(){
		return impl.findAll();
	}

	/*
	 * ルーティンIDのルーティンタイトルを取得
	 */
	public D_Routine findById(Integer routineId) {
		return impl.findById(routineId);
	}

	/*
	 * 指定ルーティンIDのルーティンタイトルの削除を行う
	 *   ①ルーティンIDと一致するルーティンコンテンツ削除
	 *   ②ルーティンIDのルーティンタイトルを削除
	 */
	@Transactional
	public void delete(D_Routine routine) {

		//---ルーティンIDと一致するルーティンコンテンツList取得
		List<Routine_Detail> list = detailService.findListByRoutineId(routine.getRoutineid());
		if(list.size()!=0) {					//---ルーティンコンテンツが０より大きい場合、コンテンツの削除処理を行う
			detailService.deleteByRoutineId(routine.getRoutineid());
		}
		impl.delete(routine); 					//---ルーティンタイトルIDと一致するルーティンタイトルを削除
	}
}
