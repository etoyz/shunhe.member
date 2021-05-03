<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>会员档案</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="plugins/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="plugins/font-awesome/css/font-awesome.min.css" media="all">
    <link rel="stylesheet" href="plugins/adjust/css/adjustLayui.css" media="all">
    <link rel="stylesheet" href="css/table.css" media="all">
    <link rel="stylesheet" href="css/form.css" media="all">
    <link rel="stylesheet" href="css/common.css" media="all">
</head>
<body>
<div class="layui-fluid main">
    <div class="layui-row">
        <div class="layui-col-md12">

            <div class="s-headerSearch">
                <form class="layui-form" action="">

                    <div class="layui-form-item">

                        <div class="layui-inline s-layuiInline">
                            <label class="layui-form-label">车牌号</label>
                            <div class="layui-input-inline">
                                <input type="text" name="carNumber" autocomplete="off" class="layui-input"
                                       placeholder="请输入车牌号">
                            </div>
                        </div>
                        <div class="layui-inline s-layuiInline">
                            <label class="layui-form-label">车架号</label>
                            <div class="layui-input-inline">
                                <input type="text" name="chassisNumber" autocomplete="off" class="layui-input"
                                       placeholder="请输入车架号">
                            </div>
                        </div>
                        <div class="layui-inline s-layuiInline">
                            <label class="layui-form-label">客户名称</label>
                            <div class="layui-input-inline">
                                <input type="text" name="customerName" autocomplete="off" class="layui-input"
                                       placeholder="请输入客户名称">
                            </div>
                        </div>
                        <div class="layui-inline s-layuiInline">
                            <label class="layui-form-label">会员卡号</label>
                            <div class="layui-input-inline">
                                <input type="text" name="membership" autocomplete="off" class="layui-input"
                                       placeholder="请输入会员卡号">
                            </div>
                        </div>

                        <div class="layui-inline s-layuiInline">
                            <button class="layui-btn layui-btn-warm">查询</button>
                            <span class="layui-btn" id="add">新增</span>
                        </div>

                    </div>
                </form>
            </div>

            <!-- 展示表格 -->
            <div class="s-commonTable">
                <table class="layui-hide" id="memberArchives" lay-filter="memberArchivesTabel"></table>
                <script type="text/html" id="operation">
                    <a class="layui-btn layui-btn-xs" lay-event="edit">修改</a>
                    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
                    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="grade">会员级别</a>
                    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">解绑</a>
                </script>
            </div>
        </div>
    </div>
</div>

<!-- 新增弹窗 -->
<div class="s-details" id="addWindow">

    <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
        <ul class="layui-tab-title">
            <li class="layui-this">车主信息</li>
            <li>车辆信息</li>
            <li>会员信息</li>
            <li>消费历史</li>
            <li>开票历史</li>
            <li>积分历史</li>
            <li>操作日志</li>
        </ul>
        <div class="layui-tab-content" style="height: 100px;">
            <div class="layui-tab-item layui-show">
                <form class="layui-form" action="">
                    <div class="layui-form-item s-formItem5">
                        <div class="layui-inline">
                            <label class="layui-form-label"><i>*</i>客户姓名</label>
                            <div class="layui-input-inline">
                                <input type="text" name="customerName" lay-verify="required" autocomplete="off"
                                       class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">性别</label>
                            <div class="layui-input-inline">
                                <select name="gender" lay-filter="gender">
                                    <option value="">请选择性别</option>
                                    <option value="1">男</option>
                                    <option value="2">女</option>
                                    <option value="3">未知</option>
                                </select>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">生日</label>
                            <div class="layui-input-inline">
                                <input type="text" name="date" id="date" lay-verify="date" placeholder="yyyy-MM-dd"
                                       autocomplete="off" class="layui-input">
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item s-formItem5">
                        <label class="layui-form-label">省市区</label>
                        <div class="layui-input-inline">
                            <select name="provid" id="provid" lay-filter="provid">
                                <option value="">请选择省</option>
                            </select>
                        </div>
                        <div class="layui-input-inline">
                            <select name="cityid" id="cityid" lay-filter="cityid">
                                <option value="">请选择市</option>
                            </select>
                        </div>
                        <div class="layui-input-inline">
                            <select name="areaid" id="areaid" lay-filter="areaid">
                                <option value="">请选择县/区</option>
                            </select>
                        </div>
                    </div>

                    <div class="layui-form-item s-formItem5">
                        <label class="layui-form-label">地址</label>
                        <div class="layui-input-block">
                            <input type="text" name="detailedAddress" placeholder="请输入" autocomplete="off"
                                   class="layui-input">
                        </div>
                    </div>

                    <div class="layui-form-item s-formItem5">
                        <div class="layui-inline">
                            <label class="layui-form-label">微信解除关注日期</label>
                            <div class="layui-input-inline">
                                <input type="text" name="date1" id="date1" lay-verify="date" placeholder="yyyy-MM-dd"
                                       autocomplete="off" class="layui-input">
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="layui-tab-item">
                <table class="layui-hide" id="memberInfo" lay-filter="memberInfoTabel"></table>
            </div>
            <div class="layui-tab-item">内容3</div>
            <div class="layui-tab-item">内容4</div>
            <div class="layui-tab-item">内容5</div>
            <div class="layui-tab-item">内容6</div>
            <div class="layui-tab-item">内容7</div>
        </div>
    </div>

    <div class="s-tabBottomButton">
        <button class="layui-btn" id="confirm">确认</button>
        <button class="layui-btn layui-btn-warm">取消</button>
    </div>

