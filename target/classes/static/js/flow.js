//流程中查看信息禁止输入
$(function(){
    $('.documentInfo input,.recordInfo input,.archivesInfo input,.fendInfo input').attr('disabled','disabled');
});

/*var deptId = 1;
// var usrId = 46;
var username = "李秀会";*/
//由我发起 table 列
window.operateEvents2 = {
    'click #docView': function (e, value, row, index) {
        // alert(row.reelType+"    "+row.reelNum + "  r  "+row.result);
        /*$('#myLiuChengDetail').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
        getDblclickFlow(row);*/
        showLendingDetail(row);
    }
}

var flowByMeCol = [{"field":"status","checkbox":true},{"field":"title","title":"题名"},
     {"field":"flows.createdDt","title":"发起时间"},
    {"field":"flows.deadLine","title":"截止时间"},{"field":"lendingPermission","title":"借阅权限"},{"field":"flowId","title":"id"},
    {
        field: "Button",
        title: "操作",
        events: operateEvents2,
        formatter: addViewButton,
        align: "center"
    }, {field: "result", title: "结果", visible: false}];
var flowProcessByMeCol = [{"field": "status", "checkbox": true}, {
    field: "Number", title: "序号", halign: 'center', align: 'center',
    formatter: function (value, row, index) {
        var options = $('#myLiuCheng1Table').bootstrapTable("getOptions");
        return (options.pageNumber - 1) * (options.pageSize) + index + 1;
    }
}, {"field": "title", "title": "题名"},
    {"field":"reelNum","title":"档号"}, {"field":"lendingUser","title":"发起人"},{"field":"flows.createdDt","title":"发起时间"},
    {"field":"flows.deadLine","title":"截止时间"},{"field":"lendingPermission","title":"借阅权限"},{"field":"flowId","title":"id"},
    {"field":"reelType","title":"类型"},{"field":"depId","title":"部门id"}];
var flowProcessToMeCol = [{"field": "status", "checkbox": true}, {
    field: "Number", title: "序号", halign: 'center', align: 'center',
    formatter: function (value, row, index) {
        var options = $('#myLiuCheng2Table').bootstrapTable("getOptions");
        return (options.pageNumber - 1) * (options.pageSize) + index + 1;
    }
}, {"field": "title", "title": "题名"},
    {"field":"reelNum","title":"档号"}, {"field":"lendingUser","title":"发起人"},{"field":"flows.createdDt","title":"发起时间"},
    {"field":"flows.deadLine","title":"截止时间"},{"field":"lendingPermission","title":"借阅权限"}];

function addViewButton(value, row, index) {
    var flows = row.flows;
    if (flows.result == "") {
        return "正在审批中";
    } else if (flows.result == "ACCEPT") {
        var endTime = flows.deadLine;
        endTime = endTime.replace("-", "/");
        var d2 = new Date();
        var d1 = new Date(Date.parse(endTime));
        if (d1 < d2) return "已过期";

        return [
            // '<button id="docView" type="button" class ="btn btn-success">查看</button>'
            '<a id="docView"><u>查看</u></a>'
        ].join("");
    } else if (flows.result == "REJECT") {
        return "否决";
    }


    return "正在审批中";
}

//发起借阅
function lendingStart1() {
    var title = $('#lendingtitle').val();
    if(title==''){
        $('#vaildlending').html("标题不能为空").removeClass("hidden");
        return
    }
    var reelNum = $('#lendingreel').val();
    if(reelNum==''){
        $('#vaildlending').html("档号不能为空").removeClass("hidden");
        return
    }
    var reelType = $('#reeltype option:selected').attr("id");
    console.log("reelType      _-   ", reelType);
    var permission = $('#lendingrole option:selected').val();
    var expiredate = $('#expiredate').val();
    console.log("expiredate" + expiredate);
    if(expiredate==''){
        $('#vaildlending').html("截止时间不能为空").removeClass("hidden");
        return
    }


    $.axx({
       type:'POST',
       url:"/flowLending/isvalid",
        data:{reelNum:reelNum,reelType:reelType},
        success:function (json) {
            var isvalid = json.content;
            if(!isvalid){
                $('#vaildlending').html("档号或类型输入错误").removeClass("hidden");
                return
            }


            var description = $('#lendingcause').val();
            var deptadmin =$('#deptadmin option:selected').attr("id");

            var data = {title:title,reelNum:reelNum,reelType:reelType,lendingPermission:permission,description:description,
                deadLine:expiredate,assigneeId:deptadmin,assigneeBy:usrId,deptId:deptId,lendingUser:username}

            $.each(data, function (key, value) {
                console.log(key + "        " + value);
            })
            $.axx({
                type:'POST',
                url:"/flowLending",
                data:data,
                success:function (json) {
                    alert("发起成功");
                    getFlowStartByme('get',queryparams);

                    $('#lendingtitle').val("");
                    $('#lendingreel').val("");
                    $('#lendingrole option:selected').val("");
                    $('#expiredate').val("");

                    $('#jieYue').modal("hide");
                }
            });
        }
    });
}

