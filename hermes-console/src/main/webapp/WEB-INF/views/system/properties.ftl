<div class="panel panel-primary">
  <div class="panel-heading">请填写配置项</div>
  <div class="panel-body">
    <div id="data" style="display:block">
      <form class="form-horizontal" role="form">
        
        <div class="form-group">
          <label for="inputName" class="col-sm-2 control-label"><span class="color_red">* </span>平台LOGO</label>
          <div class="col-sm-10">
            <div class="form-group">
              <div class="col-xs-5">
                <label for="upload" class="sr-only">上传文件</label>
                <input id="logo" name="" value="" class="file_file form-control" type="file"  onchange="setValue()">
              </div>
            </div>
          </div>
        </div>
        
        <div class="form-group">
          <div class="col-sm-offset-2 col-sm-10">
            <span class="help-block">图片请根据实际显示大小上传</span>
          </div>
          
          
          <div class="col-sm-offset-2 col-sm-10">
            <div><img src="${logo}" ></div>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label"><span class="color_red">* </span>平台名称</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="" placeholder="最多不超过8个汉字" value="<@config key="app.copyright"/>">
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">平台简称</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="nickname" name="nickname" placeholder="不填则系统默认为平台名称" value="<@config key="app.company.nickname"/>">
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label"><span class="color_red">* </span>运营方名称</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="operationName" name="operationName" placeholder="如上海金鹿金融信息服务有限公司" value="<@config key="app.operation.name"/>">
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label"><span class="color_red">* </span>运营方所在地</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="operationAddress" name="operationAddress" placeholder="如上海" value="<@config key="app.operation.address"/>">
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">运营方联系地址</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="operationContact" name="operationContact" placeholder="" value="<@config key="app.operation.contact"/>">
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">平台网址</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="website" name="website" placeholder="" value="<@config key="app.website"/>">
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">版权所有</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="copyright" name="copyright" placeholder="" value="<@config key="app.copyright"/>">
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">备案号</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="icp" name="icp" placeholder="" value="<@config key="app.icp"/>">
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">客服电话</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="serviceTel" name="serviceTel" placeholder="" value="<@config key="site.service.tel"/>">
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">客服邮箱</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="serviceEmail" name="serviceEmail" placeholder="" value="<@config key="app.customer.service.email"/>">
          </div>
        </div>
        <div class="form-group">
          <div class="row">
            <div class="col-sm-offset-2 col-xs-5">
              <div class="col-xs-3"><button type="submit" class="btn btn-primary btn-block">提交</button></div>
              <div class="col-xs-3"><button type="submit" class="btn btn-default btn-block"  data-dismiss="modal">取消</button></div>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>