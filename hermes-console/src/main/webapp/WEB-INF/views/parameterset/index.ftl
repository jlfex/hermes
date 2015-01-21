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
				<tr style="height:45px">
					<td align="right">参数类：</td>
					<td><select class="textbox selector" name="parameterType" id="parameterType1" style="width:130px">
                        <option value="176c9150-7103-11e3-ae10-6cae8b21aeab">产品担保方式</option>
						<option value="176c9150-7103-11e3-ae10-6cae8b21aeac">产品用途</option>
                        <option value="176c9150-7103-11e3-ae10-6cae8b21aead" selected="selected">产品招标期限</option>
						</select>
					</td>
				</tr>
				<tr style="height:45px">
					<td align="right">参数值：</td>
					<td><input type="text" class="textbox pav" name="parameterValue" id ="parameterValue1" style="width:130px" /></td>
					<td class="alert-danger" style="display:none;background:none;width:200px;">只能输入1~100的数字</td>				                               					
				</tr>
				<tr style="height:45px">
					<td align="right">状态：</td>
					<td><select class="textbox" name="status" style="width:130px">
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
	//如果参数类选择产品招标期限，在此加入校验
	$('#parameterValue1').keyup(function(){
	   var parameterType  = $("#parameterType1").val();
	   var parameterValue = $("#parameterValue1").val(); 
	   if(parameterType == '176c9150-7103-11e3-ae10-6cae8b21aead' && !/^([0-9]{1,2}|100)$/.test(parameterValue)){
		   $(this).parent().parent().find(".alert-danger:eq(0)").show();    
	   }else{
	       $(this).parent().parent().find(".alert-danger:eq(0)").hide();
	   }
	});
	$("#addBtn").click(function(){
	$(".pav").val("");
		$("#addDialog").dialog({ 
			height:250, 
			width:600, 
               buttons:{  
                "确定":function(){  
                    var form = $(this).find("#addForm");
                    
                    $.ajax({  
                        url:form.attr('action'),  
                        type:form.attr('method'),  
                        data:form.serialize(),  
                        dataType:"json",  
                        success:function(data){ 
                             if(data.code==0){					                                    
                                   $("#operateTip").dialog({
					                        height:150, 
						                    width:300,
						                     buttons:{ 
						                      "关闭":function(){
						                          	$(this).dialog("close");
						                         	$(this).parent().prev().find("#addDialog").dialog("close");
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
						                        height:150, 
							                    width:300,
							                     buttons:{ 
							                      "关闭":function(){
							                       $(this).dialog("close");
						                           $(this).parent().prev().find("#addDialog").dialog("close");     		
							                         }
							                        }							                     
							           })
	                             }},  
                        error:function(){  
                            $(this).dialog("close");
						    $(this).parent().prev().find("#addDialog").dialog("close");                            
                         }  
                    })  
                },  
                "关闭": function() {
                	$(this).dialog("close");
					$(this).parent().prev().find("#addDialog").dialog("close");
                }  
           }  
     });
  });
});


</script>



