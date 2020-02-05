package com.bp.common.constants;


/**
 * @author
 * @version 1.0
 * @Description: 系统全局常量
 * @date 2016年8月1日
 */
public class SysConstant {

	/**
	 * 超级管理员角色code
	 */
	public static final String ADMIN = "admin";

	/**
	 * 用户中心加密key
	 */
	public static String PASS_KEY="b0qk7cqwntc0ttqx";

	/**
	 * 字典顶级父id
	 */
	public static final String DIC_PID = "0";

	/**
	 * session用户key
	 */
	public static final String CURRENT_USER = "currentUser";

	/**
	 * 分页起始数
	 */
	public static final String START = "start";

	/**
	 * 每页显示数量
	 */
	public static final String LENGTH = "length";

	/**
	 * 每页限制数量
	 */
	public static final String LIMIT = "limit";

	/**
	 * 分页页数
	 */
	public static final String PAGE = "page";

	/**
	 * 逗号常量
	 */
	public static final String COMMA = ",";


	/**
	 * 日志类别
	 */
	public enum logType{
		/**
		 * 登录日志类别
		 */
		LOGIN("0"),
		/**
		 * 操作日志类别
		 */
		DO("1"),
		/**
		 * 异常日志类别
		 */
		EXCEPTION("2");

		logType(String type) {
			this.type = type;
		}

		private String type;

		public String getType() {
			return type;
		}
	}

	/**
	 * 任务状态
	 */
	public enum jobStaus{
		/**
		 * 启动任务
		 */
		START("0"),
		/**
		 * 停止任务
		 */
		STOP("1");

		jobStaus(String status) {
			this.status = status;
		}

		private String status;

		public String getStatus() {
			return status;
		}
	}

	/**
	 * 用户账号状态
	 */
	public enum userStatus{
		/**
		 * 正常的
		 */
		NORMAL("0"),
		/**
		 * 禁用的
		 */
		FORBIDDEN("1");

		userStatus(String status) {
			this.status = status;
		}

		private String status;

		public String getStatus() {
			return status;
		}
	}

	/**
	 * 记分类型
	 */
	public enum gradeTypeCode{
		/**
		 * 扣分
		 */
		KF("01"),
		/**
		 * 奖分
		 */
		JF("02"),
		/**
		 * 罚分
		 */
		FF("03"),
		/**
		 * 突出表现
		 */
		TCBX("04"),
		/**
		 * 严重违纪
		 */
		YZWJ("05");

		gradeTypeCode(String code) {
			this.code = code;
		}
		private String code;

		public String getCode() {
			return code;
		}
	}

	/**
	 * 记分类型
	 */
	public enum gradeIndexCode{
		/**
		 * 遵规守纪
		 */
		ZGSJ("01"),
		/**
		 * 戒毒康复
		 */
		JDKF("02"),
		/**
		 * 教育矫治
		 */
		JYJZ("03"),
		/**
		 * 康复劳动
		 */
		KFLD("04"),
		/**
		 * 坦白检举
		 */
		TBJJ("05");

		gradeIndexCode(String code) {
			this.code = code;
		}
		private String code;

		public String getCode() {
			return code;
		}
	}

	/**
	 * 收治情况类型
	 */
	public enum szqkCode{
		/**
		 * 同意接收
		 */
		tyjs("同意接收"),
		/**
		 * 不予接收
		 */
		byjs("不同意接收"),
		/**
		 * 暂不接收
		 */
		zbjs("暂不接收");

		szqkCode(String code) {
			this.code = code;
		}
		private String code;

		public String getCode() {
			return code;
		}
	}

	/**
	 * 教育适应区阶段性诊断评估成绩类别(BaseStepSecond)
	 */
	public enum stepSecondScoreType{
		/**
		 * 戒毒医疗考核结果
		 */
		jdyl("A2"),
		/**
		 * 教育矫正考核结果
		 */
		jyjz("E1"),
		/**
		 * 康复训练考核结果
		 */
		kfxl("H1");

		stepSecondScoreType(String type) {
			this.type = type;
		}
		private String type;

		public String getType() {
			return type;
		}
	}

	/**
	 * 康复巩固阶段性诊断评估成绩类别(BaseStepThird)
	 */
	public enum stepThirdScoreType{
		/**
		 * 戒毒医疗考核结果
		 */
		jdyl("A3"),
		/**
		 * 教育矫正考核结果
		 */
		jyjz("E2"),
		/**
		 * 心理矫治考核结果
		 */
		xljz("K"),
		/**
		 * 行为表现考核结果
		 */
		xwbx("C"),
		/**
		 * 社会环境与适应能力考核结果D
		 */
		shhjysynl("D"),
		/**
		 * 康复训练考核结果
		 */
		kfxl("H2");

