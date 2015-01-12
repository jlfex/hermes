 <div class="panel panel-primary">
        <div class="panel-heading">内容发布</div>
        <div class="panel-body">
            <div id="data" style="display:block">
          <form class="form-horizontal" role="form">
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span class="color_red">* </span>文章标题</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="" placeholder="文章标题">
                </div>
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">发布人</label>
                <div class="col-sm-5">
                  <span class="vlight">admin</span>
                </div>
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span class="color_red">* </span>所属分类</label>
                <div class="col-sm-8">
                  <div class="row">
                        <div class="col-xs-2 hm-col">
                            <label for=""  class="sr-only">选择一级分类</label>
                            <select id="status" name="status" class="form-control">
                                <option value="">选择一级分类</option>
                                <option value="00">帮助中心</option>
                                <option value="10">了解易联</option>
                            </select>
                        </div>
                        <div class="col-xs-2 hm-col">
                            <label for=""  class="sr-only">选择二级分类</label>
                            <select id="status" name="status" class="form-control">
                                <option value="">选择二级分类</option>
                                <option value="00">帮助中心</option>
                                <option value="10">了解易联</option>
                            </select>
                        </div>
                        <div class="col-xs-2 hm-col">
                            <label for=""  class="sr-only">选择三级分类</label>
                            <select id="status" name="status" class="form-control">
                                <option value="">选择三级分类</option>
                                <option value="00">帮助中心</option>
                                <option value="10">了解易联</option>
                            </select>
                        </div>
                    </div>
                
                  
                </div>
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">类型</label>
                <div class="col-sm-5">
                  <select id="status" name="status" class="form-control">
            <option value="">文章页面</option>
            <option value="00">帮助中心</option>
            <option value="10">了解易联</option>
          </select>
                </div>
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">排序</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="" placeholder="1">
                </div>
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">文章关键字</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="" placeholder="文章关键字">
                </div>
                <span class="vlight">请以，号隔开</span>
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">文章描述</label>
                <div class="col-sm-8">
                  <textarea class="form-control" rows="3"></textarea>
                </div>
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span class="color_red">* </span>内容编辑</label>
                <div class="col-sm-8">
                  <textarea id="editor"></textarea>
                </div>
              </div>
              
              <div class="form-group">
                <div class="col-sm-offset-2 col-xs-1">
                  <button type="submit" class="btn btn-primary btn-block">发布</button>
                </div>
              </div>
            </form>     
        </div> 
        </div>
      </div>