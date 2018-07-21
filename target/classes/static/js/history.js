var contextUser;
var username;
var exportFileName;
var tm;
var dh;
var title;
var filecode;

var globalType;
var currentUrl;
var archiveType;

$(function(){
    $.ajaxSetup({ cache: false });
    getSessionUser();
    var url=location.search;
    console.log(url);
    var type=GetQueryString("type");
    globalType = type;
    console.log("globalType    " + globalType);
    createTableByType(type);
    tablePaging(currentUrl);
    searchPaging(currentUrl);

});

function GetQueryString(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  decodeURI(r[2]); return null;
}

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



function createTableByType(type) {
    if (type == 5) {
        currentUrl = "/historydocument/list";
        exportFileName = '文书档案';
    } else if (type == 6) {
        currentUrl = "/historydocument/list2";
        exportFileName = '未归档档案';
    } else if (type == 7) {
        currentUrl = "/historydocument/list3";
        exportFileName = '声像档案';
    } else if (type == 8) {
        currentUrl = "/historydocument/list4";
        exportFileName = '实物档案';
    }
}

$('#oldFileInfo').on('show.bs.modal', function (event) {

    if ($('#table_oldFile').bootstrapTable('getSelections').length != 1) {
        alert("请选中一个条目!");
        throw "select error";
    } else {
        $('#table_oldFileInfo').bootstrapTable({

            columns: [{
                field: 'type',
                title: '类型',
                align: "center"
            }, {
                field: 'content',
                title: '内容',
                align: "center"
            }],
        });

//条目表格
        var tmid = $('#table_oldFile').bootstrapTable('getSelections')[0].id;
        console.log("tmid          " + tmid);
        $.axx({
            url: "/historydocument/list5",
            type: "GET",
            data: {
                "tmid": tmid
            },
            dataType: "json",
            success: function (json) {
                console.log("tmid", tmid);
                $('#table_oldFileInfo').bootstrapTable('load', json.content);
            }
        });
    }
})
$('#oldFileInfo').on('hidden.bs.modal', function (event) {
    $('#table_oldFileInfo').bootstrapTable('destroy');
    // location.reload(true);
})

$("#downloadInfo").click(function () {
    if ($('#table_oldFile').bootstrapTable('getSelections').length != 1) {
        alert("请选中一个条目!");

    } else {
        var dlid = $('#table_oldFile').bootstrapTable('getSelections')[0].id;
        var dltm = $('#table_oldFile').bootstrapTable('getSelections')[0].tm;
        var reeltype = "historyDocument";
        var form = $("<form>");   //定义一个form表单
        form.attr('style', 'display:none');   //在form表单中添加查询参数
        form.attr('target', '');
        form.attr('method', 'get');
        form.attr('action', "/fileZipDownload1");
        var input1 = $('<input>');
        input1.attr('type', 'hidden');
        input1.attr('name', 'fileId');
        input1.attr('value', dlid);
        var input2 = $('<input>');
        input2.attr('type', 'hidden');
        input2.attr('name', 'fileName');
        input2.attr('value', dltm);
        var input3 = $('<input>');
        input3.attr('type', 'hidden');
        input3.attr('name', 'reelType');
        input3.attr('value', reeltype);
        $('body').append(form);  //将表单放置在web中
        form.append(input1);   //将查询参数控件提交到表单上
        form.append(input2);
        form.append(input3);
        form.submit();   //表单提交
    }
})

var historyCol = [
    {
        field: "status",
        checkbox: true
    }, {
        field: 'tm',
        title: '标题',
        align: "center"
    }, {
        field: 'dh',
        title: '档号',
        align: "center"
    }, {
        field: 'wjxcsj',
        title: '创建时间',
        align: "center"
    }, {
        field: "id",
        title: "id",
        visible: false
    }];
var entityColum = [
    {
        field: "status",
        checkbox: true
    }, {
        field: 'swsm',
        title: '标题',
        align: "center"
    }, {
        field: 'dh',
        title: '档号'
    }, {
        field: 'xcsj',
        title: '时间'
    }, {
        field: "id",
        title: "id",
        visible: false
    }];

function tablePaging(currentUrl) {
    var tableCol;
    var $table;
    if (globalType > 4 && globalType < 8) {
        tableCol = historyCol;
        $table = $('#table_oldFile');
    } else if (globalType == 8) {
        tableCol = entityColum;
        $table = $('#table_oldFile');
    }

    var queryParams = function (params) {
        console.log("pageNumber", params.pageNumber);
        return {
            pageSize: params.pageSize,
            pageNum: params.pageNumber,
        }
    }
    createBootTable($table, currentUrl, queryParams, tableCol);
}

function createBootTable($table, currentUrl, queryParams, column) {
    console.log("$table          "+$table);
    console.log("currentUrl          "+currentUrl);
    console.log("queryParams          "+queryParams);
    console.log("column          "+column);
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
        paginationShowPageGo:true,
        // exportTypes:['excel','pdf','doc'],
        singleSelect: false,
        exportOptions: {
            fileName: exportFileName,  //文件名称设置
            worksheetName: 'sheet1',  //表格工作区名称
            ignoreColumn:['status']
            /*tableName: exportFileName,
            excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],*/
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

function searchPaging(currentUrl) {
    $("#search").keydown(function (event) {
        if (event.keyCode == 13) {
            title = $("#searchGlobleTitle").val();
            filecode = $("#searchGlobleCode").val();
            if ($.trim(title) == "" && $.trim(filecode) == "") {
                alert("请输入至少一个搜索条件");
                return
            }
            var queryParams = function (params) {
                console.log("pageNumber", params.pageNumber);
                return {
                    pageSize: params.pageSize,
                    pageNum: params.pageNumber,
                    "tm": title,
                    "dh": filecode
                }
            }
            createBootTable($('#table_oldFile'), currentUrl, queryParams,
                globalType == 8 ? entityColum : historyCol);
        }
    });
}