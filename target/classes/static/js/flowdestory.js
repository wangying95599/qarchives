// var deptId = 8;
// var usrId = 15;
<!--<tr>-->
// <!--<th>题名</th>-->
// <!--<th>档号</th>-->
// <!--<th>责任人</th>-->
// <!--<th>发起时间</th>-->
// <!--<th>截止时间</th>-->
// <!--</tr>-->
window.operateEvents = {
    'click .remove': function (e, value, row, index) {
        $('#destroyAskUsers').bootstrapTable('remove', {
            field: 'id',
            values: [row.id]
        });
    },
    'click #docDelete': function (e, value, row, index) {
        var confirm = window.confirm("您确定要销毁  " + row.title + "  吗");
        if (!confirm)
            return;
        $.axx({
            type: 'get',
            url: '/flowDestroy/'+row.flowId+'/destroy',
            data: {},
            success: function (json) {
                // alert("destroy success");
                console.log(json.content);
                getFlowDestory();
            }
        })
    }
};

var destoryCol = [
    {
        field: "flowId",
        title: "id",
        visible: false
    }, {
        field: "reelType",
        title: "类型",
        visible: false
    }, {
        field: "status1",
        checkbox: true
    }, {
        field:"title",
        title: "题名"
    }, /*{
        field:"reelNum",
        title: "档号"
    },*/ {
        field: "destory_user",
        title: "责任人"
    }, {
        field:"createdDt",
        title: "发起时间"
    }, {
        field: "deadLine",
        title: "截止时间"
    }, {
        field: "Button",
        title: "操作",
        events: operateEvents,
        formatter: AddRemoveButton,
        align: 'center'
    }, {
        "field": "result",
        "title": "结果",
        visible: false
    }];

function commitDestroyFlow() {
    var bootstrapValidator = $('#form_destroy_create').data('bootstrapValidator');
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        var flowFormDestroy = $('#form_destroy_create').serializeJson();
        var ids = $.map($('#destroyAskUsers').bootstrapTable('getData'), function (row) {
            return row.id;
        });
        if (ids.length == 0) {
            alert("请选择审批用户");
            return;
        }
        var docIds = $.map($('#desFormDoc').bootstrapTable('getData'),function (obj) {
            return obj.id;
        });

        var recIds = $.map($('#desFormRec').bootstrapTable('getData'),function (obj) {
            return obj.id;
        })

        if(docIds.length==0&&recIds.length==0){
            alert("请选择要销毁的文件");
            return
        }

        flowFormDestroy.docIds = docIds;
        flowFormDestroy.recIds = recIds;
        flowFormDestroy.assigneeIds = ids;

        $.axx({
            type: "POST",
            url: "/flowDestroy",
            data: flowFormDestroy,
            success: function (json) {
                $('#newFeiQi').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
                getFlowDestory();
            }
        });
    }

}

$('#xiaoHui input').focus(function () {
    $('#vaildDestroy').addClass("hidden");
})

var destroyProcessByMeCol = [
    {"field": "status", "checkbox": true},
    {"field": "title", "title": "题名"},
    // {"field": "reelNum", "title": "档号"},
    {"field": "destoryUser", "title": "责任人"},
    {"field": "flows.createdDt", "title": "发起时间"},
    {"field": "flows.deadLine", "title": "截止时间"},
    {"field": "flowId", "title": "id"},
    {"field": "reelType", "title": "类型"}
];

var destroyProcessToMeCol = [
    {"field": "status", "checkbox": true},
    {"field": "title", "title": "题名"},
    // {"field": "reelNum", "title": "档号"},
    {"field": "destoryUser", "title": "责任人"},
    {"field": "flows.createdDt", "title": "发起时间"},
    {"field": "flows.deadLine", "title": "截止时间"}
];

function getoldFlowDestory(mehtod, isProcessed, queryparams) {
    var $table = $('#oldFeiQiByMeTable');
    var url = "/flowDestroy/" + usrId + "/process/" + isProcessed;
    var column = destroyProcessByMeCol;
    loadingBootstrapTable(mehtod, $table, url, column, queryparams);
    $table.bootstrapTable("hideColumn", "flowId");
    $table.bootstrapTable("hideColumn", "reelType");
}

