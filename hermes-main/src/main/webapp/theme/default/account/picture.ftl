<div class="panel-group" id="accordion">
	<#list labels as label>
		<div class="panel panel-default">
			<div class="panel-heading clearfix">
		      <h5 class="panel-title pull-left">${label.name}</h5>
		      <input id="label${label.id}" name="label" type="hidden" value="${label.id}" />
		        <div class="pull-right">
		        	<a href="#" data-toggle="collapse" data-parent="#accordion" data-target="#collapse${label_index}" >
		       			 <i class="fa fa-angle-double-up" style="font-size:1.5em;"></i>
		       		 </a>
		        </div>
		    </div>
		      <div id="collapse${label_index}" class="panel-collapse collapse in">
		      <div class="panel-body">
			      <div class="row">
			      	<div id="imageGrid">
						<#list images as image>
							<#if label.id==image.label.id>
								<div class="col-sm-6 col-md-3">
				      				<div class="thumbnail">
										<img alt="" src="${image.image}" style="height:100px;" />
									</div>
			      				</div>
							</#if>
						</#list>
						<div id="addImage${label.id}" class="col-sm-6 col-md-3">
							<div class="thumbnail" style="height:100px;">
								<div class="caption add-block" style="padding-top:25%;">
									<span><a href="#" class="ad_img"  id="${label.id}"><i class="fa fa-plus-circle" style="font-size:2em;"></i></a></span>
									<input id="file" name="file" type="file" multiple="multiple" class="hidden" />
								</div>
							</div>
						</div>
					</div>
			      </div>
		      </div>
		    </div>
		</div>
	</#list>
</div>


<script type="text/javascript" charset="utf-8">
<!--
var labelId;
jQuery(function($) {
	// 点击上传处理
	$('#imageGrid .ad_img').on('click', function(e) {labelId=this.id;$('#file').click();});
	$('#file').on('change', function(e) {upload($(this).get(0).files);});
	
	// 拖拽上传处理
	$('#imageGrid .ad_img').on('dragenter', function(e) {
		$(this).css('border-style', 'dashed');
	}).on('dragover', function(e) {
		return false;
	}).on('drop', function(e) {
		labelId=this.id
		upload(e.originalEvent.dataTransfer.files);
		$(this).trigger('dragleave');
		return false;
	}).on('dragleave', function(e) {
		$(this).css('border-style', 'solid');
	});
	
	$('#accordion .panel-heading .fa').on('click', function() {
		if ($(this).hasClass('fa-angle-double-down')) {
			$(this).removeClass('fa-angle-double-down').addClass('fa-angle-double-up');
		} else {
			$(this).removeClass('fa-angle-double-up').addClass('fa-angle-double-down');
		}
	});
});
// 异步上传
function upload(files) {
	$.each(files, function(i, file) {
		// 初始化
		var reader = new FileReader(), xhr = new XMLHttpRequest(), formData = new FormData();
		var entry = $('#addImage'+labelId);
		
		// 定义加载事件
		reader.onload = function(e) { $(['<div class="col-sm-6 col-md-3"><div class="thumbnail"><img alt="', file.name, '" src="', e.target.result, '" /></div></div>'].join('')).insertBefore(entry);};
		reader.readAsDataURL(file);
		
		// 异步上传
		formData.append('label', $('#label'+labelId).val());
		formData.append('file', file);
		xhr.open('POST', '${app}/account/uploadImage');
		 //xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");//post提交设置
		xhr.send(formData);
	});
}
//-->
</script>