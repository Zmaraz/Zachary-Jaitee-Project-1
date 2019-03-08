window.onload = () =>{
    console.log("in window.onload()");
    const appbody = document.getElementById('appbody');
    const SOURCE = document.getElementById('source');
    const DYNAMIC_CSS = document.getElementById('dynamic-css');
    loadLogin();
}

//get request to view servlet
async function loadLogin(){
    console.log('loading login...')

    appbody.innerHTML = await fetchView('login.view');
    configureLogin();
}

async function login (){
    //need to get jwt from response somehow???
    loadDashboard();

}

function configureLogin(){
    console.log('configuring login...');
    document.getElementById('sign-in-button').addEventListener('click', login);
    document.getElementById('register').addEventListener('click', loadRegister);
}

//helper method that gets the view for all the methods
async function fetchView(uri) {
    console.log("in fetchView()");
    let response = await fetch(uri, {
        method: 'GET',
        mode: 'cors',
        headers: {
            'headers': 'for later'
        }
    });
    return await response.text();
}

async function loadRegister(){
    appbody.innerHTML = await fetchView('register.view');
    configureRegister();
}

function configureRegister(){
    document.getElementById('log-in').addEventListener('click',loadLogin);
    DYNAMIC_CSS.href = 'register.css';
    //Thanks Bootstrap!
}

async function loadDashboard(){
    appbody.innerHTML = await fetchView('dashboard.view');
    configureRegister();
}

function configureDashboard(){
    SOURCE.src = 'dashboard.js';
}