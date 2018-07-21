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