</div>

<!-- 编辑弹窗 -->
<div class="s-details" id="editWindow">
    编辑信息
</div>

<!-- 会员级别弹窗 -->
<div class="s-details" id="grade">
    <form class="layui-form" action="">
        <div class="layui-form-item s-formItem3">
            <label class="layui-form-label">原会员级别</label>
            <div class="layui-input-inline">
                <input type="text" name="originalLevel" placeholder="1" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item s-formItem3">
            <label class="layui-form-label">新会员级别</label>
            <div class="layui-input-inline">
                <select name="newLevel" lay-verify="required" id="newLevel" lay-filter="newLevel">
                    <option value="">请选择会员级别</option>
                    <option value="1">1</option>
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-input-block s-submitButton5">
                <button type="button" class="layui-btn" lay-submit lay-filter="save2">确认</button>
                <a class="layui-btn layui-btn-danger" id="cancel">取消</a>
            </div>
        </div>
    </form>
</div>

<script src="plugins/layui/layui.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script>
    $("#confirm").click(function (){
        $.get({
            url: "demo/test",
            success: function (data) {
                console.log(data)
            }
        })
    });

    layui.use(['form', 'laydate', 'table', 'layer', 'element'], function () {
        var $ = layui.jquery,
            form = layui.form,
            laydate = layui.laydate,
            layer = layui.layer,
            table = layui.table,
            element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块

        //日期
        laydate.render({
            elem: '#date'
        });
        laydate.render({
            elem: '#date1'
        });

        // 显示“新增弹窗”
        $('body').on('click', '#add', function () {
            var index = layer.open({
                type: 1,
                title: '新增弹窗',
                content: $('#addWindow'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                area: ['1022px', '600px'],
                offset: '100px'
            });
            $('#exit').on('click', function () {
                // 关闭当前弹出层
                layer.close(index);
            });
        });

        // “会员档案表格”渲染
        var tableIns = table.render({
            elem: '#memberArchives',
            url: './datas/user.json',
            height: 'full-291',
            page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: ['prev', 'page', 'next', 'skip', 'count'], //自定义分页布局
                limit: 15,
                //,curr: 5 //设定初始在第 5 页
                groups: 3, //只显示 1 个连续页码
                first: false, //不显示首页
                last: false //不显示尾页

            },
            toolbar: true, //仅开启工具栏，不显示左侧模板
            defaultToolbar: ['exports'], //头部工具栏右侧图标
            cols: [[
                {type: 'checkbox'},
                {field: 'customer', width: 80, title: '客户名称'},
                {field: 'gender', width: 56, title: '性别'},
                {field: 'birthday', width: 96, title: '生日'},
                {field: 'idNo', width: 156, title: '证件号'}, //单元格内容水平居中
                {field: 'membershipCard', width: 160, title: '会员卡号'},
                {field: 'membershipGrade', width: 80, title: '会员级别'},
                {field: 'province', width: 152, title: '省'},
                {field: 'city', width: 152, title: '市'},
                {field: 'district', width: 152, title: '区'},
                {field: 'detailedAddress', width: 302, title: '详细地址'},
                {field: 'vin', width: 156, title: '车架号'},
                {field: 'licensePlate', width: 88, title: '车牌号'},
                {field: 'brand', width: 82, title: '品牌'},
                {field: 'carSeries', width: 54, title: '车系'},
                {field: 'motorcycleType', width: 210, title: '车型'},
                {field: 'operation', width: 210, title: '操作', toolbar: '#operation', fixed: 'right'}
            ]],
            size: 'sm'
        });
        //监听“会员档案表格”工具条
        table.on('tool(memberArchivesTabel)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            // var page = data.page;
            // var limit = data.limit;
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            var customer = data.customer;
            if (layEvent === 'resetPasswords') { // 重置密码
                console.log(tableIns.config.page.curr);
                console.log(tableIns.config.page.limit);
                layer.confirm('确定要重置用户账号：[' + user + ']密码吗？', {icon: 3, title: '提示'}, function (index) {
                    layer.close(index);
                    //向服务端发送删除指令

                    // 提示窗
                    layer.alert('重置密码成功！初始密码为：123456', {icon: 1});
                });
            } else if (layEvent === 'edit') { // 编辑
                var index1 = layer.open({
                    type: 1,
                    title: '修改弹窗',
                    content: $('#editWindow'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    area: '1022px'
                });
                $('#exit1').on('click', function () {
                    // 关闭当前弹出层
                    layer.close(index1);
                });
            } else if (layEvent === 'grade') { // 会员级别
                var index2 = layer.open({
                    type: 1,
                    title: '会员级别',
                    content: $('#grade'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    area: '327px'
                });
                $('#cancel').on('click', function () {
                    // 关闭当前弹出层
                    layer.close(index2);
                });
                // 确认
                form.on('submit(save2)', function (data) {
                    // 提示窗
                    layer.alert('修改成功！', {icon: 1});
                    // 关闭当前弹出层
                    layer.close(index2);
                });
            } else if (layEvent === 'delete') { // 删除
                layer.confirm('确定要删除[' + customer + ']吗？', {icon: 3, title: '提示'}, function (index) {
                    // 删除对应行（tr）的DOM结构，并更新缓存
                    obj.del();
                    layer.close(index);
                    // 向服务端发送删除指令

                    // 提示窗
                    layer.alert('删除成功！', {icon: 1});
                });
            }
        });

        // "会员信息表格"渲染
        var tableIns1 = table.render({
            elem: '#memberInfo',
            url: './datas/memberInfo.json',
            height: 400,
            page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: ['prev', 'page', 'next', 'skip', 'count'], //自定义分页布局
                limit: 15,
                //,curr: 5 //设定初始在第 5 页
                groups: 3, //只显示 1 个连续页码
                first: false, //不显示首页
                last: false //不显示尾页

            },
            cols: [[
                {type: 'numbers'},
                {field: 'cardName', width: 124, title: '卡券名称'},
                {field: 'cardType', width: 82, title: '卡券类型'},
                {field: 'quantity', width: 80, title: '购买数量'},
                {field: 'usageQuantity', width: 80, title: '使用数量'}, //单元格内容水平居中
                {field: 'remainingQuantity', width: 80, title: '剩余数量'},
                {field: 'effectiveDate', width: 146, title: '生效日期'},
                {field: 'expiryDate', width: 146, title: '失效日期'},
                {field: 'projectName', width: 88, title: '项目名称'}
            ]],
            size: 'sm'
        });
    }).config({  // 省市区联动选择
        base: './build/js/'
    }).use(['jquery', 'areaThreeLevel'], function () {
        var $ = layui.jquery,
            areaThreeLevel = layui.areaThreeLevel;

        // 2.省市区三级联动选择
        areaThreeLevel.set({
            s1: 'provid',
            s2: 'cityid',
            s3: 'areaid',
            v1: '',
            v2: '',
            v3: ''
        }).treeSelect();
    });
</script>
</body>
</html>