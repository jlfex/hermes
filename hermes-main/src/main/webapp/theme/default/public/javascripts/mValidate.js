$(document).ready(function(){
		
		// 手机号码验证
		$.validator.addMethod(
			'cellphone',function(value,element){
				  var length = value.length;
   				  var phone =  /^((1)+\d{10})$/
   				  return this.optional(element) || (length == 11 && phone.test(value));
			},'手机号码格式错误'
		);
		// 中文验证
		$.validator.addMethod(
			'chinese',function(value,element){
				 var chinese = /^[\u4e00-\u9fa5]+$/;
   				 return this.optional(element) || (chinese.test(value));
			},'只能输入中文'
		);
		// 身份证号码验证   
		$.validator.addMethod("idNumber", function(value, element) {   
  			return this.optional(element) || checkidcard(value);   
		}, "请正确输入您的身份证号码");  
		
		$('#userBasic').validate({
		    rules: {
		      account: {
		        minlength: 2,
		        required: true
		      },
		      realName:{
		      	minlength: 2,
		        required: true,
		        chinese:true
		      },
		      idNumber:{
		        required: true,
		        idNumber: true
		      },
		      cellphone:{
		        required: true,
		        cellphone:true
		      },
		      age:{
		        required: true,
		        number:true
		      },
		      address:{
		        required: true
		      }
		    },
		     messages:{
		     	account:{
		     		required:'不能为空',
		        	minlength:'请至少输入2个字符'
		     	},
		     	realName:{
		     		required:'不能为空',
		        	minlength:'请至少输入2个字符'
		     	},
		     	idNumber:{
		     		required:'不能为空'
		     	},
		     	cellphone:{
		     		required:'不能为空',
		     		number:'请输入正确的手机号'
		     	},
		     	age:{
		     		required:'不能为空',
		     		number:'只能输入数字'
		     	},
		     	address:{
		     		required:'不能为空',
		     	}
		     	
		    },
			highlight: function(element) {
				$(element).closest('.eidt-group').removeClass('has-success').addClass('has-error');
			},
			success: function(element) {
				$(element).addClass('valid').closest('.eidt-group').removeClass('has-error').addClass('has-success');
			},
			submitHandler:function(form) { 
				saveBasic();
			 }
			 
	  });
	  $('#jobForm').validate({
	  		rules:{
	  			name:{
	  				required:true
	  			},
	  			address:{
	  				required:true
	  			},
	  			phone:{
	  				 number:true
	  			},
	  			position:{
	  				required:true
	  			},
	  			annualSalary:{
	  				required:true,
	  				 number:true,
	  				 min:0
	  			},
	  			type:{
	  				required:true
	  			},
	  			registeredCapital:{
	  				 number:true,
	  				 min:0
	  			}
	  		},
	  		messages:{
		     	name:{
		     		required:'不能为空'
		     	},
		     	address:{
		     		required:'不能为空'
		     	},
		     	phone:{
		     		number:'只能输入数字'
		     	},
		     	position:{
		     		required:'不能为空'
		     	},
		     	annualSalary:{
		     		required:'不能为空',
		     		number:'只能输入数字',
		     		min:'不能为负'
		     	},
		     	type:{
		     		required:'不能为空'
		     	},
		     	registeredCapital:{
		     		number:'只能输入数字',
		     		min:'不能为负'
		     	}
		   },
	  		highlight: function(element) {
				$(element).closest('.col-xs-4').removeClass('has-success').addClass('has-error');
			},
			success: function(element) {
				$(element).addClass('valid').closest('.col-xs-4').removeClass('has-error').addClass('has-success');
			},
			submitHandler:function(form) { 
				saveJob();
			 }
	  });
	  $('#houseForm').validate({
	  		rules:{
	  			address:{
	  				required:true
	  			},
	  			certificate:{
	  				required:true
	  			},
	  			area:{
	  				required:true,
	  				 number:true
	  			},
	  			year:{
	  				required:true
	  			}
	  		},
	  		messages:{
		     	address:{
		     		required:'不能为空'
		     	},
		     	certificate:{
		     		required:'不能为空'
		     	},
		     	area:{
		     		required:'不能为空',
		     		number:'只能输入数字'
		     	},
		     	year:{
		     		required:'不能为空'
		     	}
		   },
	  		highlight: function(element) {
				$(element).closest('.col-xs-4').removeClass('has-success').addClass('has-error');
			},
			success: function(element) {
				$(element).addClass('valid').closest('.col-xs-4').removeClass('has-error').addClass('has-success');
			},
			submitHandler:function(form) { 
				saveHouse();
			 }
	  });
	    $('#carForm').validate({
	  		rules:{
	  			brand:{
	  				required:true
	  			},
	  			purchaseYear:{
	  				required:true
	  			},
	  			purchaseAmount:{
	  				required:true,
	  				 number:true,
	  				 min:0
	  			},
	  			licencePlate:{
	  				required:true
	  			}
	  		},
	  		messages:{
		     	brand:{
		     		required:'不能为空'
		     	},
		     	purchaseYear:{
		     		required:'不能为空'
		     	},
		     	purchaseAmount:{
		     		required:'不能为空',
		     		number:'只能输入数字',
		     		min:'不能为负'
		     	},
		     	licencePlate:{
		     		required:'不能为空'
		     	}
		   },
	  		highlight: function(element) {
				$(element).closest('.col-xs-4').removeClass('has-success').addClass('has-error');
			},
			success: function(element) {
				$(element).addClass('valid').closest('.col-xs-4').removeClass('has-error').addClass('has-success');
			},
			submitHandler:function(form) { 
				saveCar();
			 }
	  });
	   $('#contacterForm').validate({
	  		rules:{
	  			name:{
	  				required:true,
	  				chinese:true
	  			},
	  			relationship:{
	  				required:true
	  			},
	  			phone:{
	  				required:true,
	  				 number:true
	  			},
	  			address:{
	  				required:true
	  			}
	  		},
	  		messages:{
		     	name:{
		     		required:'不能为空'
		     	},
		     	relationship:{
		     		required:'不能为空'
		     	},
		     	phone:{
		     		required:'不能为空',
		     		number:'只能输入数字'
		     	},
		     	address:{
		     		required:'不能为空'
		     	}
		   },
	  		highlight: function(element) {
				$(element).closest('.col-xs-4').removeClass('has-success').addClass('has-error');
			},
			success: function(element) {
				$(element).addClass('valid').closest('.col-xs-4').removeClass('has-error').addClass('has-success');
			},
			submitHandler:function(form) { 
				saveContacter();
			 }
	  });
	$('#passwordForm').validate({
		    rules: {
		      email: {
		        minlength: 2,
		        required: true
		      }
		    },
			highlight: function() {
				alert("ss");
			},
			success: function() {
				alert("dd");
			}
	  });
	   $('#authPhoneFrm').validate({
	    rules: {
	      cellphone:{
		        required: true,
		        cellphone:true
		      },
	      validateCode:{
	        required: true,
	         number:true,
	         maxlength:6
	      }
	    },
	     messages:{
	     	cellphone:{
	     		required:'不能为空',
	        	minlength:'请输入正确的手机号'
	     	},
	     	validateCode:{
	     		required:'不能为空',
	     		number:'只能输入数字',
	     		maxlength:'请输入一个长度为6的数字'
	     	},
	    },
		highlight: function(element) {
			$(element).closest('.col-xs-4').removeClass('has-success').addClass('has-error');
		},
		success: function(element) {
			$(element).addClass('valid').closest('.col-xs-4').removeClass('has-error').addClass('has-success');
		},
		submitHandler:function(form) { 
			subAuthPhone();
		 }
		 
  });
	  $('#authIdentityForm').validate({
	    rules: {
	      realName:{
	      	minlength: 2,
	        required: true,
	        chinese:true
	      },
	      idNumber:{
	        required: true,
	        idNumber: true
	      }
	    },
	     messages:{
	     	realName:{
	     		required:'不能为空',
	        	minlength:'请至少输入2个字符'
	     	},
	     	idNumber:{
	     		required:'不能为空'
	     	},
	    },
		highlight: function(element) {
			$(element).closest('.col-xs-4').removeClass('has-success').addClass('has-error');
		},
		success: function(element) {
			$(element).addClass('valid').closest('.col-xs-4').removeClass('has-error').addClass('has-success');
		},
		submitHandler:function(form) { 
			authIdentity();
		 }
		 
  });
}); // end document.ready
function checkidcard(num){  
    var len = num.length, re;  
    if (len == 15)  
        re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{3})$/);  
    else if (len == 18)  
        re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\d)$/);  
    else{  
        //alert("请输入15或18位身份证号,您输入的是 "+len+ "位");   
        return false;  
    }  
    var a = num.match(re);  
    if (a != null){  
        if (len==15){  
            var D = new Date("19"+a[3]+"/"+a[4]+"/"+a[5]);  
            var B = D.getYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5];  
        }else{  
            var D = new Date(a[3]+"/"+a[4]+"/"+a[5]);  
            var B = D.getFullYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5];  
        }  
        if (!B){  
            //alert("输入的身份证号 "+ a[0] +" 里出生日期不对！");   
            return false;  
        }  
    }  
  
    return true;  
}   