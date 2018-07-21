/*****************************************************************
 jQuery Ajax封装通用类  (linjq)
 *****************************************************************/
$(function(){

  jQuery.axx=function(data) {
    data.async = (data.async==null || data.async=="" || typeof(data.async)=="undefined")? "true" : data.async;
    data.type = (data.type==null || data.type=="" || typeof(data.type)=="undefined")? "post" : data.type;
    data.dataType = (data.dataType==null || data.dataType=="" || typeof(data.dataType)=="undefined")? "json" : data.dataType;
    data.data = (data.data==null || data.data=="" || typeof(data.data)=="undefined")? {"date": new Date().getTime()} : data.data;
    $.ajax({
      type: data.type,
      async: data.async,
      data: data.data,
      url: data.url,
      dataType: data.dataType,
      contentType: data.contentType,
      success: function(d){
        try {
          data.success(d);
        } catch (e) {
        }
      },
      error: function(e){
        console.log('some error here');
        try {
          data.error(e);
        } catch (e) {
        }
        redirect(e)
      }
    });
  };
  /**
   * ajax封装
   * url 发送请求的地址
   * data 发送到服务器的数据，数组存储，如：{"date": new Date().getTime(), "state": 1}
   * async 默认值: true。默认设置下，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为 false。
   *       注意，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
   * type 请求方式("POST" 或 "GET")， 默认为 "GET"
   * dataType 预期服务器返回的数据类型，常用的如：xml、html、json、text
   * successfn 成功回调函数
   * errorfn 失败回调函数
   */
  jQuery.ax=function(url, data, async, type, dataType, successfn, errorfn) {
    async = (async==null || async=="" || typeof(async)=="undefined")? "true" : async;
    type = (type==null || type=="" || typeof(type)=="undefined")? "post" : type;
    dataType = (dataType==null || dataType=="" || typeof(dataType)=="undefined")? "json" : dataType;
    data = (data==null || data=="" || typeof(data)=="undefined")? {"date": new Date().getTime()} : data;
    $.ajax({
      type: type,
      async: async,
      data: data,
      url: url,
      dataType: dataType,
      success: function(d){
        successfn(d);
      },
      error: function(e){
        if(errorfn)
        errorfn(e);
        redirect(e)
      }
    });
  };

  /**
   * ajax封装
   * url 发送请求的地址
   * data 发送到服务器的数据，数组存储，如：{"date": new Date().getTime(), "state": 1}
   * successfn 成功回调函数
   */
  jQuery.axs=function(url, data, successfn) {
    data = (data==null || data=="" || typeof(data)=="undefined")? {"date": new Date().getTime()} : data;
    $.ajax({
      type: "post",
      data: data,
      url: url,
      dataType: "json",
      success: function(d){
        successfn(d);
      }
    });
  };

  /**
   * ajax封装
   * url 发送请求的地址
   * data 发送到服务器的数据，数组存储，如：{"date": new Date().getTime(), "state": 1}
   * dataType 预期服务器返回的数据类型，常用的如：xml、html、json、text
   * successfn 成功回调函数
   * errorfn 失败回调函数
   */
  jQuery.axse=function(url, data, successfn, errorfn) {
    data = (data==null || data=="" || typeof(data)=="undefined")? {"date": new Date().getTime()} : data;
    $.ajax({
      type: "post",
      data: data,
      url: url,
      dataType: "json",
      success: function(d){
        successfn(d);
      },
      error: function(e){

        errorfn(e);
        redirect(e)
      }
    });
  };



});

function redirect(e) {
  sessionout(e.status);
}
function sessionout(status) {
  if (status == "405") {
    alert("会话超时 ！请重新登录...");
    location.href = "/";
  }
}