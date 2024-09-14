console.log("VARIABLE.js loaded");

/* BUTTONS AND DOM ELEMENTS :- */
let clear = document.getElementById("clear-response-btn"); //clear button
let review = document.getElementById("review-btn"); //review button
let next = document.getElementById("next-btn"); //next button
let prev = document.getElementById("prev-btn"); //previous button
let submit = document.getElementById("submit-btn"); //submit button
let btns = document.getElementById("question-btns"); //side view buttons DOM
let test = document.getElementById("test-container-all"); //main test container where slide will get loaded DOM
let time_clock = document.getElementById("time-clock"); //time clock DOM
let question_type = document.getElementById("question_type"); //question type DOM
let marks = document.getElementById("marks"); //for showing marks DOM
let set_btns = document.getElementById("question_type"); //for showing the set
let slides = undefined; //stores the slides
let questionNumber = document.getElementById("question-number"); //to access the question number
let test_id = document.getElementById("test_id").value; // will contain info about test_id
let user_id = document.getElementById("user___id").value; // will contain user_id
let confirmSubmit = document.getElementById("confirmSubmit"); //for confirming submit
let instruction_button = document.getElementById("inst-btn");
let isSubmitButtionClicked = false;
let wasLastSlide = false;

/* main variables */
let total_exam_duration = 0; //stores the time of exams
let currentSlide = 0; //will store the currentSlide info
let previousSlide = -1; //will store the previous slide info
let current_question_set = 0; //will store the question set active
let previous_question_set = undefined; //will store the question set which was active previously
let current_question_number = undefined; //stores the question number of active set
let previous_question_number = undefined; //stores the previous question number of active set
let test_store = undefined; //will store the whole test to itself
let test_started = undefined; //will store a boolean value
let answer_status_store = {}; //will store the object so that we can store it
let is_db_for_answer = false; //for checking if we have a answer database
//let global_index				=   0;

/* variables that will store the main ingredients of test */
let test_slides = []; //will contain all the question slides
let sideview_buttons = []; //will contain the sideview buttons
let question_sets = []; //will contain the question sets button

/* global variables that will be used frequently */
let test_type = undefined; //stores the type of test
let start_time = undefined; //stores the start_time of test
let end_time = undefined; //stores the end_time of test
let setChangeButtonClicked = false; //for checking if change set button was clicked or not
