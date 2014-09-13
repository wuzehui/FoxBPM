<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程选择</title>
<base target="_self" />
<jsp:include page="/common/head.jsp" flush="true" />
</head>
<body>
	<form id="subForm" method="post" action="selectProcessDefList.action">
		<div style="padding: 8px;">
			<div id="search" class="search">
				<table width="100%">
					<tr>
						<td class="title-r" style="width: 120px; align: left;">流程Key：</td>
						<td style="width: 180px"><input type="text" id="text_0"
							name="queryProcessKey" class="fix-input" style="width: 160px;"
							value="${result.queryProcessKey}" /></td>
						<td class="title-r" style="width: 120px; align: left;">流程名称：</td>
						<td style="width: 180px"><input type="text" id="text_0"
							name="queryProcessName" class="fix-input" style="width: 160px;"
							value="${result.queryProcessName}" /></td>
						<td style="width: 70px"><div class="btn-normal">
								<a href="javascript:void(0)" onclick="$('#subForm').submit();">查
									找</a>
							</div></td>
						<td><div class="btn-normal">
								<a href="javascript:void(0)" id="ok">确定</a>
							</div></td>
					</tr>
				</table>
			</div>
			<div class="content">
				<table id="dataList" width="100%" class="fix-table">
					<thead>
						<th style="width: 50%;">流程Key</th>
						<th>流程名称</th>
					</thead>
					<tbody>
						<c:forEach items="${result.dataList}" var="list" varStatus="index">
							<tr data-rowData="${list}">
								<td>${list.key}</td>
								<td>${list.name}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<!-- 分页 -->
				<div id="page">
					<jsp:include page="/common/page.jsp" flush="true" />
				</div>
			</div>
		</div>
	</form>
</body>
<script>
	$(function() {
		var obj = window.dialogArguments;
		var isMulti = Fix.Util.GetQueryString("isMulti");
		$("table#dataList tr").click(function() {
			if (isMulti == "false") {
				$("table#dataList tr.selected").removeClass("selected");
				$(this).addClass("selected");
			} else {
				if ($(this).hasClass("selected")) {
					$(this).removeClass("selected");
				} else {
					$(this).addClass("selected");
				}
			}
		});
		$("#ok").click(function() {
			var rv = [];
			$("table#dataList tr.selected").each(function(index) {
				var r = "{";
				var rowData = $(this).attr("data-rowData");
				rowData = rowData.substring(1, rowData.length - 1);
				var rowDatas = rowData.split(",");
				for (var i = 0; i < rowDatas.length; i++) {
					var d = rowDatas[i].split("=");
					r += d[0];
					r += ":\"" + d[1] + "\",";
				}
				r = r.substring(0, r.length - 1);
				r += "}";
				eval("var j = " + r);
				rv[index] = j;
			});
			if (window.opener) {
				window.opener.rv = rv;
			} else if (obj.opener) {
				obj.opener.rv = rv;
			} else {
				obj.fn(obj.params, rv);
			}
			window.close();
		});

		var color = window.localStorage.getItem("color");
		if (color) {
			var url = $("#color").attr("href");
			url = url.substring(0, url.lastIndexOf("_") + 1);
			url += color + ".css";
			$("#color").attr("href", url);
		} else {
			var url = $("#color").attr("href");
			url = url.substring(0, url.lastIndexOf("_") + 1);
			url += "red.css";
			$("#color").attr("href", url);
		}
	});
</script>
</html>