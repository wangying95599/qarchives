$.ajaxSetup({cache: false});
var usrId =1;
var contextUser ;
var globalDeptId ;
var roleId;
//部门列表下user  table使用的列名
var deptUserCol=[{"field":"status","checkbox":true},{"field":"loginName","title":"登录名"},
    {"field":"name","title":"姓名"},{"field":"id","title":"id"},{"field":"roleName","title":"角色"},{"field":"state","title":"状态"}];
//角色列表下user  table 使用的列名
var roleUserCol=[{"field":"status","checkbox":true},{"field":"loginName","title":"登录名"},{"field":"name","title":"姓名"}];
//角色选择下拉框


$(document).ready(function(){
    $.ajaxSetup({ cache: false });
    getConfig();
    getSessionUser();
    $('#deptTable').bootstrapTable({
        toolbar: ".toolBar_1",
        search: true,
        clickToSelect: true,
        singleSelect: true,
        pagination: true,
        pageSize: 10,
        editable: true,
        uniqueId: "id",
        columns: deptUserCol,
    });
    $('#roleTable').bootstrapTable({toolbar:"#toolBar3",search: true,clickToSelect:true,pagination: true,pageSize:10,columns:roleUserCol});
});

/*$(document).ready(function() {
    $('#summernote').summernote({
        lang: 'zh-CN' // default: 'en-US'
    });
});*/

function getConfig(){
  $.axx({
    url:"/config/checkoaconfigenable",
    type:'get',
    data:{},
    dataType:'json',
    success:function (json) {
        // if(json.content == 'OA')
        displayNone();
  }
  })
}

function loadingUser(userId) {
    $.axx({
        type: 'get',
        url: "/users/loading/" + userId,
        data: {},
        success: function (json) {
            var roleId = json.content.role_id;
            if(roleId==1){
                $('#home_page_configure').removeClass("hidden");
                $('#storehouse_configure').removeClass("hidden");
            }else {
                $('#home_page_configure').addClass("hidden");
                $('#storehouse_configure').addClass("hidden");
            }
        }
    })
}

function displayNone(){
  $('#creatDepart1').addClass("hidden");
  $('#writeDepart1').addClass("hidden");
  //$('#creatUser1').addClass("hidden");
  //$('#writeUser1').addClass("hidden");
  // $('#closeUser').addClass("hidden");
  // $('#activateUser').addClass("hidden");
  $('#updateDept').addClass("hidden")
}
function getSessionUser() {
   $.axx({
     url: ARCHIVE_API.user_detail,
      type: 'get',
      data: {},
      dataType: 'json',
      success: function (json) {
        contextUser = json.content;
          //显示用户名
          $('#loginName').html(contextUser.name);

        usrId = contextUser.id;
          loadingUser(usrId);
          getDepartTree();
        getRoleTree();
        getRoleList();
      }
    })
}

function getDepartTree() {
  $.axx({
    url: ARCHIVE_API.dept_byuser.replace("{0}", usrId),
        type: 'get',
        data: {},
        dataType: 'json',
        success: function (json) {
          var models = json.content;
          //fillUpdateDept(models);
            getTree1(models);
        }
      }
  );
}

//部门树
function getTree1(data) {
    var tree = [];
    for (var i = 0; i < data.length; i++) {
        tree.push(new dept(data[i].name, data[i].id, usrId, data[i].hasChilds));
    }
    departTree(tree);
}

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

/*function dept(text,deptId,usrId,hasChilds){
  this.text=text;
  this.deptId=deptId;
  this.usrId = usrId;
  this.nodes = [];
  this.hasChilds =hasChilds;
  this.state.expanded = true;
  console.log("123"+this.state.expanded);
}*/
function rootdept(text,deptId,usrId){
  this.text=text;
  this.deptId=deptId;
  this.usrId = usrId;
}


function role(text,deptId,roleId){
    this.text=text;
    this.deptId=deptId;
    this.roleId = roleId;
}

