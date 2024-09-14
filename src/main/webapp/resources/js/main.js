$(document).ready(function () {
  if (window.innerWidth >= 800 && window.innerHeight >= 560) {
    // Resolution is 1024x768 or above
    $("#fullpage").fullpage({
      //options here
      anchors: [
        "sec-front",
        "sec-courses-info",
        "sec-info",
        "sec-team",
        "last",
      ],

      navigation: true,
      slidesNavigation: true,
      autoScrolling: true,
      scrollHorizontally: true,
      responsiveSlides: true,
      menu: "#menu-hack",
    });
  }

  $(".right-slide-x").slick({
    arrows: true,
    dots: true,
    centerMode: true,
    focusOnSelect: true,
    slidesToShow: 1,
    slidesToScroll: 1,

    prevArrow: "<i class='fa fa-arrow-left slide-arrow arrow-prev'></i>",
    nextArrow: "<i class='fa fa-arrow-right slide-arrow arrow-next'></i>",
    asNavFor: ".left-slide-x",
  });
  $(".xx-1").slick({
    arrows: true,
    dots: true,
    centerMode: true,
    focusOnSelect: true,
    slidesToShow: 5,
    slidesToScroll: 1,
    prevArrow: "<i class='fa fa-arrow-left slide-arrow arrow-prev'></i>",
    nextArrow: "<i class='fa fa-arrow-right slide-arrow arrow-next'></i>",
    // asNavFor: '.left-slide-x'
  });

  $(".left-slide-x").slick({
    arrows: true,
    dots: true,
    centerMode: true,
    focusOnSelect: true,
    slidesToShow: 1,
    slidesToScroll: 1,
    variableWidth: true,

    prevArrow: "<i class='fa fa-arrow-left slide-arrow arrow-prev'></i>",
    nextArrow: "<i class='fa fa-arrow-right slide-arrow arrow-next'></i>",
    asNavFor: ".right-slide-x",
  });

  $("#search-box").on("keypress", function (e) {
    // enter
    if (e.which === 13) {
      if ($("#search-box").val().length != 0) {
        window.location = "/search?query=" + $("#search-box").val();
      } else {
        // do something else here
      }
    }
  });
});