function getWithNumCol($table, column) {
    newColumn.splice(0, 0, {
        field: "Number", title: "序号", halign: 'center', align: 'center',
        formatter: function (value, row, index) {
            var options = $table.bootstrapTable("getOptions");
            return (options.pageNumber - 1) * (options.pageSize) + index + 1;
        }
    });
    return newColumn;
}

function loadingBootstrapTable(method, $table, url, column, queryparams) {
    initBootstrapTable(method, $table, url, column, queryparams, true, "application/x-www-form-urlencoded");
}

//页面加载时 ，加载  由我发起流程table
function initBootstrapTable(method, $table, url, column, queryparams, singleSelect, contentType, toolbar) {
    $table.bootstrapTable('destroy');
    $table.bootstrapTable({
        method:method,
        contentType: contentType,
        //是否显示行间隔色
        toolbar: toolbar,
        striped: true,
        //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        cache: false,
        //是否显示分页（*）
        pagination: true,
        //是否启用排序
        sortable: true,
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
        url:url,
        singleSelect: singleSelect,
        /* //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
       //queryParamsType:'',
       //查询参数,每次调用是会带上这个参数，可自定义
       queryParams: function (params) {
         var subcompany = $('#subcompany option:selected').val();
         var name = $('#name').val();
         return {
           pageNumber: params.offset + 1,
           pageSize: params.limit,
           companyId: subcompany,
           name: name
         };
       },*/
        // showRefresh: true,
        clickToSelect: true,
        paginationShowPageGo:true,
        queryParams:queryparams,
        responseHandler:function (result){
            console.log("rrrrrrrrrrrrrrrrrrrrrrrrrr        ",result.content.total)

            var model = result.content;
            var total = model.total;
            var data = model.list;
            //如果没有错误则返回数据，渲染表格
            /*return {
                total : total, //总页数,前面的key必须为"total"
                data : data[0] //行数据，前面的key要与之前设置的dataField的值一致.
            };*/
            return model;
        },
      onLoadError:function (status) {
        sessionout(status);
        return false;
      },
        uniqueId: "flowId",
        columns: column
    });

}

// //获得选中的流程并给其填充 信息（由我审批）
function getclickFlow($element) {
    $('#lending_approval_tbody').empty();

    var data = {
        flowId: $element.flowId,
        deptId: $element.depId,
        reelNum: $element.reelNum,
        reelType: $element.reelType
    };
    console.log("depId      " + $element.depId);
    $.axx({
        type:'POST',
        url:"/flowLending/details",
        data:data,
        success:function (json) {
            // $('#lending_approval_tbody').empty();
            var model = json.content;
            var reel = model.flowFormLending.lendingModel;
            var deptName = model.dept;
            var reelType  = model.flowFormLending.reelType;
            if(reel!=null){
                console.log("reel title"+reel.fileName)
            }

            $('#lending_approval_tbody').append(
                '<tr>'+
                '<td>' + $element.title + '</td>' +
                '<td>' + reel.title + '</td>' +
                '<td>' + $element.reelNum + '</td>' +
                '<td>' + $element.lendingUser + '</td>' +
                // '<td>'+select_tr[0].flows.createdDt+'</td>'+
                '<td>'+deptName+'</td>'+
                '<td>' + $element.lendingPermission + '</td>' +
                '<td class="hidden" id="hidden_depId">'+model.flowFormLending.depId+'</td>'+
                '<td class="hidden" id="hidden_deptId">'+reel.deptId+'</td>'+
                '<td class="hidden" id="hidden_td">' + $element.flowId + '</td>' +
                '</tr>'
            );

            fileLendingInfo(reel,reelType);
            showFlowFormByReelType(reelType);
        }
    });

}

