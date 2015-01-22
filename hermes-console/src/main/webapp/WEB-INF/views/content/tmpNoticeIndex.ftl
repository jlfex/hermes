 <#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>

 <div class="panel panel-primary">
        <div class="panel-heading">临时公告</div>
        <div class="panel-body">
            <div id="data" style="display:block">
               <form class="form-horizontal" role="form" id="addForm" method="post">
						<table class="table table-striped table-hover">        	
						    <thead>
						        <tr>
						        <th class="align-center">公告标题</th>            
						        <th class="align-center">公告内容</th>
						        <th class="align-center">显示开始时间</th>
						        <th class="align-center">显示结束时间</th>
						        <th class="align-center">操作</th>
						    	</tr>
						    </thead>
						<#if tmpNotices.numberOfElements == 0>
						        <tr>
						            <td colspan="4" class="align-center"><@messages key="common.table.empty" /></td>
						        </tr>
						        <#else>
						        <#list tmpNotices.content as l>                            
						    <tr>
						        <td class="align-center">${l.title!}</td>        
						        <td class="align-center">${l.content!}</td>
						        <td class="align-center">${l.startDate?string("yyyy-MM-dd")}</td>
						        <td class="align-center">${l.endDate?string("yyyy-MM-dd")}</td>
						        <td class="align-center">
						            <button type="button" class="btn btn-link" data-toggle="modal" data-target="#myModal02" id="editBtn" pid="${l.id}">编辑</button>
						        </td>
						    </tr>
						        </#list>
						 </#if>                    
						</table>
                  </form>
              </div> 
          </div>
    </div>
<script type="text/javascript">
<!--
jQuery(function($) {
   //点击编辑按钮
	$("#editBtn").on("click",function(){
		var pid = $(this).attr("pid");
		$.link.html(null, {
			url: '${app}/content/editTmpNotice?id='+pid,
			target: 'main'
		});
	});			
	
});
//-->
</script>