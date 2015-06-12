select hl.user_id,hd.name as purpose,
round(hl.amount) as amount,
hl.rate as rate ,
hl.period,round(hl.proceeds) as proceeds,
round(hl.amount-hl.proceeds) as remain,
round(hl.proceeds/hl.amount*100) as progess,
hr.name,hl.status,hl.id,hl.purpose as loanPurpose,
hl.loan_no as applicationNo, hl.loan_kind as loanKind, hl.deadline
  from hm_loan hl
  inner join hm_product hp on hl.product = hp.id 
  inner join hm_repay hr on hp.repay= hr.id
  left  join hm_dictionary hd on hl.purpose = hd.id 
 where hp.status='00' and hr.status='00'
 and hl.status in('10','11','12','99')
  %s
  