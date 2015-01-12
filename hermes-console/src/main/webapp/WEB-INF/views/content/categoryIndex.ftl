		<div class="row panel-body">
        	<div class="col-xs-1">
        		<button class="btn btn-primary btn-block" type="button" id="addBtn">+ 新增分类</button>
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
                    <tbody>
                    <#list categories as l>
                    <tr>
                        <td class="align-center">${l.name}</td>
                        <td class="align-center">1</td>
                        <td class="align-center">${l.level}</td>
                        <td class="align-center">${l.createTime?string('yyyy-MM-dd hh:mm:ss')}</td>
                        <td class="align-center">admin</td>
                        <td class="align-center">
                            <button type="button" class="btn btn-link">编辑</button>
                            <button type="button" class="btn btn-link hm-col">删除</button>
                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>
        
<script type="text/javascript">
	jQuery(function($) {
		
		$("#addBtn").on("click",function(){
			$.link.html(null, {
				url: '${app}/content/addCategory',
				target: 'main'
			});
		});
	});
</script>    