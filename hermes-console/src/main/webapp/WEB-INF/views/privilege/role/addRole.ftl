<div class="panel panel-primary">
	<div class="panel-heading"><#if creditor??>债权人编辑<#else>债权人新增</#if></div>
	<div class="panel-body">
		<form id="addForm" method="post" action="">
			<div class="row hm-row form-group">
				<label for="creditorNo" class="col-xs-1 control-label">债权人编号</label>
				<div class="col-xs-2">
					<input  disabled="true" type="text" class="form-control" value=" <#if creditor??>${(creditor.creditorNo)!''}<#else>${creditorNo!''}</#if>"> 
					<input id="creditorNo" name="creditorNo" type="hidden" class="form-control" value="<#if creditor??>${(creditor.creditorNo)!''}<#else>${creditorNo!''}</#if>">
					<#if creditor??>
					 <input  name="id" type="hidden" class="form-control" value="${(creditor.id)}">
					</#if>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="creditorName" class="col-xs-1 control-label">债权人名称</label>
				<div class="col-xs-2">
					<input id="creditorName" name="creditorName" type="text" class="form-control" value="${(creditor.creditorName)!}">
				</div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">请输入2-10个字符，支持中文,英文,数字, “_”,“-”</span>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="certType" class="col-xs-1 control-label">证件类型</label>
				<div class="col-xs-2">
				    <select class="form-control" name="certType" id="certType">
						<option value="00"  >身份证</option>
						<!--<option value="01"  >组织机构代码</option>-->
					</select>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="certificateNo" class="col-xs-1 control-label">证件号码</label>
				<div class="col-xs-2">
					<input id="certificateNo" name="certificateNo" type="text" class="form-control" value="${(creditor.certificateNo)!}">
				</div>
				<div class="col-xs-2">
					<span class="alert-danger" id="certificateNo-alert-danger" style="display:none;background:none">必填项</span>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="bankAccount" class="col-xs-1 control-label">银行卡号</label>
				<div class="col-xs-2">
					<input id="bankAccount" name="bankAccount" type="text" class="form-control" value="${(creditor.bankAccount)!}">
				</div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项:请输入有效的银行卡号</span>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="bankName" class="col-xs-1 control-label">银行名称</label>
				<div class="col-xs-2">
					<input id="bankName" name="bankName" type="text" class="form-control" value="${(creditor.bankName)!}">
				</div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="bankBrantch" class="col-xs-1 control-label">所在地</label>
				<div class="col-xs-2">
					<input id="bankBrantch" name="bankBrantch" type="text" class="form-control" value="${(creditor.bankBrantch)!}">
				</div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="contacter" class="col-xs-1 control-label">联系人</label>
				<div class="col-xs-2">
					<input id="contacter" name="contacter" type="text" class="form-control" value="${(creditor.contacter)!}">
				</div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">必填项</span>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="cellphone" class="col-xs-1 control-label">电话</label>
				<div class="col-xs-2">
					<input id="cellphone" name="cellphone" type="text" class="form-control" value="${(creditor.cellphone)!}">
				</div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">格式有误</span>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="mail" class="col-xs-1 control-label">邮箱</label>
				<div class="col-xs-2">
					<input id="mail" name="mail" type="text" class="form-control" value="${(creditor.mail)!}" >
				</div>
				<div class="col-xs-2">
					<span class="alert-danger" style="display:none;background:none">无效的邮箱</span>
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="assoureType" class="col-xs-1 control-label">担保方式</label>
				<div class="col-xs-2">
					<input id="assoureType" name="assoureType" type="text" class="form-control" value="${(creditor.assoureType)!}">
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="source" class="col-xs-1 control-label">来源</label>
				<div class="col-xs-2">
					<input id="source" name="source" type="text" class="form-control" value="${(creditor.source)!}">
				</div>
			</div>
			<div class="row hm-row form-group">
				<label for="remark" class="col-xs-1 control-label">简介</label>
				<div class="col-xs-2">
				    <textarea class="form-control" rows="5" cols="3" name="remark" id="description">${(creditor.remark)!}</textarea>
				</div>
			</div>
			
			<div class="row hm-row form-group">
				<div class="col-xs-offset-1 col-xs-3">
				     <#if creditorKind?? && creditorKind=='01' >
				     <#else>
				      <button id="pushIn" type="button" class="btn btn-promary">提交</button>
				     </#if>
					<button id="retreat" type="button" class="btn btn-promary">取消</button>
				</div>
			</div>
		</form>
	</div>
