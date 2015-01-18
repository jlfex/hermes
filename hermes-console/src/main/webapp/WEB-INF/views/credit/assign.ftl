
<div class="panel panel-primary">
        <div class="panel-heading">外部债权导入</div>  </div>
    	<div class="panel-body">
            <form id="searchForm" method="post" action="#">
                <div class="row">
                    <div class="col-xs-2 hm-col form-group">
                        <label for="account">债权来源</label>
                        <select id="status" name="status" class="form-control">
                            <option value="">A机构</option>
                            <option value="00">B级构</option>
                            <option value="10">C级构</option>
                        </select>
                    </div>
                    <div class="col-xs-2 hm-col form-group">
                        <label for="cellphone">债权编号</label>
                        <input id="cellphone" name="cellphone" value="" class="form-control" type="text">
                    </div>
                    <div class="col-xs-2 hm-col form-group">
                        <label for="realname">借款类型</label>
                        <select id="status" name="status" class="form-control">
                            <option value="">学生贷</option>
                            <option value="00">房贷</option>
                            <option value="10">车贷</option>
                        </select>
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
                            <input readonly="" id="beginDate" name="beginDate" value="2015-01-06" class="form-control hasDatepicker" type="text">
                        </div>
                        <div class="col-xs-2 hm-col form-group">
                            <label for="endDate">&nbsp;</label>
                            <input readonly="" id="endDate" name="endDate" value="2015-01-06" class="form-control hasDatepicker" type="text">
                        </div>
                        <div class="col-xs-2 hm-col form-group">
                            <label for="realname">借款用途</label>
                            <select id="status" name="status" class="form-control">
                                <option value="">买方</option>
                                <option value="00">买车</option>
                                <option value="10">日常消费</option>
                            </select>
                        </div>
                         <div class="col-xs-1 hm-col form-group">
                            <label>&nbsp;</label>
                            <input id="file" name="file" type="file" multiple="multiple" class="hidden" />
                            <button id="uploadBtn" type="button" class="btn  btn-default btn-block">+ 导入新债权</button>&nbsp;
                        </div>
                        <div class="col-xs-2 hm-col form-group">
                            <label>&nbsp;&nbsp;&nbsp;&nbsp;</label>
                            <div id="err_file_kind"></div>
                        </div>
                </div>
            </form>
        </div>
        <div id="importResult"></div>
        <div id="data" style="display:block">
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th class="align-left">债权来源</th>
                        <th class="align-center">第三方编号</th>
                        <th class="align-center">债权编号</th>
                        <th class="align-center">借款人</th>
                        <th class="align-right">证件号码</th>
                        <th class="align-right">金额</th>
                        <th class="align-center">年利率</th>
                        <th class="align-center">期限</th>
                        <th class="align-center">导入日期</th>
                        <th class="align-center">借款类型</th>
                        <th class="align-center">借款用途</th>
                        <th class="align-center">操作</th>
                    </tr>
                </thead>
                <tbody>
                   <#if infoList??>
                   <#if infoList.numberOfElements == 0>
					<tr>
						<td colspan="6" class="align-center"><@messages key="common.table.empty" /></td>
					</tr>
					<#else>
						<#list infoList.content as l>
							<tr>
							    <td class="align-left">${(l.creditor.source)!''}</td>
								<td class="align-center">${(l.creditor.creditorNo)!''}</td>
								<td class="align-center">${l.crediteCode!''}</td>
								<td class="align-center">${l.borrower!''}</td>
								<td class="align-right">${l.certificateNo!''}</td>
								<td class="align-right">${l.amount!''}</td>
								<td class="align-center">${l.rate!''}</td>
								<td class="align-center">${l.period!''}</td>
								<td class="align-center">${l.createTime!''}</td>
								<td class="align-center">${l.crediteType!''}</td>
								<td class="align-center">${l.purpose!''}</td>
								<td class="align-center">
									<a href="#" data-url="${app}/credit/repayPlanDetail/${l.id}" >查看</a>
								</td>
							</tr>
						</#list>
					</#if>
					<#else>
					    <tr>
						<td colspan="6" class="align-center"><@messages key="common.table.empty" /></td>
					</tr>
					</#if>
                </tbody>
        </table>
            <div class="pull-right">
                <ul class="pagination" data-number="0" data-total-pages="4"><li class="active"><a href="#">1</a></li><li><a href="#" data-page="1">2</a></li><li><a href="#" data-page="2">3</a></li><li><a href="#" data-page="3">4</a></li></ul>
            </div>
        </div>
  
    
<script type="text/javascript" charset="utf-8">
<!--
var labelId;
jQuery(function($) {
	// 点击上传处理
	$('#uploadBtn').on('click', function(e) { $('#file').click();});
	$('#file').on('change', function(e) {
	var name=$(this).get(0).files[0].name;
	var imgExt = name.substring(name.lastIndexOf(".")); 
	if(!(imgExt=='.xlsx'||imgExt=='.xls')){
	   $("#err_file_kind").html("文件类型有误,请选择模板文件");
	   return false;
	}
	upload($(this).get(0).files);
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
		formData.append('file', file);
		xhr.open('POST', '${app}/credit/import');
	    xhr.onreadystatechange = function(){
	       if(xhr.readyState == 4) {
	          var data = jQuery.parseJSON(xhr.responseText);
              if(xhr.status == 200 ) {
                   if(data.code == "00"){
                        $("#importResult").html(xhr.responseText);
                   }else{
                        $("#importResult").html(xhr.responseText);
                   }
              }else{
                   $("#importResult").html("服务器响应异常,请重试。");
              }     
           }   
	    }; 
		xhr.send(formData);
	});
}
function processResponse(){
	   $.link.html(null, {
			url: '${app}/credit/assign',
			target: 'main'
		});
}
$("table a").on("click",function(){
		$.link.html(null, {
			url: $(this).attr("data-url"),
			target: 'main'
		});
});
	

//-->
</script>