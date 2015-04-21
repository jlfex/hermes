package com.jlfex.hermes.common.support.freemarker;
import java.io.IOException;
import java.util.Map;
import com.jlfex.hermes.common.App;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 国际化消息指令
 */
public class MessagesDirective implements TemplateDirectiveModel {

	private static final String PARAM_NAME_KEY	= "key";
	
	/* (non-Javadoc)
	 * @see freemarker.template.TemplateDirectiveModel#execute(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateModel[], freemarker.template.TemplateDirectiveBody)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		if (!params.containsKey(PARAM_NAME_KEY)) {
			throw new TemplateModelException("key is necessary!");
		}
		env.getOut().write(App.message(params.get(PARAM_NAME_KEY).toString()));
	}
}
