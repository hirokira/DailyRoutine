package DailyRoutineApp.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity web)throws Exception{

		web.formLogin().loginPage("/login").defaultSuccessUrl("/routine/top").failureUrl("/login-error").permitAll();
		web.authorizeRequests().antMatchers("/css/**", "/img/neko.png", "/js/**","/account/new","/account/create").permitAll()
		.antMatchers("/account/index").hasRole("ADMIN")
        .anyRequest().authenticated();
		web.logout().logoutSuccessUrl("/login").permitAll();
	}

	/**
     * 認証時に利用するデータソースを定義する設定メソッド
     * ここではDBから取得したユーザ情報をuserDetailsServiceへセットすることで認証時の比較情報としている
     * @param auth
     * @throws Exception
     * AuthenticationManagerBuilderは認証系の機能を有している。
     * userDetailsServiceもその一つでフォームに入力されたユーザが使用可能か判断します。
     * https://docs.spring.io/spring-security/site/docs/4.0.x/apidocs/org/springframework/security/config/annotation/authentication/builders/AuthenticationManagerBuilder.html
     */
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception{

		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

}
