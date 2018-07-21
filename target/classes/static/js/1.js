
var usrId;
var username;
var loginName;
var deptId;
var deptName;
var roleId;
var roleName;
var simpleDeptName;


var users=[//整理档案，上传，审批
    ["test01",123,"recordsManagement",["all"]],//档案管理员
    ["test02",123,"archiveArrangement",["all"]],//档案整理员
    ["test03",123,"sectionRecordsManagement",["部门1"]],//部门档案管理员
    ["test04",123,"sectionArchiveArrangement",["部门1"]],//部门档案整理理员
    ["test05",123,"upLoad",["部门1"]],//上传人员
    ["test06",123,"readOnly",["部门1"]]//可见人员
];

var listType;
var archiveFileCol =  [{"field":"status","checkbox":true},{"field":"fileName","title":"文件名"},{"field":"fileCode","title":"件号"},{"field":"id","title":"id"}];
var archiveRecordCol =   [{"field":"status","checkbox":true},{"field":"title","title":"文件名"},{"field":"reelNum","title":"档号"},{"field":"id","title":"id"}];
var arr=[];
/*function role(role){
    if(role=="archiveArrangement"){}
    if(role=="sectionRecordsManagement"){}
    if (role == "sectionArchiveArrangement") {
    }
    if(role=="upLoad"){
        $('.zLdA').remove();
    }
    if(role=="readOnly"){
        $('.zLdA,.sC').remove();
    }
}*/
$(document).keydown(function(event){
    if (event.keyCode == 13) {
        $('form').each(function() {
            event.preventDefault();
        });
    }
});


window.onload=function(){

    $.ajaxSetup({ cache: false });
    $("#toolbar").hide;
    documentModel = null;
    initDestroyInfoTable();
    getSessionUser();
    // getTree();

}

function getSessionUser() {
  $.axx({
    url: ARCHIVE_API.user_detail,
    type: 'get',
    data: {},
    dataType: 'json',
    success: function (json) {
      contextUser = json.content;
      usrId = contextUser.id;
      username = contextUser.name;
      loginName = contextUser.loginName;
      $('#loginName').html(username);
      loadingUser(usrId);
      getLink();
      getInformation();
      getPicture();
      remainToBeDone();
      to_assist();
      var password = contextUser.password;
        console.log("password"+password);
      if(password==INIT_PASSWORD){
          $('#setpassword').attr('data-backdrop', false).attr('data-keyboard',false);
          $('#setpassword button[data-dismiss="modal"]').addClass("hidden");

//          $('#setpassword').modal('show');
      };
        initAssistTable();
    }
  })
}


function loadingUser(userId) {
    $.axx({
        type: 'get',
        url: "/users/loading/" + userId,
        data: {},
        success: function (json) {
            var model = json.content;
            deptId = model.dept_id;
            deptName = model.deptname;
            simpleDeptName = model.description;
            roleId = model.role_id == null ? ROLE_ID_EVERYONE : model.role_id;
            roleName = model.rolename == null ? "普通用户" : model.rolename;
            /*$.each(model, function (key, value) {
                console.log(key + "     " + value);
            })*/
            if (roleId == ROLE_ID_MANAGER || roleId == ROLE_ID_ARRANGE) {
                $('.notglobal').removeClass("hidden");
            } else {
                $('.notglobal').remove();
            }
            if (!(roleId == ROLE_ID_MANAGER || roleId == ROLE_ID_DEPT_MANAGER)) {
                $('.deptAdmin').remove();
            }
            if (!(roleId == ROLE_ID_MANAGER || roleId == ROLE_ID_ARRANGE || roleId == ROLE_ID_DEPT_MANAGER || roleId == ROLE_ID_DEPT_ARRANGE)) {
                $('.upLoadMan').remove();
            }
            if(roleId == ROLE_ID_UPLOAD){
                $('#easyUser').siblings().not(".easyUser").not('#files_system').remove();
                $('.readOnly').remove();
                $('#btn_add').siblings().remove();
            }
            if (roleId == ROLE_ID_EVERYONE) {
                $('#easyUser').siblings().not(".easyUser").remove();
                $('.readOnly').remove();
                $('#collapseTwo').removeClass("collapse");//协查借阅展开
                // $('#assistToHome').trigger('click');
            }
            getTree();
            getDeptAdmin();
            getFolderTree();
        }
    })

}

function remainAssist(flowId){
    $('#searchHelpProcess').removeClass('hidden').hide().fadeIn(500).siblings().addClass("hidden");
    $.axx({
        type: 'get',
        url: '/flowFormAssist/' + flowId + '/detail',
        data: {},
        success: function (json) {
            var form = json.content;
            setForm(form);
            initButton(form,true);
            initAssistToolbar(form);
            var url = "/flowFormAssist/searchdoc/_/"+flowId+"/doc";
            initBootstrapTable('get', $('#assist_grid'), url, docCol, queryparams, false, "application/x-www-form-urlencoded");
        }
    });
    $('#ATE_back').unbind('click').on('click',function (e) {
        backToHome();
    });
}

function remainTurnOver(flowId) {
    $.axx({
        type: 'get',
        url: '/flowFormDeliver/' + flowId + '/detail',
        data: {},
        success: function (json) {
            var flowFormDeliver = json.content;

            showToolbarByUserInTO(flowFormDeliver);
            $('#turnOverEnd').removeClass('hidden').hide().fadeIn(500).siblings().addClass("hidden");
            createTOEndDocsAndRecs();

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
            initTurnOverToolbar(flowFormDeliver);
            $('#TOE_back').unbind('click').on('click',function (e) {
                backToHome();
            });
        }
    })
}

function remainLending(flowId){
    $('#LendingEnd').removeClass('hidden').hide().fadeIn(500).siblings().addClass("hidden");
    getLendingInfoForEnd(flowId);
    $('#LDE_back').unbind('click').on('click',function (e) {
        backToHome();
    })
}

