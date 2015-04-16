SELECT
	h2.id,
	h2. NAME AS paramType,
	h1. NAME AS paramValue,
	h1. STATUS,
	h1.id AS dicId,
	h2.STATUS AS typeStatus,
	h1.code AS code,
	h2.code AS typeCode
FROM
	hm_dictionary_type h2
LEFT JOIN hm_dictionary h1 ON h1.type = h2.id %s
ORDER BY
	h1.update_time DESC