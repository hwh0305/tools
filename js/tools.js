function setDefaultOption(selectObject, defaultValue) {
	if (selectObject != null && selectObject.tagName && !/select/i.test(selectObject.tagName))
		return;
	var options = selectObject.options;
	var index = 0;
	for (i = 0; i < options.length; ++ i) {
		if (options[i].value == defaultValue) {
			index = i;
			break;
		}
	}
	selectObject.selectedIndex = index;
}

function removeAllOptions(selectObject) {
	if (selectObject != null && selectObject.tagName && !/select/i.test(selectObject.tagName))
		return;
	selectObject.innerHTML = null;
}

function addOptions(selectObject, value, text) {
	if (selectObject != null && selectObject.tagName && !/select/i.test(selectObject.tagName))
		return;
	var option = document.createElement("OPTION");
	option.setAttribute("value", value);
	option.setAttribute("innerHTML", text);
	selectObject.appendChild(option);
}

String.prototype.trim = function () {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}
String.prototype.ltrim = function () {
	return this.replace(/(^\s*)/g, "");
}
String.prototype.rtrim = function () {
	return this.replace(/(\s*$)/g, "");
}