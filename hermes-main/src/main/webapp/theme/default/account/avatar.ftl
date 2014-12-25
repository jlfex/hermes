<div>
		<ul class="thumbnails" >
	     		<li class="span4" >
		     		<a href="#" class="thumbnail" >
	               	 <img id="original" alt="" style="width: 270px; height: 270px;" src="">
	              	</a>
	     		</li>
	     		<li class="span3" >
	     			<a href="#" class="thumbnail">
		     			<div  id="preview" style="width: 100px; height: 100px; overflow: hidden;">
		     				<img  id="previewImg" alt="" style="width: 100px; height: 100px;" src="">
		     			</div>
	              	</a>
	     		</li>
	     		<li class="span3">
	     					<button type="button" id="chooseAvatar" class="btn btn-primary"><@messages key="account.info.choose.avatar" /></button>
	     						<div>&nbsp;</div>
	     					<button type="button" id="save" class="btn btn-primary"><@messages key="account.info.save.avatar" /></button>
	     		</li>
	     	</ul>
	     	<input id="avatar" name="avatar" type="file" multiple="multiple" class="hidden" />
	     	<input id="startX" name="startX" type="text" class="hidden"/>
			<input id="startY" name="startY" type="text" class="hidden"/><br/>
			<input id="width" name="width" type="text" class="hidden"/>
			<input id="height" name="height" type="text" class="hidden"/>
</div>
<link rel="stylesheet" type="text/css" href="${app.css}/imgareaselect-animated.css">
<script src="${app.js}/jquery.imgareaselect.js"></script>
<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
	
	//上传头像
	$('#avatar').on('change', function(e) {uploadImg($(this).get(0).files);});
	$('#original').imgAreaSelect({
		aspectRatio : '1:1',
		handles : true,
		fadeSpeed : 200,
		onSelectChange : preview
	});
	$("#chooseAvatar").on("click",function(){
		$('#avatar').click();
	});
	$("#save").on("click",function(){
		if($("#avatar").get(0).files.length==0){
		   return false;
		}
		var name=$("#avatar").get(0).files[0].name;
		var imgExt = name.substring(name.lastIndexOf("."));
		if(imgExt=='.jpg'||imgExt=='.jpeg'||imgExt=='.gif'||imgExt=='.png'||imgExt=='.bmp'){
			var reader = new FileReader(), xhr = new XMLHttpRequest(), formData = new FormData();
			var startX,startY,width,height;
			startX=$("#startX").val();
			startY=$("#startY").val();
			width=$("#width").val();
			height=$("#height").val();
			var imgWidth = $("#original").width();
			var imgHeight = $("#original").height();
			$.each($("#avatar").get(0).files,function(i,file){
				// 异步上传
				formData.append('file',file );
				formData.append('startX',startX);
				formData.append('startY',startY);
				formData.append('width',width);
				formData.append('height',height);
				formData.append('imgWidth',imgWidth);
				formData.append('imgHeight',imgHeight);
				xhr.open('POST', '${app}/account/saveAvatar');
				xhr.send(formData);
			});
		}
		
	});
});
// 异步上传
function uploadImg(files) {
	$.each(files, function(i, file) {
		// 初始化
		var reader = new FileReader();
		
		// 定义加载事件
		reader.onload = function(e) { $('#original').attr('src',e.target.result);$('#previewImg').attr('src',e.target.result);};
		reader.readAsDataURL(file);
		
	});
}
function preview(img, selection) {
	if (!selection.width || !selection.height)
		return;
	var scaleX = 100 / selection.width;
	var scaleY = 100 / selection.height;

	$('#preview img').css({
		width : Math.round(scaleX * 300),
		height : Math.round(scaleY * 300),
		marginLeft : -Math.round(scaleX * selection.x1),
		marginTop : -Math.round(scaleY * selection.y1)
	});
	$("#startX").val(selection.x1);
	$("#startY").val(selection.y1);
	$("#width").val(selection.width);
	$("#height").val(selection.height);
}
//-->
</script>