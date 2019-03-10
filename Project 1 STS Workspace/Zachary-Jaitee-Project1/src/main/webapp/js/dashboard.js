// Dashboard functionality, loading of dashboard is done in app.ja

function configureDashboard(){
    getTickets();
    document.getElementById('dynamic-css').href = './css/dashboard.css';
    // let tableHidden = true;
    document.getElementById('ticketTable').hidden = true;
    //method to make ticket visible when btn clicked
    document.getElementById('tickets').addEventListener('click', toggleTable);
    document.getElementById('add').addEventListener('click',createTicket);
    
}

function toggleTable(e){
    document.getElementById('ticketTable').hidden = false;
}

function createTicket(){
    console.log('in createTicket()');
}

async function getTickets(){
    // tableHidden = false;
    console.log('in getTickets()');
    let response = await fetch('ticket', {
        method: 'GET',
        mode: 'cors',
        headers: {
            'Authorization': localStorage.getItem('jwt')
            
        }
    });    
    console.log(response.headers);
    console.log('Headers has role: '+ response.headers.has('UserRole'));
    let role = response.headers.get('UserRole');
    let body = await response.json();
    loadTable(body, role);
   
}

function loadTable(response, role){
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

        if(role === 'manager'){
            if(response[i].status == 'PENDING'){
                newRow.innerHTML += `<td><button id="ApproveButton${i}">Approve</button></td>
                                    <td><button id="DenyButton${i}">Deny</button></td>`;
            
            }
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