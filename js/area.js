var Area = Class.create();

Area.prototype = {
	initialize: function (parent, country, state, city, county) {
		this.country = document.createElement("SELECT");
		this.country.setAttribute("name", country);
		this.country.setAttribute("id", country);
		this.state = document.createElement("SELECT");
		this.state.setAttribute("name", state);
		this.state.setAttribute("id", state);
		this.city = document.createElement("SELECT");
		this.city.setAttribute("name", city);
		this.city.setAttribute("id", city);
		this.county = document.createElement("SELECT");
		this.county.setAttribute("name", county);
		this.county.setAttribute("id", county);
		parent.appendChild(this.country);
		parent.appendChild(this.state);
		parent.appendChild(this.city);
		parent.appendChild(this.county);
		this.country.onchange = this.setState.bindAsEventListener(this);
		this.state.onchange = this.setCity.bindAsEventListener(this);
		this.city.onchange = this.setCounty.bindAsEventListener(this);
		var obj = null;
		DWREngine.setAsync(false);
		areaBean.findByParent(0, function (o) {
			obj = o;
		});
		DWREngine.setAsync(true);
		this.removeAll(this.country);
		this.addOptions(this.country, obj);
		this.removeAll(this.state);
		this.removeAll(this.city);
		this.removeAll(this.county);
		this.setState();
	},
	
	setState: function() {
		var cid = this.country.options[this.country.selectedIndex].value;
		var obj = null;
		DWREngine.setAsync(false);
		areaBean.findByParent(cid, function (o) {
			obj = o;
		});
		DWREngine.setAsync(true);
		this.removeAll(this.state);
		this.removeAll(this.city);
		this.removeAll(this.county);
		this.addOptions(this.state, obj);
		this.setCity();	
	},
	
	setCity: function() {
		var sid = this.state.options[this.state.selectedIndex].value;
		var obj = null;
		DWREngine.setAsync(false);
		areaBean.findByParent(sid, function (o) {
			obj = o;
		});
		DWREngine.setAsync(true);
		this.removeAll(this.city);
		this.removeAll(this.county);
		this.addOptions(this.city, obj);
		this.setCounty();
	},
	
	setCounty: function() {
		var cid = this.city.options[this.city.selectedIndex].value;
		var obj = null;
		DWREngine.setAsync(false);
		areaBean.findByParent(cid, function (o) {
			obj = o;
		});
		DWREngine.setAsync(true);
		this.removeAll(this.county);
		this.addOptions(this.county, obj);
	},
	
	removeAll: function(obj) {
		obj.innerHTML = "";
	},
	
	addOptions: function(obj, json) {
		var option = document.createElement("option");
		option.setAttribute("value", -1);
		option.innerHTML = "请选择...";
		obj.appendChild(option);
		if (json != null && json.length != 0) {
			for (i = 0; i < json.length; ++ i) {
				var option = document.createElement("option");
				option.setAttribute("value", json[i].id);
				option.innerHTML = json[i].name;
				obj.appendChild(option);
			}
		}
	}
}