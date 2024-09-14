(function () {
  $(document).on("click", ".s-btn", function (e) {
    console.log(e);
    let articleId = $(this)[0].id.split("-").pop();

    //<i class="fas fa-times-circle"></i>
    if ($(this).hasClass("approve")) {
      console.log("approve");
      let email_send = $(this)[0].parentNode.parentNode.childNodes[5].innerText;
      let name = $(this)[0].parentNode.parentNode.childNodes[7].innerText;

      $.ajax({
        method: "POST",
        url: "/admin/articles/api/approve",
        data: { id: articleId },
        beforeSend: function () {
          console.log("Approving Articel ID: " + articleId);
        },
        success: function (data) {
          if (data) {
            $.ajax({
              method: "POST",
              url: "/user/api/send-mail-article-action",
              data: {
                approved: true,
                emailTo: email_send,
                articleName: name,
              },
              beforeSend: function () {},
              success: function (data) {
                if (data) {
                  let mail = JSON.parse(data);
                  console.log(data);
                }
              },
              error: function (a, xhr, c) {
                console.log(a, xhr, c);
              },
            });

            $("#status-" + articleId).removeClass("fa-check-circle");
            $("#status-" + articleId).addClass("fa-times-circle");
            $("#article-" + articleId).removeClass("btn-success");
            $("#article-" + articleId).addClass("btn-danger");
            $("#article-" + articleId).removeClass("approve");
            $("#article-" + articleId).addClass("disapprove");

            $("#article-" + articleId).attr("title", "Disapprove Article");
          }
        },
        error: function (a, xhr, b) {
          console.log(a, xhr, b);
        },
      });
    } else if ($(this).hasClass("disapprove")) {
      console.log("disapprove");
      let email_send = $(this)[0].parentNode.parentNode.childNodes[5].innerText;
      let name = $(this)[0].parentNode.parentNode.childNodes[7].innerText;

      $.ajax({
        method: "POST",
        url: "/admin/articles/api/disapprove",
        data: { id: articleId },
        beforeSend: function () {
          console.log("Approving Articel ID: " + articleId);
        },
        success: function (data) {
          $.ajax({
            method: "POST",
            url: "/user/api/send-mail-article-action",
            data: {
              approved: false,
              emailTo: "adityasaxena602@gmail.com",
              articleName: "My Demo Article",
            },
            beforeSend: function () {},
            success: function (data) {
              if (data) {
                let mail = JSON.parse(data);
                console.log(data);
              }
            },
            error: function (a, xhr, c) {
              console.log(a, xhr, c);
            },
          });

          $("#status-" + articleId).removeClass("fa-times-circle");
          $("#status-" + articleId).addClass("fa-check-circle");
          $("#article-" + articleId).removeClass("btn-danger");
          $("#article-" + articleId).addClass("btn-success");
          $("#article-" + articleId).removeClass("disapprove");
          $("#article-" + articleId).addClass("approve");
        },
        error: function (a, xhr, b) {
          console.log(a, xhr, b);
        },
      });
    }
  });
})();
