package DailyRoutineApp.app.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import DailyRoutineApp.app.daoImpl.RoutineDetailDaoImpl;
import DailyRoutineApp.app.entity.Routine_Detail;

@Component
public class DetailComponent {

	@Autowired
	private RoutineDetailDaoImpl detailImpl;


	/*
	 * "完了！"または"未完了"ボタン押下時のComplateフラグ処理とSuccess_Cnt加算・減算処理
	 */
	public void complateCnt(Integer detailId,boolean complate_flg) {	//---更新するRoutine詳細IDと、現在のComplate_flg情報を引数「

		Routine_Detail detail =detailImpl.findById(detailId);			//---更新するRoutine詳細IDから、更新対象オブジェクトを取得
		if(complate_flg==true) {								//---現在、Complate_flgの状態が【完了済み】だった場合
			detail.setComplate_flg(false);							//---Complate_flgを "false" に変更する
			detail.setSuccess_cnt(detail.getSuccess_cnt()-1);		//--Success_CNTの値をデクリメントする
		}else {													//---現在、Complate_flgの状態が【未完了】だった場合
			detail.setComplate_flg(true);							//---Complate_flgを "True" に変更する
			detail.setSuccess_cnt(detail.getSuccess_cnt()+1);		//---Success_CNTの値をインクリメントする。
		}
		detailImpl.update(detail);								//---更新されたRoutine詳細オブジェクトでアップデート処理を行う。
	}


	/*
	 * スケジューリング機能実装
	 * 　毎日０時０分０秒に、全てのComplate_flgを "false" に変更する。
	 */
	@Scheduled(cron = "40 * * * * *",zone="Asia/Tokyo")
	public void doa() {
		System.out.println("-----ひろみのうんこはくっさいのー♪-----");
	}

}
