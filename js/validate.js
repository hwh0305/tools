/**
 * 判断是否为整数
 * author: Wenhao.Hu
 */
function isInteger(src) {
	var pattern = /^(0|([+-]?[1-9]\d+))$/;
	return pattern.test(src);
}

/**
 * 判断是否为自然数，此自然数遵从数论中的自然数概念，即从1到正无穷大的整数集合。
 * author: Wenhao.Hu
 */
function isNaturalNumber(src) {
	var pattern = /^([1-9]|([1-9]\d+))$/;
	return pattern.test(src);
}

/**
 * 判断是否为实数
 * author: Wenhao.Hu
 */
function isRealNumber(src) {
	var pattern = /^([+-]?(\d|([1-9]\d+))|(\d+\.\d+))$/;
	return pattern.test(src);
}

/**
 * 判断是否为价格
 * author: Wenhao.Hu
 */
function isMoney(src) {
	var pattern = /^((\d|([1-9]\d+))|(\d+\.\d{1,2}))$/;
	return pattern.test(src);
}

/**
 * 验证是否为手机号码
 * author: Wenhao.Hu
 */
function isMobile(src) {
	var pattern = /^((?:13\d|15[89])-?\d{5}(\d{3}|\*{3}))$/;
	return pattern.test(src);
}

/**
 * 验证是否为电话号码
 * author: Wenhao.Hu
 */
function isTel(src) {
	var pattern = /^((([0\+]\d{2,3}-?)?(0?\d{2,3})-?)?(\d{6,8})(-?(\d+))?)$/;
	return pattern.test(src);
}
 
/**
 * 验证是否为车牌号码
 * author: Wenhao.Hu
 */
function isCarNumber(src) {
	var pattern = /^(([\u4e00-\u9fa5]|[A-Z]){1,2}([A-Za-z]{1,2}-?)[0-9A-Za-z]{5,})$/;
	return pattern.test(src);
}

/**
 * 验证是否为邮政编码
 * author: Wenhao.Hu
 */
function isPostCode(src) {
	var pattern = /^([0-9]{6})$/;
	return pattern.test(src);
}

/**
 * 判断字符串是否为空或全部是空白字符
 * author: Wenhao.Hu
 */
function nullOrBlank(src) {
	if (src && src.trim().length != 0)
		return false;
	else
		return true;
}