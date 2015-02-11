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
              <input type="hidden" value="${(imageManage.id)!}" name="id" id="imageManageId"/>          
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>所属分类</label>
                <div class="col-sm-5">
                   <select id="type" name="type" class="form-control" onchange=check(this.value)>
                   	  <option value="首页banner广告">首页banner广告</option>
					  <option value="首页—我要理财">首页—我要理财</option>
					  <option value="首页—我要借款">首页—我要借款</option>
					  <option value="登录界面">登录界面</option>
					  <option value="注册界面">注册界面</option>				  
                   </select>
                </div>
		        <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div>                                                        
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>图片名称</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="name"  name="name" value="${imageManage.name}">
                </div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，汉字限定为8个字</span>
				</div>                
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">图片地址</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="link" name="link"  value="${imageManage.link}">
                </div>
		        <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，请输入合法的地址</span>
				</div>                                                        
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>排序</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="order" name="order" value="${imageManage.order}">
                </div>
                <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，只能输入数字</span>
				</div>                                
              </div>
                  <div class="form-group">
                        <label for="inputName" class="col-sm-2 control-label"><span style="color:red;">* </span>上传图片</label>
                        <div class="col-sm-5">
                            <input type="file" class="form-control" id='file' name="file" multiple="multiple" value="选择文件" onchange="javascript:setImagePreview(this,localImag,preview);"/>
                        </div>
                        <div class="col-xs-2">
					         <span class="alert-danger" style="display:none;background:none">请选择要上传的图片</span>
				        </div>                                                                              
                  </div>
                  
                  <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10" id="outerDivId">
                    	<span class="help-block">图片尺寸大小必须为1920*390</span>   
                    </div>
                  </div>
                  
                  <div class="form-group" id="localImag" style="margin-left:220px;">
                    	<img src="${imageManage.image}" id='preview' onclick="over(preview,divImage,imgbig);" width="200" height="120">
                  </div>
                  <!--
                  <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <a class="hm-link" href="#" data-url="" data-target="data">预览大图</a>
                    </div>
                  </div>     -->         
              <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                  <div class="col-xs-3"><button type="button" class="btn btn-primary btn-block" id="updateImageManage">保存</button></div>
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
	    if(v=='首页banner广告'){
	        outerDiv.html("<span class='help-block'>图片尺寸大小必须为1920*390</span>");
	    }else if(v=='首页—我要理财' || v=='首页—我要借款'){
	        outerDiv.html("<span class='help-block'>图片尺寸大小必须为132*117</span>");        
	    }else if(v=='登录界面' || v=='注册界面'){
	        outerDiv.html("<span class='help-block'>图片尺寸大小必须为440*250</span>");            
	    }	
	}
	//检查图片的格式是否正确,同时实现预览
    function setImagePreview(obj, localImagId, imgObjPreview) {
  
        var array = new Array('jpeg', 'png', 'jpg'); //可以上传的文件类型         
        var imgFileSize=Math.ceil(obj.files[0].size/1024*100)/100;//取得图片文件的大小    
    	if(imgFileSize>300){
    		$('#preview').attr({src:''}); 
            $('#file').val(''); 
    		alert("请选择300K以下的图片！");
    		return false;
    	}  else {
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
                $('#file').val(''); 
                alert("上传图片类型不正确，仅支持jpeg、png、jpg格式的图片!");
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
    
    // 异步上传
    var xhr;
	function upload(files) {
	 	xhr = new XMLHttpRequest();
	 	xhr.onreadystatechange = zswFun;//设置回调函数		 	
	    var files = $('input[name="file"]').prop('files');
	    var reader = new FileReader(), formData = new FormData();	
		$.each(files, function(i, file) {
		    var id = $('#imageManageId').val(),type = $('#type').val(),name = $('#name').val(),link = $('#link').val(),order = $('#order').val(),image = $('#file').val();
		    reader.readAsDataURL(file);
		    formData.append('id', id);		    
			formData.append('type', type);
			formData.append('name', name);
			formData.append('link', link);
			formData.append('order', order);		    
			formData.append('file', file);			
		});
		xhr.open('POST', '${app}/content/handerEditImageManage');
		xhr.send(formData);
	 }
		//回调函数    
    function zswFun(){   
	    if(xhr.readyState == 4 && xhr.status == 200){    
	        var b = xhr.responseText;
	        var data = $.parseJSON(b);  
	        if(data.code == '0'){
	            alert(data.attachment);
		        $.link.html(null, {
				   url: '${app}/content/imageIndex',
				   target: 'main'			
		        });
	        }else if(data.code == '1'){
	            alert(data.attachment);
                $.link.html(null, {
			       url: '${app}/content/editImageManage',
			       target: 'main'	       
	            });
	        } 	         
	     }    
     }  
		
</script>
<script type="text/javascript">    
jQuery(function($) {
	$("#order,#name").on('blur',function(i,item){
		checkInput(this);
	});
	//对输入元素进行校验
	function checkInput(e){
		var $this = $(e);
		var val = $this.val();
		if($this.val()==''||(e.id == 'order' && !/^[0-9]+$/.test(val))		    
		    ){
			$this.parent().parent().find(".alert-danger:eq(0)").attr("e_id",e.id);
			$this.parent().parent().find(".alert-danger:eq(0)").show();
			return false;
		}else{
			var e_id = $this.parent().parent().find(".alert-danger:eq(0)").attr("e_id");
			if(e_id=='' || e_id==e.id){
				$this.parent().parent().find(".alert-danger:eq(0)").hide();
			}
			return true;
		}
	}
	//元素失去焦点时，触发数据校验事件
	function checkAll(){
		$("#order,#name").each(function(i,item){
			checkInput(this);
		});
		return $("span.alert-danger:visible").length==0;
	}
    $('#updateImageManage').on('click', function(e) {
       if(checkAll()){ 
         upload($(this).get(0).files); 
       };
    });	
    //点击取消按钮
	$("#cancelImageManage").on("click",function(){
		$.link.html(null, {
			url: '${app}/content/imageIndex',
			target: 'main'
		});
	});
	
	$("#type").val("${(imageManage.type)!}");
});

</script>

