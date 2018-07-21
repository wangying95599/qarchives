//配置首页
function getLink() {
    $.axx({
        url: "/homePage/getLink",
        type: "GET",
        dataType: "json",
        success: function (json) {

            $('#editNew').bootstrapTable('load', json.content);
        }
    });
}

var editNewCol = [{"field": "status", "checkbox": true}, {"field": "content", "title": "标题"}, {
    field: "id",
    title: "id",
    visible: false
}];
// $('#editNew').bootstrapTable({toolbar:".editNewToolbar",search: false,clickToSelect:true,pagination: true,pageSize:10,editable: true,uniqueId: "id",columns:editNewCol});

$('#writeHome_box2').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget);
    var recipient = button.data("whatever");
    var url;
    if (recipient == 1) {
        $('#writeHome_box2 h4').text("编辑使用须知");
        $('#writeHome_box1 h4').text("编辑使用须知");
        url = "/homePage/getInformation";
        $('#btn_add').removeAttr('data-whatever').attr('data-whatever', 1);
        $('#btn_edit').removeAttr('data-whatever').attr('data-whatever', 2);
    } else if (recipient == 2) {
        $('#writeHome_box2 h4').text("编辑最新通知");
        $('#writeHome_box1 h4').text("编辑最新通知");

        url = "/homePage/getLink";
        $('#btn_add').removeAttr('data-whatever').attr('data-whatever', 3);
        $('#btn_edit').removeAttr('data-whatever').attr('data-whatever', 4);
    }
    loadingHomePageTable(url,'editNew');

    $('#btn_delete').unbind('click').on('click',function (e) {
        homePageDel($('#editNew'));
    });

})
$('#writeHome_box4').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget);
    var recipient = button.data("whatever");
    var url;
    if (recipient == 1) {
        $('#writeHome_box4 h4').text("编辑库房管理规范");
        $('#writeHome_box1 h4').text("编辑库房管理规范");
        url = "/homePage/getRoom";
        $('#btn_add').removeAttr('data-whatever').attr('data-whatever', 5);
        $('#btn_edit').removeAttr('data-whatever').attr('data-whatever', 6);
    } else if (recipient == 2) {
        $('#writeHome_box4 h4').text("编辑消防知识");
        $('#writeHome_box1 h4').text("编辑消防知识");

        url = "/homePage/getFire";
        $('#btn_add').removeAttr('data-whatever').attr('data-whatever', 7);
        $('#btn_edit').removeAttr('data-whatever').attr('data-whatever', 8);
    }
    loadingHomePageTable(url,'editNew4');
    $('#btn_delete').unbind('click').on('click',function (e) {
        homePageDel($('#editNew4'));
    });
})

function loadingHomePageTable(url,id) {
	
    $('#'+id).bootstrapTable('destroy');
    $('#'+id).bootstrapTable({
        method: 'get',
        toolbar: '#toolbar',
        contentType: "application/x-www-form-urlencoded",
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
        pageNumber: 1,
        //每页的记录行数（*）
        pageSize: 8,
        //可供选择的每页的行数（*）
        // pageList: [10, 25, 50, 100],
        //分页方式：client客户端分页，server服务端分页（*）
        sidePagination: "server",
        //是否显示搜索
        search: false,
        //Enable the strict search.
        strictSearch: true,
        queryParamsType: '',
        totalField: 'total',
        dataField: 'list',
        url: url,
        // singleSelect: true,
        //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
        //queryParamsType:'',
        //查询参数,每次调用是会带上这个参数，可自定义
        queryParams: function (params) {
            return {
                pageNum: params.pageNumber,
                pageSize: params.pageSize,
            };
        },
        responseHandler: function (result) {
            console.log("rrrrrrrrrrrrrrrrrrrrrrrrrr        ", result.content.total)

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
        // showRefresh: true,
        clickToSelect: true,
        uniqueId: "id",
        columns: editNewCol
    });
}

//information 新增
function create(obj) {
    var url;
    if (obj == 1)
        url = "/homePage/information";
    else if (obj == 3)
        url = "homePage/link";
    else if (obj == 5)
        url = "homePage/room";
    else if (obj == 7)
        url = "homePage/fire";
    console.log("url   " + url + "   " + obj);
    var content = $('#contentId').val();
    if(!docId){
    	console.log('is null');
    	 alert("必须上传文档");
    	return;
    }
    $.axx({
        type: 'post',
        url: url,
        data: {content: content,docId:docId},
        success: function (json) {
            alert("新增成功");
            $('#writeHome_box1').modal('hide');
            if(obj==1||obj==3)
                $('#editNew').bootstrapTable("refresh");
            else if(obj==5||obj==7)
                $('#editNew4').bootstrapTable("refresh");
        }
    })
}

//修改
function update(obj) {
    var selections;
    if(obj==2||obj==4)
        selections = $("#editNew").bootstrapTable("getSelections");
    else if(obj==6||obj==8)
        selections = $('#editNew4').bootstrapTable("getSelections");
    var content = $('#contentId').val();
    var url = "/homePage/update";
    var data = {id: selections[0].id, content: content};
    $.axx({
        type: 'put',
        url: url,
        data: data,
        success: function (json) {
            // alert("修改成功");
            $('#writeHome_box1').modal('hide');
            if(obj==2||obj==4)
                $('#editNew').bootstrapTable("refresh");
            else if(obj==6||obj==8)
                $('#editNew4').bootstrapTable("refresh");
        }
    })
}

//删除
function homePageDel($table) {
    var selections = $table.bootstrapTable("getSelections");
    if (selections.length < 1) {
        alert("请选择一条或多条记录");
        return;
    }
    var ids = new Array();
    for (var i = 0; i < selections.length; i++) {
        ids.push(selections[i].id);
    }

    var confirm = window.confirm("您确定要删除您选定的文件吗？")
    if (!confirm)
        return;

    $.axx({
        type: 'put',
        url: "homePage/delete",
        data: {ids: ids},
        success: function (json) {
            alert('删除成功');
            $table.bootstrapTable("refresh");
        }
    })
}


$('#writeHome_box1').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget);
    var recipient = button.data("whatever");
    $('#submitId').unbind("click")
    var remainder = recipient%2;
    var obj ;
    if(remainder==1){
        obj = $('#btn_add').attr('data-whatever');
    }else if(remainder==0){
        obj = $('#btn_edit').attr('data-whatever');
    }
    docId='';
   
    $('.file-input').hide();
    file_upload.fileinput('clear');
    console.log("recipient:" + recipient+"_obj:"+obj);
    // var obj = $('#btn_add').attr('data-whatever');
    if (obj == 1 || obj == 3 || obj==5 ||obj==7) {
    	 $('.file-input').show();
        $('#writeHome_box1 label').text("请输入新增标题");
        $('#writeHome_box1 textarea').val("");
        $('#submitId').on('click', function () {
            create(obj);
        });
    } else if (obj == 2 || obj == 4) {
        var selection = validSelect('editNew');
        $('#writeHome_box1 label').text("请输入修改标题");
        $('#writeHome_box1 textarea').val(selection.content);
        $('#submitId').on('click', function () {
            update(obj);
            	
        })
    }else if (obj ==6 ||obj == 8){
        var selection = validSelect('editNew4');
        $('#writeHome_box1 label').text("请输入修改标题");
        $('#writeHome_box1 textarea').val(selection.content);
        $('#submitId').on('click', function () {
            update(obj);
        })
    }
})


