var currentFlowId="";
var jumpDataForAssist;
$(function () {
        //to_assist();
    });

//待办跳转成功后，删除掉redis中的记录
function delPropelFromOA() {
    $.axx({
        url:'/redis/insJump/',
        type:'put',
        data:{},
        success:function (json) {
            console.log(json.content);
        }
    })
}

function getQueryStringFromRedis(name,value) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = value.match(reg);
    if(r!=null)return  decodeURI(r[2]); return null;
}
 function to_assist(){
	 console.log("to_assist");
	 $.axx({
         url:'/redis/insJump/',
         type:'get',
         data:{},
         success:function (json) {
             var redisMap = json.content;
             if (redisMap!=null) {
                 var jumpto = redisMap.jump_to;
                 var jumpData = redisMap.jump_data;
                 console.log("jumpto: " + jumpto + " jumpData: " + jumpData);
                 console.log("00" == jumpto);
                 if("00" == jumpto){
                     console.log("inside23043294")
                     jumpDataForAssist = jumpData;
                     var assist = getQueryStringFromRedis("assist", jumpData);
                     console.log("inside 111111 :")+assist;
                     // $('#getTurnOverToMe').trigger("click");
                     getTurnOverToMe();
                 } else if ("01" == jumpto){
                     var flowId = jumpData.split("_")[0];
                     var type = jumpData.split("_")[1];
                     if(FLOWSTYPE_LENDING==type){
                         remainLending(flowId);
                     }else if(FLOWSTYPE_DELIVER==type){
                         remainTurnOver(flowId);
                     }else if(FLOWSTYPE_ASSIST==type){
                         remainAssist(flowId);
                     }else if(FLOWSTYPE_DESTROY==type){
                         remainDestroy(flowId);
                     }
                     delPropelFromOA();
                 }
             }
         }
     })
 	/*var assist = GetQueryString("assist");
 	 console.log("to_assist");
 	 if(assist){
	 	$('#getTurnOverToMe').trigger("click");
	 	 console.log("to_assist");
	 	//$("#assistLending").click();
	 	getTurnOverToMe();
 	 }*/
 }

// 发起协查  返回
function  goBackInAssist() {
    $("#myHelpSearchByme").removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
}
function  goBackInAssist2() {
    $("#myHelpSearchTome").removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
}
function startAssist(){
	var assigneeId = $("#assitUser").find("option:selected").val();
	    if(assigneeId.trim()==''){
	        alert("请选择协查人");
	        return;
	    }
	    console.log(assigneeId);
	    startAssistBy(assigneeId);
}
function startAssistBy(assigneeId){
    var deliver = $('#searchHelpStart').serializeJson();
    console.log(deliver);
    

    deliver.assigneeId = assigneeId;
    console.log(deliver);
    
    //if(1==1) return;
    var data = {flowFormDeliver: deliver};
    $.axx({
        type:'post',
        url:'/flowFormAssist',
        data:deliver,
        success:function (json) {
            alert("发起协查成功");
            $("#myHelpSearchByme").removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
            getTurnOverFromMe();
        }
    })
}
function restartAssistBy(){
	var assigneeId = $("#select_CAdmin").find("option:selected").val();
    if(assigneeId.trim()==''){
        alert("请选择协查人");
        return;
    }
    
    var deliver = $('#searchHelpProcess').serializeJson();
    console.log(deliver);
    

    deliver.assigneeId = assigneeId;
    // deliver.descri
    console.log(deliver);
    
    //if(1==1) return;
    var data = {flowFormDeliver: deliver};
    $.axx({
        type:'post',
        url:'/flowFormAssist/restart',
        data:deliver,
        success:function (json) {
            alert("发起协查成功");
            $("#myHelpSearchByme").removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
            getTurnOverFromMe();
        }
    })
}

