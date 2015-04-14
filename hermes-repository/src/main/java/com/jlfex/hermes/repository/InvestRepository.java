package com.jlfex.hermes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.Invest;
import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.model.User;

/**
 * 
 * 理财信息仓库
 * @since 1.0
 */
@Repository
public interface InvestRepository extends  PagingAndSortingRepository<Invest, String>, JpaSpecificationExecutor<Invest> {
	/**
	 * 通过用户信息查找对应理财信息
	 * 
	 * @param user
	 * @return
	 */
	@Query("from Invest  where user = ?1 order by status asc")
	public List<Invest> findByUser(User user);

	/**
	 * 通过借款信息查找对应理财信息
	 * 
	 * @param loan
	 * @return
	 */
	@Query("from Invest  where loan = ?1 order by datetime desc")
	public List<Invest> findByLoan(Loan loan);
	
	/**
	 * 通过用户id和状态统计记录数
	 * 
	 * @param userId
	 * @param status
	 * @return
	 */
	@Query("select count(id) from Invest where user =  ?1 and status in ?2")
	public Long loadCountByUserAndStatus(User user, List<String> statusList);

	/**
	 * 通过用户id加载理财信息
	 * 
	 * @param userId
	 * @return
	 */
	public List<Invest> findByUserIdOrderByStatusAscDatetimeDesc(String userId);
	
	/**
	 * 债权标 : 用户获取债权标 信息
	 * @param user
	 * @param loanKind
	 * @return
	 */
	@Query("SELECT  t  FROM Invest  t  where t.loan.loanKind in ?1 and t.user=?2 order by t.status asc")
	public List<Invest> findByUserAndLoanKind(List<String> loanKindList, User user);

    /**
    * 根据 标 和状态 获取 理财信息
    * @param loan
    * @param status
    * @return
    */
	public List<Invest> findByLoanAndStatus(Loan loan, String status);


	public Page<Invest> findByLoan(Loan loan,Pageable pageable);


}
