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
            $("#postTextArea").val("");
            $("#postList").prepend($.addItem(data.postList[0], data.user));
        }).fail(function (xhr, status, exception) {
            alert(status, exception);
        });
    });

    $.addItem = function(postItem, user) {
        if (postItem.unhealth_info == false) {
            return "";
        }
        var itemDetail = '';
        var hasLiked = "like";
        var hasLikedClass = "";
        if (postItem.likedBy.length > 0) {
            $.each(postItem.likedBy, function (i, item) {
                if (item.username === user.username) {
                    hasLiked = "unlike";
                    hasLikedClass = " heartAnimation";
                }
            });
        }
        itemDetail += '<div class="panel panel-default post" id="panel_' + postItem.id + '">';
        itemDetail += '<div class="panel-body"><div class="row"><div class="col-sm-2">';
        itemDetail += '<a href="/user/profile/' + postItem.owner.username + '" class="post-avatar thumbnail post-thumbnail"><img src="' + ((postItem.owner.portrait != null && postItem.owner.portrait !== "") ? "/userimg/" + postItem.owner.portrait : "/img/user.png") + '" alt=""/><div class="text-center">'+ postItem.owner.username +'</div></a>';
        itemDetail += '<div class="likes text-center datePosition" id="likeCount"><span class="date">' + DateFormat.format.prettyDate(postItem.date) + '</span></div></div>';
        itemDetail += '<div class="col-sm-10"><div class="bubble"><div class="pointer"><p>';
        itemDetail += postItem.text + '</p>';
        if (postItem.attachType == 1) {
            itemDetail += '<p><img src="' + postItem.attach + '" rel="" /></p>';
        } else if (postItem.attachType == 2) {
            itemDetail += '<p><video src="' + postItem.attach + '"  controls="controls">Your browser does not support the video tag.</video></p>';
            // width="320" height="240"
        }
        itemDetail += "</div>"
        itemDetail += '<div class="pointer-border"></div></div>';
        itemDetail += '<p class="post-actions"><div class="heart' + hasLikedClass + '" id="like_' + postItem.id + '" rel="'+ hasLiked +'"></div><div class="likeCount" id="likeCount' + postItem.id + '">' + postItem.likedBy.length + '</div></p>';
        itemDetail += '<div class="comment-form">' +
            '<form class="form-inline" id="form_' + postItem.id + '">' +
            '<div class="form-group"><input id="input_text_' + postItem.id + '" type="text" class="form-control" placeholder="enter comment"></div>' +
            '<button type="button" class="commentSubmit btn btn-default">Add</button></form></div>';
        itemDetail += '<div class="clearfix"></div>' +
            '<div class="comments" id="comments_' + postItem.id + '">';
        $.each(postItem.comments, function (index, value) {
            itemDetail += $.addCommit(value, postItem.id);
        });

        itemDetail += '</div></div></div></div></div>';
        return itemDetail;
    };

    function initData() {
        $.ajax({
            type: 'GET',
            dataType: "json",
            url: "/user/" + $("#postList").attr("value") + "/allPosts"
        }).done(function (data) {
            $.each(data.postList, function (index, value) {
                $("#postList").prepend($.addItem(value, data.user));
            });
        }).fail(function (xhr, status, exception) {
            alert(status, exception);
        });
    }

    initData();
});
