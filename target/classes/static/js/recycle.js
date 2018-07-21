
//回收站与移交流程js
var recycleCol = [{"field": "status", "checkbox": true}, {
    field: "Number", title: "序号", halign: 'center', align: 'center',
    formatter: function (value, row, index) {
        var options = $('#recycleTable').bootstrapTable("getOptions");
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
//查看回收站
function showRecycleBin(deptId,type) {
    var url = "/documents/"+deptId+"/recycle/"+type;
    initBootstrapTable('get', $('#recycleTable'), url, recycleCol, sortQueryParams, false, "application/x-www-form-urlencoded", '#recycle');
}

//恢复
function recover() {
    var selectTr = $('#recycleTable').bootstrapTable("getSelections");
    if (selectTr.length < 1) {
        alert("请选择一条或多条记录");
        throw "check error";
    }
    var ids = $.map(selectTr, function (obj) {
        return obj.id;
    })
    $.axx({
        type:'put',
        url:'/documents/recover',
        data:{ids:ids},
        success:function (json) {
            $('#recycleTable').bootstrapTable('refresh');
        }

    })
}

//删除
function recycleRemove() {
    var selectTr = $('#recycleTable').bootstrapTable("getSelections");
    if (selectTr.length < 1) {
        alert("请选择一条或多条记录");
        throw "check error";
    }

    var confirm = window.confirm("您确定要删除所有您选定的文件吗");
    if (!confirm)
        return;

    var ids = $.map(selectTr, function (obj) {
        return obj.id;
    })
    $.axx({
        type:'put',
        url:'/documents/remove',
        data:{ids:ids},
        success:function (json) {
            $('#recycleTable').bootstrapTable('refresh');
        }

    })
}

window.turnTODocEvents = {
    'click .remove': function (e, value, row, index) {
        $('#turnFormDoc').bootstrapTable('remove', {
            field: 'id',
            values: [row.id]
        });
    }
};

window.turnTORecEvents = {
    'click .remove': function (e, value, row, index) {
        $('#turnFormRec').bootstrapTable('remove', {
            field: 'id',
            values: [row.id]
        });
    }
};

var turnOTDocCol = [{"field": "status", "checkbox": true}, {
    field: "Number", title: "序号", halign: 'center', align: 'center',
    formatter: function (value, row, index) {
        var options = $('#turnFormDoc').bootstrapTable("getOptions");
        return (options.pageNumber - 1) * (options.pageSize) + index + 1;
    }, width: "5%"
}, {
    field: 'operate',
    title: '操作',
    align: 'center',
    halign: 'center',
    valign: 'middle',
    width: "10%",
    events: turnTODocEvents,
    formatter: function (value, row, index) {
        return [
            '<a class="remove" href="javascript:void(0)" title="Remove">',
            '<i class="glyphicon glyphicon-remove"></i>',
            '</a>'
        ].join('');
    }
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

var turnOTRecCol = [{"field": "status", "checkbox": true}, {
    field: "Number", title: "序号", halign: 'center', align: 'center',
    formatter: function (value, row, index) {
        var options = $('#turnFormRec').bootstrapTable("getOptions");
        return (options.pageNumber - 1) * (options.pageSize) + index + 1;
    }, width: "5%"
}, {
    field: 'operate',
    title: '操作',
    align: 'center',
    halign: 'center',
    valign: 'middle',
    width: "10%",
    events: turnTORecEvents,
    formatter: operateFormatter
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
//移交流程
var turnOverCol = [{"field": "status", "checkbox": true},{
    field: "Number", title: "序号", halign: 'center', align: 'center',
    formatter: function (value, row, index) {
        var options = $('#turnOverTable').bootstrapTable("getOptions");
        return (options.pageNumber - 1) * (options.pageSize) + index + 1;
    }, width: "5%"
}, {"field": "title", "title": "题名", width: "50%"},{field:"deliverUser",title:"移交人"},{field:"deliverDept.name",title:"移交部门"},
    {field:"receiveDept.name",title:"接收部门"},{field:"created_dt",title:"移交时间"},{field:"description",title:"备注"}];

var turnOverToMeCol = [{"field": "status", "checkbox": true},{
    field: "Number", title: "序号", halign: 'center', align: 'center',
    formatter: function (value, row, index) {
        var options = $('#turnOverTomeTable').bootstrapTable("getOptions");
        return (options.pageNumber - 1) * (options.pageSize) + index + 1;
    }, width: "5%"
}, {"field": "title", "title": "题名", width: "50%"},{field:"deliverUser",title:"移交人"},{field:"deliverDept.name",title:"移交部门"},
    {field:"receiveDept.name",title:"接收部门"},{field:"created_dt",title:"移交时间"},{field:"description",title:"备注"}];

var turnOverToMeEndCol = [{"field": "status", "checkbox": true},{
    field: "Number", title: "序号", halign: 'center', align: 'center',
    formatter: function (value, row, index) {
        var options = $('#turnOverTomeEndTable').bootstrapTable("getOptions");
        return (options.pageNumber - 1) * (options.pageSize) + index + 1;
    }, width: "5%"
}, {"field": "title", "title": "题名", width: "50%"},{field:"deliverUser",title:"移交人"},{field:"deliverDept.name",title:"移交部门"},
    {field:"receiveDept.name",title:"接收部门"},{field:"created_dt",title:"移交时间"},{field:"description",title:"备注"}];

var selectTODocsCol = [{"field": "status", "checkbox": true}, {
    field: "Number", title: "序号", halign: 'center', align: 'center',
    formatter: function (value, row, index) {
        var options = $('#selectTODocs').bootstrapTable("getOptions");
        return (options.pageNumber - 1) * (options.pageSize) + index + 1;
    }, width: "5%"
},  {field: "dataSource", title: "数据源"}, {field: "fileNum", title: "档号", width: "20%"}, {
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

var selectTORecsCol = [{"field": "status", "checkbox": true}, {
    field: "Number", title: "序号", halign: 'center', align: 'center',
    formatter: function (value, row, index) {
        var options = $('#selectTORecs').bootstrapTable("getOptions");
        return (options.pageNumber - 1) * (options.pageSize) + index + 1;
    }, width: "5%"
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

var TOInfoDocsCol = [{"field": "status", "checkbox": true}, {
    field: "Number", title: "序号", halign: 'center', align: 'center',
    formatter: function (value, row, index) {
        return index + 1;
    }, width: "5%"
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

var TOInfoDocsCol_1 = [{"field": "status", "checkbox": true}, {
    field: "Number", title: "序号", halign: 'center', align: 'center',
    formatter: function (value, row, index) {
        return index + 1;
    }, width: "5%"
}, {field: "dataSource", title: "数据源"}, {field: "oldFileNum", title: "原档号", width: "20%"},{field: "newFileNum", title: "移交后档号", width: "20%"}, {
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

var TOInfoRecsCol = [{"field": "status", "checkbox": true}, {
    field: "Number", title: "序号", halign: 'center', align: 'center',
    formatter: function (value, row, index) {
        return  index + 1;
    }, width: "5%"
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

var TOInfoRecsCol_1 = [{"field": "status", "checkbox": true}, {
    field: "Number", title: "序号", halign: 'center', align: 'center',
    formatter: function (value, row, index) {
        return  index + 1;
    }, width: "5%"
}, {field: "oldFileNum", title: "原档号", sortable: true},{field: "newFileNum", title: "移交后档号", sortable: true},
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
//移交切换
$('#turnOverLi').on('click','li',function(){
    var a=$(this).attr("href");
    $(a).removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
});
//协查发起表单
function showSearchHelpStart() {
    $('#searchHelpStart').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
    $('#searchHelpStart input[name=applyUser]').val(username);
    $('#searchHelpStart input[name=dept]').val(deptName);
    $('#searchHelpStart input[name=applyId]').val(loginName);
    getAssistAdmin();
}

//由我发起
function getTurnOverFromMe1() {
    showCreateTurnOverButton();
    var url = "/flowFormDelivers";
    initBootstrapTable('get', $('#turnOverTable'), url, turnOverCol, queryparams, false, "application/x-www-form-urlencoded", '#turnOverToolbar');
}

//由我审批（未审批）
function getTurnOverToMe1() {
    var url = "/flowFormDelivers/01/process";
    initBootstrapTable('get', $('#turnOverTomeTable'), url, turnOverToMeCol, queryparams, false, "application/x-www-form-urlencoded", '#turnOverToolbar2');
}

//由我审批（已审批）
function getTurnOverToMeEnd1() {
    var url = "/flowFormDelivers/02/process";
    initBootstrapTable('get', $('#turnOverTomeEndTable'), url, turnOverToMeEndCol, queryparams, false, "application/x-www-form-urlencoded");
}

//发起移交 部门到部门 表单
function showTurnOverFormForD2D() {
    $('#turnOverForm')[0].reset();
    $('#TOd2d').removeClass("hidden");
    $('#TOd2c').addClass("hidden");
    $('#receiveDept').removeAttr("disabled");
    showTurnOverForm(1);
    $('#turnOverSubmit').unbind('click').on('click',function (e) {
        createTurnOverForD2d();
    });
}

//发起移交 部门到公司 表单
function showTurnOverFormForD2C() {
    $('#turnOverForm')[0].reset();
    $('#TOd2c').removeClass("hidden");
    $('#TOd2d').addClass("hidden");
    $('#receiveDept').val('KYA001');
    showTurnOverForm(2);
    $('#turnOverSubmit').unbind('click').on('click',function (e) {
        createTurnOverForD2c();
    });
}

//发起移交 公司到公司 表单
function showTurnOverFormForC2C() {
    $('#turnOverForm')[0].reset();
    $('#TOd2c').addClass("hidden");
    $('#TOd2d').addClass("hidden");
    $('#receiveDept').val('KYA001');
    showTurnOverByC2cCol();
    $('#receiveDept').attr("disabled","disabled");
    $('#turnOverSubmit').unbind('click').on('click',function (e) {
        createTurnOverForC2c();
    });
}

//移交发起下拉按钮的隐藏
function showCreateTurnOverButton() {
    if(deptId==KMHK_DEPT_ID){
        $('#turnOverLi1').addClass("hidden");
        $('#turnOverLi2').addClass("hidden");
        $('#turnOverLi3').removeClass("hidden");
    }else {
        $('#turnOverLi1').removeClass("hidden");
        $('#turnOverLi2').removeClass("hidden");
        $('#turnOverLi3').addClass("hidden");
    }
}

//显示发起 公司到公司 移交表单页面
function showTurnOverByC2cCol() {
    $('#turnOverForm').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
    $('#turnOverForm input[name="deliverUser"]').val(username);
    $('#turnOverForm input[name="deliverDeptId"]').val(deptId);
    $('#deliverDept').val(deptName);
    createC2CTODocTable();
    createC2CTORecTable();
}

//显示发起移交表单页面
function showTurnOverForm(type) {
    $('#turnOverForm').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
    $('#turnOverForm input[name="deliverUser"]').val(username);
    $('#turnOverForm input[name="deliverDeptId"]').val(deptId);
    $('#deliverDept').val(deptName);
    createTODocTable();
    createTORecTable();
    getDeptInfoByDsc(type);
}

//件级档案table
function createTODocTable() {
    $('#turnFormDoc').bootstrapTable('destroy');
    $('#turnFormDoc').bootstrapTable({columns: turnOTDocCol});
}

//D2c件级档案table
function createC2CTODocTable() {
    $('#turnFormDoc').bootstrapTable('destroy');
    $('#turnFormDoc').bootstrapTable({columns: turnOTDocColForC2c,editable:true});
}

//卷级档案table
function createTORecTable() {
    $('#turnFormRec').bootstrapTable('destroy');
    $('#turnFormRec').bootstrapTable({columns: turnOTRecCol});
}

//D2c卷级档案table
function createC2CTORecTable() {
    $('#turnFormRec').bootstrapTable('destroy');
    $('#turnFormRec').bootstrapTable({columns: turnOTRecColForC2c,editable:true});
}

$('#toDocModal').on('show.bs.modal',function (event) {
    var button = $(event.relatedTarget);
    var recipient = button.data('whatever');
    //recipient  1（移交调用） ,2(销毁调用）
    if(recipient==1){
        $('#toDocModalSubmit').unbind('click').on('click',function (e) {
            addDocsToTO();
        })
    }else if(recipient==2){
        $('#toDocModalSubmit').unbind('click').on('click',function (e) {
            addDocsToDes();
        })
    }
    var url = "/documents/page/" + deptId + "/type/3";
    initBootstrapTable('get', $('#selectTODocs'), url, selectTODocsCol, sortQueryParams, false, "application/x-www-form-urlencoded");

})

//件级档案搜索（移交流程中）
function toDocModalSearch() {
    url = "/documents/sortSearch/" + deptId + "/type/" + 3;
    initBootstrapTable('post', $('#selectTODocs'), url, selectTODocsCol, getSearchQueryParams($('#toDocModalForm')), false, 'application/json;charset=utf-8');
}

//卷积档案搜索（移交流程中）
function toRecModalSearch() {
    url = "/records/sortSearch/" + deptId;
    initBootstrapTable('post', $('#selectTORecs'), url, selectTORecsCol, getRecordSearchParams($('#toRecModalForm')), false, 'application/json;charset=utf-8');
}

$('#toDocModalForm select').on('change', function () {
    toDocModalSearch();
})

$('#toRecModalForm select').on('change',function (e) {
    toRecModalSearch();
})

$('#toRecModal').on('show.bs.modal',function (event) {
    $('#toRecModalForm')[0].reset();
    url = "/records/sortSearch/" + deptId;
    initBootstrapTable('post', $('#selectTORecs'), url, selectTORecsCol, getRecordSearchParams($('#toRecModalForm')), false, 'application/json;charset=utf-8');
    var button = $(event.relatedTarget);
    var recipient = button.data('whatever');
    if(recipient==1){
        $('#toRecModalSubmit').unbind('click').on('click',function (e) {
            addRecsToTO();
        })
    }else if(recipient==2){
        $('#toRecModalSubmit').unbind('click').on('click',function (e) {
            addRecsToDes();
        })
    }
})

function getToPerModal() {
    getAssignee($('#selectTOPers'), deptId);
    $('#toPerModal').modal('show');
}

function getTurnOverAsker(deptId) {
    getAssignee($('#turnOverAskTable'), deptId);
    $('#flowNodeSelect').modal('show');
}

function getTurnOverManager($select,deptId) {
    $select.empty();
    $.axx({
        type:'get',
        url:'/flowFormDeliver/'+deptId+'/manager',
        data:{},
        success:function (json) {
            var users = json.content;
            for( var i=0;i<users.length;i++){
                $select.append('<option value="'+users[i].id+'">'+users[i].name+'</option>');
            }
        }
    });
}

function getAssignee($table,deptId) {
    var column = [{"field": "status", "checkbox": true}, {"field": "name", "title": "用户名"}, {
        "field": "id",
        "title": "id"
    }];
    $table.bootstrapTable('destroy');
    $table.bootstrapTable({
        search: true, pagination: true, pageSize: 10, editable: true, clickToSelect: true,
        columns: column, singleSelect : true
    });
    getDestroyUserByDept($table, deptId);
    // $modal.modal('show');
}



function extractedAddToTO(oldData, array) {
    var oldIds = $.map(oldData, function (obj) {
        return obj.id;
    });
    var newArray = [];
    for (var i = 0; i < array.length; i++) {
        if ($.inArray(array[i].id, oldIds) == -1) {
            newArray.push(array[i]);
        }
    };
    return newArray;
}

//添加件级档案
function addDocsToTO() {
    var array = $('#selectTODocs').bootstrapTable("getSelections");
    var oldData = $('#turnFormDoc').bootstrapTable("getData");
    var newArray = extractedAddToTO(oldData, array);
    getDuplicateInTurnOver('documents',newArray,$('#toDocModal'),$('#turnFormDoc'),$('#selectTODocs'));
};

//添加卷级档案
function addRecsToTO() {
    var array = $('#selectTORecs').bootstrapTable("getSelections");
    var oldData = $('#turnFormRec').bootstrapTable("getData");
    var newArray = extractedAddToTO(oldData, array);
    getDuplicateInTurnOver('record',newArray,$('#toRecModal'),$('#turnFormRec'),$('#selectTORecs'));
}

//判断档案是否已在移交流程中
function getDuplicateInTurnOver(fileType,newArray,$modal,$form,$table) {
    if(newArray==null||newArray.length==0) return;
    var docIds = $.map(newArray,function (obj) {
        return obj.id;
    });
    $.axx({
        type:'get',
        url:'/flowFormDeliver/'+fileType+'/duplicate',
        data:{docIds:docIds},
        success:function (json) {
            var dupIds = json.content;
            var dupArray = $.map(newArray,function (obj) {
                if ($.inArray(obj.id, dupIds) !=-1) {
                    return obj;
                }
            });
            var endArray = $.map(newArray, function (obj) {
                if ($.inArray(obj.id, dupIds) ==-1) {
                    return obj;
                }
            });
            console.log(dupIds);
            console.log(dupArray);
            console.log(endArray)
            if(dupArray!=null&&dupArray.length>0){
                $form.bootstrapTable('append',endArray);
                $table.bootstrapTable('uncheckAll');
                $table.bootstrapTable('checkBy', {field: 'id', values: dupIds});
                var dupName = $.map(dupArray, function (obj) {
                    return obj.title;
                }).join(',');
                console.log(dupName);
                alert(dupName+" 已在移交流程中");
            }else{
                $modal.modal("hide");
                $form.bootstrapTable('append',endArray);
                $table.bootstrapTable('uncheckAll');
            }
        }
    });
}

//添加审批人
function addPerToTO() {
    var array = $('#selectTOPers').bootstrapTable("getSelections");
    $('#TOLeaderName').val(array[0].name);
    $('#TOLeaderId').val(array[0].id);
    $('#toPerModal').modal('hide');
}

//点击部门列表时 获取部门所有信息
function getDeptInfoByDsc(type) {
    var description = $('#receiveDept').val();
    $.axx({
        type: 'get',
        url: '/depts/' + description + '/dsc',
        data:{},
        success:function (json) {
            $('#turnOverForm input[name="receiveDeptId"]').val(json.content.id);
            if(type==2){
                $('#receiveDept').attr("disabled","disabled");
                getTurnOverManager($('#managerForD2C'), $('#turnOverForm input[name=receiveDeptId]').val());
            }
        }
    });
}

$('#turnOverForm select').on('change',function () {
    getDeptInfoByDsc(1);
})

function createTurnOverForD2d(){
    var assigneeId = $('#TOLeaderId').val();
    if(assigneeId.trim()==''){
        alert("请选择审批人");
        return;
    }
    createTurnOver(assigneeId);
}

function createTurnOverForD2c() {
    var assigneeId = $('#managerForD2C').val();
    if(assigneeId.trim()==''){
        alert("请选择公司管理员");
        return;
    }
    createTurnOver(assigneeId);
}

function createTurnOverForC2c() {
    var deliver = $('#turnOverForm').serializeJson();
    console.log(deliver);
    var docArray = $("#turnFormDoc").bootstrapTable("getData");
    var revArray = $("#turnFormRec").bootstrapTable("getData");
    if(docArray.length==0&&revArray.length==0){
        alert("请选择要移交的文件");
        return;
    }
    deliver.documentsList=docArray;
    deliver.recordList=revArray;
    if(docArray.length>0){
        for(var i=0;i<docArray.length;i++){
            var newReserveLocation = docArray[i].newReserveLocation;
            if(newReserveLocation==null||newReserveLocation.trim()==''){
                alert("请填写新存放位置");
                return;
            }
        }
    }
    if(revArray.length>0){
        for(var j=0;j<revArray.length;j++){
            var newReserveLocation = revArray[j].newReserveLocation;
            if(newReserveLocation==null||newReserveLocation.trim()==''){
                alert("请填写新存放位置");
                return;
            }
        }
    }
    $.axx({
        type: 'post',
        url: "/flowFormDelivers/c2c",
        data: JSON.stringify(deliver),
        contentType: 'application/json;charset=utf-8',
        success:function (json) {
            alert("发起移交成功");
            $("#turnOver").removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
            getTurnOverFromMe1();
        }
    });
}

//发起移交
function createTurnOver(assigneeId) {
    var deliver = $('#turnOverForm').serializeJson();
    var deliverDeptId = deliver.deliverDeptId;
    var receiveDeptId = deliver.receiveDeptId;
    if(deliverDeptId==receiveDeptId){
        alert("不能移交到本部门下");
        return;
    }
    console.log(deliver);
    var docArray = $("#turnFormDoc").bootstrapTable("getData");
    var docIds = $.map(docArray,function (obj) {
        return obj.id;
    });
    var revArray = $("#turnFormRec").bootstrapTable("getData");
    var revIds = $.map(revArray,function (obj) {
        return obj.id;
    });
    if(docArray.length==0&&revArray.length==0){
        alert("请选择要移交的文件");
        return;
    }

    deliver.docIds = docIds;
    deliver.revIds = revIds;
    deliver.assigneeId = assigneeId;
    var data = {flowFormAssist: deliver, docIds: docIds, revIds: revIds};
    $.axx({
        type:'post',
        url:'/flowFormDelivers',
        data:deliver,
        success:function (json) {
            alert("发起移交成功");
            $("#turnOver").removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
            getTurnOverFromMe1();
        }
    })
}

// 发起移交  返回
function  goBackInTO() {
    $("#turnOver").removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
}

//查看信息
/*function showTurnOverInfo() {
    $('#turnOverForm input').attr("readOnly", "readOnly");
    $('#turnOverForm select').attr("disabled", "disabled");
    $('#turnOverForm textarea').attr("readOnly", "readOnly");
    $('#TOLeaderName').addClass("hidden");
    $('#assignPer').addClass("hidden");
    $('#turnOverDocs button').addClass("hidden");
    $('#turnOverDocs label').removeClass("hidden");
    $('#turnOverRecs button').addClass("hidden");
    $('#turnOverRecs label').removeClass("hidden");
}*/

function getDeliverInfo(flowId) {
    $.axx({
        type:'get',
        url:'/flowFormDeliver/'+flowId+'/detail',
        data:{},
        success:function (json) {
            var flowFormDeliver = json.content;
            var documentsList = flowFormDeliver.documentsList;
            var recordList = flowFormDeliver.recordList;
            if(documentsList.length!=0){
                $('#TOInfoBody1').removeClass("hidden");
                $('#TOInfoDocs').bootstrapTable('load', documentsList);
            }else {
                $('#TOInfoBody1').addClass("hidden");
            };
            if(recordList.length!=0){
                $('#TOInfoBody2').removeClass("hidden");
                $('#TOInfoRecs').bootstrapTable('load', recordList);
            }else{
                $('#TOInfoBody2').addClass("hidden");
            };
        }
    })
}

function objToTurnOverForm1(goods) {
    var deliverDept = goods.deliverDept;
    var receiveDept = goods.receiveDept;
    var obj ={title:goods.title,deliverUser:goods.deliverUser,deliverOffice:goods.deliverOffice,
    deliverDept:deliverDept.name,receiveDept:receiveDept.name,description:goods.description};
    for (var name in obj) {
        if (obj.hasOwnProperty(name)) {
            $("#turnOverEnd input[name='" + name + "']:not(:file)").val(obj[name]);
            $("#turnOverEnd textarea[name='" + name + "']").val(obj[name]);
        }
    }
}

$('#turnOverInfo').on('show.bs.modal',function (event) {
    var button = $(event.relatedTarget);
    var recipient = button.data('whatever');
    var select;
    if(recipient==1)
        select= checkVaild($('#turnOverTable'));
    else if(recipient==2)
        select=checkVaild($('#turnOverTomeEndTable'));
    $('#TOInfoDocs').bootstrapTable({columns: TOInfoDocsCol_1});
    $('#TOInfoRecs').bootstrapTable({columns: TOInfoRecsCol_1});
    getDeliverInfo(select[0].flowId);
})

function initTurnOverToolbar(row) {
    $('#TOE_status').unbind('click').on('click',function () {
        getFlowStatus(row.flowId);
        $('#flow-status').modal('show');
    })
    $('#TOE_ok').unbind('click').on('click',function (e) {
        $('#shenpiyijian1').modal('show');
        $('#flow-modal-submit').unbind('click').on('click',function (e) {
            endTurnOverFlow(row,'ACCEPT');
        })
    });
    $('#TOE_no').unbind('click').on('click',function (e) {
        $('#shenpiyijian1').modal('show');
        $('#flow-modal-submit').unbind('click').on('click',function (e) {
            endTurnOverFlow(row,'REJECT');
        })
    });
    $('#TOE_ask').unbind('click').on('click',function (e) {
        var receiveDeptId = row.receiveDeptId;
        getTurnOverAsker(receiveDeptId);
        getTurnOverManager($('#managerForTO'),receiveDeptId);
        $('#TOAskSubmit').unbind('click').on('click',function (e) {
            initiateTurnOverAsk(row);
        })
    })
}

$('#turnOverTomeTable').on('dbl-click-row.bs.table',function (e,row, $element, field) {
    showToolbarByUserInTO(row);
    $('#turnOverEnd').removeClass('hidden').hide().fadeIn(500).siblings().addClass("hidden");
    createTOEndDocsAndRecs();
    getDeliverInfoForEnd(row.flowId);
    initTurnOverToolbar(row);
    $('#TOE_back').unbind('click').on('click',function (e) {
        $('#turnOverTome').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
    });
});

//根据当前用户在流程中的角色来处理toolbar的显示
function showToolbarByUserInTO(obj) {
    var createdBy = obj.flows.createdBy;
    console.log("createdBy:" + createdBy);
    if(usrId==createdBy){
        $('#TOE_ok').addClass("hidden");
        $('#TOE_ask').removeClass("hidden");
    }else{
        $('#TOE_ask').addClass("hidden");
        $('#TOE_ok').removeClass("hidden");
    }
}

function initiateTurnOverAsk(obj) {
    var receiveLeader = checkVaild($('#turnOverAskTable'));
    var receiveManager = $('#managerForTO').val();
    if(receiveManager==null){
        alert("档案接收管理员不能为空");
        return ;
    }
    var managers = $('#managerForTO option').map(function () {
        return $(this).val();
    }).get();
    var receiveLeaderId = receiveLeader[0].id+'';
    var isInArray = $.inArray(receiveLeaderId,managers);
    if(isInArray!=-1){
        alert("部门领导不能选择部门管理员");
        return;
    }
    var data = {flowId:obj.flowId,leader:receiveLeader[0].id,receiveUser:receiveManager};
    $.axx({
        type:'post',
        url:'/flowFormDelivers/process',
        data:data,
        success:function (json) {
            $('#flowNodeSelect').modal('hide');
            getTurnOverToMeEnd1();
            $('#turnOverTomeEnd').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
        }
    })
}

function getDeliverInfoForEnd(flowId) {
    $.axx({
        type: 'get',
        url: '/flowFormDeliver/' + flowId + '/detail',
        data: {},
        success: function (json) {
            var flowFormDeliver = json.content;
            objToTurnOverForm1(flowFormDeliver);
            var documentsList = flowFormDeliver.documentsList;
            var recordList = flowFormDeliver.recordList;
            if(documentsList.length!=0){
                $('#TOEndLabel1').removeClass("hidden");
                $('#TOEndDocs').bootstrapTable('load', documentsList);
            }else {
                $('#TOEndLabel1').addClass("hidden");
            };
            if(recordList.length!=0){
                $('#TOEndLabel2').removeClass("hidden");
                $('#TOEndRecs').bootstrapTable('load', recordList);
            }else{
                $('#TOEndLabel2').addClass("hidden");
            };
        }
    })
}


function createTOEndDocsAndRecs() {
    var TOEndDocsCol = [{"field": "status", "checkbox": true}, {
        field: "Number", title: "序号", halign: 'center', align: 'center',
        formatter: function (value, row, index) {
            var options = $('#TOEndDocs').bootstrapTable("getOptions");
            return (options.pageNumber - 1) * (options.pageSize) + index + 1;
        }, width: "5%"
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
    $('#TOEndDocs').bootstrapTable({columns: TOEndDocsCol});
    var TOEndRecsCol = [{"field": "status", "checkbox": true}, {
        field: "Number", title: "序号", halign: 'center', align: 'center',
        formatter: function (value, row, index) {
            var options = $('#TOEndRecs').bootstrapTable("getOptions");
            return (options.pageNumber - 1) * (options.pageSize) + index + 1;
        }, width: "5%"
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
    $('#TOEndRecs').bootstrapTable({columns: TOEndRecsCol});
}


//移交流程结束
function endTurnOverFlow(row,action) {
    var flowId = row.flowId;
    var deptId = row.receiveDeptId;
    var description = $('#flow-modal-description').val();
    var data = {flowId:flowId,deptId:deptId,description:description,action:action};
    $.axx({
        type:'post',
        url:"/flowFormDeliver/endSwitch",
        data:data,
        success:function (json) {
            alert("审批成功");
            $('#flow-modal-description').val("");
            $('#shenpiyijian1').modal("hide");
            getTurnOverToMeEnd1();
            $('#turnOverTomeEnd').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
        }
    })
}

//公司到公司
var turnOTDocColForC2c = [{"field": "status", "checkbox": true}, {
    field: "Number", title: "序号", halign: 'center', align: 'center',
    formatter: function (value, row, index) {
        var options = $('#turnFormDoc').bootstrapTable("getOptions");
        return (options.pageNumber - 1) * (options.pageSize) + index + 1;
    }, width: "5%"
}, {
    field: 'operate',
    title: '操作',
    align: 'center',
    halign: 'center',
    valign: 'middle',
    width: "10%",
    events: turnTODocEvents,
    formatter: function (value, row, index) {
        return [
            '<a class="remove" href="javascript:void(0)" title="Remove">',
            '<i class="glyphicon glyphicon-remove"></i>',
            '</a>'
        ].join('');
    }
}, {field: "fileNum", title: "档号", width: "20%"},{"field": "title", "title": "题名", width: "50%"},
    {field:"reserveLocation","title":"原存放位置"},{field:"newReserveLocation","title":"新存放位置", editable: {
        type: 'text',
        validate: function (v) {
            if (!v) return '新存放位置不能为空';
        }
    }}];

var turnOTRecColForC2c = [{"field": "status", "checkbox": true}, {
    field: "Number", title: "序号", halign: 'center', align: 'center',
    formatter: function (value, row, index) {
        var options = $('#turnFormRec').bootstrapTable("getOptions");
        return (options.pageNumber - 1) * (options.pageSize) + index + 1;
    }, width: "5%"
}, {
    field: 'operate',
    title: '操作',
    align: 'center',
    halign: 'center',
    valign: 'middle',
    width: "10%",
    events: turnTORecEvents,
    formatter: function (value, row, index) {
        return [
            '<a class="remove" href="javascript:void(0)" title="Remove">',
            '<i class="glyphicon glyphicon-remove"></i>',
            '</a>'
        ].join('');
    }
}, {field: "fileNum", title: "档号", width: "20%"},{"field": "title", "title": "题名", width: "50%"},
    {field:"reserveLocation","title":"原存放位置"},{field:"newReserveLocation","title":"新存放位置", editable: {
        type: 'text',
        validate: function (v) {
            if (!v) return '新存放位置不能为空';
        }
    }}];





