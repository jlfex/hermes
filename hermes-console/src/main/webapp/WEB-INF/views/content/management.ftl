<div class="row panel-body">
        	<div class="col-xs-1">
        		<button class="btn btn-primary btn-block" type="button" id="publishContent">+ 发布内容</button>
            </div>
        </div>
        <div class="row">
        	<form id="searchForm" method="post" action="#">
			<div class="row">
            	<div class="col-xs-2 hm-col form-group">
					<label for="status" class="sr-only">分类</label>
					<select id="status" name="status" class="form-control">
						<option value="">选择一级分类</option>
						<option value="00">帮助中心</option>
						<option value="10">了解易联</option>
					</select>
				</div>
                
                <div class="col-xs-2 hm-col form-group">
					<label for="status" class="sr-only">分类</label>
					<select id="status" name="status" class="form-control">
						<option value="">选择二级分类</option>
						<option value="00">帮助中心</option>
						<option value="10">了解易联</option>
					</select>
				</div>
                
                <div class="col-xs-2 hm-col form-group">
					<label for="status" class="sr-only">分类</label>
					<select id="status" name="status" class="form-control">
						<option value="">选择三级分类</option>
						<option value="00">帮助中心</option>
						<option value="10">了解易联</option>
					</select>
				</div>
                
				<div class="col-xs-2 hm-col form-group">
					<label for="name" class="sr-only">内容</label>
					<input id="content" name="name" class="form-control" type="text">
				</div>
                
				<div class="col-xs-1 hm-col form-group">
					<label class="sr-only">&nbsp;</label>
					<button id="searchBtn" type="button" class="btn btn-primary btn-block">查询</button>
					<input id="page" name="page" value="0" type="hidden">
				</div>
			</div>
		</form>
        </div>
        <div id="data" style="display:block">
            <table class="table table-striped">        	
                <thead>
                    <tr>
                        <th class="align-center" width="10%">ID</th>
                        <th class="align-left"   width="5%">排序</th>
                        <th class="align-center" width="20%">文章标题</th>
                        <th class="align-center" width="10%">更新时间</th>
                        <th class="align-center" width="10%">所属分类</th>
                        <th class="align-center" width="10%">发布人</th>
                        <th class="align-center" width="20%">操作</th>
                        <th class="align-right"  width="15%">选择</th>
                	</tr>
                </thead>
                    <tbody>
                    <tr>
                        <td class="align-center">0000001</td>
                        <td class="align-center">
                        	<input class="form-control" id="disabledInput" type="text" placeholder="6" >
                        </td>
                        <td class="align-center">易联天下上线通知</td>
                        <td class="align-center">2014-10-27</td>
                        <td class="align-center">易联公告</td>
                        <td class="align-center">发布人</td>
                        <td class="align-center">
                            <button type="button" class="btn btn-link">预览</button>
                            <button type="button" class="btn btn-link hm-col">编辑</button>
                            <button type="button" class="btn btn-link">删除</button>
                        </td>
                        <td class="align-right">
                              <input type="checkbox" id="chooseCheckbox" value="option1">
                        </td>
                    </tr>
					<tr>
                        <td class="align-center">0000001</td>
                        <td class="align-center">
                        	<input class="form-control" id="disabledInput" type="text" placeholder="6" >
                        </td>
                        <td class="align-center">易联天下上线通知</td>
                        <td class="align-center">2014-10-27</td>
                        <td class="align-center">易联公告</td>
                        <td class="align-center">发布人</td>
                        <td class="align-center">
                            <button type="button" class="btn btn-link">预览</button>
                            <button type="button" class="btn btn-link hm-col">编辑</button>
                            <button type="button" class="btn btn-link">删除</button>
                        </td>
                        <td class="align-right">
                              <input type="checkbox" id="chooseCheckbox" value="option1">
                        </td>
                    </tr>
                    <tr>
                        <td class="align-center">0000001</td>
                        <td class="align-center">
                        	<input class="form-control" id="disabledInput" type="text" placeholder="6" >
                        </td>
                        <td class="align-center">易联天下上线通知</td>
                        <td class="align-center">2014-10-27</td>
                        <td class="align-center">易联公告</td>
                        <td class="align-center">发布人</td>
                        <td class="align-center">
                            <button type="button" class="btn btn-link">预览</button>
                            <button type="button" class="btn btn-link hm-col">编辑</button>
                            <button type="button" class="btn btn-link">删除</button>
                        </td>
                        <td class="align-right">
                              <input type="checkbox" id="chooseCheckbox" value="option1">
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="mr_15px clearfix">
                <div class="pull-right">
                    <span class="vlight"><button type="submit" class="btn btn-default">批量删除</button></span>
                    <span class="vlight hm-col">&nbsp;&nbsp;&nbsp;&nbsp; 全选 <input type="checkbox"> </span>
                </div>
            </div>
            <div class="clearfix">
                <div class="pull-right">
                    <ul class="pagination" data-number="0" data-total-pages="4"><li class="active"><a href="#">1</a></li><li><a href="#" data-page="1">2</a></li><li><a href="#" data-page="2">3</a></li><li><a href="#" data-page="3">4</a></li></ul>
                </div>
            </div>
        </div>
        
<script type="text/javascript">
jQuery(function($) {

	$("#publishContent").on("click",function(){
		$.link.html(null, {
			url: '${app}/content/publish',
			target: 'main'
		});
	});

});
</script> 