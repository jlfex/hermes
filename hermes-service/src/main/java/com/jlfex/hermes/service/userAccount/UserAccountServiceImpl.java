package com.jlfex.hermes.service.userAccount;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.repository.UserAccountRepository;


/**
 * 用户账户 业务
 * @author Administrator
 *
 */
@Service
@Transactional
public class UserAccountServiceImpl implements  UserAccountService {

	@Autowired
	private UserAccountRepository userAccountRepository;

	/**
	 * 通过用户编号和类型查询用户账户
	 * 
	 * @param userId
	 * @param type
	 * @return
	 */
	@Override
	public UserAccount findByUserIdAndType(String userId, String type) {
		return userAccountRepository.findByUserIdAndType(userId, type);
	}

	/**
	 * 通过用户及类型查询用户账户
	 * 
	 * @param user
	 * @param type
	 * @return
	 */
	@Override
	public UserAccount findByUserAndType(User user, String type) {
		return userAccountRepository.findByUserAndType(user, type);
	}
    /**
     * 判断用户现金账户余额是否 大于amount
     */
	@Override
	public boolean checkEnoughCash(User user, String type, BigDecimal amount) throws Exception {
		UserAccount userAccount = userAccountRepository.findByUserAndType(user, type);
		if(userAccount!=null && userAccount.getBalance().compareTo(amount) >= 0){
			return true;
		}
		return false;
	}
	
	

	
	
	
	
	

}
