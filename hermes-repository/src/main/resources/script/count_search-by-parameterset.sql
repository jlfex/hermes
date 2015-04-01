select count(*) from hm_dictionary_type h2
left join hm_dictionary h1 on h1.type = h2.id %s