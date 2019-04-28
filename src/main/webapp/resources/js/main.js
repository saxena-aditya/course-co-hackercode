$(document).ready(function() {

    $('#fullpage').fullpage({
        //options here
        navigation: true,
        slidesNavigation: true,
        autoScrolling: true,
        scrollHorizontally: true,
        menu: '#menu-hack'

    });
    $('.right-slide-x').slick({
        arrows: true,
        dots: true,
        centerMode: true,
        focusOnSelect: true,
        slidesToShow: 1,
        slidesToScroll: 1,
        prevArrow: "<i class='fa fa-arrow-left slide-arrow arrow-prev'></i>",
        nextArrow: "<i class='fa fa-arrow-right slide-arrow arrow-next'></i>",
        asNavFor: '.left-slide-x'

    });
    $('.xx-1').slick({
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

    $('.left-slide-x').slick({
        arrows: true,
        dots: true,
        centerMode: true,
        focusOnSelect: true,
        slidesToShow: 1,
        slidesToScroll: 1,
          variableWidth: true,

        prevArrow: "<i class='fa fa-arrow-left slide-arrow arrow-prev'></i>",
        nextArrow: "<i class='fa fa-arrow-right slide-arrow arrow-next'></i>",
        asNavFor: '.right-slide-x'

    });

});