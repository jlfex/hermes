<div class="block">
	<div class="body-xs">
		<div class="row u-row">
			<div class="col-xs-6 u-col line-right">
				<div class="body-sm clearfix">
					<div class="pull-left headImg" style="padding-right:20px;">
						<#if avatar?exists>
								<p><img alt="" src="${avatar.image!''}" class="avatar-lg"></p>
								<a class="replace_hd_img" href="#" style="padding-left:15px;"><@messages key="account.info.change.avatar" /></a>
							<#else>
								<p><img class="headimg" src="${app.theme}/public/other/images/icon1/acdount_head_img02.png"/></p>
								<a class="replace_hd_img" href="#" style="padding-left:15px;"><@messages key="account.info.change.avatar" /></a>
							</#if>
					</div>
					<div class="pull-left">
						<p class="name">${email}</p>
						<p><a id="modPwd" href="${app}/account/index?type=password"><@messages key="account.info.user.reset.password" /></a></p>
						<ul id="authUl" class="auth clearfix">
							<li class="email active" data-auth="${userPro.authEmail}"><i class="fa fa-envelope"></i></li>
							<li class="cellphone" data-auth="${userPro.authCellphone}"><i class="fa fa-mobile"></i></li>
							<li class="name" data-auth="${userPro.authName}"><i class="fa fa-user"></i></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-xs-6 u-col">
				<div class="body">
					<div class="pull-center" >
						<p class="text-muted"><@messages key="account.info.user.cash" />&nbsp;<span class="currency currency-lg">${cashBalance?string('#,##0.00')}</span>&nbsp;<@messages key="common.unit.cny" /><#t></p>
					</div>
					<div class="pull-center" >
						<@messages key="account.info.user.balance" />&nbsp;<span class="currency">${allBalance?string('#,##0.00')}</span>&nbsp;<@messages key="common.unit.cny" /><#t>
						|<#t>
						<@messages key="account.info.user.frozen" />&nbsp;<span class="currency">${freezeBalance?string('#,##0.00')}</span>&nbsp;<@messages key="common.unit.cny" /><#t>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="block">
	<div class="body-sm">
		<!-- Nav tabs -->
		<ul id="navTab" class="nav nav-tabs">
		  <li><a href="#basic" data-toggle="tab"><@messages key="account.person.basic" /></a></li>
		  <li><a href="#job" data-toggle="tab"><@messages key="account.person.job" /></a></li>
		  <li><a href="#house" data-toggle="tab"><@messages key="account.person.house" /></a></li>
		  <li><a href="#car" data-toggle="tab"><@messages key="account.person.car" /></a></li>
		  <li><a href="#contacter" data-toggle="tab"><@messages key="accoutn.person.contacter" /></a></li>
		  <li><a href="#picture" data-toggle="tab"><@messages key="account.person.picture" /></a></li>
		</ul>
		
		<!-- Tab panes -->
		<div class="tab-content">
		  <div class="tab-pane active" id="basic"><div id="basicInfo" style="min-width:800px;height:100%;"></div></div>
		  <div class="tab-pane" id="job"><div id="jobInfo"  style="height:100%;"></div></div>
		  <div class="tab-pane" id="house"><div id="houseInfo"  style="height:100%;"></div></div>
		  <div class="tab-pane" id="car"><div id="carInfo"  style="height:100%;"></div></div>
		  <div class="tab-pane" id="contacter"><div id="contacterInfo"  style="height:100%;"></div></div>
		  <div class="tab-pane" id="picture"><div id="pictureInfo"  style="height:100%;"></div></div>
		</div>

	</div>
</div>
<div id="myModal" class="modal fade bs-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" >
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	         <h4 class="modal-title"><@messages key="account.info.upload.avatar" /></h4>
	      </div>
	     <div class="modal-body" style="width:600px;height:450px;">
	     
	      </div>
    </div>
  </div>
</div>

<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
	//初始化认证图标
	$("#authUl").find('li').each(function() {
		if ($(this).data().auth == 10) 
		{
			$(this).addClass('active');
			$(this).popover({
				html: true,
				placement: 'bottom',
				trigger: 'hover', 
				content: '<span style="white-space:nowrap;">已认证</span>'
			});
		}else{
			$(this).popover({
				html: true,
				placement: 'bottom',
				trigger: 'hover', 
				content: '<span style="white-space:nowrap;">未认证</span>'
			});
		}
	});
 	$('#navTab a:first').tab('show') // Select first tab
 	$("#navTab").find('li').click(function(){
 		var index = $(this).index();
 		if(index==1){
 			init("${app}/account/getUserJob","jobInfo");
 		}else if(index==2){
 			init("${app}/account/getUserHouse","houseInfo");
 		}else if(index==3){
 			init("${app}/account/getUserCar","carInfo");
 		}else if(index==4){
 			init("${app}/account/getUserContacter","contacterInfo");
 		}else if(index==5){
 			init("${app}/account/getUserPicture","pictureInfo");
 		}
 	});
	init("${app}/account/getUserBasic","basicInfo");
	
	//上传头像
	$(".headImg img").mouseenter(function(){
		$("a.replace_hd_img").show();
		});
	$(".headImg").mouseleave(function(){
		$("a.replace_hd_img").hide();
	});
	$(".replace_hd_img").on("click",function(){
		 $('#myModal').modal('show');
	});
	$('#myModal').on('show.bs.modal',function(){
		$.ajax({
		     url: "${app}/account/changeAvatar",
		     success:function(data) {
		       $(".modal-body").html(data) // 内容装入div中
			 }
	   })
	});

	$(".close").on("click",function(){
		window.location.reload();
	});
});
function init(_url,_content){
		$.ajax({
		     url: _url,
		     success:function(data) {
		       $("#"+_content).html(data) // 内容装入div中
			 }
	   })
}
//-->
</script>