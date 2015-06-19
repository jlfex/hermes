<div class="panel panel-primary">
	<div class="panel-heading">设置权限</div>
	<div class="panel-body">
<div id="iTree" class="tree well">
	<ul>
		<li>
			<span><i class='icon-folder-open'></i>全部</span>
			<ul id='root'></ul>
		</li>
	</ul>
</div>
<div class="row hm-row form-group">
<div class="col-xs-offset-1 col-xs-3">
	<button id="pushIn" type="button" class="btn btn-promary">提交</button>		
	<button id="retreat" type="button" class="btn btn-promary">取消</button>				     
</div>
<input id="roleId" type="hidden" value="${role.id}">				
</div>
</div>
</div>

<script type="text/javascript">
$(function(){
		$.ajax({
			url:"${app}/privilege/getPrivileges/"+$("#roleId").val(),
			type:"POST",
			dataType:'json',
			success:function(data) {
				var idata = eval(data.children);
				<!-- var showlist = $("<ul><li><span><i class='icon-folder-open'></i> Parent</span><ul id='root'></ul></li></ul>"); -->
				
				showall(idata,$("#root"));
				
				
				$('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', 'Collapse this branch');
			    $('.tree li.parent_li > span').on('click', function (e) {
			        var children = $(this).parent('li.parent_li').find(' > ul > li');
			        if (children.is(":visible")) {
			            children.hide('fast');
			            $(this).attr('title', 'Expand this branch').find(' > i').addClass('icon-plus-sign').removeClass('icon-minus-sign');
			        } else {
			            children.show('fast');
			            $(this).attr('title', 'Collapse this branch').find(' > i').addClass('icon-minus-sign').removeClass('icon-plus-sign');
			        }
			        e.stopPropagation();
    			});
    			
    			 $('input').iCheck({ checkboxClass: 'icheckbox_square-blue', radioClass: 'iradio_square-blue', increaseArea: '20%' });
    			 
    			 $('input').on('ifClicked', function(event){
    			 	var obj = $(this);
    			 	var flag = obj.is(":checked")
   					setCb(obj,!flag);		 	
   			
   				
   					setcbUp(obj,!flag);
				});
			},
			error:function() {
				
			}
		});
		
		$("#pushIn").on("click",function() {
			var privileges = "";
			var cbs = $("input");
			$.each(cbs,function(n,value) {
				if($(value).is(":checked")) {
					privileges += ($(value).attr("id"))+","; 
				}
			});
			
			var roleId = $("#roleId").val();
			
			$.ajax({
				url:"${app}/privilege/setPrivilege",
				type:"POST",
				dataType:'json',
				data:{"privileges":privileges,"roleId":roleId},  
				success:function(data) {
						if(data.type == "SUCCESS") {
     						$.alert({
							    title: '结果',
							    content: data.messages[0],
							    icon: 'glyphicon glyphicon-heart',
							    confirm: function(){
							     		$.link.html(null, {
											url: '${app}/privilege/roleIndex',
											target: 'main'
										});
							    }
							});
     					} else {
     						$.alert({
							    title: '结果',
							    icon: 'fa fa-warning',
							    content: data.message
							});
     					}
				},
				error:function() {
					flag = false;
				}
			});
			
		});
});

function setcbUp(cb,flag) {
	var p = $(cb.parent().parent().parent().parent().parent().children().eq(0).children().eq(0).children().eq(0));
	if(flag) {
		$(cb).iCheck('check');	
	} else {
		var slibings = $(cb).parent().parent().parent().parent().children();
		if(slibings.length > 1) {
			var f = true;
			$.each(slibings,function(n,value) {
			  var con1 = $(value).children().eq(0).children().eq(0).children().eq(0).is(":checked");
			  if(con1) {
			  	f = false;
			  }
			});
			
			if(!f) {
				$(p).iCheck('check');	
			} else {
				$(p).iCheck('uncheck');
			}
		} else {
			$(p).iCheck('uncheck');
		}
	}

	if(p.length >0) {
		setcbUp(p,flag);
	}  else {
		return;
	}
}

function setCb(cb,flag) {
	var obj = $(cb);
	var child = obj.parent().parent().parent().children().eq(1).children();
	if(child.length > 0) {
		 $.each(child,function(n,value) {
		 	var cb1 = $(value).eq(0).children().eq(0).children().eq(0).children().eq(0);
		 	if(flag) {
		 		$(cb1).iCheck('check');
		 	} else {
		 		$(cb1).iCheck('uncheck');
		 	}
		 
   			setCb(cb1,flag);
   		 });  
	} else {
		if(flag) {
			$(cb).iCheck('check');
		} else {
			$(cb).iCheck('uncheck');
		}
	
	}			 	
}

function showall(menu_list, parent) {
	for (var menu in menu_list) {
			if(menu_list[menu].typeCode == "console") {
				//如果有子节点，则遍历该子节点
				if (menu_list[menu].children && menu_list[menu].children.length > 0) {
					if(menu_list[menu].havingBySoftModel) {
						//创建一个子节点li
						var li = $("<li></li>");
						//将li的文本设置好，并马上添加一个空白的ul子节点，并且将这个li添加到父亲节点中
						if(menu_list[menu].havingByRole) {
							$(li).append("<span><input type='checkbox' checked='checked' id='"+menu_list[menu].id+"'  />&nbsp;&nbsp;<i class='icon-minus-sign'></i>"+menu_list[menu].name+"</span>").appendTo(parent);	
						} else {
							$(li).append("<span><input type='checkbox' id='"+menu_list[menu].id+"'  />&nbsp;&nbsp;<i class='icon-minus-sign'></i>"+menu_list[menu].name+"</span>").appendTo(parent);
						}
						$(li).append("<ul></ul>").appendTo(parent);
					 }
					
					
					//将空白的ul作为下一个递归遍历的父亲节点传入
					showall(menu_list[menu].children, $(li).children().eq(1));
				}
				//如果该节点没有子节点，则直接将该节点li以及文本创建好直接添加到父亲节点中
				else {
					if(menu_list[menu].havingBySoftModel) {
						if(menu_list[menu].havingByRole) {
							$("<li><span><input type='checkbox' checked='checked' id='"+menu_list[menu].id+"' />&nbsp;&nbsp;<i class='icon-leaf'></i>"+menu_list[menu].name+"</span></li>").appendTo(parent);
						} else {
							$("<li><span><input type='checkbox' id='"+menu_list[menu].id+"' />&nbsp;&nbsp;<i class='icon-leaf'></i>"+menu_list[menu].name+"</span></li>").appendTo(parent);
						}
					}
				}
			}
	}
}

$("#retreat").on("click",function(){
		$.link.html(null, {
			url: '${app}/privilege/roleIndex',
			target: 'main'
		});
	});	
</script>
