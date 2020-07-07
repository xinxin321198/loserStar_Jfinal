<#setting classic_compatible=true>
package com.kaen.constants;
/**
 * 本文件通过代码生成器自动生成，请勿直接修改本文件，因为每次生成都会覆盖此文件
 * date:${.now}
 * remarks: 公共的字典常量(java后端使用)
 */
public class DictConstants {
    <#list data as map>
    public static class ${map.type}{
        <#list map.list as dict>
        /**
    	 * ${dict.name}
    	 */
		public static final String ${dict.c_name} = "${dict.value}";//${dict.remarks}
        </#list>
	}
    </#list>
}