$('#oldFeiQiByMeTable').on('dbl-click-row.bs.table',function (e,row, $element, field) {
    var flows = row.flows;
    if(usrId!=flows.createdBy){
        $('#xiaoHuiEnd button[data-target="#selectDetoryFlow"]').addClass("hidden");
        $('#askEnd').addClass("hidden");
    }else{
        $('#xiaoHuiEnd button[data-target="#selectDetoryFlow"]').removeClass("hidden");
        $('#askEnd').removeClass("hidden");
    }
    $('#xiaoHuiEnd').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
    getDesInfo(row.flowId);
    $('#del_back').unbind('click').on('click',function (e) {
        $('#oldFeiQiByMe').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
    });
})

function getleftDestroyTable() {
    getoldFlowDestory('get', false, queryparams);
}

function getProcessedDesTable() {
    // getoldFlowDestory('get', true, queryparams);
    var url = "/flowDestroy/" + usrId + "/process/" + true;
    var column = destroyProcessToMeCol;
    loadingBootstrapTable('get', $('#endFeiQiByMeTable'), url, column, queryparams);
}

function getFlowDestory(){
    $("#sendDestoryFlow").bootstrapTable('destroy');
    $("#sendDestoryFlow").bootstrapTable({
        method:'get',
        url:"flowDestorying/start/"+usrId,
        //contentType : "application/x-www-form-urlencoded",
        //是否显示行间隔色
        striped: true,
        //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        cache: false,
        //是否显示分页（*）
        pagination: true,
        //是否启用排序
        sortable: false,
        //排序方式
        sortOrder: "asc",
        //初始化加载第一页，默认第一页
        pageNumber:1,
        //每页的记录行数（*）
        pageSize: 10,
        //可供选择的每页的行数（*）
        pageList: [10, 25, 50, 100],
        //分页方式：client客户端分页，server服务端分页（*）
        sidePagination: "server",
        //是否显示搜索
        search: false,
        //Enable the strict search.
        strictSearch: true,
        queryParamsType:'',
        totalField: 'total',
        dataField: 'list',
        singleSelect: true,
        editable: true,
        paginationShowPageGo:true,
        // showRefresh: true,
        clickToSelect: true,
       // uniqueId: "flowId",
        columns: destoryCol,
        queryParams:queryparams,
        responseHandler:function (result){
            var model = result.content;
            return model;
        },
        onLoadError:function (status) {
            sessionout(status);
            return false;
        },
    });

}

//把对象信息写入到form表单中
function objToDesForm(goods) {
    var obj = new Object();
    var flows  = goods.flows;
    obj.flowId = flows.id;
    obj.deadLine = flows.deadLine;
    obj.title = goods.title;
    obj.reelSize = goods.reelSize;
    obj.destoryUser = goods.destoryUser;
    obj.superviseUser = goods.superviseUser;
    obj.description = goods.description;
    for (var name in obj) {
        if (obj.hasOwnProperty(name)) {
            $("#xiaoHuiEnd input[name='" + name + "']:not(:file)").val(obj[name]);
            $("#xiaoHuiEnd select[name='" + name + "']").val(obj[name]);
            $("#xiaoHuiEnd textarea[name='" + name + "']").val(obj[name]);
        }
    }
}

function  getDesInfo(flowId) {
    $.axx({
        type:"get",
        url: '/flowDestroy/'+flowId+'/details',
        data:{},
        dataType:"json",
        success:function (json) {
            var flowFormDestroy = json.content;
            var documentsList = flowFormDestroy.documentsList;
            var recordList = flowFormDestroy.recordList;
            objToDesForm(flowFormDestroy);
            if(documentsList.length!=0){
                $('#desEndLabel1').removeClass("hidden");
                $('#desEndDoc').bootstrapTable('load', documentsList);
            }else{
                $('#desEndLabel1').addClass("hidden");
            }
            if(recordList.length!=0){
                $('#desEndLabel2').removeClass("hidden");
                $('#desEndRec').bootstrapTable('load', recordList);
            }else{
                $('#desEndLabel2').addClass("hidden");
            }
        }
    })
}


