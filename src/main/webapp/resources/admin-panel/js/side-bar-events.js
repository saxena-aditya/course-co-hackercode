const prod_test_history = "/get-completed-tests";
const prod_test_all = "/get-live-tests";
const prod_courses = "/get-live-courses";
const prod_get_lesson = "/get-course-lessons";
const prod_upload_profile_pic = "/save-profile-pic";

const dev_upload_profile_pic = "save-profile-pic";
const dev_get_lessons = "get-course-lessons";
const dev_courses = "get-live-courses";
const dev_test_history = "get-completed-tests";
const dev_test_all = "get-live-tests";

var activeLesson = "";
var activeChapter = "";
function readURL(input) {
  if (input.files && input.files[0]) {
    var reader = new FileReader();

    reader.onload = function (e) {
      $("#usr-img").attr("src", e.target.result);
    };

    reader.readAsDataURL(input.files[0]);

    let fdata = new FormData();
    fdata.append("profile-pic", input.files[0]);

    var rs = {
      file: input.files[0],
      name: input.files[0].name,
    };
    console.log(rs);
    // in admin-panels's main.js, upload image to S3
    $("#img-contain").addClass("running");
    uploadFile(rs);
  }
}

$("#img-inp").change(function () {
  readURL(this);
});

function clickFileUpload() {
  $("#img-inp").click();

  return false;
}

function getMyCourses() {
  const breadstep = "Courses";
  $.ajax({
    type: "GET",
    url: dev_courses,
    beforeSend: function () {
      $("#status-arr").addClass("running");
    },
    success: function (data) {
      $("#content").html(data);
      //formatTestTime(".time-convert");
      //formatDate(".date-convert");

      $("#status-arr").removeClass("running");
      $(document).trigger("make-slick-course-gal");
    },
    error: function () {
      alert("error");
    },
  });
}
function getCompletedTests() {
  const breadstep = "Test History";
  $.ajax({
    type: "GET",
    url: dev_test_history,
    beforeSend: function () {
      $("#status-arr").addClass("running");
    },
    success: function (data) {
      $("#content").html(data);
      formatTestTime(".time-convert");
      formatDate(".date-convert");

      $("#status-arr").removeClass("running");
    },
    error: function () {
      alert("error");
    },
  });
}

function getLiveTests() {
  const breadStep = "Tests";
  $.ajax({
    type: "GET",
    url: dev_test_all,
    beforeSend: function () {
      $("#status-arr").addClass("running");
    },
    success: function (data) {
      $("#content").html(data);
      formatTestTime(".time-convert");
      formatTimestamp(".valid-till-date");
      $("#status-arr").removeClass("running");
    },
    error: function (a, b, c) {
      alert("error");
    },
  });
}

function formatTestTime(node) {
  $(node).each(function () {
    let val = $(this)[0].innerText;
    let formatedVal = formatDurationHMS(val);
    $(this)[0].innerText = formatIntoMinutes(formatedVal);
  });
}
function parseDuration(duration) {
  let remain = duration;

  let days = Math.floor(remain / (1000 * 60 * 60 * 24));
  remain = remain % (1000 * 60 * 60 * 24);

  let hours = Math.floor(remain / (1000 * 60 * 60));
  remain = remain % (1000 * 60 * 60);

  let minutes = Math.floor(remain / (1000 * 60));
  remain = remain % (1000 * 60);

  let seconds = Math.floor(remain / 1000);
  remain = remain % 1000;

  let milliseconds = remain;

  return {
    days,
    hours,
    minutes,
    seconds,
    milliseconds,
  };
}

function formatIntoMinutes(hms) {
  var a = hms.split(":"); // split it at the colons
  var minutes = +a[0] * 60 + +a[1];
  console.log("minutes", minutes);

  return minutes + " mins";
}

function formatTimeHMS(o) {
  let hours = o.hours.toString();
  if (hours.length === 1) hours = "0" + hours;

  let minutes = o.minutes.toString();
  if (minutes.length === 1) minutes = "0" + minutes;

  let seconds = o.seconds.toString();
  if (seconds.length === 1) seconds = "0" + seconds;

  return hours + ":" + minutes + ":" + seconds;
}

function formatDurationHMS(duration) {
  let time = parseDuration(duration);
  return formatTimeHMS(time);
}

function formatDateStr(dateStr) {
  const months = [
    "Jan",
    "Feb",
    "Mar",
    "Apr",
    "May",
    "June",
    "July",
    "Oct",
    "Sept",
    "Oct",
    "Nov",
    "Dec",
  ];

  var dateBlocks = dateStr.split("-");
  return dateBlocks[2] + "-" + months[dateBlocks[1] - 1] + "-" + dateBlocks[0];
}
function formatDate(node) {
  $(node).each(function () {
    $(this)[0].innerText = formatDateStr($(this)[0].innerText);
  });
}

function formatTimestampStr(stamp) {
  var blocks = stamp.split(" ");
  console.log(blocks);
  var dateStr = blocks[0];
  var timeStr = blocks[1];

  var dateVal = formatDateStr(dateStr);
  var timeVal = timeStr;
  console.log(dateVal);

  return dateVal + " " + timeVal;
}
function formatTimestamp(node) {
  $(node).each(function (i) {
    $(this)[0].innerText = formatTimestampStr($(this)[0].innerText);
    console.log("date " + i + "c" + $(this)[0].innerText);
  });
}

