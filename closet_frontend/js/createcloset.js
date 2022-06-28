function loginout(){
  console.log("클릭")
  if(localStorage.getItem('userCode')!=null){
    console.log("로그아웃")
    logout();
    window.location.reload()
  }
  else{
   window.location="http://127.0.0.1:5500/html/login.html"}

}

window.onload=function main(){
    if(localStorage.getItem('userCode')!=null)
        document.getElementById("login").textContent="로그아웃";     
}



function logout(){
  localStorage.removeItem('userCode')
  alert("로그아웃되었습니다.")
}

function loadFile(input){
  const all=document.getElementById('image-show')
  all.innerHTML="";
   
    var file = input.files[0]

    var newImage=document.createElement("img");
    newImage.setAttribute("class",'img')

    newImage.src = URL.createObjectURL(file);   

    newImage.style.width = "300px";
    newImage.style.height = "400px";
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


    const formdata= new FormData();
    formdata.append('file',UploadPic.files[0])
    formdata.append(
      "jsonClothesDto",
      JSON.stringify({
          season: season.options[season.selectedIndex].value,
          type:type.options[type.selectedIndex].value,
          color:color.options[color.selectedIndex].value,
          material: material.options[material.selectedIndex].value,
      })
    )
    formdata.append('userCode',localStorage.getItem('userCode'))


    console.log(UploadPic.files[0]);
    console.log(localStorage.getItem('userCode'));



    fetch('http://localhost:8080/mycloset/', {
        method: "POST",
        body: formdata
      }).then((response) => response.json())
      .then((data) => {
        if(data.success==true) {
          window.location = "http://127.0.0.1:5500/html/mycloset.html"}
        else{
          alert(data.message);
        }
    })

    console.log("fetch 요청은 성공")

}

