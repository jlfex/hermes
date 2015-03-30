package com.jlfex.hermes.repository.sequence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.Sequence;

/**
 * api 接口 配置
 * @author Administrator
 * 
 */
@Repository
public interface SequenceRepository extends JpaRepository<Sequence, String>, JpaSpecificationExecutor<Sequence> {

	public Sequence findBySeqNameAndStatus(String seqName, String status);


}