//获取部门管理员
function getAssistAdmin() {

    $.axx({
        type:'GET',
        url:"/roles/3/depts/"+deptId,
        success:function (json) {
            var models = json.content;
            console.log(json);
            // console.log(" dept name" + models[0].name);
            if(models.length>0){
                $('#assitUser').empty();
                for(var i=0;i<models.length;i++){
                    $('#assitUser').append(
                        '<option value="'+models[i].id+'">'+models[i].name+'</option>'
                    );
                }
            }
        }
    });
}
function getCAdmin(dept) {
	$("#select_CAdmin").removeClass("hidden");
    $.axx({
        type:'GET',
        url:"/roles/3/depts/"+dept,
        success:function (json) {
            var models = json.content;
            console.log(json);
            // console.log(" dept name" + models[0].name);
            if(models.length>0){
                $('#select_CAdmin').empty();
                for(var i=0;i<models.length;i++){
                    $('#select_CAdmin').append(
                        '<option value="'+models[i].id+'">'+models[i].name+'</option>'
                    );
                }
            }
        }
    });
}
var assistCol_1=[
	//{"field":"flowId","title":"id"},
	{"field":"checkStatus","checkbox":true},
    {
        field: "Number", title: "序号", halign: 'center', align: 'center',
        formatter: function (value, row, index) {
            var options = $('#myHelpSearchBymeTable').bootstrapTable("getOptions");
            return (options.pageNumber - 1) * (options.pageSize) + index + 1;
        }, width: "5%"
    },
    {"field":"createdDt","title":"发起时间"},
     {"field":"endDt","title":"结束时间"},
    {"field": "deadLine", "title": "截止时间"},
    {"field":"assistType","title":"协查类型"},
    {"field": "result", "title": "结果", visible: false}];

var assistCol_2=[
    //{"field":"flowId","title":"id"},
    {"field":"checkStatus","checkbox":true},
    {
        field: "Number", title: "序号", halign: 'center', align: 'center',
        formatter: function (value, row, index) {
            var options = $('#myHelpSearchTomeTable').bootstrapTable("getOptions");
            return (options.pageNumber - 1) * (options.pageSize) + index + 1;
        }, width: "5%"
    },
    {"field":"createdDt","title":"发起时间"},
    {"field":"endDt","title":"结束时间"},
    {"field": "deadLine", "title": "截止时间"},
    {"field":"assistType","title":"协查类型"},
    {"field": "result", "title": "结果", visible: false}];

var assistCol_3=[
    //{"field":"flowId","title":"id"},
    {"field":"checkStatus","checkbox":true},
    {
        field: "Number", title: "序号", halign: 'center', align: 'center',
        formatter: function (value, row, index) {
            var options = $('#myHelpSearchTomeBeforTable').bootstrapTable("getOptions");
            return (options.pageNumber - 1) * (options.pageSize) + index + 1;
        }, width: "5%"
    },
    {"field":"createdDt","title":"发起时间"},
    {"field":"endDt","title":"结束时间"},
    {"field": "deadLine", "title": "截止时间"},
    {"field":"assistType","title":"协查类型"},
    {"field": "result", "title": "结果", visible: false}];
window.delete_assist_doc = {
	    'click .remove': function (e, value, row, index) {
	    	console.log(row);
	    	console.log(currentFlowId);
	    	console.log(row.type);
	    	var id="";
	    	var type=row.type;
	    	if(row.documentLocalId){
	    		id=row.documentLocalId;
	    	}else{
	    		id=row.id	    		
	    	}
	    	if(currentFlowId){
	    	    $.axx({
	    	        type: 'get',
	    	        url: '/flowFormAssist/deldoc/' + currentFlowId + '/'+id+"/"+type,
	    	        data: {},
	    	        success: function (json) {
	    	            
	    	        }
	    	    });  
	        }
	        $('#assist_grid').bootstrapTable('remove', {
	            field: 'id',
	            values: [row.id]
	        });
	    }
	};

var docCol=[
	{"field":"status","checkbox":true},
    {"field": "Button",
        "title": "操作",
        events: delete_assist_doc,
        formatter: function (value, row, index) {
            return [
                '<a class="remove" href="javascript:void(0)" title="Remove">',
                '<i class="glyphicon glyphicon-remove"></i>',
                '</a>'
            ].join('');
        },
        align: 'center'
    },
    { field: 'id',
        title: '序号',
        align:"center"
    },
    {
        field:'type',
        title:'类型',
        formatter:function (value,row,index) {
            var type = row.type;
            if(type=='doc'){
                return '文件';
            }else if(type=='record'){
                return '卷级档案';
            }else if(type =='recdoc'){
                return '文件';
            }
        }
    },
	{ field: 'fileNum',
     title: '档号',
     align:"center"
 }, 
 {
 	field: 'title',
     title: '题名'
 },
 {
	    field: 'themeWord',
     title: '主题词'
 },
 {
	    field: 'location',
  title: '路径'
},{
    field: 'reserveLocation',
    title: '存放位置'
  },
    {"field": "lendFlagDesc", "title": "结果"}];
