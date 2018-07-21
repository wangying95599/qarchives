window.operateEvents3 = {
    'click #arrangLog': function (e, value, row, index) {
        accessLogObj = {strId: row.documentLocalId, objId: row.id, objType: "文档"};
        extractShowLog(row.documentLocalId, row.id, "文档");
    },
    'click #arrangeUpdate': function (e, value, row, index) {
        console.log("entityNum:   " + row.entityNum)
        $('#file input').val('');
        $('#fileCodeSpan').addClass("hidden");
        $('#file input[name=archiveYear]').attr("readonly", "readonly");
        $('#file select[name=archiveType]').attr("disabled", "disabled");
        $('#file select[name=reserveDuration]').attr("disabled", "disabled");
        $('#fileSave').addClass('hidden');
        objToForm(row);
        $('#modifySubmit').unbind('click').on('click', function (event) {
            extractModifySubmit(row, $('#arrangedTable'), 1);//1.表示归档 即arrange_flag 变为true 2.表示save arrange_flag仍为false
        });
        $('#file').modal('show');
    },
    'click #arrangeDownLoad': function (e, value, row, index) {
        importantValid(row);
        window.location = "/fileZipDownload1?reelType=document" + "&fileName=" + encodeURI(encodeURI(row.fileName)) +
            "&fileId=" + row.documentLocalId;
    }
}

window.operateEvents4 = {
    'click #recordLog': function (e, value, row, index) {
        accessLogObj = {strId: row.documentLocalId, objId: row.id, objType: "文档"};
        extractShowLog(null, row.id, "案卷");
    },
    'click #recordUpdate': function (e, value, row, index) {
        if (row.archiveType == 'JJ') {
            $('#constructionForm')[0].reset();
            $('#constructBtn').addClass('hidden');
            $('#constructionForm select[name=archiveType]').attr("disabled", "disabled");
            $('#constructionForm input[name=itemNum]').attr("readonly", "readonly");
            objToFormForConstruction(row);
            $('#constructionSubmit').unbind().on('click', function (event) {
                updateRecord(row, $('#constructionForm'));
            })
            $('#construction').modal('show');
        } else {
            $('#createRecordForm')[0].reset();
            $('#anjuanBtn').addClass('hidden');
            $('#anjuan input[name=archiveYear]').attr("readonly", "readonly");
            $('#anjuan select[name=archiveType]').attr("disabled", "disabled");
            $('#anjuan select[name=reserveDuration]').attr("disabled", "disabled");
            objToFormForanjuan(row);
            $('#anjuanSubmit').unbind('click').on('click', function (event) {
                updateRecord(row, $('#createRecordForm'), 1);
            });
            $('#anjuan').modal('show');
        }
    },
    'click #recordDownLoad': function (e, value, row, index) {
        importantValid(row);
        window.location = "/fileZipDownload1?reelType=record" + "&fileName=" + encodeURI(encodeURI(row.title)) +
            "&fileId=" + row.id;
    },
    'click #recordPlace': function (e, value, row, index) {
        $('#placeModal').modal('show');
        getPlaceTable(row);
        $('#placeSubmit').unbind('click').on('click', function (event) {
            placeToRecord(row);
        })
    }
}

window.operateEvents5 = {
    'click #detailUpdate': function (e, value, row, index) {
        if (row.archiveType != 'SX') {
            $('#detailUpdateForm')[0].reset();
            $('#detailUpdateForm input[name=recordFileNum]').val($('#detailFileNum').html());
            for (var name in row) {
                if (row.hasOwnProperty(name) && name != 'fileNum') {
                    $("#detailUpdateForm input[name='" + name + "']:not(:file)").val(row[name]);
                    $("#detailUpdateForm select[name='" + name + "']").val(row[name]);
                    $("#detailUpdateForm textarea[name='" + name + "']").val(row[name]);
                }
            }
            $('#detailUpdateModal').modal('show');
            $('#detailSubmit').unbind('click').on('click', function (e) {
                updatePlaceDoc(row);
            })
        } else {
            $('#acousticForm')[0].reset();
            $('#acousticForm input[name=recordFileNum]').val($('#detailFileNum').html());
            for (var name in row) {
                if (row.hasOwnProperty(name) && name != 'fileNum') {
                    $("#acousticForm input[name='" + name + "']:not(:file)").val(row[name]);
                    $("#acousticForm select[name='" + name + "']").val(row[name]);
                    $("#acousticForm textarea[name='" + name + "']").val(row[name]);
                }
            }
            ;
            var acousticImage = row.acousticImage;
            console.log(acousticImage);
            for (var name in acousticImage) {
                if (acousticImage.hasOwnProperty(name) && name != 'fileNum') {
                    $("#acousticForm input[name='" + name + "']:not(:file)").val(acousticImage[name]);
                }
            }
            ;
            $('#acousticImage').modal('show');
            $('#acousticSubmit').unbind('click').on('click', function (e) {
                updatePlaceAcoustic(row);
            })
        }

    },
    'click #detailDownLoad': function (e, value, row, index) {
        importantValid(row);
        window.location = "/fileZipDownload1?reelType=document" + "&fileName=" + encodeURI(encodeURI(row.fileName)) +
            "&fileId=" + row.documentLocalId;
    },
    'click #detailLog': function (e, value, row, index) {
        accessLogObj = {strId: row.documentLocalId, objId: row.id, objType: "文档"};
        extractShowLog(row.documentLocalId, row.id, "文档");
    }
}

