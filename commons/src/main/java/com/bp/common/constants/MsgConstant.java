package com.bp.common.constants;


/**
 * @author xcj
 * @version 1.0
 * @Description: 系统全局常量
 * @date 2019年12月5日
 */
public class MsgConstant {

	/**
     * 新闻消息-发送对象类型-人员
     */
    public static final String RECEIVE_TYPE_RY = "1";

    /**
     * 新闻消息-发送对象类型-所有人
     */
    public static final String RECEIVE_TYPE_ALL = "5";

	/**
	 * 新闻消息-发送对象类型-部门
	 */
	public static final String RECEIVE_TYPE_BM = "2";

	/**
	 * 新闻消息-发送对象类型-系统角色
	 */
	public static final String RECEIVE_TYPE_XTJS = "3";
	/**
	 * 新闻消息-发送对象类型-流程角色
	 */
	public static final String RECEIVE_TYPE_LCJS = "4";

	/**
	 * 消息通知-标识-系统通知
	 */
	public static final String  MSG_SIGN_XXTZ= "1";
	/**
	 * 消息通知-标识-待办事项
	 */
	public static final String MSG_SIGN_DBSX = "2";
	/**
	 * 消息通知-标识-工作提醒
	 */
	public static final String MSG_SIGN_GZTX = "3";
	/**
	 * 消息通知-标识-通知公告
	 */
	public static final String MSG_SIGN_TZGG = "4";


	/**
	 * 消息类型
	 */
	public enum type{
		//系统发送消息
		xttz("0"),

		//人为发送通知
		rwtz("1");

		type(String type) {
			this.type = type;
		}
		private String type;

		public String getType() {
			return type;
		}

	}

	/**
	 * 消息状态
	 */
	public enum status{
		//正常
		ok("正常"),

		//删除
		delete("删除");

		status(String status){
			this.status = status;
		}
		private String status;
		public String getStatus(){
			return status;
		}
	}


}
