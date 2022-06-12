// var imgfile;

// function loadFile(input) {
//     var file = input.files[0];	//선택된 파일 가져오기
//     imgfile = file;
//     console.log(imgfile);
//     //미리 만들어 놓은 div에 text(파일 이름) 추가
//     // var name = document.getElementById('fileName');
//     // name.textContent = file.name;

//     //새로운 이미지 div 추가
//     var newImage = document.createElement("img");
//     newImage.setAttribute("class", 'img');

//     //이미지 source 가져오기
//     newImage.src = URL.createObjectURL(file);

//     newImage.style.width = "70%";
//     newImage.style.height = "70%";
//     newImage.style.visibility = "hidden";   //버튼을 누르기 전까지는 이미지를 숨긴다
//     newImage.style.objectFit = "contain";

//     //이미지를 image-show div에 추가
//     var container = document.getElementById('image-show');
//     // container.appendChild(newImage);
    
// function loadFile(input) {
// const data = new FormData();
// // data.enctype = "multipart/form-data";
// data.append('file', input.files[0]);
// data.append('JsonString', "asddas");
// console.log(data);
// fetch("http://localhost:8080/mycody", {
//   method: "POST",
//   // headers: {
//   // "Content-Type": "multipart/form-data",
//   // }, 
//   // headers 를 비워준다.
//   body: data



//   // body 엔 JS object 가 아닌 form 을 그대로 보낸다.
// });
// };
// document.querySelector('#fileUpload').addEventListener('onchange', event => {
//   handleImageUpload(event);
// })

//create cody {경은이누나한테 사진 따로 json data 따로 보내야 될것같다고 말해야할듯.}





      //클릭한놈의 item id랑 json 파일의 codyNum이랑 비교해서 값이 같으면, 그놈에 대한 페이지로 이동과 동시에, 
      //그 옷들을 보여줌 
      //나는 이해가 안되는게. 도대체 ㅅㅂ 저 코디이미지들은 워떻게 보여줄거냐 아썅;;;
      //안보이는거 invisible로 해놨다가, 아니지.. 이미 해놨네 위에 ?

//구현 1 만약 코디 리스트가 10개 이상이면 다음 페이지로 이동 , 

// cody Get 요청 
const mycody_1 = document.querySelector('.mycody_1');
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
    mycody_1.appendChild(img);

    for(j=0; j<jsondata.codyList[i].length; j++)
    {
    const img = document.createElement('img');
    img.setAttribute('class' , 'mycloset_img');
    img.setAttribute('id' , jsondata.codyList[i][j].clothesId);
    img.setAttribute('src' , jsondata.codyList[i][j].clothesImage);
    mycody_1.appendChild(img);
    console.log(img.id);
    
    img.addEventListener("click",() => {
      if(img.id === jsondata.codyList[i][j].codyNum){
        location.href = `http://localhost:8080/mycody/${img.id}`;

        // 새탭으로 띄워서 innerhtml 코디 보여주기
         
      }
    })
    }
  }
//이렇게 첫번째 줄은 코디 리스트고 2번째줄이 옷 아이템 리스트

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



const handleImageUpload = event => {
  const files = event.target.files
  const formData = new FormData()
  formData.append('myFile', files[0])

  fetch('http://localhost:8080/mycody', {
    method: 'POST',
    headers: { 'Content-Type' : 'multipart/form-data'
    },
    body: JSON.stringify({ formData , 
    "jsonString": {
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
              }
  })
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

