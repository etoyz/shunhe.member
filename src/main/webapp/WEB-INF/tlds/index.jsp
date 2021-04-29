<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/plugins/font-awesome/css/font-awesome.min.css?v=${fns:getConfig('version')}" media="all">
    <link rel="stylesheet" href="/css/common.css?v=${fns:getConfig('version')}" media="all">
    <!-- 浏览器Tab标签页图标  -->
    <link rel="icon" type="image/ico" href="/images/browserTabIcon2.png"/>
</head>
<body>
<!-- header -->
<div class="s-header">
    <!-- 用户名+注销 -->
    <div class="s-headerContent">
        <h1>综合平台</h1>
        <ul class="s-account">
            <li><a href="javascript:void(0);"><i class="fa fa-user"></i>${fns:getUser().loginname}</a></li>
            <li><a href="/logout"><i class="fa fa-power-off"></i>注销</a></li>
        </ul>
        <div class="blank0px"></div>
    </div>
</div>

<!-- content -->
<div class="s-content">
    <ul class="s-module">
        <li>
            <a href="/mongoUserWeb/index" target="_blank">
                <img src="/images/moduleIcon1.png">
                <div class="s-systemName">
                    <h2>用户系统</h2>
                    <span>用户管理模块</span>
                </div>
            </a>
        </li>
        <li>
            <a href="http://10.30.11.13:8701/" target="_blank">
            <%--<a href="http://devicedebug.bek88.com/" target="_blank">--%>
                <img src="/images/moduleIcon2.png">
                <div class="s-systemName">
                    <h2>设备系统</h2>
                    <span>设备管理模块</span>
                </div>
            </a>
        </li>
        <li>
            <a href="http://10.30.11.11:8709/" target="_blank">
            <%--<a href="http://chebao.bek88.com/" target="_blank">--%>
                <img src="/images/moduleIcon3.png">
                <div class="s-systemName">
                    <h2>车辆系统</h2>
                    <span>车辆管理模块</span>
                </div>
            </a>
        </li>
        <li>
            <a href="javascript:void(0);" target="_blank">
                <img src="/images/system.png">
                <div class="s-systemName">
                    <h2>待开发</h2>
                    <span>待开发</span>
                </div>
            </a>
        </li>

        <li>
            <a href="javascript:void(0);" target="_blank">
                <img src="/images/system.png">
                <div class="s-systemName">
                    <h2>待开发</h2>
                    <span>待开发</span>
                </div>
            </a>
        </li>
        <li>
            <a href="javascript:void(0);" target="_blank">
                <img src="/images/system.png">
                <div class="s-systemName">
                    <h2>待开发</h2>
                    <span>待开发</span>
                </div>
            </a>
        </li>
        <li>
            <a href="javascript:void(0);" target="_blank">
                <img src="/images/system.png">
                <div class="s-systemName">
                    <h2>待开发</h2>
                    <span>待开发</span>
                </div>
            </a>
        </li>
        <li>
            <a href="javascript:void(0);" target="_blank">
                <img src="/images/system.png">
                <div class="s-systemName">
                    <h2>待开发</h2>
                    <span>待开发</span>
                </div>
            </a>
        </li>
    </ul>
    <div class="blank0px"></div>
</div>

<!-- footer -->
<div class="s-footer">
    <span>©2018&nbsp;顺合有限公司&nbsp;版权所有</span>
</div>
</body>
</html>