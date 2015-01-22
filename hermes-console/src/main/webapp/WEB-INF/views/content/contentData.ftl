<div id="dataResult" style="display:block">
    <table class="table table-striped" style="margin-top:15px;">        	
        <thead>
            <tr>
                <#-- <th class="align-center" width="10%">ID</th> -->
                <th class="align-left"   width="5%">排序</th>
                <th class="align-center" width="20%">文章标题</th>
                <th class="align-center" width="20%">更新时间</th>
                <th class="align-center" width="10%">所属分类</th>
                <th class="align-center" width="10%">发布人</th>
                <th class="align-center" width="20%">操作</th>
                <th class="align-right"  width="15%">选择</th>
        	</tr>
        </thead>
        <#if contentData.numberOfElements == 0>
            <tr>
	            <td colspan="7" class="align-center"><@messages key="common.table.empty" /></td>
            </tr>
            <#else>
            <#list contentData.content as l>                    
            <tr>
                <#-- <td class="align-center">0000001</td>
                <td class="align-center">
                	<input class="form-control" id="disabledInput" type="text" placeholder="6" >
                </td> -->
                <td class="align-center">${l.order!}</td>                
                <td class="align-center">${l.articleTitle!}</td>
                <td class="align-center">${l.updateTime!}</td>
                <td class="align-center">${l.category.name!}</td>
                <td class="align-center">${l.author!}</td>
                <td class="align-center">
                    <button type="button" class="btn btn-link previewBtn" rid="${l.id}">预览</button>
                    <button type="button" class="btn btn-link hm-col editBtn" id="editBtn" pid="${l.id}">编辑</button>
                    <button type="button" class="btn btn-link deleteBtn" id="deleteBtn" cid="${l.id}">删除</button>
                </td>
                <td class="align-right">
                      <input type="checkbox" id="chooseCheckbox" name="deleteBox" value="${l.id}">
                </td>
            </tr>
            </#list>
        </#if>                    
    </table>
    <div class="mr_15px clearfix">
        <div class="pull-right">
            <span class="vlight"><button type="button" class="btn btn-default batch" >批量删除</button></span>
            <span class="vlight hm-col">&nbsp;&nbsp;&nbsp;&nbsp; 全选 <input type="checkbox" class="all" name="artchbox"> </span>
        </div>
    </div>
</div>
  <div class="clearfix">
    <div class="pull-right">
      <ul class="pagination" data-number="${contentData.number}" data-total-pages="${contentData.totalPages}"></ul>                
    </div>
  </div>
  
<script type="text/javascript">
$('.pagination').pagination({
		handler: function(elem) {
			$('#page').val(elem.data().page);
			$('#searchForm').trigger('submit');
		}
	});
	
	
 //点击批量删除按钮  
$(".batch").on("click",function(){
		 var ches=document.getElementsByName("deleteBox");
		 var ids="";		 
		 for(var i=0;i<ches.length;i++){
			   if(ches[i].checked){
			     ids+=ches[i].value+",";
			   }
		 }
		$.link.html(null, {
			   url: '${app}/content/batchDeleteContent?ids='+ids,
			   target: 'main'
	    });		 	
	});

	$(".all").on("click",function(){
		 var ches=document.getElementsByName("deleteBox");
		 for(var i=0;i<ches.length;i++){
		   if(ches[i].checked){
		      ches[i].checked=false;
		   }else{
		    ches[i].checked=true;
		   }		 
		 }		
	});
	
	
//点击编辑按钮
$(".editBtn").on("click",function(){
	var pid = $(this).attr("pid");
	$.link.html(null, {
		url: '${app}/content/editContent?id='+pid,
		target: 'main'
	});
});	
//点击预览按钮
$(".previewBtn").on("click",function(){
	var rid = $(this).attr("rid");
	window.open('${app}/content/preview?id='+rid);

});	


//点击删除按钮
$(".deleteBtn").on("click",function(){
	var cid = $(this).attr("cid");
	$.link.html(null, {
		url: '${app}/content/deleteContent?id='+cid,
		target: 'main'
	});
});				
			

</script> 