//查看信息(destroy start by me)
function getDetailsStartByme($table, url, type) {
    var select_tr = $table.bootstrapTable('getSelections');
    if (select_tr.length != 1) {
        alert("请选择一个记录");
        return
    }

    if (select_tr[0].result == "DELETED") {
        alert("该文件已删除")
        return
    }

    // $('#flowForm input').val("");
    var data = {
        flowId: select_tr[0].flowId,
        reelNum: select_tr[0].reelNum,
        reelType: select_tr[0].reelType
    };

    $.axx({
        type: "POST",
        url: url,
        data: data,
        dataType: "json",
        success: function (json) {
            var model;
            if (type == 1) {
                model = json.content.flowFormLending;
                if (!model.lendingModel) {
                    alert("文件已删除");
                    return;
                }
            }
            if (type == 2) {
                model = json.content;
                if (!model.destoryModel) {
                    alert("文件已删除");
                    return;
                }
            }

            fileLendingInfo(type == 1 ? model.lendingModel : model.destoryModel, model.reelType);
            showFlowFormByReelType(model.reelType);
            $('#flowForm').modal('show');
        }
    })
}

var delInfoDocCol = [
    {field: "status", "checkbox": true},
    {field: "dataSource", title: "数据源"},
    {field: "fileNum", title: "档号", width: "20%"},
    {field: "beforeNum", title: "文件编号", halign: 'center', align: 'center', sortable: true},
    {field: "fileName", "title": "题名", width: "50%"},
    {field: "responsible", title: "责任者"},
    {field: "archiveDate", title: "归档日期", halign: 'center', align: 'center', sortable: true},
    {field: "importance", title: "密级"},
    {field: "paginalNum", title: "页数"},
    {field: "remark", title: "备注"}
];
var delInfoRecCol = [
    {field: "status", "checkbox": true},
    {field: "fileNum", title: "档号", sortable: true},
    {field: "title", "title": "题名", width: "40%"},
    {field: "responsible", title: "归档人"},
    {field: "archiveDate", title: '归档日期', sortable: true},
    {field: "importance", title: "密级"},
    {field: "appendices", title: "卷内文件数", halign: 'center', align: 'center', width: "5%"},
    {field: "remark", title: "备注", width: "15%"}
];


function initDestroyInfoTable() {
    $('#delInfoDocs').bootstrapTable({columns: delInfoDocCol});
    $('#delInfoRecs').bootstrapTable({columns: delInfoRecCol});
}

/*$('#destroyInfo').on('show.bs.modal',function (event) {
    var button = $(event.relatedTarget);
    var recipient = button.data('whatever');
    if(!recipient)
        return;
    var select;
    if(recipient==1)
        select= checkVaild($('#sendDestoryFlow'));
    else if(recipient==3)
        select=checkVaild($('#endFeiQiByMeTable'));
    getDestroyInfo(select[0]);
})*/

function showDestroyInfoFor1_3(recipient) {
    var row;
    if(recipient==1)
        row= checkVaild($('#sendDestoryFlow'));
    else if(recipient==3)
        row=checkVaild($('#endFeiQiByMeTable'));

    if (row[0].result == "DELETED") {
        alert("该文件已删除")
        return
    }
    var id = row[0].flowId;
    $.axx({
        type:"get",
        url: '/flowDestroy/'+id+'/details',
        data:{},
        dataType:"json",
        success:function (json) {
            var flowFormDestroy = json.content;
            var documentsList = flowFormDestroy.documentsList;
            var recordList = flowFormDestroy.recordList;
            if(documentsList.length!=0){
                $('#delInfoBody1').removeClass("hidden");
                $('#delInfoDocs').bootstrapTable('load', documentsList);
            }else{
                $('#delInfoBody1').addClass("hidden");
            }
            if(recordList.length!=0){
                $('#delInfoBody2').removeClass("hidden");
                $('#delInfoRecs').bootstrapTable('load', recordList);
            }else{
                $('#delInfoBody2').addClass("hidden");
            }
            $('#destroyInfo').modal('show');
        }
    })

}


//销毁流程批准
function endDestroyFlow(action, description) {
    var flowId = $('#form_destroy_end input[name=flowId]').val();
    var data = {
        usrId:usrId,
        description:description,
        flowId:flowId,
        action:action
    }
    $.axx({
        type:'POST',
        url: "/flowDestroy/endSwitch/",
        data:data,
        success:function (json) {
            alert("审批成功");
            $('#flow-modal-description').val("");
            $('#shenpiyijian1').modal("hide");
            getProcessedDesTable();
            $('#endFeiQiByMe').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");

        }
    });
}

