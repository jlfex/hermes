 <#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>
 <div class="panel panel-primary">
<div class="panel-heading">内容编辑</div>
<div class="panel-body">
    <div id="data" style="display:block">
  <form class="form-horizontal" role="form" id="editForm" method="post">
      <input type="hidden" value="${(article.id)!}" name="id" />
      <div class="form-group">
        <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>文章标题</label>
        <div class="col-sm-5">
          <input type="text" class="form-control" id="articleTitle"  name="articleTitle" placeholder="文章标题" value="${article.articleTitle!}">
        </div>
		<div class="col-xs-2">
			<span class="alert-danger" style="display:none;background:none">必填项，限定为60个字符（30个汉字）</span>
		</div>                
      </div>
      <div class="form-group">
        <label for="" class="col-sm-2 control-label">发布人</label>
        <div class="col-sm-5">
          <span class="vlight">admin</span>
        </div>
      </div>
      <div class="form-group">
        <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>所属分类</label>
        <div class="col-sm-8">
          <div class="row">
                <div class="col-xs-2 hm-col">
                    <select id="categoryLevelOne" name="levelOne" class="form-control">
 						<option value="">请选择</option>
 						<#if categoryForLevel1??>  
							     <option value="d82d503c-9efe-11e4-aae2-1f2b79deec07">帮助中心</option>
						</#if>
                    </select>
                </div>
                <div class="col-xs-2 hm-col">
			        <select id="categoryLevelTwo" name="levelTwo" class="form-control">
			        	<option value="">请选择</option>
			        	<#if categoryForLevel2??>  
						     <#list categoryForLevel2 as cf2>
							     <option value="${cf2.id}">${cf2.name}</option>
						     </#list>
			            </#if>
			        </select>
                </div>
                <div class="col-xs-2 hm-col">
			        <select id="categoryLevelThree" name="levelThree" class="form-control">
						<option value="">请选择</option>
						<#if categoryForLevel3??>  
						     <#list categoryForLevel3 as cf3>
							     <option value="${cf3.id}">${cf3.name}</option>
						     </#list>
					    </#if>
			        </select>
                </div>
             <div class="col-xs-2">
				<span class="alert-danger" style="display:none;background:none">必填项</span>
			 </div>                                        
            </div>                                  
        </div>
      </div>
      <div class="form-group">
        <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>排序</label>
        <div class="col-sm-5">
          <input type="text" class="form-control" id="order" name="order" placeholder="1" value="${article.order!}">
        </div>
        <div class="col-xs-2">
			<span class="alert-danger" style="display:none;background:none">必填项，只能为数字</span>
		</div>                                        
      </div>
      <div class="form-group">
        <label for="" class="col-sm-2 control-label">文章关键字</label>
        <div class="col-sm-5">
          <input type="text" class="form-control" id="keywords" name="keywords" placeholder="文章关键字" value="${article.keywords!}">
        </div>
        <span class="vlight">请以，号隔开</span>
        <div class="col-xs-2">
			<span class="alert-danger" style="display:none;background:none">非必填项，限定为60个字符（30个汉字）</span>
		</div>                                
      </div>
      <div class="form-group">
        <label for="" class="col-sm-2 control-label">文章描述</label>
        <div class="col-sm-8">
          <textarea class="form-control" rows="3" id="description" name="description">${article.description!}</textarea>
        </div>
        <div class="col-xs-2">
			<span class="alert-danger" style="display:none;background:none">非必填项，限定字符400个（汉字为200个）</span>
		</div>                                                
      </div>
      <div class="form-group">
        <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>内容编辑</label>
            <div class="col-sm-8">
                  <textarea rows="8" id="content" name="content">${article.content!}</textarea>
            </div>        
        <div class="col-xs-2">
			<span class="alert-danger" style="display:none;background:none">必填项，限定字符1万个（汉字为5000个）</span>
		</div>                                                                
      </div>
      
      <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
          <div class="col-xs-3"><button type="button" class="btn btn-primary btn-block" id="editContentButton">保存</button></div>
          <div class="col-xs-3"><button type="button" class="btn btn-default btn-block" id="cacelContentButton">取消</button></div>
        </div>
      </div>
    </form>     
</div> 
</div>
</div>
<script type="text/javascript">
jQuery(function($) {
    UE.getEditor('content');
	$("#articleTitle,#keywords,#description,#content").on("blur",function(){
		checkInput(this);
	});

    //对输入元素进行校验
	function checkInput(e){
		var $this = $(e);
		var val = $this.val();
		if( ($this.val()==''
		    ||(e.id == 'articleTitle' && val.length > 60)
		    ||(e.id == 'order' && !/^[0-9]*$/.test(val) )
		    ||(e.id == 'keywords' && val.length > 60)
		    ||(e.id == 'description' && val.length > 400)
		    ||(e.id == 'content' && val.length > 10000) ) 		    
		    ){
			$this.parent().parent().find(".alert-danger:eq(0)").attr("e_id",e.id);
			$this.parent().parent().find(".alert-danger:eq(0)").show();
			return false;
		}else{
			var e_id = $this.parent().parent().find(".alert-danger:eq(0)").attr("e_id");
			if(e_id=='' || e_id==e.id){
				$this.parent().parent().find(".alert-danger:eq(0)").hide();
			}
			return true;
		}
	}

    //元素失去焦点时，触发数据校验事件
	function checkAll(){
		$("#articleTitle,#keywords,#description,#content").blur();
		return $("span.alert-danger:visible").length==0;
	}

	//点击编辑页面中保存按钮
	$("#editContentButton").on("click",function(){
		$.link.html(null, {
			url: '${app}/content/handerEditContent',
			data: $("#editForm").serialize(),
			target: 'main'
		});
	});
    //点击编辑页面中取消按钮
	$("#cacelContentButton").on("click",function(){
		$.link.html(null, {
			url: '${app}/content/contentIndex',
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
					option += "<option  value=\"" + v['id'] + "\">" + v['name'] + "</option>";
				});
				$("#categoryLevelTwo").append(option);
			},
			error : function(msg, textStatus, e)
			{
				alert("编辑内容失败，请重新添加！");
				$.link.html(null, {
					url: '${app}/content/categoryIndex',
					target: 'main'
				});
			}
		});
		});
		
		$("#categoryLevelOne").val("${levelOne!}");
		$("#categoryLevelTwo").val("${levelTwo!}");
		$("#categoryLevelThree").val("${levelThree!}");
});
</script> 