<div class="panel panel-primary">
  <div class="panel-heading">请填写配置项</div>
  <div class="panel-body">
    <form role="form" method="post" action="${app}/system/properties/save">
      <div class="row hm-row form-group">
        <label class="col-xs-2 control-label" for="logo">平台LOGO</label>
        <input type="file" class="form-control" id="logo" placeholder="选择文件">
      </div>
      <div class="row hm-row form-group">
        <label class="col-xs-2 control-label" for="appName">平台名称</label>
        <div class="col-xs-3">
          <input type="text" class="form-control" id="appName" placeholder="最多不超过8个汉字">
        </div>
      </div>
      <div class="row hm-row form-group">
        <label class="col-xs-2 control-label" for="copyRight">版权所有</label>
        <div class="col-xs-3"><input type="text" class="form-control" id="copyRight"></div>
      </div>
      <div class="row hm-row form-group">
        <label class="col-xs-2 control-label" for="copyRight">备案号</label>
        <div class="col-xs-3"><input type="text" class="form-control" id="copyRight"></div>
      </div>
      <div class="row hm-row form-group">
        <label class="col-xs-2 control-label" for="copyRight">客服电话</label>
        <div class="col-xs-3"><input type="text" class="form-control" id="copyRight"></div>
      </div>
      <div class="row hm-row form-group">
        <div class="col-xs-offset-2 col-xs-1">
          <button type="submit" class="btn btn-primary btn-block">保存</button>
        </div></div>
      </form>
    </div>
  </div>