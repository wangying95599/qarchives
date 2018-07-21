
//年统计
function report_year() {
	var dept=$("#year_dept").val();
	var start=$("#year_start").val();
	var end=$("#year_end").val();

	var url = "/report/year/juan/"+long_null(dept)+"/"+str_null(start)+"/"+str_null(end);
	$('#reportForYearTable1').bootstrapTable('refreshOptions',{url:url});
	
    /*$.axx({
        type:'GET',
        url:"/report/year/juan/"+long_null(dept)+"/"+str_null(start)+"/"+str_null(end),
        success:function (json) {
            var models = json.content;
            $('#reportForYearTable1').bootstrapTable('load', models);
            //console.log(json);
            // console.log(" dept name" + models[0].name);
            /!*if(models.length>0){
                $('#juan_reportForYearTbody').empty();
                for(var i=0;i<models.length;i++){
                    //console.log(models[i]);
                    
                    var $trTemp = $("<tr></tr>");
                    $trTemp.append("<td>"+ models[i].year +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i].JJ) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i].SX) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i].SJJC) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i].AJ) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i].SG) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i].XX) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i].ZTB) +"</td>");
                    
                    $trTemp.appendTo("#juan_reportForYearTbody");
                }
            }*!/
        }
    });*/
    
    $.axx({
        type:'GET',
        url:"/report/year/jian/"+long_null(dept)+"/"+str_null(start)+"/"+str_null(end),
        success:function (json) {
            var models = json.content;
            console.log(json);
            $('#reportForYearTable2').bootstrapTable('load', models);
            // console.log(" dept name" + models[0].name);
            /*if(models.length>0){
                $('#jian_reportForYearTbody').empty();
                for(var i=0;i<models.length;i++){
                    console.log(models[i]);
                   
                    var $trTemp = $("<tr></tr>");
                    $trTemp.append("<td>"+ models[i].year +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i].WS) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i].KJ) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i].RS) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i].SW) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i].HT) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i].KW) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i].YX) +"</td>");
                    
                    $trTemp.append("<td>"+ int_show(models[i].FC) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i].ZJ) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i].DJ) +"</td>");

                    $trTemp.appendTo("#jian_reportForYearTbody");
                }
            }*/
        }
    });
}
//保存期限统计
function report_duration() {
	var dept=$("#year_dept").val();
	var start=$("#year_start").val();
	var end=$("#year_end").val();
	
    $.axx({
        type:'GET',
        url:"/report/duration/juan/"+long_null(dept)+"/"+str_null(start)+"/"+str_null(end),
        success:function (json) {
            var models = json.content;
            //console.log(json);
            // console.log(" dept name" + models[0].name);
            if(models.length>0){
                $('#juan_reportForDurationTbody').empty();
                for(var i=0;i<models.length;i++){
                    //console.log(models[i]);
                    
                    var $trTemp = $("<tr></tr>");
                    $trTemp.append("<td>"+ models[i].type +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i]['5年']) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i]['10年']) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i]['30年']) +"</td>");
                    /*$trTemp.append("<td>"+ int_show(models[i]['短期']) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i]['长期']) +"</td>");*/
                    $trTemp.append("<td>"+ int_show(models[i]['永久']) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i]['cnt']) +"</td>");
                    
                    $trTemp.appendTo("#juan_reportForDurationTbody");
                }
            }
        }
    });
    
    $.axx({
        type:'GET',
        url:"/report/duration/jian/"+long_null(dept)+"/"+str_null(start)+"/"+str_null(end),
        success:function (json) {
            var models = json.content;
            console.log(json);
            // console.log(" dept name" + models[0].name);
            if(models.length>0){
                $('#jian_reportForDurationTbody').empty();
                for(var i=0;i<models.length;i++){
                    console.log(models[i]);
                   
                    var $trTemp = $("<tr></tr>");
                    $trTemp.append("<td>"+ models[i].type +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i]['5年']) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i]['10年']) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i]['30年']) +"</td>");
                    /*$trTemp.append("<td>"+ int_show(models[i]['短期']) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i]['长期']) +"</td>");*/
                    $trTemp.append("<td>"+ int_show(models[i]['永久']) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i]['cnt']) +"</td>");
                    
                    $trTemp.appendTo("#jian_reportForDurationTbody");
                }
            }
        }
    });
}
//利用统计
function report_use() {
	var dept=$("#use_dept").val();
	var start=$("#use_start").val();
	var end=$("#use_end").val();
	
    $.axx({
        type:'GET',
        url:"/report/use/juan/"+long_null(dept)+"/"+str_null(start)+"/"+str_null(end),
        success:function (json) {
            var models = json.content;
            //console.log(json);
            // console.log(" dept name" + models[0].name);
            if(models.length>0){
                $('#juan_reportForUseTbody').empty();
                for(var i=0;i<models.length;i++){
                    //console.log(models[i]);
                    
                    var $trTemp = $("<tr></tr>");
                    $trTemp.append("<td>"+ models[i].type +"</td>");                
                    $trTemp.append("<td>"+ int_show(models[i]['电子版只读']) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i]['电子版水印下载']) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i]['纸质版复印件']) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i]['cnt']) +"</td>");
                    
                    $trTemp.appendTo("#juan_reportForUseTbody");
                }
            }
        }
    });
    
    $.axx({
        type:'GET',
        url:"/report/use/jian/"+long_null(dept)+"/"+str_null(start)+"/"+str_null(end),
        success:function (json) {
            var models = json.content;
            console.log(json);
            // console.log(" dept name" + models[0].name);
            if(models.length>0){
                $('#jian_reportForUseTbody').empty();
                for(var i=0;i<models.length;i++){
                    console.log(models[i]);
                   
                    var $trTemp = $("<tr></tr>");
                    $trTemp.append("<td>"+ models[i].type +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i]['电子版只读']) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i]['电子版水印下载']) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i]['纸质版复印件']) +"</td>");
                    $trTemp.append("<td>"+ int_show(models[i]['cnt']) +"</td>");
                    
                    $trTemp.appendTo("#jian_reportForUseTbody");
                }
            }
        }
    });
}
function int_show(value){
	if(value){
		return value;
	}else{
		return 0;
	}
}
function str_null(value){
	if(value){
		return value;
	}else{
		return '_';
	}
}
function long_null(value){
	if(value){
		return value;
	}else{
		return -1;
	}
}