function getTurnOverFromMe(){
        var url= "flowFormAssist/start/"+usrId;
	    console.log("songjianqiang");
	    $("#myHelpSearchBymeTable").bootstrapTable('destroy');
	    $("#myHelpSearchBymeTable").bootstrapTable({
	        //contentType : "application/x-www-form-urlencoded",
            url:url,
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
            queryParams:queryparams,
	        totalField: 'total',
	        dataField: 'list',
	        singleSelect: true,
	        editable: true,
	        // showRefresh: true,
	        clickToSelect: true,
            paginationShowPageGo:true,
            responseHandler:function (result) {
                return result.content;
            },
            onLoadError:function (status) {
                sessionout(status);
                return false;
            },
	       // uniqueId: "flowId",
	        columns: assistCol_1
	    });
	  //  $('#sendDestoryFlow').bootstrapTable("hideColumn", "flowId");
	}


//由我审批（未审批）
function getTurnOverToMe() {
	 console.log("getTurnOverToMe1");
    var url1 = "/flowFormAssist/01/process";
    console.log("getTurnOverToMe2");
    //initBootstrapTable('get', $('#myHelpSearchTomeTable'), url, assistCol, queryparams, false, "application/x-www-form-urlencoded", '#turnOverToolbar2');
    
    console.log("songjianqiang");
    $("#myHelpSearchTomeTable").bootstrapTable('destroy');
    $("#myHelpSearchTomeTable").bootstrapTable({
        //contentType : "application/x-www-form-urlencoded",
        url:url1,
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
        queryParams:queryparams,
        totalField: 'total',
        dataField: 'list',
        singleSelect: true,
        editable: true,
        // showRefresh: true,
        clickToSelect: true,
        paginationShowPageGo:true,
        responseHandler:function (result) {
            return result.content;
        },
        onLoadError:function (status) {
            sessionout(status);
            return false;
        },
       // uniqueId: "flowId",
        columns: assistCol_2
    });

    if(jumpDataForAssist){
        flowId=getQueryStringFromRedis("flowId",jumpDataForAssist);
        assistId=getQueryStringFromRedis("assist",jumpDataForAssist);
        recordtype=getQueryStringFromRedis("recordtype",jumpDataForAssist);
        console.log("abc "+flowId);
        console.log("abc "+assistId);
        if(flowId){
            console.log("----------insert---------");
            $('#searchHelpProcess').removeClass('hidden').hide().fadeIn(500).siblings().addClass("hidden");
            getInfoDoc(flowId,assistId,recordtype);
        }
        jumpDataForAssist=null;
        //删除redis中的insideJump
        $.axx({
            url:"/redis/insJump",
            type:"put",
            data:{},
            success:function (json) {
                console.log("insJump delete");
            }
        })
    }
  //  $('#sendDestoryFlow').bootstrapTable("hideColumn", "flowId");
  //   $('#myHelpSearchTomeTable').bootstrapTable("hideColumn","reelType");
    /*$.axx({
        type:"GET",
        url:url1,
        data:{
            "offset":0,
            "limit":10
           
        },
        dataType:"json",
        success:function (json) {
           
            $('#myHelpSearchTomeTable').bootstrapTable('load', json.content.list);
            $('#myHelpSearchTomeTable').bootstrapTable('hideColumn', "flowId");
            console.log("xxxx     is ok             ");
            var url_now=location.search;
            console.log(url_now);



        },

        error:function () {

        }
    });*/
    
    console.log("getTurnOverToMe3");

    
}

