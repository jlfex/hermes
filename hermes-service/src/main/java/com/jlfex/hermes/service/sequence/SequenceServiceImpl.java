package com.jlfex.hermes.service.sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.model.Sequence;
import com.jlfex.hermes.repository.sequence.SequenceRepository;

/**
 * 序列
 * @author Administrator
 *
 */
@Service
@Transactional
public class SequenceServiceImpl implements SequenceService {
	
	@Autowired
    private  SequenceRepository   sequenceRepository;
	/**
	 * 根据虚列名称 + 状态 获取序列
	 */
	@Override
	public Sequence findBySeqNameAndStatus(String seqName, String status) throws Exception {
		return sequenceRepository.findBySeqNameAndStatus(seqName, status);
	}
	
	@Override
	public Sequence saveSequnce(Sequence sequence) throws Exception {
		return sequenceRepository.save(sequence);
	}
   
	
}