var fromLocalCol = [{"field": "status", "checkbox": true}, {
    field: "Number", title: "序号", halign: 'center', align: 'center',
    formatter: function (value, row, index) {
        var options = $('#fromLocalTable').bootstrapTable("getOptions");
        return (options.pageNumber - 1) * (options.pageSize) + index + 1;
    }, width: "5%"
}, {field: "appendices", title: "原文数量", halign: 'center', align: 'center', width: "5%"}, {
    field: "beforeNum",
    title: "文件编号",
    halign: 'center',
    align: 'center',
    sortable: true
},
    {"field": "fileName", "title": "题名", width: "50%"}, {field: "dataSource", title: "数据源",sortable:true}, {
        field: "documentCreatime",
        title: "形成时间",
        halign: 'center',
        align: 'center',
        sortable: true
    }];


var fromOACol = [{"field": "status", "checkbox": true}, {
    field: "Number", title: "序号", halign: 'center', align: 'center',
    formatter: function (value, row, index) {
        var options = $('#fromLocalTable').bootstrapTable("getOptions");
        return (options.pageNumber - 1) * (options.pageSize) + index + 1;
    }, width: "5%"
}, {field: "appendices", title: "原文数量", halign: 'center', align: 'center', width: "5%"}, {
    field: "beforeNum",
    title: "文件编号",
    halign: 'center',
    align: 'center',
    sortable: true
},
    {"field": "fileName", "title": "题名", width: "50%"}, {
        field: "documentCreatime",
        title: "形成时间",
        halign: 'center',
        align: 'center',
        sortable: true
    }];


var arrangedCol = [{"field": "status", "checkbox": true}, {
    field: "Number", title: "序号", halign: 'center', align: 'center',
    formatter: function (value, row, index) {
        var options = $('#arrangedTable').bootstrapTable("getOptions");
        return (options.pageNumber - 1) * (options.pageSize) + index + 1;
    }, width: "5%"
}, {
    field: 'operate',
    title: '操作',
    align: 'center',
    halign: 'center',
    valign: 'middle',
    width: "10%",
    events: operateEvents3,
    formatter: arrangedOperateFormatter
}, {field: "dataSource", title: "数据源"}, {field: "fileNum", title: "档号", width: "20%"}, {
    field: "beforeNum",
    title: "文件编号",
    halign: 'center',
    align: 'center',
    sortable: true
}, {"field": "fileName", "title": "题名", width: "50%"}, {field: "responsible", title: "责任者"}, {
    field: "archiveDate",
    title: "归档日期",
    halign: 'center',
    align: 'center',
    sortable: true
}, {field: "importance", title: "密级"},
    {field: "paginalNum", title: "页数"}, {field: "remark", title: "备注"}];

var recordCol = [{"field": "status", "checkbox": true}, {
    field: "Number", title: "序号", halign: 'center', align: 'center',
    formatter: function (value, row, index) {
        var options = $('#recordTable').bootstrapTable("getOptions");
        return (options.pageNumber - 1) * (options.pageSize) + index + 1;
    }, width: "5%"
}, {
    field: 'operate',
    title: '操作',
    align: 'center',
    halign: 'center',
    valign: 'middle',
    width: "10%",
    events: operateEvents4,
    formatter: recordOperateFormatter
}, {field: "fileNum", title: "档号", sortable: true},
    {"field": "title", "title": "题名", width: "40%"}, {field: "responsible", title: "归档人"}, {
        field: "archiveDate",
        title: '归档日期',
        sortable: true
    },
    {field: "importance", title: "密级"}, {
        field: "appendices",
        title: "卷内文件数",
        halign: 'center',
        align: 'center',
        width: "5%"
    }, {field: "remark", title: "备注", width: "15%"}];

var recordDetailCol = [{"field": "status", "checkbox": true}, {
    field: "Number", title: "序号", halign: 'center', align: 'center',
    formatter: function (value, row, index) {
        var options = $('#recordsDetailTable').bootstrapTable("getOptions");
        return (options.pageNumber - 1) * (options.pageSize) + index + 1;
    }, width: "5%"
}, {
    field: 'operate',
    title: '操作',
    align: 'center',
    halign: 'center',
    valign: 'middle',
    width: "10%",
    events: operateEvents5,
    formatter: detailOperateFormatter
}, {field: "dataSource", title: "数据源"}, {field: "insideNum", title: "卷内文件号", sortable: true},
    {"field": "fileName", "title": "题名", width: "40%"}, {field: "responsible", title: "责任者"}, {
        field: "archiveDate",
        title: "归档日期",
        halign: 'center',
        align: 'center',
        sortable: true
    }, {field: "importance", title: "密级"},
    {field: "paginalNum", title: "页数"}, {field: "remark", title: "备注", width: "15%"}];

var placeOnFileCol = [{"field": "status", "checkbox": true}, {
    field: "Number", title: "序号", halign: 'center', align: 'center',
    formatter: function (value, row, index) {
        var options = $('#placeTable').bootstrapTable("getOptions");
        return (options.pageNumber - 1) * (options.pageSize) + index + 1;
    }, width: "5%"
}, {"field": "fileName", "title": "题名", width: "50%"}, {
    field: "beforeNum",
    title: "文件编号",
    halign: 'center',
    align: 'center',
    sortable: true
}, {field: "dataSource", title: "数据源"}, {
    field: "documentCreatime",
    title: "形成时间",
    halign: 'center',
    align: 'center',
    sortable: true
}];

