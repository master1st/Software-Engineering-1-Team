// 코디에 대한 id를 가져와서 그놈에 대한 개별 ITEM 에대한 ID를 가져와서 js에서 child 추가해서 출력
var axios = require('axios');

var config = {
  method: 'get',
  url: 'https://api.openweathermap.org/data/2.5/weather?q=Seoul,kr&appid=bc18dc0c7a3e79577f1e9500b9e29a0f',
  headers: { }
};

axios(config)
.then(function (response) {
  console.log(JSON.stringify(response.data));
})
.catch(function (error) {
  console.log(error);
});

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