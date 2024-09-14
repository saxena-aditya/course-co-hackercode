$(document).ready(function () {
  $("#sidebarCollapse").on("click", function (e) {
    $("#sidebar").toggleClass("active");
    $(this).toggleClass("active");
  });

  /*$('#test-1').on('click', function(e) {
     	window.open('../take-test/index.html', 'Take Test', 'type=fullWindow, fullscreen, scrollbars=yes');
     });*/
});

function uploadFile(rs) {
  var file = rs.file;
  var fileName = file.name;
  const options = { partSize: 5 * 1024 * 1024, queueSize: 1 };
  var dir = "resources/profile-pictures/";
  fileName = fileName.replace(/\s+/g, "-");
  var fileKey = dir + Date.now() + "_" + fileName;
  //var fileKey = dir +  Date.now() + "_" + fileName;
  s3.upload(
    {
      Key: fileKey,
      Body: file,
      ACL: "public-read",
    },
    options,
  )
    .on("httpUploadProgress", function (evt) {
      var x = parseInt((evt.loaded * 100) / evt.total);
      console.log(x);
    })
    .send(function (err, data) {
      // upload data to database;

      if (!err) {
        $.ajax({
          type: "POST",
          url: "save-profile-pic",
          data: { fileName: fileKey },

          beforeSend: function () {
            $("#img-contain").addClass("running");
            console.log("sending ", fileKey);
          },
          success: function (data) {
            if (data) {
              $("#img-contain").removeClass("running");
            }
          },
          error: function () {
            alert("error could not save filepath");
          },
        });
      } else {
        alert(err);
        console.log(err);
      }
    });

  return;
}
