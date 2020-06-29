<#setting classic_compatible=true>
package com.kaen.constants;
/**
 * date:${.now}
 * remarks: 字典常量
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
