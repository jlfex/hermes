package com.jlfex.hermes.service.impl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jlfex.hermes.common.Assert;
import com.jlfex.hermes.model.Label;
import com.jlfex.hermes.repository.LabelRepository;
import com.jlfex.hermes.service.LabelService;

/**
 * 标签业务实现
 */
@Service
@Transactional
public class LabelServiceImpl implements LabelService {

	/** 标签仓库 */
	@Autowired
	private LabelRepository labelRepository;
	
	/* (non-Javadoc)
	 * @see com.jlfex.hermes.service.LabelService#findByNames(java.lang.String[])
	 */
	@Override
	public List<Label> findByNames(String... names) {
		// 验证参数
		Assert.notNull(names, "names is null.");
		// 遍历查询标签
		List<Label> labels = new ArrayList<Label>(names.length);
		for (String name: names) {
			Label label = labelRepository.findByName(name);
			if (label == null) {
				label = new Label();
				label.setName(name);
				labelRepository.save(label);
			}
			labels.add(label);
		}
		
		// 返回结果
		return labels;
	}
}
