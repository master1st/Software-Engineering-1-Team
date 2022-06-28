function loadItems(){
  url="http://localhost:8080/mycloset/?userCode="+localStorage.getItem('userCode')
    return fetch(url,{
      method:"GET"
    })
    .then((response)=>response.json())
    .then((json)=>json.clothesList)
}
function displayItems(clothesList) {
  console.log(clothesList)
    const container = document.querySelector(".img_list");
    container.innerHTML = clothesList.map((item) => createHTMLString(item)).join(""); // join을 해서 붙여줘야함
  }

function search(){

}
  
  //items object를 HTML li형태로 리턴
function createHTMLString(item) {
    return `
    <li class="item" onclick="location.href='/html/closetinfo.html?alt='+${item.clothesId}">
          <img src="${item.clothesImage}" alt="${item.type}" class="item__thumbnail" />
          <br></br>
          <span class="item__description">${item.color}, ${item.type}</span>
          
    </li>`;
  }

loadItems().then((clothesList)=>{
    console.log(clothesList);
    displayItems(clothesList);
})