select l.status, count(*)
  from hm_loan l
 where exists (select 1 from hm_loan_repay lr where lr.loan = l.id and lr.status = '20')
 group by l.status