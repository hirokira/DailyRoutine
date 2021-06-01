package DailyRoutineApp.app.component;

import org.springframework.stereotype.Component;

@Component
public class AccountComponent {

//
//	@Autowired
//	private AccountDaoImpl impl;
//
//
//
//	/*
//	 * 入力したアカウントIdの重複チェックロジック
//	 * 　　false：既に登録済み
//	 * 　　true：未登録
//	 */
//	public boolean acIdCheck(Account ac) {
//		if(impl.acIdCheck(ac)!=0) {
//			return false;
//		}else {
//			return true;
//		}
//	}
//
//	/*
//	 * 入力したアカウント名の重複チェックロジック
//	 * 　　false：既に登録済み
//	 * 　　true：未登録
//	 */
//	public boolean acNameCheck(Account ac) {
//		if(impl.acNameCheck(ac)!=0) {
//			return false;
//		}else {
//			return true;
//		}
//	}
//
//	/*
//	 * アカウントID入力チェック時に重複あった場合のメッセージを返却するメソッド
//	 */
//	public String acIdCheckMsg(Account ac) {
//		if(acIdCheck(ac)==false) {
//			return "アカウントIDは既に使用されています。";
//		}else {
//			return null;
//		}
//	}
//
//	/*
//	 * アカウント名の入力チェック時に重複あった場合のメッセージを返却するメソッド
//	 */
//	public String acNameCheckMsg(Account ac) {
//		if(acNameCheck(ac)==false) {
//			return "アカウント表示名は既に使用されています。";
//		}else {
//			return null;
//		}
//	}
//
//	/*
//	 * アカウント新規作成時のメッセージ返却メソッド
//	 * 		Listに格納し、nullの要素は削除して返却。
//	 */
//	public List<String> acCheckMsg(Account account){
//		List<String> msgList = new ArrayList<String>();
//		msgList.add("入力内容に不備があります。");
//		msgList.add(acIdCheckMsg(account));
//		msgList.add(acNameCheckMsg(account));
//		msgList.removeAll(Collections.singleton(null));
//		return msgList;
//	}
//
//	/*
//	 * アカウント更新時、アカウント名が更新されたかどうかのチェック
//	 * 　　true:未更新
//	 * 　　false：更新された
//	 */
//	public boolean acNameUpdateCheck(Account account) {
//		String before = impl.findById(account.getAccountid()).getAccountname();//---DBのアカウント名を取得
//		String after = account.getAccountname();									//---フォームに入力されたアカウント名を取得
//		if(before.equals(after)) {
//			return true;
//		}else {
//			return false;
//		}
//	}
//
//	/*
//	 * アカウント更新時、更新するアカウントのパスワードと入力された前のパスワードが一致しているかを確認する
//	 * 　　true：一致
//	 * 　　false：不一致
//	 */
//	public boolean passwordCheck(String accountid,String pass) {
//		if(impl.findById(accountid).getPassword().equals(pass)) {
//			return true;
//		}else {
//			return false;
//		}
//	}
//
//	/*
//	 * アカウント更新時、更新するアカウントのパスワードと入力された前パスワードが不一致時のメッセージ返却メソッド
//	 */
//	public String passCheckMsg(String accountid,String pass) {
//		if(passwordCheck(accountid,pass)) {
//			return null;
//		}else {
//			return "前パスワードが異なります。";
//		}
//	}
//
//	/*
//	 * アカウント更新時のメッセージ返却メソッド
//	 *   アカウント名が更新時のみ、アカウント名チェックメッセージメソッドは実施する。
//	 *   前パスワードと入力パスワードが異なるときのみ、パスワードチェックメッセージメソッドは実施する
//	 *   null要素は削除して返却する
//	 */
//	public List<String> acUpdateCheckMsg(Account ac,String accountid,String pass) {
//		List<String> msgList = new ArrayList<String>();
//		msgList.add("入力内容に不備があります。");
//		if(acNameUpdateCheck(ac)==false) {					//---アカウント名が更新時のみアカウント名チェックメソッド実施
//			msgList.add(acNameCheckMsg(ac));				//---アカウント名に重複があった場合、メッセージを追加
//		}
//		if(passwordCheck(accountid,pass)==false) {			//---前パスワードと入力パスワードが不一致時のみ、パスワードメッセージメソッド実施
//			msgList.add(passCheckMsg(accountid,pass));		//---パスワード不一致メッセージ追加
//		}
//		msgList.removeAll(Collections.singleton(null));
//		return msgList;
//	}

}