//由我审批（已审批）
function getTurnOverToMeEnd() {

	 console.log("getTurnOverToMe1");
	    var url1 = "/flowFormAssist/02/process";
	    console.log("getTurnOverToMe2");
	    //initBootstrapTable('get', $('#myHelpSearchTomeTable'), url, assistCol, queryparams, false, "application/x-www-form-urlencoded", '#turnOverToolbar2');

	    console.log("songjianqiang");
	    $("#myHelpSearchTomeBeforTable").bootstrapTable('destroy');
	    $("#myHelpSearchTomeBeforTable").bootstrapTable({
	        //contentType : "application/x-www-form-urlencoded",
            url:url1,
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
            queryParams:queryparams,
	        totalField: 'total',
	        dataField: 'list',
	        singleSelect: true,
	        editable: true,
	        // showRefresh: true,
	        clickToSelect: true,
	       // uniqueId: "flowId",
            paginationShowPageGo:true,
            responseHandler:function (result) {
                return result.content;
            },
            onLoadError:function (status) {
                sessionout(status);
                return false;
            },
	        columns: assistCol_3
	    });
	  //  $('#sendDestoryFlow').bootstrapTable("hideColumn", "flowId");
	    console.log("getTurnOverToMe3");
}

function endAssist(row) {
    $.axx({
        url:'/flowFormAssist/end/REJECT',
        type:'get',
        data:{flowId:row.flowId},
        success:function (json) {
            getTurnOverToMeEnd();
            $("#myHelpSearchTomeBefore").removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
        }
    })
}

function initAssistToolbar(row) {
    /*$('#datetimepicker1').on('hide',function(e) {
        var name = $(this).children('input').attr("name");
        $(this).parents('form').data('bootstrapValidator').updateStatus(name, 'NOT_VALIDATED', null).validateField(name);
        /!*$('#createLending').data('bootstrapValidator')
            .updateStatus('deadLine', 'NOT_VALIDATED',null)
            .validateField('deadLine');*!/
    });*/

    $('#lend_create_submit').unbind('click').on('click',function (e) {
        console.log("lend_create_submid click");
        //获取表单对象
        var bootstrapValidator = $('#createLending').data('bootstrapValidator');
        //手动触发验证
        bootstrapValidator.validate();
        if (bootstrapValidator.isValid()) {
            lendingStart(row);
        }
    });

    $('#ATE_status').unbind('click').on('click',function (e) {
        getFlowStatus(row.flowId);
        $('#flow-status').modal('show');
    });

    $('#btn_endAssist').unbind('click').on('click',function (e) {
        endAssist(row);
    })
}

$('#myHelpSearchTomeTable').on('dbl-click-row.bs.table',function (e,row, $element, field) {

    $('#searchHelpProcess').removeClass('hidden').hide().fadeIn(500).siblings().addClass("hidden");
    getInfo(row.flowId,true);
    initAssistToolbar(row);

    $('#ATE_back').unbind('click').on('click',function (e) {
        getTurnOverToMe();
        $('#myHelpSearchTome').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
    });

});
$('#myHelpSearchBymeTable').on('dbl-click-row.bs.table',function (e,row, $element, field) {

    $('#searchHelpProcess').removeClass('hidden').hide().fadeIn(500).siblings().addClass("hidden");
    getInfo(row.flowId,false);

    $('#ATE_back').unbind('click').on('click',function (e) {
        getTurnOverFromMe();
        $('#myHelpSearchByme').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
    });
    $('#ATE_status').unbind('click').on('click',function (e) {
        getFlowStatus(row.flowId);
        $('#flow-status').modal('show');
    })
});
//发起借阅
function lendingStart(row) {
    var assitFlowId = row.flowId;
    var flowFormLending = $("#createLending").serializeJson();
    var title = flowFormLending.title;
    var lendingPermission = flowFormLending.lendingPermission;
    var description = flowFormLending.description;
    var deadLine = flowFormLending.deadLine;
    var assigneeId = row.assitUser;
    var selectTr = $('#assist_grid').bootstrapTable('getSelections');
    var revIds =[];
    var docIds = [];
    $.each(selectTr,function (index,val) {
        var type = val.type;
        if(type=='doc'||type=='recdoc'){
            docIds.push(val.id);
        }else if(type =='record'){
            revIds.push(val.id);
        }
    })

    var data = {title:title,lendingPermission:lendingPermission,description:description, deadLine: deadLine, assigneeId: assigneeId, revIds: revIds,docIds:docIds,depId:deptId,assitFlowId:assitFlowId};
    $.axx({
        type:'post',
        url:'/flowLending',
        data:data,
        success:function (json) {
            alert("发起成功");
            getFlowStartByme('get',queryparams);
            $('#myLiuChengTable1').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
            $('#jieYue').modal('hide');
            $('#createFileForm').data('bootstrapValidator').resetForm();
        }
    });
}