function departTree(data) {
    var orgNa = {
        bootstrap2: false,
        expandIcon: 'glyphicon glyphicon-plus',
        showTags: true,
        levels: 0,
        enableLinks: true,
        selectedIcon: "glyphicon glyphicon-map-marker",
        showBorder: false,
        data: data,

    }
    $('#leftTree').treeview(orgNa);

    //获取左侧树节点信息
    $('#leftTree').on('nodeSelected', function (event, data) {
        $('#depart').fadeIn(500);
        $('#pole').fadeOut(0);

        var select_node1 = $('#leftTree').treeview('getSelected');
        var parent_node1 = $('#leftTree').treeview('getParent', select_node1);
        if (parent_node1.nodeId) {
            console.log("parent model           " + parent_node1.nodeId);
            $('#assignRoles').addClass("hidden");
        } else {
            $('#assignRoles').removeClass("hidden");
        }
        var deptId = data.deptId;
        getUsersByDept(deptId);

        $('#toolBar1').fadeIn(500);
        $('#toolBar2').fadeOut(0);
    });
    $('#leftTree').on("nodeExpanded ", function (event, node) {
        if (node.nodes.length == 0) {
            if(node.hasChilds)
                getChildDepts($('#leftTree'), node);
        } else {
            var arr = node.nodes;
            for (var i = 0; i < arr.length; i++) {
                var nodeId = arr[i].nodeId;
                var hasChilds = arr[i].hasChilds;
                if (!hasChilds) {
                    $('#leftTree').treeview('expandNode', [nodeId]);
                }
            }
        }
    });

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

//获得部门的用户
function getUsersByDept(deptId) {
    $.axx({
        url: ARCHIVE_API.dept_list_users.replace("{0}",deptId),
        type: 'get',
        data: {},
        // dataType:'json',
        success: function (json) {
            $('#deptTable').bootstrapTable('load', json.content);
            $('#deptTable').bootstrapTable("hideColumn", "id");
        }
    });
}

//首次刷新页面是  即加载准备角色树数据
function getRoleTree (){
    $.axx({
        type:'GET',
        url:"/roles/users/"+usrId,
        data:{},
        dataType:'json',
        success:function(json){
            var data = json.content;
            var roleTree =[];
            for(var i=0;i<data.length-1;i++){
                roleTree.push(new role(data[i].name,-1,data[i].id));
            }
            var roleSize = data.length;
            getDepartForRoleList(roleTree,roleSize);
        }
    });
}


function getDepartForRoleList(roleTree,roleSize){
  console.log("getDepartForRoleList      1");
    var orgNa = {
        bootstrap2: false,
        showTags: true,
        levels:0,
        enableLinks:true,
        selectedIcon:"glyphicon glyphicon-map-marker",
        showBorder:false,
        data: roleTree
    };
    $('#poleTree').treeview(orgNa);
    //获取左侧树节点信息

    $.axx({
      url:"/depts/"+usrId+"/byuser",
      type:'get',
      data:{},
      dataType:'json',
      success:function (json) {
        var models = json.content;

        for(var i = 2;i<6;i++){
          for(var j=0;j<models.length;j++){
            if(roleSize<5){
                $('#poleTree').treeview('addNode',[i-2,{node:new rootdept(models[j].name,models[j].id,usrId)}]);
            }else{
                $('#poleTree').treeview('addNode',[i,{node:new rootdept(models[j].name,models[j].id,usrId)}]);
            }
          }
        }

      }
    });

    //根据选中节点的不同  获取部门下或总公司 下 特定的角色的 用户
    $('#poleTree').on('nodeSelected',function(event, data) {
        $('#depart').fadeOut(0);
        $('#pole').fadeIn(500);
      var select_node = $('#poleTree').treeview('getSelected');
      var parent_node =$('#poleTree').treeview('getParent',select_node);

      var prtRoleId = parent_node.roleId;
      if(prtRoleId!=null){
          getUsersByRoleAndDept(prtRoleId,select_node[0].deptId)
      }else{
          var roleName = select_node[0].text;
          if(roleName=="档案管理员"||roleName=="档案整理员"||roleName=="公司级领导"||
          roleName=="鉴定小组"||roleName=="部门级领导审批"){
              getUsersByRoleAndDept(select_node[0].roleId,-1);
          }else{
              //如果是部门档案管理员和部门档案整理员及上传人员，清空表中数据
              $('#roleTable').bootstrapTable('load',[]);
          }
      }
    });

}


function getUsersByRoleAndDept(RoleId,deptId) {
    $.axx({
        type:'GET',
        url:"/roles/"+RoleId+"/depts/"+deptId,
        data:{},
        dataType:'json',
        success:function (json) {
                    //动态加载表中数据
            $('#roleTable').bootstrapTable('load', json.content);

        }
    });
}
//角色以及部门列表的切换
$('.depart').click(function(){
    $('#depart').fadeIn(500);
    $('#pole').fadeOut(0);
});
$('.pole').click(function(){
    $('#depart').fadeOut(0);
    $('#pole').fadeIn(500);
});
//获得背景颜色
$('#deptTable').on('click','tr',function(){
    $(this).addClass("trHover").siblings("tr").removeClass("trHover");
    $('#toolBar1').fadeOut(0);
    $('#toolBar2').fadeIn(500);
    var a=$(this).children("td.hidden").text();
    console.log("USERID    "+a);
});

$('#roleTable').on('click','tr',function(){
    $(this).addClass("trHover").siblings("tr").removeClass("trHover");
});

$('#createDeptButton').click(function () {
  console.log('createDepart    -');
  var dept = {name: $('#createDept').val()};
  $.axx({
    type: 'POST',
    url: "/depts",
    data: dept,
    dataType: 'json',
    success: function (json) {
      console.log('创建成功'+json);
      var newDept = json.content;
      console.log("json    id     "+newDept.id )
      if(newDept.id ==null){
          alert("部门已经存在");
      }else {
          var new_node = {
              text:newDept.name,
              deptId:newDept.id,
              usrId:usrId
          }

          $('#createDept').val("");

          $('#leftTree').treeview('addNode', [-1, {node: new_node}]);
          $('#creatDepart').modal("hide");
      }
    },
    error:function (json) {
        alert("部门创建失败");
    }
  });
})

function updateDepart(){
  var select_node = $('#leftTree').treeview('getSelected');
  var nodeDeptId = select_node[0].deptId;
  var nodeId = select_node[0].nodeId;
  // if(select_node.length == 1){
  //     for(var i=0;i<select_node.length;i++){
  //         console.log(i+"   sel dept  "+select_node[i].deptId +"    nodeId  "+ select_node[i].nodeId);
  //     }
    if(nodeDeptId!=null) {
      var dept = {
        id : nodeDeptId,
        name:$('#updateDeptButton').val()
      }
      var new_node = {
        text:$('#updateDeptButton').val(),
        deptId:nodeDeptId,
        usrId:usrId
      }

      $.axx({
        type: 'put',
        url:"/depts",
        data:dept,
        dataType:'json',
        success:function(json){
            var model = json.content;
            if(model == null){
                alert("部门名已经存在");
            }else {
                $('#updateDeptButton').val("");
                $('#leftTree').treeview('editNode',[nodeId,new_node]);
                $('#writeDepart').modal("hide");
            }
          // var dept = json.content;
          // console.log("select_node"+select_node[0].text);

        }
      });
    }else {

    }
  // }

}

function createUser() {
  var select_node = $('#leftTree').treeview("getSelected");
  console.log("aaaaa createUser 1")
    console.log("length     "+select_node.length)

  console.log("aaaaa createUser 2")
    var userDeptId = select_node[0].deptId;
    var user = {
      loginName: $('#userLoginName').val(),
      name: $('#userName').val(),
      // password: $('#userPassword').val(),
      deptId: userDeptId
    };
    $.axx({
      type: 'POST',
      url: "/users",
      data: user,
      dataType: 'json',
      success: function (json) {
        console.log('用户创建成功');

        $('#userLoginName').val("");
        $('#userName').val("");
        $('#creatUser').modal('hide');
          getUsersByDept(userDeptId);
      },
      error: function () {
        $('#creatUserError').text("");
      }
    });

}

//激活用户
function activateUser() {
    var userTh = $('#deptTable').bootstrapTable('getSelections');

    if (userTh.length==1) {
        if ("关闭" == userTh[0].state) {
            $.axx({
                type: 'GET',
                url: "/users/activate/" + userTh[0].id,
                data: {},
                success: function (json) {

                    userTh[0].state='激活';
                    userTh[0].status=false;
                    console.log("status        "+userTh[0].status)

                    $('#deptTable').bootstrapTable("removeByUniqueId",userTh[0].id);
                    $('#deptTable').bootstrapTable("insertRow",{index:0,row:userTh[0]});

                }

            });
        } else {
        }
    } else {
        alert("请选择一个用户");
    }
}

//关闭用户
function closeUser() {
    var userTh = $('#deptTable').bootstrapTable('getSelections');

    if (userTh.length==1) {
        if ("激活" == userTh[0].state) {
            $.axx({
                type: 'GET',
                url: "/users/close/" + userTh[0].id,
                data: {},
                success: function (json) {
                    userTh[0].state = "关闭";
                    userTh[0].status=false;
                    $('#deptTable').bootstrapTable("removeByUniqueId",userTh[0].id);
                    $('#deptTable').bootstrapTable("insertRow",{index:0,row:userTh[0]});
                }

            });
        } else {
        }
    } else {
        alert("请选择一个用户");
    }

}


//编辑用户
function updateUser() {
    var userTh = $('#deptTable').bootstrapTable('getSelections');

    if (userTh.length==1) {
        var name = $('#updateUserNameButton').val();
        var pwd = $('#updateUserPswButton').val();
        var pwdAgain = $('#updateUserPswAgain').val();
        if(pwd==pwdAgain){
            var data = {
                id:userTh[0].id,
                name:name,
                password:$('#updateUserPswButton').val()
            };
            $.axx({
                type: 'PUT',
                url: "/users",
                data: data,
                success: function (json) {
                    var model = json.content;
                    if(model.name!=null)
                        userTh[0].name= model.name;
                    userTh[0].status=false;

                    //清空input框数据
                    $('#updateUserNameButton').val("");
                    $('#updateUserPswButton').val("");
                    $('#updateUserPswAgain').val("");

                    $('#writeUser').modal("hide");
                    $('#deptTable').bootstrapTable("removeByUniqueId",userTh[0].id);
                    $('#deptTable').bootstrapTable("insertRow",{index:0,row:userTh[0]});
                }

            });
        }else{
            $('#passwordSure').removeClass("hidden");
        }


    } else {
        alert("请选择一个用户");
    }
}
$('#writeUser input').focus(function(){
    $('#passwordSure').addClass("hidden");
});
//移除用户（特定角色下特定部门的用户）
function removeUser() {
    console.log("removeUser ")
    var select_node = $('#poleTree').treeview("getSelected");
    console.log("select_node   "+select_node[0].roleId +" J    "+select_node[0].deptId);
    var parent_node =$('#poleTree').treeview('getParent',select_node[0].nodeId);
    var deptId;
    var roleId;
    if(parent_node.roleId==null){
        deptId=-1;
        roleId=select_node[0].roleId;
    }else {
        deptId = select_node[0].deptId;
        roleId = parent_node.roleId;
    }
    var users = $('#roleTable').bootstrapTable("getSelections");
    if(users.length<1){
        alert("请您选择一个或多个用户");
    } else {
        console.log("remove deptID     " + deptId);
        console.log("remove roleId     " + roleId);
        console.log("remove prtdeptId     " + select_node[0].deptId);


        var usrIds =new Array();
        for(var i=0;i<users.length;i++){
            usrIds[i]=users[i].id;
            console.log("usersids     " + usrIds[i]);
        }
        var data = {usrIds:usrIds};
        $.axx({
            type:'POST',
            url:"/roles/"+roleId+"/depts/"+deptId+"/users",
            data:data,
            success:function (json) {
                var model = json.content;
                getUsersByRoleAndDept(model.roleId, model.deptId);
            }


        });
    }
}

//角色下拉框对象
/*
function roleSelector(id,name) {
    this.value = id;
    this.text = name
}
*/

//获得角色列表
function getRoleList() {
    $.axx({
       type:'GET',
       url:"/roles/users/"+usrId,
       data:{},

       success:function (json) {
           var models = json.content;
           $('#dropDownMenu').empty();
           for(var i=0;i<models.length;i++){
               console.log("menu     " + models[i].name);
               $('#dropDownMenu').append(
                   '<li>'+
                   '<a id ="'+models[i].id+'">'+models[i].name+'</a>'+
                   '</li>'
               )
           }
       }
    });
}

//填充更改部门下拉菜单
function fillUpdateDept(data){
    $('#updateDeptDropDownMenu').empty();
    for(var i=0;i<data.length;i++){
        console.log("menu     " + data[i].name);
        $('#updateDeptDropDownMenu').append(
            '<li>'+
            '<a id ="'+data[i].id+'">'+data[i].name+'</a>'+
            '</li>'
        )
    }
}

//给选定的用户分配角色（已知部门id）
$('#dropDownMenu').on('click','a',function () {
    var userTh = $('#deptTable').bootstrapTable('getSelections');
    var userTh = $('#deptTable').bootstrapTable('getSelections');
    if(userTh.length==1){
        var newRoleName = $(this).text();
        console.log("oldRoleName    " + newRoleName);
        if(userTh[0].roleName!=newRoleName){
            var roleId = $(this).attr('id');
            console.log("assign roleId   " + roleId);
            var usrRole = userTh[0].roleName;
            var usrId = userTh[0].id;
            var dept_nodes = $('#leftTree').treeview('getSelected');
            var deptId = dept_nodes[0].deptId;
            //给选定的用户设定角色
            if(newRoleName=='可见人员')
                roleId = -1;
            if(newRoleName=='档案管理员'||newRoleName=='档案整理员'||newRoleName=="公司级领导"||
                newRoleName=="鉴定小组"||newRoleName=="部门级领导审批")
                deptId = -1;
            $.axx({
                type:'PUT',
                url:"/assign/"+roleId+"/"+usrId+"/"+deptId,
                success:function (json) {
                    var model = json.content;
                    userTh[0].roleName = model.roleName;
                    userTh[0].status=false;
                    /*$('#deptTable').bootstrapTable("removeByUniqueId",model.usrId);
                    $('#deptTable').bootstrapTable("insertRow",{index:0,row:userTh[0]});*/
                    $('#deptTable').bootstrapTable("updateByUniqueId", {id: model.usrId, row: userTh[0]});
                    alert("角色修改成功");
                },
                error:function () {
                    alert("角色修改失败");
                }
            });
        }else{
            alert("角色没有改变");
        }

    }else{
        alert("请选择一个用户");
    }

});

//用户更改部门
$('#updateDeptDropDownMenu').on('click','a',function () {
    var userTh = $('#deptTable').bootstrapTable('getSelections');
    if(userTh.length==1){
        var newDeptId = $(this).attr("id");
        var usrId = userTh[0].id;
        var dept_nodes = $('#leftTree').treeview('getSelected');
        var oldDeptId = dept_nodes[0].deptId;

        $.axx({
            type:'GET',
            url:"/depts/"+oldDeptId+"/update/"+newDeptId+"/users/"+usrId,
            data:{},
            success:function (json) {
                var model = json.content;
                console.log(model.oldDeptId + "  model   " + model.usrId);
                alert("修改成功");
                // $('#deptTable').treeview('load',)
                getUsersByDept(model.oldDeptId);
            }

        });

    }else {
        alert("请选择一个用户");
    }
});

$('#setpassword input').focus(function () {
    $('#setpassword span').addClass("hidden");
})

$('#setpassword').on('hide.bs.modal', function (event) {
    $('#setpassword input').val("");
})
