package com.jlfex.hermes.service.sequence;

import com.jlfex.hermes.model.Sequence;

/**
 * 序列
 * @author Administrator
 *
 */
public interface SequenceService {

	/**
	 * 根据名称+状态 获取序列
	 * @param seqName
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public Sequence findBySeqNameAndStatus(String seqName, String status) throws Exception ;

	/**
	 * 保存序列
	 * @param sequence
	 * @return
	 * @throws Exception
	 */
	public Sequence saveSequnce(Sequence sequence) throws Exception;

}
