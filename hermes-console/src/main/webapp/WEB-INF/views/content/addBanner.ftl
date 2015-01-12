	<form class="form-horizontal" role="form">
                  <div class="form-group">
                    <label for="inputName" class="col-sm-2 control-label">所诉分类</label>
                    <div class="col-sm-10">
                      	<select id="status" name="status" class="form-control">
                            <option value="">文章页面</option>
                            <option value="00">帮助中心</option>
                            <option value="10">了解易联</option>
						</select>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputName" class="col-sm-2 control-label">专题名称</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputName" placeholder="专题名称">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputName" class="col-sm-2 control-label">专题地址</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputName" placeholder="专题地址">
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="inputName" class="col-sm-2 control-label">排序</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputName" placeholder="1">
                    </div>
                  </div>
                  
                  <div class="form-group">
                        <label for="inputName" class="col-sm-2 control-label">上传图片</label>
                        <div class="col-sm-10">
                            <div class="form-group">
                                <div class="col-xs-8">
                                    <label for="upload" class="sr-only">上传文件</label>
                                    <input id="file" name="" value="" class="file_file form-control" type="file"  onchange="setValue()">
                                    <input type='text' class="form-control" id='file_txt' value="选择文件" />
                                </div>
                                <div class="col-xs-2"><button class=" btn btn-default">导入</button></div>
                            </div>
                        </div>
                  </div>
                  
                  <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                    	<span class="help-block">图片请根据实际显示大小上传</span>   
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                    	<img src="images/logo.png" width="132" height="71">
                    </div>
                  </div>
                  
                  <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <a class="hm-link" href="#" data-url="" data-target="data">预览大图</a>
                    </div>
                  </div>
                  
                </form>  