function arrangedOperateFormatter() {
    return [
        '<a id="arrangeUpdate" href="javascript:void(0)" title="修改">',
        '<i class="glyphicon glyphicon-pencil"></i>',
        '</a> &nbsp;',
        '<a id="arrangeDownLoad" href="javascript:void(0)" title="下载">',
        '<i class="glyphicon glyphicon-save"></i>',
        '</a> &nbsp;',
        '<a id="arrangLog" href="javascript:void(0)"  title="访问日志">',
        '<i class="glyphicon glyphicon-info-sign"></i>',
        '</a>'
    ].join('');
}

function recordOperateFormatter() {
    return [
        '<a id="recordPlace" href="javascript:void(0)" title="归档">',
        '<i class="glyphicon glyphicon-folder-close"></i>',
        '</a> &nbsp;',
        '<a id="recordUpdate" href="javascript:void(0)" title="修改">',
        '<i class="glyphicon glyphicon-pencil"></i>',
        '</a> &nbsp;',
        '<a id="recordDownLoad" href="javascript:void(0)" title="下载">',
        '<i class="glyphicon glyphicon-save"></i>',
        '</a> &nbsp;',
        '<a id="recordLog" href="javascript:void(0)"  title="访问日志">',
        '<i class="glyphicon glyphicon-info-sign"></i>',
        '</a>'
    ].join('');
}

function detailOperateFormatter() {
    return [
        '<a id="detailUpdate" href="javascript:void(0)" title="修改">',
        '<i class="glyphicon glyphicon-pencil"></i>',
        '</a> &nbsp;',
        '<a id="detailDownLoad" href="javascript:void(0)" title="下载">',
        '<i class="glyphicon glyphicon-save"></i>',
        '</a> &nbsp;',
        '<a id="detailLog" href="javascript:void(0)"  title="访问日志">',
        '<i class="glyphicon glyphicon-info-sign"></i>',
        '</a>'
    ].join('');

}

/*url = "documents/lookrecords/" + row.id + "/type/" + row.archiveType;
    initBootstrapTable('get', $('#recordsDetailTable'), url, recordDetailCol, sortQueryParams, false, "application/x-www-form-urlencoded");
}*/
function initRecDetailTableWithExport(row) {
    $('#recordsDetailTable').bootstrapTable('destroy').bootstrapTable({
        striped:true,
        method:'get',
        toolbar:'#rec_det_toolbar',
        url:"documents/lookrecords/" + row.id + "/type/" + row.archiveType,
        cache:false,
        // undefinedText:"0",
        sidePagination: "server",
        pagination: true,
        pageNumber:1,
        pageSize: 10,
        pageList: [10, 25, 50, 100],
        queryParamsType:'',
        totalField: 'total',
        dataField: 'list',
        clickToSelect: true,
        queryParams:sortQueryParams,
        showRefresh:true,
        showExport: true,
        exportDataType: 'all',
        paginationShowPageGo:true,
        exportTypes:[ 'excel'],  //导出文件类型
        exportOptions:{
            fileName:'案卷内文件表',
            tableName:'案卷内文件表',
            ignoreColumn:[0,2],
            worksheetName: 'sheet1',
            excelstyles: ['background-color', 'color', 'font-size', 'font-weight','border-top']
        },
        responseHandler:function (result) {
            return result.content;
        },
        onLoadError:function (status) {
            sessionout(status);
            return false;
        },
        columns:recordDetailCol,

    });
}


function getFromLocalTable() {
    $('#btn_add').removeClass("hidden");
    // $('#btn_add,#btn_delete').removeClass("hidden");
    $('#fileSearchForm').removeClass("hidden");
    $('#fileSearchForm1').addClass("hidden");
    url = "/documents/page/" + deptId + "/type/1";
    initBootstrapTable('get', $('#fromLocalTable'), url, fromLocalCol, sortQueryParams, false, "application/x-www-form-urlencoded", '#localtoolbar');
}

function getFromOATable() {
    $('#btn_add').addClass("hidden");
    $('#fileSearchForm').addClass("hidden");
    $('#fileSearchForm1').removeClass("hidden");
    url = "/documents/page/" + deptId + "/type/2";
    initBootstrapTable('get', $('#fromLocalTable'), url, fromOACol, sortQueryParams, false, "application/x-www-form-urlencoded", '#localtoolbar');
}

function getArrangedTable() {
    url = "/documents/page/" + deptId + "/type/3";
    initExport('get', $('#arrangedTable'), url, arrangedCol, sortQueryParams, false, "application/x-www-form-urlencoded");
}


function getRecordTable() {
    $('#recordSearchForm')[0].reset();
    url = "/records/sortSearch/" + deptId;
    initBootstrapTable('post', $('#recordTable'), url, recordCol, getRecordSearchParams($('#recordSearchForm')), false, 'application/json;charset=utf-8', '#recordToolbar');
}

function getRecordDetailTable(row) {
    initRecDetailTableWithExport(row);
}

function getPlaceTable(row) {
    /*var importance = row.importance;
    if(importance=="重要")url = "/documents/page/" + deptId + "/type/4";//重要的文档
    else url = "/documents/page/" + deptId + "/type/5";*/
    url = "/documents/page/" + deptId + "/type/4";
    initBootstrapTable('get', $('#placeTable'), url, placeOnFileCol, sortQueryParams, false, "application/x-www-form-urlencoded");
}

