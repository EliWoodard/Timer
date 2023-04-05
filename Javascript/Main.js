const timerPanel = document.getElementById("timer");
const countdownInput = document.getElementById("countdown-input");
const addTimeInput = document.getElementById("add-time-input");

let intervalId;
let startTime;
let elapsedTime = 0;
let countdownTime = 0;

function addTime() {
  const timeString = addTimeInput.value;
  const timeParts = timeString.split(":");
  const hours = parseInt(timeParts[0]) || 0;
  const minutes = parseInt(timeParts[1]) || 0;
  const seconds = parseInt(timeParts[2]) || 0;
  countdownTime += hours * 60 * 60 * 1000 + minutes * 60 * 1000 + seconds * 1000;
  addTimeInput.value = "";
}

function startTimer() {
  startTime = Date.now();
  intervalId = setInterval(updateTimer, 10);
}

function stopTimer() {
  clearInterval(intervalId);
}

function resetTimer() {
  elapsedTime = 0;
  countdownTime = 0;
  updateTimer();
}

function updateTimer() {
  const elapsedMilliseconds = Date.now() - startTime + elapsedTime;
  const remainingMilliseconds = countdownTime - elapsedMilliseconds;
  const time = formatTime(remainingMilliseconds);
  timerPanel.innerHTML = time;
  if (remainingMilliseconds <= 0) {
    stopTimer();
  }
}

function formatTime(milliseconds) {
  const totalSeconds = Math.floor(milliseconds / 1000);
  const hours = Math.floor(totalSeconds / 3600);
  const minutes = Math.floor((totalSeconds % 3600) / 60);
  const seconds = totalSeconds % 60;
  const timeString = `${padNumber(hours)}:${padNumber(minutes)}:${padNumber(seconds)}`;
  return timeString;
}

function padNumber(number) {
  return number.toString().padStart(2, "0");
}

document.querySelector(".left-panel button.active").addEventListener("click", () => {
  countdownInput.style.display = "none";
  addTimeInput.style.display = "block";
  timerPanel.innerHTML = "00:00:00";
});

document.querySelector(".left-panel button:not(.active)").addEventListener("click", () => {
  countdownInput.style.display = "block";
  addTimeInput.style.display = "none";
  timerPanel.innerHTML = "00:00:00";
});

document.getElementById("add-time-input").querySelectorAll("button").forEach(button => {
  button.addEventListener("click", () => {
    if (button.innerHTML === "Add") {
      addTime();
    } else if (button.innerHTML === "Start") {
      startTimer();
    } else if (button.innerHTML === "Stop") {
      stopTimer();
    } else if (button.innerHTML === "Reset") {
      resetTimer();
    }
  });
});

document.getElementById("countdown-input").querySelectorAll("button").forEach(button => {
  button.addEventListener("click", () => {
    if (button.innerHTML === "Start") {
      startTimer();
    } else if (button.innerHTML === "Stop") {
      stopTimer();
    } else if (button.innerHTML === "Reset") {
      resetTimer();
    }
  });
});
