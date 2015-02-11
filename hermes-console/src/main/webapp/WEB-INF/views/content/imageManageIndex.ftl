 <#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>

<div class="row panel-body">
	<div class="col-xs-1">
		<button  id="addBtn" class="btn btn-primary btn-block" type="button" data-toggle="modal" data-target="#myModal">+ 添加图片</button>
		<input id="page" name="page" type="hidden" value="0"/>
    </div>
</div>


<div id="data" style="display:block">
    <table class="table table-striped table-hover">        	
        <thead>
            <tr>
            <th class="align-center">排序</th>
            <th class="align-center">图片名称</th>
            <th class="align-center">图片地址</th>
            <th class="align-center">图片</th>            
            <th class="align-center">所属分类</th>
            <th class="align-center">操作</th>
        	</tr>
        </thead>
    <#if imageManages.numberOfElements == 0>
            <tr>
	            <td colspan="6" class="align-center"><@messages key="common.table.empty" /></td>
            </tr>
            <#else>
            <#list imageManages.content as l>                            
        <tr>
            <td class="align-center" style="">${(l.order)!}</td>
            <td class="align-center">${(l.name)!}</td>
            <td class="align-center">${(l.link)!}</td>
            <td class="align-center"><img alt="" src="${l.image}" style="height:100px;" /></td>            
            <td class="align-center">${(l.type)!}</td>
            <td class="align-center">
                <button type="button" class="btn btn-link editBtn"  pid="${l.id}">编辑</button>
                <button type="button" class="btn btn-link hm-col deleteBtn"  cid="${l.id}">删除</button>
            </td>
        </tr>
            </#list>
     </#if>                    
    </table>
          <ul class="pagination" data-number="${imageManages.number}" data-total-pages="${imageManages.totalPages}"></ul>      
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
			url: '${app}/content/addImageManage',
			target: 'main'
		});
	});
	//点击编辑按钮
	$(".editBtn").on("click",function(){
		var pid = $(this).attr("pid");
		$.link.html(null, {
			url: '${app}/content/editImageManage?id='+pid,
			target: 'main'
		});
	});	
    //点击删除按钮
	$(".deleteBtn").on("click",function(){
		var cid = $(this).attr("cid");
		$.link.html(null, {
			url: '${app}/content/deleteImageManage?id='+cid,
			target: 'main'
		});
	});
});
//-->
</script>