

// cody Get 요청 
const mycody_1 = document.querySelector('.mycody_1');
const mycody_2 = document.querySelector('.mycody_2');
fetch("../test.json")
.then(response => {
   return response.json();
})
// .then(jsondata => console.log(jsondata))
.then(jsondata => {
  // jsondata.length 
  // jsondata[0].length
  for(i=0; i<jsondata.codyList.length; i++)
  {
    const img = document.createElement('img');
    img.setAttribute('class' , 'mycody_img');
    img.setAttribute('id' , jsondata.codyList[i][0].codyNum);
    img.setAttribute('src' , jsondata.codyList[i][0].codyImage);
    if(i<5){
    mycody_1.appendChild(img);
    } else {
      mycody_2.appendChild(img);
    }
    

    for(j=0; j<jsondata.codyList[i].length; j++)
    {
    // const img = document.createElement('img');
    // img.setAttribute('class' , 'mycloset_img');
    // img.setAttribute('id' , jsondata.codyList[i][j].clothesId);
    // img.setAttribute('src' , jsondata.codyList[i][j].clothesImage);
    // mycody_1.appendChild(img);
    // console.log(img.id);
    // 수정페이지에서 할것이 사진변경, 그리고 closet 올릴때, 사진 날씨정보나 그런것들 올리는 건데, 
    // 그거 변경에 대한 페이지가 필요한데 처음 이미지를 만들때의 그 정보가 필요해.
    // 사용자가 입력한 input들을 db 저장? 쌉가능 그냥 일반 div태그로 해서 query로 빼면되.
    // ㅇㅋ 바로간다. 그럼 수정하기에서 필요한건 먼저 필요한 데이터들을 담는 text필드, 그리고 
    // 삭제버튼과, 수정하기 버튼 ok 여기까지 하면 완벽 
    // 그니까 사진을 누르면 동시에 수정하기 페이지가 보여지며, fetch를 보내야해 방법은 ? 
    }

    if(jsondata.codyList.length > 10){
      //10개가 될때마다 새로운페이지
    }
  }
  
  for(i=0; i<jsondata.codyList.length; i++)
  {
    for(j=0; j<jsondata.codyList[i].length; j++)
    {
  img.addEventListener("click",() => {
    if(img.id === jsondata.codyList[i][j].codyNum){
      location.href = "http://127.0.0.1:5500/html/createcody.html";
      console.log(img.id);
      fetch(`http://localhost:8080/mycody/${img.id}`)
      .then(response => {
         return response.json();
         })
      .then(jsondata => console.log(jsondata))

  }
  })
  }
  }
})
// fetch('../test.json', {
//   method: 'GET',
// })
// .then(response => {
//   return response.json();
// })
// .then(data => {
//   data.map(item => {
//     const img = document.createElement('img');
//     img.setAttribute('class' , 'mycody_img');
//     img.setAttribute('id' , 'mycody_img');
//     img.setAttribute('src' , item.clothesImage);
//     game_field.appendChild(img);
// })   
// });



//get요청으로 mycloset 보내고, 그것을 문자열형태로 어디 저장해야하나?
var input = document.querySelector('input[type="file"]')

var data = new FormData()
data.append('file', input.files[0])
data.append('jsonString', {
  "clothesList": [
      {
          "clothesId": 1,
          "clothesImage": "파일명.jpg",
          "color": "red",
          "material": "leather",
          "type": "pants",
          "season": "fall",
          "userCode": 1
      },
      {
          "clothesId": 2,
          "clothesImage": "image",
          "color": "black",
          "material": "material",
          "type": "hat",
          "season": "all",
          "userCode": 1
      }
  ]
})

fetch('http://localhost:8080/mycody', {
  method: 'POST',
  body: data
})
.then(response => response.json())
  .then(data => {
    console.log(data)
  })
  .catch(error => {
    console.error(error)
  })


const handleImageUpload = event => {
  const files = event.target.files
  const formData = new FormData()
  formData.append('file', files[0])
  formData.append('jsonString', {
    "clothesList": [
      {
          "clothesId": 1,
          "clothesImage": "파일명.jpg",
          "color": "red",
          "material": "leather",
          "type": "pants",
          "season": "fall",
          "userCode": 1
      },
      {
          "clothesId": 2,
          "clothesImage": "image",
          "color": "black",
          "material": "material",
          "type": "hat",
          "season": "all",
          "userCode": 1
      }
  ]
  })

  fetch('http://localhost:8080/mycody', {
    method: 'POST',
    headers: { 
    },
    body: formData,
})
  .then(response => response.json())
  .then(data => {
    console.log(data)
  })
  .catch(error => {
    console.error(error)
  })
}



// 코디 수정하기 



// const cody_each = document.querySelector('.cody_each');

// cody_each.addEventListener(click => {
  
// })

// 메인 페이지에는 내가 DB에 저장한 코디들에 대한 정보를 빼와서 
// 그것들로 화면에 뿌려줄것임. 
//append child? 아니면 지수가 했던 `<li> 이런식? 

// 애초에 mycody 페이지에 들어가면, 보여주어야 하는것 아닌가? 

