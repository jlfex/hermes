 <#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>

          <div class="modal-body">
           		<form class="form-horizontal" role="form" id="addForm">
                  <div class="form-group">
                    <label for="inputName" class="col-sm-2 control-label">分类名称</label>
                    <div class="col-xs-5 hm-col">
                      <input type="text" class="form-control" id="inputName" name = "inputName" placeholder="分类名称">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputBefore" class="col-sm-2 control-label">上级分类</label>
                    <div class="col-sm-10">
                        <div class="row">
                            <div class="col-xs-6 hm-col">
                                <select class="form-control" name = "categoryLevelOne" id="categoryLevelOne"> 
                                </select>
                            </div>
                            <div class="col-xs-6 hm-col">
                                <select class="form-control" name = "categoryLevelTwo" id="categoryLevelTwo">                               
                                </select>
                            </div>
                        </div>
                    </div>
                  </div>
                </form>   
               </div>
  	             <div class="row">
	                <div class="col-sm-offset-2 col-sm-10">
	                    <div class="col-xs-3"><button type="button" class="btn btn-primary btn-block" id="addCategoryButton">新增</button></div>
	                    <div class="col-xs-3"><button type="button" class="btn btn-default btn-block" id="cacelCategoryButton">取消</button></div>
	                </div>
	            </div>            


<script type="text/javascript">

jQuery(function($) {
     //点击新增分类页面中新增按钮
	$("#addCategoryButton").on("click",function(){
		$.link.html(null, {
			url: '${app}/content/insertCategory',
			data: $("#addForm").serialize(),
			target: 'main'
		});
	});
	//点击新增分类页面中取消按钮
	$("#cacelCategoryButton").on("click",function(){
		$.link.html(null, {
			url: '${app}/content/categoryIndex',
			target: 'main'
		});
	});
	//获取一级分类下拉框
	$.ajax({
		type : 'POST',
		url : '${app}/content/findCategoryByLevel',
		data : 'level=一级',
		success : function(msg)
		{
			// 清空表格
			$("#categoryLevelOne").empty();						
			var option = "<option value=\"\">请选择</option>";
			// 循环组装下拉框选项
			$.each(msg, function(k, v)
			{
				option += "<option value=\"" + v['id'] + "\">" + v['name'] + "</option>";
			});
			$("#categoryLevelOne").append(option);
		},
		error : function(msg, textStatus, e)
		{
			alert("获取一级分类失败");
			$.link.html(null, {
				url: '${app}/content/categoryIndex',
				target: 'main'
			});
		}
	});
    //当一级分类下拉框改变时候，获取二级分类下拉框
	$("#categoryLevelOne").bind("change",function() {
		var parentCode = $("#categoryLevelOne").val();		
		$.ajax({
		type : 'POST',
		url : '${app}/content/findCategoryByParent',
		data : 'level=二级&parentCode='+parentCode+'&parentId='+parentCode,
		success : function(msg)
		{
			// 清空表格
			$("#categoryLevelTwo").empty();
			var option = "<option value=\"\">请选择</option>";
			var _data = msg[0].parent.children;
			_data[0] = msg[0];
			$.each(_data,function(index,v)
			{
				option += "<option value=\"" + v['id'] + "\">" + v['name'] + "</option>";
			});
			$("#categoryLevelTwo").append(option);
		},
		error : function(msg, textStatus, e)
		{
			$.link.html(null, {
				url: '${app}/content/categoryIndex',
				target: 'main'
			});
		}
	});
	});
});
</script>  