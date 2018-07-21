let selectedFolderNum = 0, folderTotal = 0;
const folder_modal_mapper = {// mapper the modal input id to the java model field name
		name_input: 'name',

	description_area: 'description',


		folder_id_input: 'id'
    }
;

function getFolderId() {
    $table = $("#folder_list_table");
    if ($table.bootstrapTable("getSelections").length != 1) {
        throw "select error";
    } else {
        return $table.bootstrapTable("getSelections")[0].id;
    }
}
function getTreeFolderId() {
    if(parentId){
    	return parentId;
    }else{
    	return "0";
    }
}

//folder,no,bid,date,extract,name,build，company,agent,org,purchase,mode,expert,state

var folder_list_col = [
    {
        checkbox: true
    },
       {
        title: '文件夹名称',
        field: 'name',
        align: 'center',
        sortable: true
        //formatter:wjsKeyStatusFormatter
    },

    {
        title: '描述',
        field: 'description',
        align: 'center',
        sortable: true
        //formatter:wjsKeyStatusFormatter
    },


    {
        field: 'id',
        visible: false
    }
];
function folderStatusFormatter(data) {
    if (data == "" || data == null || data == " ") {
        return '未设置';
    }else if (data == "SET") {
        return '已设置';
    }else if (data == "EXTRACTSET") {
        return '已抽取';
    }else if (data == "CONFIRM") {
        return '部分确认';
    }else if (data == "CONFIRMED") {
        return '已确认';
    }
    return '';
}
function toggleFolderRelatedButtons() {
    const selectedNum = $("#folder_list_table").bootstrapTable('getSelections').length;
    if (selectedNum === 1) {
        $('#createFolderButton').hide();
        $('#editFolderButton').show();
        $('#removeFolderButton').show();
    } else if (selectedNum > 1) {
        $('#createFolderButton').hide();
        $('#editFolderButton').hide();
        $('#removeFolderButton').show();
    } else if (selectedNum === 0) {
        $('#createFolderButton').show();
        $('#editFolderButton').hide();
        $('#removeFolderButton').hide();
    }
}

function loadAllFolder() {
    selectedNum = 0;
    $.axx({
        type: 'get',
        url: '/folder/all',
        success: function (json) {
            folderTotal = json.content.length;
            $('#folder_list_table').bootstrapTable("load", json.content);
        },
        error: function (res) {
            console.log(res);
            alert(res);
        }
    })
}

function setUpFolderPage() {
    $('#folder_list_table').bootstrapTable({
        pagination: false,
        clickToSelect: true,
        singleSelect: true,
        uniqueId: 'id',//唯一的标识
        totalField: '总数：',
        columns: folder_list_col,
        toolbar: '#folder_table_toolbar',
        search: true,
        showLoading: true,
        onCheck: toggleFolderRelatedButtons,
        onUncheck: toggleFolderRelatedButtons
    });
    loadAllFolder();

    $('#folderModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var recipient = button.data('title') // Extract info from data-* attributes
        // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
        // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
        var modal = $(this)
        modal.find('.modal-title').text(recipient);
        let selectedFolder = $('#folder_list_table').bootstrapTable('getSelections')[0];
        if (selectedFolder) {
            console.log(selectedFolder);
            setModalData(modal, folder_modal_mapper, selectedFolder);
        } else {
            setModalData(modal, folder_modal_mapper);
        }
    });
    $(function () {
        $('#folder_datetimepicker1').datetimepicker({
            autoclose: true,
            todayBtn: true,
            todayHighlight: true,
            showMeridian: true,
            pickerPosition: "bottom-left",
            language: 'zh-CN',//中文，需要引用zh-CN.js包
            startView: 0,//月视图
            minView: 0//日期时间选择器所能够提供的最精确的时间选择视图
        });
    });
}

function createFolder() {
    const folder = $('#createFolderForm').serializeJson();
    folder.parentId=getTreeFolderId();
    console.log(folder);
    if (folder.id) {
        $.axx({
            type: 'put',
            url: '/folder',
            data: JSON.stringify(folder),
            contentType: 'application/json',        //有关不能传递复杂类型的问题：这个要设置
            success: function (json) {
                $('#folderModal').hide();
                $('body').removeClass('modal-open');
                $('.modal-backdrop').remove();
                loadAllFolder();
            },
            error: function (res) {
                console.log(res);
                alert(res);
            }
        });
    } else {
        $.axx({
            type: 'post',
            url: '/folder/create',
            data: JSON.stringify(folder),
            contentType: 'application/json',        //有关不能传递复杂类型的问题：这个要设置
            success: function (json) {
                $('#folderModal').hide();
                $('body').removeClass('modal-open');
                $('.modal-backdrop').remove();
                loadAllFolder();
            },
            error: function (res) {
                console.log(res);
                alert(res);
            }
        });
    }
}

function deleteFolder() {
    const selectedFolder = $('#folder_list_table').bootstrapTable('getSelections');
    const ids = selectedFolder.map(function (item) {
        return item.id;
    });
    console.log(JSON.stringify(ids));
    $.axx({
        type: 'delete',
        url: '/folder/delete',
        data: JSON.stringify(ids),
        contentType: 'application/json',        //有关不能传递复杂类型的问题：这个要设置
        success: function (json) {
            loadAllFolder();
        },
        error: function (res) {
            console.log(res);
            alert(res);
        }
    });
}
setUpFolderPage();