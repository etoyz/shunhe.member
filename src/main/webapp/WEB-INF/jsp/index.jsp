<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp" %>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>车辆管理平台</title>
    <link rel="stylesheet" href="${fns:getConfig('forwarding.url')}plugins/layui/css/layui.css?v=${fns:getConfig('version')}" media="all">
    <link rel="stylesheet" href="${fns:getConfig('forwarding.url')}plugins/font-awesome/css/font-awesome.min.css?v=${fns:getConfig('version')}" media="all">
    <link rel="stylesheet" href="${fns:getConfig('forwarding.url')}menu/common/css/app.css?v=${fns:getConfig('version')}" media="all"/>
    <link rel="stylesheet" href="${fns:getConfig('forwarding.url')}menu/common/css/default.css?v=${fns:getConfig('version')}" media="all" id="skin" kit-skin/>

    <link rel="stylesheet" href="${fns:getConfig('forwarding.url')}plugins/adjust/css/adjustLayui.css?v=${fns:getConfig('version')}" media="all">
    <link rel="stylesheet" href="${fns:getConfig('forwarding.url')}build/css/table.css?v=${fns:getConfig('version')}" media="all">
    <link rel="stylesheet" href="${fns:getConfig('forwarding.url')}css/common.css?v=${fns:getConfig('version')}" media="all">
    <!-- 浏览器Tab标签页图标  -->
    <link rel="icon" type="image/ico" href="${fns:getConfig('forwarding.url')}images/logo.png"/>
</head>

<body>
<div class="layui-layout layui-layout-admin kit-layout-admin">
    <div class="layui-header">
        <div class="layui-logo s-layuiLogo">
<%--            <img src="${fns:getConfig('forwarding.url')}images/kb.png">--%>
            <div class="layui-logo">会员系统</div>
        </div>
        <div class="s-systematicName">车辆管理平台</div>
        <ul class="layui-nav layui-layout-right kit-nav">
<%--            <li class="layui-nav-item"><a href="javascript:;" class="s-headPortrait"><img--%>
<%--                    src="${fns:getConfig('forwarding.url')}images/icon.png" class="layui-nav-img"> </a></li>--%>
            <li class="layui-nav-item"><a href="${fns:getConfig('forwarding.url')}logout" class="s-logout"><i class="fa fa-sign-out"
                                                                            aria-hidden="true"></i> 注销</a></li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black kit-side">
        <div class="layui-side-scroll">
            <div class="kit-side-fold"><i class="fa fa-navicon" aria-hidden="true"></i></div>
            <!-- 左侧导航区域（可配合layui已有的垂直导航）-->
            <ul class="layui-nav layui-nav-tree" lay-filter="kitNavbar" kit-navbar>
                <li class="layui-nav-item">
                    <a href="javascript:;" kit-target
                       data-options="{url:'distribute',icon:'fa-plug',title:'菜单',id:'1'}"><i class="fa fa-plug"
                                                                                             aria-hidden="true"></i><span> 菜单</span></a>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;"><i class="fa fa-car" aria-hidden="true"></i><span> 车辆</span></a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a href="javascript:;" data-url="vehicleManagement" data-icon="fa-car" data-title="车辆管理"
                               kit-target data-id='3'><i class="fa fa-car" aria-hidden="true"></i><span> 车辆管理</span></a>
                        </dd>
                        <dd>
                            <a href="javascript:;" data-url="deviceManagement" data-icon="fa-cogs" data-title="设备管理"
                               kit-target data-id='4'><i class="fa fa-cogs"
                                                         aria-hidden="true"></i><span> 设备管理</span></a>
                        </dd>
                        <dd>
                            <a href="javascript:;" data-url="siminfoManagement" data-icon="fa-credit-card"
                               data-title="sim管理" kit-target data-id='5'><i class="fa fa-credit-card"
                                                                            aria-hidden="true"></i><span> sim管理</span></a>
                        </dd>
                        <dd>
                            <a href="javascript:;" data-url="warnManagement" data-icon="fa-warning" data-title="报警管理"
                               kit-target data-id='6'><i class="fa fa-warning" aria-hidden="true"></i><span> 报警管理</span></a>
                        </dd>
                        <dd>
                            <a href="javascript:;" data-url="locationManagement" data-icon="fa-map-marker"
                               data-title="车辆定位" kit-target data-id='7'><i class="fa fa-map-marker"
                                                                           aria-hidden="true"></i><span> 车辆定位</span></a>
                        </dd>
                        <dd>
                            <a href="javascript:;" data-url="trailManagement" data-icon="fa-paper-plane-o"
                               data-title="车辆轨迹" kit-target data-id='8'><i class="fa fa-paper-plane-o"
                                                                           aria-hidden="true"></i><span> 车辆轨迹</span></a>
                        </dd>
                        <dd>
                            <a href="javascript:;" data-url="distribute" data-icon="fa-map-o" data-title="车辆分布"
                               kit-target data-id='9'><i class="fa fa-map-o"
                                                         aria-hidden="true"></i><span> 车辆分布</span></a>
                        </dd>
                        <dd>
                            <a href="javascript:;" data-url="customer" data-icon="fa-users" data-title="车主管理"
                               kit-target data-id='10'><i class="fa fa-users"
                                                         aria-hidden="true"></i><span> 车主管理</span></a>
                        </dd>
                        <dd>
                            <a href="javascript:;" data-url="deviceStatistics" data-icon="fa-bar-chart" data-title="设备统计"
                               kit-target data-id='12'><i class="fa fa-bar-chart"
                                                         aria-hidden="true"></i><span> 设备统计</span></a>
                        </dd>
                        <shiro:hasPermission name="devicemanagement:dict:view">
                        <dd>
                            <a href="javascript:;" data-url="dict" data-icon="fa-list" data-title="通用管理"
                               kit-target data-id='11'><i class="fa fa-list"
                                                         aria-hidden="true"></i><span> 通用管理</span></a>
                        </dd>
                        </shiro:hasPermission>
                    </dl>
                </li>
            </ul>
        </div>
    </div>
    <!-- 报警提醒 -->
    <div class="s-details" id="alarmWindow">
        <p class="s-alarmInfoNumber">未读信息条数：<span id="warnCount">0</span></p>
        <!-- 报警图片 -->
        <div class="s-alarmImg">
            <img id="img" src="images/alarm.jpg">
        </div>
        <!-- 报警信息 -->
        <div class="s-alarmInfo">
            <ul>
                <li>客户姓名：<span id="oName"></span></li>
                <li>客户电话：<span id="oTel"></span></li>
                <li>设备号：<span id="termanalNo"></span></li>
                <li>车系：<span id="carStyle"></span></li>
                <li>车牌号：<span id="palteNumber"></span></li>
                <li>
                    <%--<a class="layui-btn" id="wDetail" >查看详情</a>--%>
                    <a href="javascript:;" class="layui-btn" data-url="warnManagement?secrchKey=碰撞报警（拍照）"
                       data-icon="fa-user" data-title="报警管理" data-id='6' id="wDetail">查看详情</a>
                </li>
            </ul>
        </div>
        <div class="blank0px"></div>
    </div>
    <div class="layui-body" id="container">
        <!-- 内容主体区域 -->
        <div style="padding: 15px;">主体内容加载中,请稍等...</div>
    </div>

    <!-- 底部固定区域 -->
    <div class="layui-footer">© 2019  版权所有</div>
