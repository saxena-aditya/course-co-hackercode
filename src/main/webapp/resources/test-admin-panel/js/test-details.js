(function () {
  let data = JSON.parse(document.getElementById("test-data").value);
  let ctx = document.getElementById("test-cnv").getContext("2d");
  let username = [];
  let marks = [];
  let maxMarks = [];
  console.log(data, username, marks, maxMarks);

  data.forEach(function (item, ind) {
    username.push(item.username);
    marks.push(item.marks);
    maxMarks.push(item.max_marks);
  });
  var config = {
    type: "line",
    data: {
      labels: username,
      datasets: [
        {
          label: "Obtained Marks",
          backgroundColor: "red",
          borderColor: "red",
          data: marks,
          fill: false,
        },
        {
          label: "Maximum Marks",
          fill: false,
          backgroundColor: "blue",
          borderColor: "blue",
          data: maxMarks,
        },
      ],
    },
    options: {
      responsive: true,
      title: {
        display: true,
        text: "Total Marks Obtained by Students",
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
              labelString: "Name",
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
          },
        ],
      },
    },
  };

  window.myLine = new Chart(ctx, config);
})();
