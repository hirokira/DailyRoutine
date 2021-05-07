package DailyRoutineApp.app.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import DailyRoutineApp.app.component.AccountComponent;
import DailyRoutineApp.app.daoImpl.AccountDaoImpl;
import DailyRoutineApp.app.entity.Account;
import DailyRoutineApp.app.service.AccountService;
import DailyRoutineApp.app.service.TestService;

@Controller
public class AccountController {


	@Autowired
	private AccountDaoImpl impl;

	@Autowired
	private AccountService acService;

	@Autowired
	private AccountComponent acComponent;

	@Autowired
	private TestService sv;

	@Autowired
	private HttpSession session;

	@RequestMapping(value="/test",method = RequestMethod.GET)
	public String test(Model model) {
		sv.svAdmin("admin","admin","管理者");
		sv.svUser("hiro", "1234","ヒロ");
//		sv.svItem("ピーマン", 98);
		return "top";
	}

	@RequestMapping(value="/top",method =RequestMethod.GET)
	public ModelAndView top(ModelAndView mav) {
		mav.setViewName("top");
		return mav;
	}

	/*
	 * アカウント一覧表示画面
	 */
	@RequestMapping(value="/account/index",method = RequestMethod.GET)
	public ModelAndView acIndex(ModelAndView mav) {
		mav.setViewName("/account/acIndex");
		List<Account> list = impl.acAll();
		System.out.println(list);
		mav.addObject("list", list);
		return mav;
	}

	/*
	 * アカウント新規作成画面
	 */
	@RequestMapping(value="/account/new",method=RequestMethod.GET)
	public ModelAndView acNew(ModelAndView mav) {
		mav.setViewName("/account/acNew");
		Account ac = new Account();		//---空のモデル作成
		mav.addObject("formModel", ac);		//---空モデルをセット
		return mav;
	}

	/*
	 * アカウント新規作成
	 */
	@RequestMapping(value="/account/create",method=RequestMethod.POST)
	public ModelAndView acCreate(@ModelAttribute("formModel")@Validated Account account,
								BindingResult result,ModelAndView mav) {
		ModelAndView res = null;
		//---バリデーションエラーがない かつ 『アカウントID』、『アカウント名』が未登録時の処理
		if(!result.hasErrors() && acComponent.acIdCheck(account) && acComponent.acNameCheck(account)) {
			acService.insert(account);					//---インサート処理
			session.setAttribute("msg", "登録が完了しました。");	//---セッションに完了メッセージ登録
			res = new ModelAndView("redirect:/top");				//---リダイレクト
		}else {											//---入力不備などがあった場合の処理
			mav.setViewName("/account/acNew");
			mav.addObject("formModel", account);
			mav.addObject("result", result);
			mav.addObject("msg", "入力内容に不備があります。");
			mav.addObject("msg2", acComponent.acCheckMsg(account));		//---入力アカウントID、アカウント名が重複時のメッセージを追加ロジック
			res = mav;
		}
		return res;
	}

	/*
	 * アカウント情報詳細確認
	 */
	@RequestMapping(value="/account/show/{accountid}",method=RequestMethod.GET)
	public ModelAndView acShow(@PathVariable("accountid")String accountid,ModelAndView mav) {
		Account account = acService.findById(accountid);	//---アカウント情報取得
		mav.addObject("formModel", account);
		mav.setViewName("/account/acShow");
		return mav;
	}


}
