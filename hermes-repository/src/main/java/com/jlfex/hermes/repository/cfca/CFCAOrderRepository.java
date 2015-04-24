package com.jlfex.hermes.repository.cfca;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.cfca.CFCAOrder;

@Repository
public interface CFCAOrderRepository extends JpaRepository<CFCAOrder, String>,JpaSpecificationExecutor<CFCAOrder> {
	
	List<CFCAOrder> findAllByStatus(Integer status);
	
	List<CFCAOrder> findAllByStatusAndType(Integer status,String type);

	@Query("select count(t.amount) from CFCAOrder t where t.invest.user.id = ?1 and (t.status = 20 or t.status = 30) and createTime between ?2 and ?3")
	BigDecimal countTodayTotalAmountByUser(String userId, Date startDate, Date endDate);
	
	List<CFCAOrder> findAllByInvestUserAndStatus(User user,Integer status);
	
	List<CFCAOrder> findAllByStatusInAndType(List<Integer> status,String type);
}