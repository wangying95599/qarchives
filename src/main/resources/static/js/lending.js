//借阅流程
var dblLendingPermission=false;
var lendingDetailCol = [{"field": "status", "checkbox": true}, {
    field: "Number", title: "序号", halign: 'center', align: 'center',
    formatter: function (value, row, index) {
        var options = $('#lendingDetailTable').bootstrapTable("getOptions");
        return (options.pageNumber - 1) * (options.pageSize) + index + 1;
    }, width: "5%"
}, {field: "dataSource", title: "数据源"}, {field: "fileNum", title: "档号", width: "20%"}, {
    field: "beforeNum",
    title: "文件编号",
    halign: 'center',
    align: 'center',
    sortable: true
}, {"field": "fileName", "title": "题名", width: "50%"},{
    field:"savaLocation",title:"实体文件位置"
}, {field: "responsible", title: "责任者"}, {
    field: "archiveDate",
    title: "归档日期",
    halign: 'center',
    align: 'center',
    sortable: true
}, {field: "importance", title: "密级"},
    {field: "paginalNum", title: "页数"}, {field: "remark", title: "备注"}];

var lendingDocInRecCol = [{"field": "status", "checkbox": true}, {
    field: "Number", title: "序号", halign: 'center', align: 'center',
    formatter: function (value, row, index) {
        var options = $('#docTable').bootstrapTable("getOptions");
        return (options.pageNumber - 1) * (options.pageSize) + index + 1;
    }, width: "5%"
},  {field: "dataSource", title: "数据源"}, {field: "insideNum", title: "内部编号", width: "20%"}, {
    field: "beforeNum",
    title: "文件编号",
    halign: 'center',
    align: 'center',
    sortable: true
}, {"field": "fileName", "title": "题名", width: "50%"},{
    field:"savaLocation",title:"实体文件位置"
},{field: "responsible", title: "责任者"}, {
    field: "archiveDate",
    title: "归档日期",
    halign: 'center',
    align: 'center',
    sortable: true
}, {field: "importance", title: "密级"},
    {field: "paginalNum", title: "页数"}, {field: "remark", title: "备注"}];

