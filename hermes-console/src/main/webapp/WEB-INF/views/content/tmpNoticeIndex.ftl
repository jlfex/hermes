 <#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>
<div class="row panel-body">
	<div class="col-xs-1">
		<button  id="addBtn" class="btn btn-primary btn-block" type="button" data-toggle="modal" data-target="#myModal">+ 添加公告</button>
		<input id="page" name="page" type="hidden" value="0"/>
    </div>
</div>
<div id="data" style="display:block">
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
                        <button type="button" class="btn btn-link editBtn"  pid="${l.id}">编辑</button>
                        <button type="button" class="btn btn-link hm-col deleteBtn"  cid="${l.id}">删除</button>
			        </td>
			    </tr>
			        </#list>
			 </#if>                    
			</table>
          <ul class="pagination" data-number="${tmpNotices.number}" data-total-pages="${tmpNotices.totalPages}"></ul>  
</div>
			
<script type="text/javascript">
<!--
jQuery(function($) {
	$('.pagination').pagination({
		handler: function(elem) {
			$('#page').val(elem.data().page);
			$('#searchForm').trigger('submit');
		}
    });
	//点击新增按钮
	$("#addBtn").on("click",function(){
		$.link.html(null, {
			url: '${app}/content/addTmpNotice',
			target: 'main'
		});
	});

   //点击编辑按钮
	$(".editBtn").on("click",function(){
		var pid = $(this).attr("pid");
		$.link.html(null, {
			url: '${app}/content/editTmpNotice?id='+pid,
			target: 'main'
		});
	});		
		
    //点击删除按钮
	$(".deleteBtn").on("click",function(){
		var cid = $(this).attr("cid");
		$.link.html(null, {
			url: '${app}/content/deleteTmpNotice?id='+cid,
			target: 'main'
		});
     });				
	
});
//-->
</script>