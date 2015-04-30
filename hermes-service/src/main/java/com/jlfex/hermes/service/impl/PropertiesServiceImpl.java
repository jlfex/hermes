package com.jlfex.hermes.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Assert;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.Properties;
import com.jlfex.hermes.model.Text;
import com.jlfex.hermes.repository.CommonRepository;
import com.jlfex.hermes.repository.PropertiesRepository;
import com.jlfex.hermes.repository.TextRepository;
import com.jlfex.hermes.service.PropertiesService;
import com.jlfex.hermes.service.TextService;
import com.jlfex.hermes.service.common.Pageables;
import com.jlfex.hermes.service.common.Query;
import com.jlfex.hermes.service.web.PropertiesFilter;

/**
 * 系统属性业务实现
 */
@Service
@Transactional
public class PropertiesServiceImpl implements PropertiesService {
	
	/** 系统属性仓库 */
	@Autowired
	private PropertiesRepository propertiesRepository;

	/** 公共仓库 */
	@Autowired
	private CommonRepository commonRepository;
	@Autowired
	private TextService textService;
	@Autowired
	private TextRepository textRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.PropertiesService#findById(java.lang.String)
	 */
	@Override
	public Properties findById(String id) {
		Assert.notEmpty(id, "properties id is empty.", "exception.properties");
		return propertiesRepository.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.PropertiesService#findAll()
	 */
	@Override
	public List<Properties> findAll() {
		return propertiesRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.PropertiesService#findByNameAndCode(java.lang
	 * .String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Page<Properties> findByNameAndCode(String name, String code, Integer page, Integer size) {
		// 初始化
		Pageable pageable = Pageables.pageable(page, size);
		Query query = new Query("from Properties where 1 = 1");

		// 查询数据
		query.and("name like :name", "name", "%" + name + "%", !Strings.empty(name));
		query.and("code like :code", "code", "%" + code + "%", !Strings.empty(code));
		query.order("code asc");
		Long total = commonRepository.count(query.getCount(), query.getParams());
		List<Properties> properties = commonRepository.pageByJpql(query.getJpql(), query.getParams(), pageable.getOffset(), pageable.getPageSize(), Properties.class);

		// 返回结果
		Page<Properties> pageProp = new PageImpl<Properties>(properties, pageable, total);
		return pageProp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.PropertiesService#save(com.jlfex.hermes.model
	 * .Properties)
	 */
	@Override
	public Properties save(Properties properties) {
		// 断言
		Assert.notNull(properties, "properties is null.");

		// 验证代码
		// 若是修改数据判断代码是否变更
		// 若是添加数据判断代码是否唯一
		if (Strings.empty(properties.getId())) {
			Properties byCode = propertiesRepository.findByCode(properties.getCode());
			Assert.isNull(byCode, "properties code is duplicated.", "exception.properties.code.duplicate");
		} else {
			Properties byId = propertiesRepository.findOne(properties.getId());
			Assert.notNull(byId, "properties id is not correct. id is " + properties.getId(), "exception.properties");
			Assert.equals(byId.getCode(), properties.getCode(), "cannot edit code " + byId.getCode() + " to " + properties.getCode(), "exception.properties.code.edit");
		}

		// 清除缓存并保存数据返回
		PropertiesFilter.clear();
		return propertiesRepository.save(properties);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jlfex.hermes.service.PropertiesService#save(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Properties save(String id, String name, String code, String value) {
		// 断言
		Assert.notEmpty(name, "properties name is empty.", "exception.properties.name.empty");
		Assert.notEmpty(code, "properties code is empty.", "exception.properties.code.empty");
		Assert.notEmpty(value, "properties value is empty.", "exception.properties.value.empty");

		// 设置属性
		Properties properties = Strings.empty(id) ? new Properties() : propertiesRepository.findOne(id);
		properties.setName(name);
		properties.setCode(code);
		properties.setValue(value);

		// 保存并返回
		return save(properties);
	}

	@Override
	public Properties findByCode(String code) {
		return propertiesRepository.findByCode(code);
	}

	@Override
	public void saveConfigurableProperties(String logo, String operationName, String operationNickname, String website, String copyright, String icp, String serviceTel, String serviceTime, String companyName, String companyAddress, String companyCity, String smtpHost,
			String smtpPort, String smtpUsername, String smtpPassword, String mailFrom, String serviceEmail, String jobNoticeEmail, String indexLoanSize, String emailExpire, String smsExpire, String realnameSwitch, String cellphoneSwitch) {

		Properties properties = findByCode("app.logo");
		Text text = textService.loadById(properties.getValue());
		String source = text.getText();
		if (logo != null) {
			if (!source.equals(logo) && !logo.contains("data:null")) {
				text.setText(logo);
			}
		}
		properties = findByCode("app.operation.name");
		source = properties.getValue();
		if (!source.equals(operationName)) {
			properties.setValue(operationName);
			propertiesRepository.save(properties);
		}
		properties = findByCode("app.operation.nickname");
		source = properties.getValue();
		if (!source.equals(operationNickname)) {
			properties.setValue(operationNickname);
			propertiesRepository.save(properties);
		}
		properties = findByCode("app.website");
		source = properties.getValue();
		if (!source.equals(website)) {
			properties.setValue(website);
			propertiesRepository.save(properties);
		}
		properties = findByCode("app.copyright");
		source = properties.getValue();
		if (!source.equals(copyright)) {
			properties.setValue(copyright);
			propertiesRepository.save(properties);
		}
		properties = findByCode("app.icp");
		source = properties.getValue();
		if (!source.equals(icp)) {
			properties.setValue(icp);
			propertiesRepository.save(properties);
		}
		properties = findByCode("site.service.tel");
		source = properties.getValue();
		if (!source.equals(serviceTel)) {
			properties.setValue(serviceTel);
			propertiesRepository.save(properties);
		}
		properties = findByCode("site.service.time");
		source = properties.getValue();
		if (!source.equals(serviceTime)) {
			properties.setValue(serviceTime);
			propertiesRepository.save(properties);
		}
		properties = findByCode("app.company.name");
		source = properties.getValue();
		if (!source.equals(companyName)) {
			properties.setValue(companyName);
			propertiesRepository.save(properties);
		}
		properties = findByCode("app.company.address");
		source = properties.getValue();
		if (!source.equals(companyAddress)) {
			properties.setValue(companyAddress);
			propertiesRepository.save(properties);
		}
		properties = findByCode("app.company.city");
		source = properties.getValue();
		if (!source.equals(companyCity)) {
			properties.setValue(companyCity);
			propertiesRepository.save(properties);
		}
		properties = findByCode("mail.smtp.host");
		source = properties.getValue();
		if (!source.equals(smtpHost)) {
			properties.setValue(smtpHost);
			propertiesRepository.save(properties);
		}
		properties = findByCode("mail.smtp.port");
		source = properties.getValue();
		if (!source.equals(smtpPort)) {
			properties.setValue(smtpPort);
			propertiesRepository.save(properties);
		}
		properties = findByCode("mail.smtp.username");
		source = properties.getValue();
		if (!source.equals(smtpUsername)) {
			properties.setValue(smtpUsername);
			propertiesRepository.save(properties);
		}
		properties = findByCode("mail.smtp.password");
		source = properties.getValue();
		if (!source.equals(smtpPassword)) {
			properties.setValue(smtpPassword);
			propertiesRepository.save(properties);
		}
		properties = findByCode("mail.from");
		source = properties.getValue();
		if (!source.equals(mailFrom)) {
			properties.setValue(mailFrom);
			propertiesRepository.save(properties);
		}
		properties = findByCode("app.customer.service.email");
		source = properties.getValue();
		if (!source.equals(serviceEmail)) {
			properties.setValue(serviceEmail);
			propertiesRepository.save(properties);
		}
		properties = findByCode("address.job.notice");
		source = properties.getValue();
		if (!source.equals(jobNoticeEmail)) {
			properties.setValue(jobNoticeEmail);
			propertiesRepository.save(properties);
		}
		properties = findByCode("index.loan.size");
		source = properties.getValue();
		if (!source.equals(indexLoanSize)) {
			properties.setValue(indexLoanSize);
			propertiesRepository.save(properties);
		}
		properties = findByCode("auth.email.expire");
		source = properties.getValue();
		if (!source.equals(emailExpire)) {
			properties.setValue(emailExpire);
			propertiesRepository.save(properties);
		}
		properties = findByCode("auth.sms.expire");
		source = properties.getValue();
		if (!source.equals(smsExpire)) {
			properties.setValue(smsExpire);
			propertiesRepository.save(properties);
		}
		properties = findByCode("auth.realname.switch");
		source = properties.getValue();
		if (!source.equals(realnameSwitch)) {
			properties.setValue(realnameSwitch);
			propertiesRepository.save(properties);
		}
		properties = findByCode("auth.cellphone.switch");
		source = properties.getValue();
		if (!source.equals(cellphoneSwitch)) {
			properties.setValue(cellphoneSwitch);
			propertiesRepository.save(properties);
		}

		App.config(loadFromDatabase());
	}

	@Override
	public Map<String, String> loadFromDatabase() {
		// 初始化
		List<Properties> properties = findAll();
		Map<String, String> values = new HashMap<String, String>();

		// 遍历读取数据并设置
		for (Properties prop : properties) {
			values.put(prop.getCode(), prop.getValue());
		}
		// 返回结果
		values.put(HermesConstants.KEY_DATABASE, "true");
		return values;
	}
}
