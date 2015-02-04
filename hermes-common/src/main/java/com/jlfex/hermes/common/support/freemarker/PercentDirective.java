package com.jlfex.hermes.common.support.freemarker;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 标 进度百分比特殊处理
 * @author Administrator
 *
 */
public class PercentDirective implements TemplateDirectiveModel {
	private static final String PARAM_NAME_TOTAL	= "total";
	private static final String PARAM_NAME_REMAIN   =  "remain";
	
	@SuppressWarnings("rawtypes")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		if (!params.containsKey(PARAM_NAME_TOTAL)) {
			throw new TemplateModelException("key is necessary!");
		}
		if (!params.containsKey(PARAM_NAME_REMAIN)) {
			throw new TemplateModelException("key is necessary!");
		}
		String result = "";
		String total = params.get(PARAM_NAME_TOTAL).toString().replace(",", "").trim();
		String remain = params.get(PARAM_NAME_REMAIN).toString().replace(",", "").trim();
		if(total.equals("0") || total.replace("0", "").length() == 0 || total.replace("0", "").equals(".")){
			result = "0";
		}else{
			BigDecimal D_total = new BigDecimal(total);
			BigDecimal D_remain = new BigDecimal(remain);
			BigDecimal D_precess = D_total.subtract(D_remain);
			BigDecimal progress = D_precess.divide(D_total,2,RoundingMode.FLOOR).multiply(new BigDecimal("100"));
			result = progress.toString();
			if(result.endsWith(".00")){
				result = result.replace(".00", "");
			}
		}
		env.getOut().write(result+"%");
	}
}
