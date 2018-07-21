var assistCol1=[
	{field: "Number", title: "序号", halign: 'center', align: 'center',
    formatter: function (value, row, index) {
        var options = $('#get_room_position_table').bootstrapTable("getOptions");
        return (options.pageNumber - 1) * (options.pageSize) + index + 1;
    }
},
     {"field":"fileNum","title":"档号"},
    {"field": "title", "title": "题名"},
    {"field":"reserveLocation","title":"存放位置"}
];

function room_normal(){
	
  $.axx({
    url: "/homePage/getRoom",
    type: "GET",
    dataType: "json",
    success: function (json) {
    	console.log('getRoom');
    	console.log(json.content.list);
        var models = json.content.list;
        $('#room_info_ul').empty();
        $.each(models,function (index,obj) {
            var id = obj.fileId;
            var url="/view?fileId=" + id  + "&fileName=";
          
            var str ="<a href='"+url+"' target='_blank'>"+obj.content+"</a>";
            var li = document.createElement("li");
            li.setAttribute("id", id);
            li.innerHTML = str;
            $('#room_info_ul').append(li);
            
        });
    }
  });
  room_position();	
}
function room_position(){
	 $.axx({
		    url: "/homePage/getFire",
		    type: "GET",
		    dataType: "json",
		    success: function (json) {
		    	console.log('getFire');
		    	console.log(json.content.list);
		        var models = json.content.list;
		        $('#fire_info_ul').empty();
		        $.each(models,function (index,obj) {
		            var id = obj.fileId;
		            var url="/view?fileId=" + id  + "&fileName=";
		          
		            var str ="<a href='"+url+"' target='_blank'><em>"+obj.content+"</em></a>";
		            var li = document.createElement("li");
		            li.setAttribute("id", id);
		            li.innerHTML = str;
		            $('#fire_info_ul').append(li);
		            
		        });

		    }
		  });
}

function getRoomPosition() {
    console.log("get_room_position");
    var title = $("#room_title").val();
    var filenum = $("#room_filenum").val();

    var _url = "/records/selectrooms";
    var _deptId = deptId;
    if (deptId == KMHK_DEPT_ID) {
        _deptId = 0;
    }
    var queryParams = function (params) {
        console.log("pageNumber", params.pageNumber);
        return {
            "limit": params.pageSize,
            "offset": params.pageNumber,
            "title": title,
            "fileNum": filenum,
            "deptId": _deptId
        }
    }

    $("#get_room_position_table").bootstrapTable('destroy');
    $("#get_room_position_table").bootstrapTable({
        method: "GET",
        url: _url,
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
        pageNumber: 1,
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
        queryParamsType: '',
        totalField: 'total',
        dataField: 'list',
        singleSelect: true,
        editable: true,
        showRefresh: true,
        showExport: true,
        exportDataType: 'all',
        exportTypes:[ 'excel'],  //导出文件类型
        exportOptions:{
            fileName:'库房档案位置',
            tableName:'库房档案位置',
            // ignoreColumn:[0,2],
            worksheetName: 'sheet1',
            excelstyles: ['background-color', 'color', 'font-size', 'font-weight','border-top']
        },
        clickToSelect: true,
        columns: assistCol1,
        queryParams: queryParams,
        responseHandler: function (result) {
            console.log("responseHandler");
            console.log(result.content);
            return result.content;
        }
    });


}