//销毁流程请求审批
function askForDestroy() {
    var flowId = $('#form_destroy_end input[name=flowId]').val();
    var checks = $.map($('#destroyAskUsers1').bootstrapTable('getData'), function (row) {
        return row.id;
    });

    console.log("checks  " + checks);
    console.log("flowId  " + flowId);
    if(checks.length==0){
        $('#askDestorySure').removeClass("hidden");
        return;
    }

    var data ={
        checks:checks,
        usrId:usrId,
        flowId:flowId
    }

    $.axx({
        type:'POST',
        url: "/flowDestroy/process",
        data:data,
        success:function (json) {
            alert(json.content);
            $('#selectDetoryFlow').modal('hide');
           // getFlowProcessByme('get',true,queryparams);
            getProcessedDesTable();
            $('#endFeiQiByMe').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
        }
    });
}


$('#delPerson').on('show.bs.modal', function (event) {
    getDestroyTree();
    var button = $(event.relatedTarget) // 触发事件的按钮
    var recipient = button.data('whatever');//  1(发起页面) 2（批准界面） 3（请求审批）
    $('#delPersonSubmit').one('click', function () {
        if (recipient == 1)
            addDestroyUser($('#destroyAskUsers'));
        else if (recipient == 2)
            addDestroyUser($('#destroyAskUsers1'));
        else if(recipient ==3)
            addDestroyUser($('#destroyAskUsersEnd'));
    });
});

$('#selectDetoryFlow').on('show.bs.modal', function (event) {
    var column = [{field: "name", title: "用户名"}, {field: "id", title: "id"}, {
        field: "operate",
        title: "remove",
        events: operateEvents1,
        formatter: operateFormatter
    }]
    createDestroyAskTable($('#destroyAskUsers1'), column);
})

$('#selectDetoryFlow').on('hidden.bs.modal', function (event) {

})

$('#xiaoHui').on('show.bs.modal', function (event) {
    var column = [{field: "name", title: "用户名"}, {field: "id", title: "id"}, {
        field: "operate",
        title: "remove",
        events: operateEvents,
        formatter: operateFormatter
    }]
    createDestroyAskTable($('#destroyAskUsers'), column);
})

$('#xiaoHui').on('hidden.bs.modal', function (event) {
    $(this).find("input").val("");
})

function createDestroyAskTable($table, column) {
    $table.bootstrapTable('destroy');
    $table.bootstrapTable({editable: true, columns: column, showHeader: false});
    $table.bootstrapTable('hideColumn', 'id');

    console.log("destroyAskUsers                        create")
}

function operateFormatter(value, row, index) {
    return [
        '<a class="remove" href="javascript:void(0)" title="Remove">',
        '<i class="glyphicon glyphicon-remove"></i>',
        '</a>'
    ].join('');
}

function AddRemoveButton(value, row, index) {

    if (row.result == "") {
        return "正在审批中";
    }
    if (row.result == "ACCEPT") {
        return [
            // '<button id="docDelete" type="button" class ="btn btn-danger">销毁</button>'
            '<a id="docDelete"><u>销毁</u></a>'
        ].join("")
    }
    if (row.result == "REJECT") {
        return "否决";
    }
    if (row.result == "DELETED")
        return "已删除"
    return "正在审批中";
}


window.operateEvents1 = {
    'click .remove': function (e, value, row, index) {
        $('#destroyAskUsers1').bootstrapTable('remove', {
            field: 'id',
            values: [row.id]
        });
    }
};

function extractedToArray($table) {
    var select_users = $table.bootstrapTable('getSelections');
    var array = [];
    for (var i = 0; i < select_users.length; i++) {
        console.log(select_users[i].id);
        console.log(select_users[i].name);
        var row = {name: select_users[i].name, id: select_users[i].id};
        array.push(row)
    }
    return array;
}

function addDestroyUser($table) {
    var array = extractedToArray($('#destoryTableId'));

    $('#delPerson').modal('hide');
    var oldArray = $table.bootstrapTable('getData');
    var newArray = extractedAddToTO(oldArray, array);
    $table.bootstrapTable("append", newArray);
    $('#destoryTableId').bootstrapTable("uncheckAll");

}