// //获得选中的流程并给其填充 信息（由我发起）
function getDblclickFlow(row) {
    $('#startbyme-tbody').empty();
    var data = {
        flowId: row.flowId,
        deptId: null,
        reelNum: row.reelNum,
        reelType: row.reelType
    };

    $.axx({
        type:'POST',
        url:"/flowLending/details",
        data:data,
        success:function (json) {

            var model = json.content;
            var reel = model.flowFormLending.lendingModel;
            var deptName = model.dept;
            var reelType  = model.flowFormLending.reelType;
            var lendingPer = model.flowFormLending.lendingPermission;
            if(reel!=null){
                console.log("reel title"+reel.title)
                console.log("reel type   " + lendingPer);
            }
            if (lendingPer == "电子版只读") {
                $("#downloadFlow").addClass("hidden");
            } else {
                $("#downloadFlow").removeClass("hidden");
            }
            var reelTypeName = reelType == "document" ? "文件" : reelType == "record" ? "案卷" : reelType == "archive" ? "档案" : "全宗";
            $('#startbyme-tbody').append(
                '<tr>'+
                '<td>'+reel.title+'</td>'+
                '<td>'+row.reelNum+'</td>'+
                '<td>'+reelTypeName+'</td>'+
                '</tr>'
            );
            $('#startbyme-tbody').children().on('dblclick',function () {
                if(reelType=='document'){
                    if (lendingPer == "电子版只读")
                        lookUploadFile(reel.documentLocalId, 4);
                    else
                        lookUploadFile(reel.documentLocalId, 2);
                    $('#wenJian_detail').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
                }
                if(reelType=='archive'){
                    if (lendingPer == "电子版只读")
                        getRecordFormAchives(reel.id, 4);
                    else
                        getRecordFormAchives(reel.id, 2);
                    $('#archive_detail').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
                    $('#archive_detail button').addClass("hidden");
                };
                if(reelType=='record'){
                    if (lendingPer == "电子版只读")
                        getDocumentFormRecord(reel.id, 4);
                    else
                        getDocumentFormRecord(reel.id, 2);
                    $('#records_detail').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
                    $('#records_detail button').addClass("hidden");
                };
                if(reelType=='box'){
                    $('.breadcrumb').removeClass("hidden");
                    if (lendingPer == "电子版只读")
                        getArchiveFormBoxs(reel.id, 4);
                    else
                        getArchiveFormBoxs(reel.id, 2);
                    $('#fond_detail').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
                    $('#fond_detail button').addClass("hidden");
                };
            })


            fileLendingInfo(reel,reelType);
            showFlowFormByReelType(reelType);
            $('#downloadFlow').off('click');
            $('#downloadFlow').on('click', function (event) {
                downloadFile2(reelType, reel);
            })
        }
    });
}

function downloadFile2(reelType, model) {
    if (model == null) return;
    if (reelType == 'document') {
        window.location = "/fileZipDownload1?reelType=document" + "&fileName=" + model.fileName +
            "&fileId=" + model.documentLocalId;
    } else if (reelType == 'record' || reelType == 'archive' || reelType == 'box') {
        window.location = "/fileZipDownload1?reelType=" + reelType + "&fileName=" + model.title +
            "&fileId=" + model.id;
    }
}

