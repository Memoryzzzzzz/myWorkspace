/*******
 *
 * JYWLCommon
 * zhouyang 2019-01-017 09:41
 *
 * */
var JYWLCommon = (function (common) {

  var Net = {};//网络
  var Arrays = {};//数组
  var Modal = {};//弹框
  var Verify = {};//验证
  var Str = {};//字符操作
  var Map = {};//Map操作

  /**
   * 获取指定的URL参数值
   */
  Net.getQueryParam = function (name) {
    var search = document.location.search;
    //alert(search);
    var pattern = new RegExp("[?&]" + name + "\=([^&]+)", "g");
    var matcher = pattern.exec(search);
    var items = null;
    if (null != matcher) {
      try {
        items = decodeURIComponent(decodeURIComponent(matcher[1]));
      } catch (e) {
        try {
          items = decodeURIComponent(matcher[1]);
        } catch (e) {
          items = matcher[1];
        }
      }
    }
    return items;
  };
  /**
   * 获取相对路径 假如当前 Url 是 http// www. liangshunet. com/pub/item.aspx?t=osw7，则截取到的相对路径为：/pub/item.aspx。
   * @returns {string}
   * @constructor
   */
  Net.GetUrlRelativePath = function () {
    var url = document.location.toString();
    var arrUrl = url.split("//");
    var start = arrUrl[1].indexOf("/");
    var relUrl = arrUrl[1].substring(start);//stop省略，截取从start开始到结尾的所有字符
    if (relUrl.indexOf("?") !== -1) {
      relUrl = relUrl.split("?")[0];
    }
    return relUrl;
  };
  /**
   * 数组去重
   * @param array
   * @param keys
   */
  Arrays.UniqeByKeys = function (array, keys) {
    var arr = [];
    var hash = {};
    for (var i = 0, j = array.length; i < j; i++) {
      var k = Arrays.obj2key(array[i], keys);
      if (!(k in hash)) {
        hash[k] = true;
        arr.push(array[i]);
      }
    }
    return arr;
  };
  /**
   * obj2key
   */
  Arrays.obj2key = function (obj, keys) {
    var n = keys.length,
        key = [];
    while (n--) {
      key.push(obj[keys[n]]);
    }
    return key.join('|');
  };
  /**
   * 弹框
   * @param content
   */
  Modal.alert = function (content) {
    var dia = dialog({
      title: "提示",
      content: '<div style="text-align:center;padding:15px;font-size:14px;"><div style="margin-top: 20px;"><p>'
          + content + '</p></div></div>',
      width: 400,
      height: 100
    });
    dia.showModal();
  };
  /**
   * 判断为空
   */
  Verify.IsNullOrEmpty = function (v) {
    return !(typeof (v) === "string" && v.length !== 0);
  };
  /**
   * 判断相等
   */
  Verify.IsEqual = function (str1, str2) {
    return (str1 === str2);
  };
  /**
   * 判断Interger
   * @param str
   * @returns {boolean}
   */
  Verify.IsInteger = function (str) {
    var re = new RegExp(/^(-|\+)?\d+$/);
    return re.test(str);
  };
  /**
   * Trim
   */
  Str.Trim = function (str) {
    var result = str.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
    return result;
  };
  /**
   * @param str 要获得长度的字符串
   * @returns {number} 获得字符串实际长度，中文2，英文1
   */
  Str.GetLength = function (str) {
    var realLength = 0, len = str.length, charCode = -1;
    for (var i = 0; i < len; i++) {
      charCode = str.charCodeAt(i);
      if (charCode >= 0 && charCode <= 128) {
        realLength += 1;
      } else {
        realLength += 2;
      }
    }
    return realLength;
  };
  /**
   * js截取字符串，中英文都能用
   * @param str：需要截取的字符串
   * @param len: 需要截取的长度
   */
  Str.cutstr = function (str, len) {
    var str_length = 0;
    var str_len = 0;
    var str_cut = new String();
    str_len = str.length;
    for (var i = 0; i < str_len; i++) {
      a = str.charAt(i);
      str_length++;
      if (escape(a).length > 4) {
        //中文字符的长度经编码之后大于4
        str_length++;
      }
      str_cut = str_cut.concat(a);
      if (str_length >= len) {
        str_cut = str_cut.concat("...");
        return str_cut;
      }
    }
    //如果给定字符串小于指定长度，则返回源字符串；
    if (str_length < len) {
      return str;
    }
  };
  /**
   * 空字符
   * @returns {string}
   */
  Str.Empty = function () {
    return "";
  };
  /**
   * 验证字符
   * @param original
   * @param  compare
   */
  Str.StartWith = function (original, compare) {
    if (compare == null || compare === Str.Empty || original === null || original === Str.Empty || original.length === 0
        || compare.length > original.length) {
      return false;
    }
    return original.substr(0, compare.length) === compare;
  };
  /**
   * 模拟Java Map操作
   * @constructor
   */
  Map.NewMap = function Map() {
    var struct = function (key, value) {
      this.key = key;
      this.value = value;
    };
    // 添加map键值对
    var put = function (key, value) {
      for (var i = 0; i < this.arr.length; i++) {
        if (this.arr[i].key === key) {
          this.arr[i].value = value;
          return;
        }
      }
      ;
      this.arr[this.arr.length] = new struct(key, value);
    };
    //  根据key获取value
    var get = function (key) {
      for (var i = 0; i < this.arr.length; i++) {
        if (this.arr[i].key === key) {
          return this.arr[i].value;
        }
      }
      return null;
    };
    //   根据key删除
    var remove = function (key) {
      var v;
      for (var i = 0; i < this.arr.length; i++) {
        v = this.arr.pop();
        if (v.key === key) {
          continue;
        }
        this.arr.unshift(v);
      }
    };
    //   获取map键值对个数
    var size = function () {
      return this.arr.length;
    };
    // 判断map是否为空
    var isEmpty = function () {
      return this.arr.length <= 0;
    };
    this.arr = [];
    this.get = get;
    this.put = put;
    this.remove = remove;
    this.size = size;
    this.isEmpty = isEmpty;
    return this;
  };

  common.Net = Net;
  common.Arrays = Arrays;
  common.Modal = Modal;
  common.Verify = Verify;
  common.Str = Str;
  common.Map = Map;
  return common;
})(JYWLCommon || {});
