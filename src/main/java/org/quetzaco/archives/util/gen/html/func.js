let selectedwjsUpperKeyNum = 0, wjsKeyTotal = 0;
const wjsKey_modal_mapper = {// mapper the modal input id to the java model field name
	wjsMapperBody
		wjsKey_id_input: 'id', 
    }
;

function getwjsUpperKeyId() {
    $table = $("#wjsKey_list_table");
    if ($table.bootstrapTable("getSelections").length != 1) {
        throw "select error";
    } else {
        return $table.bootstrapTable("getSelections")[0].id;
    }
}

//wjsKey,no,bid,date,extract,name,build，company,agent,org,purchase,mode,expert,state

var wjsKey_list_col = [
    {
        checkbox: true
    },
   wjsColBody
    {
        field: 'id',
        visible: false
    }
];
function wjsKeyStatusFormatter(data) {
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
function togglewjsUpperKeyRelatedButtons() {
    const selectedNum = $("#wjsKey_list_table").bootstrapTable('getSelections').length;
    if (selectedNum === 1) {
        $('#createwjsUpperKeyButton').hide();
        $('#editwjsUpperKeyButton').show();
        $('#removewjsUpperKeyButton').show();
    } else if (selectedNum > 1) {
        $('#createwjsUpperKeyButton').hide();
        $('#editwjsUpperKeyButton').hide();
        $('#removewjsUpperKeyButton').show();
    } else if (selectedNum === 0) {
        $('#createwjsUpperKeyButton').show();
        $('#editwjsUpperKeyButton').hide();
        $('#removewjsUpperKeyButton').hide();
    }
}

function loadAllwjsUpperKey() {
    selectedNum = 0;
    $.axx({
        type: 'get',
        url: '/wjsKey/all',
        success: function (json) {
            wjsKeyTotal = json.content.length;
            $('#wjsKey_list_table').bootstrapTable("load", json.content);
        },
        error: function (res) {
            console.log(res);
            alert(res);
        }
    })
}

function setUpwjsUpperKeyPage() {
    $('#wjsKey_list_table').bootstrapTable({
        pagination: false,
        clickToSelect: true,
        singleSelect: true,
        uniqueId: 'id',//唯一的标识
        totalField: '总数：',
        columns: wjsKey_list_col,
        toolbar: '#wjsKey_table_toolbar',
        search: true,
        showLoading: true,
        onCheck: togglewjsUpperKeyRelatedButtons,
        onUncheck: togglewjsUpperKeyRelatedButtons
    });
    loadAllwjsUpperKey();

    $('#wjsKeyModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var recipient = button.data('title') // Extract info from data-* attributes
        // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
        // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
        var modal = $(this)
        modal.find('.modal-title').text(recipient);
        let selectedwjsUpperKey = $('#wjsKey_list_table').bootstrapTable('getSelections')[0];
        if (selectedwjsUpperKey) {
            console.log(selectedwjsUpperKey);
            setModalData(modal, wjsKey_modal_mapper, selectedwjsUpperKey);
        } else {
            setModalData(modal, wjsKey_modal_mapper);
        }
    });
    $(function () {
        $('#wjsKey_datetimepicker1').datetimepicker({
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

function createwjsUpperKey() {
    const wjsKey = $('#createwjsUpperKeyForm').serializeJson();
    if (wjsKey.id) {
        $.axx({
            type: 'put',
            url: '/wjsKey',
            data: JSON.stringify(wjsKey),
            contentType: 'application/json',        //有关不能传递复杂类型的问题：这个要设置
            success: function (json) {
                $('#wjsKeyModal').hide();
                $('body').removeClass('modal-open');
                $('.modal-backdrop').remove();
                loadAllwjsUpperKey();
            },
            error: function (res) {
                console.log(res);
                alert(res);
            }
        });
    } else {
        $.axx({
            type: 'post',
            url: '/wjsKey/create',
            data: JSON.stringify(wjsKey),
            contentType: 'application/json',        //有关不能传递复杂类型的问题：这个要设置
            success: function (json) {
                $('#wjsKeyModal').hide();
                $('body').removeClass('modal-open');
                $('.modal-backdrop').remove();
                loadAllwjsUpperKey();
            },
            error: function (res) {
                console.log(res);
                alert(res);
            }
        });
    }
}

function deletewjsUpperKey() {
    const selectedwjsUpperKey = $('#wjsKey_list_table').bootstrapTable('getSelections');
    const ids = selectedwjsUpperKey.map(function (item) {
        return item.id;
    });
    console.log(JSON.stringify(ids));
    $.axx({
        type: 'delete',
        url: '/wjsKey/delete',
        data: JSON.stringify(ids),
        contentType: 'application/json',        //有关不能传递复杂类型的问题：这个要设置
        success: function (json) {
            loadAllwjsUpperKey();
        },
        error: function (res) {
            console.log(res);
            alert(res);
        }
    });
}