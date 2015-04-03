package com.jlfex.hermes.repository.cfca;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.jlfex.hermes.model.cfca.CFCAOrder;
import com.jlfex.hermes.model.yltx.JlfexOrder;

@Repository
public interface CFCAOrderRepository extends JpaRepository<JlfexOrder, String>, JpaSpecificationExecutor<JlfexOrder> {
	List<CFCAOrder> findAllByStatus(Integer status);
}