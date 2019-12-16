$(document).on('click', '.commentSubmit', function () {
    // var content = $(this).siblings().find('input').val();
    var postId = $(this).parent().attr("id").substring(5);
    var content = $("#input_text_" + postId).val();
    if (content.length == 0) {
        alert("Content should not plain");
        return;
    }
    $.ajax("/user/addComment", {
        type : "POST",
        dataType : 'JSON',
        data : JSON.stringify({"content": content, "postId": postId}),
        contentType : 'application/json'
    }).done(function (data) {
        $("#input_text_" + postId).val("");
        $("#comments_"+postId).append($.addCommit(data, postId));
    }).fail(function (xhr, status, exception) {
        alert(status, exception);
    });
});

$(document).ready(function() {
    $.addCommit = function(comment, postId) {
        var itemDetail = '';
        itemDetail += '<div class="comment">' +
            '<a href="/user/follow/'+ comment.owner.username +'" class="comment-avatar pull-left"><img src="' + ((comment.owner.portrait != null && comment.owner.portrait !== "") ? "/userimg/" + comment.owner.portrait : "/img/user.png") + '" alt=""/></a><div class="comment-text">' +
            '<p>'+ comment.content +'</p></div></div>' + '<div class="clearfix"></div>';
        return itemDetail;
    }
});
