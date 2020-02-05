package com.bp.common.base;

import com.bp.common.bean.ReturnBean;
import com.bp.common.dto.ActRollBackEntity;
import org.springframework.web.bind.annotation.*;

public abstract class BaseActController extends BaseController {

    public abstract BaseServiceImplByAct getBaseServiceImplByAct();

    /* *
     *
     *  根据流程实例ID修改流程状态状态
     * @param pid 流程实例ID
     * @param docState 单据状态
     * @param flowState 流程状态
     * @return 修改结果
     *
     */
    @GetMapping("api/updateFlowState/{pid}/{docState}/{flowState}")
    public ReturnBean updateFlowState(@PathVariable String pid, @PathVariable String docState, @PathVariable Integer flowState){
        getBaseServiceImplByAct().updateWorkFlowStateByPid(pid,docState,flowState);
        return ReturnBean.ok();
    }
    /**
     * 流程结束时执行的业务逻辑
     *
     * @param actRollBackEntity 流程参数
     * @return
     */
    @PostMapping("api/doFinishBusiness")
    public ReturnBean doFinishBusiness(@RequestBody ActRollBackEntity actRollBackEntity) {
        getBaseServiceImplByAct().doFinishBusiness(actRollBackEntity);
        return ReturnBean.ok();
    }

    /**
     *  任务结束时执行的业务逻辑
     * @param actRollBackEntity 流程参数
     * @return
     */
    @PostMapping("api/taskComplete")
    public ReturnBean taskComplete(@RequestBody ActRollBackEntity actRollBackEntity)
    {
        System.out.println("actRollBackEntity1 = " + actRollBackEntity);
        getBaseServiceImplByAct().taskComplete(actRollBackEntity);
        return ReturnBean.ok();

    }
    @PostMapping("api/taskCreate")
    public ReturnBean taskCreate(@RequestBody ActRollBackEntity actRollBackEntity) {
        //todo 消息通知
        System.out.println("actRollBackEntity2 = " + actRollBackEntity);
        getBaseServiceImplByAct().taskCreate(actRollBackEntity);
        return ReturnBean.ok();
    }

    /**
     * 根据流程實例ID查詢
     * @return
     */
    @GetMapping("findByPid/{pid}")
    public ReturnBean findByPid(@PathVariable("pid") String pid) {
       Object o = getBaseServiceImplByAct().findByPid(pid);
        return ReturnBean.ok(o);
    }

}
