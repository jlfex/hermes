//中文英文名（可有空格）
//var ceregex = /^([\u4e00-\u9fa5]+|([a-z]+\s?)+)$/;
var ceregex = /^\D{1,30}$/;

// 手机号码
var mobilePhone = /^1[3|4|5|8][0-9]\d{8}$/;
// 身份证
var identityCard = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;

/** 验证姓名 */
$.validator.addMethod("ceName", function(value, element, param) {
	return this.optional(element) || ceregex.test(value);
}, "姓名不能包含数字!");
/** 验证手机号码 */
$.validator.addMethod("mobilePhone", function(value, element, param) {
	return this.optional(element) || mobilePhone.test(value);
}, "手机号码输入有误!");
/** 验证身份证 */
$.validator.addMethod("identityCard", function(value, element) {
	return this.optional(element) || (identityCard.test(value));
}, "身份证格式不合法!");

/** 银行卡验证 */
$.validator.addMethod("isBankInfo", function(value, element) {
	var bank = /^[A-Za-z0-9]{5,25}$/;
	return this.optional(element) || (bank.test(value));
}, "请输入正确的银行卡账户");

/** 开户行验证 */
$.validator.addMethod("checkSubbranch", function(value, element) {
	return this.optional(element) || (/^[\w\u4e00-\u9fa5]{1,30}$/.test(value));
}, "应为长度在1到30字符之间的中文、英文字母、数字");

$.validator.addMethod("digitsGtZero", function(value, element) {
	return this.optional(element) || Jlfex.Regx.digitsGtZero(value);
}, "请输入一个大于0的正整数");
