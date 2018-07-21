var exportFileName;
var tm;
var dh;
var title;
var filecode;
var redisValue ;

var globalType;
var currentUrl;
var option;
var archiveType;
var fileColumn =  [ {
    field: 'fileName',
    title: '标题',
    align:"center"
}, {
  field: 'content',
        title: '内容'
    }
    ,{field: 'location',
        title: '路径'
    },{field: 'info',
        title: '条目信息'
    }];
var fileColumn_assist =  [
	{"field": "status", "checkbox": true}, {
	  field: 'fileName',
	        title: '标题',
	        align:"center"
	    }, {
	  field: 'content',
	        title: '内容'
	    }
	  ,{field: 'location',
        title: '路径'
    },{field: 'info',
        title: '条目信息'
    }];
var recordColumn_assist =  [
	{"field": "status", "checkbox": true},
	{
	    field: "Number", title: "序号", halign: 'center', align: 'center',
	    formatter: function (value, row, index) {
	        var options = $('#table_searchResult').bootstrapTable("getOptions");
	        return (options.pageNumber - 1) * (options.pageSize) + index + 1;
	    }, width: "5%"
	},
	{
	  field: 'fileNum',
	        title: '档号',
	        align:"center"
	    }, 
	    {
	    	field: 'title',
	        title: '题名'
	    },
	    {
		    field: 'archiveDate',
	        title: '归档日期'
      },
      {
		    field: 'importance',
		    title: '密级'
      },
      {
		    field: 'reserveLocation',
		    title: '存放位置'
  },
  {
	    field: 'archiveType',
	    title: '类型'
}
      
      ];
var recordColumn = [
    {
        field: "Number", title: "序号", halign: 'center', align: 'center',
        formatter: function (value, row, index) {
            var options = $('#table_searchResult').bootstrapTable("getOptions");
            return (options.pageNumber - 1) * (options.pageSize) + index + 1;
}, width: "5%"
    }, {
        field: 'fileNum',
        title: '档号',
        align: "center"
    },
    {
        field: 'title',
        title: '题名'
    },
    {
        field: 'archiveDate',
        title: '归档日期'
    },
    {
        field: 'importance',
        title: '密级'
    },
    {
        field: 'reserveLocation',
        title: '存放位置'
    },
    {
        field: 'archiveType',
        title: '类型'
    }
];
var otherColum = [{
    field: 'title',
    title: '标题',
    align: "center"
}, {
    field: 'reelNum',
    title: '档号'
}, {
    field: 'deptName',
    title: '部门'
}, {
    field: 'createTime',
    title: '创建时间'
}];

var flowId=null;

function getQueryStrWithVal(name) {
    return getQueryStringFromRedis(name, redisValue);
}

function getQueryStringFromRedis(name, value) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = value.match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}

$(function () {
    $.ajaxSetup({cache: false});

    getSessionUser();
    $.axx({
        url: '/redis',
        type: 'get',
        data: {},
        success: function (json) {
            redisValue = json.content;
            var type = getQueryStringFromRedis("type", redisValue);
            globalType = type;
            console.log("globalType    " + globalType);
            console.log(type);
            createTableByType(type);
            // tablePaging(currentUrl);

            flowId = getQueryStringFromRedis("flowId", redisValue);
            console.log("test.js " + flowId);

            $("#btn_toassist").addClass("hidden");
            if (flowId) {
                $("#btn_toassist").removeClass("hidden");
            }

            if (type == 2) {
                searchPaging2(currentUrl);
            } else if (type == 1) {
                option = getQueryStringFromRedis("option", redisValue);
                archiveType = getQueryStringFromRedis("archiveType", redisValue);
                searchPaging1();
            }
        }
    })


});

function getSessionUser() {
    $.axx({
        url: ARCHIVE_API.user_detail,
        type: 'get',
        data: {},
        dataType: 'json',
        success: function (json) {
            contextUser = json.content;
            // usrId = contextUser.id;
            username = contextUser.name;
            $('#loginName').html(username)
        }
    })
}

