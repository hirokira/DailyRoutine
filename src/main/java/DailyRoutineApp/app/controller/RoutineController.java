package DailyRoutineApp.app.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import DailyRoutineApp.app.component.D_RoutineComponent;
import DailyRoutineApp.app.component.DetailComponent;
import DailyRoutineApp.app.daoImpl.RoutineDetailDaoImpl;
import DailyRoutineApp.app.entity.Account;
import DailyRoutineApp.app.entity.D_Routine;
import DailyRoutineApp.app.entity.Routine_Detail;
import DailyRoutineApp.app.service.AccountService;
import DailyRoutineApp.app.service.D_RoutineService;

@Controller
public class RoutineController {

	@Autowired
	private AccountService acService;

	@Autowired
	private D_RoutineService d_service;

	@Autowired
	private D_RoutineComponent component;

	@Autowired
	private RoutineDetailDaoImpl detailImpl;

	@Autowired
	private DetailComponent detailComponent;

	@Autowired
	private HttpSession session;

	/*
	 * Routine一覧表示
	 */
	@RequestMapping(value="/routine/index",method=RequestMethod.GET)
	public ModelAndView routineIndex(ModelAndView mav) {
		List<D_Routine> list = d_service.findAll();
		mav.addObject("list", list);
		mav.setViewName("/routine/routineIndex");
		if(session.getAttribute("msg")!=null) {					//---セッションにメッセージが登録されていればVIEWへ送り、セッションは削除する
			mav.addObject("msg", session.getAttribute("msg"));
			session.removeAttribute("msg");						//---msgのセッション削除
		}
		return mav;
	}

	/*
	 * イイネ押下時の処理(Routine一覧表示)
	 */
	@RequestMapping(value="/routine/index",method=RequestMethod.POST)
	public ModelAndView routineIndexPost(@RequestParam("routineid")Integer id,ModelAndView mav) {
		D_Routine routine = d_service.findById(id);
		routine.setNicepnt(component.nicePntAdd(routine));
		d_service.update(routine);
		List<D_Routine> list = d_service.findAll();
		mav.addObject("list", list);
		mav.addObject("msg", "いいね！ しました。");
		mav.setViewName("/routine/routineIndex");
		return mav;
	}

	/*
	 * 新規Routine登録画面
	 */
	@RequestMapping(value="/routine/new",method=RequestMethod.GET)
	public ModelAndView routineNew(ModelAndView mav) {
		D_Routine routine = new D_Routine();
		mav.addObject("formModel", routine);
		mav.setViewName("/routine/routineNew");
		return mav;
	}

	/*
	 * 新規Routine登録処理
	 */
	@RequestMapping(value="/routine/create",method=RequestMethod.POST)
	public ModelAndView routineCreate(@ModelAttribute("formModel")@Validated D_Routine routine,
			BindingResult result,ModelAndView mav) {
		if(!result.hasErrors()) {
			Account account = acService.findById("admin");
			routine.setAccount(account);
			d_service.insert(routine);
			mav.setViewName("/routine/routineSuccess");
		}else {
			mav.setViewName("/routine/routineNew");
			mav.addObject("formModel", routine);
		}
		return mav;
	}

	/*
	 * Routine詳細表示
	 */
	@RequestMapping(value="/routine/show/{routineid}",method=RequestMethod.GET)
	public ModelAndView routineShow(@PathVariable("routineid")Integer routineid,ModelAndView mav) {
		D_Routine routine = d_service.findById(routineid);								//---routineidに該当するRoutine詳細を取得
		List<Routine_Detail> detailList = detailImpl.findListByRoutineId(routineid);	//---Routineの内容一覧をRoutine_Detailから取得
		mav.addObject("detailList", detailList);
		mav.addObject("formModel", routine);
		mav.setViewName("/routine/routineShow");
		return mav;
	}

	/*
	 * Routine削除処理
	 */
	@RequestMapping(value="/routine/delete",method=RequestMethod.POST)
	public ModelAndView routineDelete(@RequestParam("routineid")Integer routineid,ModelAndView mav) {
		mav=new ModelAndView("redirect:/routine/index");
		d_service.delete(d_service.findById(routineid));			//---削除処理を行う
		session.setAttribute("msg", "Routineの削除が完了しました");
		return mav;
	}

	/*
	 * 完了/未完了ボタンを押下された際の処理
	 * Routine詳細表示
	 * 2021/05/19 追加
	 */
	@RequestMapping(value="/routine/show",method=RequestMethod.POST)
	public ModelAndView routineShowComplate(@RequestParam("routineid") Integer routineid,
											@RequestParam("r_Detailid") Integer r_Detailid,
											@RequestParam("complate_flg") boolean complate_flg,ModelAndView mav) {
		/*
		 * Complateフラグ処理とSuccess_Cnt加算処理
		 * 　　False:Complateフラグがfalseの時、Trueに変更してSuccess_cntに１を加算
		 * 　　True：ComplateフラグがTrueの時、falseに変更してSuccess_cntから１を減算
		 */
		detailComponent.complateCnt(r_Detailid, complate_flg);							//---Complateフラグ処理とSuccess_Cnt加算処理

		D_Routine routine = d_service.findById(routineid);								//---routineidに該当するRoutine詳細を取得
		List<Routine_Detail> detailList = detailImpl.findListByRoutineId(routineid);	//---Routineの内容一覧をRoutine_Detailから取得

		mav.setViewName("/routine/routineShow");
		mav.addObject("formModel", routine);
		mav.addObject("detailList", detailList);
		return mav;
	}
}