//给 reeltype 对应info 中输入值
function fileLendingInfo(model,reelType){
    $reel_inputs = $('.'+reelType+'Info input');
    console.log("aaaaa                ",'.'+reelType+'Info input')
    /*$.each(model, function (i, val) {
        console.log("model." + i + ":" + val);
    })*/
    console.log("reel_inpur     "+$reel_inputs[0]);
    console.log("2344455555"+$('.documentInfo input')[0]);
    console.log("111111111111"+$('#1111111111111'));
    if(reelType == "document"){
        $reel_inputs[0].setAttribute("value", model.fileName);
        $reel_inputs[1].setAttribute("value", model.title!=null?model.title:"");
        $reel_inputs[2].setAttribute("value", model.fondsId!=null?model.fondsId:"");
        $reel_inputs[3].setAttribute("value", model.fileNum!=null?model.fileNum:"");
        $reel_inputs[4].setAttribute("value", model.comeOffice!=null?model.comeOffice:"");
        $reel_inputs[5].setAttribute("value", model.archiveYear!=null?model.archiveYear:"");
        $reel_inputs[6].setAttribute("value", model.saveNum!=null?model.saveNum:"");
        $reel_inputs[7].setAttribute("value", model.reserveLocation!=null?model.reserveLocation:"");
        $reel_inputs[8].setAttribute("value", model.archiveDate!=null?model.archiveDate:"");
        $reel_inputs[9].setAttribute("value", model.fileCode!=null?model.fileCode:"");
        $reel_inputs[10].setAttribute("value", model.responsible!=null?model.responsible:"");
        $reel_inputs[11].setAttribute("value", model.importance!=null?model.importance:"");
        $reel_inputs[12].setAttribute("value", model.archiveType!=null?model.archiveType:"");
        $reel_inputs[13].setAttribute("value", model.beloadDepartment!=null?model.beloadDepartment:"");
        $reel_inputs[14].setAttribute("value", model.reserveDuration!=null?model.reserveDuration:"");
        $reel_inputs[15].setAttribute("value", model.archiveNum!=null?model.archiveNum:"");
        $reel_inputs[16].setAttribute("value", model.beforeNum!=null?model.beforeNum:"");
        $reel_inputs[17].setAttribute("value", model.documentCreateTime?model.documentCreateTime:"");
    }else{
        $reel_inputs[0].setAttribute("value", model.title!=null?model.title:"");
        $reel_inputs[1].setAttribute("value", model.reelNum!=null?model.reelNum:"");
        $reel_inputs[2].setAttribute("value", model.fileNum!=null?model.fileNum:"");
        $reel_inputs[3].setAttribute("value", model.importance!=null?model.importance:"");
        $reel_inputs[4].setAttribute("value", model.recordsLocation!=null?model.recordsLocation:"");
        $reel_inputs[5].setAttribute("value", model.responsible!=null?model.responsible:"");
    }

}
//弹出 批准/否决模态框时 触发
$('#shenpiyijian1').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget) // 触发事件的按钮
    var recipient = button.data('whatever') // 解析出data-whatever内容
    console.log("121212122        "+ recipient)
    if(recipient==null)return;
    $('#flow-modal-submit').unbind('click');
    $('#flow-modal-submit').one("click",function () {
        var description = $('#flow-modal-description').val();
        console.log("description          " + description);

        getRecipient(recipient,description);
    })

})

//弹出  状态 模态框时 触发
$('#flow-status').on('show.bs.modal',function (event) {
    var button = $(event.relatedTarget) // 触发事件的按钮
    var recipient = button.data('whatever') // 解析出data-whatever内容
    var flowId ;
    if(recipient==1)
        flowId = getFlowId($('#myLiuChengTable'));
    else if(recipient==2)
        flowId=document.getElementById('hidden_td').innerHTML;
    else if (recipient == 3)
        flowId = $('#form_destroy_end input[name=flowId]').val();
    else if (recipient == 4)
        flowId = getFlowId($('#sendDestoryFlow'));
    else if(recipient==42)
        flowId = getFlowId($('#endFeiQiByMeTable'));
    else if(recipient==5)
        flowId = getFlowId($('#turnOverTable'));
    else if(recipient==6)
        flowId = getFlowId($('#turnOverTomeTable'));
    else if(recipient==7)
        flowId = getFlowId($('#turnOverTomeEndTable'));
    else if(recipient==8)
        flowId = getFlowId($('#myHelpSearchBymeTable'));
    else if(recipient==9)
        flowId = getFlowId($('#myHelpSearchTomeTable'));
    else if(recipient==11)
        flowId = getFlowId($('#myLiuChengTable'));
    else if(recipient==12)
        flowId = getFlowId($('#myLiuCheng2Table'));
    if(flowId==null)return;

    getFlowStatus(flowId);
})

function getFlowId($table) {
    if ($table.bootstrapTable("getSelections").length != 1) {
        alert('请选择一个记录');
        throw "select error";
    } else {
        return $table.bootstrapTable("getSelections")[0].flowId;
    }
}

$('#flow-status').on('hidden.bs.modal', function (event) {
    $('#status-tbody1').empty();
})


//解析是那个按钮调的 modal
function getRecipient(recipient,description){
    if(recipient==1)
        endLendingFlow("ACCEPT",description);//批转借阅流程
    else if(recipient==2)
        endLendingFlow("REJECT",description);//否决借阅流程
    else if (recipient == 3)
        endDestroyFlow("ACCEPT", description);
    else if (recipient == 4)
        endDestroyFlow("REJECT", description);
}


