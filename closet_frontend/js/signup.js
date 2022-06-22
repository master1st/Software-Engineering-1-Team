function signup() {

    //회원가입 버튼 클릭했을때 발생하는 이벤트
        const signupurl = "signupurl"
        const SignupId = document.querySelector('#id').value
        const SignupPw = document.querySelector('#password').value
        const validPw = document.querySelector('#passwordcheck').value

        const data = {}
        data.id = SignupId
        data.password = SignupPw
        //ifelse로 pw일치하는지 확인
        if (SignupPw != validPw) {
            alert("패스워드가 일치하지 않습니다.");
            return false;
        }
        else {
            fetch("http://localhost:8080/signup", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json',
                },
                mode: 'cors',
                body: JSON.stringify(data)
            }).then((response) => response.json())
            .then((data) => {
                console.log(data)
                if (data.success==true) {
                    alert("회원가입성공");
                }
                else{
                    alert(data.message)
                }
            });
        }

}