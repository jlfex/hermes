<table class="table table-bordered">        	
    <thead>
        <tr>
            <#-- <th class="align-center">编号</th>-->
            <th class="align-center">排序</th>
            <th class="align-center">网站名称</th>
            <th class="align-center">网站地址</th>
            <th class="align-center">logo</th>
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
            <td class="align-center">l.order</td>
            <td class="align-center">l.name</td>
            <td class="align-center">l.link</td>
            <td class="align-center"></td>
            <td class="align-center">l.category.name</td>
            <td class="align-center">
                <button type="button" class="btn btn-link" data-toggle="modal" data-target="#myModal02">编辑</button>
                <button type="button" class="btn btn-link hm-col">删除</button>
            </td>
        </tr>
            </#list>
     </#if>                    
</table>
      <ul class="pagination" data-number="${friendLinks.number}" data-total-pages="${friendLinks.totalPages}"></ul>  
                    
<script type="text/javascript">
jQuery(function($) {
$('.pagination').pagination({
		handler: function(elem) {
			$('#page').val(elem.data().page);
			$('#searchForm').trigger('submit');
		}
	});
});
</script> 
