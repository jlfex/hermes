<div class="panel panel-primary">
        <div class="panel-heading">外部债权导入</div>  </div>
    	<div class="panel-body">
            <form id="searchForm" method="post" action="#">
                <div class="row">
                    <div class="col-xs-2 hm-col form-group">
                        <label for="account">债权人名称</label>
                        <input id="creditorName" name="creditorName" value="" class="form-control" type="text">
                    </div>
                    <div class="col-xs-2 hm-col form-group">
                        <label for="cellphone">债权编号</label>
                        <input id="crediteCode" name="crediteCode" value="" class="form-control" type="text">
                    </div>
                    <div class="col-xs-2 hm-col form-group">
                         <label for="cellphone">借款用途</label>
                        <input id="purpose" name="purpose" value="" class="form-control" type="text">
                    </div>
                    
                    <div class="col-xs-1 hm-col form-group">
                        <label>&nbsp;</label>
                        <button id="searchBtn" type="button" class="btn btn-primary btn-block">查询</button>
                        <input id="page" name="page" value="0" type="hidden">
                    </div>
    		
                </div>
                <div class="row">
                        <div class="col-xs-2 hm-col form-group">
                            <label for="beginDate">导入日期</label>
                            <input readonly="" id="beginDate" name="beginDate" class="form-control" type="text">
                        </div>
                        <div class="col-xs-2 hm-col form-group">
                            <label for="endDate">&nbsp;</label>
                               <input readonly="" id="endDate" name="endDate"  class="form-control" type="text">
                        </div>
                        <div class="col-xs-1 hm-col form-group">
                            <label>&nbsp;&nbsp;&nbsp;&nbsp;</label>
                           <button id="downloadModelFile" type="button" class="btn  btn-default btn-block">
                             	模板下载
                           </button>&nbsp;
                        </div>
                        <div class="col-xs-1 hm-col form-group">
                            <label>&nbsp;</label>
                            <input id="file" name="file" type="file" multiple="multiple" class="hidden" />
                            <button id="uploadBtn" type="button" class="btn  btn-default btn-block">+ 导入新债权</button>&nbsp;
                        </div>
                </div>
            </form>
        </div>
<div id="importResult"></div>
<div id="data"></div>   
<script type="text/javascript" charset="utf-8">
<!--
var labelId;
jQuery(function($) {
    $("#beginDate").datepicker();  
	$("#endDate").datepicker();
    //$('#table a').link();
	// 点击上传处理
	$('#uploadBtn').on('click', function(e) { $('#file').click(); });
	$('#file').on('change', function(e) {  
	var name=$(this).get(0).files[0].name;
	var imgExt = name.substring(name.lastIndexOf(".")); 
	if(!(imgExt=='.xlsx'||imgExt=='.xls')){
	   $("#importResult").html("文件类型有误,请下载模板文件");
	   return false;
	}
	upload($(this).get(0).files);
	$('#file').val("");
	});
	
	$("#downloadModelFile").click(function(){
		var form=$("<form>");
		form.attr("style","display:none");
		form.attr("target","");
		form.attr("method","post");
		form.attr("action","${app}/credit/exportData");
		var input1=$("<input>");
		input1.attr("type","hidden");
		input1.attr("name","exportData");
		input1.attr("value",(new Date()).getMilliseconds());
		$("body").append(form);
		form.append(input1);
		form.submit();
	});
	
	$.page.withdraw({
	search: '${app}/credit/loandata'
	});
	
	
});
// 异步上传
function upload(files) { 
	$.each(files, function(i, file) {
		// 初始化
		var reader = new FileReader(), xhr = new XMLHttpRequest(), formData = new FormData();
		//var entry = $('#addImage'+labelId);
		// 定义加载事件
		//reader.onload = function(e) { $(['<div class="col-sm-6 col-md-3"><div class="thumbnail"><img alt="', file.name, '" src="', e.target.result, '" /></div></div>'].join('')).insertBefore(entry);};
		reader.readAsDataURL(file);
		// 异步上传
		formData.append('file', file);
		xhr.open('POST', '${app}/credit/import');
	    xhr.onreadystatechange = function(){
	       if(xhr.readyState == 4) {
              if(xhr.status == 200 ) {
                  var data = jQuery.parseJSON(xhr.responseText);
                   var _content =   "<div class=\"form-group\"><div class=\"col-xs-10\">"; 
                   if(data.code == "00"){
	                   _content = _content +"文件："+data.fileName+ "，导入成功! </p>";
	                   _content = _content + "<p>债权列表：共<span class=\"text-primary\">"+data.sheet1AllNum+"</span>条记录，导入成功<span class=\"text-primary\">"+data.sheet1SucNum+"</span>条，导入失败<span class=\"color_red\">"+data.sheet1ErrNum+"</span>条。</p>";
	                   _content = _content + "<p> 还款计划表：共<span class=\"text-primary\">"+data.sheet2AllNum+"</span>条记录，导入成功<span class=\"text-primary\">"+data.sheet2SucNum+"</span>条，导入失败<span class=\"color_red\">"+data.sheet2ErrNum+"</span>条。</p>";
                   }else{
                       var err_msg = "文件导入失败。";
                       _content = _content +"文件："+data.fileName+ "，导入失败! </p>";
                       if(typeof(data.msg) != "undefined"){
                            err_msg = data.msg;
                       }
                       _content = _content +  "<p>"+err_msg+"</p>";
                   }
                   _content = _content +  "<p>"+data.checkErrorMsg+"</p>";
                   _content = _content + "</div></div>";
                   $("#creditInfoIds").val(data.creditInfoIds);
                   $("#importResult").html(_content);
                   $.page.withdraw({
	                 search: '${app}/credit/loandata'
	               });
              }else{
                   $("#importResult").html("服务器响应异常,请重试。");
              }     
           }   
	    }; 
		xhr.send(formData);
	});
}
 


//-->
</script>