//根据用户 获取 由我审批的流程  table
function getFlowProcessByme(mehtod,isProcessed,queryparams) {
    // $('.pagination').addClass("hidden")
    var $table = $('#myLiuCheng1Table');
    var url = "/flowLending/"+usrId+"/process/"+isProcessed;
    var column = flowProcessByMeCol;
    loadingBootstrapTable(mehtod,$table, url, column,queryparams);
    $table.bootstrapTable("hideColumn","flowId");
    $table.bootstrapTable("hideColumn","reelType");
    $table.bootstrapTable("hideColumn","depId");

    if(queryparams == searchQueryParams){
        $('#searchProcessByme').modal('hide');
    }

    //如果未审批的话  切换toolbar
    /*if(!isProcessed){
        $('#myLiuCheng1Table').unbind('click-row.bs.table');
        $('#myLiuCheng1Table').one('click-row.bs.table', function (row, $element) {
            /!*$.each(event, function (key, value) {
                console.log(key + "    " + value);
            })*!/
            $('#shenpi').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
            getclickFlow($element);
        });
    } else {
        $('#myLiuCheng1Table').unbind('click-row.bs.table');
    }*/
}

//获取未审批的流程
function getFlowWillProcessByme(){
    getFlowProcessByme('get',false,queryparams);
}

//获取已经审批 的流程
function getFlowProcessedByme(){
    // getFlowProcessByme('get',true,queryparams);
    console.log("339383982929283938");
    var url = "/flowLending/"+usrId+"/process/"+true;
    var column = flowProcessToMeCol;
    loadingBootstrapTable('get',$('#myLiuCheng2Table'), url, column,queryparams);
}

//获取状态信息（由我审批）
function getFlowStatus(flowId) {
    $('#status-tbody1').empty();
    console.log("flowId       "+flowId)
    $.get({
        url:"/flowLending/status/"+flowId,
        success:function (json) {
            var models = json.content;
            $('#status-tbody1').empty();
            console.log("models.leng     " + models.length);
            for(var i=0;i<models.length;i++){

                var action = models[i].action=="ACCEPT"?"批准":models[i].action=="REJECT"?"否决":"请求审批";

                if(models[i].action == null){
                    models[i].description="";
                    action="未审批";
                    models[i].createdDt="";
                }
                if(models[i].description==null){
                    models[i].description="";
                }
                $('#status-tbody1').append(
                    '<tr>'+
                    '<td>'+models[i].assigneeBy+'</td>'+
                    '<td>'+models[i].assignee+'</td>'+
                    '<td>'+action+'</td>'+
                    '<td>'+models[i].description+'</td>'+
                    '<td>'+models[i].createdDt+'</td>'+
                    '</tr>'
                )
            }
        }
    })
}

$('#selectFlowUser').on('show.bs.modal',function (event) {
    getAskApproveInfo();
})

//获取请求审批所需要的信息（本部门的审批成员和文件所在部门管理员）
function getAskApproveInfo() {
    $(".depApproUser").empty();
    $(".fileDepApproUser").empty();
    $(".companyLead").empty();
    var deptId = document.getElementById('hidden_depId').innerHTML;
    var fileDeptId = document.getElementById('hidden_deptId').innerHTML;
    $.get({
        url:"/flowLending/"+deptId+"/files/"+fileDeptId,
        success:function (json) {
            var model = json.content;
            var depApproUser = model.reel;
            var fileDepApproUser = model.file;
            var companyLead = model.lead;

            for(var i = 0;i<depApproUser.length;i++){
                $(".depApproUser").append(
                    '<div class="checkbox">'+
                    '<label><input type="checkbox" value="'+depApproUser[i].id+'">'+depApproUser[i].name+'</label>'+
                    '</div>'
                )
            }

            for(var j =0;j<fileDepApproUser.length;j++){
                $(".fileDepApproUser").append(
                    '<div class="checkbox">'+
                    '<label><input type="checkbox" value="'+fileDepApproUser[j].id+'">'+fileDepApproUser[j].name+'</label>'+
                    '</div>'
                )
            }

            for(var j =0;j<companyLead.length;j++){
                $(".companyLead").append(
                    '<div class="checkbox">'+
                    '<label><input type="checkbox" value="'+companyLead[j].id+'">'+companyLead[j].name+'</label>'+
                    '</div>'
                )
            }
        }
    })
}

