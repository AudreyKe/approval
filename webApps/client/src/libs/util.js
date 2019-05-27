let util = {
  setObjNull(obj) { //对象置空
    obj = JSON.parse(JSON.stringify(obj));
    for (let k of Object.keys(obj)) {
      obj[k] = ''
    }
    return obj;
  },
  fixMoney(s) { //金额格式化
    let t;
    if (s === "" || s == null || s === undefined) {
      return "0.00";
    } else {
      s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(2) + "";
      var l = s.split(".")[0].split("").reverse(),
        r = s.split(".")[1];
      t = "";
      for (let i = 0; i < l.length; i++) {
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
      }
      return t.split("").reverse().join("") + "." + r;
    }
  },
  fixMoneyInt(s) {
    if (s) {
      s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(2) + "";
      var l = s.split(".")[0].split("").reverse(),
        r = s.split(".")[1];
      let t = "";
      for (let i = 0; i < l.length; i++) {
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
      }
      if (r == '00') {
        return t.split("").reverse().join("");
      } else {
        return t.split("").reverse().join("") + "." + r;
      }
    } else {
      return "0";
    }
  },
  getUrlParameter: function (name) { //获取url参数
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    let index = window.location.hash.indexOf('?');
    var r = window.location.hash.substr(index+1).match(reg);
    if (r != null) {
      return decodeURI(r[2]);
    }
    return null;
  }
};

//日期格式化
Date.prototype.format = function (format) {
  var o = {
    "M+": this.getMonth() + 1, //month
    "d+": this.getDate(), //day
    "h+": this.getHours(), //hour
    "m+": this.getMinutes(), //minute
    "s+": this.getSeconds(), //second
    "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
    "S": this.getMilliseconds() //millisecond
  }
  if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
    (this.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (var k in o)
    if (new RegExp("(" + k + ")").test(format))
      format = format.replace(RegExp.$1,
        RegExp.$1.length == 1 ? o[k] :
        ("00" + o[k]).substr(("" + o[k]).length));
  return format;
}
export default util;
