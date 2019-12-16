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
            $("#postList").prepend($.addItem(data));
        }).fail(function (xhr, status, exception) {
            alert(status, exception);
        });
    });

    $.addItem = function(postItem) {
        var itemDetail = '';
        itemDetail += '<div class="panel panel-default post" id="panel_' + postItem.id + '">';
        itemDetail += '<div class="panel-body"><div class="row"><div class="col-sm-2">';
        itemDetail += '<a th:href="@{/user/follow/' + postItem.owner.username + '}" class="post-avatar thumbnail"><img src="' + ((postItem.owner.portrait != null && postItem.owner.portrait !== "") ? "/userimg/" + postItem.owner.portrait : "/img/user.png") + '" alt=""/><div class="text-center">'+ postItem.owner.username +'</div></a>';
        itemDetail += '<div class="likes text-center" id="likeCount">7 Likes</div></div>';
        itemDetail += '<div class="col-sm-10"><div class="bubble"><div class="pointer"><p>';
        if (postItem.attachType == 0) {
            itemDetail += postItem.text + '</p></div>';
        } else if (postItem.attachType == 1) {
            itemDetail += '<img src="' + postItem.attach + '" rel="" /></p></div>';
        } else {
            itemDetail += '<video src="' + postItem.attach + '"  controls="controls">Your browser does not support the video tag.</video></p></div>';
            // width="320" height="240"
        }
        itemDetail += '<div class="pointer-border"></div></div>';
        itemDetail += '<p class="post-actions"><a href="#">Comment</a> - <a href="#">Like</a> - <a href="#">Follow</a> - <a href="#">Share</a></p>';
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
            url: "/user/allPosts"
        }).done(function (data) {
            $.each(data, function (index, value) {
                $("#postList").prepend($.addItem(value));
            });
        }).fail(function (xhr, status, exception) {
            alert(status, exception);
        });
    }

    initData();
});
