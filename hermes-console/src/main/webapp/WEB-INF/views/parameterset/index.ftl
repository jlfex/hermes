                           <#-----------------------查询及新增------------------------>
<div class="panel panel-primary">
	<div class="panel-heading">参数设置</div>
	<div class="panel-body">
		<form id="searchForm" method="post" action="#">
			<div class="row hm-row">
				<div class="col-xs-2 hm-col form-group">
					<label for="parameterType">参数类</label>
					<input id="parameterType" name="parameterType" type="text" class="form-control">
				</div>
				<div class="col-xs-2 hm-col form-group">
					<label for="parameterValue">参数值</label>
					<input id="parameterValue" name="parameterValue" type="text" class="form-control">
				</div>
				<div class="col-xs-1 hm-col form-group">
					<label>&nbsp;</label>
					<button id="searchBtn" type="button" class="btn btn-primary btn-block"><@messages key="common.op.search" /></button>
				</div>
				<div class="col-xs-1 hm-col form-group">
					<label>&nbsp;</label>
					<button id="addBtn" type="button" class="btn btn-primary btn-block">新增</button>
					<input id="page" name="page" type="hidden" value="0">
				</div>				
			</div>
		</form>
	</div>
</div>

<div id="data"></div>
                                  <#-----------------------新增参数配置------------------------>
<div id="addDialog" title="新增参数配置" style="display:none;">
			<form id="addForm"   method="post" action="${app}/parameter/addDictionary">
			<table align="center">
				<tr>
					<td align="right">参数类：</td>
					<td><select class="textbox" name="parameterType">
						<#list names as s>
						<option value="${s.key}">${s.value}</option>
						</#list>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">参数值：</td>
					<td><input type="text" class="textbox pav" name="parameterValue" /></td>
				</tr>
				<tr>
					<td align="right">状态：</td>
					<td><select class="textbox" name="status">
							<option value="00">启用</option>
							<option value="09">禁用</option>
						</select>
					</td>
				</tr>
			</table>
		</form>

</div>
                                <#-----------------------弹出窗口------------------------>
<div id="operateTip" title="操作信息" style="display:none;">保存成功</div>
<div id="errorTip" title="错误提示" style="display:none;"></div>

<script type="text/javascript">

jQuery(function($) {
		$.page.withdraw({
		search: '${app}/parameter/parameterdata'
	});
	$("#addBtn").click(function(){
	$(".pav").val("");
		$("#addDialog").dialog({ 
			height:380, 
			width:500, 
               buttons:{  
                "确定":function(){  
                    var form = $("#addForm");  
                    $.ajax({  
                        url:form.attr('action'),  
                        type:form.attr('method'),  
                        data:form.serialize(),  
                        dataType:"json",  
                        success:function(data){ 
                             if(data.code==0){					                                    
                                   $("#operateTip").dialog({
					                        height:200, 
						                    width:300,
						                     buttons:{ 
						                      "关闭":function(){
						                          $("#operateTip").dialog("close");
						                          $("#addDialog").dialog("close"); 
							                          $.link.html(null,{
															url:'${app}/parameter/index',
															data:'',
															target:'main'
														});			
						                         }}
						                     
						                      })
	                             }else{
	                               $("#errorTip").html( data.attachment);
	                                  $("#errorTip").dialog({
						                        height:200, 
							                    width:300,
							                     buttons:{ 
							                      "关闭":function(){
							                          $(this).dialog("close");
							                          $("#addDialog").dialog("close"); 
								                         		
							                         }}
							                     
							                      })
	                             }   
	                                                                 
	                    },  
                        error:function(){  
                            $("#addDialog").dialog("close");                              
                         }  
                    })  
                },  
                "关闭": function() {  
                    $("#addDialog").dialog("close");  
                }  
           }  
     });
  });
});


</script>



