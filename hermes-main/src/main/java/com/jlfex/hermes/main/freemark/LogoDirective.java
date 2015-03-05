package com.jlfex.hermes.main.freemark;

import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.Properties;
import com.jlfex.hermes.model.Text;
import com.jlfex.hermes.repository.PropertiesRepository;
import com.jlfex.hermes.service.TextService;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * logo 图标实时读取
 * @author Administrator
 *
 */
public class LogoDirective implements TemplateDirectiveModel {
   
	@Autowired
	private PropertiesRepository propertiesRepository;
	@Autowired
	private TextService textService;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Properties properties = propertiesRepository.findByCode("app.logo");
		Text text = textService.loadById(properties.getValue());
		if(text == null){
			Logger.error("logo图标信息为空");
			env.getOut().write("");
		}
		String logoBase64Code = text.getText();
		if (!Strings.empty(logoBase64Code)) {
			env.getOut().write(logoBase64Code);
		}else{
			Logger.error("logo图标没有初始化，请到后台配置");
		}
	}
}
