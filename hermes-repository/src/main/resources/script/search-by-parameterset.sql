SELECT
	h1.id,
	h2. NAME AS paramType,
	h1. NAME AS paramValue,
	h1. STATUS
FROM
	hm_dictionary h1
LEFT JOIN hm_dictionary_type h2 ON h1.type = h2.id %s
ORDER BY
	h1.create_time DESC