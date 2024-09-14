$(document).ready(function () {
  var saveTestBtn = $("#add-save-test");
  var status = $("#status");
  $("#status-contain").hide();
  saveTestBtn.on("click", function (e) {
    e.preventDefault();
    var formData = new FormData();
    formData.append("name", $("input[name='name']").val());
    formData.append("admin", $("input[name='admin']").val());
    formData.append("testId", $("input[name='testId']").val());
    formData.append("filePath", $("input[name='filePath']").val());
    formData.append("code", $("input[name='code']").val());
    formData.append("password", $("input[name='password']").val());
    formData.append("startTime", $("input[name='startTime']").val());
    formData.append("endTime", $("input[name='endTime']").val());
    formData.append("totalTimeHours", $("input[name='totalTimeHours']").val());
    formData.append(
      "totalTimeMinutes",
      $("input[name='totalTimeMinutes']").val(),
    );
    formData.append("courseCode", $("select[name='courseCode']").val());

    formData.append("totalTime", $("input[name='totalTime']").val());
    formData.append(
      "isTimeStrict",
      $("input[name='isTimeStrict']:checked").val(),
    );
    formData.append(
      "isAnsShuffle",
      $("input[name='isAnsShuffle']:checked").val(),
    );

    var fileInput = document.getElementById("upload-btn-real");
    var file = fileInput.files[0];

    formData.append("file", file);

    for (var pair of formData.entries()) console.log(pair[0] + ", " + pair[1]);

    $.ajax({
      type: "POST",
      url: "save-test",
      data: formData,
      processData: false,
      contentType: false,
      beforeSend: function (xhr) {
        $("#status")
          .removeClass("fa fa-arrow-right")
          .removeClass("fa-check")
          .addClass("spinner-border spinner-border-sm");
      },
      success: function (result) {
        console.log(result);
        if (result == 1) {
          $("#status")
            .addClass("fa fa-check")
            .removeClass("spinner-border spinner-border-sm");
          $("#status-text").html(
            "<h4 style='color: #4CAF50'>Test Details saved successfully!</h4>",
          );
          $("#status-contain").show();
        } else {
          $("#status")
            .addClass("fa fa-arrow-right")
            .removeClass("spinner-border spinner-border-sm");
          $("#status-text").html(
            "<h4 style='color: #F44336'>Oops! Some Error Occured!</h4>",
          );
          $("#status-contain").show();
        }
      },
      error: function (xhr, status, error) {},
    });
    return false;
  });
});
