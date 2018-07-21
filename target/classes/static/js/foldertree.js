/*档案左侧树状图*/
var parentId=null;
function getFolderTree() {
	console.log("getFolderTree");
	console.log(DMS_API.dept_list_folder.replace("{0}", usrId).replace("{1}", "0"));
  $.axx({
    url: DMS_API.dept_list_folder.replace("{0}", usrId).replace("{1}", "999"),
        type: 'get',
        data: {},
        dataType: 'json',
        success: function (json) {
          var models = json.content;
            initFolderTree(models);
        }
      }
  );
}

//部门树
function initFolderTree(data) {
    var tree = [];
    for (var i = 0; i < data.length; i++) {
        tree.push(new folderTreeNode(data[i].name, data[i].id, usrId, data[i].hasChilds));
    }
    folderTree(tree);
}

function folderTreeNode(text,folderId,usrId,hasChilds){
    var obj = new Object();
    obj.text=text;
    obj.folderId=folderId;
    obj.usrId = usrId;
    obj.nodes = [];
    obj.hasChilds =hasChilds;
    var state = new Object();
    state.expanded = hasChilds?false:true
    obj.state = state;
    return obj;
}


function folderTree(data) {
    var treeview = {
        bootstrap2: false,
        expandIcon: 'glyphicon glyphicon-plus',
        showTags: true,
        levels: 0,
        enableLinks: true,
        selectedIcon: "glyphicon glyphicon-map-marker",
        showBorder: false,
        data: data,

    }
    $('#folderTree').treeview(treeview);

    //获取左侧树节点信息
    $('#folderTree').on('nodeSelected', function (event, data) {
//        $('#depart').fadeIn(500);
//        $('#pole').fadeOut(0);
    	showFolderTable();

        var select_node1 = $('#folderTree').treeview('getSelected');
        var parent_node1 = $('#folderTree').treeview('getParent', select_node1);
        if (parent_node1.nodeId) {
            console.log("parent model           " + parent_node1.nodeId);
            $('#assignRoles').addClass("hidden");
        } else {
            $('#assignRoles').removeClass("hidden");
        }
        var folderId = data.folderId;
        getModelByFolder(folderId);

//        $('#toolBar1').fadeIn(500);
//        $('#toolBar2').fadeOut(0);
    });
    
    //展开
    $('#folderTree').on("nodeExpanded ", function (event, node) {
        if (node.nodes.length == 0) {
            if(node.hasChilds)
                getChildByFolder($('#folderTree'), node);
        } else {
            var arr = node.nodes;
            for (var i = 0; i < arr.length; i++) {
                var nodeId = arr[i].nodeId;
                var hasChilds = arr[i].hasChilds;
                if (!hasChilds) {
                    $('#folderTree').treeview('expandNode', [nodeId]);
                }
            }
        }
    });

}

function showFolderTable(){
	$('#folderTab').removeClass("hidden").hide().fadeIn(500).siblings().addClass("hidden");
}

//左侧树展开查询
function getChildByFolder(tree,node) {
	  $.axx({
	    url: DMS_API.dept_list_folder.replace("{1}", node.folderId).replace("{0}", usrId),
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
	          {node: new folderTreeNode(models[j].name, models[j].id, usrId,models[j].hasChilds)}]);
	      }

	    }
	  });
}

//查詢文件夾的内容列表
function getModelByFolder(folderId) {
	parentId = folderId;
    $.axx({
        url: DMS_API.dept_list_model.replace("{1}",folderId).replace("{0}", usrId),
        type: 'get',
        data: {},
        // dataType:'json',
        success: function (json) {
            $('#folder_list_table').bootstrapTable('load', json.content);
            $('#folder_list_table').bootstrapTable("hideColumn", "id");
        }
    });
}
