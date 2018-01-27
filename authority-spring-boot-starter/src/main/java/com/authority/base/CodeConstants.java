package com.authority.base;
/**
 * 业务定义
 */
public class CodeConstants {

	
	 public final static int BC_ADDRESS_VERSION = 1;
	 public final static int OK = 200;
	
	/********************4xx 请求错误**********************************/
	 /**
	  * 错误请求
	  */
	 public final static int BAD_REQUEST = 400;
	 
	 /**
	  * 参数错误,不能为空
	  */
	 public final static int PARAMETER_ERROR_NULl = 401;
	 /**
	  * 签名错误
	  */
	 public final static int SINGATURE_ERROR = 402;
	 
	 /**
	  * 禁止访问
	  */
	 public final static int URL_FORBIDDEN = 403;
	 
	 /**
	  * 并发处理错误，重复提交订单
	  */
	 public final static int CONCURRENT_PROCESS_ERROR = 403;
	 
	 /**
	  * 实体无法找到
	  */
	 public final static int ITEM_NOT_FIND = 404;
	 /**
	  * 实体已经存在
	  */
	 public final static int ITEM_EXITS = 407;
	
	 /**
	  * 地址解析错误
	  */
	 public final static int ADDRESS_PARSER_ERROR = 406;
	 
	 /**
	  * 订单已经锁定
	  */
	 public final static int  ORDER_APPLY_LOCKER = 408;
	
	 /**
	  * 订单已经提交，正在处理
	  */
	 public final static int  ORDER_APPLY_SUBMIT = 409;
	 
	 public final static int METHOD_NOT_ALLOW = 405;
	 /**
	  * 先觉得条件未满足
	  */
	 public final static int PREREQUISITE_NOT_SUPPORT = 412;
	 
	 public final static int MEDIA_NOT_SUPPORT = 415;
	 /**
	  * 订单冲突
	  */
	 public final static int ORDER_CONCURRENT_LIMIT = 416;
	 
	 /**
	  * 用户未授权
	  */
	 public final static int USER_UN_AUTHORIZED = 417;
	 
	 /*****************5xxx 是服务错误*******************************************/
	 /**
	  * 服务器内部处理错误
	  */
	 public final static int SEVER_INNER_ERROR = 500;
	 /**
	  * 流程处理错误
	  */
	 public final static int  EXECUTE_PROCESS_ERROR = 501;
	 /**
	  * 执行失败
	  */
	 public final static int  EXECUTE_FAIL_ERROR = 502;
	 /**
	  * 请求其他服务器失败
	  */
	 public final static int  EXECUTE_REMOTE_ERROR = 503;
}
