(function ($) {
  "use strict";
  $(function () {
    var sidebar = $(".sidebar");

    //Add active class to nav-link based on url dynamically
    //Active class can be hard coded directly in html file also as required
    var current = location.pathname
      .split("/")
      .slice(-1)[0]
      .replace(/^\/|\/$/g, "");
    $(".nav li a", sidebar).each(function () {
      var $this = $(this);
      if (current === "") {
        //for root url
        if ($this.attr("href").indexOf("index.html") !== -1) {
          $(this).parents(".nav-item").last().addClass("active");
          if ($(this).parents(".sub-menu").length) {
            $(this).closest(".collapse").addClass("show");
            $(this).addClass("active");
          }
        }
      } else {
        //for other url
        if ($this.attr("href").indexOf(current) !== -1) {
          $(this).parents(".nav-item").last().addClass("active");
          if ($(this).parents(".sub-menu").length) {
            $(this).closest(".collapse").addClass("show");
            $(this).addClass("active");
          }
        }
      }
    });

    //Close other submenu in sidebar on opening any

    sidebar.on("show.bs.collapse", ".collapse", function () {
      sidebar.find(".collapse.show").collapse("hide");
    });

    //Change sidebar and content-wrapper height
    applyStyles();

    function applyStyles() {
      //Applying perfect scrollbar
      if ($(".scroll-container").length) {
        const ScrollContainer = new PerfectScrollbar(".scroll-container");
      }
    }

    //checkbox and radios
    $(".form-check label,.form-radio label").append(
      '<i class="input-helper"></i>',
    );

    $(".purchace-popup .popup-dismiss").on("click", function () {
      $(".purchace-popup").slideToggle();
    });
  });

  function clean(dirtyHTMLText) {
    var repl = dirtyHTMLText;
    return repl.replace(/<\/?[^>]+(>|$)/g, "");
  }
  var testSets = [];
  var isQuestionNew = false;
  var question = {};
  var questionId;
  var testId = 0;
  $(document).on("click", "a", function (event) {
    testSets = [];
    var id = event.target.id.split("-");
    if (id[0] === "question" && id[1] === "get") {
      var preActiveId = 0;
      testId = id[2];
      $.ajax({
        type: "GET",
        url: "get-questions-for-test/" + testId,
        beforeSend: function () {
          $("tr").each(function () {
            console.log($(this));
            if ($(this)[0].id === "label-" + testId) {
              $(this).addClass("table-success");
            } else {
              if ($(this).hasClass("table-success")) {
                $(this).removeClass("table-success");
              }
            }
          });
        },
        success: function (data) {
          var str = "";
          console.log("data", data);
          data.forEach(function (val, i) {
            console.log(val.questionContent);
            if (!testSets.includes(val.questionSet)) {
              testSets.push(val.questionSet);
            }

            str +=
              "<tr><td class='modal-init' id='shortcontent-" +
              val.questionId +
              "'>" +
              clean(val.questionContent).substring(0, 45) +
              "..." +
              "</td>" +
              "<td id='question-set-" +
              val.questionId +
              "'>" +
              val.questionSet +
              "</td><td style='display:none' id='max-marks-" +
              val.questionId +
              "'>" +
              val.questionMaxMarks +
              "</td><td style='display:none' id='neg-marks-" +
              val.questionId +
              "'>" +
              val.questionNegMarks +
              "</td><td style='display:none' id='content-" +
              val.questionId +
              "'>" +
              val.questionContent +
              "</td><td style='display:none' id='question-options-" +
              val.questionId +
              "'>" +
              val.questionOptions +
              "</td><td style='display:none' id='question-set-" +
              val.questionId +
              "'>" +
              val.questionSet +
              "</td><td style='display:none' id='question-type-" +
              val.questionId +
              "'>" +
              val.questionType +
              "</td><td style='display:none' id='question-tag-" +
              val.questionId +
              "'>" +
              val.questionTag +
              "</td><td style='display:none' id='question-ans-" +
              val.questionId +
              "'>" +
              val.questionAns +
              "</td></tr>";
          });
          console.log(testSets);
          $("#test-questions").html(str);
        },
        error: function (a, b, c) {
          alert("error");
        },
      });
    }
  });

  // $('#modal').iziModal('open');
  $(document).on("click", ".modal-init", function (event) {
    event.preventDefault();

    questionId = $(this)[0].id.split("-")[1];
    question = {
      id: questionId,
      content: $("#content-" + questionId).html(),
      options: $("#question-options-" + questionId).text(),
      answer: $("#question-ans-" + questionId).text(),
      maxMarks: $("#max-marks-" + questionId).text(),
      negMarks: $("#neg-marks-" + questionId).text(),
      set: $("#question-set-" + questionId).text(),
      tag: $("#question-tag-" + questionId).text(),
    };
    $("input[name='questionTag']").val(question.tag);
    $("input[name='questionOptions']").val(question.options);
    $("input[name='questionAns']").val(question.answer);
    $("input[name='questionMaxMarks']").val(question.maxMarks);
    $("input[name='questionNegMarks']").val(question.negMarks);
    $("input[name='questionSet']").val(question.set);
    $("textarea[name='questionContent']").val(question.content);

    testSets.forEach(function (val, i) {
      if (val === question.set) {
        $("select[name='questionSet']").append(
          "<option value='" + val + "' selected>" + val + "</option>",
        );
      } else {
        $("select[name='questionSet']").append(new Option(val, val));
      }
    });
    $("#summernote").summernote({
      toolbar: [
        ["table", ["table"]],
        ["insert", ["picture", "video"]],
        ["style", ["style"]],
        ["font", ["bold", "underline", "clear"]],
        ["view", ["codeview", "help"]],
      ],
      height: 300,
      codemirror: {
        // code mirror options
        theme: "monokai",
      },
    });
    console.log(question, testSets);
    // $('#modal').iziModal('setZindex', 99999);
    // $('#modal').iziModal('open', { zindex: 99999 });
    $("#modal").iziModal("open");
  });

  $(document).on("click", "#save-question", function (e) {
    e.preventDefault();
    var data = new FormData();
    data.append("questionContent", $("#summernote").summernote("code"));
    data.append("questionAns", $("input[name='questionAns']").val());
    data.append("questionMaxMarks", $("input[name='questionMaxMarks']").val());
    data.append("questionNegMarks", $("input[name='questionNegMarks']").val());
    data.append("questionOptions", $("input[name='questionOptions']").val());
    data.append("questionSet", $("select[name='questionSet']").val());
    data.append("questionTag", $("input[name='questionTag']").val());
    data.append("isQuestionNew", isQuestionNew);
    data.append("testId", testId);
    data.append("questionId", questionId);

    $.ajax({
      type: "POST",
      url: "save-question",
      data: data,
      processData: false,
      contentType: false,
      beforeSend: function () {
        $("#save-status").addClass("spinner-border spinner-border-sm");
      },
      success: function (data) {
        console.log("done");
        $("#save-status").removeClass("spinner-border spinner-border-sm");
        $("#shortcontent-" + questionId).html(
          clean($("#summernote").summernote("code")).substring(0, 45) + "...",
        );
        $("#content-" + questionId).html($("#summernote").summernote("code"));
        // set back all the question parameters to give an updated results feel.

        $("#question-set-" + questionId).html(
          $("select[name='questionSet']").val(),
        );

        $("#question-options-" + questionId).text(
          $("input[name='questionOptions']").val(),
        ),
          $("#question-ans-" + questionId).text(
            $("input[name='questionAns']").val(),
          ),
          $("#max-marks-" + questionId).text(
            $("input[name='questionMaxMarks']").val(),
          ),
          $("#neg-marks-" + questionId).text(
            $("input[name='questionNegMarks']").val(),
          ),
          $("#question-tag-" + questionId).text(
            $("input[name='questionTag']").val(),
          );
      },
      error: function (z, b, c) {
        alert("error");
      },
    });

    for (var pair of data.entries()) {
      console.log(pair[0] + ", " + pair[1]);
    }
  });

  if (typeof $("#modal").iziModal !== "undefined") {
    $("#modal").iziModal({
      width: "70%",
      onClosing: function () {
        $("#summernote").summernote("destroy");
        $("select[name='questionSet']").html("");
        $("textarea[name='questionContent']").html("");
      },
    });
  }
})(jQuery);
