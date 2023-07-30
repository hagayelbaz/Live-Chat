/**
 * This function validates the password and confirm password fields
 */

function validatePasswords(){
    const password = document.getElementById("password");
    const confirmPassword = document.getElementById("confirmPassword");

    const validate = () => {
        if (password.value !== confirmPassword.value) {
            confirmPassword.setCustomValidity("Passwords Don't Match");
        } else {
            confirmPassword.setCustomValidity('');
        }
    }

    password.onchange = validate;
    confirmPassword.onkeyup = validate;
}

window.onload = () =>{
    try{
    validatePasswords();
    }catch(e){}
}