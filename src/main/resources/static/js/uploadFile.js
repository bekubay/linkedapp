$(function () {
    $("#imgSubmit").click(function () {
        var form = $("#uploadPost")[0];
        var data = new FormData(form);
        $.ajax("upload", {
            type : "POST",
            processData : false,
            contentType : false,
            enctype : "multipart/form-data",
            data : data
        }).done(function (data) {
            alert(data);
        }).fail(function (xhr, status, exception) {
            alert(status, exception);
        });
    })

    $("#myfile").change(function () {
        var file = this.files[0];
        if (this.files.length > 1) {
            alert("Each time can only upload one file");
        }
        var formData = new FormData();
        formData.append('file', file);
        $.ajax("/user/uploadFile", {
            type : "POST",
            processData : false,
            contentType : false,
            enctype : "multipart/form-data",
            data : formData
        }).done(function (data) {
            alert(data);
        }).fail(function (xhr, status, exception) {
            alert(status, exception);
        });
        // 这里写一个ajax把通过post把this.files[0]提交到后台就可以了。
    })
});
