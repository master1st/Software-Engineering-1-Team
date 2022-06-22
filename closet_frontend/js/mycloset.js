function loadItems(){
    return fetch("/html/data.json")
    .then((response)=>response.json())
    .then((json)=>json.items);
}
function displayItems(items) {
    const container = document.querySelector(".img_list");
    container.innerHTML = items.map((item) => createHTMLString(item)).join(""); // join을 해서 붙여줘야함
  }
  
  //items object를 HTML li형태로 리턴
  function createHTMLString(item) {
    return `
    <li class="item" onclick="location.href='/html/closetinfo.html?alt='+${item.numb}">
          <img src="${item.image}" alt="${item.type}" class="item__thumbnail" />
          <br></br>
          <span class="item__description">${item.gender}, ${item.size}</span>
          
    </li>`;
  }

loadItems().then((items)=>{
    console.log(items);
    displayItems(items);
})