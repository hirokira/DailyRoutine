package DailyRoutineApp.app.component;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class MailUtil {
    private final MailSender mailSender;

    public MailUtil(MailSender mailSender) {
        this.mailSender = mailSender;

        this.sendMail();
    }

    public void sendMail() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("test@test.jp"); // 送信元メールアドレス
        mailMessage.setTo("kkkhhhkkk1@outlook.jp");
        mailMessage.setCc("study.test011616@gmail.com");
        mailMessage.setBcc("study.test011616@gmail.com");
        mailMessage.setSubject("うんこひろみへ");
        mailMessage.setText("いつもうんこ臭いよありがとう。");

        try {
            mailSender.send(mailMessage);
        } catch (MailException e) {
            // TODO: エラー処理
        }
    }

}
