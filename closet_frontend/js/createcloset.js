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
}   

function addfile(){
    const clothes={
        weather:document.getElementById('weather'),
        type:document.getElementById('type'),
        color:document.getElementById('color'),
        material:document.getElementById('material'),
        clothesImage:document.getElementById("UploadPic").src
    }
    const data={}
    data.userid= 'mycloset-userid'
    data.clothes=clothes

    fetch('http://localhost:8080/mycloset/create', {
        method: "POST",
        headers: {
          'Content-Type': 'application/json',
        },
        mode: 'cors',
        body: JSON.stringify(data)
      }).then(response => {
        if(!response.createsuccess.ok) {
          alert("옷 등록 완료!");
        }
        else{
          alert("오류발생오류발생")
        }
    })

    

}

