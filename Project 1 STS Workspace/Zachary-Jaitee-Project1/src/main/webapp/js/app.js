window.onload = () =>{
    console.log("in window.onload()");
    const appbody = document.getElementById('appbody');
    const SOURCE = document.getElementById('source');
    const DYNAMIC_CSS = document.getElementById('dynamic-css');
    loadLogin();
}

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

// LOGIN
//----------------------------------------------------------------------------------------------------------
//get request to view servlet
async function loadLogin(){
    console.log('loading login...')
    window.localStorage.setItem('jwt',null);
    appbody.innerHTML = await fetchView('login.view');
    configureLogin();
}

function configureLogin(){
    console.log('configuring login...');
    document.getElementById('login-button').addEventListener('click', login);
    document.getElementById('register').addEventListener('click', loadRegister);
}

async function login (){
    //need to get jwt from response somehow???
    // loadDashboard();
    console.log('inLogin');
    // let xhr = new XMLHttpRequest;
    // xhr.open('POST', 'auth', true);
    // xhr.send({
    //     username: document.getElementById('username'),
    //     password: document.getElementById('password')
    // });
    // console.log('sent');
    // xhr.onreadystatechange = () => {
    //     console.log('readystate: '+ xhr.readyState +' statuscode: '+ xhr.status);
    //     if (xhr.readyState == 4 && xhr.status == 200) {
    //         let userDetails = JSON.parse(xhr.responseText);
    //         console.log(userDetails);
    //         // checkLogin(userDetails);
    //     }
    // }
    let credentials = [];
    credentials.push('login'); 
   credentials.push(document.getElementById('username').value);
   credentials.push(document.getElementById('password').value);
   console.log(credentials[0] +" "+credentials[1]);
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
    if(response.status == 200){
        localStorage.setItem('jwt', response.headers.get('Authorization'));
        loadDashboard();
        console.log('200 response: ' + response);
    }
    else{
        console.log('not 200 response: ' + response);
    }
    
}


//------------------------------------------------------------------------------------------------------------------


// REGISTER
//-----------------------------------------------------------------------------------------------------------------
async function loadRegister(){
    appbody.innerHTML = await fetchView('register.view');
    configureRegister();
}

function configureRegister(){
    document.getElementById('register-button').addEventListener('click',register);
    DYNAMIC_CSS.href = 'register.css';
    //Thanks Bootstrap!
}

async function register(){
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
        console.log("failed");
        console.log(response);
    }
}

//------------------------------------------------------------------------------------------------------------------


// DASHBOARD
//-------------------------------------------------------------------------------------------------------------------
async function loadDashboard(){
    appbody.innerHTML = await fetchView('dashboard.view');
    configureDashboard();
    
}

function configureDashboard(){
    
    // SOURCE.src = 'dashboard.js';
    getTickets();
}
async function getTickets(){
    let response = await fetch('ticket', {
        method: 'GET',
        mode: 'cors',
        headers: {
            'Authorization': localStorage.getItem('jwt')
            
        }
    });    
    let body = await response.json();
    console.log(body);
    loadTable(body);
}

function loadTable(response){
    console.log('in loadtable');
    console.log(response);

    while (document.getElementById('tablebody').firstChild) {
        document.getElementById('tablebody').removeChild(document.getElementById('tablebody').firstChild);
    }

    for(let i=0; i < response.length; i++){
        let newRow = document.createElement('tr');
        newRow.innerHTML = `
        <td>${response[i].reimbId}</td>
        <td>${response[i].authorId}</td>
        <td>${response[i].amount}</td>
        <td>${response[i].type}</td>
        <td>${response[i].timeSubmitted}</td>
        <td>${response[i].timeResolved}</td>
        <td>${response[i].status}</td>`;

        if(response[i].status == 'PENDING'){
            newRow.innerHTML += `<td><button id="ApproveButton${i}">Approve</button></td>
                                <td><button id="DenyButton${i}">Deny</button></td>`;
        

        }
        document.getElementById('tablebody').appendChild(newRow);
    }
    for(let i=0; i < response.length; i++){
        if(document.getElementById(`ApproveButton${i}`))
            document.getElementById(`ApproveButton${i}`).addEventListener('click', selectApprove);
        if(document.getElementById(`DenyButton${i}`))
            document.getElementById(`DenyButton${i}`).addEventListener('click', selectDeny);
    }
}

function selectApprove(){
    console.log('in selectApprove');
}
function selectDeny(){
    console.log('in selectDeny');
}

//-------------------------------------------------------------------------------------------------------------------