//发起 请求审批
function askForApproval() {
    console.log("松紧带离开家分店");
    var selectedRadio =$('#askRadio input:radio:checked').val();
    console.log("selectedRadio");
    var checks=new Array();
    if(selectedRadio=='option1'){
        var $checked=$('.depApproUser input:checkbox:checked')
        for(var i=0;i<$checked.length;i++){
            checks[i]=$checked[i].value;
        }
    }else if(selectedRadio=='option2'){
        var $checked=$('.fileDepApproUser input:checkbox:checked')
        for(var i=0;i<$checked.length;i++){
            checks[i]=$checked[i].value;
        }
    }else if(selectedRadio=='option3'){
        var $checked=$('.companyLead input:checkbox:checked')
        for(var i=0;i<$checked.length;i++){
            checks[i]=$checked[i].value;
        }
    } else {
        alert("请选择审批类型");
        return;
    }
    console.log("checks  " + checks);
    if(checks.length==0){
        $('#askSure').removeClass("hidden");
        return;
    }

    var flowId = document.getElementById('hidden_td').innerHTML;
    var data ={
        checks:checks,
        usrId:usrId,
        flowId:flowId
    }

    $.axx({
        type:'POST',
        url:"/flowLending/process",
        data:data,
        success:function (json) {
            alert(json.content);
            $('#selectFlowUser').modal('hide');
            getFlowProcessByme('get',true,queryparams);
            $('#myLiuCheng1Table1').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
        }
    });

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
//根据用户获取 我发起的流程
function getFlowStartByme(method,queryparams){
    $('.pagination').addClass("hidden");
    var $table= $('#myLiuChengTable');
    var url =  "/flowLending/start/"+usrId;
    var column = flowByMeCol;
    initBootstrapTable(method,$table,url,column,queryparams,true, "application/x-www-form-urlencoded",'#toolBar1')
    // loadingBootstrapTable(method,$table,url,column,queryparams);
    $('#myLiuChengTable').bootstrapTable("hideColumn","flowId");
    // $('#myLiuChengTable').bootstrapTable("hideColumn","reelType");
}
//根据用户获取我发起的协查
function getFlowSearchByme(method,queryparams){
    // $('.pagination').addClass("hidden");
    var $table= $('#myHelpSearchByme');
    var url =  "/flowLending/start/"+usrId;
    var column = flowByMeCol;
    loadingBootstrapTable(method,$table,url,column,queryparams);
    $('#myHelpSearchBymeTable').bootstrapTable("hideColumn","flowId");
    $('#myHelpSearchBymeTable').bootstrapTable("hideColumn","reelType");
}
//根据用户获取我审批的协查
function getFlowSearchTome(method,queryparams){
    $('.pagination').addClass("hidden");
    var $table= $('#myHelpSearchTome');
    var url =  "/flowLending/start/"+usrId;
    var column = flowByMeCol;
    loadingBootstrapTable(method,$table,url,column,queryparams);
    $('#myHelpSearchTomeTable').bootstrapTable("hideColumn","flowId");
    $('#myHelpSearchTomeTable').bootstrapTable("hideColumn","reelType");
}
//获取部门管理员
function getDeptAdmin() {
    $.axx({
        type:'GET',
        url:"/roles/3/depts/"+deptId,
        success:function (json) {
            var models = json.content;
            // console.log(" dept name" + models[0].name);
            if(models.length>0){
                $('#deptadmin').empty();
                for(var i=0;i<models.length;i++){
                    $('#deptadmin').append(
                        '<option id="'+models[i].id+'">'+models[i].name+'</option>'
                    );
                }
            }
        }
    });
}

$('#jieYue input').focus(function(){

    $('#vaildlending').addClass("hidden");
});

//双击由我发起的流程table时，显示具体内容列表dbl-click-row.bs.table
/*$('#myLiuChengTable').on('dbl-click-row.bs.table',function (e, row, $element) {
    $('#myLiuChengDetail').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
    getDblclickFlow(row);
})*/

//根据不同的 reelType 显示不同的flowForm模态框
function showFlowFormByReelType(reelType){
    var reelInfo = "."+reelType+"Info";
    $("#flowForm "+reelInfo).removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
}

//请求审批时 ，选则不同的单选框
$('.radio-inline>input').click(function(){
    var optionSelect=$(this).attr("href")
    $("."+optionSelect).removeClass("hidden").siblings().addClass("hidden");
});


//搜索  准备请求参数
var searchQueryParams=function(params){
    var checkInput= new Array();
    var $modal =$('#searchProcessByme input:text')
    for(var i = 0;i<$modal.length;i++){
        checkInput[i]=$modal[i].value;
    }
    for(var j=0;j<checkInput.length;j++){
        $modal[j].value="";
    }
    return{
        pageSize : params.pageSize, //每一页的数据行数，默认是上面设置的10(pageSize)
        pageNum : params.pageNumber,
        searchInfo:checkInput
    }
}

//非搜索时 所用的参数
var queryparams = function(params) {
    console.log("pageNumber", params.pageNumber);
    console.log("pageorder", params.order);
    return{
        pageSize : params.pageSize, //每一页的数据行数，默认是上面设置的10(pageSize)
        pageNum: params.pageNumber,
    }
}
//测试时 用的  程序没用到
function getSearchInfo() {
    var checkInput= new Array();
    var $modal =$('#searchProcessByme input:text')
    for(var i = 0;i<$modal.length;i++){
        console.log(i+"      "+$modal[i].value);
        checkInput[i]=$modal[i].value;
    }
    var option = $('#searchProcessByme input:radio:checked').val();
    console.log("option     " + option);
    console.log("checkInput   " + checkInput);
}

//由我审批  搜索
function searchProcessByme() {
    //option1 待审批 option2 已审批 false 待审批 true 已审批
    var option = $('#searchProcessByme input:radio:checked').val();
    if(option=="option1")
        getFlowProcessByme('post',false,searchQueryParams);
    if(option=="option2")
        getFlowProcessByme('post',true,searchQueryParams);

}

//由我发起 搜索
function searchStartByme() {
    var searchQueryParams=function(params){
        var checkInput= new Array();
        var $modal =$('#searchStartByme input:text')
        for(var i = 0;i<$modal.length;i++){
            checkInput[i]=$modal[i].value;
        }
        for(var j=0;j<checkInput.length;j++){
            $modal[j].value="";
        }
        return{
            pageSize : params.pageSize, //每一页的数据行数，默认是上面设置的10(pageSize)
            pageNum : params.pageNumber,
            searchInfo:checkInput
        }
    }

    getFlowStartByme('post',searchQueryParams);
    $('#searchStartByme').modal('hide');
}


var accessLogObj;
//访问日志
var accessLogCol = [{field: "user.name", title: "用户名"}, {field: "accessType", title: "访问类型"},
    {field: "description", title: "描述"}, {field: "time", title: "访问时间"}];


function extractShowLog(strId, objId, objType) {
    var queryParams = function (param) {
        return {
            "pageNum": param.pageNumber,
            "pageSize": param.pageSize,
            "strId": strId,
            "objId": objId,
            "objType": objType
        };
    }
    createAccessLogTable(queryParams);
    $('#accessLog').modal('show');
}

function showAccessLog() {
    var objId;
    var strId;
    var objType;
    var accessType;
    var name;

    var selectTr = $('#fromLocalTable').bootstrapTable("getSelections");
    if (selectTr.length != 1) {
        alert("请选择一条记录");
        throw "check error";
    }
    strId = selectTr[0].documentLocalId;
    objId = selectTr[0].id;
    objType = "文档";
    accessLogObj = {strId: strId, objId: objId, objType: objType};
    extractShowLog(strId, objId, objType);
}


$('#accessLog').on('hide.bs.modal', function (event) {
    $('#accessLogTable').bootstrapTable('destroy');
})

function createAccessLogTable(queryParams) {
    $('#accessLogTable').bootstrapTable('destroy');
    var url = "/accessLog/list";
    $('#accessLogTable').bootstrapTable({
        contentType: "application/x-www-form-urlencoded",
        method: 'post', url: url, columns: accessLogCol, cache: false, pagination: true,
        sortable: false, queryParamsType: '', totalField: 'total', dataField: 'list',
        sidePagination: "server", queryParams: queryParams, responseHandler: function (result) {
            return result.content;
        }
    })
}

$('#accessLog input').keydown(function (event) {
    if (event.keyCode == 13) {
        accessLogSearch();
        /*var name = $('#searchAccessLog1').val();
        // var accessType = $('#searchAccessLog2').val();
        var accessType=$('#accessLogSelect option:selected').text();
        alert(accessType);
        return
        if($.trim(name)=="" && $.trim(accessType)==""){
            alert("请输入至少一个搜索条件");
            return
        }

        var queryParams1 = function(param){
            /!*var data = {
                user:{name:name},accssType:accessType,strId:accessLogObj.strId,objId:accessLogObj.objId,objType:accessLogObj.objType,
                pageNum:param.pageNumber,pageSize:param.pageSize
            }*!/
            return {

                pageNum:param.pageNumber,
                pageSize:param.pageSize,
                strId:accessLogObj.strId,
                objId:accessLogObj.objId,
                objType:accessLogObj.objType,
                accessType:accessType,
                name:name,
                // user:{name:name}
                // name:name
            };
        }

       createAccessLogTable(queryParams1);*/
    }
})


function accessLogSearch() {

    var name = $('#searchAccessLog1').val();
    var accessType = $('#accessLogSelect option:selected').text();

    if (accessType == "全部") {
        accessType = null;
    }

    var queryParams1 = function (param) {
        /*var data = {
            user:{name:name},accssType:accessType,strId:accessLogObj.strId,objId:accessLogObj.objId,objType:accessLogObj.objType,
            pageNum:param.pageNumber,pageSize:param.pageSize
        }*/
        return {

            pageNum: param.pageNumber,
            pageSize: param.pageSize,
            strId: accessLogObj.strId,
            objId: accessLogObj.objId,
            objType: accessLogObj.objType,
            accessType: accessType,
            name: name,
            // user:{name:name}
            // name:name
        };
    }

    createAccessLogTable(queryParams1);

}


function docsBack(obj) {
    var $tbody;
    if (obj == 1)
        $tbody = $('#reelDetail');
    else if (obj == 2)
        $tbody = $('#archiveDetail');
    else if (obj == 3)
        $tbody = $('#fondDetail');

    var i = 0;
    i = getSelectCount($tbody);
    if (i != 1) {

        alert("请选择一条记录");
        throw "select no one";
    }

    if (listType == "#records") {
        var confirm = window.confirm("您确定要回退  " + documentModel.fileName + " 文件吗");
        if (!confirm)
            return;
        var docId = documentModel.id;
        var recordId1 = recordModel.id;
        console.log("docsBack         " + docId + recordId1);
        $.axx({
            type: 'get',
            url: "/records/" + recordId1 + "/remove/" + docId,
            data: {},
            success: function (json) {
                alert("回退成功");
                getDocumentFormRecord(recordId1, 1);
            }
        });
    } else if (listType == "#archives") {
        var confirm = window.confirm("您确定要回退  " + recordModel.title + " 案卷吗");
        if (!confirm)
            return;
        var recordId2 = recordModel.id;
        var archiveId1 = archiveModel.id
        console.log("recBack         " + archiveId1 + recordId2);
        $.axx({
            type: 'get',
            url: "/archives/" + archiveId1 + "/remove/" + recordId2,
            data: {},
            success: function (json) {
                alert("回退成功");
                getRecordFormAchives(archiveId1, 1);
            }
        });
    } else if (listType == "#fond") {
        var confirm = window.confirm("您确定要回退  " + archiveModel.title + " 档案吗");
        if (!confirm)
            return;
        var archiveId = archiveModel.id;
        var fondId = fondModel.id;
        console.log("arcBack         " + fondId + archiveId);
        $.axx({
            type: 'get',
            url: "/boxs/" + fondId + "/remove/" + archiveId,
            data: {},
            success: function (json) {
                alert("回退成功");
                getArchiveFormBoxs(fondId, 1);
            }
        });
    }
}

$('#lendingInfo').on('show.bs.modal',function (event) {
    var button = $(event.relatedTarget);
    var recipient = button.data('whatever');
    var select;
    if(recipient==1)
        select= checkVaild($('#myLiuChengTable'));
    else if(recipient==2)
        select = checkVaild($('#myLiuCheng2Table'));
    $('#LDInfoDocs').bootstrapTable({columns: TOInfoDocsCol});
    $('#LDInfoRecs').bootstrapTable({columns: TOInfoRecsCol});
    getLendingInfo(select[0].flowId);
})

function getLendingInfo(flowId) {
    $.axx({
        type:'get',
        url:'/flowLending/'+flowId+'/detail',
        data:{},
        success:function (json) {
            var flowFormLending = json.content;
            var documentsList = flowFormLending.documentsList;
            var recordList = flowFormLending.recordList;
            if(documentsList.length!=0){
                $('#LDInfoBody1').removeClass("hidden");
                $('#LDInfoDocs').bootstrapTable('load', documentsList);
            }else {
                $('#LDInfoBody1').addClass("hidden");
            };
            if(recordList.length!=0){
                $('#LDInfoBody2').removeClass("hidden");
                $('#LDInfoRecs').bootstrapTable('load', recordList);
            }else{
                $('#LDInfoBody2').addClass("hidden");
            };
        }
    })
}