function getDesInfoForRemain(flowId) {
    $.axx({
        type:"get",
        url: '/flowDestroy/'+flowId+'/details',
        data:{},
        dataType:"json",
        success:function (json) {
            var flowFormDestroy = json.content;
            var documentsList = flowFormDestroy.documentsList;
            var recordList = flowFormDestroy.recordList;
            var flows = flowFormDestroy.flows;
            if(usrId!=flows.createdBy){
                $('#xiaoHuiEnd button[data-target="#selectDetoryFlow"]').addClass("hidden");
                $('#askEnd').addClass("hidden");
            }else{
                $('#xiaoHuiEnd button[data-target="#selectDetoryFlow"]').removeClass("hidden");
                $('#askEnd').removeClass("hidden");
            }
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

function  remainDestroy(flowId) {
    getDesInfoForRemain(flowId);
    $('#xiaoHuiEnd').removeClass('hidden').hide().fadeIn(500).siblings().addClass("hidden");
    $('#del_back').unbind('click').on('click',function (e) {
        backToHome();
    })

}

function remainToBeDone() {
    $.axx({
        url:'/users/remain',
        type:'get',
        data:{},
        success:function (json) {
            var models = json.content;
            var toBeDoneModels = models.toBe;
            var giveBackModels = models.giveBack;
            $('#home_box5_ul').empty();
            $.each(toBeDoneModels,function (index,obj) {
                var id = obj.flow_id;
                var name = obj.name;
                var type = obj.type;
                var created_dt = obj.created_dt;
                var str ="<a href='#'>"+created_dt+" " +name+" 向您分配了一个"+type+"流程，请您尽快处理。</a>";
                var li = document.createElement("li");
                li.setAttribute("id", "remind_"+id+"_"+type);
                li.innerHTML = str;
                $('#home_box5_ul').append(li);
            });
            $.each(giveBackModels,function (index,obj) {
                var id = obj.id;
                console.log("222222"+id)
                var lendingUser = obj.lending_user;
                var deadLine = obj.dead_line;
                var str = "<a href='#'> " + name + " 借阅的实体文件于 " + deadLine + " 到期,请您确认他是否归还.</a>"
                var li = document.createElement("li");
                li.setAttribute("id", "remind_" + id);
                li.innerHTML = str;
                $('#home_box5_ul').append(li);
            });
            $('#home_box5_ul li').unbind('click').on('click',function (e) {
                var oldId = this.id;
                var arr = oldId.split("_");
                if(arr.length==3){
                    var newId = oldId.split("_")[1];
                    var type = oldId.split("_")[2];
                    console.log("li id:"+ newId+" type:"+type);
                    if(type=="借阅"){
                        remainLending(newId);
                    }else if(type=="移交"){
                        remainTurnOver(newId);
                    }else if(type=="协查"){
                        remainAssist(newId);
                    }else if(type=="销毁"){
                        remainDestroy(newId);
                    }
                }else if(arr.length==2){
                    $('#giveBack').modal('show');
                    $('#give_back_btn').unbind('click').on('click',function (e) {
                        var newId = oldId.split("_")[1];
                        var url = 'flowLending/'+newId+'/giveBack';
                        $.axx({
                            type:'get',
                            url:url,
                            data:{},
                            success:function (json) {
                                $('#giveBack').modal('hide');
                                remainToBeDone();
                            }
                        })
                    })
                }

            })
        }
    })
}

function getLink() {
  $.axx({
    url: "/homePage/getInformation",
    type: "GET",
    dataType: "json",
    success: function (json) {
    	console.log('getLink');
    	console.log(json.content.list);
        var models = json.content.list;
        $('#home_box6_ul').empty();
        $.each(models,function (index,obj) {
            var id = obj.fileId;
            var url="/view?fileId=" + id  + "&fileName=";
          
            var str ="<a href='"+url+"' target='_blank'>"+obj.content+"</a>";
            var li = document.createElement("li");
            li.setAttribute("id", id);
            li.innerHTML = str;
            $('#home_box6_ul').append(li);
            
        });
        
    }
  });
}

function getInformation() {
  $.axx({
    url: "/homePage/getLink",
    type: "GET",
    dataType: "json",
    success: function (json) {
    	console.log('getLink');
    	console.log(json.content.list);
        var models = json.content.list;
        $('#home_box7_ul').empty();
        $.each(models,function (index,obj) {
            var id = obj.fileId;
            var url="/view?fileId=" + id  + "&fileName=";
          
            var str ="<a href='"+url+"' target='_blank'>"+obj.content+"</a>";
            var li = document.createElement("li");
            li.setAttribute("id", id);
            li.innerHTML = str;
            $('#home_box7_ul').append(li);
            
        });

    }
  });
}

function getPicture() {
    $.axx({
        type: 'get',
        url: "/homePictures",
        success: function (json) {
            var models = json.content;
            for (var i = 0; i < models.length; i++) {
                $('.carousel-inner img')[i].src = models[i].location;
                $('.carousel-inner').find('.carousel-caption')[i].innerHTML = models[i].content;
            }
        }
    })
}


/*档案左侧树状图*/
var models;
var pageNum=1;
function getTree() {

    // alert("usrId   "+usrId)
    var tree = [];
    var recycleNodes = [];
    $.axx({
        type:'GET',
        url: '/depts/' + usrId + "/byuser",
        data:{},
        dataType:'json',
        success:function(json){
            models = json.content;
            for(var i=0;i<models.length;i++){
                var nodes;
                if (roleId == ROLE_ID_MANAGER || roleId == ROLE_ID_ARRANGE || roleId == ROLE_ID_DEPT_MANAGER || roleId == ROLE_ID_DEPT_ARRANGE) {
                    nodes = [
                        {
                            text: "文件中心",
                            id: models[i].id,
                            deptName: models[i].name,
                            href: "#wenJian",
                            nodes: [
                                {
                                    text: "手工录入",
                                    id: models[i].id,
                                    deptName: models[i].name,
                                    description: models[i].description,
                                    tabName: "#fromLocal",
                                },
                                {
                                    text: "OA数据",
                                    id: models[i].id,
                                    deptName: models[i].name,
                                    description: models[i].description,
                                    tabName: "#fromOA",
                                }
                            ]
                        },
                        {
                            text: "件级档案",
                            id: models[i].id,
                            deptName: models[i].name,
                            description: models[i].description,
                            tabName: "#arranged"
                        },
                        {
                            text: "卷级档案",
                            id: models[i].id,
                            deptName: models[i].name,
                            description: models[i].description,
                            tabName: "#records"
                        }
                    ];
                } else if (roleId == ROLE_ID_UPLOAD){
                    nodes = [
                        {
                            text: "文件中心",
                            id: models[i].id,
                            deptName: models[i].name,
                            href: "#wenJian",
                            nodes: [
                                {
                                    text: "手工录入",
                                    id: models[i].id,
                                    deptName: models[i].name,
                                    description: models[i].description,
                                    tabName: "#fromLocal",
                                }
                            ]
                        }
                    ]
                } ;

                var nodes2 = [{
                    text: "所有",
                    id: models[i].id,
                    deptName: models[i].name,
                    href: "#0"
                },
                    {
                        text: "今天",
                        id: models[i].id,
                        deptName: models[i].name,
                        href: "#1"
                    },
                    {
                        text: "昨天",
                        id: models[i].id,
                        deptName: models[i].name,
                        href: "#2"
                    },
                    {
                        text: "前7天",
                        id: models[i].id,
                        deptName: models[i].name,
                        href: "#3"
                    },
                    {
                        text: "前15天",
                        id: models[i].id,
                        deptName: models[i].name,
                        href: "#4"
                    },
                    {
                        text: "前30天",
                        id: models[i].id,
                        deptName: models[i].name,
                        href: "#5"
                    },
                    {
                        text: "超过30天",
                        id: models[i].id,
                        deptName: models[i].name,
                        href: "#6"
                    }];
                tree.push(new obj(models[i].name,models[i].id,nodes));
                recycleNodes.push(new obj(models[i].name,models[i].id,nodes2));
            }
           departTree(tree);
            recycleTree(recycleNodes);
        }
    });

}
//树状对象
function obj(text,id,node){
    this.text=text;
    // console.log('id'+id);
    this.id=id;
    this.href='#';
    this.nodes=node;
}

function departTree(depart){
   var list = {
        bootstrap2: false,
        showTags: true,
        levels:0,
        enableLinks:true,
        selectedIcon:"glyphicon glyphicon-map-marker",
        showBorder:false,
        data: depart
    };
    $('#lefttree').treeview(list);
    // console.log(depart);

    //获取左侧树节点信息
    $('#lefttree').on('nodeSelected',function(event, data) {
        if (data.nodes != null) {
            var select_node = $('#lefttree').treeview('getSelected');
            if (select_node[0].state.expanded) {
                $('#lefttree').treeview('collapseNode', select_node);
                select_node[0].state.selected = false;
            }
            else {
                $('#lefttree').treeview('expandNode', select_node);
                select_node[0].state.selected = false;
            }
        } else {
            var num = $(this).index()
            deptId = data.id;
            var list = data.tabName;
            var text = data.text;
            //   console.log('id'+data.id);
            deptName = data.deptName;
            simpleDeptName=data.description;
            var str = "";
            listType = list;

            if (list == "#fromLocal") {
                getFromLocalTable();
            } else if (list == "#fromOA") {
                getFromOATable();
            } else if (list == "#arranged") {
                getArrangedTable();
            } else if (list == "#records") {
                getRecordTable();
            }
           
            var temporary = list == "#fromOA" ? "#fromLocal" : list;
            $(temporary).removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
            $("button[data-target='#biaoDan'],button[data-target='#modify'],.selectFile,.selectFile1").addClass("hidden");
            $('.breadcrumb').empty().append(
                "<li class='active' style='color: #4876FF' href=" + temporary + ">" + text +
                "</li>").removeClass("hidden");
        }
    });
}

//回收站部门列表
function recycleTree(nodes) {
    var list = {
        bootstrap2: false,
        showTags: true,
        levels:0,
        enableLinks:true,
        selectedIcon:"glyphicon glyphicon-map-marker",
        showBorder:false,
        data: nodes
    };
    $('#recycleOneTree').treeview(list);

    $('#recycleOneTree').on("nodeSelected",function (event,data) {
        if (data.nodes != null) {
            var select_node = $('#recycleOneTree').treeview('getSelected');
            if (select_node[0].state.expanded) {
                $('#recycleOneTree').treeview('collapseNode', select_node);
                select_node[0].state.selected = false;
            }
            else {
                $('#recycleOneTree').treeview('expandNode', select_node);
                select_node[0].state.selected = false;
            }
        } else {
            showRecycleBin(data.id,data.href.charAt(1));
            $('#recycleBin').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
        }
    })
}


//获取document列表方法

var currentIndex = -1;
var fileId ;
var  documentModel = null;
var documentModels ;
function  getDocumentList(page,list) {

    var data;
    var url;
    if(list=="#wenJianSerach"){
        var title = $("#searchFileTitle").val();
        var filecode = $("#searchFileCode").val();
        data={
                "title":title,
                "fileCode": filecode,
                "offset": page,
                "limit": 10
        }
        url="documents/search/"+deptId;
    }else {
        url = "/documents/page/"+deptId;
        data={
            "pageNum": page,
            "pageSize": 10
        }
    }

    $("#wenJian_1").empty();
    pageNum = page;

    console.log(deptId);
    $.axx({
        type:'GET',
        url:url,
        data:data,
        dataType:"json",
        success:function (json) {
            $("#searchFileTitle").val("");
            $("#searchFileCode").val("");

            $("#file").modal("hide");
            var documentList = json.content.list;
            documentModels = documentList;
            fenYe(json.content, list);
            for (var i = 0; i < documentList.length; i++) {

                var documentDetail = documentList[i];
                console.log(documentDetail);
                var tr = document.createElement('tr');
                var documentName = documentDetail.fileName;
                var documentCode = documentDetail.fileCode;
                var documentStatus = documentDetail.status;
                if (documentName == null) {
                    documentName = "";
                }
                if (documentCode == null) {
                    documentCode = "";
                }

                if (documentStatus == null) {
                    documentStatus = "";
                }
                tr.innerHTML = '<td>' + documentName + '</td>' +
                    '<td>' + documentCode + '</td>' +
                    '<td>' + documentStatus + '</td>';
                tr.style.background = "white";
                $("#wenJian_1").append(tr);
            }

            var arr = $("#wenJian_1").children();
            console.log(arr.length);
            for (var i = 0; i < arr.length; i++) {
                var a = arr[i];
                $(a).click(function () {
                    var index = $(this).index();
                    var document1 = documentList[index]
                    fileId = document1.id;
                    documentModel = document1;
                    message(document1,list);
                   // lookUploadFile(document1.documentLocalId);
                    //commitMessage(document1);
                    $("button[data-target='#file'],button[data-target='#accessLog'],button[data-target='#biaoDan'],.selectFile,.selectFile1").removeClass("hidden");
                    change(index,list);
                });
            }
        },
        error:function () {

        }
    });
}

//获取record列表
var recordModels ;
var archiveModels;
var fondModels;
var recordModel;
var archiveModel;
var fondModel;
var recordId;
var archiveId;
var fondId;
function getRecordList(page,list) {
    console.log("list："+list);
    var title = $("#searchRecordTitle").val();
    var reelNum = $("#searchRecordReelNum").val();
    //  var recordsLocation = $("#")


    $("#archives_1").empty();
    $("#records_1").empty();
    $("#fonds_1").empty();


    var data;
    var url;
    if(list=="#records"){
        url = "/records/page/" + deptId;
        data={
            "offset": page,
            "limit": 10
        }
    }else if(list=="#archives"){
        url = "/archives/page/" + deptId;
        data={
            "offset": page,
            "limit": 10
        }
    }else if(list=="#fond"){
        data={
            "offset": page,
            "limit": 10
        }
        url = "boxs/page/" + deptId;
    }else if(list=="#recordsSearch"){
        data={
            "title": title,
            "reelNum": reelNum,
            "deptId": deptId,
            "offset": page,
            "limit": 10
        }
        url = "records/search/"+deptId;
    }else if(list=="#archivesSearch"){
        data={
            "title": title,
            "reelNum": reelNum,
            "deptId": deptId,
            "offset": page,
            "limit": 10
        }
        url= "archives/search/"+deptId;
    }else if(list=="#fondsSearch"){
        data={
            "title": title,
            "reelNum": reelNum,
            "deptId": deptId,
            "offset": page,
            "limit": 10
        }
        url="boxs/search/"+deptId;
    }

    if(list=="#records"||list=="#recordsSearch"){

        console.log("songjianqiang");
        $.axx({
            type: 'GET',
            url: "/records/page/" + deptId,
            data: {
                "offset": page,
                "limit": 10
            },
            dataType: "json",
            success: function (json) {
                $("#searchRecordTitle").val("");
                $("#searchRecordReelNum").val("");
                $("#anjuan").modal("hide");
                var recordList = json.content.list;
                fenYe(json.content, list);
                for (var i = 0; i < recordList.length; i++) {
                    var record = recordList[i];
                    // <td>11111111111111</td>
                    //     <td>560001</td>
                    var tr = document.createElement("tr");
                    tr.innerHTML='<td>'+record.title+'</td>'+
                        '<td>'+record.reelNum+'</td>';
                    tr.style.background = "white";
                    $("#records_1").append(tr);
                }

                recordModels = recordList;
                var arr = $("#records_1").children();
                console.log(arr.length);
                for (var i = 0; i < arr.length; i++) {
                    $("#anjuan").modal("hide");
                    var a = arr[i];
                    $(a).click(function () {
                        var index = $(this).index();
                        var record1 = recordList[index]
                        //fileId = record1.id;
                        recordModel = record1;
                        message(record1,list);
                        //commitMessage(document1);
                        //documentModel = document1;
                        recordId = record1.id;
                        change(index,list);
                        $("button[data-target='#file'],button[data-target='#accessLog'],button[data-target='#biaoDan'],button[data-target='#modify'],.selectFile").removeClass("hidden");
                    });
                }
            },

            error: function () {

            }
        });
    }else if(list=="#archives"||list=="#archivesSearch"){

        console.log("songjianqiang");
        $.axx({
            type: 'GET',
            url: url,
            data: data,
            dataType: "json",
            success: function (json) {
                $("#anjuan").modal("hide");
                var archivesList = json.content.list;
                console.log("archivesList"+archivesList.length);
                fenYe(json.content, list);
                for (var i = 0; i <archivesList.length; i++) {

                    var archives = archivesList[i];
                    archiveModels = archivesList;
                    console.log("archives"+archives.title);
                    // <td>11111111111111</td>
                    //     <td>560001</td>

                    var tr = document.createElement("tr");
                    tr.innerHTML='<td>'+archives.title+'</td>'+
                        '<td>'+archives.reelNum+'</td>';
                    tr.style.background = "white";
                    $("#archives_1").append(tr);
                }

             //   archiveModel = archivesList;
                var arr = $("#archives_1").children();
                console.log(arr.length);
                for (var i = 0; i < arr.length; i++) {
                    var a = arr[i];
                    $(a).click(function () {
                        var index = $(this).index();
                        var record1 = archivesList[index];
                        //fileId = record1.id;
                        archiveModel = record1;
                        message(record1,list);
                        //commitMessage(document1);
                        //documentModel = document1;
                        archiveId = record1.id;
                        change(index,list);
                        $("button[data-target='#file'],button[data-target='#accessLog'],button[data-target='#biaoDan'],button[data-target='#modify'],.selectFile").removeClass("hidden");
                    });
                }
            },

            error: function () {

            }
        });
    }else if(list=="#fond"||list=="#fondsSearch"){

        console.log("songjianqiang");
        $.axx({
            type: 'GET',
            url: url,
            data: data,
            dataType: "json",
            success: function (json) {
                var recordList = json.content.list;
                fondModels = recordList;
                fenYe(json.content, list);
                for (var i = 0; i < recordList.length; i++) {
                    var record = recordList[i];
                    // <td>11111111111111</td>
                    //     <td>560001</td>
                    var tr = document.createElement("tr");
                    tr.innerHTML='<td>'+record.title+'</td>'+
                        '<td>'+record.reelNum+'</td>';
                    tr.style.background = "white";
                    $("#fonds_1").append(tr);
                }

               // recordModel = recordList;
                var arr = $("#fonds_1").children();
                console.log(arr.length);
                for (var i = 0; i < arr.length; i++) {
                    var a = arr[i];
                    $(a).click(function () {
                        var index = $(this).index();
                        var record1 = recordList[index]
                        //fileId = record1.id;
                        fondModel = record1;
                        message(record1,list);
                        //commitMessage(document1);
                        //documentModel = document1;
                        fondId = record1.id;
                        change(index,list);
                        $("button[data-target='#file'],button[data-target='#accessLog'],button[data-target='#biaoDan'],button[data-target='#modify'],.selectFile").removeClass("hidden");
                    });
                }
            },

            error: function () {

            }
        });
    }

}

function change(obj,list){
    console.log("松江"+list+obj);
    var arr = new Array();
    if(list=="#wenJian"||list=="#wenJianSerach"){
        // documentModel = null;
         arr = $("#wenJian_1").children();
    }else if(list=="#records"||list=="#recordsSearch") {
         arr = $("#records_1").children();
    }else if(list=="#recordDocument"){
        arr = $("#reelDetail").children();
    }else if(list=="#archives"||list=="#archivesSearch"){
        arr = $("#archives_1").children();
    }else if(list=="#fond"||list=="#fondsSearch"){
        arr = $("#fonds_1").children();
    }else if(list=="#archiveDetail"){
        arr = $("#archiveDetail").children();
    }else if(list=="#fondDetail"){
        arr = $("#fondDetail").children();
    }else if(list=="#mainfile"){
        arr = $("#main").children();
    }else if(list=="#accessoryFile"){
        arr = $("#accessory").children();
    }

    for (var i = 0; i < arr.length; i++) {
        var a = arr[i];

        a.style.background = "white";
        if(i==obj){
            if (list=="#mainfile"){
                $(a).css("background", "#ddd");
                //a[a.length-1].css("background-color" , "red");
            }else {
                a.style.background = "#ddd";
            }

        }
    };

    if (list == "#mainfile") {
        var arr2 = $("#accessory").children();
        for (var j = 0; j < arr2.length; j++) {
            arr2[j].style.background = "white";
        }
    } else if (list == "#accessoryFile") {
        var arr3 = $("#main").children();
        for(var k=0;k<arr3.length;k++){
            arr3[k].style.background = "white";
        }
    }
   // obj.style.background = "red";
}

//增加删除安全权限
$('#safe_add_1').click(function(){
    $('#safeadd').modal();
});
//用户树
function getSafeTree() {
    var tree = [
        {
            text: "Parent 1"
        },
        {
            text: "Parent 2"
        },
        {
            text: "Parent 3"
        }
    ];
    return tree;
}
$(function(){
    var options = {
        showTags: true,
        levels:0,
        data: getSafeTree()
    };
    $('#safeTree').treeview(options);
});
//

//增加
$('#safe_add_2').click(function(){
    $('#safe_list').append(
        "<tr><td><span class='glyphicon glyphicon-user'></span>"+"123"+"</td><td><div class='input-group input-group-sm'> <select class='form-control'><option>"+"阅读"+"</option> <option>"+"可见"+"</option> <option>"+"上传"+"</option> <option>"+"修改"+"</option> <option>"+"最高控制"+"</option> </select> </div> </td> <td style='text-align: center'><span class='glyphicon glyphicon-trash'></span></td> </tr>"
    );
});
//删除
$('#safe .glyphicon-trash').click(function(){
    $(this).parent().parent().remove();
});

//文档列表切换
$('#cabinet').on('click','tr',function(){
    var a=$(this).attr("href");
    $(a).removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
});
//流程切换
$('#jieyueliucheng').on('click','li',function(){
    var a=$(this).attr("href");
    $(a).removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
});
$('#feiQi').on('click','li',function(){
    var feiQiName=$(this).attr("href");
    $(feiQiName).removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
    $('.shuxing').fadeOut(300);
});
//库房切换
$('#storeroom').on('click','li',function () {
    var a = $(this).attr("href");
    $(a).removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
})
//审批切换
$('#newShenP').click(function(){
    $('#shen_1').removeClass("hidden").siblings("tbody").addClass("hidden");
});
$('#oldShenP').click(function(){
    $('#shen_2').removeClass("hidden").siblings("tbody").addClass("hidden");
});
//调销毁审批界面
$('#newFeiQi>tbody').on('click','tr',function(){
    console.log("1");
    $('#delShenPi').removeClass("hidden").hide().fadeIn(500).siblings("table").addClass("hidden");
});

//由我发起的流程
$('#myLiuChengTable>tbody').on('dblclick','tr',function(){
    $('#myLiuChengDetail').removeClass("hidden").hide().fadeIn(500).siblings("table").addClass("hidden");
});
//经我审批待审批
/*$('#shen_1').on('click','tr',function(){
    $('#shenpi').removeClass("hidden").hide().fadeIn(500).siblings("table").addClass("hidden");
});*/
$('#datareport').on('click','li',function(){
    var a=$(this).attr("href");
    $(a).removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
});

//分页
var fenYe=function(content,list){
    $('.pagination').removeClass("hidden");
    $('.pagination').html("");
    var pageCount = content.pages;
    var prePage = content.prePage;
    var nextPage = content.nextPage;
    var pageNum = content.pageNum;
    var hasPreviousPage = content.hasPreviousPage;
    var hasNextPage = content.hasNextPage;
    var navigatepageNums = content.navigatepageNums;
    console.log(pageCount+'prePage'+prePage+nextPage);
    var page=pageCount;

    // $('.pagination').append('<li><a>'+pageNum+"/"+pageCount+'</a></li>');
    if(hasPreviousPage==true){
        $('.pagination').append('<li><a href=' + list + ' onclick=gotoPage(' + 1 + ')>首页</a></li>');
        $('.pagination').append('<li><a href='+list+' onclick=gotoPage('+prePage+')>&laquo;</a></li>');
    }

    for (var i = 0; i < navigatepageNums.length; i++) {
        $('.pagination').append('<li><a href=' + list + ' onclick=gotoPage(' + navigatepageNums[i] + ')>' + navigatepageNums[i] + '</a></li>');
    }
    if(hasNextPage==true){
        $('.pagination').append('<li><a href='+list+' onclick=gotoPage('+nextPage+')>&raquo;</a></li>');
        $('.pagination').append('<li><a href=' + list + ' onclick=gotoPage(' + pageCount + ')>尾页</a></li>');
    }

    var index = getIndex(navigatepageNums, pageNum);
    if (hasPreviousPage == true)
        index += 2;
    $(".pagination  a").eq(index).css({"background": "#CCC"});
    // $(".pagenow").text(pageNum+"/"+pageCount);
}

function getIndex(pageArr, now) {
    var index = 0;
    for (var i = 0; i < pageArr.length; i++) {
        if (pageArr[i] == now) {
            index = i;
        }
    }
    console.log("iiiiiiii       " + index);
    return index;
}

//翻页
function gotoPage(page) {
    console.log('page'+page);
    console.log("listType"+listType);
    if(listType=="#wenJian"){
        getDocumentList(page,listType);
    }else {
        getRecordList(page,listType);
    }

}
//查看表单
$('.fileBiao').click(function(){
    $('#biaoDan_1').empty();
    $('#biaoDan_2').empty();
    $('#biaoDan_1').append(
        '<div class="form-group">'+
        '<label class="col-sm-2 control-label">请输入密码</label>'+
        '<div class="col-sm-10">'+
        '<input type="text" class="form-control" value="请输入密码" disabled>'+
        '</div>'+
        '</div>'
    );
    $('#biaoDan_2').append(
        '<li class="list-group-item"><a href="#">11111</a></li>'
    );
});
var documentFile;
//文件具体内容
$('#wenJian_1').on('dblclick','tr',function(){
    $('#wenJian_detail').removeClass("hidden").siblings("table").addClass("hidden");
    var index = $(this).index();
    var document = documentModels[index];
    $('.breadcrumb').append("<li href='#wenJian_detail'>" +document.fileName+
        "</li>");
    $('.breadcrumb:last').addClass("active").siblings().removeClass("active");
    listType="#wenJian";
    lookUploadFile(document.documentLocalId, 1);

    documentFile = document;
    //面包屑

});
//案卷具体内容
$('#records_1').on('dblclick','tr',function(){

    $('#records_detail').removeClass("hidden").siblings("table").addClass("hidden");
    var index = $(this).index();
    listType="#records";
    var model = recordModels[index];
    getDocumentFormRecord(model.id, 1);
    $("button[data-target='#file'],button[data-target='#biaoDan'],button[data-target='#accessLog'],.docBack,.selectFile,.selectFile1").addClass("hidden");
    $('.breadcrumb').append("<li href='#records_detail'>" +model.title+
        "</li>");
    $('.breadcrumb:last').addClass("active").siblings().removeClass("active");
});

//档案具体内容
$('#archives_1').on('dblclick','tr',function () {
    $("#archive_detail").removeClass("hidden").siblings("table").addClass("hidden");
    var index = $(this).index();
    listType="#archives";
    var model = archiveModels[index];
    getRecordFormAchives(model.id, 1);
    $("button[data-target='#biaoDan'],button[data-target='#accessLog'],.docBack,.selectFile ,.selectFile1").addClass("hidden");
    $('.breadcrumb').append("<li href='#archive_detail'>" +model.title+
        "</li>");
    $('.breadcrumb:last').addClass("active").siblings().removeClass("active");
})

//全宗具体内容
$('#fonds_1').on('dblclick','tr',function () {
    $("#fond_detail").removeClass("hidden").siblings("table").addClass("hidden");
    var index = $(this).index();
    var model = fondModels[index];
    listType="#fond";
    $("button[data-target='#biaoDan'],button[data-target='#accessLog'],.docBack,.selectFile,.selectFile1").addClass("hidden");
    getArchiveFormBoxs(model.id, 1);
    $('.breadcrumb').append("<li href='#fond_detail'>" +model.title+
        "</li>");
    $('.breadcrumb:last').addClass("active").siblings().removeClass("active");
})
//案卷中文件具体内容
$('#reelDetail').on('dblclick','tr',function () {
    $('#wenJian_detail').removeClass("hidden").siblings("table").addClass("hidden");
   //  var index = $(this).index();
   //  var document = recordModel[index];
   //  listType="#records";
   // getDocumentFormRecord(document.id);
    $('.breadcrumb').append("<li href='#wenJian_detail'>" +documentModel .fileName+
        "</li>");
    $('.breadcrumb:last').addClass("active").siblings().removeClass("active");
});

$('#archiveDetail').on('dblclick','tr',function () {
    $("#records_detail").removeClass("hidden").siblings("table").addClass("hidden");
    $("button[data-target='#file'],button[data-target='#biaoDan'],button[data-target='#accessLog'],.docBack,.selectFile,.selectFile1").addClass("hidden");
    // $("#records_detail button")
    $('.breadcrumb').append("<li href='#records_detail'>" +documentModel.title+
        "</li>");
    $('.breadcrumb:last').addClass("active").siblings().removeClass("active");
})

$('#fondDetail').on('dblclick','tr',function () {
   $("#archive_detail").removeClass("hidden").siblings("table").addClass("hidden");
    $("button[data-target='#biaoDan'],button[data-target='#accessLog'],.docBack,.selectFile,.selectFile1").addClass("hidden");
    $('.breadcrumb').append("<li href='#archive_detail'>" +documentModel .title+
        "</li>");
    $('.breadcrumb:last').addClass("active").siblings().removeClass("active");
})


$('#input-1a').fileinput({
    language: 'zh', //设置语言
    uploadUrl: "/fileUpload",
    uploadAsync: true,
//                      allowedFileExtensions: ['jpg', 'gif', 'png'],//接收的文件后缀
    showUpload: true, //是否显示上传按钮
    showCaption: true,//是否显示标题
    browseClass: "btn btn-primary", //按钮样式
    maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
    //minFileCount: 0,
//   maxFileCount: 10, //表示允许同时上传的最大文件个数
//   msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！"
    uploadExtraData: function (previewId, index) {

        var obj = {
            reserveLocation: documentFile.savaLocation,
            docId: documentFile.documentLocalId,
            fileType: "main"
        };
        return obj;
        // "fileName":$('#fileName').val(),
        // var data = {
        //
        // };
        // return data;
        // ?"+"reserveLocation="+documentFile.reserveLocation+"&docId="+documentFile.documentLocalId
        //  +"&fileType=accessory
    },
    layoutTemplates: {
        actionDelete: '',
        actionUpload: '',
    }
});

$('#input-1b').fileinput({
    language: 'zh', //设置语言
    uploadUrl: "/fileUpload",
    // "fileName":document.getElementById('fileName').innerHTML,
    uploadAsync:true,
    showPreview:true,
//                      allowedFileExtensions: ['jpg', 'gif', 'png'],//接收的文件后缀
    showUpload: true, //是否显示上传按钮
    showCaption: true,//是否显示标题
    browseClass: "btn btn-primary" ,//按钮样式
    //maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
    //minFileCount: 0,
//                       maxFileCount: 10, //表示允许同时上传的最大文件个数
//                       msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！"

    uploadExtraData:function (previewId,index) {

        var obj={
            reserveLocation:documentFile.savaLocation,
            docId:documentFile.documentLocalId,
            fileType:"accessory"
        };
        return obj;
        // "fileName":$('#fileName').val(),
        // var data = {
        //
        // };
        // return data;
       // ?"+"reserveLocation="+documentFile.reserveLocation+"&docId="+documentFile.documentLocalId
          //  +"&fileType=accessory
    },
    /*layoutTemplates:{
        // actionDelete:'',
        actionUpload:'',
    }*/
});

// $('.fileinput-upload-button').addClass("hidden");

function displayButton($table, recipient) {
    if (recipient == 1)
        $("#records_detail button").find(".retrieve,.download");
}


//上传文件


//删除文件
function deleteFile() {
    var i = getSelectFile();
    if (i != 1) {
        alert("请选择一条记录");
        return;
    }

    if (documentFile.status == "已完成") {
        alert("文件已整理，不允许删除");
        return;
    }
    var confirm = window.confirm("您确定要删除  " + fileModel.fileName + "  吗");
    if (!confirm)
        return;

    var data = {id: fileModel.id, location: fileModel.location, docId: documentFile.id};
    $.axx({
        type: "POST",
        url: "documents/files",
        data: data,
        success: function (json) {
            alert("删除成功");
            lookUploadFile(documentFile.documentLocalId, 1);
        },
        error: function (json) {
            alert("删除失败");
            lookUploadFile(documentFile.documentLocalId, 1);
        }
    })
}

$(function () {
    validIsSelectButton($('#biaoDan'));
    // validIsSelectButton($('#file'));
});

//对下载的文件做重要程度判断
function importantValid(model) {
    if (!(roleId == 1 || roleId == 2 || roleId == 3 || roleId == 4)) {
        var importance = model.importance;
        if (importance == "重要") {
            alert("下载请走借阅流程");
            throw "no permission";
        }
    }

}

//对下载按钮做 检验判断
$(".selectFile").on('click', function (event) {
    var $table = $(this).parents('table');
    var $tbody = $table.find('tbody');
    var id = $tbody[0].id;
    var j;
    console.log("tbody[]   " + $(this).attr("data-whatever"));
    if ($table[0].id != "wenJian_detail") {
        j = getSelectCount($tbody);

    } else {
        j = getSelectFile();
        // $('#wenJian_detail ')
    }

    console.log("j    ", j);
    if (j != 1) {
        alert("请选择一条记录");
        throw "select no one";
    }
    /*$.each(this,function (key,val) {
        console.log("tbody    "+key +"    "+val)
    })*/

    var isFlow = $(this).attr("data-whatever");
    if (id == "wenJian_1" || id == "reelDetail")
        downLoadFile('wenJian', isFlow);
    else if (id == "records_1" || id == "archiveDetail")
        downLoadFile("record", isFlow);
    else if (id == "archives_1" || id == "fondDetail")
        downLoadFile('archives', isFlow);
    else if (id == "fonds_1")
        downLoadFile('fond', isFlow);
    else
        downLoadFile('wenJianDetail', isFlow);

})

//根据不同的按钮 做检验判断
function validIsSelectButton($modal) {
    $modal.on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // 触发事件的按钮
        var recipient = button.data('whatever') // 解析出data-whatever内容
        var target = button.data('target');

        var i = 0;
        if (recipient == 1 || target == '#file')
            i = getSelectCount($('#wenJian_1'));
        else if (recipient == 2)
            i = getSelectCount($('#records_1'));
        else if (recipient == 3)
            i = getSelectCount($('#archives_1'));
        else if (recipient == 4)
            i = getSelectCount($('#fonds_1'));
        else if (recipient == 5)
            i = 1;
        else if (recipient == 6)
            i = getSelectCount($('#reelDetail'));
        else if (recipient == 7)
            i = getSelectCount($('#archiveDetail'));
        else if (recipient == 8)
            i = getSelectCount($('#fondDetail'));
        else
            i = 1;

        console.log("getSelectCount    " + i + "   " + recipient);

        if (i != 1) {

            alert("请选择一条记录");
            throw "select no one";
        }
    })
}

//检验当前表格中是否有tr 选中


//遍历 table tbody 的tr 看是否有选中的
function getSelectCount($tbody) {
    var i = 0;
    $tbody.find('tr').each(function () {
        if (this.style.background.substr(0, 5) != "white")
            i++;
        console.log("background    " + this.style.background)
    })
    return i;
}

function getSelectFile() {
    var i = 0;
    $('#wenJian_detail li').each(function () {
        if (this.style.background.substr(0, 5) != "white")
            i++;
    });
    return i;
}


function message(model,list) {
    if(list=="#wenJian"||list=="#wenJianSerach"){
        $("#documentMessage").empty();
        $("#title").val(model.fileName);
        $("#themeword").val(model.themeWord);
        $("#fondsid").val(model.fondsId);
        $("#appendices").val(model.appendices);
        $("#comeoffice").val(model.comeOffice);
        $("#archiveyear").val(model.archiveYear);
        $("#savenum").val(model.saveNum);
        $("#reservelocation").val(model.reserveLocation);
        $("#archivedate").val(model.archiveDate);
        $("#filecode").val(model.fileCode);
        $("#responsible").val(model.responsible);
        $("#importance").val(model.importance);
        $("#archivetype").val(model.archiveType);
        $("#beloadepartment").val(model.beloadDepartment);
        $("#reserveduration").val(model.reserveDuration);
        $("#beforenum").val(model.beforeNum);
        $("#archivenum").val(model.archiveNum);
        $("#remark").val(model.remark);
        $("#filecreatetime").val(model.fileCreatetime);

        $('#fileCodeBtn').attr("disabled", false);
        // <li class="list-group-item">文件提名：宋建强</li>
        //documentMessage

        if(documentModel.fileName!=null){
            var li = document.createElement('li');
            li.innerHTML = '<li class=\"list-group-item\">题名:'+documentModel.fileName+'</li>';
            $("#documentMessage").append(li);
        }
        if(documentModel.themeWord!=null){
            console.log('themeword'+documentModel.themeword);
            var li = document.createElement('li');
            li.innerHTML = '<li class=\"list-group-item\">主题词:'+documentModel.themeWord+'</li>';
            $("#documentMessage").append(li);
        }

        if(documentModel.fondsId!=null){
            var li = document.createElement('li');
            li.innerHTML = '<li class=\"list-group-item\">全宗号:'+documentModel.fondsId+'</li>';
            $("#documentMessage").append(li);
        }


        if(documentModel.comeOffice!=null){
            var li = document.createElement('li');
            li.innerHTML = '<li class=\"list-group-item\">来文机关:'+documentModel.comeOffice+'</li>';
            $("#documentMessage").append(li);
        }


        if(documentModel.appendices!=null){
            var li = document.createElement('li');
            li.innerHTML = '<li class=\"list-group-item\">附件数:'+documentModel.appendices+'</li>';
            $("#documentMessage").append(li);
        }

        if(documentModel.archiveYear!=null){
            var li = document.createElement('li');
            li.innerHTML = '<li class=\"list-group-item\">归档年度:'+documentModel.archiveYear+'</li>';
            $("#documentMessage").append(li);
        }

        if(documentModel.archiveDate!=null){
            var li = document.createElement('li');
            li.innerHTML = '<li class=\"list-group-item\">归档日期:'+documentModel.archiveDate+'</li>';
            $("#documentMessage").append(li);
        }

        if(documentModel.reserveLocation!=null){
            var li = document.createElement('li');
            li.innerHTML = '<li class=\"list-group-item\">存放位置:'+documentModel.reserveLocation+'</li>';
            $("#documentMessage").append(li);
        }

        if(documentModel.fileCode!=null){
            var li = document.createElement('li');
            li.innerHTML = '<li class=\"list-group-item\">件号:'+documentModel.fileCode+'</li>';
            $("#documentMessage").append(li);
        }

        if(documentModel.responsible!=null){
            var li = document.createElement('li');
            li.innerHTML = '<li class=\"list-group-item\">责任人:'+documentModel.responsible+'</li>';
            $("#documentMessage").append(li);
        }

        if(documentModel.importance!=null){
            var li = document.createElement('li');
            li.innerHTML = '<li class=\"list-group-item\">重要程度:'+documentModel.importance+'</li>';
            $("#documentMessage").append(li);
        }

        if(documentModel.archiveType!=null){
            var li = document.createElement('li');
            li.innerHTML = '<li class=\"list-group-item\">档案类型:'+documentModel.archiveType+'</li>';
            $("#documentMessage").append(li);
        }

        if(documentModel.beloadDepartment!=null){
            var li = document.createElement('li');
            li.innerHTML = '<li class=\"list-group-item\">所属部门:'+documentModel.beloadDepartment+'</li>';
            $("#documentMessage").append(li);
        }

        if(documentModel.reserveDuration!=null){
            var li = document.createElement('li');
            li.innerHTML = '<li class=\"list-group-item\">保管期限:'+documentModel.reserveDuration+'</li>';
            $("#documentMessage").append(li);
        }

        if(documentModel.fileCreatetime!=null){
            var li = document.createElement('li');
            li.innerHTML = '<li class=\"list-group-item\">文件形成时间:'+documentModel.fileCreatetime+'</li>';
            $("#documentMessage").append(li);
        }

        if(documentModel.saveNum!=null){
            var li = document.createElement('li');
            li.innerHTML = '<li class=\"list-group-item\">保存份数:'+documentModel.saveNum+'</li>';
            $("#documentMessage").append(li);
        }

        if(documentModel.beforeNum!=null){
            var li = document.createElement('li');
            li.innerHTML = '<li class=\"list-group-item\">原文件编号:'+documentModel.beforeNum+'</li>';
            $("#documentMessage").append(li);
        }

        if(documentModel.archiveNum!=null){
            var li = document.createElement('li');
            li.innerHTML = '<li class=\"list-group-item\">档案编号:'+documentModel.archiveNum+'</li>';
            $("#documentMessage").append(li);
        }

        if(documentModel.remark!=null){
            var li = document.createElement('li');
            li.innerHTML = '<li class=\"list-group-item\">备注:'+documentModel.remark+'</li>';
            $("#documentMessage").append(li);
        }
    }else if(list=='#records'||list=="#archives"||list=="#fond"||list=="#recordsSearch"
        ||list=="#archivesSearch"||list=="#fondsSearch") {
        $("#documentMessage").empty();
        if(model.title!=null){
            var li = document.createElement('li');
            li.innerHTML = '<li class=\"list-group-item\">标题:'+model.title+'</li>';
            $("#documentMessage").append(li);
        }
        if(model.importance!=null){
           // console.log('themeword'+documentModel.importance);
            var li = document.createElement('li');
            li.innerHTML = '<li class=\"list-group-item\">重要程度:'+model.importance+'</li>';
            $("#documentMessage").append(li);
        }

        if(model.reelNum!=null){
            var li = document.createElement('li');
            li.innerHTML = '<li class=\"list-group-item\">卷号:'+model.reelNum+'</li>';
            $("#documentMessage").append(li);
        }


        if(model.recordsLocation!=null){
            var li = document.createElement('li');
            li.innerHTML = '<li class=\"list-group-item\">案卷位置:'+model.recordsLocation+'</li>';
            $("#documentMessage").append(li);
        }


        if(model.fileNum!=null){
            var li = document.createElement('li');
            li.innerHTML = '<li class=\"list-group-item\">件数:'+model.fileNum+'</li>';
            $("#documentMessage").append(li);
        }

        if(model.responsible!=null){
            var li = document.createElement('li');
            li.innerHTML = '<li class=\"list-group-item\">责任人:'+model.responsible+'</li>';
            $("#documentMessage").append(li);
        }

        if(model.remark!=null){
            var li = document.createElement('li');
            li.innerHTML = '<li class=\"list-group-item\">备注:'+model.remark+'</li>';
            $("#documentMessage").append(li);
        }

    }



}

function defaultMessage() {
    $("#title").val(null);
    $("#themeword").val(null);
    $("#fondsid").val(null);
    $("#filecreatetime").val();
    $("#appendices").val(null);
    $("#comeoffice").val(null);
    $("#archiveyear").val(null);
    $("#savenum").val(null);
    $("#reservelocation").val(null);
    $("#archivedate").val(null);
    $("#filecode").val(null);
    $("#responsible").val(null);
    $("#importance").val(null);
    $("#archivetype").val(null);
    $("#beloadepartment").val(null);
    $("#reserveduration").val(null);
    $("#beforenum").val(null);
    $("#archivenum").val(null);
    $("#remark").val(null);
}

//填寫信息
function fillMessage() {
    $("#fileDialog").text("修改信息");
$("#commitMessage").text("确认");
}


$("#file input").focus(function () {
    $("#anjuanSpan1").addClass("hidden");
})
$("#file select").focus(function () {
    $("#anjuanSpan1").addClass("hidden");
})

//获取流水号
function getSwiftNumber() {
    var beloadepartment = $('#beloadepartment').val();
    var archivetype = $('#archivetype').val();
    var archiveyear = $('#archiveyear').val();
    var reserveduration = $('#reserveduration').val();

    isEmpty(beloadepartment, "所属部门");
    isEmpty(archivetype, "档案类别");
    isEmpty(archiveyear, "归档年度");
    isEmpty(reserveduration, "保管期限");

    var arr = new Array();
    arr.push(beloadepartment.trim());
    arr.push(archivetype.trim());
    arr.push(archiveyear.trim());
    arr.push(reserveduration.trim());

    var prefix = arr.join("-");
    console.log("prefix       " + prefix);
    $.axx({
        type: 'get',
        url: '/swifts',
        data: {prefix: prefix},
        dataType: 'json',
        success: function (json) {
            var swiftNum = json.content;
            $('#filecode').val(swiftNum);
            $('#filecode').attr("title", swiftNum);
            $('#fileCodeBtn').attr('disabled', true);

        }
    });
}

function isEmpty(obj, str) {
    if (obj == null || obj.trim() == "") {
        $('#anjuanSpan1').text(str + "不能为空").removeClass("hidden");
        throw "empty";
    }

}

function commitMessage() {

}


//確認信息
function commitMessage() {
    // $("#title").val(null);
    // $("#themeword").val(null);
    // $("#fondsid").val(null);
    // $("#appendices").val(null);
    // $("#comeoffice").val(null);
    // $("#archiveyear").val(null);
    // $("#savenum").val(null);
    // $("#reservelocation").val(null);
    // $("#archivedate").val(null);
    // $("#filecode").val(null);
    // $("#responsible").val(null);
    // $("#importance").val(null);
    // $("#archivetype").val(null);
    // $("#beloadepartment").val(null);
    // $("#reserveduration").val(null);
    // $("#beforenum").val(null);
    // $("#archivenum").val(null);
    // $("#remark").val(null);
   // console.log()
    if ($("#commitMessage").html()=="确认"){
        console.log("songjianqiang");
        var title = $("#title").val();
        var themeword = $("#themeword").val();
        var fondsid = $("#fondsid").val();
        var appendices = $("#appendices").val();
        var comeoffice = $("#comeoffice").val();
        var archiveyear = $("#archiveyear").val();
        var savenum = $("#savenum").val();
        var reservelocation = $("#reservelocation").val();
        var archivedate = $("#archivedate").val();
        var filecode = $("#filecode").val();
        var responsible = $("#responsible").val();
        var importance = $("#importance").val();
        var  archivetype = $("#archivetype").val();
        var beloadepartment = $("#beloadepartment").val();
        var reserveduration = $("#reserveduration").val();
        var beforenum = $("#beforenum").val();
        var archivenum = $("#archivenum").val();
        var  remark = $("#remark").val();
        var  deptid = documentModel.deptId;
        var  documentId = documentModel.id;
        var  fileNum = documentModel.fileNum;
        var  documentLocalId = documentModel.documentLocalId;
        var  documentCreateTime = documentModel.documentCreateTime;
        var  recordFlag = documentModel.recordFlag;
        var  fileCreateTime = $("#filecreatetime").val();

        var oldStatus = documentModel.status;
        var oldFileCode = documentModel.fileCode;

        /*$.each(documentModel,function (key,value) {
            console.log("m  " + key + "  " + value);
        })*/

        if (title.trim() == "") {
            $("#anjuanSpan1").text("请输入题名").removeClass("hidden");
            return;
        }

        if (themeword.trim() == "") {
            $("#anjuanSpan1").text("请输入主题词").removeClass("hidden");
            return;
        }

        if (fondsid.trim() == "") {
            $("#anjuanSpan1").text("请输入全宗号").removeClass("hidden");
            return;
        }

        if (appendices.trim() == "") {
            $("#anjuanSpan1").text("请输入附件数").removeClass("hidden");
            return;
        }

        if (isNaN(appendices)) {
            $("#anjuanSpan1").text("附件数必须是数字").removeClass("hidden");
            return;
        }

        if (comeoffice.trim() == "") {
            $("#anjuanSpan1").text("请输入来文机关").removeClass("hidden");
            return;
        }

        if (archiveyear.trim() == "") {
            $("#anjuanSpan1").text("请输入归档年度").removeClass("hidden");
            return;
        }

        if (savenum.trim() == "") {
            $("#anjuanSpan1").text("请输入保存份数").removeClass("hidden");
            return;
        }

        if (isNaN(savenum)) {
            $("#anjuanSpan1").text("保存份数必须是数字").removeClass("hidden");
            return;
        }

        if (reservelocation.trim() == "") {
            $("#anjuanSpan1").text("请输入存放位置").removeClass("hidden");
            return;
        }

        if (archivedate.trim() == "") {
            $("#anjuanSpan1").text("请输入归档日期").removeClass("hidden");
            return;
        }

        if (filecode.trim() == "") {
            $("#anjuanSpan1").text("请输入件号").removeClass("hidden");
            return;
        }

        if (responsible.trim() == "") {
            $("#anjuanSpan1").text("请输入责任人").removeClass("hidden");
            return;
        }

        if (importance == "" || importance == null) {
            $("#anjuanSpan1").text("请输入重要程度").removeClass("hidden");
            return;
        }

        if (archivetype == "" || archivetype == null) {
            $("#anjuanSpan1").text("请输入归档类型").removeClass("hidden");
            return;
        }

        if (beloadepartment == "" || beloadepartment == null) {
            $("#anjuanSpan1").text("请输入所属部门").removeClass("hidden");
            return;
        }

        if (reserveduration == "" || reserveduration == null) {
            $("#anjuanSpan1").text("请输入保管期限").removeClass("hidden");
            return;
        }

        if (fileCreateTime.trim() == "") {
            $("#anjuanSpan1").text("请输入文件形成时间").removeClass("hidden");
            return;
        }

        if (beforenum.trim() == "") {
            $("#anjuanSpan1").text("请输入原文件编号").removeClass("hidden");
            return;
        }

        if (archivenum.trim() == "") {
            $("#anjuanSpan1").text("请输入档案编号").removeClass("hidden");
            return;
        }

        var obj = {
            title: title,
            themeword: themeword,
            fondsid: fondsid,
            appendices: appendices,
            comeoffice: comeoffice,
            archiveyear: archiveyear,
            savenum: savenum,
            reservelocation: reservelocation,
            archivedate: archivedate,
            filecode: filecode,
            responsible: responsible,
            importance: importance,
            archivetype: archivetype,
            beloadepartment: beloadepartment,
            reserveduration: reserveduration,
            beforenum: beforenum,
            archivenum: archivenum,
            remark: remark,
            deptid: deptid,
            documentId: documentId,
            fileNum: fileNum,
            documentLocalId: documentLocalId,
            documentCreateTime: documentCreateTime,
            recordFlag: recordFlag,
            fileCreateTime: fileCreateTime
        };

        if (oldStatus == "未梳理" || oldFileCode != filecode) {
            $.axx({
                type: "get",
                url: "document/" + filecode + "/valid/document",
                data: {},
                success: function (json) {
                    var isUse = json.content;
                    if (isUse) {
                        $("#anjuanSpan1").text("件号已经存在");
                        $("#anjuanSpan1").removeClass("hidden");
                    } else {
                        modifyDocument(obj)
                    }

                }
            });
        } else {
          modifyDocument(obj);
        }
    } else {

    }
}

function modifyDocument(obj) {
    $.axx({
        type: "PUT",
        url: "documents/save/" + fileId,
        data: {
            "recordFlag": obj.recordFlag,
            "deptid": obj.deptid,
            "status": "已完成",
            "doucumentId": obj.documentId,
            "filenum": obj.fileNum,
            "documentLocalId": obj.documentLocalId,
            "documentCreateTime": obj.documentCreateTime,
            "title": obj.title,
            "themeWord": obj.themeword,
            "fondsId": obj.fondsid,
            "appendices": obj.appendices,
            "comeOffice": obj.comeoffice,
            "archiveYear": obj.archiveyear,
            "saveName": obj.savenum,
            "reserveLocation": obj.reservelocation,
            "archiveDate": obj.archivedate,
            "fileCode": obj.filecode,
            "responsible": obj.responsible,
            "importance": obj.importance,
            "archiveType": obj.archivetype,
            "beloadDepartment": obj.beloadepartment,
            "reserveDuration": obj.reserveduration,
            "beforeNum": obj.beforenum,
            "archiveNum": obj.archivenum,
            "remark": obj.remark,
            "filecreatetime": obj.fileCreateTime
        },
        success: (function () {
            defaultMessage();

            getDocumentList(1, listType);
            //data-dismiss="modal"
            $("#file").modal("hide");
            alert("修改成功");
        }),

        error: (function () {

        })

    });
}

//查看信息
function lookMessage() {
   // message(documentModel,listType);
}
function suitUpload() {


}



$("#anjuan input").focus(function () {
    $("#anjuanSpan").addClass("hidden");
})

function crateRecord() {

    if( $("#anjuanCommit").html()=="确认") {
        console.log("createRecord");
        var title = $("#recordTitle").val();
        var importance = $("#recordImportance").val();
        var reelNum = $("#recordReelNum").val();
        //  var recordsLocation = $("#")
        var responsible = $("#recordResponsible").val();
        var recordsLocation = $("#recordRecordsLocation").val();
        var fileNum = $("#recordFileNum").val();
        var remark = $("#recordRemark").val();
        if(title==""){
            alert("请输入标题");
            return;
        }
        if(importance==""){
            alert("请输入重要程度");
            return;
        }

        if(reelNum==""){
            alert("请输入卷号");
            return;
        }

        if(responsible==""){
            alert("请输入责任人");
            return;
        }
        if(recordsLocation==""){
            alert("请输入案卷位置");
            return;
        }
        if(fileNum==""){
            alert("请输入件数");
            return;
        }
        console.log(title + importance + listType);

        var reelType;
        if (listType == "#records")
            reelType = "record";
        else if (listType == "#archives")
            reelType = "archive";
        else if (listType == "#fond")
            reelType = "box";

        $.axx({
            type: "get",
            url: "/document/" + reelNum + "/valid/" + reelType,
            data: "",
            success: function (json) {
                var isUse = json.content;
                if (isUse) {
                    $("#anjuanSpan").removeClass("hidden").text("档号已经存在");
                    return;
                } else {
                    if (listType == "#records") {

                        $.axx({
                            type: "POST",
                            url: "records/" + deptId,
                            data: {
                                "title": title,
                                "importance": importance,
                                "reelNum": reelNum,
                                "recordsLocation": recordsLocation,
                                "fileNum": fileNum,
                                "responsible": responsible,
                                "remark": remark,
                                "deptId": deptId,
                                "deptName": deptName

                            },

                            dataType: "json",
                            success: function (json) {
                                alert("创建成功");
                                getRecordList(1, "#records");
                                $("#anjuan").modal("hide");

                            },
                            error: function () {

                            }
                        });
                    } else if (listType == "#archives") {
                        $.axx({
                            type: "POST",
                            url: "archives/" + deptId,
                            data: {
                                "title": title,
                                "importance": importance,
                                "reelNum": reelNum,
                                "recordsLocation": recordsLocation,
                                "fileNum": fileNum,
                                "responsible": responsible,
                                "remark": remark,
                                "deptId": deptId,
                                "deptName": deptName

                            },

                            dataType: "json",
                            success: function (json) {
                                alert("创建成功");
                                getRecordList(1, "#archives");
                                $("#anjuan").modal("hide");

                            },
                            error: function () {

                            }
                        });
                    } else if (listType == "#fond") {
                        $.axx({
                            type: "POST",
                            url: "boxs/" + deptId,
                            data: {
                                "title": title,
                                "importance": importance,
                                "reelNum": reelNum,
                                "recordsLocation": recordsLocation,
                                "fileNum": fileNum,
                                "responsible": responsible,
                                "remark": remark,
                                "deptId": deptId,
                                "deptName": deptName

                            },

                            dataType: "json",
                            success: function (json) {
                                alert("创建成功");
                                getRecordList(1, "#fond");
                                $("#anjuan").modal("hide");

                            },
                            error: function () {

                            }
                        });
                    }
                }
            }
        });
    }else {

    }

}
//归档选择文件列表方法




//获取已经梳理的文件
function achive() {
    $('#dupTitle').addClass("hidden");
  // if()
    console.log(listType);
  archiveFileType();

}

function archiveFileType() {
    $('#table_1').bootstrapTable("destroy");
    if(listType=="#records"){

        $('#table_1').bootstrapTable({

            search: true,pagination: true,pageSize:7,editable: true,
            columns: archiveFileCol,

        });
        $.axx({
            type:"GET",
            url:"documents/records/"+deptId,
            data:{"imprortant":recordModel.importance},
            dataType:"json",
            success:function (json) {

                $('#table_1').bootstrapTable('load', json.content);
                $('#table_1').bootstrapTable("hideColumn", "id");


            },
            error:function () {

            }
        });
    }else if(listType=="#archives"){


        $('#table_1').bootstrapTable({

            search: true,pagination: true,pageSize:7,editable: true,

            columns: archiveRecordCol,

        });
        console.log("songjainiqgkdfkjd");
        $.axx({
            type:"GET",
            url:"/records/archiveto/" + deptId,
            data:{
              "imprortant":archiveModel.importance
            },
            dataType:"json",
            success:function (json) {
                console.log("档案的归档");
                $('#table_1').bootstrapTable('load', json.content);
                $('#table_1').bootstrapTable("hideColumn", "id");


            },
            error:function () {
            }
        });
    }else if(listType=="#fond"){
        $('#table_1').bootstrapTable({

            search: true,pagination: true,pageSize:7,editable: true,

            columns: archiveRecordCol,

        });
        $.axx({
            type:"GET",
            url:"/archives/archiveto/" + deptId,
            data:{
                "imprortant":fondModel.importance
            },
            dataType:"json",
            success:function (json) {
                console.log("档案的归档");
                console.log("档案的归档");
                $('#table_1').bootstrapTable('load', json.content);
                $('#table_1').bootstrapTable("hideColumn", "id");


            },
            error:function () {

            }
        });
    }
}

//确认归档
function commitActive(){
    var userTh = $('#table_1').bootstrapTable('getSelections');
    /*var docIds = new Array();
    var titles = new Array();
    for(var i=0;i<userTh.length;i++){
        docIds[i]=userTh[i].id;
        console.log("id             cvcvcv" +userTh[i].id);
        titles[i]=listType=="#records"?userTh[i].fileName:userTh[i].title;
    }*/


    if(listType=="#records"){
        $.axx({
            type:"POST",
            url:"/records/archive/"+recordId,
            data: JSON.stringify(userTh),
            dataType:"json",
            contentType: 'application/json;charset=utf-8',
            success:function (json) {
                var dupTitles = json.content;
                getDocumentFormRecord(recordId, 1);
                if (dupTitles == null || dupTitles.length == 0) {
                    $("#records_detail_1").modal("hide");
                } else {
                    archiveFileType();
                    var warn = dupTitles.join(",") + "   文件已经存在，如需归档，请重命名。"
                    $('#dupTitle').html(warn).removeClass("hidden");
                }

            },
            error:function () {

            }
        });
    }else if(listType=="#archives"){
        $.axx({
            type:"POST",
            url:"/archives/records/"+archiveId,
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(userTh),
            dataType:"json",
            success:function (json) {
                var dupTitles = json.content;
                getRecordFormAchives(archiveId, 1);
                if (dupTitles == null || dupTitles.length == 0) {
                    $("#records_detail_1").modal("hide");
                } else {
                    archiveFileType();
                    var warn = dupTitles.join(",") + "   案卷已经存在，如需归档，请重命名。"
                    $('#dupTitle').html(warn).removeClass("hidden");
                }

            },
            error:function () {

            }
        });
    }else if(listType=="#fond"){
        $.axx({
            type:"POST",
            url:"boxs/archives/"+fondId,
            data: JSON.stringify(userTh),
            contentType: 'application/json;charset=utf-8',
            dataType:"json",
            success:function (json) {
                var dupTitles = json.content;
                getArchiveFormBoxs(fondId, 1);
                if (dupTitles == null || dupTitles.length == 0) {
                    $("#records_detail_1").modal("hide");
                } else {
                    archiveFileType();
                    var warn = dupTitles.join(",") + "   档案已经存在，如需归档，请重命名。";
                    $('#dupTitle').html(warn).removeClass("hidden");
                }
            },
            error:function () {

            }
        });
    }

}


//获取进入案卷的文件
function getDocumentFormRecord(recordId, recipient) {
    ///documents/record/{recordId}
    console.log("recipient doc   " + recipient);
    if (recipient == 1) {
        $('#records_detail button.create').removeClass("hidden");
        // $('#records_detail button.docBack').removeClass("hidden");
    } else {
        $('#records_detail button.create').addClass("hidden");
        // $('#records_detail button.docBack').addClass("hidden");
    }
    // $('#records_detail button').find(".create,.docBack").addClass("hidden");

    $("#reelDetail").empty();
    $.axx({
        type:"GET",
        url:"documents/lookrecords/"+recordId,
        data:{},
        dataType:"json",
        success:function (json) {
            console.log("songjiangiang"+json.content);
            var recordDocumentList = json.content;

            for (var i = 0; i < recordDocumentList.length; i++) {
                var recordDocument = recordDocumentList[i];
                var tr = document.createElement("tr");
                console.log("fileName+fileCode"+recordDocument.fileName+recordDocument.fileCode);
                tr.innerHTML = '<td>' + recordDocument.fileName + '</td>' +
                    '<td>' + recordDocument.fileCode + '</td>';
                tr.style.background = "white";
                $("#reelDetail").append(tr);
            }

            var arr = $("#reelDetail").children();
            console.log(arr.length);
            for (var i = 0; i < arr.length; i++) {
                var a = arr[i];
                $(a).click(function () {
                    var index = $(this).index();
                     var document1 = recordDocumentList[index]
                   // fileId = document1.id;
                    documentModel = document1;
                    message(document1,"#wenJian");
                    //commitMessage(document1);

                    change(index,"#recordDocument");

                    // lookUploadFile(document1.documentLocalId);
                    if (recipient == 1)
                        $("button[data-target='#biaoDan'],button[data-target='#accessLog'],.docBack,.selectFile,.selectFile1").removeClass("hidden");
                    else if (recipient == 2) {
                        $("#records_detail .selectFile").attr("data-whatever", "1");
                        $("button[data-target='#biaoDan'],.selectFile").removeClass("hidden");
                    }
                    else if (recipient == 3)
                        $("button[data-target='#biaoDan'],.selectFile,button[data-target='#accessLog']").removeClass("hidden");
                    else if (recipient == 4)
                        $("button[data-target='#biaoDan']").removeClass("hidden");

                });

                $(a).on('dblclick', function () {
                    var index = $(this).index();
                    var document1 = recordDocumentList[index];
                    if (recipient == 1)
                        lookUploadFile(document1.documentLocalId, 3);
                    else
                        lookUploadFile(document1.documentLocalId, recipient);
                })
            }
        },
        error:function () {

        }
    });
}

//获取已经归档的案卷
function getRecordFormAchives(achivesId, recipient) {
    console.log("recipient rec   " + recipient);

    if (recipient == 1) {
        $('#archive_detail button.create').removeClass("hidden");
    } else {
        $('#archive_detail button.create').addClass("hidden");
    }

    ///documents/record/{recordId}
    console.log("songjiang");
    $("#archiveDetail").empty();
    $.axx({
        type:"GET",
        url:"records/archive/"+achivesId,
        data:{
            "offset": 1,
            "limit": 10
        },
        dataType:"json",
        success:function (json) {
            console.log("songjiangiang"+json.content);
            var recordDocumentList = json.content.list;

            for (var i = 0; i < recordDocumentList.length; i++) {
                var recordDocument = recordDocumentList[i];
                var tr = document.createElement("tr");
                console.log("fileName+fileCode" + recordDocument.title + recordDocument.reelNum);
                tr.innerHTML = '<td>' + recordDocument.title+ '</td>' +
                    '<td>' + recordDocument.reelNum + '</td>';
                tr.style.background = "white";
                $("#archiveDetail").append(tr);
            }

            var arr = $("#archiveDetail").children();
            console.log(arr.length);
            for (var i = 0; i < arr.length; i++) {
                var a = arr[i];
                /* var index = $(a).index();
                 var document1 = recordDocumentList[index];*/
                $(a).click(function () {
                    var index = $(this).index();
                    var document1 = recordDocumentList[index]
                    // fileId = document1.id;
                    recordModel = document1;
                    message(document1,"#records");
                    //commitMessage(document1);

                    change(index,"#archiveDetail");
                    // getDocumentFormRecord(document1.id);
                    //lookUploadFile(document1.documentLocalId);
                    if (recipient == 1)
                        $("button[data-target='#file'],button[data-target='#accessLog'],button[data-target='#biaoDan'],.docBack,.selectFile,.selectFile1").removeClass("hidden");
                    else if (recipient == 2) {
                        $("#archive_detail .selectFile").attr("data-whatever", 1);
                        $("button[data-target='#biaoDan'],.selectFile").removeClass("hidden");
                    }
                    else if (recipient == 3)
                        $("button[data-target='#biaoDan'],.selectFile,button[data-target='#accessLog']").removeClass("hidden");
                    else if (recipient == 4)
                        $("button[data-target='#biaoDan']").removeClass("hidden");
                });
                $(a).on('dblclick',function () {
                    var index = $(this).index();
                    var document1 = recordDocumentList[index];
                    if (recipient == 1)
                        getDocumentFormRecord(document1.id, 3);
                    else
                        getDocumentFormRecord(document1.id, recipient);
                })
            }
        },
        error:function () {

        }
    });
}
//获取全宗下的档案
function getArchiveFormBoxs(boxId, recipient) {
    ///documents/record/{recordId}
    console.log("recipient arc   " + recipient);
    if (recipient == 1)
        $('#fond_detail button[data-target="#records_detail_1"]').removeClass("hidden");
    else
        $('#fond_detail button[data-target="#records_detail_1"]').addClass("hidden");
    console.log("songjiang");
    $("#fondDetail").empty();
    $.axx({
        type:"GET",
        url:"archives/boxs/"+boxId,
        data:{
            "offset": 1,
            "limit": 10
        },
        dataType:"json",
        success:function (json) {
            console.log("songjiangiang"+json.content);
            var recordDocumentList = json.content.list;

            for (var i = 0; i < recordDocumentList.length; i++) {
                var recordDocument = recordDocumentList[i];
                var tr = document.createElement("tr");
                console.log("fileName+fileCode"+recordDocument.title+recordDocument.fileCode);
                tr.innerHTML = '<td>' + recordDocument.title+ '</td>' +
                    '<td>' + recordDocument.reelNum + '</td>';
                tr.style.background = "white";
                $("#fondDetail").append(tr);
            }

            var arr = $("#fondDetail").children();
            console.log(arr.length);
            for (var i = 0; i < arr.length; i++) {
                var a = arr[i];
                $(a).click(function () {
                    var index = $(this).index();
                    var document1 = recordDocumentList[index];
                    // fileId = document1.id;
                    archiveModel = document1;
                    message(document1,"#records");
                    //commitMessage(document1);

                    change(index,"#fondDetail");
                    console.log("archive--record     " + document1.id);
                    //lookUploadFile(document1.documentLocalId);
                    if (recipient == 1)
                        $("button[data-target='#file'],button[data-target='#accessLog'],button[data-target='#biaoDan'],.docBack,.selectFile,.selectFile1").removeClass("hidden");
                    else if (recipient == 2) {
                        $("#fond_detail .selectFile").attr("data-whatever", "1");
                        $("button[data-target='#biaoDan'],.selectFile").removeClass("hidden");
                    } else if (recipient == 4)
                        $("button[data-target='#biaoDan']").removeClass("hidden");
                });

                $(a).on('dblclick', function () {
                    var index = $(this).index();
                    var document1 = recordDocumentList[index];
                    if (recipient == 1)
                        getRecordFormAchives(document1.id, 3);
                    else
                        getRecordFormAchives(document1.id, recipient);
                })
            }
        },
        error:function () {

        }
    });
}

var fileModel;
//双击文档查看上载文件
function lookUploadFile(docId, recipient) {
    // $('.pagination').addClass("hidden");
    $('#wenJian_detail button').addClass("hidden");
    if (recipient == 1)
        $('#wenJian_detail button[data-target="#fileUpload"],button[data-target="#fileUploads"]').removeClass("hidden");

    $.axx({
        type:"GET",
        url:"files/"+docId,
        data:{},
        dataType:"json",
        success:function (json) {
            // console.log("docId"+docId+"content"+json.content);
            var filesList = json.content;

            var mainList = new Array();
            var j = 0;

            var accessoryList = new Array();
            var k = 0;

            $("#accessory").empty();
            $("#main").empty();
            for (var i=0;i<filesList.length;i++){
                var file = filesList[i];
                if(file.fileType=='main'){
                    var li = '<li class="list-group-item" >' + file.fileName + '</li>';
                    mainList[j] = file;
                    j++;
                    $("#main").append(li);
                }else {
                    var li = '<li class="list-group-item">' + file.fileName + '</li>';
                    console.log(file.fileName);
                    accessoryList[k] = file;
                    k++;
                    $("#accessory").append(li);
                }
            }
            var mainArr = $("#main").children();
            for (var i = 0; i < mainArr.length; i++) {
                var a = mainArr[i];
                $(a).click(function () {
                    var index = $(this).index();
                    var file1 = mainList[index]
                    fileModel = file1;
                    console.log("fileModel" + fileModel.fileName + "   " + index);
                    change(index,"#mainfile");
                    if (recipient == 1)
                        $('#wenJian_detail button').removeClass("hidden");
                    if (recipient == 2 || recipient == 3)
                        $("button[data-target='#file'],button[data-target='#biaoDan'],.selectFile").removeClass("hidden");
                    if (recipient == 2)
                        $('#wenJian_detail .selectFile').attr("data-whatever", "1");
                    if (recipient == 4)
                        $("button[data-target='#file'],button[data-target='#biaoDan']").removeClass("hidden");
                });
                $(a).on('dblclick', function () {
                    var index = $(this).index();
                    var file1 = mainList[index];
                    fileModel = file1;
                    window.open("/view?fileId=" + file1.id  + "&fileName=" + encodeURI(file1.fileName));
                })
            }

            var accessaryArr = $("#accessory").children();
            for (var j=0;j<accessaryArr.length;j++){
                var a = accessaryArr[j];
                $(a).click(function () {
                    var index = $(this).index();
                    var file1 = accessoryList[index];
                    fileModel = file1;
                    console.log("fileModel" + fileModel.fileName + "   " + index);
                    change(index,"#accessoryFile");
                    if (recipient == 1)
                        $('#wenJian_detail button').removeClass("hidden");
                    if (recipient == 2 || recipient == 3)
                        $("button[data-target='#file'],button[data-target='#biaoDan'],.selectFile").removeClass("hidden");
                    // $("button[data-target='#file'],button[data-target='#biaoDan'],.selectFile").removeClass("hidden");
                });
                $(a).on('dblclick', function () {
                    var index = $(this).index();
                    var file1 = accessoryList[index];
                    // fileModel = file1;
                    console.log("fileId  " + file1.id);
                    window.open("/view?fileId=" + file1.id + "&fileName=" + encodeURI(file1.fileName));
                })
            }
        },
        error:function () {

        }
    });
}


//


//异步上传错误结果处理
$('#input-1b').on('fileerror', function(event, data, msg) {
    console.log("async error");
    // alert("async error")
});

//异步上传成功结果处理
$("#input-1b").on('fileuploaded', function (event, data, previewId, index) {
    console.log("async success ");
    // lookUploadFile(documentFile.documentLocalId);

});

$('#input-1b').on('filebatchuploadsuccess', function(event, data, reviewId, index) {

    console.log("async1 success");
    console.log("宋建强4"+"1"+event+"2"+data+"4"+index);

    // $("#fileUpload").modal("hide");
    // lookUploadFile(documentFile.documentLocalId);

});

//同步上传错误结果处理
$('#input-1a').on('filebatchuploaderror', function(event, data, msg) {

    console.log("sync error")
    $("#fileUpload").modal("hide");
    //records_detail_1
    // lookUploadFile(documentFile.documentLocalId);
    console.log("宋建强3"+"1"+event+"2"+data+"3"+msg);
});



//同步上传成功结果处理
$('#input-1a').on('filebatchuploadsuccess', function(event, data, reviewId, index) {

    console.log("sync success");
    console.log("宋建强4"+"1"+event+"2"+data+"4"+index);
});



//选择文件后处理事件
$("#fileinput").on("filebatchselected", function(event, files) {

});

//上传正文
function commitMainFile() {
    // $('#fileUpload').find('.fileinput-upload-button').trigger('click');
    $('#fileUpload').find('.fileinput-remove-button').trigger('click');
  $('#fileUpload').modal('hide');

  console.log("documentlocalid  " + documentFile.documentLocalId);
  lookUploadFile(documentFile.documentLocalId, 1);
}

//上传附件fileUploads
function commitFile() {
  $('#fileUploads').find('.fileinput-remove-button').trigger('click');
  $('#fileUploads').modal('hide');

  console.log("documentlocalid  " + documentFile.documentLocalId);
  lookUploadFile(documentFile.documentLocalId, 1);
}

//下载文件
function downLoadFile(type, isFlow) {
    // $.axx({
    //     type:"GET",
    //     url:"/fileDownload",
    //     data:{},
    //     dataType:"json",
    //     success:function (json) {
    //
    //     },
    //     error:function () {
    //
    //     }
    // });

    console.log("songjaingnkajfkg");
    if (fileModel == null && documentModel == null && recordModel == null && archiveModel == null && fondId == null) {
        alert("请选择文件");
        return;
    }
    if(type=='wenJianDetail'){
        console.log('wenJianDetail');
        if (isFlow != 1)
            importantValid(documentModel);
        window.location = "/fileDownload?localtion=" + fileModel.location + "&fileName=" + encodeURI(encodeURI(fileModel.fileName)) + "&fileId=" +
            fileModel.id;
    } else if(type=='wenJian'){
        if (isFlow != 1)
            importantValid(documentModel);
       //
        console.log('wenjian');
        /*window.location="/fileZipDownload?localtion=wenJian"+"&fileName="+documentModel.fileName+
            "&fileId="+documentModel.documentLocalId;*/
        window.location = "/fileZipDownload1?reelType=document" + "&fileName=" + encodeURI(encodeURI(documentModel.fileName)) +
            "&fileId="+documentModel.documentLocalId;
    }else if(type=='record'){
        if (isFlow != 1)
            importantValid(recordModel);
        /*console.log('record');
        window.location="/fileZipDownload?localtion=record"+"&fileName="+recordModel.fileName+
            "&fileId="+recordModel.record;*/
        window.location = "/fileZipDownload1?reelType=record" + "&fileName=" + encodeURI(encodeURI(recordModel.title)) +
            "&fileId=" + recordModel.id;
    }else if(type=='archives'){
        if (isFlow != 1)
            importantValid(archiveModel);
        /*window.location="/fileZipDownload?localtion=record"+"&fileName="+recordModel.fileName+
            "&fileId="+recordModel.record;*/
        window.location = "/fileZipDownload1?reelType=archive" + "&fileName=" + encodeURI(encodeURI(archiveModel.title)) +
            "&fileId=" + archiveModel.id;
    }else  if(type=='fond'){
        if (isFlow != 1)
            importantValid(fondModel);
        /*window.location="/fileZipDownload?localtion=record"+"&fileName="+fondModel.fileName+
            "&fileId="+fondModel.record;*/
        window.location = "/fileZipDownload1?reelType=box" + "&fileName=" + encodeURI(encodeURI(fondModel.title)) +
            "&fileId=" + fondModel.id;
    }

   // window.open("/fileDownload");
}

//面包屑
$('#backup').click(function(){
    $(".breadcrumb>li:nth-last-child(2)").trigger();
});
$('.breadcrumb').on('click','li',function(){
    if($(this).index()==$(".breadcrumb").children().length-1){
        return;
    }else{
        // $('.pagination').removeClass('hidden');
        var href=$(this).attr("href");
        $(href).removeClass("hidden").siblings().not(".breadcrumb").addClass("hidden");
        $(this).nextAll().remove();
    }
});

//创建案卷
function createAnjuan() {
    $("#anjuanTitle").text("填写案卷信息");
    $("#anjuanCommit").text("确认");
}
//搜索
var searchArea;
$('#dropdownMenuOne,#dropdownMenuTwo,#dropdownMenuThree,#dropdownMenuFoue,#dropdownMenuFive,#dropdownMenuSix,#dropdownmenuseven').on('click','li',function(){
    console.log("添加额甲方领导甲方");

    defaultMessage();
    searchArea=$(this).index();
});
$('#searchResult').click(function(){
    var title = $("#searchFileTitle").val();
    var filecode = $("#searchFileCode").val();
    if(title==""&&filecode==""){
        alert("请输入至少一个搜索条件");
        return;
    }
    search("#wenJianSerach")

});
$('#searchRecordResult').click(function () {
    var title = $("#searchRecordTitle").val();
    var reelNum = $("#searchRecordReelNum").val();
    if(title==""&&reelNum==""){
        alert("请输入至少一个搜索条件");
        return;
    }
    switch(listType){
        case "#records":search("#recordsSearch");break;
        case "#archives":search("#archivesSearch");break;
        default :search("#fondsSearch");
    }
});
function search(type) {

    console.log("searchArea"+searchArea+type);
    var url;

        if(type=="#wenJianSerach"){

            getDocumentList(1,type);
        }else {
           getRecordList(1,type);
        }
}


function clearModal($modal){
    $modal.on("hidden.bs.modal",function () {
        $(this).removeData("bs.modal");
    })
}

$('#setpassword input').focus(function () {
    $('#setpassword span').addClass("hidden");
})

$('#setpassword').on('hide.bs.modal', function (event) {
    $('#setpassword input').val("");
})


//修改案卷，档案，全宗
$('#modify').on('show.bs.modal', function (event) {
    console.log("modifyy      1111")
    var button = $(event.relatedTarget) // 触发事件的按钮
    var recipient = button.data('whatever');
    var url;

    var modifyModel;
    var i = 0;
    if (recipient == 1) {
        $('#modifyInfo').html("修改案卷信息");
        i = getSelectCount($('#records_1'));
        modifyModel = recordModel;
    }
    else if (recipient == 2) {
        $('#modifyInfo').html("修改档案信息");
        i = getSelectCount($('#archives_1'));
        modifyModel = archiveModel;
    }
    else if (recipient == 3) {
        $('#modifyInfo').html("修改全宗信息");
        i = getSelectCount($('#fonds_1'));
        modifyModel = fondModel;
    }
    console.log("iiiiiiiiiiiiiii        " + i)
    if (i != 1) {

        alert("请选择一条记录");
        throw "select no one";
    }

    if (recipient == 1)
        url = "/records/" + recordModel.id;
    else if (recipient == 2)
        url = "/archives/" + archiveModel.id;
    else if (recipient == 3)
        url = "/box/" + fondModel.id;

    $('#modifyTitle').val(modifyModel.title);
    $('#modifyReelNum').val(modifyModel.reelNum);
    $('#modifyFileNum').val(modifyModel.fileNum);
    $('#modifyImportance').val(modifyModel.importance);
    $('#modifyLocation').val(modifyModel.recordsLocation);
    $('#modifyResponsible').val(modifyModel.responsible);
    $('#modifyRemark').val(modifyModel.remark);

    $('#modifyCommit').unbind('click');
    $('#modifyCommit').on('click', function () {
        modifyAjax(url);
    });
})

function modifyAjax(url) {
    var obj = {
        title: $('#modifyTitle').val(),
        reelNum: $('#modifyReelNum').val(), fileNum: $('#modifyFileNum').val(),
        importance: $('#modifyImportance').val(), recodsLocation: $('#modifyLocation').val(),
        responsible: $('#modifyResponsible').val(), remark: $('#modifyRemark').val()
    };

    $.axx({
        type: 'put',
        url: url,
        data: JSON.stringify(obj),
        contentType: 'application/json;charset=utf-8',
        success: function (json) {
            alert("修改成功");
            var content = json.content;
            getRecordList(1, content);
            $('#modify').modal('hide');
        }

    })
}

$('#modify').on('hide.bs.modal', function (event) {
    $('#modify input').val("");
    $('#modify textarea').val("");
    $('#modifyCommit').unbind('click');

})

//生成件号
function createFileNumForDoc() {
    createFileNum($('#archiving'), 'jianji');
}

function createFileNumForRec() {
    createFileNum($('#createRecordForm'), 'juanji');
}

function createFileNum($form, type) {
    var data = $form.serializeJson();
    var archiveType = data.archiveType;
    var archiveYear = data.archiveYear;
    var reserveDuration = data.reserveDuration;
    var fondsId = data.fondsId;

    if (archiveType == null || archiveType.trim() == "") {
        alert("档案种类不能为空");
        return;
    }
    if (archiveYear == null || archiveYear.trim() == "") {
        alert("归档年度不能为空");
        return;
    }
    if (reserveDuration == null || reserveDuration.trim() == "") {
        alert("保管期限不能为空");
        return;
    }

    var prefix = fondsId + "-" + archiveType + "-" + archiveYear + "-" + reserveDuration;
    getFileNumFromDB($form, prefix, type)
}

function createConstruction() {
    var data = $('#constructionForm').serializeJson();
    var fondsId = data.fondsId;
    var archiveType = data.archiveType;
    var itemNum = data.itemNum;

    if (archiveType == null || archiveType.trim() == "") {
        alert("档案种类不能为空");
        return;
    }
    ;
    if (itemNum == null || itemNum.trim() == "") {
        alert("项目号不能为空");
        return;
    }
    var prefix = fondsId + "-" + archiveType + "-" + itemNum;
    getFileNumFromDB($('#constructionForm'), prefix, 'juanji');
}

//发起生成件号后台请求
function getFileNumFromDB($form, prefix, type) {
    $.axx({
        type: 'GET',
        url: '/fileNumber',
        data: {"prefix": prefix, "fileNumberType": type},
        dataType: "json",
        success: function (json) {
            var fileNumber = json.content;
            $form.find("input[name=fileNum]").val(fileNumber);
            // $("#fileNumber").val(fileNumber);
            $form.data("bootstrapValidator").updateStatus("fileNum","NOT_VALIDATED").validateField("fileNum");


        }
    })
}

//生成件级档号 type等于1 时 生成件号  type等于2时生成卷号
function createFileNumber(type) {
  var qzh;
  var fileType;
  var fileYear;
  var retentionPeriod;
  var prefix;
  var fileNumberType = "jianji";
  var fileNumber;
  qzh = $("#QZH").val();
  fileType = $("#fileType").val();
  fileYear = $("#fileYear").val();
  retentionPeriod = $("#retentionPeriod").val();
    if (fileType == null || fileType.trim() == "") {
        alert("档案种类不能为空");
        return;
    }
    if (fileYear == null || fileYear.trim() == "") {
        alert("归档年度不能为空");
        return;
    }
    if (retentionPeriod == null || retentionPeriod.trim() == "") {
        alert("保管期限不能为空");
        return;
    }
  prefix = qzh + "-" + fileType + "-" + fileYear + "-" + retentionPeriod;
  $.axx({
    type: 'GET',
    url: '/fileNumber',
    data: {
      "prefix": prefix,
      "fileNumberType": fileNumberType

    },
    dataType: "json",
    success: function (json) {
      var fileNumber = json.content;
      // alert("hahah"+fileNumber);
      $("#fileNumber").val(fileNumber);

    }
  })
}

/*
//提交档号
function saveFileNumber() {
  var qzh;
  var fileType;
  var fileYear;
  var retentionPeriod;
  var prefix;

  var fileNumber;
  qzh = $("#QZH").val();
  fileType = $("#fileType").val();
  fileYear = $("#fileYear").val();
  retentionPeriod = $("#retentionPeriod").val();
  prefix = qzh + "-" + fileType + "-" + fileYear + "-" + retentionPeriod;
  $.axx({
    type: 'GET',
    url: '/saveFileNumber',
    data: {
      "prefix": prefix
    },
    dataType: "json",
    success: function (json) {

    }
  })
}*/
