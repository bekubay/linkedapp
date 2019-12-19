$(document).ready(function () {
    $(document).on('click', '.heart', function () {
        var heart = $(this);
        var likeCount = $("#likeCount" + heart.attr("id").split("like_")[1]);
        var intCount = parseInt(likeCount.html());
        var rel = heart.attr("rel");

        heart.css("background-position", "");

        var postId = heart.attr("id").substring(5);
        $.ajax("/user/commitLike", {
            type : "POST",
            dataType : 'JSON',
            data : JSON.stringify({"content": "", "postId": postId}),
            contentType : 'application/json'
        }).done(function (data) {
            if (data.like) { //rel === "like"
                likeCount.html(data.count); //intCount + 1
                heart.addClass("heartAnimation").attr("rel", "unlike");
            } else {
                likeCount.html(data.count); //intCount - 1
                heart.removeClass("heartAnimation").attr("rel", "like");
                heart.css("background-position", "left");
            }
        }).fail(function (xhr, status, exception) {
            alert(status, exception);
        });

    }).on("mouseleave", function () {
        $(this).css("background-position", "");
    });

});