/*$(".button").click(function(){
    $('#table_searchResult').tableExport( {
            type: 'excel',
            escape: false
        });
});*/

$("#globalSearch").keydown(function (event) {
    if (event.keyCode == 13) {
        if (globalType == 1)
            searchPaging1();
        else if (globalType == 2)
            searchPaging2(currentUrl);
    }
});

/////////////////////////////////////////////////历史数据
//文件表格


function createTableByType(type) {
  if (type == 1) {
    currentUrl = "documents/searchGlobal";
    exportFileName = '文件导出报表';
  } else if (type == 2) {
    currentUrl = "records/searchGlobal";
    exportFileName = '案卷导出报表';
  } else if (type == 3) {
    currentUrl = "archives/searchGlobal";
    exportFileName = '档案导出报表';
  } else if (type == 4) {
    currentUrl = "boxs/searchGlobal";
    exportFileName = '全宗导出报表';
  }
}

function tablePaging(currentUrl) {
  var tableCol;
  var $table;
  if (globalType == 1) {
    tableCol = fileColumn;
//    if(flowId){
//    	tableCol = fileColumn_assist;
//    }
    $table = $('#table_searchResult')
  } else if (globalType == 2) {
    tableCol = otherColum;
    $table = $('#table_searchResult')
  } else if (globalType == 3) {
    tableCol = otherColum;
    $table = $('#table_searchResult')
  } else if (globalType == 4) {
    tableCol = otherColum;
    $table = $('#table_searchResult')
  }

  var queryParams = function (params) {
    console.log("pageNumber", params.pageNumber);
    return {
      pageSize: params.pageSize,
      pageNum: params.pageNumber,
    }
  }
    $table.bootstrapTable({columns: tableCol})

}

function newCreateBootTable($table, currentUrl, queryParams, column) {
    console.log("$table          " + $table);
    console.log("currentUrl          " + currentUrl);
    console.log("queryParams          " + queryParams);
    console.log("column          " + column);
    $table.bootstrapTable('destroy');
    $table.bootstrapTable({
        url: currentUrl,
        method: 'get',
        contentType: "application/x-www-form-urlencoded",
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        clickToSelect: true,
        showExport: true,
        queryParamsType: '',
        totalField: 'total',
        dataField: 'list',//是否显示导出
        exportDataType: 'all',
        paginationShowPageGo: true,
        // exportTypes:['excel','pdf','doc'],
        singleSelect: false,
        exportOptions: {
            fileName: exportFileName,  //文件名称设置
            worksheetName: 'sheet1',  //表格工作区名称
            tableName: exportFileName,
            ignoreColumn: ['status']
            // excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
        },

        columns: column,
        queryParams: queryParams,
        responseHandler: function (result) {
            console.log("responseHandler");
            console.log(result.content);
            var esMap = result.content;
            var otherHave = esMap.otherHave;
            var isManager = esMap.isManager;
            $('#btn_total').addClass("hidden");
            if(otherHave>0 && !isManager){
                $('#btn_total').removeClass("hidden");
            }

            return result.content.thisPage;
        }
    });
}



function createBootTable($table, currentUrl, queryParams, column) {
    console.log("$table          " + $table);
    console.log("currentUrl          " + currentUrl);
    console.log("queryParams          " + queryParams);
    console.log("column          " + column);
    $table.bootstrapTable('destroy');
    $table.bootstrapTable({
        url: currentUrl,
        method: 'get',
        contentType: "application/x-www-form-urlencoded",
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        clickToSelect: true,
        showExport: true,
        queryParamsType: '',
        totalField: 'total',
        dataField: 'list',//是否显示导出
        exportDataType: 'all',
        paginationShowPageGo: true,
        // exportTypes:['excel','pdf','doc'],
        singleSelect: false,
        exportOptions: {
            fileName: exportFileName,  //文件名称设置
            worksheetName: 'sheet1',  //表格工作区名称
            tableName: exportFileName,
            ignoreColumn: ['status']
            // excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
        },

        columns: column,
        queryParams: queryParams,
        responseHandler: function (result) {
            console.log("responseHandler");
            console.log(result.content);
            return result.content;
        }
    });
}

