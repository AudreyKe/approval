
let util = {
    toDefaultPage(routers, name, route, next) {
        let len = routers.length;
        let i = 0;
        let notHandle = true;
        while (i < len) {
            if (routers[i].name === name && routers[i].children && routers[i].redirect === undefined) {
                route.replace({
                    name: routers[i].children[0].name
                });
                notHandle = false;
                next();
                break;
            }
            i++;
        }
        if (notHandle) {
            next();
        }
    },
    openNewPage(vm, name, argu, query) {
        let pageOpenedList = vm.$store.state.app.pageOpenedList;
        let openedPageLen = pageOpenedList.length;
        let i = 0;
        let tagHasOpened = false;
        while (i < openedPageLen) {
            if (name === pageOpenedList[i].name) { // 页面已经打开
                vm.$store.commit('pageOpenedList', {
                    index: i,
                    argu: argu,
                    query: query
                });
                tagHasOpened = true;
                break;
            }
            i++;
        }
        if (!tagHasOpened) {
            let tag = vm.$store.state.app.tagsList.filter((item) => {
                if (item.children) {
                    return name === item.children[0].name;
                } else {
                    return name === item.name;
                }
            });
            tag = tag[0];
            if (tag) {
                tag = tag.children ? tag.children[0] : tag;
                if (argu) {
                    tag.argu = argu;
                }
                if (query) {
                    tag.query = query;
                }
                vm.$store.commit('increateTag', tag);
            }
        }
        vm.$store.commit('setCurrentPageName', name);
    },
    setObjNull(obj){//对象置空
        obj = JSON.parse(JSON.stringify(obj));
        for (let k of Object.keys(obj)) {
            obj[k] = ''
        }
        return obj;
    },
    fixMoney(s) {//金额格式化
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