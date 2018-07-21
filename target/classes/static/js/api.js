var ARCHIVE_API  = new Object();
ARCHIVE_API.dept_create = "/depts";
ARCHIVE_API.dept_list_root = "/depts";
ARCHIVE_API.dept_list_child = "/depts/{0}";
ARCHIVE_API.dept_update = "/depts";
ARCHIVE_API.dept_list_users = "/depts/{0}/users";
ARCHIVE_API.config_list="/config";
ARCHIVE_API.user_detail = "/users/detail";
ARCHIVE_API.dept_byuser = "/depts/{0}/byuser";
BOSS_DEPT_ID = 4; //总裁室（部门id）
KMHK_DEPT_ID=175;//昆明航空有限公司（部门id）
INIT_PASSWORD="0fedf119cd212806b710ca35cbfb4091";
FLOWSTYPE_LENDING = "lending";
FLOWSTYPE_DESTROY="destroy";
FLOWSTYPE_DELIVER="deliver";
FLOWSTYPE_ASSIST="assist";
var ARCHIVE_TYPE=new Object();
ARCHIVE_TYPE.doc=["WS","KJ","RS","SW","HT","KW","YX","FC","ZJ","DJ"];//件级档案类型
ARCHIVE_TYPE.rev=["JJ","SX","SJJC","AJ","SG","XX","ZTB"];//卷级档案类型
ROLE_ID_MANAGER=1
ROLE_ID_ARRANGE=2;
ROLE_ID_DEPT_MANAGER=3;
ROLE_ID_DEPT_ARRANGE=4;
ROLE_ID_UPLOAD=5;
ROLE_ID_EVERYONE=7;


var DMS_API=new Object();
DMS_API.dept_list_folder="/folder/child/{0}/{1}";
DMS_API.dept_list_model="/folder/model/{0}/{1}";


function setModalData(modal, mapper, data) {
    Object.keys(mapper).forEach(function (key) {
        const value = mapper[key];
        if (data) {
            if (typeof(value) === 'string') {
                modal.find('#' + key).val(data[value]);
            } else {
                if (value.isCheckbox) {
                    const regionsFromService = data[value.name];
                    if (value.name === 'regionList') {
                        if (regionsFromService) {
                            //（jquery1.9以上，checkbox attr不能重复操作）可使用prop代替
                            modal.find('input[name=' + key + ']').attr('checked', false);
                            $.each(regionsFromService, function (i, item) {
                                $("input[name='set_region'][value=" + item.region + "]").prop("checked", "checked");
                            });
                        }
                    }
                }
            }
        } else {
            if (typeof(value) === 'string') {
                modal.find('#' + key).val(null);
            } else {
                if (value.isCheckbox) {
                    modal.find('input[name=' + key + ']').attr('checked', false);
                }
            }
        }
    });
}

function isArray(o) {
    return Object.prototype.toString.call(o) == '[object Array]';
}

//退出登录
function logOut() {
    $.axx({
        type: 'get',
        url: "logOut/users",
        data: {},
        success: function (json) {
            console.log(json.content);
            window.location.href = "/";
        }
    })
}


//更改密码
function updatePwd() {
    var bootstrapValidator = $('#updatePwd').data('bootstrapValidator');
    //手动触发验证
    bootstrapValidator.validate();
    if (bootstrapValidator.isValid()) {
        var form = $('#updatePwd').serializeJson();
        var newPwd = form.newPwd;
        var oldPwd = form.oldPwd;
        var againPwd = form.againPwd;
        var str = "";
        /*$.each(form,function (name,val) {
            str = str + "_" + name + ":" + val + "_";
        })
        console.log(str);*/
        var data = {oldPwd: oldPwd, newPwd: newPwd, usrId: usrId};
        $.ajax({
            type: 'post',
            url: "/users/updatePwd",
            data: data,
            success: function (json) {
                var bool = json.content;
                if (bool) {
                    alert('密码修改成功');
                    $('#setpassword').modal('hide');
                    $('#updatePwd').data('bootstrapValidator').resetForm();
                    $('#setpassword').removeAttr('data-backdrop').removeAttr('data-keyboard');
                    $('#setpassword button[data-dismiss="modal"]').removeClass("hidden");
                }
                else
                    alert("旧密码输入错误");
            }
        })
    }
    /*var newPassword = $('#newpassword').val();
    var againPassword = $('#againpassword').val();
    var oldPwd = $('#oldpassword').val();

    if ($.trim(oldPwd) == "") {
        $('#setpassword span').html("旧密码不能为空").removeClass("hidden");
        return;
    }
    if ($.trim(newPassword) == "") {
        $('#setpassword span').html("新密码不能为空").removeClass("hidden");
        return;
    }
    if ($.trim(againPassword) == "") {
        $('#setpassword span').html("确认密码不能为空").removeClass("hidden");
        return;
    }
    if (newPassword != againPassword) {
        $('#setpassword span').html("您输入的两次新密码不一致").removeClass("hidden");
        return;
    }
    if (oldPwd == newPassword) {
        $('#setpassword span').html("新旧密码一样，请重新输入").removeClass("hidden");
    }

    var data = {oldPwd: oldPwd, newPwd: newPassword, usrId: usrId};
    $.ajax({
        type: 'post',
        url: "/users/updatePwd",
        data: data,
        success: function (json) {
            var bool = json.content;
            if (bool) {
                alert('密码修改成功');
                $('#setpassword').modal('hide');
            }
            else
                alert("旧密码输入错误");

        }
    })*/
}


