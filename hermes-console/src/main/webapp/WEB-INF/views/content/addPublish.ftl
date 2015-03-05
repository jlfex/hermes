<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>发布内容</title>
<link rel="stylesheet" type="text/css" href="${app.css}/css/bootstrap-combined.no-icons.min.css" />
<link rel="stylesheet" type="text/css" href="${app.css}/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" type="text/css" href="${app.css}/css/font-awesome.css" />
<link rel="stylesheet" type="text/css" href="${app.css}/css/index.css" />
<script type="text/javascript" src="${app.js}/jquery.hotkeys.js" charset="utf-8"></script>
<script type="text/javascript" src="${app.js}/bootstrap-wysiwyg.js" charset="utf-8"></script>
</head>
<body>

 <#if msg??>
	<div class="alert alert-danger alert-dismissible fade in" role="alert">
		<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
		${msg!}
	</div>
</#if>
 <div class="panel panel-primary">
        <div class="panel-heading">内容发布</div>
        <div class="panel-body">
            <div id="data" style="display:block">
          <form class="form-horizontal" role="form" id="addForm" method="post">
          	  <input type="hidden" name="content" id="content" />
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>文章标题</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="articleTitle"  name="articleTitle" placeholder="文章标题" style="height:35px;">
                </div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，限定为60个字符（30个汉字）</span>
				</div>                
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">发布人</label>
                <div class="col-sm-5">
                  <span class="vlight">admin</span>
                </div>
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>所属分类</label>
                <div class="col-sm-8">
                  <div class="row">
                        <div class="col-xs-2 hm-col">
                            <select id="levelOne" name="levelOne" class="form-control categoryLevelOne" style="margin-left:25px;">
  						         <option value="">请选择</option>						               
							     <option value="d82d503c-9efe-11e4-aae2-1f2b79deec07">帮助中心</option>						               
                            </select>
                        </div>
                        <div class="col-xs-2 hm-col" style="margin-left:25px;">
					        <select id="levelTwo" name="levelTwo" class="form-control categoryLevelTwo" style="margin-left:25px;">
						         <option value="">请选择</option>
						               <#list categoryForLevel2 as cf2>
							     <option value="${cf2.id}">${cf2.name}</option>
						               </#list>
					        </select>
                        </div>
                        <div class="col-xs-2 hm-col" style="margin-left:25px;">
					        <select id="levelThree" name="levelThree" class="form-control categoryLevelThree" style="margin-left:25px;">
						         <option value="">请选择</option>
						               <#list categoryForLevel3 as cf3>
							     <option value="${cf3.id}">${cf3.name}</option>
						               </#list>
					        </select>
                        </div>
	                 <div class="col-xs-2" style="margin-left:25px;">
						<span class="alert-danger" style="display:none;background:none" style="margin-left:25px;">三级分类必须同时选择</span>
					 </div>                                        
                    </div>                                  
                </div>
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>排序</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="order" name="order" style="height:35px;">
                </div>
		        <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，只能为数字</span>
				</div>                                                        
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">文章关键字</label>
                <div class="col-sm-5">
                  <input type="text" class="form-control" id="keywords" name="keywords" placeholder="文章关键字" style="height:35px;">
                </div>
                <span class="vlight">请以，号隔开</span>
                <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">非必填项，限定为60个字符（30个汉字）</span>
				</div>                                
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">文章描述</label>
                <div class="col-sm-8">
                  <textarea class="form-control" rows="3" id="description" name="description"></textarea>
                </div>
                <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">非必填项，限定字符400个（汉字为200个）</span>
				</div>                                                
              </div>
              
              
              <div id="toolbar" class="btn-toolbar" data-role="editor-toolbar" data-target="#editor" style="padding-left:160px;">
					<div class="btn-group">
						<a class="btn dropdown-toggle" data-toggle="dropdown" title="字体"><i
							class="icon-font"></i><b class="caret"></b></a>
						<ul class="dropdown-menu">
						</ul>
					</div>

					<div class="btn-group">
						<a class="btn dropdown-toggle" data-toggle="dropdown" title="字号"><i
							class="icon-text-height"></i>&nbsp;<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a data-edit="fontSize 5"><font size="5">Huge</font></a></li>
							<li><a data-edit="fontSize 3"><font size="3">Normal</font></a></li>
							<li><a data-edit="fontSize 1"><font size="1">Small</font></a></li>
						</ul>
					</div>

					<div class="btn-group">
						<a class="btn dropdown-toggle" data-toggle="dropdown" title="字体颜色"><i
							class="icon-tint"></i>&nbsp;<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a class="btn" data-edit="foreColor Red" title="使用红色">
									<font color="Red">红色</font>
							</a></li>
							<li><a class="btn" data-edit="foreColor Green" title="使用绿色">
									<font color="Green">绿色</font>
							</a></li>
							<li><a class="btn" data-edit="foreColor Blue" title="使用蓝色"><font
									color="Blue">蓝色</font></a></li>
							<li><a class="btn" data-edit="foreColor Yellow" title="使用黄色"><font
									color="Yellow">黄色</font></a></li>
							<li><a class="btn" data-edit="foreColor Orange" title="使用橙色"><font
									color="Orange">橙色</font></a></li>
							<li><a class="btn" data-edit="foreColor White" title="使用白色"><font
									color="White">白色</font></a></li>
							<li><a class="btn" data-edit="foreColor Gray" title="使用灰色"><font
									color="Gray">灰色</font></a></li>
							<li><a class="btn" data-edit="foreColor Black" title="使用黑色"><font
									color="Black">黑色</font></a></li>
						</ul>
					</div>

					<div class="btn-group">
						<a class="btn" data-edit="bold" title="加粗 (Ctrl/Cmd+B)"><i
							class="icon-bold"></i></a> <a class="btn" data-edit="italic"
							title="倾斜 (Ctrl/Cmd+I)"><i class="icon-italic"></i></a> <a
							class="btn" data-edit="strikethrough" title="中划线"><i
							class="icon-strikethrough"></i></a> <a class="btn"
							data-edit="underline" title="下划线 (Ctrl/Cmd+U)"><i
							class="icon-underline"></i></a>
					</div>

					<div class="btn-group"> 
						<a class="btn" data-edit="insertunorderedlist" title="项目符号"><i
							class="icon-list-ul"></i></a> <a class="btn"
							data-edit="insertorderedlist" title="编号"><i
							class="icon-list-ol"></i></a> <a class="btn" data-edit="outdent"
							title="减少缩进量 (Shift+Tab)"><i class="icon-indent-left"></i></a> <a
							class="btn" data-edit="indent" title="增加缩进量 (Tab)"><i
							class="icon-indent-right"></i></a>
					</div>

					<div class="btn-group">
						<a class="btn" data-edit="justifyleft" title="文本左对齐 (Ctrl/Cmd+L)"><i
							class="icon-align-left"></i></a> <a class="btn"
							data-edit="justifycenter" title="居中 (Ctrl/Cmd+E)"><i
							class="icon-align-center"></i></a> <a class="btn"
							data-edit="justifyright" title="文本右对齐 (Ctrl/Cmd+R)"><i
							class="icon-align-right"></i></a> <a class="btn"
							data-edit="justifyfull" title="两端对齐(Ctrl/Cmd+J)"><i
							class="icon-align-justify"></i></a>
					</div>

					<div class="btn-group">
						<a class="btn dropdown-toggle" data-toggle="dropdown" title="超链接"><i
							class="icon-link"></i></a>
						<a class="btn" data-edit="unlink" title="取消超链接"><i
							class="icon-cut"></i></a>

					</div>
					<div class="btn-group">
						<a class="btn" title="插入图片 " id="pictureBtn"><i
							class="icon-picture"></i></a> <input id="insertPic" type="file"
							data-role="magic-overlay" data-target="#pictureBtn"
							data-edit="insertImage" accept="image/*" />
					</div>

					<div class="btn-group">
						<a class="btn" data-edit="undo" title="撤销 (Ctrl/Cmd+Z)"><i
							class="icon-undo"></i></a> <a class="btn" data-edit="redo"
							title="重做 (Ctrl/Cmd+Y)"><i class="icon-repeat"></i></a>
					</div>
				</div>
            
              <div class="form-group">
                <label for="" class="col-sm-2 control-label"><span style="color:red;">* </span>内容编辑</label>
                <div class="col-sm-8">
                  <div id="editor" style="width:900px;"></div>
                </div>
                <div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项，限定字符1万个（汉字为5000个）</span>
				</div>                                                                
              </div>
              
              <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                  <div class="col-xs-3"><button type="button" class="btn btn-primary btn-block" id="publishContent">提交发布</button></div>
                  <div class="col-xs-3"><button type="button" class="btn btn-primary btn-block" id="submitAgin">提交并继续发布</button></div>
                  <div class="col-xs-3"><button type="button" class="btn btn-default btn-block" id="cacelContentButton">取消发布</button></div>                  
                </div>
              </div>
            </form>     
        </div> 
        </div>
      </div>
