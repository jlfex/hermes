package com.jlfex.hermes.console.freemark;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jlfex.hermes.model.Loan;
import com.jlfex.hermes.repository.LoanRepository;
import com.jlfex.hermes.service.DictionaryService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 风险金：备注信息查询
 * @author Administrator
 *
 */
public class RiskDirective implements TemplateDirectiveModel {
   
	@Autowired
	private LoanRepository loanRepository;
	@Autowired
	private DictionaryService dictionaryService;
	private static final String PARAM_NAME_KEY	= "val";
	
	/* (non-Javadoc)
	 * @see freemarker.template.TemplateDirectiveModel#execute(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateModel[], freemarker.template.TemplateDirectiveBody)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String loanNameAndCode = "";
		if (!params.containsKey(PARAM_NAME_KEY)) {
			throw new TemplateModelException("key is necessary!");
		}
		String referenceId = params.get(PARAM_NAME_KEY).toString();
		Loan  loan = loanRepository.findOne(referenceId);
		if(loan!=null){
			String purpose = loan.getPurpose();
			if(Loan.LoanKinds.NORML_LOAN.equals(loan.getLoanKind())){
				purpose = dictionaryService.loadById(loan.getPurpose()).getName(); 
			}
			loanNameAndCode = purpose+"("+loan.getLoanNo()+")";
		}
		env.getOut().write(loanNameAndCode);
	}
}
