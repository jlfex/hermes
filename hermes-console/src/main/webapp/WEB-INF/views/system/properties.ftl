<div class="panel panel-primary">
  <div class="panel-heading">请填写配置项</div>
  <div class="panel-body">
    <div id="data" style="display:block">
      <form id="propertiesForm" class="form-horizontal" role="form" enctype="multipart/form-data">
        <p class="success-message"></p>
        <!--业务规则配置-->
        <div class="form-group">
          <label for="inputName" class="col-sm-2 control-label"><span class="color_red">* </span>平台LOGO</label>
          <div class="col-sm-10">
            <div class="form-group">
              <div class="col-xs-5">
                <label for="logo" class="sr-only">上传文件</label>
                <input id="logo" name="logo" class="file_file form-control" type="file">
              </div>
            </div>
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-offset-2 col-sm-10">
            <span class="help-block">图片像素大小限制：168*50</span>
          </div>
          <div class="col-sm-offset-2 col-sm-10">
            <div><img id="logoImage" src="${logo}" ></div>
          </div>
        </div>
        <div class="form-group">
          <label for="operationName" class="col-sm-2 control-label"><span class="color_red">* </span>平台名称</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="operationName" name="operationName" placeholder="最多不超过8个汉字，如开源P2P平台" value="<@config key="app.operation.name"/>">
            <p class="help-block"></p>
          </div>
        </div>
        <div class="form-group">
          <label for="operationNickname" class="col-sm-2 control-label">平台简称</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="operationNickname" name="operationNickname" placeholder="如hermes" value="<@config key="app.operation.nickname"/>">
          </div>
        </div>
        <div class="form-group">
          <label for="website" class="col-sm-2 control-label">平台网址</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="website" name="website" placeholder="" value="<@config key="app.website"/>">
          </div>
        </div>
        <div class="form-group">
          <label for="copyright" class="col-sm-2 control-label">版权所有</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="copyright" name="copyright" placeholder="" value="<@config key="app.copyright"/>">
          </div>
        </div>
        <div class="form-group">
          <label for="icp" class="col-sm-2 control-label">备案号</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="icp" name="icp" placeholder="" value="<@config key="app.icp"/>">
          </div>
        </div>
        <div class="form-group">
          <label for="serviceTel" class="col-sm-2 control-label">客服电话</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="serviceTel" name="serviceTel" placeholder="" value="<@config key="site.service.tel"/>">
          </div>
        </div>
        <div class="form-group">
          <label for="serviceTime" class="col-sm-2 control-label">工作时间</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="serviceTel" name="serviceTime" placeholder="" value="<@config key="site.service.time"/>">
          </div>
        </div>
        <div class="form-group">
          <label for="companyName" class="col-sm-2 control-label"><span class="color_red">* </span>公司名称</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="companyName" name="companyName" placeholder="如上海金鹿金融信息服务有限公司" value="<@config key="app.company.name"/>">
            <p class="help-block"></p>
          </div>
        </div>
        <div class="form-group">
          <label for="companyAddress" class="col-sm-2 control-label">公司地址</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="companyAddress" name="companyAddress" placeholder="" value="<@config key="app.company.address"/>">
          </div>
        </div>
        <div class="form-group">
          <label for="companyCity" class="col-sm-2 control-label"><span class="color_red">* </span>公司所在地</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="companyCity" name="companyCity" placeholder="如上海" value="<@config key="app.company.city"/>">
            <p class="help-block"></p>
          </div>
        </div>
        <!--邮箱配置-->
        <div class="form-group">
          <label for="smtpHost" class="col-sm-2 control-label">邮件服务器</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="smtpHost" name="smtpHost" placeholder="" value="<@config key="mail.smtp.host"/>">
          </div>
        </div>
        <div class="form-group">
          <label for="smtpPort" class="col-sm-2 control-label">端口</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="smtpPort" name="smtpPort" placeholder="" value="<@config key="mail.smtp.port"/>">
          </div>
        </div>
        <div class="form-group">
          <label for="smtpUsername" class="col-sm-2 control-label">邮件账号</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="smtpUsername" name="smtpUsername" placeholder="" value="<@config key="mail.smtp.username"/>">
          </div>
        </div>
        <div class="form-group">
          <label for="smtpPassword" class="col-sm-2 control-label">邮件密码</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="smtpPassword" name="smtpPassword" placeholder="" value="<@config key="mail.smtp.password"/>">
          </div>
        </div>
        <div class="form-group">
          <label for="mailFrom" class="col-sm-2 control-label">邮件发送地址</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="mailFrom" name="mailFrom" placeholder="" value="<@config key="mail.from"/>">
          </div>
        </div>
        <div class="form-group">
          <label for="serviceEmail" class="col-sm-2 control-label">客服邮箱</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="serviceEmail" name="serviceEmail" placeholder="" value="<@config key="app.customer.service.email"/>">
          </div>
        </div>
        <div class="form-group">
          <label for="jobNoticeEmail" class="col-sm-2 control-label">任务提醒通知地址</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="jobNoticeEmail" name="jobNoticeEmail" placeholder="" value="<@config key="address.job.notice"/>">
          </div>
        </div>
        <!--业务规则配置-->
        <div class="form-group">
          <label for="indexLoanSize" class="col-sm-2 control-label">首页标的数量</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="indexLoanSize" name="indexLoanSize" placeholder="" value="<@config key="index.loan.size"/>">
          </div>
        </div>
        <div class="form-group">
          <label for="emailExpire" class="col-sm-2 control-label">邮件认证失效时间</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="emailExpire" name="emailExpire" placeholder="" value="<@config key="auth.email.expire"/>">
          </div>
        </div>
        <div class="form-group">
          <label for="smsExpire" class="col-sm-2 control-label">短信验证码失效时间</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="smsExpire" name="smsExpire" placeholder="" value="<@config key="auth.sms.expire"/>">
          </div>
        </div>
        <!--业务规则配置-->
        <div class="form-group">
          <label for="realnameSwitch" class="col-sm-2 control-label">实名认证开关</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="realnameSwitch" name="realnameSwitch" placeholder="" value="<@config key="auth.realname.switch"/>">
          </div>
        </div>
        <div class="form-group">
          <label for="cellphoneSwitch" class="col-sm-2 control-label">手机认证开关</label>
          <div class="col-sm-5">
            <input type="text" class="form-control" id="cellphoneSwitch" name="cellphoneSwitch" placeholder="" value="<@config key="auth.cellphone.switch"/>">
          </div>
        </div>
        <div class="form-group">
          <div class="row">
            <div class="col-sm-offset-2 col-xs-5">
              <div class="col-xs-3"><button id="submit" type="submit" class="btn btn-primary btn-block">提交</button></div>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>
