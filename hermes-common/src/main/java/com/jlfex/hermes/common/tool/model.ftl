package ${package};

/**
 * ${table.name}模型
 *
 * @version 1.0, ${today}
 * @since 1.0
 */
@Entity
@Table(name = "${table.code}")
public class ${table.className} extends Model {
	
	<#list table.columns as col>
	<#if col.show>
	/** ${col.name} */
	@Column(name = "${col.code}")
	private ${col.type} ${col.fieldName};
	
	</#if>
	</#list>
	
	<#list table.columns as col>
	<#if col.show>
	/**
	 * 读取${col.name}
	 * 
	 * @return
	 * @see #${col.fieldName}
	 */
	public ${col.type} get${col.methodName}() {
		return ${col.fieldName};
	}
	
	/**
	 * 设置${col.name}
	 * 
	 * @param ${col.fieldName}
	 * @see #${col.fieldName}
	 */
	public void set${col.methodName}(${col.type} ${col.fieldName}) {
		this.${col.fieldName} = ${col.fieldName};
	}
	
	</#if>
	</#list>
}
