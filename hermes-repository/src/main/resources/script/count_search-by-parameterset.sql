select count(h2.id) from hm_dictionary h1
left join hm_dictionary_type h2 on h1.type = h2.id  GROUP BY h2.id %s