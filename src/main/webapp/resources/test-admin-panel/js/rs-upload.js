(function () {
  $("#status, .progress").hide();

  var albumBucketName = "hcvideo212";
  var bucketRegion = "ap-south-1";
  var IdentityPoolId = "ap-south-1:8ab0020b-b609-4377-899b-bd39c4aa120b";

  AWS.config.update({
    credentials: new AWS.CognitoIdentityCredentials({
      IdentityPoolId: IdentityPoolId,
    }),
  });
  AWS.config.region = bucketRegion;

  var s3 = new AWS.S3({
    apiVersion: "2006-03-01",
    params: {
      Bucket: albumBucketName,
    },
  });
  /*event listener for adding more resources fields*/
  let rs = 1;
  $(".add-rs").on("click", function (e) {
    if (e.target.tagName === "BUTTON") {
      $("#rs-body").append(
        '<div class="row" id="rs-' +
          rs +
          '">\
                            <div class="col-md-12">\
                                <div class="form-group">\
            		<div class="row">\
                                    <div class="col-md-8">\
                                        <label for="">Resource Title</label>\
                                        <input type="text" class="rs-name form-control" id="rs-title-' +
          rs +
          '">\
                                    </div>\
                                    <div class="col-md-4">\
                                        <label for="">Resource File</label>\
                                        <input type="file" class="rs-file form-control" id="rs-file-' +
          rs +
          '">\
                                    </div>\
                                        </div>\
                                </div>\
                            </div>\
                        </div>',
      );
      rs++;
    }
  });

  let rsSize = 0;
  var loaded = [];
  var titles = [];
  var totalSize = 0;
  $(".upload-rs").on("click", function (e) {
    if (e.target.tagName === "BUTTON") {
      let $rs = $(".rs-file");
      console.log($rs);
      let $rsArr = [];
      $rs.each(function (rs, i) {
        if ($(this)[0].files[0] !== undefined) {
          let id = $(this)[0].id.split("-").pop();
          console.log("#rs-title-" + id);
          let $rsObj = {
            name: $("#rs-title-" + id).val(),
            file: $(this)[0].files[0],
          };
          $rsArr.push($rsObj);
        }
      });
      loaded = [];
      totalSize = 0;
      $rsSize = $rsArr.length;
      $rsArr.forEach(function (rs, i) {
        totalSize += rs.file.size;
        loaded[rs.file.name] = 0;
      });

      $rsArr.forEach(function (rs) {
        uploadFile(rs);
      });
    }
  });

  function uploadFile(rs) {
    var file = rs.file;
    var fileName = file.name;
    const options = { partSize: 5 * 1024 * 1024, queueSize: 1 };
    var dir = encodeURIComponent("resources") + "/";
    fileName = fileName.replace(/\s+/g, "-");
    var fileKey = dir + Date.now() + "_" + fileName;
    titles[fileKey] = rs.name;
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
        var loadedTotal = 0;
        loaded[this.body.name] = evt.loaded;
        for (var j in loaded) {
          loadedTotal += loaded[j];
        }
        var xx = Math.round((loadedTotal / totalSize) * 100);
        $("#status, .progress").show();
        $("#bar-progress").attr("style", "width: " + xx + "%");
        $("#percent-progress").html(xx);

        if (loadedTotal === totalSize) {
          // remove progress bar and
          // so other
        }
      })
      .send(function (err, data) {
        // upload data to database;
        console.log(data);
        console.log(titles[data.Key]);
        $.ajax({
          type: "GET",
          url: "/api/add-resource",
          data: { name: titles[data.Key], key: data.Key },
          beforeSend: function () {
            console.log("saving resource....");
          },
          success: function (data) {
            console.log("resource added!");
            alert("Resource successfully added.");
          },
          error: function (z, b, c) {
            alert("Opps! Seems like the server is down. Please try again.");
          },
        });
      });

    return;
  }
})();
