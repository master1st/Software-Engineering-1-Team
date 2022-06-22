function loadFile(input){

    var file = input.files[0]

    var newImage=document.createElement("img");
    newImage.setAttribute("class",'img')

    newImage.src = URL.createObjectURL(file);   

    newImage.style.width = "70%";
    newImage.style.height = "70%";
    newImage.style.objectFit = "contain";

    var container = document.getElementById('image-show');
    container.appendChild(newImage);
    console.log("이미지 삽입 성공")


}   



function addfile(){



    const season=document.getElementById('season')
    const type=document.getElementById('type')
    const color=document.getElementById('color')
    const material=document.getElementById('material')
    const UploadPic=document.getElementById('UploadPic')
    console.log(UploadPic)

    const selecteddata = {}
    selecteddata.season= season.options[season.selectedIndex].value
    selecteddata.type=type.options[type.selectedIndex].value
    selecteddata.color=color.options[color.selectedIndex].value
    selecteddata.material=material.options[material.selectedIndex].value

    const data = new FormData();
    data.append('jsonClothesDto',selecteddata)
    data.append('file',UploadPic.files[0])
    console.log("11");
    for (var pair of data.entries()) {
      console.log(pair[0]+ ', ' + pair[1]); 
  }


    fetch('http://localhost:8080/mycloset/', {
        method: "POST",
        body: data
      }).then((response) => response.json())
      .then((data) => {
        if(data.success==true) {
          alert("옷 등록 완료!");
        }
        else{
          alert("옷 등록 실패");
        }
    }).catch(error => {
      console.error(error)
    })

    console.log("fetch 요청은 성공")

}

