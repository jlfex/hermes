 <#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>

  <div class="modal-body">
   		<form class="form-horizontal" role="form" id="editForm">
   		  <input type="hidden" value="${(category.id)!}" name="id" />   		
          <div class="form-group">
            <label for="inputName" class="col-sm-2 control-label">分类名称</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="inputName" name = "inputName" placeholder="分类名称" value="${category.name}">
            </div>
          </div>
          <div class="form-group">
            <label for="inputBefore" class="col-sm-2 control-label">上级分类</label>
            <div class="col-sm-10">
                <div class="row">
                    <div class="col-xs-6 hm-col">
                        <select class="form-control" name = "categoryLevelOne" id="categoryLevelOne"> 
 						         <option value="">请选择</option>
 						         	<#if categoryForLevel1??>  
						               <#list categoryForLevel1 as cf1>
							     <option value="${cf1.id}">${cf1.name}</option>
						               </#list>
									</#if>
                        </select>
                    </div>
                    <div class="col-xs-6 hm-col">
                        <select class="form-control" name = "categoryLevelTwo" id="categoryLevelTwo"> 
                         <option value="">请选择</option>
                        			        	<#if categoryForLevel2??>  
						               <#list categoryForLevel2 as cf2>
							     <option value="${cf2.id}">${cf2.name}</option>
						  </#list>
			        </#if>
                                              
                        </select>
                    </div>
                </div>
            </div>
          </div>
        </form>   
    </div>
<div class="row">
     <div class="col-sm-offset-2 col-sm-10">
          <div class="col-xs-3"><button type="button" class="btn btn-primary btn-block" id="editCategoryButton">保存</button></div>
          <div class="col-xs-3"><button type="button" class="btn btn-default btn-block" id="cacelCategoryButton">取消</button></div>
     </div>
</div>
<script type="text/javascript">
jQuery(function($) {
	var level3 = '${category.level}';
	var parentIdForLevel3 = '${category.parent.id}';
	//点击编辑页面中保存按钮
	$("#editCategoryButton").on("click",function(){
		$.link.html(null, {
			url: '${app}/content/updateCategory',
			data: $("#editForm").serialize(),
			target: 'main'
		});
	});
    //点击编辑页面中取消按钮
	$("#cacelCategoryButton").on("click",function(){
		$.link.html(null, {
			url: '${app}/content/categoryIndex',
			target: 'main'
		});
	});
	//获取二级下拉框
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
				$.each(_data, function(k, v)
				{
					option += "<option value=\"" + v['id'] + "\">" + v['name'] + "</option>";
				});
				$("#categoryLevelTwo").append(option);
			},
			error : function(msg, textStatus, e)
			{
				alert("编辑分类失败，请重新添加！");
				$.link.html(null, {
					url: '${app}/content/categoryIndex',
					target: 'main'
				});
			}
		});
		});
		
		$("#categoryLevelOne").val("${levelOne!}");
		$("#categoryLevelTwo").val("${levelTwo!}");
		
});
</script>  