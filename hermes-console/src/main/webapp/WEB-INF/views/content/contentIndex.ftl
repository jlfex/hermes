<#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>
<div class="row panel-body">
	<div class="col-xs-1">
		<button class="btn btn-primary btn-block" type="button" id="publishContent">+ 发布内容</button>
    </div>
</div>
<div class="row">
	<form id="searchForm" method="post" action="#">
	<div class="row">
    	<div class="col-xs-2 hm-col form-group">
			<label for="status" class="sr-only">分类</label>
			<select id="status" name="levelOne" class="form-control categoryLevelOne">
				<option value="">请选择</option>
				<#list categoryForLevel1 as cf1>
					<option value="${cf1.id}">${cf1.name}</option>
				</#list>
			</select>
		</div>
        
        <div class="col-xs-2 hm-col form-group">
			<label for="status" class="sr-only">分类</label>
			<select id="status" name="levelTwo" class="form-control categoryLevelTwo">
				<option value="">请选择</option>
				<#list categoryForLevel2 as cf2>
					<option value="${cf2.id}">${cf2.name}</option>
				</#list>
			</select>
		</div>
        
        <div class="col-xs-2 hm-col form-group">
			<label for="status" class="sr-only">分类</label>
			<select id="status" name="levelThree" class="form-control categoryLevelThree">
				<option value="">请选择</option>
				<#list categoryForLevel3 as cf3>
					<option value="${cf3.id}">${cf3.name}</option>
				</#list>
			</select>
		</div>
        
		<div class="col-xs-2 hm-col form-group">
			<label for="name" class="sr-only">内容</label>
			<input id="content" name="inputName" class="form-control" type="text">
		</div>
        
		<div class="col-xs-1 hm-col form-group">
			<label class="sr-only">&nbsp;</label>
			<button id="searchBtn" type="button" class="btn btn-primary btn-block">查询</button>
			<input id="page" name="page" value="0" type="hidden">
		</div>
	</div>
</form>
</div>
        
<div id="data"></div>
<script type="text/javascript">
<!--
jQuery(function($) {
	//点击查询按钮
	$.page.withdraw({
		search: '${app}/content/contentData'
	});	
	
    //点击发布内容按钮
	$("#publishContent").on("click",function(){
		$.link.html(null, {
			url: '${app}/content/publish',
			target: 'main'
		});
	});
	
	//当一级分类下拉框改变时候，获取二级分类下拉框
	$(".categoryLevelOne").bind("change",function() {
		var parentCode = $(".categoryLevelOne").val();
				
			$.ajax({
			type : 'POST',
			url : '${app}/content/findCategoryByParent',
			data : 'level=二级&parentCode='+parentCode+'&parentId='+parentCode,
			success : function(msg)
			{
				// 清空表格
				$(".categoryLevelTwo").empty();
				var option = "<option value=\"\">请选择</option>";
				var _data = msg[0].parent.children;
			    _data[0] = msg[0];				
				$.each(_data, function(k, v)
				{
					option += "<option value=\"" + v['id'] + "\">" + v['name'] + "</option>";
				});
				$(".categoryLevelTwo").append(option);
			},
			error : function(msg, textStatus, e)
			{
				alert("获取二级分类失败");
				$.link.html(null, {
					url: '${app}/content/categoryIndex',
					target: 'main'
				});
			}
		});
	});

	//当二级分类下拉框改变时候，获取三级分类下拉框
	$(".categoryLevelTwo").bind("change",function() {
		var parentCode = $(".categoryLevelTwo").val();
				
			$.ajax({
			type : 'POST',
			url : '${app}/content/findCategoryByParent',
			data : 'level=二级&parentCode='+parentCode+'&parentId='+parentCode,
			success : function(msg)
			{
				// 清空表格
				$(".categoryLevelThree").empty();
				var option = "<option value=\"\">请选择</option>";
				var _data = msg[0].parent.children;
			    _data[0] = msg[0];								
				$.each(_data, function(k, v)
				{
					option += "<option value=\"" + v['id'] + "\">" + v['name'] + "</option>";
				});
				$(".categoryLevelThree").append(option);
			},
			error : function(msg, textStatus, e)
			{
				alert("获取二级分类失败");
				$.link.html(null, {
					url: '${app}/content/categoryIndex',
					target: 'main'
				});
			}
		});
	});	
});
//-->
</script>