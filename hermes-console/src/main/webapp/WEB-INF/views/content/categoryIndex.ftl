 <#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>

<div class="row panel-body">
	<div class="col-xs-1">
		<button class="btn btn-primary btn-block" type="button" id="addBtn">+ 新增分类</button>
		<input id="page" name="page" type="hidden" value="0"/>		
    </div>
</div>
<div id="data" style="display:block">
    <table class="table table-striped table-hover">        	
        <thead>
            <tr>
                <th class="align-center">分类名称</th>
                <th class="align-center">文章数</th>
                <th class="align-center">类型</th>
                <th class="align-center">创建时间</th>
                <th class="align-center">编辑人</th>
                <th class="align-center">操作</th>
        	</tr>
        </thead>
            <#if categories.numberOfElements == 0>
            <tr>
	            <td colspan="6" class="align-center"><@messages key="common.table.empty" /></td>
            </tr>
            <#else>        
            <#list categories.content as l>
            <tr>
                <td class="align-center">${l.name}</td>
                <td class="align-center">1</td>
                <td class="align-center">${l.level!''}</td>
                <td class="align-center">${l.createTime?string('yyyy-MM-dd hh:mm:ss')}</td>
                <td class="align-center">admin</td>
                <td class="align-center">
                    <#if l.level?? && l.level == "一级">
                        <button type="button" class="btn btn-link deleteBtn" id="deleteBtn"></button>  
                    <#elseif l.name?? && l.name == "公司介绍">
                         <button type="button" class="btn btn-link deleteBtn" id="deleteBtn"></button> 
                    <#elseif l.name?? && l.name == "其他分类">
                         <button type="button" class="btn btn-link deleteBtn" id="deleteBtn"></button>                                                                                                               
                    <#elseif l.name?? && l.name == "公告">
                         <button type="button" class="btn btn-link deleteBtn" id="deleteBtn"></button>                                                                                                               
	                <#else>
                        <button type="button" class="btn btn-link editBtn" id="editBtn" pid="${l.id}">编辑</button>   
                        <button type="button" class="btn btn-link deleteBtn" id="deleteBtn" pid="${l.id}">删除</button>                                             			  			                
	                </#if>                            
                </td>
            </tr>
            </#list>
            </#if>
    </table>
           <ul class="pagination" data-number="${categories.number}" data-total-pages="${categories.totalPages}"></ul>      
</div>
        
        
<script type="text/javascript">
	jQuery(function($) {
		$('.pagination').pagination({
		handler: function(elem) {
			$('#page').val(elem.data().page);
			$('#searchForm').trigger('submit');
		}
	});
	    //点击删除按钮
		$(".deleteBtn").on("click",function(){
			$.post("${app}/content/deleteCategory",
			      {id:$(this).attr("pid")},
			      function(result){
				     if(result.code == '1'){
					    $.scojs_confirm({  
					        content: result.attachment,
					         action: function() {
					                     $.link.html(null, {
				                               url: '${app}/content/categoryIndex',
				                            target: 'main'
			                             });
						              }
					    }).show();
				    }else{
				        $.scojs_confirm({  
					        content: "您确定要删除此分类吗？",
					         action: function() {
					                     $.link.html(null, {
				                               url: '${app}/content/categoryIndex',
				                            target: 'main'
			                             });
						              }
					    }).show();				         
				    }
			    });			
		  });
		//点击新增分类按钮
		$("#addBtn").on("click",function(){
			$.link.html(null, {
				url: '${app}/content/addCategory',
				target: 'main'
			});
		});
		//点击编辑按钮
		$(".editBtn").on("click",function(){
			var pid = $(this).attr("pid");
			$.link.html(null, {
				url: '${app}/content/editCategory?id='+pid,
				target: 'main'
			});
		});				
		
	});
</script>    