<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
$("#logo").on("change", function(){
// Get a reference to the fileList
var files = !!this.files ? this.files : [];

// If no files were selected, or no FileReader support, return
if (!files.length || !window.FileReader) return;

// Only proceed if the selected file is an image
if (/^image/.test( files[0].type)){

// Create a new instance of the FileReader
var reader = new FileReader();

// Read the local file as a DataURL
reader.readAsDataURL(files[0]);

// When loaded, set image data as background of div
reader.onloadend = function(){

$("#logoImage").attr("src",this.result);

}

}

});
// 绑定输入框事件
$('input').on('blur', function() {
if ($(this).val() != '') {
$(this).parent().removeClass('has-error').find('.help-block').fadeOut('fast');
$('.success-message').fadeOut('fast');
}
});

// 绑定表单提交事件
$('#propertiesForm').submit(function(event) {
event.preventDefault();
var check = false;
if($("#companyName").val()==''){
$("#companyName").parent().addClass('has-error').find('.help-block').html('<i class="fa fa-times-circle"></i> 不能为空').fadeIn('fast');
check = true;
}
if($("#operationName").val()==''){
$("#operationName").parent().addClass('has-error').find('.help-block').html('<i class="fa fa-times-circle"></i> 不能为空').fadeIn('fast');
check = true;
}
if($("#operationAddress").val()==''){
$("#operationAddress").parent().addClass('has-error').find('.help-block').html('<i class="fa fa-times-circle"></i> 不能为空').fadeIn('fast');
check = true;
}
if(check){ return false;}
// 初始化
var _elem = $(this);

// 验证输入是否均合法
if (_elem.find('.has-error').size() > 0) {
_elem.find('.error-message').html('<i class="fa fa-times-circle"></i> <@messages key="sign.in.error" />').fadeIn('fast');
return false;
}

var formData = new FormData(_elem[0]);
// 提交异步请求
$.ajax('${app}/system/properties/save', {
data: formData,
type: 'POST',
timeout: 5000,
async: false,
cache: false,
contentType: false,
processData: false,
success: function(data, textStatus, xhr) {
if (data.typeName === 'success') {
_elem.find('.success-message').addClass('alert alert-success').html(data.firstMessage).fadeIn('fast');
} else if (data.typeName === 'warning') {
_elem.find('.success-message').addClass('alert alert-warning').html( data.firstMessage).fadeIn('fast');
} else {
_elem.find('.success-message').addClass('alert alert-danger').html(data.firstMessage).fadeIn('fast');
}
}
});

// 中断事件
return false;
});
});
//-->
</script>