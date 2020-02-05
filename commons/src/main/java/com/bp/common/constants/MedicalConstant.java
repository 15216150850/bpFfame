package com.bp.common.constants;


/**
 * @author
 * @version 1.0
 * @Description: 医疗系统服务全局变量
 * @date 2016年8月1日
 */
public class MedicalConstant {

	/**
	 * 体检类别
	 */
	public enum tjlbCode{
		/**
		 * 入所体检
		 */
		rstj("01"),
		/**
		 * 常规体检
		 */
		cgtj("02"),
		/**
		 * 专项体检
		 */
		zztj("03"),
		/**
		 * 出所体检
		 */
		cstj("04"),
		/**
		 * 其他
		 */
		qt("99");

		tjlbCode(String code) {
			this.code = code;
		}
		private String code;

		public String getCode() {
			return code;
		}
	}

	/**
	 * 体检状态
	 */
	public enum tjStatus{
		/**
		 * 待体检
		 */
		dtj("0"),

		/**
		 * 体检中
		 */
		tjz("1"),

		/**
		 * 体检完
		 */
		tjw("2");

		tjStatus(String status) {
			this.status = status;
		}

		private String status;

		public String getStatus() {
			return status;
		}
	}

	/**
	 * 人员类型
	 */
	public enum rylxType{
		/**
		 * 警察
		 */
		jc("01"),
		/**
		 * 职工
		 */
		zg("02");

		rylxType(String type) {
			this.type = type;
		}
		private String type;

		public String getType() {
			return type;
		}
	}

	/**
	 * 筛查结果
	 */
	public enum csjgType{
		/**
		 * 阴性
		 */
		yinxing("01"),
		/**
		 * 职工
		 */
		yangxing("02");

		csjgType(String type) {
			this.type = type;
		}
		private String type;

		public String getType() {
			return type;
		}
	}

}
