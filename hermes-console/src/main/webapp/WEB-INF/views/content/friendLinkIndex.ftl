 <#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>

<div class="row panel-body">
	<div class="col-xs-2">
		<button  id="addBtn" class="btn btn-primary btn-block" type="button" data-toggle="modal" data-target="#myModal"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 添加链接</button>
		<input id="page" name="page" type="hidden" value="0"/>
    </div>
</div>

<div id="data" style="display:block">
    <table class="table table-striped table-hover">        	
        <thead>
            <tr>
            <th class="align-center">排序</th>
            <th class="align-center">网站名称</th>
            <th class="align-center">网站地址</th>
            <th class="align-center">所属分类</th>
            <th class="align-center">操作</th>
        	</tr>
        </thead>
    <#if friendLinks.numberOfElements == 0>
            <tr>
	            <td colspan="6" class="align-center"><@messages key="common.table.empty" /></td>
            </tr>
            <#else>
            <#list friendLinks.content as l>                            
        <tr>
            <#-- <td class="align-center">00001</td>-->
            <td class="align-center">${l.order!}</td>
            <td class="align-center">${l.name!}</td>
            <td class="align-center">${l.link!}</td>
            <td class="align-center">${(l.type)!}</td>
            <td class="align-center">
                <button type="button" class="btn btn-link editBtn"  pid="${l.id}">编辑</button>
                <button type="button" class="btn btn-link hm-col deleteBtn"  cid="${l.id}">删除</button>
            </td>
        </tr>
            </#list>
     </#if>                    
    </table>
          <ul class="pagination" data-number="${friendLinks.number}" data-total-pages="${friendLinks.totalPages}"></ul>  
    
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
	//点击新增分类按钮
	$("#addBtn").on("click",function(){
		$.link.html(null, {
			url: '${app}/content/addFriendLink',
			target: 'main'
		});
	});
   //点击编辑按钮
	$(".editBtn").on("click",function(){
		var pid = $(this).attr("pid");
		$.link.html(null, {
			url: '${app}/content/editFriendLink?id='+pid,
			target: 'main'
		});
	});
	//点击删除按钮
	$(".deleteBtn").on("click",function(){
		var cid = $(this).attr("cid");
		$.link.html(null, {
			url: '${app}/content/deleteFriendLink?id='+cid,
			target: 'main'
		});
     });				
});
//-->
</script>