var lendingRecDetailCol = [{"field": "status", "checkbox": true}, {
    field: "Number", title: "序号", halign: 'center', align: 'center',
    formatter: function (value, row, index) {
        var options = $('#lendingRecDetailTable').bootstrapTable("getOptions");
        return (options.pageNumber - 1) * (options.pageSize) + index + 1;
    }, width: "5%"
}, {field: "fileNum", title: "档号", sortable: true},
    {"field": "title", "title": "题名", width: "40%"},{
        field:"reserveLocation",title:"实体文件位置"
    }, {field: "responsible", title: "归档人"}, {
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

var fileFormatter;


InitSubTable = function (parrow,index, row, $detail) {
    var parentid = row.documentLocalId;
    var cur_table = $detail.html('<table id="fileTable"></table>').find('table');
    $(cur_table).bootstrapTable({
        url: '/files/'+parentid,
        method: 'get',
        /*queryParams: { strParentID: parentid },
        ajaxOptions: { strParentID: parentid },*/
        clickToSelect: true,
        // detailView: true,//父子表
        uniqueId: "id",
        pageSize: 10,
        pageList: [10, 25],
        columns: [{field: 'fileName', title: '题名'},
            {
                field: 'fileType', title: '类型', formatter: function (value, row, index) {
                var fileType = row.fileType;
                if (fileType == 'main')
                    return "正文";
                else if (fileType == 'accessory')
                    return "附件";
                else return "";
            }
            }, {
                field: 'operate', title: '操作', align: 'center', halign: 'center', valign: 'middle',
                formatter: function (value, subrow, index) {
                    var lendingPermission = parrow.lendingPermission;
                    if(lendingPermission=='电子版水印下载'||lendingPermission=='纸质版复印件'){
                        return [
                            '<a class="fileView" href="javascript:void(0)" title="查看">',
                            '查看',
                            '</a> &nbsp;&nbsp;',
                            '<a class="file-load" href="javascript:void(0)" title="下载">',
                            '下载',
                            '</a>',
                        ].join('');
                    }else{
                        return [
                            '<a class="fileView" href="javascript:void(0)" title="查看">',
                            '查看',
                            '</a>',
                        ].join('');
                    }
                }, events: operateEvents6
            }],
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

InitSubRevTable = function (parrow,index, row, $detail) {
    var parentid = row.id;
    var cur_table = $detail.html('<table id="docTable"></table>').find('table');
    $(cur_table).bootstrapTable({
        url: 'documents/lookrecords/'+parentid+'/type/'+row.archiveType,
        method: 'get',
        /*queryParams: { strParentID: parentid },
        ajaxOptions: { strParentID: parentid },*/
        queryParams:sortQueryParams,
        clickToSelect: true,
        detailView: true,//父子表
        uniqueId: "id",
        pageSize: 10,
        pageList: [10, 25],
        columns: lendingDocInRecCol,
        pagination: true,
        sidePagination: "server",
        queryParamsType:'',
        totalField: 'total',
        dataField: 'list',
        //无线循环取子表，直到子表里面没有记录
        onExpandRow: function (index, row, $Subdetail) {
            InitSubTable(parrow,index, row, $Subdetail);
        },
        onLoadError:function (status) {
            sessionout(status);
            return false;
        },
        responseHandler:function (result) {
            return result.content;
        }
    });
};

function showLendingDetail(row) {
    $('#lend_load').addClass("hidden");
    $('#lendRev_load').addClass("hidden");

    $('#lendingDetail').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
    $('#lendingDetailTable').bootstrapTable({columns:lendingDetailCol,detailView:true,pageSize: 10,
        pageList: [10, 25],onExpandRow:function (index,subrow,$detail) {
            InitSubTable(row,index, subrow, $detail);
        }});
    $('#lendingRecDetailTable').bootstrapTable({columns:lendingRecDetailCol,detailView:true,pageSize: 10,
        pageList: [10, 25],onExpandRow:function (index,subrow,$detail) {
            InitSubRevTable(row,index, subrow, $detail);
}});
    $.axx({
        type:'get',
        url:'/flowLending/'+row.flowId+'/detail',
        data:{},
        success:function (json) {
            var flowFormLending = json.content;
            var documentsList = flowFormLending.documentsList;
            var recordList = flowFormLending.recordList;
            console.log("permission1:", flowFormLending.lendingPermission);
            var lendingPermission =  flowFormLending.lendingPermission;
            if(documentsList.length!=0){
                $('#lendingDocDetail').removeClass("hidden");
                $('#lendingDetailTable').bootstrapTable('load',documentsList);
                if(lendingPermission=='电子版水印下载'||lendingPermission=='纸质版复印件'){
                    $('#lend_load').removeClass("hidden");
                }
            }else{
                $('#lendingDocDetail').addClass("hidden");
            }
            if(recordList!=0){
                $('#lendingRecDetail').removeClass("hidden");
                $('#lendingRecDetailTable').bootstrapTable('load',recordList);
                if(lendingPermission=='电子版水印下载'||lendingPermission=='纸质版复印件'){
                    $('#lendRev_load').removeClass("hidden");
                }
            }else{
                $('#lendingRecDetail').addClass("hidden");
            }
        }
    })
}

window.operateEvents6={
    'click .fileView':function (e, value, row, index) {
        window.open("/view?fileId=" + row.id + "&fileName=" + encodeURI(row.fileName));
    },
    'click .file-load':function (e,value,row,index) {
        window.location = "/fileDownload?localtion=" + row.location + "&fileName=" + encodeURI(encodeURI(row.fileName)) + "&fileId=" +
            row.id;
    }
}

var fileCol = [{field: 'fileName', title: '题名'},
    {
        field: 'fileType', title: '类型', formatter: function (value, row, index) {
        var fileType = row.fileType;
        if (fileType == 'main')
            return "正文";
        else if (fileType == 'accessory')
            return "附件";
        else return "";
    }
    }, {
        field: 'operate', title: '操作', align: 'center', halign: 'center', valign: 'middle',
        formatter: function (value, subrow, index) {
            return [
                '<a class="fileView" href="javascript:void(0)" title="查看">',
                '查看',
                '</a> &nbsp;&nbsp;',
                '<a class="file-load" href="javascript:void(0)" title="下载">',
                '下载',
                '</a>',
            ].join('');
        }, events: operateEvents6
    }];





//查看返回
$('#lend_back').on('click',function (e) {
    $('#myLiuChengTable1').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
    getFlowStartByme('get',queryparams);
})
//查看件级下载
$('#lend_load').on('click',function (e) {
    /*var selects = checkVaild($('#lendingDetailTable'));
    window.location = "/fileZipDownload1?reelType=document" + "&fileName=" + encodeURI(encodeURI(selects[0].fileName)) +
        "&fileId=" + selects[0].documentLocalId;*/
    downloads($('#lendingDetailTable'),'document');
})
//查看卷级下载
$('#lendRev_load').on('click',function (e) {
    downloads($('#lendingRecDetailTable'), 'record');
})

function getLendingAsker(deptId) {
    getAssignee($('#lendingAskTable'), deptId);
}

//radio改变时决定选择借出部门 还是 总裁室的领导
function bindRadioChange(loanDeptId) {
    $('input[name=loanDept]').unbind('change');
    $('input[name=loanDept]').change(function () {
        var val = $('input[name=loanDept]:checked').val();
        if(val == 'option1'){
            getLendingAsker(loanDeptId);
        }else if(val = 'option2'){
            getLendingAsker(BOSS_DEPT_ID);
        }
    });
}

//发起请求审批
function initiateLendingAsk(obj) {
    var loanDeptId = obj.loanDeptId;
    var managerId = obj.managerId;
    var hostId = obj.hostId;
    var assignee;
    if(usrId==hostId&&deptId==loanDeptId){
        var selectTr = checkVaild($('#lendingAskTable'));
        assignee = selectTr[0].id;
    }else if(usrId==managerId&&deptId!=loanDeptId){
        assignee = $('#managerForLending').val();
    }
    var checks = [];
    checks.push(assignee);
    var data = {flowId: obj.flowId, checks: checks};
    $.axx({
        type:'post',
        url:'/flowLending/process',
        data:data,
        success:function (json) {
            $('#lendingNodeSelect').modal('hide');
            getFlowProcessedByme();
            $('#myLiuCheng1Table2').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
        }
    })
}

//借阅流程批准
function endLendingFlow(row,action) {

    var flowId = row.flowId;
    var description = $('#flow-modal-description').val();
    var data = {flowId:flowId,description:description,action:action};

    $.axx({
        type:'POST',
        url:"/flowLending/endSwitch/",
        data:data,
        success:function (json) {
            alert("审批成功");
            $('#flow-modal-description').val("");
            $('#shenpiyijian1').modal("hide");
            getFlowProcessedByme();
            $('#myLiuCheng1Table2').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
        }
    });
}

function initLendingToolbar(row) {
    $('#LDE_status').unbind('click').on('click',function () {
        getFlowStatus(row.flowId);
        $('#flow-status').modal('show');
    });
    $('#LDE_ok').unbind().on('click',function () {
        $('#shenpiyijian1').modal('show');
        $('#flow-modal-submit').unbind('click').on('click',function (e) {
            endLendingFlow(row,'ACCEPT');
        })
    });
    $('#LDE_no').unbind().on('click',function () {
        $('#shenpiyijian1').modal('show');
        $('#flow-modal-submit').unbind('click').on('click',function (e) {
            endLendingFlow(row,'REJECT');
        })
    });
    $('#LDE_ask').unbind('click').on('click',function () {
        console.log(row);
        var loanDeptId = row.loanDeptId;
        var managerId = row.managerId;
        var hostId = row.hostId;
        console.log(loanDeptId + "_" + usrId + "_" + managerId + "_" + deptId);
        if((usrId==hostId||usrId==managerId)&&deptId==loanDeptId){
            $('#lending-body-1').removeClass("hidden");
            $('#lending-body-2').addClass("hidden");
            $('input[name="loanDept"]').eq(0).attr("checked",'checked');
            getLendingAsker(loanDeptId);
            bindRadioChange(loanDeptId);
        }else if(usrId==managerId&&deptId!=loanDeptId){
            $('#lending-body-1').addClass("hidden");
            $('#lending-body-2').removeClass("hidden");
            getTurnOverManager($('#managerForLending'), loanDeptId);
        }
        $('#lendingNodeSelect').modal('show');

        $('#lendingAskSubmit').unbind('click').on('click',function (e) {
            initiateLendingAsk(row);
        })
    });
}

//流程审批
$('#myLiuCheng1Table').on('dbl-click-row.bs.table',function (e,row, $element, field) {
    var lendingPermission = row.lendingPermission;
    if(lendingPermission=='电子版水印下载'||lendingPermission=='纸质版复印件'){
        dblLendingPermission=true;
    }else{
        dblLendingPermission=false;
    }
    $('#LendingEnd').removeClass('hidden').hide().fadeIn(500).siblings().addClass("hidden");
    getLendingInfoForEnd(row.flowId);
    $('#LDE_back').unbind('click').on('click',function (e) {
        $('#myLiuCheng1Table1').removeClass('hidden').hide().fadeIn(500).siblings().addClass("hidden");
        getFlowWillProcessByme();
    })
})



function seqFormatter(value,row,index) {
    var options = $('#LDEndDocs').bootstrapTable("getOptions");
    return (options.pageNumber - 1) * (options.pageSize) + index + 1;
}

function seqFormatter1(value,row,index){
    return index+1;
}

function  objToLendingEndForm(goods) {
    var flows = goods.flows;
    var obj = {title:goods.title,lendingUser:goods.lendingUser,lendingPermission:goods.lendingPermission,createdDt:flows.createdDt,deadLine:flows.deadLine,description:goods.description};
    for (var name in obj) {
        if (obj.hasOwnProperty(name)) {
            $("#lendingEndForm input[name='" + name + "']:not(:file)").val(obj[name]);
            $("#lendingEndForm textarea[name='" + name + "']").val(obj[name]);
        }
    }
}

function getLendingInfoForEnd(flowId){
    $.axx({
        type:'get',
        url:'/flowLending/'+flowId+'/detail',
        data:{},
        success:function (json) {
            var flowFormLending = json.content;
            objToLendingEndForm(flowFormLending);
            var documentsList = flowFormLending.documentsList;
            var recordList = flowFormLending.recordList;
            if(documentsList.length!=0){
                $('#LDEndLabel1').removeClass("hidden");
                $('#LDEndDocs').bootstrapTable('load', documentsList);
            }else {
                $('#LDEndLabel1').addClass("hidden");
            };
            if(recordList.length!=0){
                $('#LDEndLabel2').removeClass("hidden");
                $('#LDEndRecs').bootstrapTable('load', recordList);
            }else{
                $('#LDEndLabel2').addClass("hidden");
            };
            initLendingToolbar(flowFormLending);
        }
    })
}