//获取审批用户模态框的左侧部门树  及右侧的用户列表
function getDestroyTree() {
    $.axx({
        url: "depts",
        type: 'GET',
        data: {},
        success: function (json) {
            var models = json.content;
            destroyTree($('#destoryTreeId'), models);
        }
    })
}

/*function dept(text, deptId, usrId) {
    this.text = text;
    this.deptId = deptId;
    this.usrId = usrId;
    this.nodes = [];
}*/

function dept(text,deptId,usrId,hasChilds){
    var obj = new Object();
    obj.text=text;
    obj.deptId=deptId;
    obj.usrId = usrId;
    obj.nodes = [];
    obj.hasChilds =hasChilds;
    var state = new Object();
    state.expanded = hasChilds?false:true
    obj.state = state;
    return obj;
}

function getChildDepts(tree,node) {
    $.axx({
        url: ARCHIVE_API.dept_list_child.replace("{0}", node.deptId),
        type: 'get',
        data: {},
        // dataType:'json',
        success: function (json) {
            var models = json.content;
            //tree.treeview('deleteNode',[node.nodeId]);
            if(models.length ==0)
                node.nodes ==null;
            for(var j=0;j<models.length;j++) {
                tree.treeview('addNode', [node.nodeId,
                    {node: new dept(models[j].name, models[j].id, usrId,models[j].hasChilds)}]);
            }

        }
    });
}

function destroyTree($tree, data) {
    var tree = [];

    for (var i = 0; i < data.length; i++) {
        tree.push(new dept(data[i].name, data[i].id, usrId,data[i].hasChilds));
    }
    var orgNa = {
        bootstrap2: false,
        expandIcon: 'glyphicon glyphicon-plus',
        emptyIcon: "glyphicon glyphicon-stop ",
        showTags: true,
        levels: 0,
        enableLinks: true,
        selectedIcon: "glyphicon glyphicon-map-marker",
        showBorder: false,
        data: tree,
    }
    $tree.treeview(orgNa);

    $tree.unbind('nodeSelected').on('nodeSelected', function (event, data) {
        var data1 = data.deptId;
        var column = [{"field": "status", "checkbox": true}, {"field": "name", "title": "用户名"}, {
            "field": "id",
            "title": "id"
        }];
        createDestroyTable($('#destoryTableId'), column);
        getDestroyUserByDept($('#destoryTableId'), data1);
    })

    $tree.unbind("nodeExpanded").on("nodeExpanded ", function (event, node) {
        if (node.nodes.length == 0) {
            if(node.hasChilds)
                getChildDepts($tree, node);
        } else {
            var arr = node.nodes;
            for (var i = 0; i < arr.length; i++) {
                var nodeId = arr[i].nodeId;
                var hasChilds = arr[i].hasChilds;
                if (!hasChilds) {
                    $tree.treeview('expandNode', [nodeId]);
                }
            }
        }
    });
}

function createDestroyTable($table, $column) {
    $table.bootstrapTable('destroy');
    $table.bootstrapTable({
        search: true, pagination: true, pageSize: 10, editable: true, clickToSelect: true,
        columns: $column,
    });
}

function getDestroyUserByDept($table, deptId) {
    $.axx({
        url: "depts/" + deptId + "/users",
        type: "GET",
        data: {},
        success: function (json) {
            $table.bootstrapTable("load", json.content);
            $table.bootstrapTable("hideColumn", "id");
        }
    })
}

//已销毁
var deletedCol = [
    {
        field: "title",
        title: "题名"
    },/* {
        field: "reelNum",
        title: "档号"
    }, {
        field: "reelType",
        title: "类型"
    },*/ {
        field: "superviseUser",
        title: "责任人"
    }, {
        field: "flows.endDt",
        title: "销毁时间"
    }, {
        field: "destoryUser",
        title: "销毁人"
    }];

//已销毁子表
var subDelCol = [
    {
        field: "title",
        title: "标题"
    }, {
        field: "file_num",
        title: "档号"
    }, {
        field: "type",
        title: "类型"
    }
]

