package com.jlfex.hermes.repository.finance;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.yltx.FinanceOrder;

/**
 * 理财产品 
 * @author Administrator
 * 
 */
@Repository
public interface FinanceOrderRepository extends JpaRepository<FinanceOrder, String>, JpaSpecificationExecutor<FinanceOrder> {
	/**
	 * 根据理财产品编号 获取理财产品
	 * @param uniqId
	 * @return
	 */
	public FinanceOrder  findByUniqId(String uniqId) ;
	/**
	 * 根据理财产品Id 获取理财产品
	 * @param id
	 * @return
	 */
	public FinanceOrder  findById(String id);
	/**
	 * 根据起息日+ 状态  获取理财产品
	 * @param dateOfValue
	 * @return
	 */
	public List<FinanceOrder>  findByDateOfValueAndStatusIn(Date dateOfValue, List<String> status);
}
