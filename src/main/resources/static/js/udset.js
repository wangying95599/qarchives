//set界面的四个table set_table_cor,set_table_person,set_table_major_from,set_table_major_to
var set_data_cor={
    "id": 1,
    "name": "张三",
    "price" : "100"
};
var set_remove_cor = {
    'click .remove': function (e, value, row, index) {
    	console.log(row);
    	console.log(currentFlowId);
    	console.log(row.type);
    	var id="";
    	var type=row.type;
	
        $('#set_table_cor').bootstrapTable('remove', {
            field: 'id',
            values: [row.id]
        });
    }
};

var set_col_cor=[  
    {   field: 'state',  
        checkbox: true  
    }, {  
        title: '回避单位名称',  
        field: 'name',  
        align: 'center',  
        sortable: true,  
        editable: false ,
        events: set_remove_cor
    } 
 ]  ;

function init(){
	
	
	//
    $('#set_table_cor').bootstrapTable({  
        pagination:true,  
        url: "user/queryAll",  
        columns:set_col_cor
    }); 
    $('#set_table_person').bootstrapTable({  
        pagination:true,  
        url: "user/queryAll",  
        columns:col_1
    }); 
    $('#set_table_major_from').bootstrapTable({  
        pagination:true,  
        url: "user/queryAll",  
        columns:col_1
    }); 
    $('#set_table_major_to').bootstrapTable({  
        pagination:true,  
        url: "user/queryAll",  
        columns:col_1
    }); 
}

function val_price(value) {  
    value = $.trim(value);  
    if (!value) {  
        return 'This field is required';  
    }  
    if (!/^\$/.test(value)) {  
        return 'This field needs to start width $.'  
    }  
    var data = $table.bootstrapTable('getData'),  
        index = $(this).parents('tr').data('index');  
    console.log(data[index]);  
    return '';  
}  
var col_1=[  
    {   field: 'state',  
        checkbox: true  
    }, {  
        title: '用户名',  
        field: 'username',  
        align: 'left',  
        sortable: true,  
        editable: true  
    }, {  
        title: '真名',  
        field: 'longname',  
        align: 'left',  
        sortable: true,  
        formatter:nullFormatter,  
        editable: {  
            type: 'text',  
            title: 'Item Price',  
            validate: val_price
        }  
    } 
 ]  ;

$(document).ready(function(){  
  
    $('#userTable').bootstrapTable({  
        pagination:true,  
        url: "user/queryAll",  
        columns:col_1
    });  
    setTimeout(function () {  
    }, 2000);  
    init();
});  
  
function nullFormatter(data) {  
  
    if(data==""||data==null||data==" ") {  
        return '未填';  
    }  
        return data;  
}  
function timeFormatter(data) {  
    if(data !=null){  
        data = transfromTime(data,true);  
    }  
    return data;  
}  
 