// Dashboard functionality, loading of dashboard is done in app.ja

function configureDashboard(){
    // getTickets();
    document.getElementById('dynamic-css').href = './css/dashboard.css';    
    
    //method to make ticket visible when btn clicked
    document.getElementById('tickets').addEventListener('click', getTickets);
    document.getElementById('add').addEventListener('click',createTicket);    
}


function createTicket(){
    console.log('in createTicket()');
    clearBody();
    
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

    console.log('Headers has role: '+ response.headers.has('UserRole'));
    console.log('Headers has id: '+ response.headers.has('UserId'));
    let role = response.headers.get('UserRole');
    let id = response.headers.get('UserId');
    let body = await response.json();
    loadTable(body, role, id);   
}

function clearBody(){
    while (document.getElementById('dashboardBody').firstChild) {
        document.getElementById('dashboardBody').removeChild(document.getElementById('dashboardBody').firstChild);
    }
}

function loadTable(response, role, id){
    console.log('in loadtable');
    console.log(response);
    clearBody();    

    //creates the table
    document.getElementById('dashboardBody').innerHTML=
    `<div class="table-responsive" id = "ticketTable">
    <h2>All Tickets</h2>
     <table class="table table-striped table-sm">
       <thead>
         <tr id = "tableHead">
           <th>ID</th>
           <th>Author</th>
           <th>Amount</th>
           <th>Type</th>
           <th>Description</th>
           <th>Submission Time</th>
           <th>Resolution Time</th>
           <th>Status</th>
         </tr>
       </thead>
       <tbody id="tablebody"></tbody>
     </table>
   </div>`;

    for(let i=0; i < response.length; i++){
        let newRow = document.createElement('tr');
        newRow.innerHTML = `
        <td>${response[i].reimbId}</td>
        <td>${response[i].authorId}</td>
        <td>${response[i].amount}</td>
        <td>${response[i].type}</td>
        <td>${response[i].ticketDescription}</td>
        <td>${response[i].timeSubmitted}</td>
        <td>${response[i].timeResolved}</td>
        <td>${response[i].status}</td>`;

        if(role === 'manager'){
            
            //checks that the response is pending and the logged in user did not make the ticket
            
            if(response[i].status == 'PENDING' && response[i].authorId != id){
                newRow.innerHTML += `<td><button id="ApproveButton${response[i].reimbId}">Approve</button></td>
                <td><button id="DenyButton${response[i].reimbId}">Deny</button></td>`;
            
            }
        }
        
        document.getElementById('tablebody').appendChild(newRow);
    }
    //add event listener and style class to buttons
    for(let i=0; i < response.length; i++){
        if(document.getElementById(`ApproveButton${response[i].reimbId}`)){
            document.getElementById(`ApproveButton${response[i].reimbId}`).addEventListener('click', selectApprove);
            document.getElementById(`ApproveButton${response[i].reimbId}`).setAttribute('class','btn btn-sm btn-outline-secondary');
        }
        if(document.getElementById(`DenyButton${response[i].reimbId}`)){
            document.getElementById(`DenyButton${response[i].reimbId}`).addEventListener('click', selectDeny);
            document.getElementById(`DenyButton${response[i].reimbId}`).setAttribute('class','btn btn-sm btn-outline-secondary');
        }
    }
}

function selectApprove(e){
    console.log('in selectApprove');
    console.log(e.target);
}
function selectDeny(e){
    console.log('in selectDeny');
    console.log(e.target);
}