$('#placeTable').on('click-row.bs.table', function (e) {
    $('#placeValid').addClass("hidden");
})

function placeToRecord(row) {
    var selectTr = $('#placeTable').bootstrapTable('getSelections');
    if (selectTr.length < 1) {
        $('#placeValid').removeClass('hidden').html("请选择一个或多个文件");
        return;
    }
    for (var i = 0; i < selectTr.length; i++) {
        selectTr[i].archiveType = row.archiveType;
    }
    var url;
    if (row.importance == "重要") url = "/records/archive/" + row.id + "/type/1";
    else url = "/records/archive/" + row.id + "/type/2";
    $.axx({
        type: "POST",
        url: url,
        data: JSON.stringify(selectTr),
        dataType: "json",
        contentType: 'application/json;charset=utf-8',
        success: function (json) {
            // alert("归档成功");

            var dupTitles = json.content;
            if (dupTitles == null || dupTitles.length == 0) {
                extractRecordDetail(row);
                $("#placeModal").modal("hide");
            } else {
                $('#placeTable').bootstrapTable('refresh');
                var warn = dupTitles.join(",") + "   文件已经存在，如需归档，请重命名。"
                $('#placeValid').html(warn).removeClass("hidden");
            }
        }

    })
}

var placeQueryParams = function (params) {
    return {
        pageSize: params.pageSize, //每一页的数据行数，默认是上面设置的10(pageSize)
        pageNum: params.pageNumber,
        sortName: params.sortName,
        sortOrder: params.sortOrder,
    }
}

var sortQueryParams = function (params) {
    return {
        pageSize: params.pageSize, //每一页的数据行数，默认是上面设置的10(pageSize)
        pageNum: params.pageNumber,
        sortName: params.sortName,
        sortOrder: params.sortOrder
    }
}

function getRecordSearchParams($from) {
    var recordSearchParams = function (params) {
        var record = $from.serializeJson();
        var data = {};
        data.pageNum = params.pageNum;
        data.pageSize = params.pageSize;
        data.sortName = params.sortName;
        data.sortOrder = params.sortOrder;
        record.pageSortSearch = data;
        return JSON.stringify(record);
    }
    return recordSearchParams;
}

function getSearchQueryParams($form) {
    var sortSearchQueryParams = function (params) {
        var data = $form.serializeJson();
        console.log(data);
        var ooxx = {};
        ooxx.documents = data;
        ooxx.pageSize = params.pageSize; //每一页的数据行数，默认是上面设置的10(pageSize)
        ooxx.pageNum = params.pageNumber;
        ooxx.sortName = params.sortName;
        ooxx.sortOrder = params.sortOrder;
        return JSON.stringify(ooxx);
    }
    return sortSearchQueryParams;
}

$('#arrangedSearchForm select').on('change', function () {
    arrangeSearch();
})

$('#recordSearchForm select').on('change', function () {
    recordSearch();
})

function recordSearch() {
    url = "/records/sortSearch/" + deptId;
    initBootstrapTable('post', $('#recordTable'), url, recordCol, getRecordSearchParams($('#recordSearchForm')), false, 'application/json;charset=utf-8', '#recordToolbar');
}

function arrangeSearch() {
    url = "/documents/sortSearch/" + deptId + "/type/" + 3;
    initExport('post', $('#arrangedTable'), url, arrangedCol, getSearchQueryParams($('#arrangedSearchForm')), false, 'application/json;charset=utf-8');
}

function docSearch() {
    var data = $('#fileSearchForm').serializeJson();
    var i = 0;
    $.each(data, function (key, val) {
        if (val.trim() == '') i++;
    });
    if (i == 3) {
        alert("请输入至少一个搜索条件");
        return;
    }

    var type = listType == "#fromLocal" ? 1 : listType == "#fromOA" ? 2 : null;
    var col = listType == "#fromLocal" ? fromLocalCol : listType == "#fromOA" ? fromOACol : null;
    url = "/documents/sortSearch/" + deptId + "/type/" + type;
    initBootstrapTable('post', $('#fromLocalTable'), url, col, getSearchQueryParams($('#fileSearchForm')), false, 'application/json;charset=utf-8', '#localtoolbar');
}

function docSearch1() {
    url = "/documents/sortSearch/" + deptId + "/type/" + 2;
    initBootstrapTable('post', $('#fromLocalTable'), url, fromOACol, getSearchQueryParams($('#fileSearchForm1')), false, 'application/json;charset=utf-8', '#localtoolbar');
}

$('#fileSearchForm1 select').on('change',function () {
    docSearch1();
})

//归档时搜索
function placeSearch() {
    /*var data = $('#placeSearchForm').serializeJson();
    var i = 0;
    $.each(data, function (key, val) {
        if (val.trim() == '') i++;
    });
    if (i == 3) {
        // alert("请输入至少一个搜索条件");
        $('#placeValid').html("请输入至少一个搜索条件").removeClass('hidden');
        return;
    }*/
    url = "/documents/sortSearch/" + deptId + "/type/" + 4;
    initBootstrapTable('post', $('#placeTable'), url, placeOnFileCol, getSearchQueryParams($('#placeSearchForm')), false, 'application/json;charset=utf-8');
}

