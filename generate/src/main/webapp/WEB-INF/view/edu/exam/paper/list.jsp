<!DOCTYPE html>
<html>
<head >
    <title>exam_paper</title>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="/layuiadmin/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="/layuiadmin/style/admin.css" media="all"/>
    <link rel="stylesheet" href="/assets/css/layui_bp_css/common.css"/>
</head>
<body>
<div class="bp_wapper">
    <!-- 顶部操作 -->
    <div class="nav-top">
       <div class="left">
            <div>
                <span id="insert"  permission="edu/exam:paper:insert"><i class="layui-icon layui-icon-add-1"></i>添加</span>
                <span id="update"  permission="edu/exam:paper:update"><i class="layui-icon layui-icon-edit" ></i>编辑</span>
                <span id="delete"  permission="edu/exam:paper:delete"><i class="layui-icon layui-icon-delete"></i>删除</span>
            </div>
        </div>
        <div class="right">
            <input type="text" id="keyWord" placeholder="请输入标题或作者">
            <i id="searchBtn" class="layui-icon layui-icon-search"></i>
            <!-- 详细查询 -->
            <div class="layui-btn layui-btn-primary see-detail" id="detailBtn">
                <i class="layui-icon layui-icon-find-fill" style="margin-right: 10px;"></i><span >展开筛选</span>
            </div>
        </div>
    </div>
    <!-- 展开筛选 -->
    <div class="detail">
        <form action="" class="layui-form" id="bpForm">
            <div class="layui-row layui-col-space15 layui-form-item">
                <div class="layui-col-lg3 layui-col-xs3 layui-col-sm3">
                    <label class="layui-form-label">测试筛选</label>
                    <div class="layui-input-block">
                        <input type="text" name="test1" id="test1" autocomplete="off" class="layui-input" >
                    </div>
                </div>
                <div class="layui-col-lg3 layui-col-xs3 layui-col-sm3">
                    <label class="layui-form-label">测试筛选</label>
                    <div class="layui-input-block">
                        <input type="text" name="test2" id="test2" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item" id="submit">
                <div style="text-align: center">
                    <button class="layui-btn layui-btn-primary" lay-submit lay-filter="formDemo"><i class="layui-icon layui-icon-search"></i>查询</button>
                    <button type="reset" class="layui-btn layui-btn-primary"><i class="layui-icon layui-icon-refresh"></i>重置</button>
                </div>
            </div>
        </form>
    </div>

        <!-- 表格 -->
    <div class="content">
        <div>
            <table class="layui-hide" lay-filter="bpTable" id="bpTable"></table>
        </div>
    </div>
</div>
<!-- jquery -->
<script src="/assets/libs/jquery/jquery-3.3.1.min.js"></script>
<!-- pace -->
<script src="/assets/libs/pace/pace.min.js"></script>
<!-- layui -->
<script src="/layuiadmin/layui/layui.js"></script>
<!-- layer -->
<script src="/assets/libs/layer/layer.js"></script>
<!-- 自定义 -->
<script src="/assets/js/common.js"></script>
<!--jq.js-->
<script src="/js/jq.js"></script>
<script type="text/javascript">
    var pers = checkPermission();
    layui.config({
        base: '/layuiadmin/', //静态资源所在路径
        tablePlug: '/layuiadmin/treetable-lay/tablePlug.js'
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'console', 'jquery', 'form', 'table'], function () {
        // 引入jquery
        var $ = jQuery = layui.$;
        var table = layui.table;
        var form = layui.form;

        //子页面执行刷新的方法
        window.top.reloadDataTable = function () {
            table.reload("bpTable");
        };
        table.render({
            elem: "#bpTable",
            id: "bpTable",
            autoSort: false, //禁用前端自动排序。注意：该参数为 layui 2.4.4 新增
            url: basePath+"/edu/exam/paper/list",
            method: "get",
            height:"full-100",
            cellMinWidth: 100,
            where: {},
            cols: [
                [
                    {
                        type: "checkbox"
                    }
                            ,{
                                field: "title",
                                title: "试卷标题",
                                align: "center"
                            }
                            ,{
                                field: "type",
                                title: "试卷类别",
                                align: "center"
                            }
                            ,{
                                field: "description",
                                title: "试卷详情",
                                align: "center"
                            }
                            ,{
                                field: "duration",
                                title: "考试用时",
                                align: "center"
                            }
                            ,{
                                field: "totalScore",
                                title: "总分",
                                align: "center"
                            }
                            ,{
                                field: "status",
                                title: "是否启用",
                                align: "center"
                            }
                ]
            ],
            page: true,
            //checkStatus: {
            //   primaryKey: 'id'
            //}
        });

        //监听行单击事件（单击事件为：rowDouble）打开详情页
        table.on('rowDouble(bpTable)', function (obj) {
            var data = obj.data;
            openDialogView("查看exam_paper信息", "/views/edu/exam/paper/see.html?uuid=" + data.uuid, "1000px", "600px");
        });

        //监听排序事件
        table.on('sort(bpTable)', function(obj){
            table.reload('bpTable', {
                initSort: obj ,
                where: {
                    field: obj.field ,//排序字段
                    order: obj.type //排序方式
                }
            });
        });

        //筛选
        var v = false;
        $('#detailBtn').on('click',function(){
            if(v){
                $("#detailBtn span").text('展开筛选');
                v = false;
            }else{
                $("#detailBtn span").text('收起筛选');
                v = true;
            }
            $(".detail").slideToggle("slow");
        });

        //刷新
        $("#searchBtn").click(function () {
            var keyWord = $('#keyWord');
            //执行重载
            table.reload('bpTable', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                , where: {
                    "keyWord": keyWord.val()
                }
            });
        });

        //监听提交
        form.on('submit(formDemo)', function(data){
            var url = basePath+"/edu/exam/paper/list?" +$("#bpForm").serialize();
            table.reload('bpTable', {
                url: url,
                page: {
                    curr: 1 //重新从第 1 页开始
                }
            });
            return false;
        });

        //添加
        $("#insert").click(function () {
            openDialogVerify("新增exam_paper信息", "/views/edu/exam/paper/insert.html", "1000px", "600px");
        });

        //批量删除
        $("#delete").click(function () {
            var checkStatus = table.checkStatus('bpTable');
            var data = checkStatus.data;
            var ids = "";
            for (var i = 0; i < data.length; i++) {
                ids = ids + data[i].uuid + ","
            }
            if (ids == "") {
                top.layer.msg("请选择要删除的数据!");
            } else {
                ids = ids.substr(0, ids.length - 1);
                confirmx("你确定" + data.length + "条数据删除吗？", function () {
                    $.ajax({
                        type: "DELETE",
                        url: basePath+"/edu/exam/paper?uuids="+ids,
                        beforeSend: function () {
                            return loading();
                        },
                        success: function (rb) {
                            closeloading();
                            if (rb.code == 0) {
                                top.layer.msg(rb.msg);
                                 table.reload("bpTable");
                            } else {
                                top.layer.msg(rb.msg);
                            }
                        },
                        error: function () {
                            closeloading();
                        }
                    });
                });
            }
        });

        //修改
        $("#update").click(function () {
            var checkStatus = table.checkStatus('bpTable');
            var data = checkStatus.data; //得到选中的数据
            if (data == '' || data.length > 1) {
                top.layer.msg("请选择其中一条数据进行修改");
                return;
            }
            var uuid = data[0].uuid;
            openDialogVerify("修改exam_paper信息", "/views/edu/exam/paper/update.html?uuid=" + uuid, "1000px", "600px");
        });

    });

</script>
</body>
</html>
