package com.loserstar.constants;
/**
 * 本文件通过代码生成器自动生成，请勿直接修改本文件，因为每次生成都会覆盖此文件
 * date:2020-8-6 12:09:09
 * remarks: 公共的字典常量(java后端使用)
 */
public class DictConstants {
    public static class TASK_BILL_H_STATE{
        /**
    	 * 作废
    	 */
		public static final String ABOLISH = "ABOLISH";//单据表：状态
        /**
    	 * 发布
    	 */
		public static final String PUBLISH = "PUBLISH";//单据表：状态
        /**
    	 * 完成
    	 */
		public static final String COMPLETE = "COMPLETE";//单据表：状态
	}
    public static class TASK_BILL_H_TYPE{
        /**
    	 * 入库
    	 */
		public static final String RK = "RK";//单据：类型
        /**
    	 * 出库
    	 */
		public static final String CK = "CK";//单据：类型
        /**
    	 * 移库
    	 */
		public static final String YK = "YK";//单据表：类型
	}
    public static class TASK_BILL_H_TYPE2{
        /**
    	 * 入库单
    	 */
		public static final String RK = "RK";//单据表：类型
        /**
    	 * 出库单
    	 */
		public static final String CK = "CK";//单据表：类型
        /**
    	 * 移库单
    	 */
		public static final String YK = "YK";//单据表：类型
        /**
    	 * 转储单
    	 */
		public static final String ZC = "ZC";//单据表：类型
        /**
    	 * 采购订单
    	 */
		public static final String CG = "CG";//单据表：类型
        /**
    	 * 交货单
    	 */
		public static final String JH = "JH";//单据表：类型
        /**
    	 * 预留单
    	 */
		public static final String YL = "YL";//单据表：类型
	}
    public static class MASTER_GOODS_LOCA_IS_USED{
        /**
    	 * 空闲
    	 */
		public static final String free = "0";//货位表：占用状态
        /**
    	 * 拟预定
    	 */
		public static final String draft_reserve = "4";//货位表：占用状态
        /**
    	 * 预订
    	 */
		public static final String reserve = "2";//货位表：占用状态
        /**
    	 * 占用
    	 */
		public static final String used = "1";//货位表：占用状态
	}
    public static class MASTER_GOODS_LOCA_IS_UNLIMITED{
        /**
    	 * 无限制
    	 */
		public static final String yes = "1";//货位表：是否无限制容量
        /**
    	 * 限制
    	 */
		public static final String no = "0";//货位表：是否无限制容量
	}
    public static class MASTER_BUS_TYPE_BUS_OBJ_TYPE{
        /**
    	 * 供应商
    	 */
		public static final String lifnr = "lifnr";//业务类型表：业务对象类型
        /**
    	 * 工厂
    	 */
		public static final String factory = "factory";//业务类型表：业务对象类型
        /**
    	 * 仓库
    	 */
		public static final String warehouse = "warehouse";//业务类型表：业务对象类型
        /**
    	 * 部门
    	 */
		public static final String dept = "dept";//业务类型表：业务对象类型
        /**
    	 * 其它
    	 */
		public static final String ohter = "ohter";//业务类型表：业务对象类型
	}
    public static class MASTER_BUS_TYPE_STOCK_TYPE{
        /**
    	 * 非限制库存
    	 */
		public static final String FXZ = "FXZ";//业务类型表：库存类型
        /**
    	 * 质检库存
    	 */
		public static final String ZJ = "ZJ";//业务类型表：库存类型
        /**
    	 * 冻结库存
    	 */
		public static final String DJ = "DJ";//业务类型表：库存类型
        /**
    	 * 在途库存
    	 */
		public static final String ZT = "ZT";//业务类型表：库存类型
	}
    public static class MASTER_BUS_TYPE_TRANSFER_TYPE{
        /**
    	 * 入库
    	 */
		public static final String RK = "RK";//业务类型表：移库类型
        /**
    	 * 出库
    	 */
		public static final String CK = "CK";//业务类型表：移库类型
        /**
    	 * 移库
    	 */
		public static final String YK = "YK";//业务类型表：移库类型
        /**
    	 * 转储
    	 */
		public static final String ZC = "ZC";//业务类型表：移库类型
	}
    public static class MASTER_BUS_TYPE_BORROW_LOAN_FLAG{
        /**
    	 * 借
    	 */
		public static final String S = "S";//业务类型表：借贷标识
        /**
    	 * 贷
    	 */
		public static final String H = "H";//业务类型表：借贷标识
	}
    public static class MOVE_VOUCHER_I_BORROW_LOAN_FLAG{
        /**
    	 * 借
    	 */
		public static final String S = "S";//移动凭证行项目表：借贷标识
        /**
    	 * 贷
    	 */
		public static final String H = "H";//移动凭证行项目表：借贷标识
	}
}
