function Template() {
}

var ARCHIVE_TPL = new Template();
ARCHIVE_TPL.getSelectOption = function (data) {
  return '<option>'+data+'</option>'
}
ARCHIVE_TPL.getDeptNodes = function(data){
  return [
            {
              text: "文件",
              id:data.id,
              deptName:data.name,
              href:"#wenJian"
            },
            {
              text: "案卷",
              id:data.id,
              deptName:data.name,
              href:"#records"
            },
            {
              text: "档案",
              id:data.id,
              deptName:data.name,
              href:"#archives"
            },
            {
              text: "全宗",
              id:data.id,
              deptName:data.name,
              href:"#fond"
            }
          ];
}

