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
  
  
  
  
  function deletefile(){
      
      const data={}
      data.userid= 'mycloset-userid'
      data.clothesId=clothes
  
      fetch(addurl, {
          method: "delete",
          headers: {
            'Content-Type': 'application/json',
          },
          mode: 'cors',
          body: JSON.stringify(data)
        }).then(response => {
          if(!response.deleteSuccess.ok) {
            alert("옷 삭제 완료!");
          }
      })
  }
  
  function updatefile(){
    const data={}
      data.userid= 'mycloset-userid'
      data.clothesId=clothes
  
      fetch(addurl, {
          method: "get",
          headers: {
            'Content-Type': 'application/json',
          },
          mode: 'cors',
          body: JSON.stringify(data)
        }).then(response => {
          if(!response.updatdeSuccess.ok) {
            alert("옷 수정 완료!");
          }
      })
  
  }