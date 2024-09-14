console.log("METHODS.js");

$(function () {
  /* function for getting test data */
  let testInfo = {
    test_id: test_id,
    user_id: user_id,
  };

  window.onload = function () {
    document.addEventListener("keydown", function (event) {
      console.log("event", event.keyCode);
      if (event.keyCode === 27 || event.keyCode === 122) {
        e.preventDefault();
        disableEvent(event);
      }
    });

    document.addEventListener(
      "contextmenu",
      function (e) {
        e.preventDefault();
      },
      false,
    );

    document.addEventListener(
      "keydown",
      function (e) {
        //document.onkeydown = function(e) {
        // "I" key
        /* if (e.ctrlKey && e.shiftKey && e.keyCode == 73) {
            disabledEvent(e);
          }*/
        // "J" key
        if (e.ctrlKey && e.shiftKey && e.keyCode == 74) {
          disabledEvent(e);
        }
        // "S" key + macOS
        if (
          e.keyCode == 83 &&
          (navigator.platform.match("Mac") ? e.metaKey : e.ctrlKey)
        ) {
          disabledEvent(e);
        }
        // "U" key
        if (e.ctrlKey && e.keyCode == 85) {
          disabledEvent(e);
        }
        // "F12" key
        if (event.keyCode == 123) {
          disabledEvent(e);
        }
      },
      false,
    );

    function disabledEvent(e) {
      if (e.stopPropagation) {
        e.stopPropagation();
      } else if (window.event) {
        window.event.cancelBubble = true;
      }
      e.preventDefault();
      return false;
    }
  };

  function getTestData() {
    let link = window.location.href;
    let testId = link.split("/").pop();
    $.ajax({
      url: test_start_url + testId,
      type: "POST",
      contentType: "application/json",
      dataType: "json",
      data: JSON.stringify(testInfo),
      beforeSend: function () {
        $(".test-loading").show();
      },
      success: function (data) {
        console.log("SERVER APPROACHED");
        console.log("data from server", data);

        //check for previous data to be received
        let previousTest_duration = data.test_duration;
        if (data.previous_data) {
          console.log("previous data of user is present");
          //update the existing object
          const previousData = data.previous_data;
          console.log("JSON PREVIOUS DATA ", previousData);
          previousTest_duration = previousData.timeLeft;
          answer_status_store = Object.assign({}, previousData.data);
          console.log("data after due to previous data ", data);
          console.log("TYPE OF ???", typeof answer_status_store);
          console.log("previousTESTDURATION ", previousTest_duration);
          console.log(
            "answer status store due to previous data ",
            Object.keys(answer_status_store).length,
            answer_status_store,
          );
        }

        //STORING THE RECEIVED DATA FROM SERVER TO VARIABLES
        const test = {
          ...data,
          test_duration: previousTest_duration,
        };

        console.log("TEST ..........<><>", test);
        startTest(test); //function to initiate test and everything
        $(".test-loading").fadeOut();
      },
      error: function (a, b, c) {
        console.log("error occured", a, b, c); //or whatever
        $(".test-error").show();
      },
    });
  }

  //method that will be called to from ajax request to start and build the test
  function startTest(test) {
    console.log("START TEST CALLED");
    //initialize test_store variable so that a copy of test can be stored and retured to the server !
    test_store = {
      ...test,
    }; //copying test to test_store

    if (!(Object.keys(answer_status_store).length === 0)) {
      console.log("@>@>@>@>@>CALLING UPDATE TEST STORE@>@>@>@>@>>@");
      //ie previous data is present
      updateTestStoreDueToPreviousData();
    }

    /* 
        now first thing to do is checking for eligibility of the user to take test or not ! 
        we need to pass the start_time , end_time , total duration of test & test_type
        */

    total_exam_duration = test.test_duration;
    console.log("@@@@@@@total exam duration@@@@@@@@@", total_exam_duration);
    start_time = new Date(test.start_time).getTime();
    end_time = new Date(test.end_time).getTime();
    test_type = test.test_type;
    test_started = test.test_started;
    is_db_for_answer = test.has_db;

    if (!test_started) {
      isEligible(start_time, end_time, total_exam_duration, test_type); //will check the eligibilty of user
      test_store.test_started = true;
      test_started = true;
    }
    testBuilder(test_store.question_set); //will build the test slides and other useful stuff
    toggleClock(total_exam_duration, test_type, start_time, end_time); //will start the clock
  }

  function isEligible(start_time, end_time, total_exam_duration, test_type) {
    //here we will check that the type of test and will call the needful function
    if (test_type == "strict") isEligibleForStrictExam(start_time, end_time);
    if (test_type == "loose") isEligibleForLooseExam(start_time, end_time);
  }

  function isEligibleForStrictExam(start_time, end_time) {
    let currentDay = new Date().getTime();
    if (
      !(
        total_exam_duration > 0 &&
        currentDay < end_time &&
        currentDay >= start_time
      )
    ) {
      window.location.href = "/notEligibleforstrict";
    }
  }

  function isEligibleForLooseExam(start_time, end_time) {
    let currentDay = new Date().getTime();
    if (!(total_duration > 0 && currentDay >= start_time)) {
      window.location.href = "/notEligibleforloose";
    }
  }

  //Function to make the test the real test
  function testBuilder(question_set) {
    console.log("TEST BUILDER CALLED");
    let keysArray = Object.keys(question_set); //we will get the keys here such as set-1 , set-2 ,set-3
    keysArray.forEach((key) => {
      //key would be like set-1 set-2 set-3
      console.log("KEY >>>>>>>>>", key);
      question_sets.push(key); //[set-1,set-2,set-3]
      question_set[key].questions.forEach((question, index) => {
        //question [{} , {} ,{} ]
        //{}
        console.log("question ", question);
        answer = null;
        if (question.answer) {
          answer = question.answer[0];
        }
        if (Object.keys(answer_status_store).length === 0) {
          test_slides.push({
            id: question.id, //id for database
            q_id: index + 1, //for question number
            status: question.status, //status
            question: question.question, //Question
            answers: question.options, //Options
            markedAnswer: answer, //answer will be a array
            marks_for_question: question.max_marks, //storing marks
            negative_for_question: question.neg_marks, //storing the negative marks
          });
        } else {
          test_slides.push({
            id: question.id, //id for database
            q_id: index + 1, //for question number
            status: answer_status_store[question.id].status, //status
            question: question.question, //Question
            answers: question.options, //Options
            markedAnswer: answer_status_store[question.id].answer, //answer will be a array
            marks_for_question: question.max_marks, //storing marks
            negative_for_question: question.neg_marks, //storing the negative marks
          });
        }
        /* adding slide to [] */
      });
      console.log("TEST SLIDES MADE : >>> ", test_slides);
    });

    initDecleration(); //declaring variables
    slidesBuilder(); //method calling for adding slides to DOM
    showSets(); //method for showing sets button on test
    showSlide(0); //will show the first slide
    activeSet(0); //will set the active class for question_set_buttons
    loadSideButton(0); //will load the buttons at the side showing questions
    addEventListenerToSetButtons(); //will add events to set
    answer_statusStore(); //for creating or updating answer_status object
  }

  function answer_statusStore() {
    //check for local storAge object
    if (Object.keys(answer_status_store).length === 0) {
      console.log("$$$$$$$$$$$$$$$$$$$$EMPTY OBJECT PRESENT$$$$$$$$$$$$$");
      ifAnswer_statusNotCreatedAlready(test_slides); //will create the object for storing to database
    }
  }

  function updateTestStoreDueToPreviousData() {
    let keysArray = Object.keys(test_store.question_set);
    keysArray.forEach((key) => {
      test_store.question_set[key].questions.forEach((question, index) => {
        question.answer = answer_status_store[question.id].answer;
        question.status = answer_status_store[question.id].status;
      });
    });

    console.log(
      "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<update test storw",
      test_store,
    );
  }

  /* method for declaring values */
  function initDecleration() {
    //variables
    current_question_set = 0; //will store the index of current question_sets
    previous_question_set = -1; //will store the index of previous question_sets

    //functions
  }

  /* for making slides div and adding to DOM */
  function slidesBuilder() {
    const questionSet = [];
    test_slides.forEach(function (currentQuestion, questionNumber) {
      var qanswers = [];
      for (var option in currentQuestion.answers) {
        if (currentQuestion.markedAnswer) {
          if (
            currentQuestion.answers[option] === currentQuestion.markedAnswer
          ) {
            qanswers.push(`<label>
                        <input type="radio" checked="true" name="question${questionNumber}" value="${currentQuestion.answers[option]}">
                        
                         ${currentQuestion.answers[option]}
                      </label>`);
          } else {
            qanswers.push(`<label>
                        <input type="radio" name="question${questionNumber}" value="${currentQuestion.answers[option]}">
                        
                         ${currentQuestion.answers[option]}
                      </label>`);
          }
        } else {
          qanswers.push(`<label>
                    <input type="radio" name="question${questionNumber}" value="${currentQuestion.answers[option]}">
                    
                     ${currentQuestion.answers[option]}
                  </label>`);
        }
      }
      questionSet.push(`<div class="slide" name=${currentQuestion.id}>
                   <div class="question"> ${currentQuestion.question} </div>
                   <div class="answers"> ${qanswers.join("")} 
                   </div>
                 </div>`);
    });
    btns.innerHTML = "";
    test.innerHTML = questionSet.join("");

    slides = document.querySelectorAll(".slide"); //stores all slides
  }

  function showSets() {
    let temp_set_buttons = question_sets.map((key, index) => {
      let newKey = key.replace(/\s+/g, "-");
      console.log("KEY FROM SHOWSETS ", newKey);
      return `<div class="tag bg-blue-light" id=${newKey} data-set=${index}><p class="margin-0 tag-text fff">${key}</p></div>`;
    });

    set_btns.innerHTML = temp_set_buttons.join("");
  }

  function addEventListenerToSetButtons() {
    for (let i = 0; i < question_sets.length; i++) {
      let key = question_sets[i]; //set-1 set-2 set-3
      $(`#${key}`)[0].addEventListener("click", function (e) {
        setChangeButtonClicked = true;
        addAnsweredOrSkippedClass(currentSlide);
        activeSet(i); //change the class for activeSet
        loadSideButton(i); //will load the buttons at the side showing questions
        console.log(
          "PREVIOUS SLIDE AND CURRENT SLIDE HERE IN ACTION LISTENER >>",
          previousSlide,
          currentSlide,
        );
        addAnsweredOrSkippedClass(previousSlide);
        setChangeButtonClicked = false;
      });
    }
  }

  /* for showing positive and negative marks on slide */
  function showMarks(right_answer, wrong_answer) {
    marks.innerHTML = `<strong class="marks-sec">Mark for this question : <span id="positive">${right_answer}</span> || Negative Marks : <span id="negative">${wrong_answer}</span></strong>`;
  }

  function showSlide(n) {
    console.log("===================> SILDE NUM: ", n);
    slides[currentSlide].classList.remove("active-slide");
    slides[n].classList.add("active-slide");

    console.log("BEFORE PREVIOUS SLIDE >>", previousSlide);
    console.log("BEFORE Current SLIDE >>", currentSlide);
    console.log("BEFORE SLIDE to show >>", n);

    previousSlide = currentSlide; //updating previous slide value
    currentSlide = n; //updating current slide value

    console.log("CURRENT PREVIOUS SLIDE >>", previousSlide);
    console.log("CURRENT Current SLIDE >>", currentSlide);
    console.log("CURRENT SLIDE to show >>", n);

    let q_number = test_slides[n].q_id;
    questionNumber.innerHTML = q_number;

    showTestButton(n); //for showing review , submit , next and previous button
    showMarks(
      test_slides[n].marks_for_question,
      test_slides[n].negative_for_question,
    ); //showing marks of a desired question
  }

  function showTestButton(n) {
    //logic for implementing the change of slide

    let lower_limit_of_length = getLengthTillSetIndex(current_question_set); //will return the length till the starting of this set
    let set_length = getSetLength(current_question_set); //will return the length of current set ie how many question

    console.log(">>", lower_limit_of_length, set_length);
    let total_length =
      parseInt(lower_limit_of_length) + parseInt(set_length) - 1;
    console.log(total_length, n);

    if (n === total_length) {
      //ie the last question of set
      wasLastSlide = true;
      next.style.display = "none";
      prev.style.display = "inline-block";
    } else if (n === lower_limit_of_length) {
      //ie the first question of set
      next.style.display = "inline-block";
      prev.style.display = "none";
      wasLastSlide = false;
    } else {
      wasLastSlide = false;
      next.style.display = "inline-block";
      prev.style.display = "inline-block";
    }
  }

  function activeSet(index) {
    let key = question_sets[index]; //will give set-1 set-2 set-3
    let newKey = key.replace(/\s+/g, "-");
    key = newKey;

    let prev_key = question_sets[current_question_set]; //will give set-1 set-2 set-3
    prev_key = prev_key.replace(/\s+/g, "-");
    console.log("PREVIOUS KEY 383", prev_key);

    const older_set_button = $(`#${prev_key}`)[0]; //will give button
    const new_set_button = $(`#${key}`)[0]; //will give the button
    /*<div class="tag bg-blue-light" id=${key}><p class="margin-0 tag-text fff">${key}</p></div>*/

    //Now remove the class from the previous button and set the class of new button
    console.log(older_set_button, "111111111111111111111111111111111");
    older_set_button.removeAttribute("class", "bg-blue tag2"); //remove the older active class
    older_set_button.setAttribute("class", "bg-blue-light tag"); //add the normal class

    new_set_button.removeAttribute("class", "bg-blue-light"); //remove the normal class
    new_set_button.setAttribute("class", "bg-blue tag2"); //add the active class

    previous_question_set = current_question_set; //updating the var
    current_question_set = index; //updating the var
  }

  function loadSideButton(index) {
    /*
            >>change the slide also when a button is clicked
            >>load the buttons to the right side of window
        */
    const key = question_sets[index];
    let sideViewButtons = []; //initialize a empty array to store sideview buttons
    let length = getLengthTillSetIndex(index);
    console.log("&&&&&&&&&&&" + length + "===" + index);
    test_store.question_set[key].questions.forEach((question, index) => {
      console.log("question.status", question.status);
      sideViewButtons.push(
        `<button class="classic-btn  ${question.status}" id="sideview${length + index}" value="${index + length}">${index + 1}</button>`,
      );
    });
    btns.innerHTML = "";
    btns.innerHTML = sideViewButtons.join("");
    addEventListenerToSideButtons(index);
    showSlide(length); //will show the first slide of set
    console.log("||||||||||| LENGTH: ", length);
  }

  //will be returning the length(starting point)
  function getLengthTillSetIndex(index) {
    let length = 0;

    if (index > 0) {
      for (let i = 0; i < index; i++) {
        const set_length =
          test_store.question_set[question_sets[i]].questions.length;
        length = length + set_length;
      }
    } else {
      length = 0;
    }

    return length;
  }

  function getSetLength(index) {
    const key = question_sets[index];
    let length = test_store.question_set[key].questions.length;
    return length;
  }

  function addEventListenerToSideButtons(index) {
    const sideViewButtons = $("#question-btns").children("button"); //will select all the buttons
    console.log("SIDE", sideViewButtons.length);
    let true_length = getLengthTillSetIndex(index);
    let length = sideViewButtons.length;
    for (let i = 0; i < length; i++) {
      const button = sideViewButtons[i];
      // adding event to button listener
      button.addEventListener("click", function () {
        showSlide(true_length + i); //will update the slide
        addAnsweredOrSkippedClass(previousSlide); //will add class to buttons
      });
    }
  }

  //will add class to current button
  function addAnsweredOrSkippedClass(slideNumber) {
    let { tags, button } = getButtonAndTags(slideNumber);
    button = button[0];
    let temp_length = getLengthTillSetIndex(current_question_set);
    const key = question_sets[current_question_set];
    const index = slideNumber - temp_length;
    //test_store.question_set[key].questions
    const temp_status_check =
      answer_status_store[test_slides[slideNumber].id].status;
    if (
      temp_status_check !== "not-answered" &&
      temp_status_check !== "visited" &&
      !(temp_status_check === "normal") &&
      !setChangeButtonClicked &&
      !isSubmitButtionClicked &&
      !(isSubmitButtionClicked || wasLastSlide)
    ) {
      alert(
        `SORRY NO CLASS CAN BE ADDED, ${temp_status_check} , ${currentSlide}, ${index} , ${slideNumber}`,
      );
      return;
    }
    if (isAnswered(tags)) {
      button.setAttribute("class", "classic-btn  visited");
      test_store.question_set[key].questions[index].status = "visited"; //updating the status of button
      answer_status_store[test_slides[slideNumber].id].status = "visited";
      isSubmitButtionClicked = false;
    } else {
      button.setAttribute("class", "classic-btn  not-answered");
      test_store.question_set[key].questions[index].status = "not-answered";
      answer_status_store[test_slides[slideNumber].id].status = "not-answered";
      isSubmitButtionClicked = false;
    }
  }

  /* function for review button */
  function addReviewedOrNotReviewedClass(n) {
    let { button, tags } = getButtonAndTags(n);
    button = button[0];
    console.log(button);
    let temp_length = getLengthTillSetIndex(current_question_set);
    const key = question_sets[current_question_set];
    const index = n - temp_length;

    if (isAnswered(tags)) {
      button.setAttribute("class", "classic-btn answered-to-review");
      test_store.question_set[key].questions[index].status =
        "answered-to-review"; //updating the status of button
      answer_status_store[test_slides[currentSlide].id].status =
        "answered-to-review";
    } else {
      button.setAttribute("class", "classic-btn to-review");
      test_store.question_set[key].questions[index].status = "to-review"; //updating the status of button
      answer_status_store[test_slides[currentSlide].id].status = "to-review";
    }
  }
  /* CLOCK METHODS  */
  /* function to toggle the clock */
  function toggleClock(total_duration, type, start_time, end_time) {
    let total_time = total_duration;
    if (type == "strict") {
      let currentTime = new Date().getTime();
      let actualTotalTime = end_time - currentTime;
      if (localStorage.prev_time) {
        localStorage.total_time = actualTotalTime;
        total_time = localStorage.prev_time;
      }
    }
    setInterval(function () {
      setClock(total_time);
      randomUpdateTime(total_time);
      localStorage.total_time = total_time;
      if (total_time === total_duration / 2) {
        /* submit button available at half time */
        toggleSubmitButton();
      }

      /* calling function for each type of exam */
      if (type === "strict") {
        /* calling strictExam() */
        strictExamSubmit(end_time, total_time);
      } else {
        /* calling for looseExam() */
        looseExamSubmit(end_time, total_time);
      }
      /* colorchange */
      if (total_time < 300000) {
        changeTimeColor();
      }
      total_time = total_time - 1000; //because 1sec = 1000 milliseconds
      total_exam_duration = total_time;
    }, 1000);
  }

  function randomUpdateTime(total_time) {
    let random = parseInt(getRandomInt(1, 59));
    if (total_time % random === 0) {
      updateTime(total_time);
      testDataUpdate();
    }
  }

  /* change attribute from disabled to availabe */
  function toggleSubmitButton() {
    submit.disabled = false;
  }

  /* generate a random int which will ping server */
  function getRandomInt(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min)) + min; //The maximum is exclusive and the minimum is inclusive
  }

  /* function to handle the total time of exam and time elapsed */
  function setClock(total_duration) {
    /* since total_duration will be in milli sec we need to convert it into sec and min and hrs asap */
    //created a function convertMillisecondsToDigitalClock
    const time = convertMillisecondsToDigitalClock(total_duration);
    const sec = time.seconds;
    const min = time.minutes;
    const hr = time.hours;
    /* if time is over */
    if (sec + min + hr === 0) {
      window.location = "/test-submit";
      return;
    }
    let clock = "";
    if (hr == 0) {
      clock = min + "m" + " " + sec + "sec";
    } else {
      clock = hr + "hr" + " " + min + "m" + " " + sec + "sec";
    }
    time_clock.innerHTML = clock;
  }

  // CONVERT MILLISECONDS TO DIGITAL CLOCK FORMAT
  function convertMillisecondsToDigitalClock(ms) {
    let hours = Math.floor(ms / 3600000); // 1 Hour = 36000 Milliseconds
    let minutes = Math.floor((ms % 3600000) / 60000); // 1 Minutes = 60000 Milliseconds
    let seconds = Math.floor(((ms % 360000) % 60000) / 1000); // 1 Second = 1000 Milliseconds
    return {
      hours: hours,
      minutes: minutes,
      seconds: seconds,
    };
  }

  /* Showing toast at last min */
  function showToast() {
    const body = $("body");
    body.append(`<div id="toast">5 Min Remaining !! Hurry Up </div>`);
    var x = document.getElementById("toast");
    x.className = "show";
    setTimeout(function () {
      x.className = x.className.replace("show", "");
    }, 3000);
    changeTimeColor();
  }

  function changeTimeColor() {
    time_clock.removeAttribute("class", "black");
    time_clock.style.color = "red";
  }

  function updateTime(time) {
    localStorage.time = time;
  }

  /* function that will return us button and input tags of slide */
  function getButtonAndTags(n) {
    return {
      button: $(`#sideview${n}`),
      tags: $(`:input[name="question${n}"]`),
    };
  }

  /* function that will receive the radio buttons group and check is answered or not */
  function isAnswered(inputTags) {
    let isAnswered = false;
    inputTags.map((i) => {
      if (inputTags[i].checked) {
        isAnswered = true;
      }
    });
    if (isAnswered) {
      //will add the answer to test_store
      let temp_length = getLengthTillSetIndex(current_question_set);
      const key = question_sets[current_question_set];
      const index = currentSlide - temp_length;
      const answer = getAnsweredValue(inputTags);
      answer_status_store[test_slides[currentSlide].id].answer = answer;
      console.log(">ANSWERED", answer);
      test_store.question_set[key].questions[index].answer = [`${answer}`]; //updating the status of button
      test_store.question_set[key].questions[index].answered = true;
      answer_status_store[test_slides[currentSlide].id].answered = true;
    }
    return isAnswered;
  }

  function getAnsweredValue(inputTags) {
    let Answer = "";
    inputTags.map((i) => {
      if (inputTags[i].checked) {
        Answer = inputTags[i].value;
      }
    });
    return Answer;
  }

  /* function for clearing response */
  function clearResponse() {
    let inputTags = getButtonAndTags(currentSlide).tags;
    inputTags.map((i) => {
      if (inputTags[i].checked) {
        inputTags[i].checked = false;
      }
    });
    console.log("CLEAR RESPONSE CALLED !!!");
    let temp_length = getLengthTillSetIndex(current_question_set);
    const key = question_sets[current_question_set];
    const index = currentSlide - temp_length;
    test_store.question_set[key].questions[index].status = "not-answered"; //updating the status of button
    test_store.question_set[key].questions[index].answer = undefined; //updating the answer
    answer_status_store[test_slides[currentSlide].id].answer = undefined;
    answer_status_store[test_slides[currentSlide].id].answered = false;
    alert(answer_status_store[test_slides[currentSlide].id]);
  }

  //EVENT LISTENER BUTTON
  review.addEventListener("click", function (e) {
    e.preventDefault();
    let l1 = getSetLength(current_question_set);
    let l2 = getLengthTillSetIndex(current_question_set);

    console.log("L1:>>>", l1);
    console.log("L2:>>>", l2);

    if (!(currentSlide === l1 + l2 - 1)) {
      console.log(
        "@@@@@@@@@@@@@@@@@@@@@@@@SHOWIING NEXT SLIDE FOR REVIEW BUTTON@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@",
      );
      addReviewedOrNotReviewedClass(currentSlide);
      showNextSlide();
    } else {
      addReviewedOrNotReviewedClass(currentSlide);
      showSlide(currentSlide - 1);
    }
  });
  prev.addEventListener("click", function (e) {
    e.preventDefault();
    addAnsweredOrSkippedClass(currentSlide);
    showPreviousSlide();
  });

  next.addEventListener("click", function (e) {
    e.preventDefault();
    isSubmitButtionClicked = true;
    addAnsweredOrSkippedClass(currentSlide);
    showNextSlide();
  });

  clear.addEventListener("click", function (e) {
    e.preventDefault();
    alert("CLEAR RESPONSE BUTTON CLICKED");
    clearResponse();
  });

  submit.addEventListener("click", function (e) {
    e.preventDefault();
    // Adding table to the modal
    const testAttemptDataString = testAttemptData();
    //alert(testAttemptDataString)
    $("#table-body").html(testAttemptDataString);
    $("#confirmSubmitModal").modal({
      backdrop: "static",
    });
  });

  confirmSubmit.addEventListener("click", function (e) {
    e.preventDefault();
    $("#confirmSubmitModal").hide();
    submitTest();
  });

  instruction_button.addEventListener("click", function (e) {
    toggleFullScreen();
    getTestData();
    $("#instructions").hide();
  });

  function showNextSlide() {
    showSlide(currentSlide + 1);
  }

  function showPreviousSlide() {
    showSlide(currentSlide - 1);
  }

  /* strict and loose exam function */
  function strictExamSubmit(end_time, total_time) {
    const currentTime = newDate.getTime();
    if (total_time <= 0 || currentTime === end_time) {
      submitTest();
    }
    /* FOR SHOWING TOAST */
    if (total_time === 300000 || end_time - currentTime === 500000) {
      showToast();
    }
  }

  function looseExamSubmit(end_time, total_time) {
    if (total_time <= 0) {
      submitTest();
    }
    if (total_time === 300000) {
      showToast();
    }
    if (total_time <= 300000) {
      changeTimeColor();
    }
  }

  //this function will store the testdata to server
  function submitTest() {
    let serverData = {
      "test-info": {
        id: test_id,
      },
      "test-data": answer_status_store,
    };

    console.log("SUBMITTE TEST", answer_status_store);
    $.ajax({
      url: test_submit_url,
      type: "GET",
      data: { json: JSON.stringify(serverData) },
      contentType: "application/json",
      beforeSend: function () {
        //alert("sending");
      },
      success: function (data) {
        data = JSON.parse(data);
        console.log("data is : ", data);
        if (data.error == 0) {
          $("#marks-result").html(data.marks);
          $("#exampleModal").modal({
            backdrop: "static",
          });
        } else {
          alert("Something went wrong!");
        }
      },
      error: function (a, b, c) {
        alert("Error");
        console.log(a, b, c);
      },
    });
    localStorage.removeItem(answer_status_store);
  }

  function testDataUpdate() {
    test_store.test_duration = total_exam_duration;
    console.log("@@@########", user_id, test_id);
    let updatedTest = {
      user: {
        userId: user_id,
        testId: test_id,
        timeLeft: total_exam_duration.toString(),
        data: JSON.stringify(answer_status_store),
      },
    };
    console.log("test-store", test_store);
    console.log("TEST STATUS STORE ", answer_status_store);
    $.ajax({
      url: test_update_url,
      type: "POST",
      data: JSON.stringify(updatedTest),
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      success: function (data) {
        //console.log(data)
      },
    });
  }

  //method for creating object if not created to store object
  function ifAnswer_statusNotCreatedAlready(slides) {
    console.log("<><><>SLIDES FOR ANSWER STATUS STORE<><><>", slides);
    slides.map((slide) => {
      answer_status_store[slide.id] = {
        answer: slide.answer,
        status: slide.status,
        answered: false,
      };
    });
  }

  //if already created object
  function alreadyCreatedAnswer_Status() {
    $.get(test_get_store_url, function (data, status) {
      if (data !== 0 && typeof data === "object") {
        answer_status_store = data;
      } else {
        ifAnswer_statusNotCreatedAlready(test_slides);
      }
    });
  }
});

