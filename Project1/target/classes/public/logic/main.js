
function attachEvents() {
    document.addEventListener('click', action);
}

const action = (e) => {
    e.preventDefault();
    const login__btn = document.getElementById("login__btn")
    let email = document.getElementById("email").value

    let password = document.getElementById("password").value
    e.target == login__btn && login(email, password);
}


const login = async (email, password) => {
    if(email != '' || password != ''){
        
        let employeeInfo = await getEmployee();
        if(email == employeeInfo.email && password == employeeInfo.password){
            employeeInfo.type == 'manager' ? location.href = "manager.html" : location.href = "employee.html";
            localStorage.setItem('employeeId', employeeInfo.id);
            localStorage.setItem('name', employeeInfo.name);
            localStorage.setItem('type', employeeInfo.type);
			localStorage.setItem('email', employeeInfo.email);
        }else{
            alert("Invalid email/password. Please re-enter")
        }
    }
}

async function getEmployee(){
    let email = document.getElementById("email").value;
    try{
        let res = await fetch(`http://localhost:7000/api/employee/${email}`)
        let data = res.json();
		console.log(data);
        return data;
    } catch (e) {
        throw (e.message);
    }
}

attachEvents()




/*
function setFormMessage(formElement, type, message){
    const messageElement = formElement.querySelector(".form__message");

    messageElement.textContent = message;
    messageElement.classList.remove("form__message--success", "form__message--error");
    messageElement.classList.add(`form__message--${type}`);
}

function setInputError(inputElement, message){
    inputElement.classList.add("form__input--error");
    inputElement.parentElement.querySelector(".form__input-error-message").textContent = message;
}

function clearInputError(inputElement){
    inputElement.classList.remove("form__input--error");
    inputElement.parentElement.querySelector(".form__input-error-message").textContent="";
}

document.addEventListener("DOMContentLoaded", () =>{
    const loginForm = document.querySelector("#login");
    const createAccountForm = document.querySelector("#createAccount");

    loginForm.addEventListener("submit", e => {
        e.preventDefault();
        // const Http = new XMLHttpRequest();
        const url = 'http://localhost:7000/';
        let email = document.getElementById("email").value
        Http.open("GET",url);
        // Http.send();
        // Http.onreadystatechange = (e) =>{
        //     console.log(Http.responseText);
        // }
        fetch(url)
        .then(data=>{return data.json()})
        .then(res=>{console.log(res)})
        //perform fetch login

        setFormMessage(loginForm, "error", "Invalid username/password combination");
    });
    inputElement.addEventListener("input", e =>{
        clearInputError(inputElement);
    });
});

*/