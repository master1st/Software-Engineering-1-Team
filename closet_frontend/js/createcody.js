var j;
// cody Get 요청 
const mycody_1 = document.querySelector('.mycody_1');
const mycody_2 = document.querySelector('.mycody_2');
fetch("../test.json")
.then(response => {
   return response.json();
})

.then(jsondata => {
  // jsondata.length 
  // jsondata[0].length
  j = jsondata;
  for(i=0; i<jsondata.codyList.length; i++)
  {
    
    const img = document.createElement('img');
    img.setAttribute('class' , 'mycody_img');
    img.setAttribute('id' , jsondata.codyList[i][0].codyNum);
    img.setAttribute('src' , jsondata.codyList[i][0].codyImage);
    img.onclick= function() {
      img_detail(img);
    }
    
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
  }
})
// closet get 요청 "http://localhost:8080/mycloset/"
const closet1 = document.querySelector('.closet1');
const box = document.querySelector('.box');
fetch("../test_post.json")
.then(response => {
   return response.json();
})  
.then(jsondata => {
  console.log(jsondata.clothesList)
  jsondata.clothesList.map(item =>{
    const img = document.createElement('img');
    img.setAttribute('class' , 'mycloset_img');
    img.setAttribute('id' , item.clothesId);
    img.setAttribute('src' , item.clothesImage);
    img.setAttribute('ondragstart', 'dragStart(event);');
    img.setAttribute('draggable', 'true');
    img.setAttribute('ondragend', 'dragEnd(event)');
    img.onclick= function() {
      img_detail(img);
    }
    box.appendChild(img);
  })
  // console.log(jsondata.clothesList[0]);
  // for(i=0; i<jsondata.codyList.length; i++)
  // { 
  //   //append child 자식 추가해서, 거기서 또 filtering을 how
  //   //mycody -> 수정하려면, 옷 정보들만 넘겨줘서 그것들로 진행하면된다.
  //   //현재 season과 userCode 포함. 
  //   const img = document.createElement('img');
  //   img.setAttribute('class' , 'mycody_img');
  //   img.setAttribute('id' , jsondata.codyList[i][0].codyNum);
  //   img.setAttribute('src' , jsondata.codyList[i][0].codyImage);
  //   img.onclick= function() {
  //     img_detail(img);
  //   }
  //   box.appendChild(img);
  // }
})



// cody Create 요청 
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
// data.append('jsonString', {
//   "clothesList": [
//       {
//           "clothesId": 1,
//           "clothesImage": "파일명.jpg",
//           "color": "red",
//           "material": "leather",
//           "type": "pants",
//           "season": "fall",
//           "userCode": 1
//       },
//       {
//           "clothesId": 2,
//           "clothesImage": "image",
//           "color": "black",
//           "material": "material",
//           "type": "hat",
//           "season": "all",
//           "userCode": 1
//       }
//   ]
// })

fetch('http://localhost:8080/mycody', {
  method: 'POST',
//   headers: {"Content-Type": "multipart/form-data"
// },
  body: data
})
.then(response => response.json())
  .then(data => {
    console.log(data)
  })
  .catch(error => {
    console.error(error)
  })





  //코디 Update 수정하기 요청
  const cody_update = document.querySelector('.cody_update');
  function img_detail(mycody_img){
    fetch(`http://localhost:8080/mycody/${mycody_img.id}`, {
    method: 'PUT',
  })
  .then(response => {
  return response.json();
  })
  .then(data => {
  data.map(item => {
    const img = document.createElement('img');
    img.setAttribute('class' , 'mycody_img');
    img.setAttribute('id' , 'mycody_img');
    img.setAttribute('src' , item.clothesImage);
    img.onclick= function() {
      img_delete(img);
    }
    cody_update.appendChild(img);
    location.href = "http://127.0.0.1:5500/html/createcody.html";
})   
});
}

  //코디 Delete 삭제하기 요청 
  function img_delete(delete_img){
    cody_update.removeChild(delete_img);
    fetch(`http://localhost:8080/mycody/${mycody_img.id}`, {
    method: 'DELETE',
  })
  .then(response => {
  return response.json();
  })
  .then(data => {
    return data;
    //이미지 클릭이 전제된 함수안에서, 클릭시에 fetch보내서 delete 보내고 
    //appendchild.remove 인가 이것만 하면 실제 삭제가 된다. 
    
    
    location.href = "http://127.0.0.1:5500/html/createcody.html";
})   
};





