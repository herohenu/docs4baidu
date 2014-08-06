
/**   
 * @Title: Constants.java
 * @Package com.baihui.haoshili.util
 * @Description: TODO(用一句话描述该文件做什么)
 * @author jason    
 * @date 2013-11-28 下午03:14:16
 */

package com.baihui.studio;


/**
 * @author jason
 */
public class Constants {
	
	/*
	 * 有关订单 
	 */
	/** 客户excel列数 */
	public static final int EXCEL_CONTACT_COLUMNS = 13;
	
	/** 冻结客户列数*/
	public static final int EXCEL_NEW_CONTACT_COLUMNS = 14;
	
	/** 新客户列数*/
	public static final int EXCEL_NEW_CONTACTS_COLUMNS = 44;
	
	/** 新销售订单列数 */
	public static final int EXCEL_NEW_SALES_ORDERS_COLUMNS = 18;
	
	/** 销售订单(本身)excel列数 */
	public static final int EXCEL_SALES_ORDERS_CONTACT_COLUMNS = 12;
	
	/** 销售订单(产品)excel列数 */
	public static final int EXCEL_SALES_ORDERS_PRODUCT_COLUMNS = 5;
	
	/** 销售订单(客户)excel列数 */
	public static final int EXCEL_SALES_ORDERS_PREZZIE_COLUMNS = 4;
	
	/** 客户excel类型 */
	public static final int EXCEL_CONTACT_TYPE = 1;
	
	/** 销售订单(客户)excel类型 */
	public static final int EXCEL_SALES_ORDERS_CONTACT_TYPE = 2;

	/** 销售订单(赠品)excel类型 */
	public static final int EXCEL_SALES_ORDERS_PREZZIE_TYPE = 3;
	
	/** 销售订单(产品)excel类型 */
	public static final int EXCEL_SALES_ORDERS_PRODUCT_TYPE = 4;
	
	/** 新销售订单excel类型 */
	public static final int EXCEL_NEW_SALES_ORDERS_PRODUCT_TYPE = 5;
	
	
	
	

	/*
	 * 通用判断1(正向思维)
	 */
	/** 通用判断1_否 */
	public static final String BRANCH_FLAG1_NO = "0";

	/** 通用判断1_是 */
	public static final String BRANCH_FLAG1_YES = "1";
	
	
	/*
	 * 有关CRM产品
	 */
	/** CRM产品请求前缀 */
	public static final String CRM_URL = "https://crm.baihui.com/crm/private/json/";
	
	/** crm产品模块名称 */
	public static final String CRM_MODULE_PRODUCTS = "Products";
	public static final String CRM_MODULE_CHINESE_PRODUCTS = "产品模块";

	/** crm产品客户名称 */
	public static final String CRM_MODULE_CONTACTS = "Contacts";
	public static final String CRM_MODULE_CHINESE_CONTACTS = "客户模块";
	
	/** crm产品销售订单名称 */
	public static final String CRM_MODULE_SALESORDERS = "SalesOrders";
	public static final String CRM_MODULE_CHINESE_SALESORDERS = "销售订单模块";
	
	/** 同步积分 */
	public static final String CRM_SYNC_POINT = "Point";
	
	/** crm查询每页数量 */
	public static final int DB2CRM_SELECT_UNIT_COUNT = 200;
	
	/** crm操作每页数量 */
	public static final int DB2CRM_DO_UNIT_COUNT = 100;
	
	/** 数据库每次提交数量 */
	public static final int DB_BATCH_DO_NUM = 1000;
	
	/*
	 * 数据库表SyncRecordTime中时间字段
	 */
	
	/** 开始同步时间 */
	public static String BEGINSYNCTIME = "beginSyncTime";
	
	/**结束同步时间*/
	public static String ENDSYNCTIME = "endSyncTime";
	
	
	/*
	 * 从哪里获取数据,DB 或  CRM
	 */
	public static String GETDATA_FROM_CRM = "getFromCRM";
	public static String GETDATA_FROM_DB = "getFromDB";
	
	
	/**
	 * 在CRM中查询的内容
	 */
	
	public static String CRM_SELECTCOLUMNS_PRODUCTS = "Products(ProductID,Product Name,Modified Time,Created Time)";
	public static String CRM_SELECTCOLUMNS_CONTACTS = "Contacts(CONTACTID,Last Name,Phone,Mobile,Created Time,Modified Time,总计积分)";
	public static String CRM_SELECTCOLUMNS_SALESORDERS = "SalesOrders(SALESORDERID,Subject,Created Time,Modified Time)";
	public static String CRM_SELECTCOLUMNS_SALESORDERS_POINT = "SalesOrders(SALESORDERID,CONTACTID,Contact Name,Subject,Created Time,Modified Time,Grand Total)";
	
	
	

}
