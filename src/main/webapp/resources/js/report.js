(function () {
  let data = document.getElementById("graph-data").value;
  let setCtx = document.getElementById("set-wise").getContext("2d");
  let qCtx = document.getElementById("questions-attempt").getContext("2d");

  data = JSON.parse(data);
  let labels = [];
  let maxVal = [];
  let userVal = [];
  let maxQuestions = [];
  let attemptedQuestions = [];

  for (key in data) {
    labels.push(key);
    maxVal.push(data[key].max);
    userVal.push(data[key].user);
    maxQuestions.push(data[key].question_num);
    attemptedQuestions.push(data[key].question_answered);
  }
  console.log(data, labels, maxVal, userVal);

  let setChart = new Chart(setCtx, {
    // The type of chart we want to create
    type: "radar",

    // The data for our dataset
    data: {
      labels: labels,
      datasets: [
        {
          label: "Scorable Marks",
          backgroundColor: "#673ab7",
          borderColor: "#673ab7",
          data: maxVal,
          fill: false,
        },
        {
          label: "Scored Marks",
          backgroundColor: "rgb(255, 29, 132)",
          borderColor: "rgb(255, 99, 132)",
          data: userVal,
          fill: false,
        },
      ],
    },

    // Configuration options go here
    options: {
      maintainAspectRatio: true,
      spanGaps: false,
      elements: {
        line: {
          tension: 0.000001,
        },
      },
      plugins: {
        filler: {
          propagate: false,
        },
        "samples-filler-analyser": {
          target: "chart-analyser",
        },
      },
    },
  });

  let qChart = new Chart(qCtx, {
    // The type of chart we want to create
    type: "bar",

    // The data for our dataset
    data: {
      labels: labels,
      datasets: [
        {
          label: "Total Questions",
          backgroundColor: "#673ab7",
          borderColor: "#673ab7",
          data: maxQuestions,
          fill: false,
        },
        {
          label: "Attempted Questions",
          backgroundColor: "rgb(255, 29, 132)",
          borderColor: "rgb(255, 99, 132)",
          data: attemptedQuestions,
          fill: false,
        },
      ],
    },

    // Configuration options go here
    options: {
      responsive: true,
      title: {
        display: true,
        text: "Questions Attempted",
      },
      tooltips: {
        mode: "index",
        intersect: false,
      },
      hover: {
        mode: "nearest",
        intersect: true,
      },
      scales: {
        xAxes: [
          {
            display: true,
            scaleLabel: {
              display: true,
              labelString: "Question Set",
            },
          },
        ],
        yAxes: [
          {
            display: true,
            scaleLabel: {
              display: true,
              labelString: "Marks",
            },
            ticks: {
              beginAtZero: true,
            },
          },
        ],
      },
    },
  });
})();
