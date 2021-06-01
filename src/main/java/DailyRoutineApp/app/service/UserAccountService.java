package DailyRoutineApp.app.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import DailyRoutineApp.app.entity.Account;
import DailyRoutineApp.app.entity.UserAccount;


@Service
public class UserAccountService implements UserDetailsService{

    @Autowired
    private AccountService repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	       if (username == null || "".equals(username)) {
	            throw new UsernameNotFoundException("Username is empty");
	        }

	        Account ac = repository.findById(username);
	        if (ac == null) {
	            throw new UsernameNotFoundException("User not found: " + username);
	        }

	        if (!ac.isEnabled()) {
	            throw new UsernameNotFoundException("User not found: " + username);
	        }

	        UserAccount user = new UserAccount(ac,getAuthorities(ac));

	        return user;
	}

	private Collection<GrantedAuthority> getAuthorities(Account account){

		if(account.isAdmin()){
			return AuthorityUtils.createAuthorityList("ROLE_ADMIN","ROLE_USER");
		}else{
			return AuthorityUtils.createAuthorityList("ROLE_USER");
		}

	}

    @Transactional
    public void registerAdmin(String userid, String username, String password) {
        Account user = new Account(userid, passwordEncoder.encode(password),username,true);
        repository.insert(user);
    }

    @Transactional
    public void registerUser(String userid, String username, String password) {
        Account user = new Account(userid, passwordEncoder.encode(password),username, false);
        repository.insert(user);
    }


}
