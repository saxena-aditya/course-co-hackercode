let currentSlide = 0;

/* DECLERATION OF GLOBAL VARIABLES !!!! */

/* adding a variable for storing the questions-bar slides */

/* setting the set at top  */
const sets_for_types_of_question = [];

/* variable to select the timer clock on web page */

/* marks of each question */

/*---------------------------------------EVENT LISTENER----------------------------*/

//Side view buttons
btns.addEventListener("click", function (e) {
  e.preventDefault();
  if (e.target.tagName === button) {
    if (e.target.attributes.id === undefined) btnID = 0;
    else btnID = parseInt(e.target.attributes.id.value);
    showSlide(btnID);
  }
});

review.addEventListener("click", function (e) {
  e.preventDefault();
  //console.log(currentSlide)
  //console.log("INSIDE REVIEW BUTON ",getSetLength(key))
  if (!(currentSlide === getSetLength(key) - 1)) {
    showNextSlide();
    reviewed(currentSlide - 1);
  } else {
    reviewed(currentSlide);
  }
});
prev.addEventListener("click", function (e) {
  e.preventDefault();
  showPreviousSlide();
});
next.addEventListener("click", function (e) {
  e.preventDefault();
  showNextSlide();
});
clear.addEventListener("click", function (e) {
  e.preventDefault();
  clearResponse();
  clearAnswerResponse(currentSlide);
});

submit.addEventListener("click", function (e) {
  e.preventDefault();
  submitTest();
});

/* toggle set change buttons */
function addEvent() {
  q_type_buttons.map((button) => {
    // console.log(button)
    q_type_buttons[button].addEventListener("click", function (e) {
      // console.log(e);
      // console.log("clicked");
      storeButtons(button);
      changeButtons(q_type_keys[button].key, button);
      toggleSetButtonClass(q_type_keys[button].key, button);
    });
  });
}

/* will remove the local storage variable */
//when browser closed - psedocode
$(window).on("unload", function (e) {
  localStorage.prev_time = localStorage.total_time;
  localStorage.removeItem(total_time);
});
