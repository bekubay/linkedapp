$(function () {
    $("#postSubmit").click(function () {
        var content = $("#postTextArea").val();
        if (content.length == 0) {
            alert("Content should not plain");
            return;
        }
        $.post("/user/submitPost", {
            content : content,
            contentType:'application/json;charset=utf-8'
        }).done(function (data) {
            var od = JSON.stringify(data);
            alert(od);
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
            $("#postTextArea").append("<br>" + data);
        }).fail(function (xhr, status, exception) {
            alert(status, exception);
        });
    })
});