$('#placeSearchForm input').focus(function () {
    $('#placeValid').addClass("hidden");
})
/*function arrangeSearch() {
    var data = $('#arrangedSearchForm').serializeJson();
}*/

$('#file').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget);// 触发事件的按钮
    var recipient = button.data('whatever'); // 解析出data-whatever内容
    //公司级档案著录信息中为移交部门，部门级档案中著录信息为移交科室
    if(deptId==KMHK_DEPT_ID) $('#file_deliver').html("移交部门");
    else $('#file_deliver').html("移交科室");
    if (recipient == 1) {
        var selectTr = $('#fromLocalTable').bootstrapTable("getSelections");
        if (selectTr.length != 1) {
            alert("请选择一条记录");
            throw "check error";
        }
        objToForm(selectTr[0]);
        if (selectTr[0].fileNum != null) {
            $('#fileCodeSpan').addClass("hidden");
            $('#file input[name=archiveYear]').attr("readonly", "readonly");
            $('#file select[name=archiveType]').attr("disabled", "disabled");
            $('#file select[name=reserveDuration]').attr("disabled", "disabled");

        } else {
            $('#fileCodeSpan').removeClass("hidden");
            $('#file input[name=archiveYear]').removeAttr("readonly");
            $('#file select[name=archiveType]').removeAttr("disabled");
            $('#file select[name=reserveDuration]').removeAttr("disabled");
        }
        $('#fileSave').removeClass("hidden");
        $('#file input[name=fondsId]').val(simpleDeptName);
        $('#modifySubmit').unbind('click').on('click', function (event) {
            extractModifySubmit(selectTr[0], $('#fromLocalTable'), 1);
        });
        $('#fileSave').unbind('click').on('click', function (event) {
            extractModifySubmit(selectTr[0], $('#fromLocalTable'), 2);
        })
    }

});

$('#anjuan').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget);// 触发事件的按钮
    var recipient = button.data('whatever'); // 解析出data-whatever内容
    if (recipient == 1) {
        $('#createRecordForm input[name="fondsId"]').val(simpleDeptName);
        $('#anjuanBtn').removeClass("hidden");
        $('#anjuan input[name=archiveYear]').removeAttr("readonly");
        $('#anjuan select[name=archiveType]').removeAttr("disabled");
        $('#anjuan select[name=reserveDuration]').removeAttr("disabled");
        $('#anjuanSubmit').unbind('click').on('click', function (event) {
            createRecord($('#createRecordForm'));
        });
    }
    //公司级档案著录信息中为移交部门，部门级档案中著录信息为移交科室
    if(deptId==KMHK_DEPT_ID){
        $('#rec_deliver_dept').removeClass("hidden");
        $('#rec_deliver_office').addClass("hidden");
    }else{
        $('#rec_deliver_dept').addClass("hidden");
        $('#rec_deliver_office').removeClass("hidden");
    }
})

$('#construction').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget);// 触发事件的按钮
    var recipient = button.data('whatever'); // 解析出data-whatever内容
    if (recipient == 1) {
        $('#constructionForm input[name="fondsId"]').val(simpleDeptName);
        $('#constructBtn').removeClass("hidden");
        $('#constructionForm select[name="archiveType"]').removeAttr("disabled");
        $('#constructionForm input[name=itemNum]').removeAttr("readonly");
        $('#constructionSubmit').unbind('click').on('click', function (event) {
            createRecord($('#constructionForm'));
        });
    }

    //公司级档案著录信息中为移交部门，部门级档案中著录信息为移交科室
    if(deptId==KMHK_DEPT_ID){
        $('#construct_deliver_dept').removeClass("hidden");
        $('#construct_deliver_office').addClass("hidden");
    }else{
        $('#construct_deliver_dept').addClass("hidden");
        $('#construct_deliver_office').removeClass("hidden");
    }
})

//把对象信息写入到form表单中
function objToForm(goods) {
    for (var name in goods) {
        if (goods.hasOwnProperty(name)) {
            $("#file input[name='" + name + "']:not(:file)").val(goods[name]);
            $("#file select[name='" + name + "']").val(goods[name]);
            $("#file textarea[name='" + name + "']").val(goods[name]);
        }
    }
}

function objToFormForanjuan(goods) {
    for (var name in goods) {
        if (goods.hasOwnProperty(name)) {
            $("#createRecordForm input[name='" + name + "']:not(:file)").val(goods[name]);
            $("#createRecordForm select[name='" + name + "']").val(goods[name]);
            $("#createRecordForm textarea[name='" + name + "']").val(goods[name]);
        }
    }
}

function objToFormForConstruction(goods) {
    for (var name in goods) {
        if (goods.hasOwnProperty(name)) {
            $("#constructionForm input[name='" + name + "']:not(:file)").val(goods[name]);
            $("#constructionForm select[name='" + name + "']").val(goods[name]);
            $("#constructionForm textarea[name='" + name + "']").val(goods[name]);
        }
    }
}


$('#file').on('hide.bs.modal', function () {
    $('#archiving').data('bootstrapValidator').resetForm();
})

$('#anjuan').on('hide.bs.modal', function () {
    $('#createRecordForm').data('bootstrapValidator').resetForm();
})

$('#construction').on('hide.bs.modal', function () {
    $('#constructionForm').data('bootstrapValidator').resetForm();
})

