select h1.id,h2.name as paramType,h1.name as paramValue,h1.status from hm_dictionary h1
left join hm_dictionary_type h2 on h1.type = h2.id %s