InitOldFeiQiSubTable = function (index, row, $detail) {
    var parentid = row.flowId;
    var cur_table = $detail.html('<table id="fileTable"></table>').find('table');
    $(cur_table).bootstrapTable({
        url: '/flowDestroy/sub/'+parentid,
        method: 'get',
        /*queryParams: { strParentID: parentid },
        ajaxOptions: { strParentID: parentid },*/
        clickToSelect: true,
        // detailView: true,//父子表
        uniqueId: "id",
        pageSize: 10,
        pageList: [10, 25],
        columns:subDelCol ,
        //无线循环取子表，直到子表里面没有记录
        /*onExpandRow: function (index, row, $Subdetail) {
            oInit.InitSubTable(index, row, $Subdetail);
        }*/
        onLoadError:function (status) {
            sessionout(status);
            return false;
        },
        responseHandler:function (result) {
            return result.content;
        }
    });
};

function initOldFeiQi(){
    $("#oldFeiQi").bootstrapTable('destroy');
    $("#oldFeiQi").bootstrapTable({
        url: "/flowDestroy/deleted/" + deptId,
        method: 'get',
        /*queryParams: { strParentID: parentid },
        ajaxOptions: { strParentID: parentid },*/
        queryParams:queryparams,
        clickToSelect: true,
        detailView: true,//父子表
        uniqueId: "id",
        pageSize: 10,
        pageList: [10, 25],
        columns: deletedCol,
        pagination: true,
        sidePagination: "server",
        queryParamsType:'',
        totalField: 'total',
        dataField: 'list',
        //无线循环取子表，直到子表里面没有记录
        onExpandRow: function (index, row, $Subdetail) {
            InitOldFeiQiSubTable(index, row, $Subdetail);
        },
        onLoadError:function (status) {
            sessionout(status);
            return false;
        },
        responseHandler:function (result) {
            return result.content;
        }
    });
}

function getDeletedTable() {
    initOldFeiQi();
   /* var url = "/flowDestroy/deleted/" + deptId;

    loadingBootstrapTable('get', $('#oldFeiQi'), url, deletedCol, queryparams);*/
    /*$('#oldFeiQi').bootstrapTable('destroy');
    $('#oldFeiQi').bootstrapTable({
        method: 'get', url: url, columns: deletedCol, search: true, pagination: true,
        //是否启用排序
        sortable: false, showRefresh: true, responseHandler: function (result) {
            return result.content;
        }
    });*/
    $('#oldFeiQiDiv').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
}

$('.depApproUser').on('change','input',function(){
    console.log("111111111");
    $('#askSure').addClass("hidden");
});

$('.fileDepApproUser').on('change','input',function(){
    console.log("111111111");
    $('#askSure').addClass("hidden");
});

$('.companyLead').on('change','input',function(){
    console.log("111111111");
    $('#askSure').addClass("hidden");
});

$('#selectDetoryId').on('click', function () {
    $('#askDestorySure').addClass("hidden");
})

$(function () {
    $('#form_destroy_create').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields:{
            title: {
                validators: {
                    notEmpty: {
                        message: '销毁资料名称必填不能为空'
                    },
                }
            },
            fileNum: {
                validators: {
                    notEmpty: {
                        message: '销毁资料档号必填不能为空'
                    },
                }
            },
            reelSize: {
                validators: {
                    notEmpty: {
                        message: '销毁资料份数必填不能为空'
                    },
                }
            },
            destoryTime: {
                validators: {
                    notEmpty: {
                        message: '销毁资料时间必填不能为空'
                    },
                }
            },
            destoryUser: {
                validators: {
                    notEmpty: {
                        message: '销毁人必填不能为空'
                    },
                }
            },
            superviseUser: {
                validators: {
                    notEmpty: {
                        message: '监督人必填不能为空'
                    },
                }
            },
            reelType: {
                validators: {
                    notEmpty: {
                        message: '文档类型必填不能为空'
                    },
                }
            },

        }
    })
});
//显示发起销毁页面
function showXiaoHui() {
    $('#xiaoHui').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
}
//发起销毁页面返回按钮
$('#des_form_back').on('click',function (e) {
    $('#newFeiQi').removeClass('hidden').hide().fadeIn(500).siblings().addClass("hidden");
    getFlowDestory();
})

window.desDocEvents = {
    'click .remove': function (e, value, row, index) {
        $('#desFormDoc').bootstrapTable('remove', {
            field: 'id',
            values: [row.id]
        });
    }
};

window.desEndEvent = {
    'click .remove': function (e, value, row, index) {
        $('#destroyAskUsersEnd').bootstrapTable('remove', {
            field: 'id',
            values: [row.id]
        });
    }
}