$('#newFile').on('hide.bs.modal', function () {
    $('#createFileForm').data('bootstrapValidator').resetForm();
})

$('#acousticImage').on('hide.bs.modal', function () {
    $('#createFileForm').data('bootstrapValidator').resetForm();
})

function extractModifySubmit(documents, $table, type) {
//获取表单对象
    var bootstrapValidator = $('#archiving').data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        var data = $('#archiving').serializeJson();
        /*data["deptId"] = documents.deptId;
        console.log("fileCreatetime:  " + documents.fileCreatetime)
        data["fileCreatetime"] = documents.fileCreatetime;
        data["documentLocalId"] = documents.documentLocalId;
        data["documentCreatime"] = documents.documentCreatime;
        data["userId"] = documents.userId;
        data["dataSource"] = documents.dataSource;
        data["isArchive"] = documents.isArchive;*/
        if (documents.fileNum == null) {
            var fileNumT = data.fileNum;
            var arr = fileNumT.split("-");
            var num = simpleDeptName.split("-").length
            console.log("num:   " + num);
            console.log("num:   2  " + arr[num - 1]);

            if (data.archiveType != arr[num] || data.archiveYear != arr[num + 1] || data.reserveDuration != arr[num + 2]) {
                alert("档号和您输入的信息不一致，请您重新输入");
                return
            }


        }
        if (data["archiveType"] == null) data["archiveType"] = documents.archiveType;
        if (data["reserveDuration"] == null) data["reserveDuration"] = documents.reserveDuration;
        //表单提交的方法、比如ajax提交
        $.axx({
            type: 'post',
            url: 'documents/save/' + documents.id + '/type/' + type,
            data: JSON.stringify(data),
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            success: function (json) {
                if (type == 1) alert("归档成功");
                else if (type == 2) alert("修改成功");
                $('#file').modal('hide');
                $table.bootstrapTable('refresh');
            }
        });
    }
}

//修改案卷中得文件
function updatePlaceDoc(row) {
    var data = JSON.stringify($('#detailUpdateForm').serializeJson());
    $.axx({
        type: 'post',
        url: "documents/place/" + row.id,
        data: data,
        contentType: 'application/json;charset=utf-8',
        success: function (json) {
            $('#recordsDetailTable').bootstrapTable('refresh');
            $('#detailUpdateModal').modal('hide');
        }
    })
}

function updatePlaceAcoustic(row) {
    // var data = JSON.stringify($('#acousticForm').serializeJson());
    var data = $('#acousticForm').serializeJson();
    var acousticImage = {};
    console.log(data.place)
    console.log(row);
    acousticImage["place"] = data.place;
    acousticImage.figure = data.figure;
    acousticImage.photographer = data.photographer;
    acousticImage.photographyTime = data.photographyTime;
    acousticImage.number = data.number;
    acousticImage.leader = data.leader;
    data.acousticImage = acousticImage;
    $.axx({
        type: 'post',
        url: "documents/place/" + row.id,
        data: JSON.stringify(data),
        contentType: 'application/json;charset=utf-8',
        success: function (json) {
            $('#recordsDetailTable').bootstrapTable('refresh');
            $('#acousticImage').modal('hide');
        }
    })
}

//修改案卷
function updateRecord(row, $form, type) {
    var bootstrapValidator = $form.data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        var data = $form.serializeJson();
        data.deptId = row.deptId;
        data.userId = row.userId;
        data.createdDt = row.createdDt;
        data.archiveType = row.archiveType;
        if (type == 1) data.reserveDuration = row.reserveDuration;
        $.axx({
            type: 'put',
            url: '/records/' + row.id,
            data: JSON.stringify(data),
            contentType: 'application/json;charset=utf-8',
            success: function (json) {
                var archiveType = json.content.archiveType;
                if (archiveType == 'JJ') $('#construction').modal('hide');
                else $('#anjuan').modal('hide');
                $form[0].reset();
                $('#recordTable').bootstrapTable('refresh');
            }
        })

    }
}

//创建案卷
function createRecord($form) {
    var bootstrapValidator = $form.data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        $.axx({
            type: 'POST',
            data: $form.serialize(),
            url: "records/" + deptId,
            dataType: 'json',
            success: function (json) {
                alert('创建成功');
                var archiveType = json.content.archiveType;
                if (archiveType == 'JJ') $('#construction').modal('hide');
                else $('#anjuan').modal('hide');
                $form[0].reset();
                // $('#fromLocalTable').bootstrapTable('refresh');
                getRecordTable();
            },
            error: function () {

            }
        });
    }
}


//创建文件
$('#createDocSubmit').on('click', function (event) {
    var bootstrapValidator = $('#createFileForm').data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        $.axx({
            type: 'POST',
            data: $('#createFileForm').serialize(),
            url: "documents/file/" + deptId,
            dataType: 'json',
            success: function (json) {
                $('#newFile').modal('hide');
                alert('创建成功');
                $('#createFileForm input').val('');
                // $('#fromLocalTable').bootstrapTable('refresh');
                getFromLocalTable();
            },
            error: function () {

            }
        });
    }
})

//批量归档
$('#batchFile').on('show.bs.modal', function (event) {
    var selectTr = checksVaild($('#fromLocalTable'));
    $('#batchFileForm input[name=fondsId]').val(simpleDeptName);
})

