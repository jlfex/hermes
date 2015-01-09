select count(*) from hm_dictionary h1
left join hm_dictionary_type h2 on h1.type = h2.id %s