// const handleImageUpload = event => {
//   const files = event.target.files
//   const formData = new FormData()
//   formData.append('file', files[0])
//   formData.append('jsonString', {
//     "clothesList": [
//       {
//           "clothesId": 1,
//           "clothesImage": "파일명.jpg",
//           "color": "red",
//           "material": "leather",
//           "type": "pants",
//           "season": "fall",
//           "userCode": 1
//       },
//       {
//           "clothesId": 2,
//           "clothesImage": "image",
//           "color": "black",
//           "material": "material",
//           "type": "hat",
//           "season": "all",
//           "userCode": 1
//       }
//   ]
//   })

//   fetch('http://localhost:8080/mycody', {
//     method: 'POST',
//     headers: { 
//     },
//     body: formData,
// })
//   .then(response => response.json())
//   .then(data => {
//     console.log(data)
//   })
//   .catch(error => {
//     console.error(error)
//   })
// }



// 옷 drag & drop 
allowDrop = function() {
  event.preventDefault();
};

dropItem = function() {
  var _targetEle = event.target;
  var _id = event.dataTransfer.getData('text');
  var _moveEle = document.getElementById(_id );
  _targetEle.before(_moveEle);
};

dragStart = function() {
  var _thisEle = event.target;
  var _thisId = _thisEle.id;
  _thisEle.classList.add('is-dragging');
  event.dataTransfer.setData('text/plain', _thisId);
};

dragEnd = function() {
  var _thisEle = event.target;
  _thisEle.classList.remove('is-dragging');
};


//코디 Detail 팝업 요청 
// const cody_each = document.querySelector('.cody_each');

// cody_each.addEventListener(click => {
  
// })

// 메인 페이지에는 내가 DB에 저장한 코디들에 대한 정보를 빼와서 
// 그것들로 화면에 뿌려줄것임. 
//append child? 아니면 지수가 했던 `<li> 이런식? 

// 애초에 mycody 페이지에 들어가면, 보여주어야 하는것 아닌가? 

// for(i=0; i<jsondata.codyList.length; i++)
// {
// for(j=0; j<jsondata.codyList[i].length; j++)
// {
//   console.log(jsondata.codyList);
//   if (mycody_img.id == jsondata.codyList[i][j].codyNum){
//   if (mycody_img.id == jsondata.codyList[i][j+1].codyNum){
//     var img=new Image();
//     var btn=new Button();
//     img.src=jsondata.codyList[i][j].clothesImage;
//     var img_width=img.width+500;
//     var win_width=img.width+25;
//     var img_height=img.height+500;
//     var win=img.height+30;
    
//     //================================
//     var img2=new Image();
//     img2.src=jsondata.codyList[i][j].clothesImage;
//     var img_width=img2.width+500;
//     var win_width=img2.width+25;
//     var img_height=img2.height+500;
//     var win=img2.height+30;
//     var OpenWindow=window.open('','_blank', 'width='+img_width+', height='+img_height+', menubars=no, scrollbars=auto');
//     OpenWindow.document.write("<style>body{margin:0px;}</style><img src='"+jsondata.codyList[i][j].clothesImage+"' width='"+win_width+"'>");
//     OpenWindow.document.write("<style>body{margin:0px;}</style><img src='"+jsondata.codyList[i][j+1].clothesImage+"' width='"+win_width+"'>");
//     OpenWindow.document.write("<style>body{margin:0px;}</style><img src='"+jsondata.codyList[i][j+1].clothesImage+"' width='"+win_width+"'>");
//     OpenWindow.document.write("<style>body{margin:0px;}</style><img src='"+jsondata.codyList[i][j+1].clothesImage+"' width='"+win_width+"'>");
    
//   }
//   }
// }
// }