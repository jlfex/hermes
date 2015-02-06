 <#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>
 <div class="panel panel-primary">
        <div class="panel-heading">添加图片</div>
        <div class="panel-body">
            <div id="data" style="display:block">
          <form class="form-horizontal" id="addForm" method="post"  enctype="multipart/form-data">
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>所属分类</label>
                <div class="col-sm-5">
                   <select id="type" name="type" class="form-control" onchange=check(this.value)>
                   	  <option value="1">首页banner广告</option>
					  <option value="2">首页—我要理财</option>
					  <option value="3">首页—我要借款</option>
					  <option value="4">登录界面</option>
					  <option value="5">注册界面</option>					  
                   </select>
                </div>
		        <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div>                                                        
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>图片名称</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="name"  name="name" placeholder="图片名称">
                </div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，汉字限定为8个字</span>
				</div>                
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">图片地址</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="link" name="link" placeholder="图片地址">
                </div>
		        <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，请输入合法的地址</span>
				</div>                                                        
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>排序</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="order" name="order">
                </div>
                <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，只能输入数字</span>
				</div>                                
              </div>
                  <div class="form-group">
                        <label for="inputName" class="col-sm-2 control-label">上传图片</label>
                        <div class="col-sm-5">
                            <input type="file" class="form-control" id='file' name="file" multiple="multiple" value="选择文件"  onchange="javascript:setImagePreview(this,localImag,preview);"/>
                        </div>
                  </div>
                  
                  <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10" id="outerDivId">
                    	<span class="help-block">图片尺寸大小必须为1920*390</span>   
                    </div>
                  </div>
                  
                  <div class="form-group" id="localImag" style="margin-left:220px;">
                    	<img src="" id='preview' onclick="over(preview,divImage,imgbig);" width="200" height="120">
                  </div>
                  
                  <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <a class="hm-link" href="#" data-url="" data-target="data">预览大图</a>
                    </div>
                  </div>              
              <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                  <div class="col-xs-3"><button type="button" class="btn btn-primary btn-block" id="addImageManage">添加</button></div>
                  <div class="col-xs-3"><button type="button" class="btn btn-default btn-block" id="cancelImageManage">取消</button></div>
                </div>
              </div>
            </form>     
        </div> 
        </div>
      </div>

<script type="text/javascript">
	function check(v){
	    var outerDiv= $("#outerDivId"); 
	    if(v==1){
	        outerDiv.html("<span class='help-block'>图片尺寸大小必须为1920*390</span>");
	    }else if(v==2 || v==3){
	        outerDiv.html("<span class='help-block'>图片尺寸大小必须为132*117</span>");        
	    }else if(v==4 || v==5){
	        outerDiv.html("<span class='help-block'>图片尺寸大小必须为440*250</span>");            
	    }	
	}
	 //检查图片的格式是否正确,同时实现预览
    function setImagePreview(obj, localImagId, imgObjPreview) {
        var array = new Array('jpeg', 'png', 'jpg'); //可以上传的文件类型        
    	var imgFileSize=Math.ceil(obj.files[0].size/1024*100)/100;//取得图片文件的大小 
    	if(imgFileSize>300){
    		$('#preview').attr({src:''}); 
            $('#logo').val(''); 
    		alert("请选择300K以下的图片！");
    		return false;
    	}
    	
        if (obj.value == '') {
            alert("请选择要上传的图片!");
            return false;
        } else {
            var fileContentType = obj.value.match(/^(.*)(\.)(.{1,8})$/)[3]; //这个文件类型正则很有用 
            //布尔型变量
            var isExists = false;
            //循环判断图片的格式是否正确
            for (var i in array) {
                if (fileContentType.toLowerCase() == array[i].toLowerCase()) {
                    //图片格式正确之后，根据浏览器的不同设置图片的大小
                    if (obj.files && obj.files[0]) {
                        //火狐下，直接设img属性 
                        imgObjPreview.style.display = 'block';
                        imgObjPreview.style.width = '180px';
                        imgObjPreview.style.height = '120px';
                        //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要以下方式 
                        imgObjPreview.src = window.URL.createObjectURL(obj.files[0]);
                    }else {
                        //IE下，使用滤镜 
                        obj.select();
                        var imgSrc = document.selection.createRange().text;
                        //必须设置初始大小 
                        localImagId.style.width = "180px";
                        localImagId.style.height = "120px";
                        //图片异常的捕捉，防止用户修改后缀来伪造图片 
                        try {
                            localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
                            localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader=").src = imgSrc;
                        }catch (e) {
                            alert("您上传的图片格式不正确，请重新选择!");
                            return false;
                        }
                        imgObjPreview.style.display = 'none';
                        document.selection.empty();
                    }
                    isExists = true;
                    return true;
                }
            }
            if (isExists == false) {
                $('#preview').attr({src:''}); 
                $('#logo').val(''); 
                alert("上传图片类型不正确!");
                return false;
            }
            return false;
        }
    }
    //显示图片  
    function over(imgid, obj, imgbig) {
        //大图显示的最大尺寸  4比3的大小  400 300  
        maxwidth = 180;
        maxheight = 120;

        //显示  
        obj.style.display = "";
        imgbig.src = imgid.src;

        //1、宽和高都超过了，看谁超过的多，谁超的多就将谁设置为最大值，其余策略按照2、3  
        //2、如果宽超过了并且高没有超，设置宽为最大值  
        //3、如果宽没超过并且高超过了，设置高为最大值  
		
        if (img.width > maxwidth && img.height > maxheight) {
            pare = (img.width - maxwidth) - (img.height - maxheight);
            if (pare >= 0)
                img.width = maxwidth;
            else
                img.height = maxheight;
        }
        else if (img.width > maxwidth && img.height <= maxheight) {
            img.width = maxwidth;
        }
        else if (img.width <= maxwidth && img.height > maxheight) {
            img.height = maxheight;
        }
    }

jQuery(function($) {
    $('#addImageManage').on('click', function(e) { 
        upload($(this).get(0).files); 
    });
	// 异步上传
	function upload(files) {
	    var files = $('input[name="file"]').prop('files');
		$.each(files, function(i, file) {
		    var reader = new FileReader(), xhr = new XMLHttpRequest(), formData = new FormData();
		    var type = $('#type').val(),name = $('#name').val(),link = $('#link').val(),order = $('#order').val(),image = $('#file').val();
		    reader.readAsDataURL(file);
			formData.append('type', type);
			formData.append('name', name);
			formData.append('link', link);
			formData.append('order', order);		    
			formData.append('file', file);
			xhr.open('POST', '${app}/content/handerAddImageManage');
			xhr.send(formData);
		});
	}
    //点击取消按钮
	$("#cancelImageManage").on("click",function(){
		$.link.html(null, {
			url: '${app}/content/imageIndex',
			target: 'main'
		});
	});
});
</script>
