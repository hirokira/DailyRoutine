package DailyRoutineApp.app.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import DailyRoutineApp.app.daoImpl.RoutineDetailDaoImpl;
import DailyRoutineApp.app.entity.Routine_Detail;
import DailyRoutineApp.app.service.D_RoutineService;
import DailyRoutineApp.app.service.RoutineDetailService;

@Controller
public class RoutineDetailController {


	@Autowired
	private RoutineDetailDaoImpl impl;

	@Autowired
	private D_RoutineService d_service;

	@Autowired
	private RoutineDetailService detailService;

	@Autowired
	private HttpSession session;

	/*
	 * 新規ルーティン詳細作成画面
	 */
	@RequestMapping(value="/content/new",method=RequestMethod.POST)
	public ModelAndView contentNew(@RequestParam("routineid")Integer id ,ModelAndView mav) {
		Routine_Detail detail = new Routine_Detail();
		detail.setD_routine(d_service.findById(id));
		mav.addObject("formModel", detail);
		mav.setViewName("/detail/detailNew");
		return mav;
	}

	/*
	 * ルーティン詳細作成処理
	 */
	@RequestMapping(value="/content/create",method=RequestMethod.POST)
	public ModelAndView contentCreate(@ModelAttribute("formModel")@Validated Routine_Detail contents,
									BindingResult result,
									@RequestParam("d_routineid")Integer id,ModelAndView mav) {
		ModelAndView res = null;
		contents.setD_routine(d_service.findById(id));			//---ルーティンIDと一致するルーティンをcontentsに追加
		if(!result.hasErrors()) {
			impl.insert(contents);								//---インサート処理
			res = new ModelAndView("redirect:/routine/index");
		}else {
			mav.addObject("formModel", contents);
			mav.setViewName("/detail/detailNew");
			res = mav;
		}
		return res;
	}

	/*
	 * ルーティン詳細編集画面
	 */
	@RequestMapping(value="/content/edit",method=RequestMethod.POST)
	public ModelAndView contentEdit(@RequestParam("r_Detailid")Integer id,ModelAndView mav) {
		Routine_Detail detailObj = detailService.findById(id);		//---ルーティン内容IDと一致するルーティン内容情報取得
		mav.addObject("formModel", detailObj);
		mav.setViewName("/detail/detailEdit");
		return mav;
	}

	/*
	 * ルーティン詳細更新処理
	 */
	@RequestMapping(value="/content/update",method=RequestMethod.POST)
	public ModelAndView contentUpdate(@ModelAttribute("formModel")@Validated Routine_Detail contents,
										BindingResult result,
										@RequestParam("d_routineid")Integer id,ModelAndView mav) {
		ModelAndView res = null;
		contents.setD_routine(d_service.findById(id));			//---ルーティン情報をセット
		if(!result.hasErrors()) {
			detailService.update(contents);						//---アップデート処理
			session.setAttribute("msg", "RoutineContentの編集が完了しました。");	//---セッションに完了メッセージ登録
			res = new ModelAndView("redirect:/routine/index");
		}else {
			mav.addObject("formModel", contents);
			mav.setViewName("/detail/detailEdit");
			res = mav;
		}
		return res;
	}

	/*
	 * ルーティン詳細削除処理
	 */
	@RequestMapping(value="/content/delete",method=RequestMethod.POST)
	public ModelAndView contentDelete(@RequestParam("r_Detailid")Integer r_Detailid,ModelAndView mav) {

		Routine_Detail detail = detailService.findById(r_Detailid);
		if(detail!=null) {
			detailService.delete(detail);										//---削除処理
			session.setAttribute("msg", "RoutineContentの削除が完了しました");  //---セッションに完了メッセージ登録
			mav=new ModelAndView("redirect:/routine/index");
		}else {
			mav.setViewName("error_");
		}
		return mav;
	}
}
