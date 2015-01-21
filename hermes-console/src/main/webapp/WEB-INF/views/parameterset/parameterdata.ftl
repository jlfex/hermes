<table id="table" class="table table-striped table-hover">
	<thead>
		<tr>
				<th width="10%" class="align-center">参数类</th>
				<th width="10%" class="align-center">参数值</th>
				<th width="10%" class="align-center">参数状态</th>
				<th width="10%" class="align-center">操作</th>
		</tr>
	</thead>
	<#if parameterSet.numberOfElements == 0>
		<tr>
			<td colspan="6" class="align-center"><@messages key="common.table.empty" /></td>
		</tr>
		<#else>
		<#list parameterSet.content as p>
		<tr>
			<td class="align-center">${p.parameterType}</td>
			<td class="align-center">${p.parameterValue}</td>
			<#if p.status == "00" >
			  <td class="align-center">启用</td>
			  
			<#else>
			  <td class="align-center">禁用</td>
			</#if>
			<td class="align-center">
				<a href="javascript:void(0)"  onclick="update('${p.id}')">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<#if p.status == "00">
				  <a href="javascript:void(0)" onclick="change('${p.id}','禁用')">禁用</a>
				<#else>
				  <a href="javascript:void(0)" onclick="change('${p.id}','启用')">启用</a>
				  </#if>
			</td>			
		</tr>
		</#list>
		</#if>
	<tbody>
</table>

<ul class="pagination" data-number="${parameterSet.number}" data-total-pages="${parameterSet.totalPages}"></ul>
                          <#-----------------------修改参数配置------------------------>
<div id="updateDialog" title="修改参数配置" style="display:none;">
			<form id="updateForm"   method="post" action="${app}/parameter/updateDictionary">
			<input type="hidden" class="dicId" name="id" />
			<input type="hidden" class="parameterType" name="parameterType" />
			<table align="center">
				<tr>
					<td align="right">参数类：</td>
					<td>
						<input type="text"  class="textbox type" name="parameterType" disabled/>
					</td>
				</tr>
				<tr>
					<td align="right">参数值：</td>
					<td><input type="text" class="textbox pv" name="parameterValue" /></td>
				</tr>
				
			</table>
		</form>

</div>
                               <#-----------------------弹框------------------------>
<div id="operateTip2" title="操作信息" style="display:none;">修改成功</div>
<div id="errorTip2" title="错误提示" style="display:none;"></div>
<div id="tip3" title="操作参数" style="display:none;"></div>

<script type="text/javascript">

function change(id,status){  
     $("#tip3").html("您确定要"+status+"该参数值吗？");
      var checkParams = {"id" : id};
			$.post("${app}/parameter/switch", checkParams,
					function(data) {
					     if(data.code==0){							     			                                    
                               $("#tip3").dialog({
				                        height:200, 
					                    width:300,
					                     buttons:{ 
					                      "确定":function(){
					                              
                                                  $(this).dialog("close");
						                          $.link.html(null,{
														url:'${app}/parameter/index',
														data:'',
														target:'main'
													});			
					                         },
					                       "关闭": function() {  
                                                $(this).dialog("close");	  
                                              } } 					                         					                     
					                      })
					       
					      }	else {
					        $("#tip3").html( data.attachment);			                                    
                               $("#tip3").dialog({
				                        height:200, 
					                    width:300,
					                     buttons:{ 
					                      "确定":function(){
					                          $(this).dialog("close");
						                          $.link.html(null,{
														url:'${app}/parameter/index',
														data:'',
														target:'main'
													});			
					                         },
					                       "关闭": function() {  
                                                $(this).dialog("close");
                                                $.link.html(null,{
														url:'${app}/parameter/index',
														data:'',
														target:'main'
													});	  
                                              } } 					                         					                     
					                      })
                         }
                })
}


function update(id){
		  var checkParams = {"id" : id};
			$.post("${app}/parameter/update", checkParams,
					function(data) {
					    if(data.code==0){
					       $(".type").empty();
					       $(".type").val(data.attachment.type.name);
					       $(".dicId").val(data.attachment.id);
					       $(".parameterType").val(data.attachment.type.id);
					       $(".pv").val(data.attachment.name);
					      }	
		                   $("#updateDialog").dialog({
		                        height:250, 
			                    width:500,
			                     buttons:{  
					                "确定":function(){  
					                    var form = $("#updateForm");  
					                    $.ajax({  
					                        url:form.attr('action'),  
					                        type:form.attr('method'),  
					                        data:form.serialize(),  
					                        dataType:"json",  
					                        success:function(data){  
					                                 if(data.code==0){					                                    
					                                       $("#operateTip2").dialog({
											                        height:150, 
												                    width:300,
												                     buttons:{ 
												                      "关闭":function(){
												                          $(this).dialog("close");
						                         	                      $(this).parent().prev().find("#updateDialog").dialog("close");
												                          
													                          $.link.html(null,{
																					url:'${app}/parameter/index',
																					data:'',
																					target:'main'
																				});			
												                         }}
												                     
												                      })
					                                 }else{
					                                   $("#errorTip2").html( data.attachment);
					                                      $("#errorTip2").dialog({
											                        height:150, 
												                    width:300,
												                     buttons:{ 
												                      "关闭":function(){
												                          $(this).dialog("close");
						                         	                      $(this).parent().prev().find("#updateDialog").dialog("close");												                          											                          												                          
												                         }
												                      }											                     
												           })
					                                                                     
					                       } }, 
					                        error:function(){  
					                            
					                             $(this).dialog("close");
						                         $(this).parent().prev().find("#updateDialog").dialog("close");					                                                         
					                         }  
					                    })  
					                },  
					                "关闭": function() {  
					                    
					                    $(this).dialog("close");
						                $(this).parent().prev().find("#updateDialog").dialog("close");					                                                         					                      
					                }  
                     }  
		  }); 
									
	  });
}

jQuery(function($) {
	$('#table a').link();
	$('.pagination').pagination({
		handler: function(elem) {
			$('#page').val(elem.data().page);
			$('#searchForm').trigger('submit');
		}
	});
});
 
</script>
