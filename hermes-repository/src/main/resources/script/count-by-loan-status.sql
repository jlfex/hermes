select l.status, count(*)
  from hm_loan l
 where l.status in (:status)
 group by l.status