function toggleFullScreen() {
  if (
    (document.fullScreenElement && document.fullScreenElement !== null) ||
    (!document.mozFullScreen && !document.webkitIsFullScreen)
  ) {
    if (document.documentElement.requestFullScreen) {
      document.documentElement.requestFullScreen();
    } else if (document.documentElement.mozRequestFullScreen) {
      document.documentElement.mozRequestFullScreen();
    } else if (document.documentElement.webkitRequestFullScreen) {
      document.documentElement.webkitRequestFullScreen(
        Element.ALLOW_KEYBOARD_INPUT,
      );
    }
  } else {
    if (document.cancelFullScreen) {
      document.cancelFullScreen();
    } else if (document.mozCancelFullScreen) {
      document.mozCancelFullScreen();
    } else if (document.webkitCancelFullScreen) {
      document.webkitCancelFullScreen();
    }
  }

  $("#test-area").show();
}
$("#test-area").hide();
/* will remove the local storage variable */
//when browser closed - psedocode
$(window).on("unload", function (e) {
  localStorage.prev_time = localStorage.total_time;
  localStorage.removeItem(total_time);
});

var isTabActive = true;
var totalPenalties = 5;
var penalties = 0;
var testCancle = false;

function showSnack() {
  var num = totalPenalties - penalties;
  var data =
    "Warning: Tab Switching is not allowed.<br>Penalties remaining: <span id='p-num'>" +
    num +
    "/" +
    totalPenalties +
    "</span>";
  var x = document.getElementById("snackbar");
  var y = document.getElementById("snackbar-text");
  y.innerHTML = data;
  x.className = "show";

  setTimeout(function () {
    x.className = x.className.replace("show", "");
  }, 3000);
}