function initAssistTable1() {
    var url1 = "/report/year/juan/-1/_/_/";
    $('#reportForYearTable1').bootstrapTable('refreshOptions',{url:url1});

    var url2 = "/report/year/jian/-1/_/_/";
    $('#reportForYearTable2').bootstrapTable('refreshOptions',{url:url2});
}


function extractInitAssistTable($table,col) {
    $table.bootstrapTable('destroy').bootstrapTable({
        striped:true,
        method:'get',
        // toolbar:'#cn_tb_1',
        // url:"/report/year/juan/-1/_/_/",
        cache:false,
        undefinedText:"0",
        sidePagination: "client",
        pagination: true,
        pageNumber:1,
        pageSize: 5,
        showRefresh:true,
        showExport: true,
        exportDataType: 'all',
        exportTypes:[ 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx'],  //导出文件类型
        responseHandler:function (result) {
            return result.content;
        },
        onLoadError:function (status) {
            sessionout(status);
            return false;
        },
        columns:col,

    });
}

function initAssistTable(){
     ASSIST_COL_1 =[[
         {
         "title": "卷级",
         "halign": "center",
         "align": "center",
         "colspan": 10
         }
     ],[{
         field:"year",
         title:"年度/类别",
         align:"center"
     },{
         field:"JJ",
         title:"基建档案",
         align:"center"
     },{
         field:"SX",
         title:"声像档案",
         align:"center"
     },{
         field:"SJJC",
         title:"审计监察档案",
         align:"center"
     },{
         field:"AJ",
         title:"案件档案",
         align:"center"
     },{
         field:"SG",
         title:"生产安全事故档案",
         align:"center"
     },{
         field:"XX",
         title:"信息化建设档案",
         align:"center"
     },{
         field:"ZTB",
         title:"招投标档案",
         align:"center"
     },{
         field:"DJ",
         title:"单机档案",
         align:"center"
     },{
         field:"KJ",
         title:"会计档案",
         align:"center"
     }]];

    ASSIST_COL_2=[[{
        "title": "件级",
        "halign": "center",
        "align": "center",
        "colspan": 9
    }],[{
        field:"year",
        title:"年度/类别",
        align:"center"
    },{
        field:"WS",
        title:"文书档案",
        align:"center"
    },{
        field:"RS",
        title:"人事档案",
        align:"center"
    },{
        field:"SW",
        title:"实物档案",/*WS,KJ,RS,SW,HT,KW,YX,FC,ZJ,DJ*/
        align:"center"
    },{
        field:"HT",
        title:"合同档案",
        align:"center"
    },{
        field:"KW",
        title:"刊物档案",
        align:"center"
    },{
        field:"YX",
        title:"印信档案",
        align:"center"
    },{
        field:"FC",
        title:"房产档案",
        align:"center"
    },{
        field:"ZJ",
        title:"证件档案",
        align:"center"
    }]];
    extractInitAssistTable($('#reportForYearTable1'),ASSIST_COL_1);
    extractInitAssistTable($('#reportForYearTable2'),ASSIST_COL_2)

}

function report_duration_excel1() {
    $('#reportForDurationTable1').tableExport({
        type : 'excel',
        escape : 'false',
        // ignoreColumn : '[' + ignoreColumn + ']'
    });
}
function report_duration_excel2() {
    $('#reportForDurationTable2').tableExport({
        type : 'excel',
        escape : 'false',
        // ignoreColumn : '[' + ignoreColumn + ']'
    });
}
function report_use_excel1() {
    $('#reportForUseTable1').tableExport({
        type : 'excel',
        escape : 'false',
        // ignoreColumn : '[' + ignoreColumn + ']'
    });
}
function report_use_excel2() {
    $('#reportForUseTable2').tableExport({
        type : 'excel',
        escape : 'false',
        // ignoreColumn : '[' + ignoreColumn + ']'
    });
}