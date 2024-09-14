$(function () {
  $(".a-tags").select2({
    tags: true,
    tokenSeparators: [","],
  });

  $("#summernote").summernote({
    height: 300,
    codemirror: {
      // code mirror options
      theme: "monokai",
    },
  });

  // get sub-categories when a category is selected.
  $("#cat").on("change", function (e) {
    $.ajax({
      method: "POST",
      url: "/user/drafter/api/get-sub-category",
      data: {
        categoryCode: e.target.value,
      },
      beforeSend: function () {
        console.log("Sending");
      },
      success: function (data) {
        if (data) {
          let categories = JSON.parse(data);
          console.log(categories);

          let subCatContainer = $("#sub-cat");
          subCatContainer.html("");
          categories.forEach((cat) => {
            let option = $("<option>", {
              value: cat.id,
            }).appendTo(subCatContainer);
            option.text(cat.name);
          });
        } else {
          console.log("incorrect result returned");
        }
      },
      error: function (a, xhr, c) {
        console.log("Error Occured!");
      },
    });
  });

  // handle article submission
  $(document).on("click", ".sub-article", function (e) {
    let article = {};
    let submitBtn = $(".sub-article");

    article.title = $(".a-title").val();
    article.tags = $(".a-tags").val();
    article.categoryCode = $(".a-cat").val();
    article.subCategoryCode = $(".a-sub-cat").val();
    article.content = $("#summernote").summernote("code");

    let error = false;
    let btnText = $(".sub-article").html();
    console.log(btnText);

    Object.keys(article).forEach((e) => {
      if (!article[e] || article[e].length == 0) error = true;
    });

    if (error) {
      alert("Error!");
    } else {
      $.ajax({
        method: "POST",
        url: "/user/drafter/api/save-article",
        data: {
          title: article.title,
          tags: article.tags.join(","),
          categoryCode: article.categoryCode,
          subCategoryCode: article.subCategoryCode,
          content: article.content,
          doUpdate: submitBtn.hasClass("update-article"),
        },
        beforeSend: function () {
          $(".sub-article").text("Saving...");
        },
        success: function (data) {
          let article = JSON.parse(data);
          console.log(article);
          if (article.error) {
            iziToast.error({
              title: "Error",
              message: article.error_text,
            });
          } else {
            iziToast.success({
              title: "Drafted",
              message: article.msg,
            });
          }

          $(".sub-article").html(btnText);
        },
        error: function (a, xhr, b) {
          console.log(a, xhr, b);
        },
      });
    }
  });

  // edit article
  $(document).on("click", ".article-edit", function (e) {
    let id = $(this)[0].id;
    console.log(e);
    console.log(id);
    $.ajax({
      method: "GET",
      url: "/user/drafter/api/get-article",
      data: {
        articleHash: id,
      },
      beforeSend: function () {},
      success: function (data) {
        if (data) {
          let article = JSON.parse(data);
          console.log(article);
          if (!article.error) {
            // add update-article class to submit button
            let submitBtn = $(".sub-article");
            let tags = article.article.tags.split(",");
            let tagSelect = $(".a-tags");
            let catSelect = $(".a-cat");
            let subCatSelect = $(".a-sub-cat");

            if (!submitBtn.hasClass("update-article"))
              submitBtn.addClass("update-article");

            submitBtn.html("Update");

            $(".a-title").val(article.article.title);
            $(".a-title").attr("disabled", "disabled");

            catSelect.html("");
            subCatSelect.html("");
            tagSelect.html("");

            tags.forEach((tag) => {
              console.log(tag);
              tagSelect
                .append(
                  $("<option>", {
                    value: tag,
                    text: tag,
                  }),
                )
                .trigger("change");
            });

            tagSelect.val(tags).trigger("change");

            article.article.categories.forEach((cat) => {
              if (cat.id == article.article.category_id) {
                catSelect.append(
                  $("<option>", {
                    value: cat.id,
                    text: cat.name,
                    selected: "selected",
                  }),
                );
              } else {
                catSelect.append(
                  $("<option>", {
                    value: cat.id,
                    text: cat.name,
                  }),
                );
              }
            });

            article.article.sub_categories.forEach((cat) => {
              console.log(
                "subcat check",
                cat.id == article.article.sub_category_id,
              );
              if (cat.id == article.article.sub_category_id) {
                console.log(subCatSelect);
                subCatSelect.append(
                  $("<option>", {
                    value: cat.id,
                    text: cat.name,
                    selected: "selected",
                  }),
                );
              } else {
                subCatSelect.append(
                  $("<option>", {
                    value: cat.id,
                    text: cat.name,
                  }),
                );
              }
            });

            $("#summernote").summernote("code", article.article.content);
          } else {
            // alert error
          }
        }
      },
      error: function (a, xhr, b) {
        console.log(a, xhr, b);
      },
    });
  });

  $(document).on("click", ".delete-article", function (e) {
    let id = $(this)[0].id;
    iziToast.question({
      timeout: 20000,
      close: false,
      overlay: true,
      displayMode: "once",
      id: "question",
      zindex: 999,
      title: "Delete Article",
      message: " Are you sure about that?",
      position: "center",
      buttons: [
        [
          "<button><b>YES</b></button>",
          function (instance, toast) {
            $.ajax({
              method: "GET",
              url: "/user/drafter/api/delete-article",
              data: { articleHash: id },
              beforeSend: function () {},
              success: function (data) {
                if (data) {
                  let del = JSON.parse(data);
                  if (del.error) {
                    iziToast.error({
                      title: "ERROR",
                      message: del.error_text,
                    });
                  } else {
                    iziToast.success({
                      title: "DELET",
                      message: del.msg,
                    });

                    $("." + id).fadeOut();
                  }
                }
              },
              error: function (a, xhr, b) {
                console.log(a, xhr, b);
              },
            });

            instance.hide({ transitionOut: "fadeOut" }, toast, "button");
          },
          true,
        ],
        [
          "<button>NO</button>",
          function (instance, toast) {
            instance.hide({ transitionOut: "fadeOut" }, toast, "button");
          },
        ],
      ],
      onClosing: function (instance, toast, closedBy) {
        console.info("Closing | closedBy: " + closedBy);
      },
      onClosed: function (instance, toast, closedBy) {
        console.info("Closed | closedBy: " + closedBy);
      },
    });
  });
});
