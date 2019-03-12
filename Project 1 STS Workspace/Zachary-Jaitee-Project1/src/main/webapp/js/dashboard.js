// Dashboard functionality, loading of dashboard is done in app.ja
let userId = '';

function configureDashboard(){
    // getTickets();
    document.getElementById('dynamic-css').href = './css/dashboard.css';    
    
    document.getElementById('dash').addEventListener('click', mainDash);
    document.getElementById('tickets').addEventListener('click', getTickets);
    document.getElementById('add').addEventListener('click',createTicket);    
}

function mainDash(){

}


function createTicket(){
    console.log('in createTicket()');
    // clearBody();
    let create = document.createElement('div');

    
    // let optionTable = document.createElement('table')
    // optionTable.setAttribute('class','table-responsive');
    // optionTable.innerHTML = `<thead>
    // <tr>
    //   <th>Amount</th>
    //   <th>Type</th>
    //   <th>Description</th>
    //   </thead>`;
    // let row = document.createElement('tr');
    // optionTable.appendChild(row);
    // //AMOUNT
    // row.innerHTML = '<td><input maxlength="5" placeholder = "5.00" /></td>';
    
    //TYPE
    let enums = ['LODGING', 'TRAVEL', 'FOOD', 'OTHER'];
    //create a select box
    let selectbox = document.createElement('select');
    selectbox.setAttribute('id','type');
    let optionArray = [];
    console.log('creating options');
    for(let i = 0; i < enums.length; i++){
        optionArray[i] = document.createElement('option');
        optionArray[i].setAttribute('value',enums[i]);
        optionArray[i].innerText = enums[i];
        selectbox.appendChild(optionArray[i]);
    }
    // row.appendChild(document.createElement('td').appendChild(selectbox));
    //DESCRIPTION
    // row.innerHTML += '<td><input placeholder = "Description" /></td>';
    //
    create.appendChild(selectbox);
    document.getElementById('dashboardBody').appendChild(create);
    console.log('adding table to body');
    let ticket = [];
    //add,AuthorId,Amount,Type,TicketDescription
    ticket.push('add');
    ticket.push(userId);
    // submitTicket(ticket);
}

async function submitTicket(ticket){
    console.log('in submitTicket')
    let response = await fetch('auth', {
        method: 'POST',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(ticket)
    });
}

async function getTickets(){
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
    userId = response.headers.get('UserId');
    let body = await response.json();
    loadTable(body, role, userId);   
}


function loadTable(response, role, id){
    console.log('in loadtable');
    // console.log(response);
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
                newRow.innerHTML += `<td><button id="ApproveButton${i}">Approve</button></td>
                <td><button id="DenyButton${i}">Deny</button></td>`;
            
            }
        }
        
        document.getElementById('tablebody').appendChild(newRow);
    }
    //add event listener and style class to buttons
    for(let i = 0; i < response.length; i++){
        if(document.getElementById(`ApproveButton${i}`)){
            document.getElementById(`ApproveButton${i}`).addEventListener('click', updateTicket);
            document.getElementById(`ApproveButton${i}`).setAttribute('class','btn btn-sm btn-outline-secondary');
            document.getElementById(`ApproveButton${i}`).setAttribute('value','APPROVED');
            document.getElementById(`ApproveButton${i}`).setAttribute('name',response[i].reimbId);
        }
        if(document.getElementById(`DenyButton${i}`)){
            document.getElementById(`DenyButton${i}`).addEventListener('click', updateTicket);
            document.getElementById(`DenyButton${i}`).setAttribute('class','btn btn-sm btn-outline-secondary');
            document.getElementById(`DenyButton${i}`).setAttribute('value','DENIED');
            document.getElementById(`DenyButton${i}`).setAttribute('name',response[i].reimbId);
        }
    }
}

async function updateTicket(e){
    console.log('in updateTicket');
    let btns = document.getElementsByName(e.target.name);
    for(let i = 0; i < btns.length; i++){
        btns[i].disabled = true;
    }
    
    let ticketData = []
    ticketData.push('update');
    ticketData.push('0') //author is
    ticketData.push(userId);//resolver id
    ticketData.push(e.target.name); //reimbId
    ticketData.push(e.target.value); //status
    console.log(ticketData);
    //["update","2","3","42","APPROVED"]
    
    let response = await fetch('ticket', {
        method: 'POST',
        mode: 'cors',
        headers: {
            'Authorization': localStorage.getItem('jwt')            
        },
        body: JSON.stringify(ticketData)
    });  
    if(response.status == 200){
        getTickets();
    }
    
}

/*** HELPER FUNCTIONS */
function clearBody(){
    while (document.getElementById('dashboardBody').firstChild) {
        document.getElementById('dashboardBody').removeChild(document.getElementById('dashboardBody').firstChild);
    }
}