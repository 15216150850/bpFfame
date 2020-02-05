package com.bp.common.constants;

/**
 * 工作流流程状态
 * @author yanjisheng
 *
 */
public enum FlowState {

	/**
	 * 未提交
	 */
	NOT_COMMITTED(0), 
	
	/**
	 * 已提交
	 */
	COMMITTED(1), 
	
	/**
	 * 审核通过
	 */
	PASSED(2), 
	
	/**
	 * 驳回
	 */
	DISALLOWED(3), 
	
	/**
	 * 关闭
	 */
	CLOSED(4), 
	
	/**
	 * 流程进行中
	 */
	PROCESSING(5), 
	
	/**
	 * 审核不通过
	 */
	REJECTED(6), 
	
	/**
	 * 流程结束
	 */
	END(7),

    /**
     * 撤销
     */
    REVOKE(99);

	
	private int num;

	private FlowState(int num) {
		this.num = num;
	}
	
	public int getState() {
		return this.num;
	}
}