$('#jieYue').on('show.bs.modal',function () {
    var selectTr = checksVaild($('#assist_grid'));
    $.each(selectTr,function (index,val) {
        if(val.lendFlag=="02"){
            alert("已借阅文件不能再次借阅");
            throw "can't again lending";
        }
    })
    $('#createLending textarea[name=description]').val($('#description').val());
})

$('#ATE_back').on('click',function (e) {
    getTurnOverToMeEnd()
    $('#myHelpSearchTomeBefore').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
})

$('#myHelpSearchTomeBeforTable').on('dbl-click-row.bs.table',function (e,row, $element, field) {

    $('#searchHelpProcess').removeClass('hidden').hide().fadeIn(500).siblings().addClass("hidden");
    getInfo(row.flowId,false);

    $('#ATE_back').unbind('click').on('click',function (e) {
        getTurnOverToMeEnd();
        $('#myHelpSearchTomeBefore').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
    });
    $('#ATE_status').unbind('click').on('click',function (e) {
        getFlowStatus(row.flowId);
        $('#flow-status').modal('show');
    })
});

function getInfo(flowId,toMe) {
	currentFlowId=flowId;
    $.axx({
        type: 'get',
        url: '/flowFormAssist/' + flowId + '/detail',
        data: {},
        success: function (json) {
            var form = json.content;
            setForm(form);

            initButton(form,toMe);
        }
    })
    var url = "/flowFormAssist/searchdoc/_/"+flowId+"/_";
    $("#assist_grid").bootstrapTable('destroy');
    initBootstrapTable('get', $('#assist_grid'), url, docCol, queryparams, false, "application/x-www-form-urlencoded");
    if(!toMe){
        $('#assist_grid').bootstrapTable("hideColumn", "Button");
    }
}

function initButton(form,toMe){
    var createdBy = form.createdBy;
    var assistUser = form.assitUser;
    $('#ATE-toolbar2').addClass("hidden");
    $("#btn_restartAssistBy").addClass("hidden");
    $('#fs_content').attr("disabled", true);
    $('#select_CAdmin').attr("disabled", true);
    $('#assit_user_select').addClass("hidden");
    if(toMe){
        $('#select_CAdmin').attr("disabled", false);
        $('#ATE-toolbar2').removeClass("hidden");
        $('#ATE-toolbar2 button').addClass("hidden");

        if(usrId==createdBy){
            $("#btn_restartAssistBy").removeClass("hidden");
            $("#ATE_ask").removeClass("hidden");
            $('#fs_content').attr("disabled", false);
            $('#assit_user_select').removeClass("hidden");
            $('#btn_endAssist').removeClass("hidden");
            getCAdmin(deptId);
        }

        if(usrId==assistUser){
            $("#btn_toassist").removeClass("hidden");
            $("#btn_tocreatedby").removeClass("hidden");
            if(deptId!=KMHK_DEPT_ID){
                $('#assit_user_select').removeClass("hidden");
                $("#btn_tocadmin").removeClass("hidden");
                getCAdmin(KMHK_DEPT_ID);
            }
        }

    }
    /*if(toMe){
        $('#select_CAdmin').attr("disabled", false);
        $("#ATE-toolbar1").addClass("hidden");
        $("#ATE-toolbar2").removeClass("hidden");
    }else{
        console.log("createBy:"+form.createdBy);
        var createdBy = form.createdBy;
        $('#select_CAdmin').attr("disabled", true);
        $("#ATE-toolbar1").removeClass("hidden");
        $("#ATE-toolbar2").addClass("hidden");
        if(usrId==createdBy){
            $('#ATE_ask').removeClass("hidden");
        }else{
            $('#ATE_ask').addClass("hidden");
        }
    }
	//hidden
	$("#btn_toassist").addClass("hidden");
	$("#btn_tocreatedby").addClass("hidden");
	$("#btn_tocadmin").addClass("hidden");
	$("#btn_restartAssistBy").addClass("hidden");
	$('#fs_content').attr("disabled", true);
	if (toMe){
		if(roleId==3 && deptId==KMHK_DEPT_ID){
			//去协查 和发起人
			$("#btn_toassist").removeClass("hidden");
			$("#btn_tocreatedby").removeClass("hidden");
		}else if(roleId ==3){
			//去协查 发起人 公司管理员
			$("#btn_toassist").removeClass("hidden");
			$("#btn_tocreatedby").removeClass("hidden");
			$("#btn_tocadmin").removeClass("hidden");
			 getCAdmin(KMHK_DEPT_ID);
		}else{		
			//提交
			$("#btn_restartAssistBy").removeClass("hidden");
			getCAdmin(deptId);
			$('#fs_content').attr("disabled", false);
		}
	} 	        */
}

