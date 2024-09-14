(function () {
  // includes a get parameter 'category' for cat name
  const ADD_CATEGORY = "/api/categories/add";
  const DELETE_CATEGORY = "/api/categories/delete/{categoryID}";

  // includes get parameter 'subCategory' for sub-cat name
  const ADD_SUB_CATEGORY = "/api/sub-categories/add/{categoryID}";
  const DELETE_SUB_CATEGORY =
    "/api/sub-categories/delete/{categoryID}/{subCategoryID}";

  const CAT_NAME_ERROR = "Category Name length should be greater than 0";
  const CAT_FOUND_ERROR = "Category already present! Please try again";

  const ADD_CATEGORY_SUCCESS = "Category add successfully";
  const ADD_CATEGORY_ERROR =
    "Oops! There was an error. Could not add the category.";

  let subCatBox = (id, subCategories) => {
    //console.log(subCategories);
    let catId = "sub-cat-d-" + id;
    let box = $("<div>", {
      class: "sub-cat-display",
      id: catId,
    });

    box.append(
      '<span class="add-sub-cat mdi mdi-plus-circle-outline" title="Add Sub Category" data-catID= ' +
        id +
        ">Add Sub Category</span><br>",
    );

    if (subCategories instanceof Array) {
      subCategories.forEach((subCat) => {
        let subCatId = "sub-cat-" + subCat.id;
        let subText = $("<div>", {
          class: "sub-cat-text",
          id: subCatId,
        });
        subText.html(
          subCat.name +
            '<span class="del-sub-cat mdi mdi-close-circle-outline" title="Delete Sub Category" data-catID = ' +
            id +
            " data-subCatID=" +
            subCat.id +
            "></span>",
        );
        box.append(subText);
      });

      if (subCategories.length === 0) {
        box.append(
          '<span class="null-cat" id="null-cat-' +
            id +
            '">Not Categories yet</span>',
        );
      }
    }
    return box;
  };

  let getLeft = () => {};

  let categoryBox = (cat) => {
    let catClass = cat.isNew ? "d-flex new-cat" : "d-flex";
    let catID = "cat-" + cat.id;
    let box = $("<div>", {
      class: catClass,
      id: catID,
    });

    let left = $("<div>", {
      class: "cat-text",
    });
    left.html(
      cat.name +
        '<span class="del-cat mdi mdi-close-circle-outline" title="Delete Category" data-catID = ' +
        cat.id +
        "></span>",
    );

    let right = subCatBox(cat.id, cat.sub_category);

    box.append(left);
    box.append(right);

    return box;
  };

  let catContainer = $("#cat-container");
  let nav;
  try {
    nav = JSON.parse($("#navJSON").val());
    //console.log(nav);
  } catch (e) {
    if (e instanceof SyntaxError) {
      console.log("JSON [404] PARSE ERROR", e);
    } else {
      console.log("JSON [403] CAN NOT READ NAVBAR JSON", e);
    }
  }
  console.log("[INIT]", nav);
  nav.forEach((cat) => {
    catContainer.append(categoryBox(cat));
  });

  let isAddSubCatClicked = [];
  $(document).on("click", ".add-sub-cat", function (e) {
    let catId = e.target.dataset.catid;
    let inputDivId = "input-" + catId;

    let subCatAddStr =
      "<span style='display:inline-flex'><input type='text' class='form-control in' id='add-sub-cat-frm-" +
      catId +
      "'> <span class='sub-cat-add-btn mdi mdi-check-circle' data-catID = '" +
      catId +
      "'  title='Save'></span><span>";

    let subCatAddDiv = $("<div>", {
      id: inputDivId,
    });
    subCatAddDiv.html(subCatAddStr);
    if (!isAddSubCatClicked[catId]) {
      $(this).parent().append(subCatAddDiv);
      isAddSubCatClicked[catId] = true;
    }
  });

  // making category add call,for adding a new category
  $(document).on("click", "#new-cat-btn", function (e) {
    let isError = false;
    let categoryName = $("#new-cat-name").val();
    // category name length should be more that 0
    if (!categoryName.length) {
      alert(CAT_NAME_ERROR);
      isError = true;
    }

    // check if category already present
    var x = nav.filter((obj) => obj.name === categoryName.trim());

    if (x.length) {
      alert(CAT_FOUND_ERROR);
      isError = true;
    }

    if (!isError) {
      $.ajax({
        url: ADD_CATEGORY,
        type: "GET",
        data: {
          category: categoryName,
        },
        beforeSend: function () {
          console.log("adding cat", categoryName, ADD_CATEGORY);
        },
        success: function (data) {
          if (data != -1) {
            console.log("category added", data);
            console.log(nav);
          } else {
            console.log(data);
          }
          $("#cat-save-msg").text(ADD_CATEGORY_SUCCESS);

          let catObj = {
            id: data,
            name: categoryName,
            sub_categories: [],
            isNew: true,
          };

          //append the new category on category array
          nav.push(catObj);
          console.log("[NEW CAT ADDED]", nav);
          // append the new category in the category view
          catContainer.append(categoryBox(catObj));
        },
        error: function (a, b, c) {
          console.log(a, b, c);
          $("#cat-save-msg").text(ADD_CATEGORY_ERROR);
        },
      });
    }
  });

  $(document).on("click", ".del-cat", function (e) {
    let catId = e.target.dataset.catid;
    $.ajax({
      url: DELETE_CATEGORY.replace("{categoryID}", catId),
      type: "GET",
      beforeSend: function () {
        console.log(
          "Removing Category",
          DELETE_CATEGORY.replace("{categoryID}", catId),
        );
        console.log(nav);
      },
      success: function (data) {
        if (data) {
          console.log("done");

          // remove the category div for this ID
          $("#cat-" + catId).remove();

          for (var i = 0; i < nav.length; i++) {
            if (parseInt(nav[i].id) === parseInt(catId)) {
              console.log("delete", nav[i], nav[i].id, catId, i);
              nav.splice(i, 1);
              break;
            }
          }
          console.log("[NEW CAT DELETE]", nav);
        } else {
          console.log("not done");
        }
      },
      error: function (a, b, c) {
        console.log(a, b, c);
      },
    });
  });

  $(document).on("click", ".sub-cat-add-btn", function (e) {
    let catID = e.target.dataset.catid;
    let name = $("#add-sub-cat-frm-" + catID).val();
    $.ajax({
      url:
        ADD_SUB_CATEGORY.replace("{categoryID}", catID) +
        "?subCategory=" +
        name,
      type: "GET",
      beforeSend: function () {
        console.log("adding subcategory");
      },
      success: function (data) {
        if (data != -1) {
          let subCatID = "sub-cat-" + data;
          let subText = $("<div>", {
            class: "sub-cat-text",
            id: subCatID,
          });

          subText.html(
            name +
              '<span class="del-sub-cat mdi mdi-close-circle-outline" title="Delete Sub Category" data-catID = ' +
              catID +
              " data-subCatID=" +
              data +
              "></span>",
          );
          $("#sub-cat-d-" + catID).append(subText);
          $("#input-" + catID).remove();
          isAddSubCatClicked[catID] = false;
          $("#null-cat-" + catID).remove();
        } else {
        }
      },
      error: function (a, b, c) {
        console.log(a, b, c);
      },
    });
  });

  $(document).on("click", ".del-sub-cat", function (e) {
    let catID = e.target.dataset.catid;
    let subID = e.target.dataset.subcatid;

    $.ajax({
      url: DELETE_SUB_CATEGORY.replace(
        "{categoryID}/{subCategoryID}",
        catID + "/" + subID,
      ),
      type: "GET",
      beforeSend: function () {
        console.log("Deleting sub-category", catID, subID);
      },
      success: function (data) {
        if (!data) {
          alert("Some error occured! Please try again.");
        } else {
          $("#sub-cat-" + subID).remove();
        }
      },
      error: function (a, b, c) {
        console.log("[ERROR] delete-sub-category", a, b, c);
      },
    });
  });
})();
