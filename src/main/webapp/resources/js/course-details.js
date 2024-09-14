(function () {
  let player = new Plyr(".plyr", {
    controls: [
      "play-large", // The large play button in the center
      "restart", // Restart playback
      "rewind", // Rewind by the seek time (default 10 seconds)
      "play", // Play/pause playback
      "fast-forward", // Fast forward by the seek time (default 10 seconds)
      "progress", // The progress bar and scrubber for playback and buffering
      "current-time", // The current time of playback
      "duration", // The full duration of the media
      "mute", // Toggle mute
      "volume", // Volume control
      "captions", // Toggle captions
      "settings", // Settings menu
      "pip", // Picture-in-picture (currently Safari only)
      "airplay", // Airplay (currently Safari only)
      "fullscreen", // Toggle fullscreen
    ],
  });
  let freeVidPlayer = new Plyr(".free-vid", {
    controls: [
      "play-large", // The large play button in the center
      "restart", // Restart playback
      "rewind", // Rewind by the seek time (default 10 seconds)
      "play", // Play/pause playback
      "fast-forward", // Fast forward by the seek time (default 10 seconds)
      "progress", // The progress bar and scrubber for playback and buffering
      "current-time", // The current time of playback
      "duration", // The full duration of the media
      "mute", // Toggle mute
      "volume", // Volume control
      "captions", // Toggle captions
      "settings", // Settings menu
      "pip", // Picture-in-picture (currently Safari only)
      "airplay", // Airplay (currently Safari only)
      "fullscreen", // Toggle fullscreen
    ],
  });
  function sticky_relocate() {
    var window_top = $(window).scrollTop();
    var div_top = $("#sticky-anchor").offset().top;
    if (window_top > div_top) {
      $("#sticky").addClass("stick");
    } else {
      $("#sticky").removeClass("stick");
    }
  }

  /*$(window).scroll(sticky_relocate);
  sticky_relocate();*/

  $("#order").on("click", function () {
    $("#paytm-submit").click();
  });

  const prod_get_lession = "/get-course-lessons";
  const dev_get_lession = "/get-course-lessons";
  console.log("path", $("#path").html());
  let totalLessons = 0;
  let totalDuration = 0;
  $.ajax({
    type: "POST",
    url: "/get-course-lessons",
    data: {
      code: $("#code").html(),
    },
    beforeSend: function () {
      console.log("sending data...");
      $("#status-arr").addClass("running");
      $("#course-type--bar").addClass("running");
    },
    success: function (data) {
      data = JSON.parse(data);
      data = data[0];
      let chapterHTML = "",
        lessonHTML = "",
        op = "";
      console.log(data);
      data.chapters.forEach(function (chapter, i) {
        chapterHTML +=
          '<li class="container tree-leaf"><p>' + chapter.name + "</p>";
        chapter.lessons.forEach(function (lesson, j) {
          let dur = "";
          let freeTag = "";
          let modalOpen = "";

          // format video time
          if (lesson.duration < 60) {
            dur = lesson.duration + " secs";
          } else {
            dur = lesson.duration / 60 + " min(s)";
          }

          // show free tag on free videos
          if (lesson.isFree) {
            freeTag = "<span>(free)</span>";
            modalOpen =
              'data-toggle="modal" data-target="#free-video-modal" data-link="' +
              lesson.resourse +
              '"';
          }

          lessonHTML +=
            '<li class="tree-leaf" ' +
            modalOpen +
            '><p class="tree-r d-flex justify-content-between"><span><i class="far fa-play-circle"></i>' +
            lesson.name +
            freeTag +
            "</span><span>" +
            dur +
            "</span></p></li>";
          totalLessons++;
          totalDuration += lesson.duration;
        });

        //lessonHTML += '<ul class="tree">' + lessonHTML + '</ul>';
        op += chapterHTML + '<ul class="tree">' + lessonHTML + "</ul></li>";
        lessonHTML = chapterHTML = "";
      });

      $("#lesson-tree").html(op);
      $("#lec-num").html(totalLessons);
      $("#top-lec-num").html(totalLessons);
      $("#lec-hrs").html((totalDuration / 60).toFixed(2));
      $("#course-desc").append($("#course-desc-h").text());
      console.log($("#course-desc-h").text());
      console.log("led", totalLessons);
      $("#status-arr").removeClass("running");
      $("#course-type--bar").removeClass("running");
    },
    error: function () {
      console.log("error");
    },
  });

  // change the player source to load the free video
  $("#free-video-modal").on("show.bs.modal", function (e) {
    console.log(e, freeVidPlayer);
    freeVidPlayer.source = {
      type: "video",
      title: "Example title",
      sources: [
        {
          src: e.relatedTarget.dataset.link,
          type: "video/mp4",
          size: 720,
        },
      ],
    };
  });

  // close and reset play to start
  $("#free-video-modal").on("hide.bs.modal", function (e) {
    freeVidPlayer.stop();
  });
})();
