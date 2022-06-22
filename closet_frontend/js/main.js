window.onload=function main(){
    if(localStorage.getItem('sessionid')!=null){
        document.getElementById("login").textContent="로그아웃";
        document.getElementById("로그인").setAttribute("onclick",logout())
    }
console.log("메인실행");
}

const weather = document.querySelector(".weather");
var requestOptions = {
  method: 'GET',
  redirect: 'follow'
};

fetch("https://api.openweathermap.org/data/2.5/weather?q=Seoul,kr&appid=bc18dc0c7a3e79577f1e9500b9e29a0f&units=metric", requestOptions)  
  .then(response => response.json())
  .then(data => {
    console.log(JSON.stringify(data));
    console.log(data.weather[0].main);
    loadWeather(data);
    todayClothes(data);
  });

function loadWeather(data) {
  let location = document.querySelector('.location');
  let currentTime = document.querySelector('.current-time');
  let currentTemp = document.querySelector('.current-temp');
  let feelsLike = document.querySelector('.feels-like');
  let minTemp = document.querySelector('.min-temp');
  let maxTemp = document.querySelector('.max-temp');
  let icon = document.querySelector('.icon');
  let weatherIcon = data.weather[0].icon;
  console.log(data);
  
  let date = new Date();
  let month = date.getMonth() + 1;
  let day = date.getDate();
  let hours = date.getHours();
  let minutes = date.getMinutes();

  console.log(data.main.temp_max);

  location.append(data.name);
  currentTemp.append(`${data.main.temp}c`);
  feelsLike.append(`${data.main.feels_like}c`);
  maxTemp.append(`최고:${data.main.temp_max}c`);
  minTemp.append(`최저:${data.main.temp_min}c`);
  icon.innerHTML = `<img src='http://openweathermap.org/img/wn/04d.png'>`;

  currentTime.append(`${month}월 ${day}일 ${hours}:${minutes}`);
}

function todayClothes(data) {
  let clothes = document.querySelector(".codytip");
  let currentTemp = data.main.temp;

  let winter = currentTemp <= 4;
  let earlyWinter = currentTemp >= 5 && currentTemp < 9;
  let beginWinter = currentTemp >= 9 && currentTemp < 12;
  let fall = currentTemp >= 12 && currentTemp < 17;
  let earlyFall = currentTemp >= 17 && currentTemp < 20;
  let earlySummer = currentTemp >= 20 && currentTemp < 23;
  let beginSummer = currentTemp >= 23 && currentTemp < 28;
  let summer = currentTemp >= 28;

  if(winter) {
    clothes.innerHTML = `
    <p>코디 Tip: 추운 날씨 입니다. <br>외투와 목도리를 준비하세요.</p>`;
  } else if(earlyWinter) {
    clothes.innerHTML = `
    <p>코디 Tip: 이른 겨울 입니다. <br>아우터에 따뜻한 옷들을 추천해드립니다.</p>`;
  } else if(beginWinter) {
    clothes.innerHTML = `
    <p>코디 Tip: 곧 겨울이 다가옵니다. <br>기모가 있는 옷들을 준비하세요.</p>`;
  } else if(fall) {
    clothes.innerHTML = `
    <p>코디 Tip: 가을의 날씨 입니다.<br>아우터를 하나 준비해두세요.</p>`;
  } else if(earlyFall) {
    clothes.innerHTML = `
    <p>코디 Tip: 날씨가 시원해집니다. <br>긴 옷을 추천해드립니다.</p>`;
  } else if(earlySummer) {
    clothes.innerHTML = `
    <p>코디 Tip: 날씨가 점점 더워집니다. <br>겉옷에 얇은옷을 준비해두세요.</p>`;
  } else if(beginSummer) {
    clothes.innerHTML = `
    <p>코디 Tip: 곧 여름이 다가오니 <br>시원한 옷을 준비하세요.</p>`;
  } else if(summer) {
    clothes.innerHTML = `
    <p>코디 Tip: 무더운 날씨 입니다. <br>얇은 옷이 좋을 것 같네요.</p>`;
  }
}


var buttonclick = true;
function showPopup(multipleFilter) {
	const popup = document.querySelector('#popup'); 
    const img = document.querySelector(`#${multipleFilter}`);
    var imgz = document.createElement('img');
    imgz.src = `${img.src}`;
    var src = document.getElementById("x");
    imgz.style.width='400px';
    if(buttonclick){
    src.appendChild(imgz);
    buttonclick = false;
    }
  if (multipleFilter) {
  	popup.classList.add('multiple-filter');
  } else {
  	popup.classList.remove('multiple-filter');
  }
  
  popup.classList.remove('hide');
}

function closePopup() {
	const popup = document.querySelector('#popup');
  popup.classList.add('hide');
}