$('#batchFileSubmit').on('click', function () {

    var bootstrapValidator = $('#batchFileForm').data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        var selectTr = checksVaild($('#fromLocalTable'));
        var ids = $.map(selectTr, function (obj) {
            return obj.id;
        });
        var documents = $('#batchFileForm').serializeJson();
        for (var i = 0; i < selectTr.length; i++) {
            selectTr[i].fondsId = documents.fondsId;
            selectTr[i].archiveType = documents.archiveType;
            selectTr[i].archiveYear = documents.archiveYear;
            selectTr[i].reserveDuration = documents.reserveDuration;
        }

        /* var ooxx = {};
         ooxx.ids = ids;
         ooxx.documents = documents;*/
        $.axx({
            type: 'post',
            url: 'documents/batchSave',
            data: JSON.stringify(selectTr),
            contentType: 'application/json;charset=utf-8',
            success: function () {
                // alert('批量成功');
                $('#fromLocalTable').bootstrapTable('refresh');
                $('#batchFile').modal('hide');
                $('#batchFileForm input').val('');
            }
        })

    }
})

function checksVaild($table) {
    var selectTr = $table.bootstrapTable("getSelections");
    if (selectTr.length < 1) {
        alert("请选择一条或多条记录");
        throw "check error";
    }
    return selectTr;
}

function checkVaild($table) {
    var selectTr = $table.bootstrapTable("getSelections");
    if (selectTr.length != 1) {
        alert("请选择一条记录");
        throw "check error";
    }
    return selectTr;
}

//删除文件
function deleteDocument() {
    var selectTr = checksVaild($('#fromLocalTable'));

    var ids = $.map(selectTr, function (obj) {
        return obj.id;
    })

    var confirm = window.confirm("您确定要删除所有您选定的文件吗");
    if (!confirm)
        return;
    $.axx({
        type: "put",
        data: {ids: ids},
        url: "documents",
        dataType: "json",
        success: function (json) {
            // alert("删除成功");
            $('#fromLocalTable').bootstrapTable('refresh');
        }
    });
}

function extractDocDetail($element) {
    $('#wenJian_detail').removeClass("hidden").siblings().addClass("hidden");
    $('.breadcrumb').append("<li href='#wenJian_detail'>" + $element.fileName +
        "</li>").removeClass("hidden");
    $('.breadcrumb:last').addClass("active").siblings().removeClass("active");
    // listType="#wenJian";
    lookUploadFile($element.documentLocalId, 1);
    documentFile = $element;
}

//双击查看文件（正文/附件）
$('#fromLocalTable').on('dbl-click-row.bs.table', function (row, $element, field) {
    extractDocDetail($element);
});

$('#recordsDetailTable').on('dbl-click-row.bs.table', function (row, $element, field) {
    extractDocDetail($element);
});

$('#arrangedTable').on('dbl-click-row.bs.table', function (row, $element, field) {
    extractDocDetail($element);
});

function extractRecordDetail(row) {
    $('#recordsDetail').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
    $('.breadcrumb').append("<li href='#recordsDetail'>" + row.title +
        "</li>").removeClass("hidden");
    $('.breadcrumb:last').addClass("active").siblings().removeClass("active");
    $('#detailFileNum').html(row.fileNum);
    $('#detailTitle').html(row.title);
    getRecordDetailTable(row);
}

//双击案卷打开文件
$('#recordTable').on('dbl-click-row.bs.table', function (e, row, $element, field) {
    extractRecordDetail(row);
})

//件级档案批量下载
function downloads1() {
    downloads($('#arrangedTable'), "document");
}
//卷级档案批量下载
function downloads2() {
    downloads($('#recordTable'),"record");
}
//卷内文件批量下载
function downloads3() {
    downloads($('#recordsDetailTable'), "document");
}

//批量下载
function downloads($table,reelType){
    var selectTr = $table.bootstrapTable("getSelections");
    if(selectTr.length<1){
        alert("请选择一条或多条记录");
        return;
    }
    var ids = $.map(selectTr, function (obj) {
        return obj.id;
    });
    window.location="/fileZipDownloads?ids=" + ids.toString() + "&reelType="+reelType;
}

$(function () {
    datetimepickerinit($('.completeDate'));
    initDateTimePicker($('.simpleDate'));
    initDateTimePicker($('#batchAchiveYear'));
    initDateTimePicker($('.startYear'));
    initDateTimePicker($('.endYear'));

})

function initDateTimePicker($input) {
    $input.datetimepicker({
        language: 'zh-CN',
        startView: 'decade',
        format: 'yyyy',
        minView: 'decade',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-right"
    }).on('hide show',function (event) {
        event.preventDefault();
        event.stopPropagation();
    }).on('hide',function(event){
        var name = $(this).children('input').attr("name");
        var parents = $(this).parents('form');
        if(parents){
            parents.data('bootstrapValidator').updateStatus(name, 'NOT_VALIDATED', null).validateField(name);
        }
    });
}

