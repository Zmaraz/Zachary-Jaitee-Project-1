const appbody = document.getElementById('appbody');
document.getElementById('log-in').addEventListener('click', loadLogin);

//get request to view servlet
async function loadLogin(){
    console.log('loading login...')

    appbody.innerHTML = await fetchView('login.view');
    configureLogin();
}

function login (){
    
}

function configureLogin(){
    console.log('configuring login...');
    document.getElementsByName('log-in').addEventListener('click', login);
}

//helper method that gets the view for all the methods
async function fetchView(uri) {
    let response = await fetch(uri, {
        method: 'GET',
        mode: 'CORS',
        headers: {
            'headers': 'for later'
        }
    });
    return await response.text();
}

