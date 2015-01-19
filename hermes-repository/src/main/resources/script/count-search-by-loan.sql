select count(*)  
    from hm_loan hl
  inner join hm_product hp on hl.product = hp.id 
  inner join hm_repay hr on hp.repay= hr.id
  left  join hm_dictionary hd on hl.purpose = hd.id 
 where hp.status='00' and hr.status='00'
 and hl.status in('10','11','12','99')
  %s