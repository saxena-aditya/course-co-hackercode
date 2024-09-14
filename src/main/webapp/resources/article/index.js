(function () {
  let articleHeight = $(".article-col").outerHeight();
  let footerHeight = $("footer").outerHeight() / 2;
  let windowHeight = $(window).outerHeight();
  console.log("article: ", articleHeight);
  console.log("footer: ", footerHeight);
  console.log("window: ", $(window).outerHeight());
  $.fn.isInViewport = function () {
    var elementTop = $(this).offset().top;
    var elementBottom = elementTop + $(this).outerHeight();

    var viewportTop = $(window).scrollTop();
    var viewportBottom = viewportTop + $(window).height();

    return elementBottom > viewportTop && elementTop < viewportBottom;
  };

  $(window).scroll(function () {
    console.log(window.innerHeight);
    if (window.innerWidth >= 800 && window.innerHeight >= 560) {
      if ($(this).scrollTop() > 120) {
        $(".dd").addClass("fix");
      } else {
        $(".dd").removeClass("fix");
      }

      if ($("footer").isInViewport()) {
        $(".dd").removeClass("fix");
      }
    }
  });

  let tags = $("#h-tags").val();
  let colors = [
    "none",
    "bg-red",
    "bg-green",
    "bg-purple",
    "bg-orange",
    "bg-blue",
  ];
  if (tags.length > 0) {
    let tagsArr = tags.split(",");
    tagsArr.forEach((tag) => {
      let span = $("<span>", {
        class:
          "tag " + colors[Math.floor(Math.random() * (colors.length - 1)) + 1],
      }).appendTo($(".tags"));
      span.text(tag);
    });
  }
})();
