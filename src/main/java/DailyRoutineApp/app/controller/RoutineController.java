package DailyRoutineApp.app.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import DailyRoutineApp.app.entity.UserAccount;
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
	 * Routine一覧表示(カード＆ページネーション）
	 */
	@RequestMapping(value="/routine/index",method=RequestMethod.GET)
	public ModelAndView routineIndex(ModelAndView mav,@PageableDefault(page=0,size=9)Pageable pageable) {
		mav.setViewName("card");
		Page<D_Routine> list = d_service.findAll(pageable);
		mav.addObject("page", list);
		mav.addObject("list", list.getContent());
		mav.addObject("title", "Routine一覧");

    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      //---UserAccountがログインユーザーの UserDetailsと同じもしくは子クラスかをチェック。
        UserAccount user=null;
    	if(authentication.getPrincipal() instanceof UserAccount) {
    		user = UserAccount.class.cast(authentication.getPrincipal());
    		mav.addObject("userInfo","現在ログインしているユーザー名："+user.getUsername());
    	}else {
    		mav.addObject("userInfo", "");
    	}
		Account account = acService.findByAccountname(user.getUsername());
		session.setAttribute("account", account);
		mav.addObject("account",session.getAttribute("account"));

		if(session.getAttribute("msg")!=null) {					//---セッションにメッセージが登録されていればVIEWへ送り、セッションは削除する
			mav.addObject("msg", session.getAttribute("msg"));
			session.removeAttribute("msg");						//---msgのセッション削除
		}
		return mav;
	}

	/*
	 * ログインユーザーのRoutine一覧表示（カード＆ページネーション)/TOP画面
	 */
	@RequestMapping(value="/routine/top",method=RequestMethod.GET)
	public ModelAndView routineTop(ModelAndView mav,@PageableDefault(page=0,size=9)Pageable pageable) {
		mav.setViewName("card");

    	//---現在のリクエストに紐づく Authentication を取得するには
    	//    	SecurityContextHolder.getContext().getAuthentication() とする。
    	//    	SecurityContextHolder.getContext() は、現在のリクエストに紐づく SecurityContext を返している。
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      //Authentication.getAuthorities() で、現在のログインユーザーに付与されている権限（GrantedAuthority のコレクション）を取得できる。
        System.out.println("  " + authentication.getAuthorities());

        //---Authentication.getPrincipal() で、ログインユーザーの UserDetails を取得できる。
        UserDetails principal = (UserDetails)authentication.getPrincipal();

      //---UserAccountがログインユーザーの UserDetailsと同じもしくは子クラスかをチェック。
        UserAccount user=null;
    	if(authentication.getPrincipal() instanceof UserAccount) {
    		user = UserAccount.class.cast(authentication.getPrincipal());
    		mav.addObject("userInfo","現在ログインしているユーザー名："+user.getUsername());
    	}else {
    		mav.addObject("userInfo", "");
    	}
		Account account = acService.findByAccountname(user.getUsername());
		session.setAttribute("account", account);
		mav.addObject("account",session.getAttribute("account"));
		Page<D_Routine> list = d_service.findAllByAccountId(pageable, account);
		mav.addObject("page",list);
		mav.addObject("list", list.getContent());
		mav.addObject("title", "MyRoutine一覧");
		return mav;
	}

	/*
	 * RoutineIndex画面にて、作成者のボタンを押した際、
	 * その作成者のルーティン一覧を取得し表示（カード）(ページネーション）
	 */
	@RequestMapping(value="/routine/index/{accountname}",method=RequestMethod.GET)
	public ModelAndView routineIndexByAccountname(@PathVariable("accountname")String accountname,ModelAndView mav,
													@PageableDefault(page=0,size=9)Pageable pageable) {
		mav.setViewName("card");
		Account account = acService.findByAccountname(accountname);
		Page<D_Routine> list = d_service.findAllByAccountId(pageable, account);

		mav.addObject("page",list);
		mav.addObject("list", list.getContent());
		mav.addObject("title", accountname+"さんのRoutine一覧");
		return mav;
	}

	/*
	 * イイネ押下時の処理(カードでRoutine一覧表示)(ページネーション）
	 */
	@RequestMapping(value="/routine/index",method=RequestMethod.POST)
	public ModelAndView routineIndexCardPost(@RequestParam("routineid")Integer id,ModelAndView mav,
											@PageableDefault(page=0,size=9)Pageable pageable) {
		D_Routine routine = d_service.findById(id);
		routine.setNicepnt(component.nicePntAdd(routine));
		d_service.update(routine);
		Page<D_Routine> list = d_service.findAll(pageable);
		mav.addObject("page",list);
		mav.addObject("list", list.getContent());
		mav.addObject("title", "Routine一覧");
		mav.addObject("msg", "いいね！ しました。");
		mav.setViewName("card");
		return mav;
	}

	/*
	 * イイネ押下時の処理(カードでRoutine一覧表示)
	 * 　2021/05/30 Add：ページネーション実装メソッド作成に伴い、削除
	 */
//	@RequestMapping(value="/routine/index2",method=RequestMethod.POST)
//	public ModelAndView routineIndexCardPost2(@RequestParam("routineid")Integer id,ModelAndView mav) {
//		D_Routine routine = d_service.findById(id);
//		routine.setNicepnt(component.nicePntAdd(routine));
//		d_service.update(routine);
//		List<D_Routine> list = d_service.findAll();
//		mav.addObject("list", list);
//		mav.addObject("title", "Routine一覧");
//		mav.addObject("msg", "いいね！ しました。");
//		mav.setViewName("card");
//		return mav;
//	}

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
		System.out.println(routine);
		if(!result.hasErrors()) {
			Account ac = (Account) session.getAttribute("account");
//			Account account = acService.findById("hiro");
			routine.setAccount(ac);
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
