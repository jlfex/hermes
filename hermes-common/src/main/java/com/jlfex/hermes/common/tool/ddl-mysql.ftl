<#list tables as tab>
--
-- 表的结构 `${tab.code}`
--

CREATE TABLE IF NOT EXISTS ${tab.code} (
	<#list tab.columns as col>
	`${col.code}` ${col.dataType} COLLATE utf8_unicode_ci <#if col.mandatory>NOT NULL </#if>COMMENT '${col.name}',
	</#list>
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='${tab.name}';

-- --------------------------------------------------------

</#list>
