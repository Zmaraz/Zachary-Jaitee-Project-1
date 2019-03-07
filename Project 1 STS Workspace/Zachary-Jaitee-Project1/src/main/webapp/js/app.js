window.onload = () =>{
    console.log("in window.onload()");
    const appbody = document.getElementById('appbody');
    document.getElementById('log-in').addEventListener('click', loadLogin);
}



//get request to view servlet
async function loadLogin(){
    console.log('loading login...')

    appbody.innerHTML = await fetchView('login.view');
    configureLogin();
}

async function login (){

}

function configureLogin(){
    console.log('configuring login...');
    document.getElementById('sign-in-button').addEventListener('click', login);
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

