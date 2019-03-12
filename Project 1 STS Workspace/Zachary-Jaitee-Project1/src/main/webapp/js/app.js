window.onload = () => {
    console.log("in window.onload()");
    
    document.getElementById('signout').addEventListener('click', logout);
    loadLogin();
}
const appbody = document.getElementById('appbody');
const SOURCE = document.getElementById('source');


//helper method that gets the view for all the methods
async function fetchView(uri) {
    console.log("in fetchView()");
    let response = await fetch(uri, {
        method: 'GET',
        mode: 'cors',
        headers: {
            'Authorization': localStorage.getItem('jwt')
        }
    });
    return await response.text();
}
function logout() {
    window.localStorage.setItem('jwt', null);
    loadLogin();
}
//helper method that returns false if any elements are falsey
function check() {
    let inputArray = document.getElementsByClassName('form-control');
    for (let i = 0; i < inputArray.length; i++) {
        if (inputArray[i].value == false) {

            return true;
        }
    }
    return false;
}
function badInput(){
    document.getElementsByClassName('alert-msg').item(0).hidden = false;
}

// LOGIN
//----------------------------------------------------------------------------------------------------------
//get request to view servlet
async function loadLogin() {
    console.log('loading login...')
    appbody.innerHTML = await fetchView('login.view');
    configureLogin();
}

function configureLogin() {
    console.log('configuring login...');
    document.getElementsByClassName('alert-msg').item(0).hidden = true;
    document.getElementById('dynamic-css').href = './css/register.css';
    document.getElementById('password').addEventListener('keyup', function (event) {
        //pressing enter works
        if (event.keyCode === 13) { login(); }
    });
    document.getElementById('login-button').addEventListener('click', login);
    document.getElementById('register').addEventListener('click', loadRegister);
}

async function login() {
    
    if (check()) {
        console.log('somethins empty');
        badInput();
    } else {
        document.getElementById('login-button').disabled = true;
        document.getElementsByClassName('alert-msg').item(0).hidden = true;
        console.log('inLogin');
        let credentials = [];
        credentials.push('login');
        credentials.push(document.getElementById('username').value);
        credentials.push(document.getElementById('password').value);
        console.log(credentials[0] + " " + credentials[1]);
        let response = await fetch('auth', {
            method: 'POST',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(credentials)
        });
        console.log('creds:' + JSON.stringify(credentials));
        console.log(response)
        if (response.status == 200) {
            localStorage.setItem('jwt', response.headers.get('Authorization'));
            loadDashboard();
            console.log('200 response: ' + response.body);
        }
        else {
            document.getElementById('login-button').disabled = false;
            badInput();
            console.log('not 200 response: ' + response.body);
        }
    }
}


//------------------------------------------------------------------------------------------------------------------


// REGISTER
//-----------------------------------------------------------------------------------------------------------------
async function loadRegister() {
    appbody.innerHTML = await fetchView('register.view');
    configureRegister();
}

function configureRegister() {
    document.getElementById('register-button').addEventListener('click', register);
    document.getElementById('log-in').addEventListener('click', loadLogin);
    document.getElementsByClassName('alert-msg').item(0).hidden = true;
}

async function register() {
    if (check()) {
        console.log('somethins empty');
        badInput();
    } else {
        document.getElementById('register-button').disabled = true;
        document.getElementsByClassName('alert-msg').item(0).hidden = true;
        let credentials = [];
        credentials.push('register');
        credentials.push(document.getElementById('firstname').value);
        credentials.push(document.getElementById('lastname').value);
        credentials.push(document.getElementById('username').value);
        credentials.push(document.getElementById('password').value);
        credentials.push(document.getElementById('email').value);

        console.log(credentials[0] + " " + credentials[1]);
        let response = await fetch('auth', {
            method: 'POST',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(credentials)
        });
        console.log(JSON.stringify(credentials));
        console.log(response)
        if (response.status == 200) {
            localStorage.setItem('jwt', response.headers.get('Authorization'));
            console.log(response);
            loadDashboard();
        }
        else {
            document.getElementById('register-button').disabled = false;
            document.getElementsByClassName('alert-msg').item(0).hidden = false;
            badInput();
            console.log("failed");
            console.log(response);
        }
    }

}

//------------------------------------------------------------------------------------------------------------------


// DASHBOARD
//-------------------------------------------------------------------------------------------------------------------
async function loadDashboard() {
    appbody.innerHTML = await fetchView('dashboard.view');
    configureDashboard();

}

//-------------------------------------------------------------------------------------------------------------------