// function for caclulating how many questions are there and how many someone attempted
function testAttemptData() {
  let attemptData = [];
  console.log("TEST ATTEMPT DATA STORE CALLED");
  let keysArray = Object.keys(test_store.question_set); //we will get the keys here such as set-1 , set-2 ,set-3
  keysArray.forEach((key) => {
    //key would be like set-1 set-2 set-3
    console.log("KEY >>>>>>>>>", key);

    let setName = key;
    let setLength = test_store.question_set[key].questions.length;
    let attemptedQuestion = 0;

    test_store.question_set[key].questions.forEach((question, index) => {
      //question [{} , {} ,{} ]
      //{}
      console.log("question ", question);
      const id = question.id;
      const fromQuestionStatusStore = answer_status_store[id];
      if (fromQuestionStatusStore.answer) {
        attemptedQuestion += 1;
      }
    });

    let htmlString = `<tr>
                           <th scope="row">${setName}</th>
                           <td>${setLength}</td>
                           <td>${attemptedQuestion}</td>
                           </tr>`;

    attemptData.push(htmlString);
  });

  return attemptData.join("");
}

window.onfocus = function () {
  if (!isTabActive) {
    if (penalties <= totalPenalties) {
      showSnack();
      isTabActive = true;
    } else {
      testCancle = true;
    }
  }
  return false;
};

window.onblur = function () {
  if (penalties >= totalPenalties) {
    alert("You have exceeded the allowed penalties. Canceling your test now!");
    window.location.href = redirect_url_after_window_switch;
    return false;
  }
  isTabActive = false;
  penalties++;
};
