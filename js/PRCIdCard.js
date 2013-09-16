/**
 * 中华人民共和国身份证号码判断器，包括15位和18位的身份证判断。
 * 其中，18位的身份证号码还要进行检验。
 * 具体算法：
 * 	如果为15位身份证号，仅判断其是否为15位的数字。
 * 
 * 	如果为18位的身份证号，首先判断其是否符合18位数字，或者17位数字加x（可为大写）的组合形式。
 * 	如果以上条件成立，进行数位校验。18位身份证号的最后一位为校验位，其中X为罗马数字中的10。
 * 
 * 	校验方法：
 *	（1）十七位数字本体码加权求和公式
 *	S = Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和
 *	Ai:表示第i位置上的身份证号码数字值
 *	Wi:表示第i位置上的加权因子
 *	Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
 *
 *	（2）计算模
 *	Y = mod(S, 11)
 *
 *	（3）通过模得到对应的校验码
 *    Y: 0 1 2 3 4 5 6 7 8 9 10
 *	校验码: 1 0 X 9 8 7 6 5 4 3 2
 * 
 * author: Wenhao.Hu
 */
function PRCIdCard(number) {
	var idCardNumber = number;
	var pattern = /^(\d{15,15})|(\d{17,17}[xX])|(\d{18,18})$/i;
	var WI = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
	var AI = [ '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' ];
	
	this.verify = function () {
		if (idCardNumber != null && pattern.test(idCardNumber))
			idCardNumber = idCardNumber.toUpperCase();
		else
			return false;
		if (idCardNumber.length == 15)
			return true;
		else {
			var flag = getVerifyCode(idCardNumber);
			return flag == idCardNumber.charAt(17);
		}
	};
	
	getVerifyCode = function (idCardNumber) {
		var sum = 0;
		for (i = 0; i < 17; ++ i) {
			var tmp = idCardNumber.charAt(i);
			sum += tmp * WI[i];
		}
		return AI[sum % 11];
	};
	
	this.transition15To18 = function () {
		if (idCardNumber != null && pattern.test(idCardNumber))
			idCardNumber = idCardNumber.toUpperCase();
		else
			return null;
		var buf = idCardNumber;
		buf = buf.substr(0, 6) + "19" + buf.substr(6);
		buf += getVerifyCode(buf);
		return buf;
	};
	
	this.getSexCode = function () {
		if (idCardNumber != null && pattern.test(idCardNumber))
			idCardNumber = idCardNumber.toUpperCase();
		else
			return null;
		if (idCardNumber.length == 15)
			return idCardNumber.charAt(14);
		else
			return idCardNumber.charAt(16);
	};
	
	this.getBirthday = function () {
		if (idCardNumber != null && pattern.test(idCardNumber))
			idCardNumber = idCardNumber.toUpperCase();
		else
			return null;
		if (idCardNumber.length == 15)
			return new Date(idCardNumber.substr(6, 2), idCardNumber.substr(8, 2) - 1, idCardNumber.substr(10, 2));
		else {
			return new Date(idCardNumber.substr(6, 4), idCardNumber.substr(10, 2) - 1, idCardNumber.substr(12, 2));}
	};
};