<div class="row panel-body">
        	<div class="col-xs-1">
        		<button class="btn btn-primary btn-block" type="button" id="addBanner">+ 添加专题</button>
            </div>
        </div>
        <div id="data" style="display:block">
            <table class="table table-bordered">        	
                <thead>
                    <tr>
                        <th class="align-center">编号</th>
                        <th class="align-center">排序</th>
                        <th class="align-center">专题名称</th>
                        <th class="align-center">专题地址</th>
                        <th class="align-center">图片</th>
                        <th class="align-center">所属分类</th>
                        <th class="align-center">操作</th>
                	</tr>
                </thead>
                    <tbody>
                    <tr>
                        <td class="align-center">00001</td>
                        <td class="align-center">2</td>
                        <td class="align-center">商业承兑汇票</td>
                        <td class="align-center">www.jlfex.com</td>
                        <td class="align-center"><img src="images/logo.png" width="132" height="71"></td>
                        <td class="align-center">首页Banner</td>
                        <td class="align-center">
                            <button type="button" class="btn btn-link" data-toggle="modal" data-target="#myModal02">编辑</button>
                            <button type="button" class="btn btn-link hm-col">删除</button>
                        </td>
                    </tr>
                    <tr>
                        <td class="align-center">00001</td>
                        <td class="align-center">2</td>
                        <td class="align-center">易联天下</td>
                        <td class="align-center">www.jlfex.com</td>
                        <td class="align-center"><img src="images/logo.png" width="132" height="71"></td>
                        <td class="align-center">首页Banner</td>
                        <td class="align-center">
                            <button type="button" class="btn btn-link" data-toggle="modal" data-target="#myModal02">编辑</button>
                            <button type="button" class="btn btn-link hm-col">删除</button>
                        </td>
                    </tr>
                    <tr>
                        <td class="align-center">00001</td>
                        <td class="align-center">2</td>
                        <td class="align-center">易联天下</td>
                        <td class="align-center">www.jlfex.com</td>
                        <td class="align-center"><img src="images/logo.png" width="132" height="71"></td>
                        <td class="align-center">首页Banner</td>
                        <td class="align-center">
                            <button type="button" class="btn btn-link"  data-toggle="modal" data-target="#myModal02">编辑</button>
                            <button type="button" class="btn btn-link hm-col">删除</button>
                        </td>
                    </tr>
                    <tr>
                        <td class="align-center">00001</td>
                        <td class="align-center">2</td>
                        <td class="align-center">易联天下</td>
                        <td class="align-center">www.jlfex.com</td>
                        <td class="align-center"><img src="images/logo.png" width="132" height="71"></td>
                        <td class="align-center">链首页Banner接</td>
                        <td class="align-center">
                            <button type="button" class="btn btn-link">编辑</button>
                            <button type="button" class="btn btn-link hm-col">删除</button>
                        </td>
                    </tr>
                    <tr>
                        <td class="align-center">00001</td>
                        <td class="align-center">2</td>
                        <td class="align-center">易联天下</td>
                        <td class="align-center">www.jlfex.com</td>
                        <td class="align-center"><img src="images/logo.png" width="132" height="71"></td>
                        <td class="align-center">首页Banner</td>
                        <td class="align-center">
                            <button type="button" class="btn btn-link">编辑</button>
                            <button type="button" class="btn btn-link hm-col">删除</button>
                        </td>
                    </tr>
                    <tr>
                        <td class="align-center">00001</td>
                        <td class="align-center">2</td>
                        <td class="align-center">易联天下</td>
                        <td class="align-center">www.jlfex.com</td>
                        <td class="align-center"><img src="images/logo.png" width="132" height="71"></td>
                        <td class="align-center">首页Banner</td>
                        <td class="align-center">
                            <button type="button" class="btn btn-link">编辑</button>
                            <button type="button" class="btn btn-link hm-col">删除</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    
          
<script type="text/javascript">
jQuery(function($) {

	$("#addBanner").on("click",function(){
		$.link.html(null, {
			url: '${app}/content/addBanner',
			target: 'main'
		});
	});

});
</script> 