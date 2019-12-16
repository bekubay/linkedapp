$(function () {
    $("#myfile").change(function () {
        var file = this.files[0];
        if (this.files.length == 0) {
            return;
        }
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
            console.log(data);
            console.log($("#postTextArea"));
            $("#postTextArea").val(data);
            console.log($("#postTextArea"));
        }).fail(function (xhr, status, exception) {
            alert(status, exception);
        });
    });
});