</div>

<script type="text/javascript">
<!--
jQuery(function($) {
	$("#creditorName,#cert_type,#certificateNo,#bankAccount,#bankName,#bankBrantch,#contacter,#cellphone,#mail").on("blur",function(){
		checkInput(this);
	});
	
	function checkInput(e){
		var $this = $(e);
		var val = $this.val();
		if($this.val()==''
		   ||( e.id == 'creditorName' && (!/^[a-zA-Z0-9\u4e00-\u9fa5_-]+$/.test(val) || val.length < 2 || val.length > 10) )
		   ||( e.id == 'bankAccount' && !/^\d{16,19}$/.test(val) )
		   ||( e.id == 'cellphone' && !/\d{7,13}|-*$/.test(val) )
		   ||( e.id == 'mail' && !/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(val) 
		   || e.id == 'certificateNo' && !valCertificateNo())
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
	
	function checkAll(){
		$("#creditorName,#cert_type,#certificateNo,#bankAccount,#bankName,#bankBrantch,#contacter,#cellphone,#mail").blur();
		return $("span.alert-danger:visible").length==0;
	}
	
	$("#pushIn").on("click",function(){
	   if(checkAll()){
		$.link.html(null, {
			url: '${app}/credit/add',
			data: $("#addForm").serialize(),
			target: 'main'
		});
		};
	});
	$("#retreat").on("click",function(){
		$.link.html(null, {
			url: '${app}/credit/index',
			target: 'main'
		});
	});	
	
	var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}
	function isCardId(sId){ 
		var identityId1=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{2}[xX\d]$/;
		if(sId.length == 15) {
			if(!identityId1.test(sId)){
				return "身份证号码输入错误";
			}
		}
		
	    var iSum=0 ;
	    
	    if(!/^\d{17}(\d|x)$/i.test(sId))  {
	    	return "您输入的身份证长度或格式错误"; 
	    }

	    sId=sId.replace(/x$/i,"a"); 
	    if(aCity[parseInt(sId.substr(0,2))]==null) {
	    	return "您的身份证地区非法"; 
	    }
	    	
	    sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2)); 
	    var d=new Date(sBirthday.replace(/-/g,"/")) ;
	    if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate())) {
	    	return "您的身份证上的出生日期非法"; 
	    }
	    	
	    for(var i = 17;i>=0;i --) {
	    	iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) ;
	    }
	    	
	    if(iSum%11!=1) {
	    	return "您输入的身份证号非法"; 
	    }
	    	
	    return "";
	}

	// 校验阻止机构代码证
	function checkOrgCode(orgCode){
	   var ret=false;
	   
	   var codeVal = ["0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"];
	   var intVal =  [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35];
	   var crcs =[3,7,9,10,5,8,4,2];
	   
	   if(!(""==orgCode) && orgCode.length==10){
	      var sum=0;
	      
	      for(var i=0;i<8;i++){
	         var codeI=orgCode.substring(i,i+1);
	         var valI=-1;
	         for(var j=0;j<codeVal.length;j++){
	             if(codeI==codeVal[j]){
	                valI=intVal[j];
	                break;
	             }
	         }
	         sum+=valI*crcs[i];
	      }
	      
	      var crc=11- (sum%11);
	      switch (crc){
	      	case 10:{
	        	crc="X";
	            	break;
	        	}default:{
	                break;
	            }
	        }
	        
	      //最后位验证码正确
	      if(crc==orgCode.substring(9)){
	      	ret=true;
	      }else{
	      	ret=false;
	    	$("#certificateNo-alert-danger").text("组织机构代码不正确！");
	      }
	   }else if(""==orgCode){
	   		ret=false;
	        $("#certificateNo-alert-danger").text("组织机构代码不能为空！");
	   }else{
	        ret=false;
	        $("#certificateNo-alert-danger").text("组织机构代码格式不正确，组织机构代码为8位数字或者拉丁字母+“-”+1位校验码，并且字母必须大写！");
	   }
	   
	   return ret;
	}
	
	function valCertificateNo() {
		debugger;
		var certificateNo = $("#certificateNo").val();
		var certType = $("#certType").val();
		if(certType == "00") {
			if(isCardId(certificateNo).length != 0) {
				var msg = isCardId(certificateNo);
				$("#certificateNo-alert-danger").text(msg);
				return false;
			}
		} else {
			if(!checkOrgCode(certificateNo)) {
				return false;
			}
		}
		
		return true;
	}
	
	
});
//-->
</script>