		stepThirdScoreType(String type) {
			this.type = type;
		}
		private String type;

		public String getType() {
			return type;
		}
	}

	/**
	 * 一年后综合诊断评估成绩类别(BaseStepFourth)
	 */
	public enum stepFourthScoreType{
		/**
		 * 生理脱毒区评估结果
		 */
		sltd("M1"),
		/**
		 * 教育适应区评估结果
		 */
		jysy("M2"),
		/**
		 * 康复巩固评估结果
		 */
		kfgg("M3");

		stepFourthScoreType(String type) {
			this.type = type;
		}
		private String type;

		public String getType() {
			return type;
		}
	}

	/**
	 * 记分类型
	 */
	public enum stepStatus{
		/**
		 * 有效
		 */
		yx("1"),
		/**
		 * 无效
		 */
		wx("0");

		stepStatus(String status) {
			this.status = status;
		}
		private String status;

		public String getStatus() {
			return status;
		}
	}

	/**
	 * 分期情况
	 */
	public enum fqqkCode{

		/**
		 * 入所前
		 */
		rsq("00"),

		/**
		 * 生理脱毒区
		 */
		sltdq("01"),
		/**
		 * 教育适应区
		 */
		jysyq("02"),
		/**
		 * 康复巩固区
		 */
		kfggq("03"),
		/**
		 * 回归指导区
		 */
		hgzdq("04"),
		/**
		 * 其他
		 */
		qt("99");

		fqqkCode(String code) {
			this.code = code;
		}
		private String code;

		public String getCode() {
			return code;
		}
	}

	/**
	 * 转区状态类型
	 */
	public enum transferAreaType{
		/**
		 * 待转
		 */
		dz("0"),
		/**
		 * 已转
		 */
		yz("1");

		transferAreaType(String type) {
			this.type = type;
		}
		private String type;

		public String getType() {
			return type;
		}
	}

	/**
	 * 部门类型
	 */
	public enum bmlx{
		/**
		 * 直属大队
		 */
		three("03"),
		/**
		 * 直属中队
		 */
		four("04");

		bmlx(String type) {
			this.type = type;
		}
		private String type;

		public String getType() {
			return type;
		}
	}
	/**
	 * 戒毒人员状态
	 */
	public enum jzryzt{
		/**
		 * 在所
		 */
		zs("01"),
		/**
		 * 离所就医
		 */
		lsjy("02"),
		/**
		 * 探视
		 */
		ts("03"),
		/**
		 * 所外就医
		 */
		swjy("04"),
		/**
		 * 死亡
		 */
		sw("05"),
		/**
		 * 脱逃
		 */
		tt("06"),
		/**
		 * 逮捕
		 */
		db("07"),
		/**
		 * 刑事拘留
		 */
		xsjl("09"),
        /**
         * 离所（其他）
         */
        ls("10"),
        /**
         * 解除
         */
        jc("11"),
        /**
         * 撤销
         */
        cx("12"),
        /**
         * 收监执行
         */
        sjzx("13"),
        /**
         * 其他
         */
        qt("99");

		jzryzt(String jzryzt) {
			this.jzryzt = jzryzt;
		}
		private String jzryzt;

		public String getJdryzt() {
			return jzryzt;
		}
	}

    /**
     * 收支类别
     */
    public enum szlb{
        /**
         * 收入
         */
        one("01"),
        /**
         * 支出
         */
        two("02");

        szlb(String type) {
            this.type = type;
        }
        private String type;

        public String getType() {
            return type;
        }
    }

    /**
     * 流程状态
     */
    public enum docState{

         finish("流程已完成");

        docState(String docState){this.docState = docState;}
        private String docState;
        public String getDocState(){return docState;}
    }

	/**
	 * 月行为审核状态
	 */
	public enum state{
		/**
		 * 待提交
		 */
		dtj(0),
		/**
		 * 待中队合议
		 */
		dzdhy(1),
		/**
		 * 待中队审批
		 */
		dzdsp(2),
		/**
		 * 待大队合议
		 */
		dddhy(3),
		/**
		 * 待大队审批
		 */
		dddsp(4),
		/**
		 * -待大队结束公示
		 */
		dddjsgs(5),
		/**
		 * 待管理科合议
		 */
		dglkhy(6),
		/**
		 * 待管理科审批
		 */
		dglksp(7),
		/**
		 * 待所领导审批
		 */
		dsldsp(8),
		/**
		 * 待大队上传公示材料
		 */
		dddscgscl(9),
		/**
		 * 审批完成
		 */
		spwc(10);
		state(Integer state) {
			this.state = state;
		}
		private Integer state;

		public Integer getState() {
			return state;
		}
	}

}
