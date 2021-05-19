package DailyRoutineApp.app.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import DailyRoutineApp.app.entity.D_Routine;
import DailyRoutineApp.app.service.D_RoutineService;

@Component
public class D_RoutineComponent {


	@Autowired
	private D_RoutineService service;

	/*
	 * いいねが押された時、１加算して返却する処理
	 */
	public Integer nicePntAdd(D_Routine routine) {
		return routine.getNicepnt()+1;//---routineオブジェクトのnicePntを１加算する
	}

}
