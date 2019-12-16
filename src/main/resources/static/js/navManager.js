$(function () {
    $("li[id^='id_']").each(function () {
        $(this).removeClass();
        var liID = $(this).attr('id').substring(3);
        if (window.location.pathname.indexOf(liID)>0) {
            $(this).addClass("active");
        }
    });
});