$(function () {
    var time = new Date().getFullYear();
    $('#archiveYear input').val(time);
    $('#archiving').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            fileNum: {
                message: '档号错误',
                validators: {
                    notEmpty: {
                        message: '档号不能为空'
                    }
                }
            },
            fondsId: {
                validators: {
                    notEmpty: {
                        message: '全宗号不能为空'
                    }
                }
            },
            archiveYear: {
                validators: {
                    notEmpty: {
                        message: '归档年度不能为空'
                    }
                }
            },
            entityNum: {
                validators: {
                    numeric: {message: '实体份数只能输入数字'}
                }
            },
            appendices: {
                validators: {
                    numeric: {message: '原文数量只能输入数字'}
                }
            },
            paginalNum: {
                validators: {
                    numeric: {message: '页数只能输入数字'}
                }
            }
        }
    });

    $("#createFileForm").bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            title: {
                validators: {
                    notEmpty: {
                        message: '题名不能为空'
                    }
                }
            },
            appendices: {
                validators: {
                    notEmpty: {
                        message: '原文数量不能为空'
                    },
                    numeric: {message: '原文数量只能输入数字'}
                }
            },
            fileCreatetime: {
                validators: {
                    notEmpty: {
                        message: '时间不能为空'
                    }
                }
            },
            beforeNum: {
                validators: {
                    notEmpty: {
                        message: '原文编号不能为空'
                    }
                }
            }
        }
    });

    $('#batchFileForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            fondsId: {
                validators: {
                    notEmpty: {
                        message: '全宗号不能为空'
                    }
                }
            },
            archiveYear: {
                validators: {
                    notEmpty: {
                        message: '归档年度不能为空'
                    }
                }
            },
            archiveType: {
                validators: {
                    notEmpty: {
                        message: '档案种类不能为空'
                    }
                }
            },
            reserveDuration: {
                validators: {
                    notEmpty: {
                        message: '保管期限不能为空'
                    }
                }
            }
        }
    })

    $('#createRecordForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            fileNum: {
                message: '档号错误',
                validators: {
                    notEmpty: {
                        message: '档号不能为空'
                    }
                }
            },
            fondsId: {
                validators: {
                    notEmpty: {
                        message: '全宗号不能为空'
                    }
                }
            },
            title: {
                validators: {
                    notEmpty: {
                        message: '题名不能为空'
                    }
                }
            },
            archiveYear: {
                validators: {
                    notEmpty: {
                        message: '归档年度不能为空'
                    }
                }
            },
            saveNum: {
                validators: {
                    numeric: {message: '数量只能输入数字'}
                }
            },
            appendices: {
                validators: {
                    numeric: {message: '卷内文件数只能输入数字'}
                }
            }
        }
    });
    $('#constructionForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            fileNum: {
                message: '档号错误',
                validators: {
                    notEmpty: {
                        message: '档号不能为空'
                    }
                }
            },
            fondsId: {
                validators: {
                    notEmpty: {
                        message: '全宗号不能为空'
                    }
                }
            },
            title: {
                validators: {
                    notEmpty: {
                        message: '题名不能为空'
                    }
                }
            },
            itemNum: {
                validators: {
                    notEmpty: {
                        message: '项目号不能为空'
                    }
                }
            },
            saveNum: {
                validators: {
                    numeric: {message: '数量只能输入数字'}
                }
            },
            appendices: {
                validators: {
                    numeric: {message: '卷内文件数只能输入数字'}
                }
            }
        }
    });

    $('#acousticForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            number: {
                validators: {
                    numeric: {message: '数量只能输入数字'}
                }
            },
            paginalNum: {
                validators: {
                    numeric: {message: '页数只能输入数字'}
                }
            },
        }

    });

    $('#createLending').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            title: {
                validators: {
                    notEmpty: {
                        message: '标题不能为空'
                    }
                }
            },
            lendingPermission: {
                validators: {
                    notEmpty: {
                        message: '借阅权限不能为空'
                    }
                }
            },
            deadLine: {
                validators: {
                    notEmpty: {
                        message: '截止时间不能为空'
                    }
                }
            },
        }
    });

    $('#updatePwd').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            oldPwd: {
                validators: {
                    notEmpty: {
                        message: '旧密码必填不能为空'
                    },
                }
            },
            newPwd: {
                validators: {
                    notEmpty: {
                        message: '新密码必填不能为空'
                    },
                    regexp: {
                        regexp:'^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$' ,
                        message: '至少8个字符，至少1个大写字母，1个小写字母和1个数字,不能包含特殊字符（非数字字母）'
                    },
                    identical: {
                        field: 'againPwd',
                        message: '新密码和确认密码不一致',
                    }
                }
            },
            againPwd: {
                validators: {
                    notEmpty: {
                        message: '确认密码必填不能为空'
                    },
                    regexp: {
                        regexp:'^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$' ,
                        message: '至少8个字符，至少1个大写字母，1个小写字母和1个数字,不能包含特殊字符（非数字字母）'
                    },
                    identical: {
                        field: 'newPwd',
                        message: '新密码和确认密码不一致',
                    }
                }
            }
        }
    })

});

function optimizeVaild(messageValue) {
    var message = messageValue + "不能为空";
    var notEmpty;
    notEmpty.message = message;
    var validators;
    validators.notEmpty = notEmpty;
    return validators;
}

//首页
function backToHome() {
    getLink();
    getInformation();
    getPicture();
    remainToBeDone();
    $('#home').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
}

$.fn.serializeJson = function () {
    var serializeObj = {};
    var array = this.serializeArray();
    $(array).each(function () {

        if (serializeObj[this.name]) {
            if ($.isArray(serializeObj[this.name])) {
                serializeObj[this.name].push(this.value);
            } else {
                serializeObj[this.name] = [serializeObj[this.name], this.value];
            }
        } else {
            serializeObj[this.name] = this.value;
        }
    });
    return serializeObj;
};