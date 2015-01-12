          <div class="modal-body">
           		<form class="form-horizontal" role="form" id="addForm">
                  <div class="form-group">
                    <label for="inputName" class="col-sm-2 control-label">分类名称</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputName" name = "inputName" placeholder="分类名称">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputBefore" class="col-sm-2 control-label">上级分类</label>
                    <div class="col-sm-10">
                        <div class="row">
                            <div class="col-xs-6 hm-col">
                                <select class="form-control" name = "categoryLevelOne">
                                  <option>选择分类</option>
                                  <option>帮助中心</option>
                                  <option>新闻动态</option>
                                  <option>了解易联</option>
                                </select>
                            </div>
                            <div class="col-xs-6 hm-col">
                                <select class="form-control" name = "categoryLevelTwo">
                                  <option>选择分类</option>
                                  <option>基础分类</option>
                                  <option>了解易联</option>
                                  <option>新闻动态</option>
                                </select>
                            </div>
                        </div>
                    </div>
                  </div>
                </form>   
               </div>
  	             <div class="row">
	                <div class="col-sm-offset-2 col-sm-10">
	                    <div class="col-xs-3"><button type="button" class="btn btn-primary btn-block" id="addCategoryButton">新增</button></div>
	                    <div class="col-xs-3"><button type="button" class="btn btn-default btn-block" id="cacelCategoryButton">取消</button></div>
	                </div>
	            </div>            


<script type="text/javascript">
jQuery(function($) {

	$("#addCategoryButton").on("click",function(){
		$.link.html(null, {
			url: '${app}/content/saveAddedCategory',
			data: $("#addForm").serialize(),
			target: 'main'
		});
	});
	$("#cacelCategoryButton").on("click",function(){
		$.link.html(null, {
			url: '${app}/content/categoryIndex',
			target: 'main'
		});
	});
});
</script>  