window.desRecEvents = {
    'click .remove': function (e, value, row, index) {
        $('#desFormRec').bootstrapTable('remove', {
            field: 'id',
            values: [row.id]
        });
    }
}

function createDesFormDocTable() {
    $('#desFormDoc').bootstrapTable({
        columns: [ {
            field: "Number", title: "序号", halign: 'center', align: 'center',
            formatter: function (value, row, index) {
                var options = $('#desFormDoc').bootstrapTable("getOptions");
                return (options.pageNumber - 1) * (options.pageSize) + index + 1;
            }, width: "5%"
        }, {
            field: 'operate',
            title: '操作',
            align: 'center',
            halign: 'center',
            valign: 'middle',
            width: "10%",
            events: desDocEvents,
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
            {field: "paginalNum", title: "页数"}, {field: "remark", title: "备注"}]
    });
}

function createDesFormRecTable() {
    $('#desFormRec').bootstrapTable({columns:[ {
        field: "Number", title: "序号", halign: 'center', align: 'center',
        formatter: function (value, row, index) {
            var options = $('#desFormRec').bootstrapTable("getOptions");
            return (options.pageNumber - 1) * (options.pageSize) + index + 1;
        }, width: "5%"
    }, {
        field: 'operate',
        title: '操作',
        align: 'center',
        halign: 'center',
        valign: 'middle',
        width: "10%",
        events: desRecEvents,
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
        }, {field: "remark", title: "备注", width: "15%"}]})
}

$(function () {
    createDesFormDocTable();
    createDesFormRecTable();
    createDesEndDocTable();
    createDesEndRecTable();
})

//添加件级档案
function addDocsToDes() {
    var array = $('#selectTODocs').bootstrapTable("getSelections");
    var oldData = $('#desFormDoc').bootstrapTable("getData");
    var newArray = extractedAddToTO(oldData, array);
    $('#toDocModal').modal('hide');
    $('#desFormDoc').bootstrapTable('append',newArray);
    $('#selectTODocs').bootstrapTable("uncheckAll");
}

//添加卷级档案
function addRecsToDes() {
    var array = $('#selectTORecs').bootstrapTable("getSelections");
    var oldData = $('#desFormRec').bootstrapTable("getData");
    var newArray = extractedAddToTO(oldData, array);
    $('#toRecModal').modal('hide');
    $('#desFormRec').bootstrapTable('append', newArray);
    $('#selectTORecs').bootstrapTable("uncheckAll");
}

//创建展示件级档案
function createDesEndDocTable(){
    $('#desEndDoc').bootstrapTable({
        columns: [ {
            field: "Number", title: "序号", halign: 'center', align: 'center',
            formatter: function (value, row, index) {
                var options = $('#desEndDoc').bootstrapTable("getOptions");
                return (options.pageNumber - 1) * (options.pageSize) + index + 1;
            }, width: "5%"},
            {field: "dataSource", title: "数据源"},
            {field: "fileNum", title: "档号", width: "20%"},
            {field: "beforeNum", title: "文件编号", halign: 'center', align: 'center', sortable: true},
            {"field": "fileName", "title": "题名", width: "50%"},
            {field: "responsible", title: "责任者"},
            {field: "archiveDate", title: "归档日期", halign: 'center', align: 'center', sortable: true},
            {field: "importance", title: "密级"},
            {field: "paginalNum", title: "页数"},
            {field: "remark", title: "备注"}]
    });
}

//创建展示卷级档案
function createDesEndRecTable() {
    $('#desEndRec').bootstrapTable({
        columns: [{
            field: "Number", title: "序号", halign: 'center', align: 'center',
            formatter: function (value, row, index) {
                var options = $('#desEndRec').bootstrapTable("getOptions");
                return (options.pageNumber - 1) * (options.pageSize) + index + 1;
            }, width: "5%"
        }, {field: "fileNum", title: "档号", sortable: true},
            {"field": "title", "title": "题名", width: "40%"},
            {field: "responsible", title: "归档人"},
            {field: "archiveDate", title: '归档日期', sortable: true},
            {field: "importance", title: "密级"},
            {field: "appendices", title: "卷内文件数", halign: 'center', align: 'center', width: "5%"},
            {field: "remark", title: "备注", width: "15%"}]
    })
}