function getCourselessons(courseId) {
  activeChapter = courseId;
  const breadstep = "Courses lessons";
  /*
   * Note: use the `courseId` value to fetch appropriate results
   *
   * */
  $.ajax({
    type: "POST",
    url: dev_get_lessons,
    data: { code: courseId },
    beforeSend: function () {
      $("#status-arr").addClass("running");
    },
    success: function (data) {
      $("#content").html(data);
      //formatTestTime(".time-convert");
      //formatDate(".date-convert");
      buildCourseUI(data, "#content");
      $("#status-arr").removeClass("running");
      $(document).trigger("make-video-player");
    },
    error: function () {
      alert("error");
    },
  });
}
let player = null;
$(document).on("make-video-player", function () {
  //alert("works");
  player = new Plyr("#my-player", {
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

  $("video").hide();
  player.on("canplay", (event) => {
    const instance = event.detail.plyr;
    console.log("canplay", new Date().getTime());
    $("#video-container").removeClass("running");
  });
  player.on("ended", (event) => {
    const instance = event.detail.plyr;
    console.log("ended", event);
    console.log("activelesson", activeLesson);
    // make a ajax call to end the less
    $.ajax({
      type: "GET",
      url: "mark-lesson-complete",
      data: { chapterCode: activeChapter, lessonCode: activeLesson },
      beforeSend: function () {
        console.log("marking lesson finish...");
      },
      success: function (data) {
        if (data) {
          console.log("marked lesson finished...");
        } else {
          console.log(data);
        }
      },
      error: function (a, c, v) {
        alert("error");
      },
    });
  });
});

$(document).on("make-slick-course-gal", function () {
  $(".slick").slick({
    infinite: true,
    center: false,
    slidesToShow: 4,
    slidesToScroll: 4,
    prevArrow:
      "<div class='n-btn prev-btn'><i class='fas fa-angle-left'></i></div>",
    nextArrow:
      "<div class='n-btn next-btn'><i class='fas fa-angle-right'></i></div>",
  });
});

$(document).on("click", ".lesson-title", function (e) {
  $("video-container").addClass("running");
  activeLesson = e.target.id;
  player.source = {
    type: "video",
    title: "Example title",
    sources: [
      {
        src: e.target.dataset.src,
      },
    ],
  };

  console.log("source set", new Date().getTime());
});

function buildCourseUI(data, dest) {
  data = JSON.parse(data);
  data = data[0];
  let courseName = data.name;
  let code = data.code;
  let days = data.days;
  let desc = data.desc;
  console.log(data);
  let output = "";
  let lessonHTML = "";
  data.chapters.forEach(function (chapter, i) {
    chapterHTML =
      '<div class="card-header" id="headingOne">\
		      <div class="hc-acc-title d-flex justify-content-between">\
      	<h5 class="mb-0">\
	        <a class="hc-indicate-alt" data-toggle="collapse" data-target="#collapse-' +
      i +
      '" aria-expanded="true" aria-controls="collapse-' +
      i +
      '">\
	          ' +
      chapter.name +
      '\
	        </a>\
        </h5>\
        <div class="indicate">\
        	<a href="#" data-toggle="collapse" data-target="#collapse-' +
      i +
      '" aria-expanded="true" aria-controls="collapse-' +
      i +
      '">\
        		<i class="fas fa-plus hc-indicator" id="id' +
      i +
      '"></i>\
        	</a>\
        </div>\
      </div>\
    </div>';

    chapter.lessons.forEach(function (lesson, j) {
      console.log(lesson);
      lessonHTML +=
        '<div class="lesson-title">\
						<i class="far fa-file-video"></i> &nbsp;<a href="#" id="' +
        lesson.code +
        '" class="lesson-title" data-src = "' +
        lesson.resourse +
        '">' +
        lesson.name +
        " " +
        lesson.resourse.split(".").pop() +
        "</a>\
					</div>";
    });
    lessonHTMLOOT =
      '<div id="collapse-' +
      i +
      '" class="collapse" aria-labelledby="headingOne" data-parent="#accordion">\
		      <div class="card-body">\
				<div class="lesson-links">' +
      lessonHTML +
      "</div></div></div>";
    output +=
      '<div class="card hc-card">' + chapterHTML + lessonHTMLOOT + "</div>";
    lessonHTML = chapterHTML = "";
  });
  let topHeader =
    '<div class="row"><div class="col-md-12 d-flex justify-content-between"><div class="course-name">' +
    courseName +
    '</div><div class="back-to-course"><a href="#courses" id="back-to-courses"><i class="fas fa-long-arrow-alt-left"></i>Courses</a></div></div></div>';
  let outputTXT =
    topHeader +
    '<div class="row"><div class="col-md-5"><div id="accordion">' +
    output +
    '</div></div><div class="col-md-7 ld-over" id="video-container">\
	<div class="ld ld-ring ld-spin"></div>\
	<video id="my-player" class="video-js" controls playsinline  controlsList="nodownload" style="width:100%">\
	  <source src="" type="video/mp4">\
	</video>\
</div></div>';
  $(dest).html(outputTXT);
  return null;
}

(function () {
  const sideNavHandles = [
    "my-reports",
    "my-exams",
    "reports-alt",
    "courses",
    "init-usr-img-upload",
    "back-to-courses",
  ];
  const courseStartHandle = "course-start-btn";
  const courseStartEvent = "getCourselessons";
  const sideNavEvents = [
    "getCompletedTests",
    "getLiveTests",
    "getCompletedTests",
    "getMyCourses",
    "clickFileUpload",
    "getMyCourses",
  ];
  let container = document.getElementById("content");
  window["getLiveTests"]();
  $(document).on("click", "a, button, i", function (e) {
    if (sideNavHandles.includes(e.target.id)) {
      let index = sideNavHandles.indexOf(e.target.id);
      let exec = sideNavEvents[index];
      window[exec]();
    }
    // handle course start button
    else {
      if (e.target.classList.contains(courseStartHandle)) {
        window[courseStartEvent](e.target.id);
      }
    }
  });
})();