function searchPaging1(){
	title = $("#searchGlobleTitle").val();
    filecode = $("#searchGlobleCode").val();
    if ($.trim(title) == "" && $.trim(filecode) == "") {
        alert("请输入至少一个搜索条件");
      return
    }
    console.log("searchPaging1      option"+option);
    var queryParams = function (params) {
      console.log("pageNumber", params.pageNumber);
      return {
        pageSize: params.pageSize,
        pageNum: params.pageNumber,
        "title": title,
        "fileCode": filecode,
        "archiveType":archiveType,
        "option":option
      }
    }
    var column = fileColumn;
    if(flowId){
    	column=fileColumn_assist;
    }
    newCreateBootTable($('#table_searchResult'), currentUrl, queryParams,
    		column);
}

function searchPaging2(currentUrl) {
    // var url = location.search;

    var archiveType = getQueryStrWithVal("archiveType");
    var fileNum = getQueryStrWithVal("fileNum");
    var title = $("#searchGlobleTitle").val();
    var reserveDuration = getQueryStrWithVal("reserveDuration");
    var startYear = getQueryStrWithVal("startYear");
    var endYear = getQueryStrWithVal("endYear");
    var importance = getQueryStrWithVal("importance");
    var docAttr = getQueryStrWithVal("docAttr");
    var themeWord = getQueryStrWithVal("themeWord");
    var saveNum = getQueryStrWithVal("saveNum");
    var responsible = getQueryStrWithVal("userId");
    var reserveLocation = getQueryStrWithVal("reserveLocation");
    var place = getQueryStrWithVal("place");
    var figure = getQueryStrWithVal("figure");
    var photographer = getQueryStrWithVal("photographer");
    var photographyTime = getQueryStrWithVal("photographyTime");
    var leader = getQueryStrWithVal("leader");
    var number = getQueryStrWithVal("number");

    var queryParams = function (params) {
        console.log("pageNumber1", params);
        console.log("pageNumber1", params.pageNumber);
        return {
            pageSize: params.pageSize,
            pageNum: params.pageNumber,
            "archiveType": archiveType,
            "fileNum": fileNum,
            "title": title,
            "reserveDuration": reserveDuration,
            "startYear": startYear,
            "endYear": endYear,
            "importance": importance,
            "docAttr": docAttr,
            "themeWord": themeWord,
            "saveNum": saveNum,
            "responsible": responsible,
            "reserveLocation": reserveLocation,
            "place": place,
            "figure": figure,
            "photographer": photographer,
            "photographyTime": photographyTime,
            "leader": leader,
            "number": number
        }
    };
    console.log("searchPaging2 " + currentUrl);
    var column = recordColumn;
    if (flowId) {
        column = recordColumn_assist;
    }
    newCreateBootTable($('#table_searchResult'), currentUrl, queryParams,
        column);

}

function to_assit(){
	var rows = $('#table_searchResult').bootstrapTable("getSelections");
	console.log(rows);
	var ids="";
	var type="doc";
	for (var i = 0; i < rows.length; i++) {
		console.log(rows[i]);
		if(rows[i].docId){
			ids += rows[i].docId + '_';
		}
		else{
			ids += rows[i].id + '_';
			type=rows[i].type;
		}
		console.log(ids);
    }
	ids=ids+"&recordtype="+type;
	console.log(ids);
	var toRedisVal = "assist="+ids+"&flowId="+flowId;
	$.axx({
        url:'/redis/cd',
        type:'put',
        data:{jumpData:toRedisVal},
        success:function (json) {
	        to_assist_url(ids);
        }
    })
}
function to_assist_url(ids){
    window.location.href="/main";
    //history.go(-2);
}



//双击结果table 展示file
$('#table_searchResult').on('dbl-click-row.bs.table',function (e,row, $element, field) {
    console.log(row);
    var fileId = row.fileId;
    window.open("/view?fileId=" + fileId + "&fileName=" + encodeURI(row.fileName));
})