</div>

<script src="${fns:getConfig('forwarding.url')}plugins/layui/layui.js?v=${fns:getConfig('version')}"></script>
<script>
    layui.config({
        base: '${fns:getConfig('forwarding.url')}menu/common/js/'
    }).use(['table', 'layer', 'laydate', 'app', 'tab'], function () {
        var $ = layui.jquery,
            layer = layui.layer,
            laydate = layui.laydate,
            tab = layui.tab;

        // 表格头部搜索-起止时间等
        laydate.render({
            elem: '#startTime',
            type: 'datetime'
        });
        laydate.render({
            elem: '#endTime',
            type: 'datetime'
        });
        interval = "";

        setOpenWarn = function () {
            $.ajax({
                type: 'post',
                url: "${fns:getConfig('forwarding.url')}warn/setOpenWarn",
                data: {},
                async: false,
                dataType: 'json',
                error: function (request) {
                },
                success: function (json) {
                }
            });
        };
        setReadWarn = function (wid) {
            $.ajax({
                type: 'post',
                url: "${fns:getConfig('forwarding.url')}warn/setReadWarn",
                data: {"wid":wid},
                async: false,
                dataType: 'json',
                error: function (request) {
                },
                success: function (json) {
                }
            });
        };

        status = 0;
        wid = 0;
        findWarn = function () {
            $.ajax({
                type: 'get',
                url: "${fns:getConfig('forwarding.url')}warn/getNoReadWarn",
                data: {},
                async: false,
                dataType: 'json',
                error: function (request) {
                },
                success: function (json) {
                    console.info("kaishi:  "+Date.now());
                    if (json.result == "success") {
                        console.info("success:  "+Date.now());
                        var data = json.data;
                        console.info(data);
                        $("#warnCount").html(data.count);
                        if (status == 0) {
                            console.info(data);
                            wid = data.wid;
                            console.info(wid+"---wid");
                            query();
                            $("#img").attr("src","${fns:getConfig('warn.url')}/warn/pic?deviceNum="+data.terminal_no+"&fileId="+data.fileId)
                            $("#oName").html(data.customername);
                            $("#oTel").html(data.customerphone);
                            $("#carStyle").html(data.vehicle_brand);
                            $("#termanalNo").html(data.terminal_no);
                            $("#palteNumber").html(data.plate_number);
                        }
                    }
                }
            });
        };
        window.onload=function(){
        }
        setInterval(findWarn, 60000);
        function query() {
            // “报警信息”弹窗
//            $("#alarmWindow").show();
            index11 = layer.open({
                type: 1,
                title: '报警信息',
                content: $('#alarmWindow'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                area: ['638px','354px'],
                offset: '50px',
                success: function (layero, index) {
                    status = 1;
//                    clearInterval(interval);
                },
                end: function () {
                    status = 0;
//                    interval = setInterval(query, 6000);
                }
            });
        }

        $("#wDetail").off('click').on('click', function () {
            var options = $(this).data('options');
            var data;
            if (options !== undefined) {
                try {
                    data = new Function('return ' + options)();
                } catch (e) {
                    layui.hint().error('Navbar 组件a[data-options]配置项存在语法错误：' + options)
                }
            } else {
                data = {
                    icon: $(this).data('icon'),
                    id: $(this).data('id'),
                    title: $(this).data('title'),
                    url: $(this).data('url')
                };
            }
            tab.tabAdd(data);
            status = 0;
            layer.close(index11);
            // 已读
            setReadWarn(wid);
        });

        var app = layui.app;
        //主入口
        app.set({
            type: 'iframe'
        }).init();
    });
</script>
</body>

</html>