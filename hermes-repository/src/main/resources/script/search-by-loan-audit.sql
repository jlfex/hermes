select  hu.account,hl.loan_no,hl.amount,hl.id,hl.status,hup.real_name,hu.cellphone,hl.period,hl.rate,hl.datetime from hm_loan hl
left join hm_user hu on hl.user=hu.id
left join hm_user_properties hup on hl.user= hup.user
where hu.status='00' 
%s 
order by hl.datetime desc