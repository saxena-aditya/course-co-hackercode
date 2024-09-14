(function () {
  let nav = $("#nav").val();
  $("#nav").remove();
  console.log("Nav is ", nav);
  if (nav.length > 0) {
    nav = JSON.parse(nav);
    console.log(nav);
    nav.forEach((cat) => {
      let li = $("<li>", {
        class: "nav-item dropdown",
      }).appendTo($("#categories"));

      let a = $("<a>", {
        class: "nav-link dropdown-toggle",
        //href: "articles/" + cat.value,
        id: cat.value,
        "data-toggle": "dropdown",
        "aria-haspopup": "true",
        "aria-expanded": "false",
      }).appendTo(li);
      a.text(cat.name);

      let div = $("<div>", {
        class: "dropdown-menu dropdown-menu-right",
        "aria-labelledby": cat.value + "-" + cat.name,
      }).appendTo(li);

      if (cat.sub_category.length > 0) {
        cat.sub_category.forEach((sub_cat) => {
          let a = $("<a>", {
            class: "dropdown-item",
            href: "/articles/" + cat.value + "/" + sub_cat.value,
          }).appendTo(div);
          a.text(sub_cat.name);
        });
      }
    });
  }

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
})();
