select count(*)  
  from hm_invest_profit hip
  inner join hm_loan_repay hlr on hip.loan_repay = hlr.id 
  inner join hm_loan hl on hlr.loan= hl.id 
  %s