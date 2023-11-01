<#setting classic_compatible=true>
package ${packgeName};
/**
 * 本文件通过代码生成器自动生成，请勿直接修改本文件，因为每次生成都会覆盖此文件
 * date:${.now}
 * remarks: 公共的字典常量(java后端使用)
 */
public class DictConstants {
    <#list data as map>
    public static class ${map.dict_type}{
    	/**
    	 * 获取字典分类的标识字符串
    	 */
    	public static String getDictTypeStr(){
    		return "${map.dict_type}";
    	}
        <#list map.list as dict>
        /**
    	 * ${dict.dict_name}
    	 */
		public static final String ${dict.dict_c_name} = "${dict.dict_value}";//${dict.dict_remarks}
        </#list>
	}
    </#list>
}