function validSelect(id) {
    var selections = $("#"+id).bootstrapTable("getSelections");
    console.log("valid:" + id);
    if (selections.length != 1) {
        alert("请选择一条记录");
        throw "select no one";
    }
    return selections[0];
}

// function link() {
//
//   var textarea = $("#link").val();
//   console.log(textarea);
//   $.axx({
//     url: "/homePage/link",
//     type: "POST",
//     data: {
//       "textarea": textarea
//     },
//     dataType: "json",
//     success: function (json) {
//
//       alert("保存成功");
//       location.reload();
//     }
//   });
// }
//
// function information() {
//   var textarea = $("#information").val();
//   console.log(textarea);
//   $.axx({
//     url: "/homePage/information",
//     type: "POST",
//     data: {
//       "textarea": textarea
//     },
//     dataType: "json",
//     success: function (json) {
//       alert("保存成功");
//       location.reload();
//     }
//   });
// }

// function picture() {
//     $.axx({
//         url: " ",
//         type: "GET",
//         data: {},
//         dataType: "json",
//         success: function (json) {
//
//         }
//     });
// }

$('#input-c').fileinput({
    language: 'zh', //设置语言
    uploadUrl: "/homePictures/upload",
    uploadAsync: false,
//                      allowedFileExtensions: ['jpg', 'gif', 'png'],//接收的文件后缀
    showUpload: true, //是否显示上传按钮
    showCaption: true,//是否显示标题
    browseClass: "btn btn-primary", //按钮样式
    maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
    //minFileCount: 0,
//                       maxFileCount: 10, //表示允许同时上传的最大文件个数
//                       msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！"
    uploadExtraData: function (previewId, index) {

        var obj = {
            content: $('#picContent').val()
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

$('#writeHome_box3').on('hidden.bs.modal', function (event) {
    $('#writeHome_box3').find('.fileinput-remove-button').trigger('click');
    $('#picContent').val("");
})

var docId="";
file_upload = $('#homepage_upload').fileinput({
    language: 'zh', //设置语言
    uploadUrl: "/homePage/homepageUpload",
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
            fileType: "homepage"
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
}).on("fileuploaded", function(event, data) {
	if(data.response)
	{
		console.log(data.response.content);
		docId=data.response.content;
	    alert('处理成功');
	}
});
console.log(file_upload);
//双击打开 
$('#editNew4').on('dbl-click-row.bs.table',function (e,row, $element, field) {
	 console.log('editNew4 dbl');
    console.log(row.fileId);
    window.open("/view?fileId=" + row.fileId  + "&fileName=");
});
$('#editNew').on('dbl-click-row.bs.table',function (e,row, $element, field) {
	 console.log('editNew4 dbl');
   console.log(row.fileId);
   window.open("/view?fileId=" + row.fileId  + "&fileName=");
});