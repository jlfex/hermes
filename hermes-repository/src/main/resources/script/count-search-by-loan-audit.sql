select  count(hu.id) from hm_loan hl
left join hm_user hu on hl.user=hu.id
where hu.status='00' GROUP BY hu.id
%s