function getInfoDoc(flowId,docIds,recordtype) {
	currentFlowId=flowId;
    $.axx({
        type: 'get',
        url: '/flowFormAssist/' + flowId + '/detail',
        data: {},
        success: function (json) {
            var form = json.content;
            setForm(form);
            
            initButton(form,true);
        }
    });
    var url = "/flowFormAssist/searchdoc/"+docIds+"/"+flowId+"/"+recordtype;
    $("#assist_grid").bootstrapTable('destroy');
    initBootstrapTable('get', $('#assist_grid'), url, docCol, queryparams, false, "application/x-www-form-urlencoded");
    $('#ATE_back').unbind('click').on('click',function (e) {
        getTurnOverToMe();
        $('#myHelpSearchTome').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
    });

    $('#ATE_status').unbind('click').on('click',function (e) {
        getFlowStatus(flowId);
        $('#flow-status').modal('show');
    });
}

function setForm(jsonValue) {  
    
    var obj=$("#searchHelpProcess");  
    $.each(jsonValue, function (name, ival) { 
    	$("#"+name).val(ival);
        console.log("name "+name);
        console.log("ival "+ival);
   });  
};
function toSearch(){
	assistContent = $("#assistContent").val();
	
	console.log(assistContent);
	var flowId = $("#flowId").val();
	console.log(flowId);
	//保存至redis
    var val = "searchText="+encodeURI(assistContent)+"&flowId="+flowId+"&type=1";
    $.axx({
        url:'/redis',
        type:'post',
        data:{esMark:val},
        success:function () {
	        window.location.href ="searchgoogle";
        }
    });
}
function toCreatedBy(){
	console.log("toCreatedBy");
	$.axx({
        type:"GET",
        url:"/flowFormAssist/process",
        data:{
            "flowId":$("#flowId").val(),
            "action":"to_createdy",
            "userId":$("#createdBy").val()
        },
        dataType:"json",
        success:function (json) {
            console.log("toCreatedBy.onsuccess");
            process_reurl();
        },

        error:function () {

        }
    });
}
function process_reurl(){
	/*window.location.href ="main";
	$('#getTurnOverToMe').trigger("click");
	 console.log("process_reurl");
	getTurnOverToMe();*/
    $('#myHelpSearchTomeBefore').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
    getTurnOverToMeEnd();
}
function toCAdmin(){
	console.log("toCAdmin");

	$.axx({
        type:"GET",
        url:"/flowFormAssist/process",
        data:{
            "flowId":$("#flowId").val(),
            "action":"to_CAdmin",
            "userId":$("#select_CAdmin").val()
        },
        dataType:"json",
        success:function (json) {
            console.log("toCAdmin.onsuccess");
            process_reurl();
        },

        error:function () {

        }
    });
}
function objToTurnOverForm(goods) {

//    var deliverDept = goods.deliverDept;
//    var receiveDept = goods.receiveDept;
//    var obj ={title:goods.title,deliverUser:goods.deliverUser,deliverOffice:goods.deliverOffice,
//    deliverDept:deliverDept.name,receiveDept:receiveDept.name,description:goods.description};
//    for (var name in obj) {
//        if (obj.hasOwnProperty(name)) {
//            $("#turnOverEnd input[name='" + name + "']:not(:file)").val(obj[name]);
//            $("#turnOverEnd textarea[name='" + name + "']").val(obj[name]);
//        }
//    }

}