</body>
<script type="text/javascript">
$(function() {
	//点击发布页面中取消按钮
		$("#cacelContentButton").on("click", function() {
			$.link.html(null, {
				url : '${app}/content/contentIndex',
				target : 'main'
			});
		});
		initToolbarBootstrapBindings();
		$('#editor').wysiwyg({
			fileUploadError : showErrorAlert
		});
		$("#pictureBtn").bind("click", function() {
			$("#insertPic").click();
		});
		$(
				"#articleTitle,#levelOne,#levelTwo,#levelThree,#order,#keywords,#description,#editor")
				.on('blur', function(i, item) {
					checkInput(this);
				});

		//点击提交并继续发布按钮
		$("#submitAgin").on("click", function() {
			if (checkAll()) {
				$.link.html(null, {
					url : '${app}/content/addPublishAgain',
					data : $("#addForm").serialize(),
					target : 'main'
				});
			};		   
		});
	

		//当一级分类下拉框改变时候，获取二级分类下拉框
		$(".categoryLevelOne").bind(
				"change",
				function() {
					var parentCode = $(".categoryLevelOne").val();

					$.ajax({
						type : 'POST',
						url : '${app}/content/findCategoryByParent',
						data : 'level=二级&parentCode=' + parentCode
								+ '&parentId=' + parentCode,
						success : function(msg) {
							// 清空表格
							$(".categoryLevelTwo").empty();
							var option = "<option value=\"\">请选择</option>";
							var _data = msg[0].parent.children;
							_data[0] = msg[0];
							$.each(_data, function(k, v) {
								option += "<option value=\"" + v['id'] + "\">"
										+ v['name'] + "</option>";
							});
							$(".categoryLevelTwo").append(option);
						},
						error : function(msg, textStatus, e) {
							alert("获取二级分类失败");
							$.link.html(null, {
								url : '${app}/content/categoryIndex',
								target : 'main'
							});
						}
					});
				});

		//当二级分类下拉框改变时候，获取三级分类下拉框
		$(".categoryLevelTwo").bind(
				"change",
				function() {
					var parentCode = $(".categoryLevelTwo").val();

					$.ajax({
						type : 'POST',
						url : '${app}/content/findCategoryByParent',
						data : 'level=二级&parentCode=' + parentCode
								+ '&parentId=' + parentCode,
						success : function(msg) {
							// 清空表格
							$(".categoryLevelThree").empty();
							var option = "<option value=\"\">请选择</option>";
							var _data = msg[0].parent.children;
							_data[0] = msg[0];
							$.each(_data, function(k, v) {
								option += "<option value=\"" + v['id'] + "\">"
										+ v['name'] + "</option>";
							});
							$(".categoryLevelThree").append(option);
						},
						error : function(msg, textStatus, e) {
							alert("获取二级分类失败");
							$.link.html(null, {
								url : '${app}/content/categoryIndex',
								target : 'main'
							});
						}
					});
				});

		//点击提交按钮
		$("#publishContent").on("click", function() {
			if (checkAll()) {
				$.link.html(null, {
					url : '${app}/content/addPublish',
					data : $("#addForm").serialize(),
					target : 'main'
				});
			}
			;
		});
		
		
	});

	//对输入元素进行校验
	function checkInput(e) {
		var $this = $(e);
		var val = $this.val();
		if (((e.id == 'articleTitle' && !/.{1,30}/.test(val))
				|| (e.id == 'order' && !/^[0-9]+$/.test(val))
				|| (e.id == 'levelOne' && $this.val() == '')
				|| (e.id == 'levelTwo' && $this.val() == '')
				|| (e.id == 'levelThree' && ($this.val() == '' || $this.val() == null))
				|| (e.id == 'keywords' && !/.{0,30}/.test(val)) //非必填
		|| (e.id == 'description' && !/.{0,400}/.test(val)) //非必填
		)) {
			$this.parent().parent().find(".alert-danger:eq(0)").attr("e_id",
					e.id);
			$this.parent().parent().find(".alert-danger:eq(0)").show();
			return false;
		} else {
			var e_id = $this.parent().parent().find(".alert-danger:eq(0)")
					.attr("e_id");
			if (e_id == '' || e_id == e.id) {
				$this.parent().parent().find(".alert-danger:eq(0)").hide();
			}
			$('#content').val($("#editor").html());
			return true;
		}
	}

	//元素失去焦点时，触发数据校验事件
	function checkAll() {
		$(
				"#articleTitle,#levelOne,#levelTwo,#levelThree,#order,#keywords,#description,#editor")
				.each(function(i, item) {
					checkInput(this);
				});
		return $("span.alert-danger:visible").length == 0;
	}
	
	function initToolbarBootstrapBindings() {
		var fonts = [ '宋体', '黑体', '隶书', '楷体_GB2312', '微软雅黑', 'Arial',
				'Times New Roman' ], fontTarget = $('[title=字体]').siblings(
				'.dropdown-menu');
		$
				.each(
						fonts,
						function(idx, fontName) {
							fontTarget
									.append($('<li><a data-edit="fontName ' + fontName + '" style="font-family:\'' + fontName + '\'">'
											+ fontName + '</a></li>'));
						});
		$('a[title]').tooltip({
			container : 'body'
		});
		$('.dropdown-menu input').click(function() {
			return false;
		}).change(
				function() {
					$(this).parent('.dropdown-menu').siblings(
							'.dropdown-toggle').dropdown('toggle');
				}).keydown('esc', function() {
			this.value = '';
			$(this).change();
		});

		$('[data-role=magic-overlay]').each(
				function() {
					var overlay = $(this), target = $(overlay.data('target'));
					overlay.css('opacity', 0).css('position', 'absolute')
							.offset(target.offset()).width(target.outerWidth())
							.height(target.outerHeight());
				});

		if ("onwebkitspeechchange" in document.createElement("input")) {
			var editorOffset = $('#editor').offset();
			$('#voiceBtn').css('position', 'absolute').offset({
				top : editorOffset.top,
				left : editorOffset.left + $('#editor').innerWidth() - 35
			});
		} else {
			$('#voiceBtn').hide();
		}
	};

	function showErrorAlert(reason, detail) {
		var msg = '';
		if (reason === 'unsupported-file-type') {
			msg = "Unsupported format " + detail;
		} else {
			console.log("error uploading file", reason, detail);
		}
		$(
				'<div class="alert"> <button type="button" class="close" data-dismiss="alert">&times;</button>'
						+ '<strong>File upload error</strong> '
						+ msg
						+ ' </div>').prependTo('#alerts');
	};
</script>
</html>