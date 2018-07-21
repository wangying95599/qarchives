function initExport(method, $table, url, column, queryparams, singleSelect, contentType, toolbar) {
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
        clickToSelect: true,
        queryParams:queryparams,
        showExport: true,
        exportDataType: 'all',
        paginationShowPageGo:true,
        exportTypes:[ 'excel'],  //导出文件类型
        exportOptions:{
            fileName:'归档文件目录',
            tableName:'归档文件目录',
            ignoreColumn:[0,2],
            worksheetName: 'sheet1',
            excelstyles: ['background-color', 'color', 'font-size', 'font-weight','border-top']
        },
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
