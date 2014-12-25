<#include "/loan/audittop.ftl" />
<#include "/loan/auditmiddle.ftl" />
<form id="dataForm" method="post" action="#" class="form-horizontal">
			<div class="panel-group" id="accordion">
			<#list labels as label>
				<div class="panel panel-default">
					<div class="panel-heading clearfix">
				      <h5 class="control-label pull-left ">${label.name}	</h5>
				      <input id="label${label.id}" name="label" type="hidden" value="${label.id}" class="label"/>
				        <div class="pull-right">
				        	<a href="#" data-toggle="collapse" data-parent="#accordion" data-target="#collapse${label_index}" >
				       			 <i class="fa fa-angle-double-up" style="font-size:1.5em;"></i>
				       		 </a>
				        </div>
				    </div>
				      <div id="collapse${label_index}" class="panel-collapse collapse">
					      <div class="panel-body">
						      <div class="row">
						      </div>
					      </div>
				    </div>
				</div>
			</#list>
		</div>
		
		
		<div class="panel panel-primary">
			<div class="panel-heading">审核结果</div>
			<div class="panel-body">
					<div class="form-group">
						<label for="remark" class="col-xs-2 control-label">借款金额调低至</label>
						<div class="col-xs-2">
							<input id="fixAmount" name="fixAmount" class="form-control"></input>
						</div>
					</div>
					<div class="form-group">
						<label for="remark" class="col-xs-2 control-label">备注</label>
						<div class="col-xs-5">
							<textarea id="remark" name="remark" rows="2" class="form-control"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-2 control-label">&nbsp;</label>
						<div class="col-xs-10">
							<button type="button" data-status="00" class="btn btn-success">通过</button>
							<button type="button" data-status="01" class="btn btn-danger">驳回</button>
							<button type="button" class="btn btn-default"><@messages key="common.op.cancel" /></button>
							<input id="detailStatus" name="status" type="hidden">
						</div>
					</div>
			</div>
		</div>
		
	<input id="loanId" name="loanId" type="hidden" value="${loanId}"></input>

</form>
<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {

	// 初始化
	var _form_search = $('#searchForm'),
		_form_data = $('#dataForm'),
		_area_remark = $('#remark'),		_area_fixAmount = $('#fixAmount'),
		_hide_status = $('#detailStatus'),
		flag = true;
		
	var confirmPass = $.scojs_confirm({  
	 content: "确认终审通过操作吗?",
	action: function() {
		doSubmit();
		this.close(); 
		}
	});
	var confirmReject = $.scojs_confirm({  
	 content: "确认终审驳回操作吗?",
	action: function() {
		doSubmit();
		this.close(); 
		}
	});
		
	// 绑定按钮事件
	_form_data.find('button').on('click', function() {
		if ($(this).hasClass('btn-default')) {
			_form_search.trigger('submit');
		} else if (_area_remark.val() === '' ||_area_fixAmount.val()!='' ) {
			_area_remark.next().remove();
			_area_fixAmount.next().remove();
			
			if(_area_remark.val() === '')
			{
				_area_remark.after($('<p />').addClass('help-block').html('<i class="fa fa-times-circle"></i> <@messages key="withdraw.label.remark.required" />')).parent().parent().addClass('has-error');
			}
			//有值的话判断是否是数字且精确到2位小数 
			else if(!(/^([1-9]\d*)(\.\d{1,2})?$/.test(_area_fixAmount.val())))
			{
				_area_fixAmount.after($('<p />').addClass('help-block').html('<i class="fa fa-times-circle"></i>金额必须为整数或小数，小数点后不超过2位')).parent().parent().addClass('has-error');
			}
			//有值的话判断是否是数字是50的倍数
			else if(!(/^([1-9]\d*)(\.0+)?$/.test(_area_fixAmount.val()) && _area_fixAmount.val()%50 === 0))
			{
				_area_fixAmount.after($('<p />').addClass('help-block').html('<i class="fa fa-times-circle"></i>输入数字应为50的整倍')).parent().parent().addClass('has-error');
			}
			else
			{	
				$.ajax({
				     url:  "${app}/loan/checkMoneyMore?loanId="+$("#loanId").val(),
				     dataType: 'json',
				     async: false,
				     data: 'fixAmount=' + _area_fixAmount.val(),
				     success:function(data) {
				     	 if(data["fixAmount"]) {
				     	 	 flag = true;
				     	 }
				     	 else
				     	 {
							_area_fixAmount.after($('<p />').addClass('help-block').html('<i class="fa fa-times-circle"></i>不可大于当前借款金额')).parent().parent().addClass('has-error');
							 flag = false;
				     	 }
					 }
	  			 })
	  			 if(flag) {
		  			  	//$(this).html('<i class="fa fa-spinner fa-spin"></i> <@messages key="common.op.doing" />').parent().find('button').prop('disabled', true);
						_hide_status.val($(this).data().status);
						if(_hide_status.val()=='00')
						{
							confirmPass.show();
						}
						else if(_hide_status.val()=='01')
						{
							confirmReject.show();
						}
					
				 }
			}
		} else {
			//$(this).html('<i class="fa fa-spinner fa-spin"></i> <@messages key="common.op.doing" />').parent().find('button').prop('disabled', true);
			_hide_status.val($(this).data().status);
			if(_hide_status.val()=='00')
			{
				confirmPass.show();
			}
			else if(_hide_status.val()=='01')
			{
				confirmReject.show();
			}
			
		}
	});	
	function doSubmit(){
		$.ajax({
				data: _form_data.serialize(),
			    url: "${app}/loan/firstaudit",
			    type: "POST",
			    dataType: 'json',
				success: function(data){
					if(data.type=="SUCCESS"){
						_form_search.trigger('submit');
					}
				}
			});	
	}
	$('button').link();
 	$('#navTab a:first').tab('show') // Select first tab
 	$("#navTab").find('li').click(function(){
 		var index = $(this).index();
 		if(index==0){
 			init("${app}/loan/getUserBasic/${loanUserId}","basicInfo");
 		}else if(index==1){
 			init("${app}/user/loadJob/${loanUserId}","jobInfo");
 		}else if(index==2){
 			init("${app}/user/loadHouse/${loanUserId}","hourseInfo");
 		}else if(index==3){
 			init("${app}/user/loadCar/${loanUserId}","carInfo");
 		}else if(index==4){
 			init("${app}/user/loadContacter/${loanUserId}","contacterInfo");
 		}
 	});
	init("${app}/loan/getUserBasic/${loanUserId}","basicInfo");
	$('#accordion .panel-heading .fa').on('click', function() {
		if ($(this).hasClass('fa-angle-double-down')) {
			$(this).removeClass('fa-angle-double-down').addClass('fa-angle-double-up');
		} else {
			$(this).removeClass('fa-angle-double-up').addClass('fa-angle-double-down');
			var labelId=$(this).closest('.panel').find('.label').val();
			var content=$(this).closest('.panel').find('.row');
			$.ajax({
			     url: "${app}/loan/loadPicture/${loanUserId}/"+labelId,
			     success:function(data) {
			       	content.html(data); // 内容装入div中
				 }
	  		 });
		
		}
	});
});
function init(_url,_content){
		$.ajax({
		     url: _url,
		     success:function(data) {
		       $("#"+_content).html(data) // 内容装入div中
			 }
	   })
}	
//-->
</script>